/**
 * 
 */
package mx.gob.imss.ctirss.ws.asignacion.implementacion.interfaces;

import java.util.List;

/**
 * @author fernando.castellanos
 *
 */
public interface CuentaMainframeDAOInterfaz {

	/**
	 * Método que se utiliza para consultar en la base de datos las cuentas de usuario de mainframe para poder ejecutar una transacción
	 * @param idSistema int Es la variable que especifica el ID del sistema del que se van a obtener las cuentas.
	 * @return List Es una lista con las cuentas de usuario que se obtuvieron de la base de datos.
	 */
	List obtenerCuentaUsuario(int idSistema); 
}
