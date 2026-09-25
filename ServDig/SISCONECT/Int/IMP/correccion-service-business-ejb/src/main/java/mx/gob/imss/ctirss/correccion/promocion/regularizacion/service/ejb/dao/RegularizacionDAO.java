package mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.ejb.dao;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface RegularizacionDAO<T extends AbstractModel> {
	
	public T agrega(T model);
	public void elimina(T model);
	public List<T> consulta(T model);
	public List<T> consultaPagosDetPorCvePago(T filtro);
	public T consultaPorClave(T model);
	public T consultaPorClavePago(T model);
	public T consultaPorClavePromocion(T model);
	public T modifica(T model);
	public DatosSalidaPaginador<T> paginaPagos(DatosEntradaPaginador<T> params);

}
