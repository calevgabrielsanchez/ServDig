/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  DesecharBusinessRemote
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.desechar
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.desechar;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.clasificacion.EstatusMovimientoException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionPropuestaDTO;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Remote
public interface DesecharBusinessRemote{

	
	/**
	 * Pasa la solicitud y tramite a estado desechado
	 * Envia movimiento 06 a SINDO
	 * Envia archivo a FS y lo registra en BD
	 * @param dto
	 * @param idTipoTramite 
	 * @param fechaSurteEfecto
	 * @return
	 * @throws Exception
	 */
	Integer desecharSolicitud(ClasificacionPropuestaDTO dto, String idTipoTramite, String fechaSurteEfecto) throws EstatusMovimientoException;

	/**
	 * Actualiza la tabla de analisis de MAC
	 * e inserta registro en la tabla de historico
	 * con estado desechado
	 * @param analisisClasificacionEmpresas
	 * @param comentarios
	 * @return
	 * @throws Exception
	 */	
	void desechaAnalisisMAC(AnalisisClasificacionEmpresas analisisClasificacionEmpresas, String comentarios)
			throws Exception;
	
	/**
	 * Actualiza la tabla de DIT_CLASIFICACION
	 * la fraccion y prima capturada
	 * @param clasificacionSO
	 * @param fraccionNueva
	 * @return
	 * @throws Exception
	 */		
	void actualizaClasificacionPatron(Clasificacion clasificacionSO, Fraccion fraccionNueva) throws Exception;

	/**
	 * Actualiza a estado desechado
	 * la solicitud y tramite
	 * @param cveIdSolicitud
	 * @return
	 * @throws Exception
	 */	
			// SE CANCELA METODO YA QUE ESTA FUNCIONALIDAD YA NO APLICA
	//void desechaSolicitudyTramite(String cveIdSolicitud) throws Exception;

	/**
	 * Actualiza el historico de causa
	 * @param analisisClasificacionEmpresas
	 * @return
	 * @throws Exception
	 */		
	void registraHistCausa(AnalisisClasificacionEmpresas analisisClasificacionEmpresas) throws Exception;

	/**
	 * Envia movimiento 06 
	 * @param sujetoObligado
	 * @param tipoCausaAnalisis
	 * @return
	 * @throws Exception
	 */		
	void enviaMovimientoSINDO(SujetoObligado sujetoObligado, String tipoCausaAnalisis, String fechaSurteEfecto);

}
