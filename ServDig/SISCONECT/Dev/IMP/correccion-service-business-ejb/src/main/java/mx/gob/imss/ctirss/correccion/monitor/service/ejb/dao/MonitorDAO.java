package mx.gob.imss.ctirss.correccion.monitor.service.ejb.dao;

import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtErrorCargaCed;

public interface MonitorDAO  <T extends AbstractModel>{

	public T agregar(T model);
	public void eliminar(T model);
	public T modificar(T model);
	public List<T> consultar(T model);
	public T consultaPorClave(T model);
	public List<T> consultarErrores(CrtErrorCargaCed cargaCed);
}
