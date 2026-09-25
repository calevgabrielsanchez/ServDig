/**
 * 
 */
package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.global.model.CalificacionTO;
import mx.gob.imss.ctirss.delta.global.model.MensajeProcesoTO;
import mx.gob.imss.ctirss.delta.global.model.MensajeTO;
import mx.gob.imss.ctirss.delta.global.model.PersonaCalificacionTO;
import mx.gob.imss.ctirss.delta.global.model.PersonaTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.PersonaGlobalUtilityLocal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author NOVUTEK101
 *
 */
@Stateless(name = "personaGlobalBusiness", mappedName = "personaGlobalBusiness")
public class PersonaGlobalBusiness implements PersonaGlobalBusinessRemote {
	protected final Log log = LogFactory.getLog(getClass());
	
	@EJB
	PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@EJB
	PersonaGlobalUtilityLocal personaUtilityService;
	@EJB
	PersonaMoralBusinessRemote personaMoralServiceBusiness;

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaGlobalBusinessRemote#evaluarPersonaConInstanciaExternas(mx.gob.imss.ctirss.delta.global.model.PersonaTO)
	 */
	@Override
	public PersonaTO evaluarPersonaConInstanciaExternas(PersonaTO persona) {
		
		MensajeProcesoTO mensajes= new MensajeProcesoTO();

		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.FISICA.longValue())){
			Fisica objFisica = new Fisica();
			objFisica.setNombre(persona.getNombre());
			objFisica.setPrimerApellido(persona.getPrimerApellido());
			objFisica.setSegundoApellido(persona.getSegundoApellido());
			objFisica.setCurp(persona.getCurp());
			objFisica.setRfc(persona.getRfc());
			objFisica.setFechaNacimiento(persona.getFechaNacimiento());
			objFisica.setLugarNacimiento(persona.getLugarNacimiento());
			objFisica.setSexo(persona.getSexo());
			objFisica.setIdPersona(persona.getIdPersona());
			
			log.debug("Aplicando modificaciones de ICA");
			ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
			icaDatosConsulta.setPersonaFisica(objFisica);
			icaDatosConsulta.setIndicadorConsultaRENAPO(Boolean.TRUE);
			icaDatosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
			icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.FALSE);
			try {
				ICADatosRespuesta icaDatosRespuesta = personaFisicaServiceBusiness.identificarCambios(icaDatosConsulta);
				
				mensajes = procesarMensajesDeEvaluacion(icaDatosRespuesta.getTraza());
				
				ICADatosRespuesta icaDatosRespuestaFinal = personaFisicaServiceBusiness.integrarCambios(icaDatosRespuesta);
				persona = personaUtilityService.convertirPersonaFiscaAPersonaGlobal(icaDatosRespuestaFinal.getPersonaFisicaIMSS());
				persona.setCalificaciones(obtenerCalificaciones(icaDatosRespuestaFinal.getPersonaFisicaIMSS().getPersonaCalificaciones()));

				persona.setMensajes(mensajes);
			}catch(ComparacionSinDiferenciasException e){
				MensajeTO nuevoMensaje = new MensajeTO();
				nuevoMensaje.setCodigo("0");
				nuevoMensaje.setDescripcion(e.getMessage());
				MensajeTO []mensajeError = {nuevoMensaje};
				mensajes.setMensaje(mensajeError);
				persona.setMensajes(mensajes);
			}catch(Exception e){
				e.printStackTrace();
				MensajeTO nuevoMensaje = new MensajeTO();
				nuevoMensaje.setCodigo("-1");
				nuevoMensaje.setDescripcion(e.getMessage());
				MensajeTO []mensajeError = {nuevoMensaje};
				mensajes.setMensaje(mensajeError);
				persona.setMensajes(mensajes);
			}
		}else if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.MORAL.longValue())){
			Moral moral = new Moral();
			moral.setIdPersona(persona.getIdPersona());
			moral.setRfc(persona.getRfc());
			
			log.debug("Aplicando modificaciones de ICA");
			ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
			icaDatosConsulta.setPersonaMoral(moral);
			icaDatosConsulta.setIndicadorConsultaRENAPO(Boolean.FALSE);
			icaDatosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
			icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.FALSE);
			
			try {
				ICADatosRespuesta icaDatosRespuesta = personaMoralServiceBusiness.identificarCambios(icaDatosConsulta);
				mensajes = procesarMensajesDeEvaluacion(icaDatosRespuesta.getTraza());
				
				ICADatosRespuesta icaDatosRespuestaFinal = personaFisicaServiceBusiness.integrarCambios(icaDatosRespuesta);
				persona = personaUtilityService.convertirPersonaMoralAPersonaGlobal(icaDatosRespuestaFinal.getPersonaMoralIMSS());
				persona.setCalificaciones(obtenerCalificaciones(icaDatosRespuestaFinal.getPersonaMoralIMSS().getPersonaCalificaciones()));
				persona.setMensajes(mensajes);
			}catch(ComparacionSinDiferenciasException e){
				MensajeTO nuevoMensaje = new MensajeTO();
				nuevoMensaje.setCodigo("0");
				nuevoMensaje.setDescripcion(e.getMessage());
				MensajeTO []mensajeError = {nuevoMensaje};
				mensajes.setMensaje(mensajeError);
				persona.setMensajes(mensajes);
			}catch (Exception e) {
				e.printStackTrace();
				MensajeTO nuevoMensaje = new MensajeTO();
				nuevoMensaje.setCodigo("-1");
				nuevoMensaje.setDescripcion(e.getMessage());
				MensajeTO []mensajeError = {nuevoMensaje};
				mensajes.setMensaje(mensajeError);
				persona.setMensajes(mensajes);
			}
		}
		return persona;
	}
	
	private MensajeProcesoTO procesarMensajesDeEvaluacion(Map<String,String> traza){
		MensajeProcesoTO listaMensajes = new MensajeProcesoTO();
		List<MensajeTO> mensajes = new ArrayList<MensajeTO>();
		for(String key :traza.keySet()){
			MensajeTO nuevoMensaje = new MensajeTO();
			nuevoMensaje.setCodigo(key);
			nuevoMensaje.setDescripcion(traza.get(key));
			mensajes.add(nuevoMensaje);
		}
		listaMensajes.setMensaje(mensajes.toArray(new MensajeTO[mensajes.size()]));
		
		return listaMensajes;
	}
	
	private PersonaCalificacionTO obtenerCalificaciones(List<PersonaCalificacion> calificaciones){
		PersonaCalificacionTO calificacionArray = new PersonaCalificacionTO();
		List<CalificacionTO> calificacionesFinales = new ArrayList<CalificacionTO>();
		for(PersonaCalificacion calificacion:calificaciones){
			CalificacionTO cTO = new CalificacionTO();
			cTO.setIdCalificacion(calificacion.getCalificacion().getIdCalificacion());
			cTO.setDescripcion(calificacion.getCalificacion().getDescripcion());
			cTO.setFechaCalificacion(calificacion.getFechaCalificacion());
			calificacionesFinales.add(cTO);
		}
		calificacionArray.setCalificacion(calificacionesFinales.toArray(new CalificacionTO[calificacionesFinales.size()]));
		return calificacionArray;
	}
}
