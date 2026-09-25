package mx.gob.imss.ctirss.correccion.catalogos.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface TrabajadoresService<T extends AbstractModel>{

	public T agregar(T model);
	public T eliminar(T model);
	public T modificar(T model);
	public List<T> consultar(T model);
	public T consultaPorClave(T model);
	public T consultaPorClaveDatos(T model);
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);
	public List<T> consultarTrabajadores(T model);
	public List<CrcTrabajadores> consultarTrabajadoresSinPeriodo(CrcTrabajadores trabajadores);
}