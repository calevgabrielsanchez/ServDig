package mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.entity;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import mx.gob.imss.ctirss.infraestructura.framework.servicios.AbstractServiceJdbcEntity;
import mx.gob.imss.ctirss.infraestructura.framework.servicios.ConexionBD;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Sistema;

/**
 * @author Joaquin Esteban Ponte Díaz
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 20/01/2012
 */
@Stateless
public class SistemaEntity extends AbstractServiceJdbcEntity implements SistemaEntityLocal{
	
	@ConexionBD("jdbc/modificaciones")
	public List<Sistema> getSistemas(Sistema filtroBusqueda){
		LinkedList<Sistema> listaSistemas = new LinkedList<Sistema>();
		datos.sSql = "\n SELECT * FROM INF_SISTEMA SIS \n";
		
		if (filtroBusqueda != null){
			if (filtroBusqueda.getCveSistema() != null&&!filtroBusqueda.getCveSistema().equals("")){
				datos.sSql = datos.sSql +  "  WHERE CVE_SISTEMA ='" + filtroBusqueda.getCveSistema() + "' \n";
			}
			else if (filtroBusqueda.getDesSistema()!=null&&!filtroBusqueda.getDesSistema().equals("")){
				datos.sSql = datos.sSql +  " WHERE upper(SIS.DES_SISTEMA) LIKE '%" + filtroBusqueda.getDesSistema().toUpperCase() + "%' \n";
			}
		}
		datos.sSql = datos.sSql + "  ORDER BY CVE_SISTEMA ";
		log.debug(datos.sSql);
		datos.ejecutaSql();
		
		try {
			while (datos.rs.next()){
				Sistema sistema = new Sistema();
				sistema.setCveSistema(datos.rs.getString("CVE_SISTEMA"));
				sistema.setDesSistema(datos.rs.getString("DES_SISTEMA"));
				listaSistemas.add(sistema);
			}
		} catch (SQLException e) {
			throw new EJBException(e.getMessage());
		}
		return listaSistemas;
	}
	
	@ConexionBD("jdbc/modificaciones")
	public boolean eliminaSistema(String sCveSistema){
		boolean bExito = false;
		datos.abreConexion("jdbc/modificaciones");
		datos.sSql = "\n DELETE FROM INF_SISTEMA WHERE CVE_SISTEMA='" + sCveSistema + "' \n";
		log.debug(datos.sSql);
		
		bExito = datos.ejecutaUpdate() > 0 ? true : false;

		return bExito;
	}
	
	
	@ConexionBD("jdbc/modificaciones")
	public boolean actualizaSistema(Sistema sistema){
		boolean bExito = false;
	
		datos.sSql = "\n UPDATE INF_SISTEMA  SET \n" +
					"   DES_SISTEMA ='" + sistema.getDesSistema() + "' \n" +
					" WHERE CVE_SISTEMA = '" + sistema.getCveSistema() + "' \n"; 
		log.debug(datos.sSql);
		bExito = datos.ejecutaUpdate() > 0 ? true : false;
		return bExito;
	}
	
	
	@ConexionBD("jdbc/modificaciones")
	public boolean altaSistema(Sistema sistema){
		boolean bExito = false;
		datos.sSql = "\n INSERT INTO INF_SISTEMA (  \n" + 
						"CVE_SISTEMA,  \n" +
						"DES_SISTEMA  \n" +
					" ) VALUES(  \n" +
						"'" + sistema.getCveSistema() + "',  \n" +
						"'" + sistema.getDesSistema() + "'   \n" +
					" ) \n";
		log.debug(datos.sSql);
		bExito = datos.ejecutaUpdate() > 0 ? true : false;
		return bExito;
	}
	
	
	@ConexionBD("jdbc/modificaciones")
	public Sistema getSistema(String sCveSistema){
		Sistema sistemaBusqueda = null;
		
		//VERIFICA QUE LA CLAVE DE SISTEMA NO VENGA VACIA O NULA
		if (sCveSistema!=null){
			if (!sCveSistema.equals("")){
				Sistema sistema = new Sistema();
				sistema.setCveSistema(sCveSistema);
				
				List<Sistema> lista = getSistemas(sistema);
				for(Sistema respuesta : lista){
					sistemaBusqueda = respuesta;
					break;
				}				
			}
		}
		return sistemaBusqueda;
	}	
}
