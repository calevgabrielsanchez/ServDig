/**
*
*
**/
package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.BuzonClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: ClaseServiceEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion
 *  @Fecha: 11:54:38
 */
@Local
public interface ClasificacionServiceEntityLocal {
	List<Clasificacion> consultarClasesExistentesPorRFC(String RFC, TipoPersonaFiscal tipoPersona, String numeroRegistroPatronal);
	List<Clasificacion> consultarClasesExistentesPorRFC(String RFC, TipoPersonaFiscal tipoPersona);

	Clasificacion consultarClasificacionPorRegistroPatronal(String numeroRegistroPatronal);
	
	/**
	 * Se crea un registro en la tabla DIT_REINTENTO_RPC asociado a la solicitud proporcionada
	 * si este registro ya existe y el indFallaRPC es "1" se descarta la actualización de este parámetro
	 * y se actualiza el indReintentoRpc a "1"
	 * @param idSolicitud
	 * @param indFallaRPC
	 */
	void crearActualizarReintentoRPC(Solicitud solicitud);
	
	/**
	 * Consulta la información de la tabla DIT_REINTENTO_RPC
	 * @param idSolicitud Identificador de la solicitud
	 * @return Solicitud
	 */
	Solicitud consultarReintentoRPC(Long idSolicitud);	
	Fraccion consultarFraccionPorId(Long idFraccion);
	
	/**
	 * Obtiene la lista de Fracciones de los distintos rp del patron
	 * solo contempla personas morales y registros patronales vigentes
	 * para validar la baja se contempla fecha baja y los datos de la tabla 
	 * DitDtsExtraPatron
	 * @param rfc
	 * @return
	 */
	List<Fraccion> obtenerFraccionesPreviasPorRfcPatron(String rfc);
	
	/**
	 * Obtiene la lista de Fracciones de los distintos rp del patron en el mismo municipio IMSS ya que se permitira la captura de una actividad diferente
	 * solo contempla personas morales y registros patronales vigentes
	 * para validar la baja se contempla fecha baja y los datos de la tabla 
	 * DitDtsExtraPatron
	 * @param rfc
	 * @return
	 */
	List<Fraccion> obtenerFraccionesPreviasPorRfcPatronMunicipioIMSS(String rfc, String cvecMunicipioSINDO);
	
	/**
     *  Obtiene el registro patronal de la persona fisica con la mayor prima de riesgo en base al RFC
     * 
     * @param  rfc
     * @return Nrp
     */
    String obtenerNrpPrimaRiesgoMayorPersonaFisica(String rfc);

	List<Fraccion> obtenerFraccionesPreviasPorRfcPatronMunicipioIMSSRPC(String rfc, String cvecMunicipioSINDO);
	
	/**
     *  Busca solicitudes con base a nrp, tipo de tramite y fecha surte efecto
     * 
     * @param  nrp
     * @param  cveIdTipoTramite
     * @param  fechaSurteEfecto
     * @return 
     */	
	List<String> buscaSolicitudesSimilares(String nrp, String cveIdTipoTramite, String fechaSurteEfecto);

	/**
     *  Inserta mensaje en la tabla de buzon de clasificacion
     * 
     * @param  nrp
     * @param  cveIdTipoTramite
     * @param  folio
     * @param  mensaje
     * @return 
     */	
	void insertaMensajeBuzon(String nrp, String cveIdTipoTramite, String folio, String mensaje);

	/**
     *  Recupera mensajes del buzon con base al registro patronal
     * 
     * @param  nrp
     * @return 
     */	
	BuzonClasificacion consultaMensajeBuzon(String nrp);
	
}
