/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

/**
 * 130912
 * @author Samuel Rodríguez Grajeda
 *
 */
@Remote
public interface ComplementarCalificacionPersonaMoralServiceBusinessRemote {
	
	
	/**
	 * 
	 * @param candidato
	 * @param entrada
	 * @return
	 * @throws ClienteWebserviceSatRfcException 
	 * @throws ErrorComparacionDatosSATException 
	 */
	Moral complementarCalificaciones(Moral candidato, Moral entrada)throws ErrorComparacionDatosSATException, ClienteWebserviceSatRfcException;
	

}
