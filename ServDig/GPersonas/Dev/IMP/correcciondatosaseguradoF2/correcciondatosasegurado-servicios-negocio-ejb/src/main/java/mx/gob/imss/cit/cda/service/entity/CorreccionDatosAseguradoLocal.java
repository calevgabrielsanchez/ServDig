package mx.gob.imss.cit.cda.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTipoNssAclaracion;
import mx.gob.imss.ctirss.delta.persistence.DitBitSeparacionPersonas;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionDatosAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.persistence.DitTipoCertificacionCorreccion;

@Local
public interface CorreccionDatosAseguradoLocal {

	Integer DIAS_MAXIMOS_ESPERA = 40;

	TramiteCorreccionCurp almacenarTramiteCDA(Long idTramite, String curp);

	void almacenaDatosLaborales(Long idTramiteCorreccion,
			DatosLaborales datosLaborales);

	void almacenaMotivos(Long idTramiteCorreccion,
			MotivoAclaracion motivoAclaracion);

	Long getIdTramiteActivo(List<String> curps, List<Integer> estadosValidos, String origen);

	DicEstadoTramite consultarEstadoTramiteById(Long idTramite);

	DitDetalleNss bloquearNSS(String nss, Long idCorreccionDatosAseg,
			Long idOrigen, String observaciones);

	DitDetalleNss consultarBloqueoNSS(String nss);

	int insertarResponableAutorizadorCorreccion(Long idTramite, int tipoUsr);

	String obtenerResponsableTramiteCDA(Long idSolicitud);

	String obtenerResponsableTramiteCDA(String folio);

	void actualizarSubdelegacionSolicitud(Solicitud sol);

	/**
	 * Actualiza el tipo del tramite para CDA
	 * 
	 * @param cveIdTramite
	 * @param cvdTipoTramite
	 */
	int actualizarTipoTramite(Long cveIdTramite, Long cvdTipoTramite,
			Long cveIdEstadoTramite);

	/**
	 * Actualiza el tipo de regulariacion CDA
	 * 
	 * @param cveIdTramite
	 * @param cvdIdTipoCorreccion
	 */
	int actualizarRegNss(String cveIdTramite, Long cvdIdTipoCorreccion);

	/**
	 * Guarda la certificacion que le aplicara al nss
	 * 
	 * @param certificacion
	 */
	void guardarCertificacionNss(DitTipoCertificacionCorreccion certificacion);

	/**
	 * Obtiene el tipo de aclaracion
	 * 
	 * @param idTipoAclaracion
	 * @return DicTipoNssAclaracion
	 */
	DicTipoNssAclaracion obtenerTipoAclaracion(Long idTipoAclaracion);

	/**
	 * Metodo que actualiza el tipo de Nss al Detalle Nss
	 * 
	 * @param idDetalle
	 * @param cveIdTipoNss
	 */
	void actualizarTipoDetalleNss(Long idDetalle, Long cveIdTipoNss);

	/**
	 * Recupera la lista de nss relacionados a una solictitud
	 * 
	 * @param cveSolicitud
	 * @param tramitesIniciales
	 * @return lista de tramites de un solicitud
	 */
	List<DitDetalleNss> obtenerTramitesSol(Long cveSolicitud,
			boolean tramitesIniciales);

	/**
	 * Se obtiene las aclaraciones para un nss
	 * 
	 * @param cveIdDetalle
	 * @return lista de Aclaraciones
	 */
	List<DitTipoCertificacionCorreccion> obtenerAclaracionesTramite(
			Long cveIdDetalle);

	/**
	 * Se obtiene las regulaciones para un nss
	 * 
	 * @param nss
	 * @return lista de DES_TIPO_NSS
	 */
	List<String> obtenerRegulacionNss(String nss);

	/**
	 * Elimina las correcion de un NSS
	 * 
	 * @param idDetalleNss
	 * @return numero de registros eliminados
	 */
	int eliminarCorrrecionesNss(Long idDetalleNss);

	/**
	 * Obtiene el detalle nss por id
	 * 
	 * @param idDetalleNss
	 * @return DitDetalleNss
	 */
	DitDetalleNss obtenerDetalleNss(Long idDetalleNss);

	/**
	 * Obtiene detalle nss por tramite y numero de seguridad social
	 * 
	 * @param idTramite
	 * @param nss
	 * @return
	 */
	DitDetalleNss obtenerDetalleNss(Long idTramite, String nss);

	DitCorreccionDatosAsegurado obtenerCorreccionDatosAsegurado(
			Long cveIdTramite);

	/**
	 * Metodo para obtener nss asociados a curp del asegurado
	 * 
	 * @param curp
	 * @return
	 */
	List<String> obtenerNssDitAsignacionPorCurp(String curp);


	List<Object> ValidacionTramiteExiste(List<String> curps);

	List<String> ObtenerIDTramite(String numFolio);

	void ActulizarTipoTramite(String idTramite);

	void eliminarMovimientoPorDetalleNss(Long cveIdDetalleNss,
			Long cveIdTipoTramiteCorrNss);
	
	int eliminarCorrrecionesNssPorSistema(Long idDetalleNss);

	void guardarActualizarBitacoraSeparacionPersonas(DitBitSeparacionPersonas bitacora);

	List<DitBitSeparacionPersonas> obtenerBitacoraSeparacionPersonas(Long cveIdTramite, boolean regActualizado);

    boolean consultaNSSPension(List<String> nss);

	void cancelarSolicitudTramiteInstancia(Long idSolicitud, Long idTramite, List<String> nss);
}
