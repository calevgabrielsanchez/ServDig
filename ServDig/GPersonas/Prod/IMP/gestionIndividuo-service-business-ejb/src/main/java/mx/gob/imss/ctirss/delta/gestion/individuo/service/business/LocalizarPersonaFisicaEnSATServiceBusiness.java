package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.PersonaFisicaServiceValidateLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessLocal;

/**
 * 
 * @author 191807 071212
 *
 */
@Stateless(name = "localizarPersonaFisicaEnSATServiceBusiness", mappedName = "localizarPersonaFisicaEnSATServiceBusiness")
public class LocalizarPersonaFisicaEnSATServiceBusiness implements LocalizarPersonaFisicaEnSATServiceBusinessRemote {

	@EJB
	PersonaBusinessLocal personaBusiness; 
	
	@EJB
	PersonaFisicaServiceValidateLocal personaFisicaServiceValidate;
	
	
	@Override
	public Fisica localizarPersonaFisicaEnSATxRFC(String rfc) throws RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException {
		System.out.println("RFC a localizar:" + rfc + ".");
		Fisica personaFisica = null;
		String validacionRFC = personaFisicaServiceValidate.validarRFC(rfc);
		
		// Antes que todo se valida el o los datos de entrada al servicio de consulta
		if(validacionRFC.equals("")){
			// Mandamos llamar al WS de SAT mediante el RFC
			personaFisica = personaBusiness.buscarPersonaFisicaPorRfcEnSat(rfc);
		}else{
			throw new ErrorValidacionDatosConsultaEnEntidaExternaException(validacionRFC);
		}
		
		// Si no se encontro a la persona, entonces se lanza una excepcion que se propagara
		if(personaFisica == null){
			throw new RFCNoLocalizadoEnEntidadExternaException();
		}
		
		// Si si se encontro, entonces simplemente se regresa la informacion obtenida
		return personaFisica;
		
	}

	@Override
	public Fisica localizarPFEnSATxRFCSitCont(String rfc) throws RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException {
		System.out.println("localizarPFEnSATxRFCSitCont, RFC a localizar:" + rfc + ".");
		Fisica personaFisica = null;
		String validacionRFC = personaFisicaServiceValidate.validarRFC(rfc);
		
		// Antes que todo se valida el o los datos de entrada al servicio de consulta
		if(validacionRFC.equals("")){
			// Mandamos llamar al WS de SAT mediante el RFC
			personaFisica = personaBusiness.buscarPFPorRfcEnSatSitCont(rfc);
		}else{
			throw new ErrorValidacionDatosConsultaEnEntidaExternaException(validacionRFC);
		}
		
		// Si no se encontro a la persona, entonces se lanza una excepcion que se propagara
		if(personaFisica == null){
			throw new RFCNoLocalizadoEnEntidadExternaException();
		}
		
		// Si si se encontro, entonces simplemente se regresa la informacion obtenida
		return personaFisica;
		
	}
	
	
}
