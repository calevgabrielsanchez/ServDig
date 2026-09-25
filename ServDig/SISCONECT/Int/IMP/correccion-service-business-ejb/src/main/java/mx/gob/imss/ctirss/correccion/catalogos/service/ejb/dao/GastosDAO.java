package mx.gob.imss.ctirss.correccion.catalogos.service.ejb.dao;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface GastosDAO<T extends AbstractModel> {
	
	public T agrega(T model);
	public T elimina(T model);	
	public T modifica(T model);
	public List<T> consulta(T model);
	public T consultaPorClave(T model);
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);
	

}
