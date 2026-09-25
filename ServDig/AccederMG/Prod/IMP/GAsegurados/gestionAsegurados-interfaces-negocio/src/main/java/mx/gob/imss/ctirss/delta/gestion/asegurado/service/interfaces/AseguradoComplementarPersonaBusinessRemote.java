/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AseguradoConNSSAsignadoException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * @author vanderluk
 * 
 */
@Remote
public interface AseguradoComplementarPersonaBusinessRemote {
	/**
	 * Metodo para complementar las calificaciones que el registro no tiene.
	 * 
	 * @param fisica
	 * @return
	 * @throws AseguradoConNSSAsignadoException
	 * @throws PersonaNoEncontradaException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws ErrorComparacionDatosSATException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws RFCNoLocalizadoEnEntidadExternaException
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws PersonaFisicaNoEncontradaException 
	 */
	Fisica complementarPersonaFisica(Fisica fisica)
			throws AseguradoConNSSAsignadoException,
			PersonaNoEncontradaException, ErrorComparacionDatosRENAPOException,
			ErrorComparacionDatosSATException,
			ClienteWebserviceSatRfcException,
			RFCNoLocalizadoEnEntidadExternaException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException, PersonaFisicaNoEncontradaException;
}
