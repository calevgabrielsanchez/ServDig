/**
 * 
 */
package mx.gob.imss.ctirss.ws.asignacion.implementacion.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.AsignacionNSSBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.ResponseMainFrameBean;

/**
 * @author fernando.castellanos
 *
 */

@Remote
public interface InicioLlamadaTransServiceRemote {

	/**
	 * Método que se utiliza para llamara a la transacción para realizar la asignación de un NSS
	 * @param forma Bean que contiene los datos necesarios para realizar una asgnación de NSS
	 * @return ResponseMainFrameBean Objeto que contiene el NSS obtenido de la transacción o el mensaje de error que corresponda
	 */
	ResponseMainFrameBean ejecutarAlta(AsignacionNSSBean forma);
}
