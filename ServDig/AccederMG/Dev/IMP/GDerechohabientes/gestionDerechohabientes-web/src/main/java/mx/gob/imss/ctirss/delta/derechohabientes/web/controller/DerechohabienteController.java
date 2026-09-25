package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Controller
@RequestMapping( value = "/derechohabiente/*")
public class DerechohabienteController extends AbstractController{
	private static final Logger logger = Logger.getLogger(DerechohabienteController.class);
	@Autowired
	DerechohabienteServiceRemote derechohabienteServiceRemote;
	@Autowired
	GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	SolicitudServiceRemote solicitudServiceRemote;

	private static final String[] RPS_17 = new String[] {"A7711544174","M6610218175","B3710738106"};
	
	/**
	 * Metodo para obtener el detalle del derechohabiente
	 * @param idDerechohabiente
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping( value = "/detalle", method = RequestMethod.POST)
	public String detalleDerechohabiente(@RequestParam("idDerechohabiente") Long idDerechohabiente, Model model, HttpServletRequest request, HttpSession session) {
		
		CabezaGrupoFamiliar cabezaGrupoFamiliar = null;
		//buscamos los datos del patron
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		//Verificamos que venga un id de Derechohabiente si viene 0 mandamos un error de que no hay informacion
		if(idDerechohabiente.equals(0)) {
			if(cabezaGrupoFamiliar== null)
				request.setAttribute("errores", ExceptionMessages.SIN_INFORMACION);
		} else{
			//buscamos al derechohabiente con el id correpondiente
			try {
				GrupoFamiliar derechohabiente = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(asignacionNSS.getIdAsignacionNSS(), idDerechohabiente);
				cabezaGrupoFamiliar = grupoFamiliarServiceRemote.cabezaGrupoFamiliar(asignacionNSS.getIdAsignacionNSS());

			//Si la calidad es pensionado(6), con último movimiento afiliatorio modalidad 17 y el patron 
			//tiene convenio, se toma el valor <ConDerechoSm> que regresa el WS de vigencia.
			Boolean isPensionadoMod17Convenio = false;
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null){
				if((cabezaGrupoFamiliar.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()))) {
					if (cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("17")){
						for(String rpMod17: RPS_17) {
							if((cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal()+cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()+cabezaGrupoFamiliar.getPatronSujetoObligado().getDigVerificador()).equals(rpMod17)){
								log.error("Si es patron con convenio " + rpMod17);
								isPensionadoMod17Convenio = true;
							}
						}
					} else {
						isPensionadoMod17Convenio = true;
					}
				}
			}

				//Agregamos al modelo los datos del derechohabiente y el domicilio particular
				model.addAttribute("derechohabiente", derechohabiente);
				model.addAttribute("domicilioParticular", derechohabiente.getDomicilio());
				model.addAttribute("cabezaGrupoFamiliar", cabezaGrupoFamiliar);
				model.addAttribute("isPensionadoMod17Convenio", isPensionadoMod17Convenio);
				
				if(cabezaGrupoFamiliar== null)
					request.setAttribute("errores", ExceptionMessages.SIN_INFORMACION);
			}
			catch(DerechohabientesBusinessException e) {
				logger.error("ocurrio un erro de derechohabientes", e);
				request.setAttribute("errores", e.getMessage());
				
			}catch (Exception e){
				logger.error("ocurrio un erro no cachado", e);
				request.setAttribute("errores", e.getMessage());	
			}
		}
		
		return "detalleDerechohabiente";
	}
	
	@RequestMapping( value = "/detalle/datos/patron", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<CabezaGrupoFamiliar> patronAseguradoSolicitud(HttpSession session, HttpServletRequest request){
		RespuestaJSON<CabezaGrupoFamiliar> encontrado = new RespuestaJSON<CabezaGrupoFamiliar>();
		CabezaGrupoFamiliar encontrada = new CabezaGrupoFamiliar();
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		
		try {
			encontrada= grupoFamiliarServiceRemote.cabezaGrupoFamiliar(asignacionNSS.getIdAsignacionNSS());
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			logger.error("ocurrio un erro de derechohabientes ", e);
		}catch (Exception e){
			request.setAttribute("errores", e.getMessage());
			logger.error("ocurrio un error no chachado", e);
		}
				
		encontrado.setModelo(encontrada);
		
		return encontrado;
	}
	
	@RequestMapping( value = "/buscarPersona/init/{curp}")
	public String iniciarBusquedaCurp(@PathVariable String curp, HttpSession session, Model model) {
		model.addAttribute("curp", curp);
		log.debug("La curp que se manda es: " + curp);
		return "initBusquedaCurp";
	}
	
	@RequestMapping( value = "/buscarPersona/resultados", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<List<Fisica>> busquedaPersonaPorCurp(@RequestParam String curp, HttpSession session) {
		RespuestaJSON<List<Fisica>> respuesta = new RespuestaJSON<List<Fisica>>();
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		List<Fisica> personasEncontradas = null;
		Fisica fisica = new Fisica();
		fisica.setCurp(curp);
		fisica.setSexo(null);
		fisica.setLugarNacimiento(null);
		
		if(StringUtils.isBlank(fisica.getCurp())) {
			respuesta.setEstado(false);
			respuesta.setMensaje("La curp no debe estar vacia");
		} else {
			if(fisica.getCurp().trim().length() != 18) {
				respuesta.setEstado(false);
				respuesta.setMensaje("La curp debe contener 18 caracteres");
			} else {
				try {
					personasEncontradas = grupoFamiliarServiceRemote.buscarPersonaPorCurpValidaExistenciaEnGrupo(asignacionNSS.getIdAsignacionNSS(), fisica);
				} catch (DerechohabientesBusinessException e) {
					log.error("Error al buscar a la persona", e);
					respuesta.setEstado(false);
					respuesta.setMensaje(e.getSituacion());
					return respuesta;
				}
				
				if(personasEncontradas != null) {
					respuesta.setEstado(true);
					respuesta.setModelo(personasEncontradas);
				} else {
					respuesta.setEstado(false);
					respuesta.setMensaje("No se encontraron personas relacionadas a la CURP");
				}
			}
		}
		
		return respuesta;
	}
	
	@RequestMapping(value = "/circunscripcion", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> tieneCircunscripcion(@RequestBody Derechohabiente integrante) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean tieneCircunscripcion = false;
		
		tieneCircunscripcion = derechohabienteServiceRemote.tieneCircunscripcionForeanea(integrante);
		
		result.put("circunscripcion", tieneCircunscripcion);
		return result;		
	}
}