/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * @author Lucio Duran Silva
 *
 */
@Remote
public interface ComplementarCalificacionPersonaFisicaServiceBusinessRemote {
	
	
	/**
	 * 
	 * @param candidato
	 * @param entrada
	 * @return
	 * @throws ErrorComparacionDatosRENAPOException 
	 * @throws ClienteWebserviceSatRfcException 
	 * @throws ErrorComparacionDatosSATException 
	 * @throws RFCNoLocalizadoEnEntidadExternaException 
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 */
	Fisica complementarCalificaciones(Fisica candidato, Fisica entrada)
			throws ErrorComparacionDatosRENAPOException,
			ErrorComparacionDatosSATException, ClienteWebserviceSatRfcException, 
			RFCNoLocalizadoEnEntidadExternaException, CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException;
	
	

}
