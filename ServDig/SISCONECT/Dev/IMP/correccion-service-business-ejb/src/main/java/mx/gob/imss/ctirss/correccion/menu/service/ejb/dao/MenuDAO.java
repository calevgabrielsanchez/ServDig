package mx.gob.imss.ctirss.correccion.menu.service.ejb.dao;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface MenuDAO<T extends AbstractModel> {
	
	public List<T> consulta(T model);
	
	public List<T> obtenerMenuPatron();
	

}
