package mx.gob.imss.ctirss.correccion.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CorrecionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.invitacion.service.interfaces.InvitacionService;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.utils.Functions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/solicitud/autorizacionDetalle")
public class AutorizacionSolicitudDetalleController extends AbstractController {

	private static Integer SOLICITUD_AUTORIZADA = 2;
	private static Integer SOLICITUD_RECHAZADA = 6;
	@Autowired
	private SolicitudService<CrtAnexosolcorrpat> solicitudService;

	@Autowired
	private PromocionService<?> promocionService;

	@Autowired
	private InvitacionService<?> invitacionService;

	@RequestMapping(method = RequestMethod.GET)
	public String getCreateForm(Model model, HttpServletRequest request) {
		model.addAttribute(new CrtSolicitudcorr());
		return "autorizacionSolicitud/detalle";
	}

	@RequestMapping(value = "/getSolicitud", method = RequestMethod.POST)
	public @ResponseBody
	CrtSolicitudcorr getSolicitud(@RequestBody CrtSolicitudcorr patron,
			HttpServletResponse response, HttpServletRequest request) {
		CrtSolicitudcorr solicitud = (CrtSolicitudcorr) request.getSession()
				.getAttribute("solicitudSeleccionada");
		if (solicitud != null) {
			solicitud = solicitudService.getSolicitudDetalles(solicitud,
					CrtAnexosolcorrpat.TIPO_REGISTRO_RP_INSCRITO);

			if (solicitud.getPatrones() != null
					&& !solicitud.getPatrones().isEmpty()) {
				solicitud.setUnoVariosRp(CrtSolicitudcorr.SOLICITUD_VARIOS_RP);
			} else {
				solicitud.setUnoVariosRp(CrtSolicitudcorr.SOLICITUD_UN_RP);
			}

			return solicitud;
		}
		return null;
	}

	@RequestMapping(value = "/paginar", method = RequestMethod.POST)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public @ResponseBody
	DatosSalidaPaginador<CrtAnexosolcorrpat> pagina(
			@RequestBody CorrecionWrapperDataTable aoData,
			HttpServletRequest request) {
		System.out
				.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtAnexosolcorrpat> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");

		CrtSolicitudcorr sol = (CrtSolicitudcorr) request.getSession()
				.getAttribute("solicitudSeleccionada");

		if (sol != null) {

			DatosEntradaPaginador send = new DatosEntradaPaginador();

			send.parserArray(aoData.getAoData());
			send.setModelo(aoData.getoForm());

			sol = solicitudService.getSolicitudDetalles(sol,
					CrtAnexosolcorrpat.TIPO_REGISTRO_RP_INSCRITO);

			DatosSalidaPaginador<CrtAnexosolcorrpat> reply = this.solicitudService
					.pagina(getAntecedentes(sol).getPatrones());
			System.out.println(".-.-controller realizo consulta) {");
			reply.setsEcho(send.getsEcho());

			return reply;
		}
		return null;
	}

	private CrtSolicitudcorr getAntecedentes(CrtSolicitudcorr sol) {

		if (sol.getPatrones() != null && !sol.getPatrones().isEmpty()) {
			List<CrtAnexosolcorrpat> currentList = new ArrayList<CrtAnexosolcorrpat>();
			Iterator<?> iter = sol.getPatrones().iterator();
			CrtAnexosolcorrpat currentAnexo = null;

			while (iter.hasNext()) {
				currentAnexo = (CrtAnexosolcorrpat) iter.next();

				CrtInvitacion invitacion = invitacionService
						.validaInvitacionExistente(currentAnexo.getCvePatron(),
								sol.getFecFechaPeriodoIni(),
								sol.getFecFechaPeriodoFin());

				if (invitacion != null) {
					currentAnexo.setAntecedenteRP("Invitación");
				} else {
					CrtPromocion promocion = promocionService
							.validaPromocionExistente(
									currentAnexo.getCvePatron(),
									sol.getFecFechaPeriodoIni(),
									sol.getFecFechaPeriodoFin());
					if (promocion != null) {
						currentAnexo.setAntecedenteRP("Promoción");
					} else {
						currentAnexo.setAntecedenteRP("Sin Antecedente");
					}
				}

				currentList.add(currentAnexo);

			}

			sol.setPatrones(currentList);
		}
		return sol;

	}

	@RequestMapping(value = "/autorizar", method = RequestMethod.POST)
	public @ResponseBody
	CrtSolicitudcorr autorizar(@RequestBody CrtSolicitudcorr patron,
			HttpServletResponse response, HttpServletRequest request) {
		CrtSolicitudcorr sol = (CrtSolicitudcorr) request.getSession()
				.getAttribute("solicitudSeleccionada");
		sol.setCveStatus(SOLICITUD_AUTORIZADA);
		sol.setFecFechaLimite(Functions.addHabilesToDate(new Date(), 40));
		sol.setFecFechaAutorizacionCorreccion(new Date());
		if (sol != null) {
			sol = solicitudService.actualizar(sol);
			request.getSession().removeAttribute("solicitudSeleccionada");

		}
		return null;

	}

	@RequestMapping(value = "/rechazar", method = RequestMethod.POST)
	public @ResponseBody
	CrtSolicitudcorr rechazar(@RequestBody CrtSolicitudcorr patron,
			HttpServletResponse response, HttpServletRequest request) {
		CrtSolicitudcorr sol = (CrtSolicitudcorr) request.getSession()
				.getAttribute("solicitudSeleccionada");
		sol.setCveStatus(SOLICITUD_RECHAZADA);
		sol.setCveMotivoRechazo(new Integer(patron.getIdMotivoRechazo()));
		sol.setObservacionesRechazo(patron.getMotivoRechazo());
		sol.setTxRefRechazo(patron.getTxRefRechazo());
		if (sol != null) {
			sol = solicitudService.actualizar(sol);
			request.getSession().removeAttribute("solicitudSeleccionada");
		}
		return null;
	}

}
