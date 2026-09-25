package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.dto.ObtCifrasSolicitudProceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;


@Local
public interface SolicitudEntityLocal {
	Solicitud crear(Solicitud solicitud);

	Solicitud consultarPorIdTramite(Long idTramite)  throws SolicitudNoEncontradaException;
	Solicitud consultar(Solicitud solicitud, boolean obtenerDatosXML) throws SolicitudNoEncontradaException;

	/**
	 * Este metodo consulta el detalle de una solicitud dado el Folio
	 * 
	 * @param solicitud Folio de Solicitud
	 * @return Detalle de la Solicitud
	 * @throws SolicitudNoEncontradaException
	 */
	Solicitud consultarFolio(Solicitud solicitud, boolean obtenerDatosXML) throws SolicitudNoEncontradaException;
	
	String consultarDescripcionEstadoSolicitud(String folioSolicitud) throws SolicitudNoEncontradaException;

	Solicitud actualizarEstados(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException;

	Solicitud actualizarTramites(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException;
        
    Solicitud actualizaTramite(Solicitud solicitud, Tramite tramite) throws SolicitudNoEncontradaException, TramiteNoEncontradoException;

	/**
	 * 191807 020712 Este metodo consulta el estatus de una Solicitud
	 * 
	 * @param solicitud
	 * @return
	 */
	Solicitud consultarEstatus(Solicitud solicitud);

	/**
	 * Metodo generico para consultar las solicitudes de una persona fisica o moral
	 * dependiendo de los filtros especificados
	 * @param idPersona
	 * @param tipo Tipo de la persona Moral o Fisica
	 * @param tipoSolicitud Tipo de la solicitud
	 * @param estadoSolicitud Estados de la solicitud a filtrar
	 * @param maximoResultados Indica si se aplica un maximo de 10 registros.
	 * @return
	 */
	List<Solicitud> obtenerSolicitudPorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud,
			EstadoSolicitudEnum estadoSolicitud, Boolean maximoResultados);
	
	List<Solicitud> obtenerSolicitudConDatosBasePorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud,
			EstadoSolicitudEnum estadoSolicitud);

	/**
	 * Metodo generico para consultar las solicitudes de una persona fisica o moral
	 * dependiendo de los filtros especificados
	 * @param idPersona
	 * @param tipo Tipo de la persona Moral o Fisica
	 * @param tipoSolicitud Tipo de la solicitud
	 * @param estadoSolicitud Estados de la solicitud a filtrar
	 * @param maximoResultados Indica si se aplica un maximo de 10 registros.
	 * @return
	 */
	List<Solicitud> obtenerSolicitudPorPersonaTramite(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite,
			EstadoSolicitudEnum estadoSolicitud, Boolean maximoResultados);
	
	/**
	 * Metodo generico para consultar las solicitudes de una persona fisica o moral
	 * dependiendo de los filtros especificados
	 * @param idPersona
	 * @param tipo Tipo de la persona Moral o Fisica
	 * @param tipoSolicitud Tipo de la solicitud
	 * @param estadoSolicitud Estados de la solicitud a filtrar
	 * @param maximoResultados Indica si se aplica un maximo de 10 registros.
	 * @param convertXml Indica si se requiere tranformar el xml del detalleTramite
	 * @return
	 */
	List<Solicitud> obtenerSolicitudPorPersonaTramite(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite,
			EstadoSolicitudEnum estadoSolicitud, Boolean maximoResultados, Boolean convertXml);	
	
	/**
	 * Crear un tramite y lo asocia a una solicitud determinada
	 * 
	 * @param tramite
	 * @param idSolicitud
	 * @return
	 */
	Tramite crearTramiteASolicitud(Tramite tramite, Long idSolicitud);
	
	/**
	 * Obtiene las solicitudes en base a los filtros seleccionados
	 * @author Hugo Martinez
	 * @Date 27 Junio 2013
	 * @param filtros
	 * @return List<Solicitud>
	 */
	DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltro(
			DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtros,
			boolean mostrarSolicInternet);
	
	/**
	 * Obtiene las solicitudes en base a los filtros seleccionados considerando &uacute;nicamente solicitudes
	 * con estado rechazado, cancelado y atendidas.
	 * @author Hugo Martinez
	 * @Date 30/08/2012
	 * @param filtros
	 * @return List<Solicitud>
	 */
	DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltroParaPatron(DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtros);

