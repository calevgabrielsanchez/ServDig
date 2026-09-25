/**
 * 
 */
package mx.gob.imss.ctirss.ws.asignacion.implementacion.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.CuentaUsuMainframeBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.interfaces.CuentaMainframeDAOInterfaz;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author fernando.castellanos
 *
 */
public class CuentaMainframeDAOImpl extends JdbcDaoSupport implements CuentaMainframeDAOInterfaz {
	
	/**
	 * Logger que se utiliza para mandar mensajes a la consola o un archivo
	 */
	private static Logger log = Logger.getLogger(CuentaMainframeDAOImpl.class);

	/**
	 * Método que se utiliza para consultar en la base de datos las cuentas de usuario de mainframe para poder ejecutar una transacción
	 * @param idSistema int Es la variable que especifica el ID del sistema del que se van a obtener las cuentas.
	 * @return List Es una lista con las cuentas de usuario que se obtuvieron de la base de datos.
	 */
	@Override
	public List obtenerCuentaUsuario(int idSistema) {
		StringBuffer query = new StringBuffer();
		List listaResQuery = null;
		Iterator iteraCuentas = null;
    	CuentaUsuMainframeBean objCuenta = null;
    	List<CuentaUsuMainframeBean> listaCuentasMainframe = new ArrayList<CuentaUsuMainframeBean>();
		
		query.append(" SELECT NOM_NOMBRE_CUENTA, REF_PALABRA_CLAVE FROM DIT_CUENTA_ACCESO ");
		query.append(" WHERE CVE_ID_MODULO = ? ");
		
		Object [] parametros= {idSistema};
		log.debug("Ejecuto el query " + query.toString());
		try {
			listaResQuery = this.getJdbcTemplate().queryForList(query.toString(),parametros);			
	    	
	    	if (listaResQuery != null) {
	    		iteraCuentas=listaResQuery.iterator();
	    		while (iteraCuentas.hasNext()){
	    			objCuenta = new CuentaUsuMainframeBean();
	    			Map cuenta = (Map) iteraCuentas.next();
	    			objCuenta.setCuentaUsuario((String) cuenta.get("NOM_NOMBRE_CUENTA"));
	    			objCuenta.setContraseniaUsuMainframe((String) cuenta.get("REF_PALABRA_CLAVE"));
	    			listaCuentasMainframe.add(objCuenta);
	    		}
	    	}
			
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
		}
		return listaCuentasMainframe;
	}

}
