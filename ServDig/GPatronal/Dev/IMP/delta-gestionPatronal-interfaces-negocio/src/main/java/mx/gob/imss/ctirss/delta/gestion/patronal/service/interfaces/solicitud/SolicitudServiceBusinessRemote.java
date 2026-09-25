/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Mart�nez Cham�nica
 *  @Proyecto: delta
 *  @Archivo: SolicitudServiceRemote.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud
 *  @Fecha: 10:12:41
 */
@Remote
public interface SolicitudServiceBusinessRemote {
	
	
	/**
	 * Crea una nueva solicitud
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param solicitud
	 * 
	 */
	Solicitud crearNuevaSolicitud(Solicitud solicitud);
	
	
	/**
	 * @author Jorge Garc�a
	 * @param tipoSolicitud
	 * @param estadoSolicitud
	 * @param usuario
	 * @param tipoTramite
	 * @param enEsperaTramitador
	 * @param sujetoObligado
	 */
	Solicitud generarSolicitud(TipoSolicitudEnum tipoSolicitud, EstadoSolicitudEnum estadoSolicitud, Usuario usuario, TipoTramiteEnum tipoTramite, EstadoTramiteEnum enEsperaTramitador, SujetoObligado sujetoObligado, boolean reintentoRPC, boolean rpcInvalido) throws GestionPatronalBusinessException;
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 14/06/2012
	 * @param idSolicitud
	 * @return Solicitud
	 */
	Solicitud consultarSolicitudPorId(Long idSolicitud);
	
	/**
	 * Actualiza la solicitud y los tr�mites asociados a la misma en base a los identificadores
	 * @author Hugo Martinez
	 * @Date 15/06/2012
	 * @param solicitud
	 * @return Solicitud
	 */
	Solicitud actualizarSolicitud(Solicitud solicitud, EstadoTramiteEnum estadoTramite, SujetoObligado sujetoObligado);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param idSolicitud
	 */
	void cancelarSolicitud(Long idSolicitud) throws SolicitudException;
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 19/06/2012
	 * @param tipo
	 * @param sujetoObligado
	 * @throws GestionPatronalBusinessException
	 */
	void validaTipoTramiteUnico(TipoTramiteEnum tipo,
			SujetoObligado sujetoObligado)
			throws GestionPatronalBusinessException;
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 19/06/2012
	 * @param tipos
	 * @param sujetoObligado
	 * @throws GestionPatronalBusinessException
	 */
	void validaTiposTramiteUnicos(List<TipoTramiteEnum> tipos,
			SujetoObligado sujetoObligado)
			throws GestionPatronalBusinessException;
	
	/**
	 * Indica si existen tr�mites de clasificaci�n de los tipos;
	 * TipoTramiteEnum.CLASIFICACION_D,
	 * TipoTramiteEnum.CLASIFICACION_E,
	 * TipoTramiteEnum.CLASIFICACION_F,
	 * TipoTramiteEnum.CLASIFICACION_J,
	 * TipoTramiteEnum.CLASIFICACION_K,
	 * TipoTramiteEnum.CLASIFICACION_L,
	 * TipoTramiteEnum.CLASIFICACION_M,
	 * TipoTramiteEnum.CLASIFICACION_N.
	 * @author Hugo Martinez
	 * @Date 05/07/2012
	 * @param cveIdPatronSujetoObligado
	 * @return
	 */
	boolean existeTramitesClasificacionActivos(Long cveIdPatronSujetoObligado, boolean esTramitador);
	
	/**
	 * Wrapper
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param solicitud
	 */
	void actualizarTramites(Solicitud solicitud);

	//OPERACIONES DEL NUEVO FLUJO

	
	/**
	 * Obtiene la solicitud activa (en captura/activa/procesando en backoffice /procesando en ventanilla) ya sea de una persona o de un sujeto obligado
	 * en base a los identificadores respectivos, el tipo de persona y el rol del usuario firmado.
	 * 
	 * Si el identificador de sujeto obligado es nulo, entonces se busca la solicitud por persona
	 * ya sea fisica o moral en base a su identificador.
	 * 
	 * @author Hugo Martinez
	 * @Date 26/07/2012
	 * @param sujetoObligado
	 * @param Usuario usuario
	 * @param tipoSolicitud
	 * @return Solicitud
	 */
	Solicitud obtenerSolicitudActiva(SujetoObligado sujetoObligado, TipoSolicitudEnum tipoSolicitud, Usuario usuario, TipoTramiteEnum tipoTramite);

