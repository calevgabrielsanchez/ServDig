package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessLocal;

import org.apache.commons.lang.StringUtils;

@Stateless(name = "actualizarRfcBusiness", mappedName = "actualizarRfcBusiness")
public class ActualizarRfcBusiness extends AbstractServiceBusiness 
	implements ActualizarRfcBusinessRemote {

	@EJB
	private PersonaBusinessLocal personaBusiness;
	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@EJB
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
	
	@Override
	public  Map<String, Object> solicitarActualizarRfc(Fisica fisica){
		Map<String, Object> response = new HashMap<String, Object>();
		Boolean solicitarRfc = new Boolean(false);
		String rfcExistenteImss = "";		
		Fisica fisicaImss = personaBusiness.getPersonaFisica(fisica.getIdPersona());		
		//Si no tiene RFC, solicitar su captura
		if(fisicaImss != null && esCadenaVacia(fisicaImss.getRfc())){
			solicitarRfc=Boolean.TRUE;
		}
		
		//Tiene RFC pero hay que verificar si esta calificado por SAT.
		if(solicitarRfc.booleanValue()==false){
			rfcExistenteImss=fisicaImss.getRfc();
			solicitarRfc=Boolean.TRUE;
			
			boolean tieneCalificacionSat = calificacionesPersonaBusinessService
				.tieneCalificacionEspecifica(fisicaImss, CalificacionEnum.VALIDADO_SAT);
			
			if(tieneCalificacionSat){
				solicitarRfc=Boolean.FALSE;
			}
		}
		
		response.put("rfcExistenteImss", rfcExistenteImss);
		response.put("solicitarRfc", solicitarRfc);
		return response;
	}
	
	
	@Override
	public void actualizarRfc(Fisica fisica) throws ClienteWebserviceSatRfcException, 
			ErrorComparacionDatosSATException, PersonasNoLocalizadasException{
		
		//Obtener persona por ID
		Fisica fisicaImss = personaBusiness.getPersonaFisica(fisica.getIdPersona());	
		//Obtener persona por RFC en SAT			
		Fisica fisicaSat = personaBusiness.buscarPersonaFisicaPorRfcEnSat(fisica.getRfc());
		
		if (fisicaSat != null) {						
			boolean registrosIguales = personaFisicaServiceBusiness
				.comparaDatosBasicosSAT(fisicaSat, fisicaImss);
			if (registrosIguales) {							
				fisicaImss.setRfc(fisica.getRfc());							
			} else {
				throw new ErrorComparacionDatosSATException("Los datos encontrados en el Instituto no " +
					"coinciden con los registrados en el SAT. Acuda a Ventanilla para actualizar su información.");
			}
		} else {
			throw new PersonasNoLocalizadasException("El RFC capturado no fue localizado en el SAT.");
		}
		
		//Actualizar RFC
		personaFisicaServiceBusiness.complementarDatosPersonas(fisica.getRfc(), fisicaImss.getIdPersona());		
		
		//Calificar SAT
		try {
			calificacionesPersonaBusinessService.calificarSAT(fisicaImss);
		} catch (AbstractException e) {
			log.error(e);
		}
		
	}
	
	private boolean esCadenaVacia(String cadena){
		if(StringUtils.isEmpty(cadena) && StringUtils.isBlank(cadena)){
			return true;
		}else{
			return false;
		}
	}
	
}
