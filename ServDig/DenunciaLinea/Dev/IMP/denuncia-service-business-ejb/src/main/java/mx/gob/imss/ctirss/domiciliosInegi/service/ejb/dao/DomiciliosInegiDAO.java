package mx.gob.imss.ctirss.domiciliosInegi.service.ejb.dao;

import java.util.List;

import mx.imss.ctirss.base.paginador.model.DatosEntradaPaginador;
import mx.imss.ctirss.base.paginador.model.DatosSalidaPaginador;
import mx.imss.ctirss.framework.base.model.AbstractModel;

public interface DomiciliosInegiDAO<T extends AbstractModel> {
	
	public T agrega(T model);
	public void elimina(T model);	
	public T modifica(T model);
	public List<T> consulta(T model);
	public T consultaPorClave(T model);
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);
	

}
