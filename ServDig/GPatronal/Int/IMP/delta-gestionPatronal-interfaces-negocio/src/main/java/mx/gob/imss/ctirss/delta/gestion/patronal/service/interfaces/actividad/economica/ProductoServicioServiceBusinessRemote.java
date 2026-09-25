package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;

@Remote
public interface ProductoServicioServiceBusinessRemote {
	
	DatosSalidaPaginador<Producto> paginarProductos( DatosEntradaPaginador<Producto> datatablein);

	Producto getProducto(Producto producto);
	
	void agregarProductoServicio(Producto producto) throws Exception;
	
	void eliminarProducto(Producto producto) throws Exception;
	
	void modificarProducto(Producto producto)  throws Exception;

}
