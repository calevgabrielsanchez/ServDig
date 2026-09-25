package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.PersonaFisicaServiceValidateLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessLocal;

/**
 * 
 * @author 191807 071212
 *
 */
@Stateless(name = "localizarPersonaFisicaEnRENAPOServiceBusiness", mappedName = "localizarPersonaFisicaEnRENAPOServiceBusiness")
public class LocalizarPersonaFisicaEnRENAPOServiceBusiness implements LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote {

	@EJB
	PersonaBusinessLocal personaBusiness;
	
	@EJB
	PersonaFisicaServiceValidateLocal personaFisicaServiceValidate;
	
	
	@Override
	public Fisica localizarPersonaFisicaEnRENAPOxCURP(String curp) throws CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException, ErrorValidacionDatosConsultaEnEntidaExternaException {

		Fisica personaFisica = null;
		String validacionCURP = personaFisicaServiceValidate.validarCURP(curp);
		
		// Antes que todo se valida el o los datos de entrada al servicio de consulta
		if(validacionCURP.equals("")){
			// Mandamos llamar al WS de RENAPO mediante la CURP
			personaFisica = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curp);
		}else{
			throw new ErrorValidacionDatosConsultaEnEntidaExternaException(validacionCURP);
		}
		
		// Si no se encontro a la persona, entonces se lanza una excepcion que se propagara
		if(personaFisica == null){
			throw new CURPNoLocalizadoEnEntidadExternaException();
		}
		
		// Si si se encontro, entonces simplemente se regresa la informacion obtenida
		return personaFisica;
	}

	@Override
	public Fisica localizarPersonaFisicaEnRENAPOxDatosBasicos(Fisica db) throws CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException, ErrorValidacionDatosConsultaEnEntidaExternaException {

		Fisica personaFisica = null;
		List<String> validacionDB = personaFisicaServiceValidate.validarDatosBasicos(db);
		
		// Antes que todo se valida el o los datos de entrada al servicio de consulta		
		if(validacionDB == null || validacionDB.isEmpty()){
			// Mandamos llamar al WS de RENAPO mediante los datos basicos
			personaFisica = personaBusiness.buscarPersonaFisicaPorDatosBasicosEnRenapo(db.getNombre(), db.getPrimerApellido(), db.getSegundoApellido(), db.getSexo().getIdSexo(), db.getFechaNacimiento(), db.getLugarNacimiento().getIdRenapo().intValue());
		}else{
			throw new ErrorValidacionDatosConsultaEnEntidaExternaException(validacionDB.toString());
		}
		
		// Si no se encontro a la persona, entonces se lanza una excepcion que se propagara
		if(personaFisica == null){
			throw new CURPNoLocalizadoEnEntidadExternaException();
		}
		
		// Si si se encontro, entonces simplemente se regresa la informacion obtenida
		return personaFisica;
		
	}

}
