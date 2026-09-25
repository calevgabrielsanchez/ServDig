package mx.gob.imss.ctirss.delta.gestion.solicitud.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudEnProcesoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/solicitud")
public class SolicitudController extends AbstractController {
	@Autowired
	SolicitudBusinessRemote solicitudBusinessRemote;
	
	
	@Autowired
	MovimientoPatronalBusinessRemote movimientoPatronalBusinessRemote;

	/**
	 * Metodo para obtener una sola serie a partir de un idSerie contenido en
	 * otro objeto Serie
	 * 
	 * @param codigo
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/consultar/estatus", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> enProceso(@RequestParam String folio,
			HttpServletResponse response) {

		Map result = new HashMap<String, Object>();

		try {
			Solicitud solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folio);

			solicitudBusinessRemote.isEnProceso(solicitud);

		} catch (SolicitudEnProcesoException e) {
			this.log.debug("en proceso ..." + e);
			result.put("idEstadoSolicitud", "SI EN_PROCESO");
			this.procesarErrorDeNegocio(e, result, response);
			return result;
		} catch (NullPointerException e) {
			this.log.error(e);
			e.printStackTrace();
		}

		result.put("idEstadoSolicitud", "NO EN_PROCESO");
		return result;
	}

	/**
	 * Metodo para mostrar la pantalla de espera de la solicitud.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/espera", method = RequestMethod.GET)
	public String login(Model model) {
		return "solicitud.enproceso";
	}
	
	
	
	@RequestMapping(value = "/movimiento/encolar", method = RequestMethod.GET)
	public String encolar(Model model) {
		
		
		MovimientoPatronalType type = new MovimientoPatronalType();
		type.setCausa(22);
		type.setCiz(1);
		type.setClase(1);
		type.setClaveAplicacion(6);
		type.setDelegacionOrigen(9);
		type.setDigitoVerificador(1);
		type.setFechaMovimiento(new Date());
		type.setFraccion(123);
		type.setGiro("GIRO");
		type.setNumeroFolio("123");
		type.setOrigenMovimiento(1);
		type.setPrima(1.56);
		type.setRegistroPatronal("B32123");
		type.setSubdelegacionOrigen(4);
		type.setTipoMovimiento(6);
		
		
		movimientoPatronalBusinessRemote.enviarModificacionPatronal(type);
		
		
		return "solicitud.enproceso";
	}

	@RequestMapping(value = "/consultar/fisica/{idPersona}", method = RequestMethod.GET)
	public String obtenerSolicitudesPersonaFisica(@PathVariable Long idPersona,
			HttpServletRequest request) {
		List<Solicitud> listSolicitudes = solicitudBusinessRemote
				.obtenerSolicitudPorPersonaFisica(idPersona);
		request.setAttribute("listSolicitudes", listSolicitudes);

		return "solicitudesPersonaFisica";
	}

	@RequestMapping(value = "/consultar/moral/{idPersona}", method = RequestMethod.GET)
	public String obtenerSolicitudesPersonaMoral(@PathVariable Long idPersona,
			HttpServletRequest request) {
		List<Solicitud> listSolicitudes = solicitudBusinessRemote
				.obtenerSolicitudPorPersonaMoral(idPersona);
		request.setAttribute("listSolicitudes", listSolicitudes);

		return "solicitudesPersonaMoral";
	}
}
