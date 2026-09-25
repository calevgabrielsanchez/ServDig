package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Controller de prueba para el servicio de Afectación de datos de una persona
 * 
 * @author Marco Sánchez
 * 
 */

@Controller
@RequestMapping(value = "/persona/afectar-datos")
public class AfectarDatosPersonaController extends AbstractController {

	@EJB
	private AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> afectarDatos(HttpServletResponse response,
			@RequestBody TramiteCambioInformacionPersona tramite) {

		Map<String, Object> result = new HashMap<String, Object>();

		Modulo moduloOrigen = new Modulo();
		moduloOrigen.setIdModulo(ModuloEnum.PATRONES.getCodigo().longValue());
		
		try {
			this.log.debug("Pasa por la clase AfectarDatosPersonaController");
			this.afectarDatosPersonaBusiness.afectarDatos(tramite, moduloOrigen);
		} catch (AfectacionDatosPersonaException e) {
			this.log.error(e);
			this.procesarErrorDeNegocio(e, result, response);
		} catch (PersonaNoEncontradaException e) {
			this.log.error(e);
			this.procesarErrorDeNegocio(e, result, response);
		}

		return result;
	}

	@RequestMapping(value = "/crearSolicitudICA", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> crearSolicitudDesdeICA(
			HttpServletResponse response, @RequestBody ICADatosRespuesta datosRespuesta) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			this.afectarDatosPersonaBusiness.crearSolicitudICA(datosRespuesta);
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			this.procesarErrorDeNegocio(e, result, response);
		}        

		return result;
	}
	
	@RequestMapping(value = "/crearSolicitudModificacion", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> crearSolicitudDesdeModificacion(
			HttpServletResponse response, @RequestBody MDMDatosEntrada datosEntrada) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			this.afectarDatosPersonaBusiness.crearSolicitudMDM(datosEntrada);
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			this.procesarErrorDeNegocio(e, result, response);
		}        

		return result;
	}
}
