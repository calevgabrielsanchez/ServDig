package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudEnProcesoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;



@Remote
public interface SolicitudBusinessRemote {
	Solicitud crear(Solicitud solicitud) throws SolicitudNoValidaException;

	Solicitud crearSolicitudInicial(EstadoSolicitud estadoSolicitud,
			TipoSolicitud tipoSolicitud, OrigenSolicitud origenSolicitud,
			Usuario usuario) throws SolicitudNoValidaException;

	Solicitud crearSolicitudInicialPorEnum(EstadoSolicitudEnum estadoSolicitudInicial, TipoSolicitudEnum tipoSolicitudInicial,
			OrigenSolicitudEnum origenSolicitudInicial, Usuario usuario)
			throws SolicitudNoValidaException;

	Tramite inicializarTramite(Tramite tramite, EstadoTramite estadoTramite,
			TipoTramite tipoTramite) throws SolicitudNoValidaException;

	Tramite inicializarTramitePorEnum(Tramite tramite,
			TipoTramiteEnum tipoTramiteInicial,
			EstadoTramiteEnum estadoTramiteInicial)
			throws SolicitudNoValidaException;

	Solicitud asociarTramiteSolicitud(Solicitud solicitud, Tramite tramite,
			EstadoTramite estadoTramite, TipoTramite tipoTramite)
			throws SolicitudNoValidaException;

	Solicitud asociarTramiteSolicitudPorEnum(Solicitud solicitud, Tramite tramite,
			TipoTramiteEnum tipoTramiteInicial,
			EstadoTramiteEnum estadoTramiteInicial)
			throws SolicitudNoValidaException;

	Solicitud consultar(Solicitud solicitud) throws SolicitudNoEncontradaException;
	Solicitud consultarPorIdTramite(Long idTramite)  throws SolicitudNoEncontradaException;
	Solicitud consultarPorFolioSolicitud(String folio) throws SolicitudNoEncontradaException;		
	
	ArrayList<String> obtenercatalogo();
	public String obtenerCVECDA(String folio);
	/**
	 * Este metodo consulta el detalle de una solicitud por el identificador sin obtener la informaci�n del XML
	 * (M�todo que permite disminuir la carga cuando no se requieren mas que los datos generales de 
	 * solicitud y tramites)
	 * @param solicitud Folio de Solicitud
	 * @return Detalle de la Solicitud
	 * @throws SolicitudNoEncontradaException
	 */
	Solicitud consultarSinDatosTramite(Solicitud solicitud) throws SolicitudNoEncontradaException;
	
	/**
	 * Este metodo consulta el detalle de una solicitud dado el Folio
	 * 
	 * @param solicitud Folio de Solicitud
	 * @return Detalle de la Solicitud
	 * @throws SolicitudNoEncontradaException
	 */
	Solicitud consultarFolio(Solicitud solicitud) throws SolicitudNoEncontradaException;
	
	
	/**
	 * Este metodo consulta el detalle de una solicitud dado el Folio sin obtener la informaci�n del XML
	 * (M�todo que permite disminuir la carga cuando no se requieren mas que los datos generales de 
	 * solicitud y tramites)
	 * @param solicitud Folio de Solicitud
	 * @return Detalle de la Solicitud
	 * @throws SolicitudNoEncontradaException
	 */
	Solicitud consultarFolioSinDatosTramite(Solicitud solicitud) throws SolicitudNoEncontradaException;

	
	// guardar & genera el detalle de tramite(s) en XML.
	/**
	 * Actualiza los tramites que se envian en la solicitud: si se trata de un
	 * tramite nuevo, lo creara, en caso de que ya exista, lo actualiza.
	 * 
	 * @param solicitud
	 * @return
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	Solicitud actualizarTramites(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException;
        
        Solicitud actualizaTramite(Solicitud solicitud, Tramite tramite) throws SolicitudNoEncontradaException, TramiteNoEncontradoException;
        
        void cancelarTramite(final Tramite tramite) throws TramiteNoEncontradoException;

	/**
	 * Actualiza los estados tanto de solicitud como de tramites pre existentes
	 * de la solicitud.
	 * 
	 * @param solicitud
	 * @return
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	Solicitud actualizarEstados(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException;

	/**
	 * 191807 020712 Este metodo consulta el estatus de una Solicitud. Si el
	 * estatus es EN_PROCESO(2) se lanzara una excepcion. Si no, no hara nada
	 * 
	 * @param solicitud
	 * @throws SolicitudEnProcesoException
	 */
	void isEnProceso(Solicitud solicitud) throws SolicitudEnProcesoException;