	/**
	 * Obtiene la informaci�n b�sica de las solicitudes de una persona,
	 * s�lo obtiene informaci�n acerca de la solicitud. Se puede filtrar
	 * por tipo y estado de la solicitud.
	 * 
	 * @param idPersona
	 * @param tipoPersona
	 * @param tipoSolicitud
	 * @param estadoSolicitud
	 * @return
	 */
	List<Solicitud> obtenerInfoBasicaSolicitudesPorPersona(
			Long idPersona,
			Long tipoPersona,
			TipoSolicitudEnum tipoSolicitud,
			mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitudEnum estadoSolicitud);

	List<Solicitud> obtenerInfoBasicaSolicitudesPorPersonaPortal(
			Long idPersona,
			Long tipoPersona,
			TipoSolicitudEnum tipoSolicitud,
			mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitudEnum estadoSolicitud);
				
	/**
	 * Obtienes todas las solicitudes asociadas al registro patronal proporcionado
	 * @param idPatronSujetoObligado Identificador del RP en la base delta
	 * @return List<Solicitud>
	 */
	List<Solicitud> listarSolicitudesDeRegistroPatronalPorId(
			Long idPatronSujetoObligado);
	
	/**
	 * Inserta registro en la tabla DitTramitePatSujObligado
	 * @param tramite
	 * @param idPatronSujetoObligado
	 */
	void asociarTramiteAltaARegistroPatronal(Tramite tramite, Long idPatronSujetoObligado);
	
	/**
	 * Se actualizan las fechas de presentaci�n y conclusi�n, el usuario, la raz�n de cancelacion, 
	 * la fecha de registro actualizado(con sysdate) y la subdelegaci�n
	 * @param solicitud
	 */
	void actualizarDatosGeneralesDeSolicitud(Solicitud solicitud);
	
	void actualizarDocumentosTramite(Long idTramite, Long idDocumentoTipo, Object bytes);
	
	Object getDocumentoPorTipoIdTramite(Long idTramite, Long idDocumentoPorTipo);
	
	Subdelegacion getSubdelegacionById(Long idSubdelegacion);
	
	Delegacion getDelegacionById(Long idDelegacion) ;
	
	void actualizarEstadoMensajeError(String folioSolicitud, Integer idEstadoParaAsignar, Integer idEstadoTramiteAsignar, String observacion);
	
	void cancelarSolicitudPorFolio(String folio);
	
	void cancelarTramitePorId(Long idTramite) throws TramiteNoEncontradoException;
        
        void cancelarTramite(Tramite tramite) throws TramiteNoEncontradoException;

	void concluirSolicitudPorFolio(String folio);
	
	/**
	 * Checa si existe una solicitud en espec�fico. Requiere ya sea del folio o
	 * id de la solicitud, primero realiza la b�squeda de la solicitud por
	 * folio, si no se cuenta con folio busca por id
	 * 
	 * @param solicitud
	 * @return
	 */
	boolean existeSolicitud(Solicitud solicitud);
	
	void agregarPersonaATramite(Long idTramite, Long idPersona) throws TramiteNoEncontradoException;
	
	Tramite actualizarXMLTramite(Tramite tramite) throws TramiteNoEncontradoException, IllegalArgumentException;
	
	mx.gob.imss.digital.modelo.tramite.Tramite actualizarXMLTramite(
			mx.gob.imss.digital.modelo.tramite.Tramite tramite)
			throws TramiteNoEncontradoException, IllegalArgumentException;
	
	void actualizaAConcluida(Solicitud solicitud) throws SolicitudNoEncontradaException;
	void cancelarSolicitud(Long idSolicitud, Long idRazonRechazo, Long idRazonCancelacion, String usuarioCancelacion, String observacionesCancelacion);
	void actuliazaUsuarioSolicitud(Solicitud solicitud) throws IllegalArgumentException;
	void actualizaTipoTramite(Long idTramite, Long idTipoTramite);
	
	List<Solicitud> getSolicitudesPorNss(String nss, List<Long> estadosSolicitud, Long idIntegrante, Long maxResult, Long idOrigenSolicitud);
	
