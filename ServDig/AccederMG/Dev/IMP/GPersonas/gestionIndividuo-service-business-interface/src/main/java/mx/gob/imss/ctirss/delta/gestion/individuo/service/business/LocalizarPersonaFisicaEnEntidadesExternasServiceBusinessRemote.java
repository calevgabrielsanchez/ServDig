package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * 120912 Esta interface corresponde al diagrama N2
 * 
 * @author Samuel Rodríguez Grajeda
 * 
 */
@Remote
public interface LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote {

	/**
	 * Este metodo se encargara de localizar a una persona fisica, tanto en el
	 * RENAPO como en el SAT y regresara otro objeto persona siempre y cuando
	 * haya sido localizada en ambas entidades externas
	 * 
	 * @param personaFisicaSugerida
	 * @return
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws ClienteWebserviceRenapoCurpException
	 */
	Fisica localizarPersonaFisicaEnEntidadesExternas(
			Fisica personaFisicaSugerida)
			throws ErrorComparacionDatosRENAPOException,
			ErrorComparacionDatosSATException,
			CURPNoLocalizadoEnEntidadExternaException,
			RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException, ClienteWebserviceSatRfcException;

	Fisica localizarCompararPersonaFisicaEnEntidadesExternasxCURPyRFC(
			Fisica personaFisicaSugerida)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException, ErrorComparacionDatosSATException, DiferenciasRENAPOContraSAT;

	/**
	 * 
	 * @param personaSugerida
	 * @return
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 */
	Fisica localizarPersonaFisicaEnRENAPO(Fisica personaSugerida)
			throws ErrorComparacionDatosRENAPOException,
			CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException;

	Fisica localizarCompararPersonaFisicaEnRENAPOxCURP(
			Fisica personaFisicaSugerida)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException;

	/**
	 * 
	 * @param personaSugerida
	 * @return
	 * @throws ErrorComparacionDatosSATException
	 * @throws RFCNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceSatRfcException 
	 */
	Fisica localizarPersonaFisicaEnSAT(Fisica personaSugerida)
			throws ErrorComparacionDatosSATException,
			RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceSatRfcException;

	Fisica localizarCompararPersonaFisicaEnSATxRFC(Fisica personaFisicaSugerida)
			throws RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosSATException;

	/**
	 * 
	 * @param personaSugerida
	 * @return
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 */
	Boolean localizarPersonaFisicaEnRENAPOByCURP(Fisica personaSugerida)  
			throws ErrorComparacionDatosRENAPOException, 
			CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException;
}
