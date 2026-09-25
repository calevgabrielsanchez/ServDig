/**
*
*
**/
package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.solicitud;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: SolicitudServiceBusinessLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.business.solicitud
 *  @Fecha: 08:46:44
 */
@Local
public interface SolicitudServiceBusinessLocal {
	
	
	/**
	 * Crea una nueva solicitud sin tramites asociados
	 * @author Hugo Armando Martínez Chamónica
	 * @param solicitud
	 * 
	 */
	Solicitud crearNuevaSolicitud(Solicitud solicitud);
	
	
	
	/**
	 * 
	 * @author Jorge García
	 * @Date 13/06/2012
	 * @param tipoSolicitud
	 * @param estadoSolicitud
	 * @param usuario
	 * @param tipoTramite
	 * @param estadoTramite
	 * @param sujetoObligado
	 * @throws GestionPatronalBusinessException
	 */
	Solicitud generarSolicitud(TipoSolicitudEnum tipoSolicitud, EstadoSolicitudEnum estadoSolicitud, Usuario usuario, TipoTramiteEnum tipoTramite, EstadoTramiteEnum estadoTramite, SujetoObligado sujetoObligado, boolean reintentoRPC, boolean rpcInvalido) throws GestionPatronalBusinessException;
	
	/**
	 * Actualiza la solicitud y los trámites asociados a la misma en base a los identificadores
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
	void cancelarSolicitud(Long idSolicitud) throws SolicitudException ;
	
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
	 * Indica si existen trámites de clasificación de los tipos
	 * @author Hugo Martinez
	 * @Date 05/07/2012
	 * @param cveIdPatronSujetoObligado
	 * @return
	 */
	boolean existeTramitesClasificacionActivos(Long cveIdPatronSujetoObligadoole, boolean esTramitador);
	
	/**
	 * Wrapper
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param solicitud
	 */
	void actualizarTramites(Solicitud solicitud);
	
	/**
	 * Obtiene la solicitud activa asociada a un patron, es decir con estatus de registrada.
	 * Si no hay solicitud activa retorna null
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return
	 */
	Solicitud obtenerSolicitudEnCapturaDeSujetoObligado(Long idPatronSujetoObligado, TipoSolicitudEnum tipoSolicitud,  TipoTramiteEnum tipoTramite);
	
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
	 * Actualiza los estados de la solicitud y los tramites asociados a la misma.
	 * Para un correcto uso de este metodo se debe consultar la solicitud y actualizar 
	 * dentro de la misma únicamente los estatus del (los) tramite(s) y la solicitud
	 * acorde a los requerimientos de negocio.
	 * @author Hugo Martinez
	 * @Date 30/07/2012
	 * @param solicitud
	 */
	void actualizarEstados(Solicitud solicitud);
	
	/**
	 * Obtiene el detalle del tipo de trámite con la finalidad de mostrar una guia detallada o rápida
	 * del trámite y los documentos requeridos para su conclusión.
	 * @author Hugo Martinez
	 * @Date 30/07/2012
	 * @param id
	 * @return
	 */
	TipoTramite consultarTipoTramite(Integer id);
	
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
	Solicitud obtenerSolicitudEnProcesoPorSujetoObligado(Long cveIdSujetoObligado, TipoSolicitudEnum tipoSolicitud,  TipoTramiteEnum tipoTramite);
	
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
	 * 
	 * @author Hugo Martinez
	 * @Date 10/08/2012
	 * @param idSolicitud
	 * @return
	 */
	Solicitud consultarSolicitudPorId(Long idSolicitud);
	
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
	 * Obtiene el detalle de la solicitud y sus trámites y además la información del sujeto obligado
	 * relacionado con la solicitud (persona fisica o moral y/o rp)
	 * @author Hugo Martinez
	 * @Date 04/09/2012
	 * @param idSolicitud
	 * @return Solicitud
	 */
	Solicitud consultarDetalleSolicitudPorIdentificador(Long idSolicitud);
	
	/**
	 * Actualiza los estados de la solicitud y del(los) trámite(s)
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
	Solicitud obtenerSolicitudPendienteAsignacion(SujetoObligado sujetoObligado, TipoSolicitudEnum tipoSolicitud);
	
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
	 * Actualiza la fecha de conclusión a todos los trámites 
	 * asociados a una solicitud
	 * @author Hugo Martinez
	 * @Date 02/10/2012
	 * @param idSolicitud
	 * @param fechaConclusion
	 */
	void actualizarFechaDeConclusionDeTramites(Long idSolicitud, Date fechaConclusion);
	
	/**
	 * Se obtiene el tipo de trámite
	 * @author Hugo Martinez
	 * @Date 05/10/2012
	 * @param codigo
	 * @return TipoTramite
	 */
	TipoTramite obtenerTipoTramite(Long codigo);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 16/10/2012
	 * @param solicitud
	 */
	Solicitud publicarDocumentosDeSolicitud(Solicitud solicitud);
	
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
	 * Actualiza la información del trámite de alta patronal en la solicitud almacenada en la base.
	 * @author Hugo Martinez
	 * @Date 13/02/2013
	 * @param solicitud
	 */
	Solicitud actualizarTramiteAlta(Solicitud solicitud, SujetoObligado sujetoTramite);
	
	/**
	 * Actualiza la información general de la solicitud, no incluye los trámites
	 * @author Hugo Martinez
	 * @Date 23/10/2012
	 * @param solicitud
	 */
	void actualizarDatosGeneralesDeSolicitud(Solicitud solicitud);
	
	void generarDocumentos(Solicitud solicitud) throws Exception;
}
