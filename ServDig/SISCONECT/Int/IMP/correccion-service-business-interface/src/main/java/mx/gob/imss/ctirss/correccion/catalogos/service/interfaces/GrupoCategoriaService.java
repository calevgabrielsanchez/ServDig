package mx.gob.imss.ctirss.correccion.catalogos.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface GrupoCategoriaService <T extends AbstractModel>{

	public T agregar(T model);
	public void eliminar(T model);
	public T modificar(T model);
	public List<T> consultar(T model);
	public T consultaPorClave(T model);
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);
}
