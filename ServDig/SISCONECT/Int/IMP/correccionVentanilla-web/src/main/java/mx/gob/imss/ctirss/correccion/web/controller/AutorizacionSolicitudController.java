/**
 * RBGSoftware setting java code convention measurements 
 * 2013.07.23
 */


package mx.gob.imss.ctirss.correccion.web.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.SolicitudCorrecionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.service.interfaces.IObraService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.web.utils.SessionHbtFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/solicitud/autorizacion")
public class AutorizacionSolicitudController extends AbstractController {

	@Autowired
	private SolicitudService<CrtSolicitudcorr> solicitudService;

	@Autowired
	private IObraService<AbstractModel> obraService;

	@RequestMapping(method = RequestMethod.GET)
	public String getCreateForm(Model model, HttpServletRequest request) {
		model.addAttribute(new CrtSolicitudcorr());

		request.getSession().removeAttribute("solicitudesEncontradas");
		request.getSession().removeAttribute("solicitudSeleccionada");

		return "autorizacionSolicitud/main";
	}

	@RequestMapping(value = "/paginar", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<CrtSolicitudcorr> pagina(
			@RequestBody SolicitudCorrecionWrapperDataTable aoData,
			HttpServletRequest request) {

		UserSession user = getUsuarioFirmado(request);

		DatosEntradaPaginador<AbstractModel> send = new DatosEntradaPaginador<AbstractModel>();

//		send.parserArray(aoData.getAoData());
//		send.setModelo(aoData.getoForm());

		List<?> solicitudes = solicitudService.consultaSolicitudesPendientes(
				new Long(user.getCveCodigoDelegacion()),
				new Long(user.getCveCodigoSubDelegacion()),
				user.getIdSubDelegacion());
		request.getSession()
				.setAttribute("solicitudesEncontradas", solicitudes);
		DatosSalidaPaginador<CrtSolicitudcorr> reply = this.solicitudService
				.paginaSolicitudes(solicitudes);
		System.out.println(".-.-controller realizo consulta) {");
		reply.setsEcho(send.getsEcho());

		return reply;
	}

	@RequestMapping(value = "/muestraDetalle", method = RequestMethod.POST)
	public String muestraDetalle(CrtSolicitudcorr solicitud,
			HttpServletResponse response, HttpServletRequest request) {
		return "autorizacionSolicitud/detalle";
	}

	@RequestMapping(value = "/setSolicitud", method = RequestMethod.POST)
	public @ResponseBody
	CrtSolicitudcorr setSolicitud(@RequestBody CrtSolicitudcorr patron,
			HttpServletResponse response, HttpServletRequest request) {
		List<?> solicitudes = (List<?>) request.getSession().getAttribute(
				"solicitudesEncontradas");
		
		if (solicitudes != null && solicitudes.size() > 0) {
			Iterator<?> i = solicitudes.iterator();
			while (i.hasNext()) {
				CrtSolicitudcorr sol = (CrtSolicitudcorr) i.next();
				if (sol.getNuFolio().equals(patron.getNuFolio())) {
					sol = solicitudService.getSolicitudDetalles(sol,
							CrtAnexosolcorrpat.TIPO_REGISTRO_RP_CENTRO_TRABAJO);
					sol = solicitudService.getSolicitudDetalles(sol,
							CrtAnexosolcorrpat.TIPO_REGISTRO_RP_FISCAL);
					
					if (sol.getIdTipoSolicitud().intValue() == CrtSolicitudcorr.SOLICITUD_TIPO_CONSTRUCCION) {

						sol = solicitudService.getSolicitudDetalles(sol,
								CrtAnexosolcorrpat.TIPO_REGISTRO_RP_OBRA);

						if (sol.getPatronObra() == null) {
							SatObra obra = this.obraService.validaObra(String
									.valueOf(sol.getCveNumeroRegObra()));
							sol.setSaticObra(obra);
						}
					}
					
					try {
						String sql = "select CVE_NROREGOBRA from CRT_SOLICITUDCORR where NU_FOLIO = '" + sol.getNuFolio() + "'";
						String numFolio = SessionHbtFactory.getInstance().findOne(sql);					
						sol.setNumeroObra(numFolio);
						sql = "select TO_CHAR(FEC_FECHANOTIFI, 'DD-MM-YYYY') AS FECHA_NOTIFICACION from CRT_INVITACION where nu_folioinvitacion = '" + sol.getNuFolio() + "'";
						String fecNoti = SessionHbtFactory.getInstance().findOne(sql);						
						
						sol.setFechaRecepcionOficio(fecNoti);
					} catch (Exception e) {
						e.printStackTrace(); 
					}
					request.getSession().setAttribute("solicitudSeleccionada",
							sol);
					return sol;
				}
			}
		}
		return null;
	}
}
