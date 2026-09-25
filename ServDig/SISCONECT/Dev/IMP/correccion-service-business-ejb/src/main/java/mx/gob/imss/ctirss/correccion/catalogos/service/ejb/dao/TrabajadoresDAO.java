package mx.gob.imss.ctirss.correccion.catalogos.service.ejb.dao;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface TrabajadoresDAO<T extends AbstractModel>{

	public T agrega(T model);
	public T elimina(T model);	
	public T modifica(T model);
	public List<T> consulta(T model);
	public T consultaPorClave(T model);
	public T consultaPorClaveDatos (T model);
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);
	public List<T> consultarTrabajadores(T filtro);
	public List<T> paginadorTrabajadoresSinPeriodo(T filtro);
	public List<T> paginadorTrabajadoresPeriodo(T filtro, Long cveAnexo, Long periodo);
	public List<T> obtenerHijos(T filtro);
	public void eliminaHijos(List<T> model);
	
}
