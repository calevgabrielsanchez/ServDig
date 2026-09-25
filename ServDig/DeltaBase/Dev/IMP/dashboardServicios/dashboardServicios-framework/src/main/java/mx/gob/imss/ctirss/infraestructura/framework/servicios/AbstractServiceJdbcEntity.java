/**
 * AbstractServiceEntity.java
 * gestionPatronal-framework-base
 * mx.gob.imss.ctirss.delta.gestion.patron.framework.base.service
 */
package mx.gob.imss.ctirss.infraestructura.framework.servicios;

import com.infraestructura.jdbc.ConexionJdbc;

/**
 * @author Lucio Duran Silva
 * 02/01/2012
 */
public abstract class AbstractServiceJdbcEntity extends AbstractService  {

	public ConexionJdbc datos = null;
	
	public void abreConexion(String sDataSouce){
		datos = new ConexionJdbc();
		datos.abreConexion(sDataSouce);	
	}

	public void cierraConexion(){
		// VERIFICA SI SE INSTANCIO LA CLASE DAO
		if (datos != null) {
			// VERIFICA SI TIENE ABIERTA LA CONEXION A BD
			if (datos.isConectado()) {
				// CIERRA LA CONEXION
				datos.cierraConexion();
			}
		}
		datos = null;
	}
	
}