	List<Solicitud> obtenerSolicitudPorPersonaFisica(Long idPersonaFisica);
	List<Solicitud> obtenerSolicitudConDatosBasePorPersonaFisica(Long idPersonaFisica);

	List<Solicitud> obtenerSolicitudPorPersonaMoral(Long idPersonaMoral);
	List<Solicitud> obtenerSolicitudConDatosBasePorPersonaMoral(Long idPersonaMoral);

	/**
	 * Servicio para crear una solicitud y asociarla a la solicitud que se
	 * recibe como par&aacute;metro
	 * 
	 * @param tramite
	 * @param idSolicitud
	 * @return
	 */
	Tramite crearTramiteASolicitud(Tramite tramite, Long idSolicitud);
	
	
	/**
	 * Obtiene la solicitud con el estado y tipo proporcionados asociadas a una persona, 
	 * en caso de encontrar m�s de una solicitud
	 * que cumpla con estas caracteristicas se genera una excepci�n
	 * @param tipoSolicitud Tipo de solicitud a consultar
	 * @param estadoSolicitud Estado de la solicitud a consultar
	 * @param idPersonaFisica Identificador de la persona fisica
	 * @return Solicitud
	 */
	Solicitud obtenerSolicitudDePersonaPorTipoyEstado(Long idPersona, TipoPersonaEnum tipoPersona, TipoSolicitudEnum tipoSolicitud, EstadoSolicitudEnum estadoSolicitud) throws SolicitudException;
	
	/**
	 * Obtiene la solicitud con el estado y tipo proporcionados asociadas a una persona, 
	 * en caso de encontrar m�s de una solicitud
	 * que cumpla con estas caracteristicas se genera una excepci�n
	 * @param tipoSolicitud Tipo de solicitud a consultar
	 * @param estadoSolicitud Estado de la solicitud a consultar
	 * @param idPersonaFisica Identificador de la persona fisica
	 * @return Solicitud
	 */
	Solicitud obtenerSolicitudDePersonaPorTipoSolicitudTramiteyEstado(Long idPersona, TipoPersonaEnum tipoPersona, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite, EstadoSolicitudEnum estadoSolicitud) throws SolicitudException;

	/**
	 * Obtiene la solicitud con el estado y tipo proporcionados asociadas a una persona, 
	 * en caso de encontrar m�s de una solicitud
	 * que cumpla con estas caracteristicas se genera una excepci�n
	 * @param tipoSolicitud Tipo de solicitud a consultar
	 * @param estadoSolicitud Estado de la solicitud a consultar
	 * @param idPersonaFisica Identificador de la persona fisica
	 * @param convertXml define si tranformar o no el xml del detalleTramite
	 * @return Solicitud
	 */
	Solicitud obtenerSolicitudDePersonaPorTipoSolicitudTramiteyEstado(Long idPersona, TipoPersonaEnum tipoPersona, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite, EstadoSolicitudEnum estadoSolicitud, Boolean covertXml) throws SolicitudException;

	
	/**
	 * Obtiene las solicitudes en base a los filtros proporcionados
	 * 
	 * @author Hugo Martinez
	 * @Date 30/08/2012
	 * @param filtro
	 * @return List<Solicitud>
	 */
	DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltro(
			DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtro,
			boolean mostrarSolicInternet);
	
