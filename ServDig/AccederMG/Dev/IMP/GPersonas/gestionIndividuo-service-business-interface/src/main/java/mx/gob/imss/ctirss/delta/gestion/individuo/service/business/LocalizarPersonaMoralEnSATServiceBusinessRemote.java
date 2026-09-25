package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

/**
 * 
 * @author 191807 071212
 * Esta interfaz corresponde al servicio definido en el CU DTS-09 Consultar SAT
 *
 */
@Remote
public interface LocalizarPersonaMoralEnSATServiceBusinessRemote {

	/**
	 * Este metodo localiza a una persona moral en SAT con la RFC
	 * @param rfc
	 * @return
	 * @throws RFCNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 */
	Moral localizarPersonaMoralEnSATxRFC(String rfc) throws RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException, ErrorValidacionDatosConsultaEnEntidaExternaException; 

	Moral localizarPMEnSATxRFCSitCont(String rfc)
			throws RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException,
			ErrorValidacionDatosConsultaEnEntidaExternaException;

}
