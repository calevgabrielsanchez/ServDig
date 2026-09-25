package mx.imss.ctirss.menu.service.ejb.dao;

import java.util.List;

import mx.imss.ctirss.base.paginador.model.DatosEntradaPaginador;
import mx.imss.ctirss.base.paginador.model.DatosSalidaPaginador;
import mx.imss.ctirss.framework.base.model.AbstractModel;

public interface MenuDAO<T extends AbstractModel> {
	
	public List<T> consulta(T model);

	public List<T> consultaDictamen(T model);
	
	
	

}
