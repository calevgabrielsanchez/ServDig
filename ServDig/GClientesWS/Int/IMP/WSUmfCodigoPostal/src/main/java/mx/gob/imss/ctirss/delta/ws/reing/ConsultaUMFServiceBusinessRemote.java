/**
 * 
 */
package mx.gob.imss.ctirss.delta.ws.reing;

import javax.ejb.Remote;

/**
 * @author vanderluk
 *
 */
 @Remote
public interface ConsultaUMFServiceBusinessRemote {



	 /**
	 * Consulta de UMF y Delegacion / Subdelegacion a partir de un Codigo Postal
	 *
	 */
	 String consultaUMFPorCodigoPostal(String codigoPostal);

}
