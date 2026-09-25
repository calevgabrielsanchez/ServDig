/**
 * 
 */
package mx.gob.imss.ctirss.delta.ws.reing;

import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 * @author vanderluk
 *
 */
 @WebService
 @Stateless
public class ConsultaUMFServiceBusiness implements
		ConsultaUMFServiceBusinessRemote {

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.ws.reing.ConsultaUMFServiceBusinessRemote#consultaUMFPorCodigoPostal(java.lang.String)
	 */
	@Override
	public String consultaUMFPorCodigoPostal(String codigoPostal) {
		// TODO Auto-generated method stub
		return "Esto es un ejemplo..";
	}

}
