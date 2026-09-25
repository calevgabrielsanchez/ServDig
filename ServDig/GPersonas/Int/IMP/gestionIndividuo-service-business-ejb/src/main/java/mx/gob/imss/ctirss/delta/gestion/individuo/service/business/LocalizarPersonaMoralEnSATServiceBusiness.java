package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.PersonaMoralServiceValidateLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessLocal;

/**
 * 
 * @author 191807 071212
 *
 */
@Stateless(name = "localizarPersonaMoralEnSATServiceBusiness", mappedName = "localizarPersonaMoralEnSATServiceBusiness")
public class LocalizarPersonaMoralEnSATServiceBusiness implements LocalizarPersonaMoralEnSATServiceBusinessRemote {

	@EJB
	PersonaBusinessLocal personaBusiness;
	
	@EJB
	PersonaMoralServiceValidateLocal personaMoralServiceValidate;
	
	
	@Override
	public Moral localizarPersonaMoralEnSATxRFC(String rfc) throws RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException {

		Moral personaMoral = null;
		String validacionRFC = personaMoralServiceValidate.validarRFC(rfc);
		
		// Antes que todo se valida el o los datos de entrada al servicio de consulta
		if(validacionRFC.equals("")){
			// Mandamos llamar al WS de SAT mediante el RFC
			personaMoral = personaBusiness.buscarPersonaMoralPorRfcEnSat(rfc);
		}else{
			throw new ErrorValidacionDatosConsultaEnEntidaExternaException(validacionRFC);
		}
		
		// Si no se encontro a la persona, entonces se lanza una excepcion que se propagara
		if(personaMoral== null){
			throw new RFCNoLocalizadoEnEntidadExternaException();
		}
		
		// Si si se encontro, entonces simplemente se regresa la informacion obtenida
		return personaMoral;
		
	}
	
	@Override
	public Moral localizarPMEnSATxRFCSitCont(String rfc) throws RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException {

		Moral personaMoral = null;
		String validacionRFC = personaMoralServiceValidate.validarRFC(rfc);
		
		// Antes que todo se valida el o los datos de entrada al servicio de consulta
		if(validacionRFC.equals("")){
			// Mandamos llamar al WS de SAT mediante el RFC
			personaMoral = personaBusiness.buscarPMPorRfcEnSatSitCont(rfc);
		}else{
			throw new ErrorValidacionDatosConsultaEnEntidaExternaException(validacionRFC);
		}
		
		// Si no se encontro a la persona, entonces se lanza una excepcion que se propagara
		if(personaMoral== null){
			throw new RFCNoLocalizadoEnEntidadExternaException();
		}
		
		// Si si se encontro, entonces simplemente se regresa la informacion obtenida
		return personaMoral;
		
	}	

}
