/*
 * Interfaz remota que define los metodos necesarios 
 * para la generacion de documentos, que incluye la insercion la generacion
 * de tramite, solicitud asociados.
 * 
 * Creado el 22/04/2013, Juan Osorio Alvarez
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabientes.PropiedadesDocumento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface TramiteDocumentosServiceRemote {
	
	/**
	 * Este metodo se encarga de la generacion del tramite de consulta de reporte
	 * de vigencia, asi como la expedición del mismo
	 * @param asignacionNSS
	 * @param usuario
	 * @param identificadoresMap Contiene los ids del tramite y solicitud a generar
	 * @param Que indica si el reporte se enviara por correo electrónico
	 * @return regresa un mapa que contiene un array de bites que representa el reporte en formato PDF, y el id del tramite para su posterior consulta
	 */
	public Map<String, Object> generaTramiteDocumentoReporteVigencia(AsignacionNSS asignacionNSS, Usuario usuario, Map<String, Integer> identificadoresMap, boolean enviaMail) throws DocumentoException;
	
	/**
	 * Este metodo se encarga de generar un comprobante en PDF de acuerdo al valor pasado en el parametro identificador
	 * @param asignacionNSS
	 * @param solicitud
	 * @param usuario
	 * @param identificadorReporte
	 * @param propiedades
	 * @param identificadoresMap
	 * @return
	 * @throws DocumentoException
	 */
	public Object generaDocumentoConSelloDigital(AsignacionNSS asignacionNSS, Solicitud solicitud, Usuario usuario, Integer identificadorReporte, PropiedadesDocumento propiedades, Map<String, Integer> identificadoresMap)throws DocumentoException;
	
	public FirmaElectronica generaFirmaElectronica(AsignacionNSS asignacionNSS, Solicitud solicitud, String nombreTramite) throws DocumentoException;
	
	/**
	 * Este metodo se encarga de hacer el envio de correo electronico con documento adjunto
	 * @param asignacionNSS
	 * @param atachDocto
	 * @param url
	 * @throws Exception
	 */
	public void enviaCorreo(AsignacionNSS asignacionNSS, byte[] atachDocto, String url) throws Exception;
	
	/**
	 * Este metodo se encarga de obtener el documento guardado en BD
	 * @param idTramite
	 * @param idDocumentoPorTipo
	 * @return
	 */
	public Object getDocumentoPorTipoIdTramite(Long idTramite, Long idDocumentoPorTipo);
	
	
	void generarSolicitudRechazo(Long idOrigenSolicitud,String curp,String observaciones);
	public Map<String, Object> generaTramiteDocumentoReporteConstanciaVigencia(AsignacionNSS asignacionNSS, Usuario usuario, Map<String, Integer> identificadoresMap, boolean enviaMail) throws DocumentoException;
	
	public Map<String, Object> generaTramiteDocumentoReporteConstanciaVigenciaWS(AsignacionNSS asignacionNSS, Usuario usuario, Map<String, Integer> identificadoresMap, boolean enviaMail) throws DocumentoException;

	public Solicitud crearTramiteSolicitud(Fisica fisica,Long idOrigenSolicitud, Usuario usuario, Map<String, Integer> identificadoresMap) throws SolicitudNoValidaException, SolicitudException;
}