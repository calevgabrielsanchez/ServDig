package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.actividad.economica;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.ProductoServicioServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.actividad.economica.ProductoServicioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;

@Stateless(name="productoServicioServiceBusiness", mappedName="productoServicioServiceBusiness")
public class ProductoServicioServiceBusiness extends AbstractServiceBusiness
		implements ProductoServicioServiceBusinessRemote,
		ProductoServicioServiceBusinessLocal {
	
	@EJB
	private ProductoServicioServiceEntityLocal productoServicioServiceEntity;

	@Override 
	public DatosSalidaPaginador<Producto> paginarProductos(
			DatosEntradaPaginador<Producto> datatablein) {
		System.out.println("Modelo recibido para paginar: "+datatablein.getModelo());
		return this.productoServicioServiceEntity.paginar(datatablein);
	}

	@Override
	public Producto getProducto(Producto producto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void agregarProductoServicio(Producto producto)
			throws Exception {
		// 1. validar si no excede el limite maximo de registros (12 en este
		// caso)
		System.out.println("************Valida Max Reg producto: "+producto);
		this.productoServicioServiceEntity.validaLimMaxRegProducto(producto);
		
		System.out.println("************Valida existe producto: "+producto);
		// 2. Validamos si se puede agregar el producto
		this.productoServicioServiceEntity.validaExisteProducto(producto);
		
		System.out.println("************Agrega producto: "+producto);
		// 3. Agregamos el producto
		this.productoServicioServiceEntity.persistir(producto);
		
		
	}

	@Override
	public void eliminarProducto(Producto producto) throws Exception {
		this.productoServicioServiceEntity.borrar(producto);
	}

	@Override
	public void modificarProducto(Producto producto) throws Exception {
		//1. Validamos si se puede agregar el producto
		this.productoServicioServiceEntity.validaExisteProducto(producto);
		
		// 2. Agregamos el producto
		this.productoServicioServiceEntity.actualizar(producto);
	}


}
