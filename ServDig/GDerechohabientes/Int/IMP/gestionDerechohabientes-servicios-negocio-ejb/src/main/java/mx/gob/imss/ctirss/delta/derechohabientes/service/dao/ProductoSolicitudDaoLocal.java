package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.math.BigInteger;

import javax.ejb.Local;

@Local
public interface ProductoSolicitudDaoLocal {


	/**
	 * Guarda los Documentos del Tramite Solicitud.
	 * 
	 * @param productoSolicitud
	 * DitTramiteSolicitud completo 
	 * DitTramiteSolicitud.ditSolicitud.cveIdSolicitud
	 * DitTramiteSolicitud.Tramite Completo
	 */
	
	//public void altaProductoSolicitud(DitProductoSolicitud productoSolicitud);
	
	//public List<DitProductoSolicitud> getProductosSolicitudTramite(BigInteger idTramite);

	//public void modificarProductoSolicitud(ProductoSolicitud producto);
	
	public byte[] getWaterMarkProductoSolicitud();
	
	public void eliminarProductoSolicitud(BigInteger id);
	 
}
