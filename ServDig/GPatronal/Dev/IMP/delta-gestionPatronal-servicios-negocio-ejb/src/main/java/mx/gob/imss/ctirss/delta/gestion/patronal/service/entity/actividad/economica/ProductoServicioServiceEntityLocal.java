package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.persistence.DitProducto;

@Local
public interface ProductoServicioServiceEntityLocal {
	
	DatosSalidaPaginador<Producto> paginar(DatosEntradaPaginador<Producto> params);
	
	DatosSalidaPaginador<Producto> paginar(DatosEntradaPaginador<Producto> params, DitProducto entity) throws Exception;
	
	Producto get(Producto producto) throws Exception;
	
	Producto persistir(Producto model);
	
	void borrar (Producto instance) throws Exception;

	void validaLimMaxRegProducto(Producto producto) throws Exception;
	
	void validaLimMinRegProducto(Producto producto) throws Exception;
	
	void validaExisteProducto(Producto producto) throws Exception;
	
	void validaBorrarProducto(Producto producto) throws Exception;
	
	Producto actualizar(Producto instance) throws Exception;
	
	int consultarNumRegistrosPorActividadEconomica(Long cveActividadEconomica);
	
	List<Producto> consultaPorClaveActividad(Producto producto) throws Exception;

}
