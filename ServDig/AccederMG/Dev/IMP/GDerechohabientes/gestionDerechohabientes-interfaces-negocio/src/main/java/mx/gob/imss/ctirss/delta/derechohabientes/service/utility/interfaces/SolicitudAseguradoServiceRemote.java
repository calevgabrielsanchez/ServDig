package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

/**
 * 
 * Intefaz encargada de hacer las consultas para ws de vigencia de tramites por asegurado
 *
 */
@Remote
public interface SolicitudAseguradoServiceRemote {
	
	/**
	 * Metodo que consulta si un asegurado ha realizado tramites en IMSS digital de derechohabientes
	 * @param strNSS
	 * @return integer con 0 si no tiene solicitudes y 1 si a realizado solicitudes.
	 */
	 int tieneSolicitudesAseguradoPorNSS(String strNSS);

}
