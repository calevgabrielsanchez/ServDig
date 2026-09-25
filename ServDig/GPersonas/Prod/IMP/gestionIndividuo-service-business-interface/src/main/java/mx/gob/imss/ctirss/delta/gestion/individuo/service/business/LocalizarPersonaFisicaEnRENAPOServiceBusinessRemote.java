package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * 
 * @author 191807 071212
 * Esta interfaz corresponde al servicio definido en el CU DTS-08 Consultar RENAPO
 *
 */
@Remote
public interface LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote {

	/**
	 * Este metodo localiza a una persona fisica en RENAPO con la CURP
	 * @param curp
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 */
	Fisica localizarPersonaFisicaEnRENAPOxCURP(String curp) throws CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException, ErrorValidacionDatosConsultaEnEntidaExternaException; 

	/**
	 * Este metodo localiza a una persona fisica en RENAPO con los DATOS BASICOS
	 * @param db
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 */
	Fisica localizarPersonaFisicaEnRENAPOxDatosBasicos(Fisica db) throws CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException, ErrorValidacionDatosConsultaEnEntidaExternaException; 

}
