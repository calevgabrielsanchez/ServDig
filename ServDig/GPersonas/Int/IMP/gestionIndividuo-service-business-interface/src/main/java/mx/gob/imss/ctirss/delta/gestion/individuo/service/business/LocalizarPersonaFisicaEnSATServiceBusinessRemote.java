package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * 
 * @author 191807 071212
 * Esta interfaz corresponde al servicio definido en el CU DTS-09 Consultar SAT
 *
 */
@Remote
public interface LocalizarPersonaFisicaEnSATServiceBusinessRemote {

	/**
	 * Este metodo localiza a una persona fisica en SAT con la RFC
	 * @param rfc
	 * @return
	 * @throws RFCNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 */
	Fisica localizarPersonaFisicaEnSATxRFC(String rfc) throws RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException; 

	/**
	 * Este metodo localiza a una persona fisica en SAT con el RFC
	 * y regresa los datos necesario para las validaciones de situacion del contribuyente
	 * @param rfc
	 * @return
	 * @throws RFCNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 */
	Fisica localizarPFEnSATxRFCSitCont(String rfc) throws RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException; 

}