	List<Solicitud> getSolicitudesPorIdNssTipoYEstadoTramite(Long idAsignacionNss, Long idTipoSolicitud, 
			Long idOrigenSolicitud, List<Long> estadosSolicitud, Long registrosAObtener );
	
	List<Solicitud> obtenerSolicitudes();
	
	List<ObtCifrasSolicitudProceso> obtenerCifrasSolicitudProceso();
	
	public List<Solicitud> obtenerSolicitudPorPersona(Long idPersona, TipoPersonaFiscal tipo, 
			List<Long> tiposSolicitud, List<Long> estadosSolicitud, Boolean maximoResultados);

	void actualizaAConcluidaDH(Solicitud solicitud) throws SolicitudNoEncontradaException;
	
	/**
	 * Metodo que asocia una persona a la solicitud
	 * @param cveIdSolicitud
	 * @param cveIdPersonaInteresada
	 * @param cveTipoPersonaInteres
	 * @throws SolicitudNoEncontradaException
	 */
	void insertaPersonaInteresadaSolicitud(Long cveIdSolicitud, Long cveIdPersonaInteresada, Long cveTipoPersonaInteres) 
			throws SolicitudNoEncontradaException;
        
    /**
     * Metodo que asocia una solicitud a la subdelegacion asignada para el tramite.
     * @param idSolicitud 
     * @param idSubDelegacion
     */
    void asociarSolicitudSubdelegacion(Long idSolicitud, Long idSubDelegacion);
    
    String getHomoclaveSolicitud(String folioSolicitud);
    
    String getHomoclaveSolicitud(Long idsolicitud);

	List<Map<String, String>> buscaSolicitudesHsql(Map<String, String> datosBusqueda, Integer primerResultado,
            Integer resultadosPorPagina);

    Long buscaConteoSolicitudesHsql(Map<String, String> datosBusqueda);
	
	
	Solicitud obtenerPorFolioSolicitud(String folioSolicitud);
	
	List<Long> encontrarSolicitudesPorPersonaYEstados(Long idPersona, List<Long> idsEstados,
            Long idTipoSolicitud);
			
	Long obtenerIdPersonaInteresada(Long solicitudId);
	
	List<Long> obtenerTramitesNoCanceladosPorPersonaYTipo(Long idPersona,
            Long tipoSolicitud);

	List<Long> encontrarSolicitudesPorPersonaYEstadosDeTramite(Long idPersona, List<Long> idsEstados,
            Long idTipoSolicitud);

    List<Long> encontrarTramitesPorPersonaYEstadosDeTramite(Long idPersona, List<Long> idsEstados);
	
	 public void actualizarTipoTramite(String Tramite, String idSolicitud, String TipoNSS);
    
    public void actualizarTipoNSS(String nss, String TipoNSS);

	ArrayList<String> obteneCatalogo();
	
	public String obtener_CVECDA(String folio);

	Solicitud actualizarTramitesMarcaOSB(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException;;
	
	/**
	 * Metodo para guardar una cita 
	 * @param cita
	 * @throws SolicitudException
	 */
	CitaSolicitud guardaCitaSolicitud(CitaSolicitud cita) throws SolicitudException;
	
	/**
	 * Metodo que actualiza una cita
	 * @param cita
	 * @throws SolicitudException
	 */
	CitaSolicitud actualizaCitaSolicitud(CitaSolicitud cita) throws SolicitudException;
	
	/**Metodo que claculoa la fecha de la proxima cita basado en los atribuots de busqueda que recibe
	 * 
	 * @param cita
	 * @throws SolicitudException
	 */
	CitaSolicitud calculaFechaCita(CitaSolicitud cita) throws SolicitudException;
	
	/**Valida si en la fecha que se envia en el objeto cita  es valida para asignar
	 * 
	 * @param cita
	 * @return
	 * @throws SolicitudException
	 */
	boolean validaFechaCita(CitaSolicitud cita) throws SolicitudException;
	
	/**COnsulta la cita ya sea foir folio, id o id solicitud
	 * 
	 * @param cita
	 * @return
	 * @throws SolicitudException
	 */
	CitaSolicitud consultaCItaSOlicitud(CitaSolicitud cita) throws SolicitudException;

}