	/**
	 * Obtiene la solicitud activa (en captura/activa/procesando en backoffice /procesando en ventanilla) ya sea de una persona o de un sujeto obligado
	 * en base a los identificadores respectivos, el tipo de persona y el rol del usuario firmado.
	 * 
	 * Si el identificador de sujeto obligado es nulo, entonces se busca la solicitud por persona
	 * ya sea fisica o moral en base a su identificador.
	 * 
	 * @author Hugo Martinez
	 * @Date 26/07/2012
	 * @param sujetoObligado
	 * @return
	 */
	Solicitud obtenerSolicitudEnCaptura(SujetoObligado sujetoObligado, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite);
	
	/**
	 * Obtiene la solicitud en estado de captura asociada a un patron, es decir con estatus de registrada.
	 * Si no hay solicitud activa retorna null
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return
	 */
	Solicitud obtenerSolicitudEnCapturaPorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud);

	/**
	 * Obtiene el detalle del tipo de tr�mite con la finalidad de mostrar una guia detallada o r�pida
	 * del tr�mite y los documentos requeridos para su conclusi�n.
	 * @author Hugo Martinez
	 * @Date 30/07/2012
	 * @param id
	 * @return TipoTramite
	 */
	TipoTramite consultarTipoTramite(Integer id);
	
	
	/**
	 * Obtiene la solicitud activa asociada a un patron, es decir con estatus de registrada.
	 * Si no hay solicitud activa retorna null
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return
	 */
	Solicitud obtenerSolicitudEnCapturaDeSujetoObligado(Long idPatronSujetoObligado, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite);

	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 30/07/2012
	 * @param sujetoTramite
	 * @return
	 */
	Tramite obtenerTramiteDeSolicitudActivaPorTipo(SujetoObligado sujetoTramite, TipoTramiteEnum tipoTramite, Usuario usuario);
	
	/**
	 * Obtiene la solicitud con estatus de En Proceso ya sea de una persona o de un sujeto obligado
	 * en base a los identificadores respectivos y el tipo de persona.
	 * 
	 * Si el identificador de sujeto obligado es nulo, entonces se busca la solicitud por persona
	 * ya sea fisica o moral en base a su identificador.
	 * 
	 * @author Hugo Martinez
	 * @Date 26/07/2012
	 * @param sujetoObligado
	 * @return Solicitud
	 */
	Solicitud obtenerSolicitudEnProceso(SujetoObligado sujetoObligado, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite);
	
	/**
	 * Obtiene las solicitudes con estatus de iniciada
	 * @author Hugo Martinez
	 * @Date 07/08/2012
	 * @param sujetoObligado
	 * @return List<Solicitud>
	 */
	List<Solicitud> listarSolicitudesEnCaptura(SujetoObligado sujetoObligado);
	
	/**
	 * Obtiene las solicitudes con estatus: PENDIENTE_AUTORIZACION, EDICION_VENTANILLA,
	 * EDICION_BACKOFFICE.
	 * @author Hugo Martinez
	 * @Date 07/08/2012
	 * @param sujetoObligado
	 * @return List<Solicitud>
	 */
	List<Solicitud> listarSolicitudesEnProceso(SujetoObligado sujetoObligado, boolean esTramitador);
	
	/**
	 * Determina el tipo de solicitud en base al tipo de tramite
	 * @author Hugo Martinez
	 * @Date 09/08/2012
	 * @param tipoTramite
	 * @return TipoSolicitudEnum
	 */
	TipoSolicitudEnum obtenerTipoSolicitudEnBaseAlTipoTramite(TipoTramiteEnum tipoTramite);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 10/08/2012
	 * @param cveIdSujetoObligado
	 * @param tipoSolicitud
	 * @return
	 */
	Solicitud obtenerSolicitudEnProcesoPorSujetoObligado(Long cveIdSujetoObligado, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 10/08/2012
	 * @param idPersona
	 * @param tipo
	 * @param tipoSolicitud
	 * @return
	 */
	Solicitud obtenerSolicitudEnProcesoPorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud);
	
	
	/**
	 * Obtiene las solicitudes en base a los filtros proporcionados
	 * 
	 * @author Hugo Martinez
	 * @Date 30/08/2012
	 * @param filtro
	 * @return List<Solicitud>
	 */
	DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltro(DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtro);
	
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
	 * Obtiene el detalle de la solicitud y sus tr�mites y adem�s la informaci�n del sujeto obligado
	 * relacionado con la solicitud (persona fisica o moral y/o rp)
	 * @author Hugo Martinez
	 * @Date 04/09/2012
	 * @param idSolicitud
	 * @return Solicitud
	 */
	Solicitud consultarDetalleSolicitudPorIdentificador(Long idSolicitud);
	
	/**
	 * Actualiza los estados de la solicitud y del(los) tr�mite(s)
	 * @author Hugo Martinez
	 * @Date 05/09/2012
	 * @param solicitud
	 */
	void actualizarEstatus(Solicitud solicitud);
	
	/**
	 * Obtiene la solicitud con estatus PARA_PROCESAR_EN_BACK_OFFICE
	 * o POR_PRESENTARSE_EN_VENTANILLA para el tipo de solicitud
	 * proporcionado
	 * @author Hugo Martinez
	 * @Date 06/09/2012
	 * @param sujetoObligado
	 * @param tipoSolicitud
	 * @return Solicitud
	 */
	Solicitud obtenerSolicitudPendienteDeAsignar(SujetoObligado sujetoObligado, TipoSolicitudEnum tipoSolicitud);
	
	/**
	 * Guarda los documentos pdf enviados en la solicitud
	 * solo se contemplan acuse y aviso
	 * @author Hugo Martinez
	 * @Date 13/09/2012
	 * @param solicitud
	 */
	void actualizarDocumentosDeSolicitud(Solicitud solicitud);
	
	/**
	 * Obtiene la solicitud  con el folio proporcionado
	 * @author Hugo Martinez
	 * @Date 13/09/2012
	 * @param folio
	 * @return Solicitud
	 */
	Solicitud consultarSolicitudPorFolio(String folio);
	
	/**
	 * Obtiene los documentos de acuse y comprobante de tr�mite y los agrega al objeto de solicitud proporcionado
	 * @author Hugo Martinez
	 * @Date 16/10/2012
	 * @param solicitud Objeto solicitud, el solicitudId es requerido
	 */
	Solicitud publicarDocumentosDeSolicitud(Solicitud solicitud);
	
	/**
	 * Obtiene la solicitud anterior por tipo de solicitud en base 
	 * al identificador proporcionado
	 * @author Hugo Martinez
	 * @Date 18/10/2012
	 * @param solicitud
	 * @return Solicitud 
	 */
	Solicitud obtenerSolicitudAnteriorPorTipo(Solicitud solicitud);
	
	/**
	 * Actualiza la informaci�n general de la solicitud, no incluye los tr�mites
	 * @author Hugo Martinez
	 * @Date 23/10/2012
	 * @param solicitud
	 */
	void actualizarDatosGeneralesDeSolicitud(Solicitud solicitud);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 05/12/2012
	 * @param sujetoObligado
	 * @return
	 */
	List<Solicitud> listarSolicitudesGlobalesEnProceso(Long cveIdSubdelegacion);

	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 06/12/2012
	 * @param solicitud
	 * @param tipoTramite
	 * @return
	 */
	Tramite obtenerTramitePorTipo(Solicitud solicitud, TipoTramiteEnum tipoTramite);
	
	/**
	 * Actualiza la informaci�n del tr�mite de alta patronal en la solicitud almacenada en la base.
	 * @author Hugo Martinez
	 * @Date 13/02/2013
	 * @param solicitud
	 */
	Solicitud actualizarTramiteAlta(Solicitud solicitud, SujetoObligado sujetoTramite);

	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 11/03/2013
	 * @param tipo
	 * @param estado
	 * @param sujetoObligado
	 * @return
	 */
	TramiteSujetoObligado construirTramite(TipoTramiteEnum tipo,
			EstadoTramiteEnum estado, SujetoObligado sujetoObligado);
	
	/**
	 * Agrega el tramite proporcionado a la solcitud.
	 * @author Hugo Martinez
	 * @Date 11/03/2013
	 * @param cveIdSolicitud
	 * @param tramite
	 */
	void agregarTramiteASolicitud(Long cveIdSolicitud, Tramite tramite);

	/**
	 * Obtiene el detalle de la solicitud y sus tr�mites y adem�s la informaci�n
	 * del sujeto obligado relacionado con la solicitud (persona fisica o moral
	 * y/o rp)
	 * 
	 * @author Marco S�nchez
	 * @Date 28/06/2013
	 * @param idSolicitud
	 * @return Solicitud
	 * @throws SolicitudNoEncontradaException
	 */
	Solicitud consultarDetalleSolicitudPorFolio(String folio)
			throws SolicitudNoEncontradaException;

	void cancelarSolicitudPorFolio(String folio)throws SolicitudException;

	void finalizarCapturaSolicitudClasificacion(Solicitud solicitud,
			EstadoTramiteEnum estadoTramite, SujetoObligado sujetoObligado,
			FirmaElectronica firmaElectronica) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException;
	
	/**
	 * Genera todos los documentos asociados a los distintos tr�mites contenidos en la solicitud cuyo folio
	 * es proporcionado como par�metro
	 * 
	 * @param folio
	 */
	void generarDocumentos(String folio) throws Exception;
	
	/**
	 * Localiza y retorna la solicitud de alta asociada al nrp proporcionado
	 * @param nrp
	 * @return
	 */
	Solicitud obtenerSolicitudAltaPatronalPorNRP(Long idPatron);

	/**
	 * Obtiene la lista de documentos resultantes para un tipo de tr�mite
	 * en espec�fico
	 * 
	 * @param idTipoTramite
	 * @return
	 */
	List<DocumentoPorTipo> obtenerDocumentosResultantesPorTipoTramite(
			long idTipoTramite);
	
	void procesarSolicitudContactoCentroTrabajo(Long idSolicitud, FirmaElectronica firma)
		throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException;
	
	void finalizarSolicitudContactoCentroTrabajo(Long idSolicitud);
	
	/**
	 * Actualiza el tramite contenido en la solicitud en base al identificador del tramite
	 * @throws TramiteNoEncontradoException si el tramite no es encontrado
	 * @param idSolicitud
	 * @param tramite
	 */
	void actualizarTramiteDeAltaEnSolicitud(Tramite tramite) throws SolicitudNoEncontradaException, TramiteNoEncontradoException;


	/**
	 * Valida si la solicitud ya esta en proceso para terminar el tramite 
	 * @param solicitud
	 * @param estadoOSB 
	 */
	Solicitud validaEstadoSolicitudEnOSB(Solicitud solicitud) throws GestionPatronalBusinessException;

	/**
	 * Agrega marca a la solicitud para indicar que ya esta en proceso para terminar el tramite 
	 * @param solicitud
	 * @param estadoOSB 
	 */
	Solicitud actualizarTramiteAltaEnProcesoOSB(Solicitud solicitud, int estadoOSB);


	/**
	 * Inserta solicitud de tramite de alta patronal para VNDI
	 * @param idSolicitud
	 * @throws GestionPatronalBusinessException
	 */
	void recibeApVNDI(Long idSolicitud) throws GestionPatronalBusinessException;	

	
	/**
	 * Inserta confirmacion de termino de solicitud de tramite de alta patronal para VNDI
	 * @param idSolicitud
	 * @throws GestionPatronalBusinessException
	 */
	void confirmaApVNDI(Long idSolicitud) throws GestionPatronalBusinessException;		
	
}