	/**
	 * Obtiene las solicitudes en base a los filtros proporcionados contemplando solo las solicitudes con los siguientes estatus:
	 * ATENDIDA, CANCELADA, RECHAZADA.
	 * Si no se proporciona un estado de solicitud solo se filtran las solicitudes que tengan alguno de estos 3 estados.
	 * 
	 * @author Hugo Martinez
	 * @Date 30/08/2012
	 * @param filtro
	 * @return List<Solicitud>
	 */
	DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltroParaPatron(DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtro);
	
	/**
	 * Actualiza la solicitud asi como todos sus tr�mites al estado concluido
	 * @param idSolicitud Identificador de la solicitud
	 */
	void actualizarSolicitudAEstatusConcluida(Long idSolicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException;
	
	/**
	 * Actualiza la solicitud asi como todos sus tr�mites al estado concluido
	 * @param solicitud Solicitud previamente consultada mediante el metodo consultar
	 */
	void actualizarSolicitudAEstatusConcluida(Solicitud solicitud)throws SolicitudNoEncontradaException, TramiteNoEncontradoException;
	
	/**
	 * Devuelve el tipo de documento solicitado, en caso de no existir lo genera y almacena en la base
	 * para ser recuperado posteriormente
	 * @param solicitud Previamente consultada mediante el metodo consultar(Solicitud solicitud)
	 * @param idTipoDocumento Tipo de documento requerido (ACUSE, AVISO)
	 * @return byte[]
	 */
	byte[] obtenerDocumento(Solicitud solicitud, Integer idTipoDocumento);
	
	
	/**
	 * Devuelve el tipo de documento solicitado, en caso de no existir lo genera y almacena en la base
	 * para ser recuperado posteriormente
	 * @param solicitud Previamente consultada mediante el metodo consultar(Solicitud solicitud)
	 * @param idTipoDocumento Tipo de documento requerido (ACUSE, AVISO)
	 * @return byte[]
	 */
	byte[] obtenerDocumento(Long idSolicitud, Integer idTipoDocumento);
	
	/**
	 * Devuelve el tipo de documento solicitado, en caso de no existir lo genera y almacena en la base
	 * para ser recuperado posteriormente
	 * @param solicitud Previamente consultada mediante el metodo consultar(Solicitud solicitud)
	 * @param idTipoDocumento Tipo de documento requerido (ACUSE, AVISO)
	 * @return byte[]
	 */
	byte[] obtenerDocumentoResultante(Long idSolicitud, Long idTramite, Integer idTipoDocumento);
	
	byte[] obtenerDocumentoResultante(Solicitud solicitud,Long idTramite, Integer idTipoDocumento);
	
	Map<String, Object> obtenerDocumentoResultanteNss(Long idSolicitud,
			Long idTramite, Integer idTipoDocumento);
	
	void guardarDocumentosResultantesPorSolicitud(Solicitud solicitud) throws SolicitudNoValidaException;
	/**
	 * Servicio que obtiene la informaci�n b�sica de las solicitudes de una
	 * persona, s�lo obtiene informaci�n acerca de la solicitud. Se puede
	 * filtrar por tipo y estado de la solicitud.
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
	 * Actualiza los estados de tr�mites y solicitud a un estado activo y en proceso respectivamente
	 * Publica en commet el inicio de procesamiento
	 * Encola la solicitud en el queue de solicitudes en proceso para su posterior procesamiento por el osb.
	 * @param idSolicitud
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	void enviarSolicitudAProceso(Solicitud solicitud, FirmaElectronica firmaElectronica) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException;
	
	/**
	 * Obtiene todas las solicitudes asociadas a un registro patronal sin importantar su estado.
	 * @return List<Solicitud>
	 */
	List<Solicitud> obtenerSolicitudesPorRegistroPatronal(Long idPatronSujetoObligado);
	
	/**
	 * Se asocia el tramite de alta con un registro patronal:
	 * 
	 * Se inserta la relaci�n entre DitPatronSujetoObligado y DitTramite mediante la inserci�n de
	 * un registro en DitTramitePatDujObligado.
	 * @param tramite
	 */
	void asociarTramiteAltaARegistroPatronal(Tramite tramite, Long idPatronSujetoObligado);
	
	/**
	 * Obtiene el estado de una solicitud
	 * 
	 * @param solicitud
	 * @return
	 */
	Solicitud obtenerEstados(Solicitud solicitud);

	/**
	 * Encapsula la actualizacion de los estados de los tramites de una
	 * solicitud y su envio a proceso
	 * 
	 * @param solicitud Solicitud
	 * @param firmaElectronica
	 * @throws TramiteNoEncontradoException
	 * @throws SolicitudNoEncontradaException
	 */
	void finalizarCapturaSolicitud(Solicitud solicitud, FirmaElectronica firmaElectronica) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException;

	/**
	 * Metodo que obtiene los datos de una subdelegacion a partir de su id
	 * @param idSubdelegacion
	 * @return
	 */
	Subdelegacion getDatosSubdelegacion(Long idSubdelegacion);
	
	/**
     * Metodo que obtiene los datos de una subdelegacion a partir de su id
     * @param idSubdelegacion
     * @return
     */
	Delegacion getDatosDelegacion(Long idDelegacion);
	
	/**
	 * V?lida que la solicitud contenga el estado o conjunto de estados v?lidos proporcionados
	 * @param solicitud
	 * @param estadoValido
	 * @throws SolicitudNoValidaException
	 */
	void validarEstadoProcesamiento(Solicitud solicitud, List<EstadoSolicitudEnum> estadosValidos) throws SolicitudNoValidaException;
	
	/**
	 * Actualiza la solicitud con el mensaje de error en el campo observacion y se retorna al estado registrado.
	 * @param idSolicitud Identificador delta para la solicitud
	 * @param mensajeError Mensaje de error
	 */
	void reportarErrorProcesamiento(Long idSolicitud, String folio, String mensajeError);
	
	/**
	 * Colocal el mensaje proporcionado en el campo ref_observacion de la base de datos
	 * @param folio
	 * @param mensajeError
	 */
	void actualizarMensajeNotificacion(String folio, String mensajeError);
	
	/**
	 * Serivicio que checa si existe una solicitud en espec�fico. Requiere ya
	 * sea del folio o id de la solicitud, primero realiza la b�squeda de la
	 * solicitud por folio, si no se cuenta con folio busca por id
	 * 
	 * @param solicitud
	 * @return
	 * @throws SolicitudException 
	 */
	boolean existeSolicitud(Solicitud solicitud) throws SolicitudException;
	
	/**
	 * Obtiene el documento guardad por tipo  y id del tramite
	 * @param idTramite
	 * @param idDocumentoPorTipo
	 * @return
	 */
	Object getDocumentoPorTipoIdTramite(Long idTramite, Long idDocumentoPorTipo);
	/**
	 * Guarda el documento resultante asociado al tr�mite
	 * @param idTramite
	 * @param idDocumentoTipo
	 * @param bytes
	 */
	void actualizarDocumentosTramite(Long idTramite, Long idDocumentoTipo, byte[] bytes);
	/**
	 * Metodo para agregar a una persona a un tramite, creara una relacion
	 * en la tabla tramitee persona fisica
	 * @param idTramite
	 * @param idPersona
	 * @throws TramiteNoEncontradoException
	 */
	void agregarPersonaATramite(Long idTramite, Long idPersona) throws TramiteNoEncontradoException;

	/**
	 * Metodo para obtener las solicituds sin importar el estado de un grupo familiar
	 * @param nss
	 * @param idOrigenSolicitud - puede ir nulo y no se comparara el origen
	 * @return
	 */
	List<Solicitud> getSolicitudesGrupoFamiliar(String nss, Long idOrigenSolicitud);
	/**
	 * Metodo para obtener solo las solicitudes activas del grupo familiar
	 * @param nss
	 * @param idOrigenSolicitud - puede ir nulo y no se conparara el origen
	 * @return
	 */
	List<Solicitud> getSolicitudesActivasGrupoFamiliar(String nss, Long idOrigenSolicitud);
	
	/**
	 * Obtiene las solicitudes de un integrante del grupo famiiar
	 * @param nss
	 * @param idIntegrante
	 * @param idOrigenSolicitud - puede ir nulo y no se comprara el origen
	 * @return
	 */
	List<Solicitud> getSolicitudesGrupoFamiliarEIntegrante(String nss, Long idIntegrante, Long idOrigenSolicitud);
	/**
	 * Obtiene solo las solicitudes activas de un integrante dentro del grupo familiar
	 * @param nss
	 * @param idIntegrante
	 * @param numeroResultados
	 * @param idOrigenSolicitud
	 * @return
	 */
	List<Solicitud> getSolicitudeActivasGrupoFamiliarEIntegrante(String nss, Long idIntegrante, Long numeroResultados, Long idOrigenSolicitud);
	
	/**
	 * Obtiene la ultima solicitud de registro de derechohabiente en un grupo familiar
	 * @param idAsignacionNss
	 * @param idOrigenSolicitud
	 * @return
	 */
	Solicitud getUltimaSolicitudActivaDeRegistroPorIdAsignacionNss(Long idAsignacionNss);
	
	/**
	 * Obtiene las solicitudes y los tramites de cualquier personas f�sicas
	 * 
	 * @param idPersona
	 * @param tipo
	 * @param tipoSolicitud
	 * @param estadoSolicitud
	 * @param maximoResultados
	 * @return <ul>
	 * 			<li>Lista de solicitudes encontradas.</li>
	 * 			<li>En caso de error o no encontrar solicitudes, devuelve una lista vac&iacute;a</li>
	 * 		   </ul> 
	 */
	public List<Solicitud> obtenerSolicitudPorPersona(Long idPersona,TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud, EstadoSolicitudEnum estadoSolicitud, Boolean maximoResultados);
	
	
	/**
	 * Metodo para obtener las solicitudes de un grupo familiar
	 * @param String nss - el nss del grupo familiar
	 * @param List<Long> estadosSolicitud- estados de la solicitud a buscas , puede ser nulo y no se comparara el estado 
	 * nulo se buscaran todas las solicitudes del grupo familiar sin importar las persona afectada
	 * @param Long maxResult - El numero de resultados a obtener, puede ir nulo
	 * @param Long idOrigenSolicitud - El origen de la solicitud, puede ser nulo, en caso de ser nulo obtendra tanto solicitudes
	 * realizadas por internet como en ventanilla
	 */
	List<Solicitud> getSolicitudesbyNSSEstadosOrigen(String nss,
			List<Long> estadosSolicitud,  Long idOrigenSolicitud, Long maxResult);
	
	List<Solicitud> obtenerSolicitudes();
	
	void obtenerCifrasSolicitudProceso();
	
	
	/**
	 * Obtene todas las razones de resultado
	 * 
	 * @return Lista de razones
	 */
	public List<RazonResultado> obtenerRazonesResultado();
	
	/**
	 * Obtene las razones para los id's proporcionados
	 * 
	 * @param idRazones Lista de id's de razones
	 * @return Lista de razones
	 */
	public List<RazonResultado> obtenerRazonesResultado(List<Long> idRazones) throws Exception;

	
	
	/**
	 * Obtiene las solicitudes y los tramites de cualquier personas f�sicas
	 * 
	 * @param idPersona
	 * @param tipo
	 * @param tiposSolicitud List<Long>
	 * @param estadosSolicitud List<Long>
	 * @param maximoResultados
	 * @return <ul>
	 * 			<li>Lista de solicitudes encontradas.</li>
	 * 			<li>En caso de error o no encontrar solicitudes, devuelve una lista vac&iacute;a</li>
	 * 		   </ul> 
	 */
	public List<Solicitud> obtenerSolicitudPorPersona(Long idPersona, TipoPersonaFiscal tipo, 
			List<Long> tiposSolicitud, List<Long> estadosSolicitud, Boolean maximoResultados);

	Tramite actualizarXmlTramite(Tramite tramite) throws TramiteNoEncontradoException, IllegalArgumentException;
	
	mx.gob.imss.digital.modelo.tramite.Tramite actualizarXmlTramite(
			mx.gob.imss.digital.modelo.tramite.Tramite tramite)
			throws TramiteNoEncontradoException, IllegalArgumentException;
	
	void actualizaAConcluida(Solicitud solicitud) throws SolicitudNoEncontradaException;
	void cancelarSolicitud(Long idSolicitud, Long idRazonRechazo, Long idRazonCancelacion, String usuarioCancelacion, String observacionesCancelacion) throws SolicitudException;
	void actualizarUsuarioSolicitud(Solicitud solicitud) throws IllegalArgumentException;
	void actualizaTipoTramite(Long idTramite, Long tipoTramite);
	
	/**
	 * Se agrega listado de documentos resultantes a cada uno de los tramites de una solicitud
	 * @param solicitud
	 * @return
	 */
	Solicitud agregarListadosDocumentosATramites(Solicitud solicitud);
	
	void actualizaAConcluidaDH(Solicitud solicitud)
			throws SolicitudNoEncontradaException;

	void finalizarSolicitud(Solicitud solicitud, FirmaElectronica firmaElectronica)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException;
	
	/**
	 * Metodo que asocia una persona a la solicitud
	 * @param cveIdSolicitud
	 * @param cveIdPersonaInteresada
	 * @param cveTipoPersonaInteres
	 * @throws SolicitudNoEncontradaException
	 */
	void insertaPersonaInteresadaSolicitud(Long cveIdSolicitud, Long cveIdPersonaInteresada, Long cveTipoPersonaInteres) 
			throws SolicitudNoEncontradaException, IllegalArgumentException;
	
	String getHomoclaveSolicitud(String folioSolicitud);
	
	String getHomoclaveSolicitud(Long idSolicitud);


	Solicitud obtenerPorFolioSolicitud(String folioSolicitud);
	
	List<Long> encontrarSolicitudesPorPersonaYEstados(Long idPersona, List<Long> idsEstados,
            Long idTipoSolicitud);
			
	Long obtenerIdPersonaInteresada(Long solicitudId);
	
	List<Long> obtenerTramitesNoCanceladosPorPersonaYTipo(Long idPersona,
            Long tipoSolicitud);

	List<Long> encontrarSolicitudesPorPersonaYEstadosDeTramite(Long idPersona, List<Long> idsEstadoTramite,
            Long tipoSolicitud);

	List<Map<String, String>> buscaSolicitudesHsqlRemoto(Map<String, String> datosBusqueda, Integer primerResultado,Integer resultadosPorPagina);		
	Long buscaConteoSolicitudesHsqlRemoto(Map<String, String> datosBusqueda);

    List<Long> encontrarTramitesPorPersonaYEstadosDeTramite(Long idPersona, List<Long> idsEstadoTramite);
	
	 public void actualizarTipoTramite(String Tramite, String idSolicitud, String TipoNSS);
    
    public void actualizarTipoNSS(String nss, String TipoNSS);

	// guardar & genera el detalle de tramite(s) en XML.
	/**
	 * Actualiza los tramites que se envian en la solicitud par indicar
	 * Que la solicitud ya esta siendo procesada por el OSB.
	 * 
	 * @param solicitud
	 * @return
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	Solicitud actualizarTramitesMarcaOSB(Solicitud solicitud)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException;
    

}

