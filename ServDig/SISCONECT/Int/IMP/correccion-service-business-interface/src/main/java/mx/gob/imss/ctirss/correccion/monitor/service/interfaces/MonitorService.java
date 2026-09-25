package mx.gob.imss.ctirss.correccion.monitor.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtErrorCargaCed;

public interface MonitorService <T extends AbstractModel>{

	public T agregar(T model);
	public void eliminar(T model);
	public T modificar(T model);
	public List<T> consultar(T model);
	public T consultaPorClave(T model);
	public List<T> consultarErrores(CrtErrorCargaCed crtErrorCargaCed);
}
