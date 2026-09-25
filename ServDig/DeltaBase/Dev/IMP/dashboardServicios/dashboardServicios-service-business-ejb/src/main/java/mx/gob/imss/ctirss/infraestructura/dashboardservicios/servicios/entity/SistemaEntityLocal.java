package mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.entity;

import java.util.List;
import javax.ejb.Local;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Sistema;

@Local
public interface SistemaEntityLocal {

	public List<Sistema> getSistemas(Sistema filtroBusqueda);
	
	public boolean eliminaSistema(String sCveSistema);
	
	public boolean actualizaSistema(Sistema sistema);
	
	public boolean altaSistema(Sistema sistema);
	
	public Sistema getSistema(String sCveSistema);

}
