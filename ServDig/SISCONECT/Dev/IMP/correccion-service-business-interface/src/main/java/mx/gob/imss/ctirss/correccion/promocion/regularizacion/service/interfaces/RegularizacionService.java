package mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public interface RegularizacionService<T extends AbstractModel> {
	
	public T agregar(T model);		
	public void eliminar(T model);
	public List<T> consultar(T model);
	public List<T> consultaPagosDetPorCvePago(T filtro);
	public T consultaPorClave(T model);
	public T consultaPorClavePago(T model);
	public T consultaPorClavePromocion(T model);
	public T modificar(T model);
	public DatosSalidaPaginador<T> paginaPagos(DatosEntradaPaginador<T> params);

}
