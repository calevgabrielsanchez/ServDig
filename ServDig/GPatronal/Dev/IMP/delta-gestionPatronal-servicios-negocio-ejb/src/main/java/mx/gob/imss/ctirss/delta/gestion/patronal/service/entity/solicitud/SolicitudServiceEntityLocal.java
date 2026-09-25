/**
*
*
**/
package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.PropietarioSolicitudUtil;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;


/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Mart�nez Cham�nica
 *  @Proyecto: delta
 *  @Archivo: SolicitudServiceEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud
 *  @Fecha: 10:26:49
 */
@Local
public interface SolicitudServiceEntityLocal {
	
	Solicitud consultarSolicitud(Long idSolicitud, TipoPersonaFiscal tipoPersona);
	
	/**
	 * Crea una nueva solicitud
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param model
	 * void
	 */
	void insertarSolicitud(Solicitud model);
	
	/**
	 * 
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param idSolicitud
	 * @param idEstadoSolicitud
	 * void
	 */
	void actualizarEstadoSolicitud(Long idSolicitud, Long idEstadoSolicitud);
	
	//******************************** Metodos del nuevo flujo
	
	/**
	 * Obtiene la solicitud con estatus de registrada.
	 * Se parte del supuesto de que �nicamente existe una solicitud activa(status: registrada)
	 * a la vez por sujeto obligado
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return
	 */
	Solicitud obtenerSolicitudEnCaptura(Long idPatronSujetoObligado, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite);
	
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPersona
	 * @param tipo
	 * @return
	 */
	Solicitud obtenerSolicitudEnCapturaPorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud);
	
	/**
	 * Obtiene la solicitud con estatus de registrada.
	 * Se parte del supuesto de que �nicamente existe una solicitud activa(status: registrada)
	 * a la vez por sujeto obligado
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return
	 */
	Solicitud obtenerSolicitudEnProceso(Long idPatronSujetoObligado, TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite);
	
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPersona
	 * @param tipo
	 * @return
	 */
	Solicitud obtenerSolicitudEnProcesoPorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud);
	
	
	/**
	 * Obtiene las solicitud con estatus de INICIADA 
	 * asociadas al patron sujeto obligado proporcionado.
	 * 
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return
	 */
	List<Solicitud> listarSolicitudesActivasPorPatron(Long idPatronSujetoObligado);
	
	/**
	 * Obtiene las solicitud con estatus de INICIADA 
	 * asociadas al patron sujeto obligado proporcionado.
	 * 
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return List<Solicitud>
	 */
	List<Solicitud> listarSolicitudesActivasPorPersona(Long idPersona, TipoPersonaFiscal tipo);
	
	/**
	 * Obtiene las solicitud con estatus de INICIADA 
	 * asociadas al patron sujeto obligado proporcionado.
	 * 
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return
	 */
	List<Solicitud> listarSolicitudesEnProcesoPorPatron(Long idPatronSujetoObligado, boolean esTramitador);
	
	/**
	 * Obtiene las solicitud con estatus de INICIADA 
	 * asociadas al patron sujeto obligado proporcionado.
	 * 
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return List<Solicitud>
	 */
	List<Solicitud> listarSolicitudesEnProcesoPorPersona(Long idPersona, TipoPersonaFiscal tipo);
	
	/**
	 * Elimina un tr�mite en base a su identificador
	 * @author Hugo Martinez
	 * @Date 27/08/2012
	 * @param idTramite
	 */
	void eliminarTramite(Long idTramite);
	
	/**
	 * Obtiene las solicitudes en base a los filtros seleccionados
	 * @author Hugo Martinez
	 * @Date 30/08/2012
	 * @param filtros
	 * @return List<Solicitud>
	 */
	DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltro(DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtros);
	
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
	 * Obtiene el detalle de la solicitud, patron y rp al que pertenece
	 * dicha solicitud
	 * @author Hugo Martinez
	 * @Date 04/09/2012
	 * @param idSolicitud
	 * @return Solicitud
	 */
	Solicitud consultarDetalle(Long idSolicitud);
	
	/**
	 * Obtiene la solicitud con estatus de registrada.
	 * Se parte del supuesto de que �nicamente existe una solicitud activa(status: registrada)
	 * a la vez por sujeto obligado
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return
	 */
	Solicitud obtenerSolicitudPendienteDeAsignarPorPatron(Long idPatronSujetoObligado, TipoSolicitudEnum tipoSolicitud);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPersona
	 * @param tipo
	 * @return
	 */
	Solicitud obtenerSolicitudPendienteDeAsignarPorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud);
	
	/**
	 * Obtiene la solicitud con estatus de POR PRESENTARSE EN VENTANILLA o PARA PROCESAR EN BACKOFFICE.
	 * Se parte del supuesto de que �nicamente existe una solicitud PENDIENTE()
	 * a la vez por sujeto obligado
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return Solicitud
	 */
	Solicitud obtenerSolicitudPendientePorSujetoObligado(Long idPatronSujetoObligado, TipoSolicitudEnum tipoSolicitud);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 12/09/2012
	 * @param idPersona
	 * @param tipo
	 * @param tipoSolicitud
	 * @return
	 */
	Solicitud obtenerSolicitudPendientePorPersona(Long idPersona, TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud);
	
	/**
	 * Actualiza los documentos de acuse y comprobante de tr�mite
	 * @author Hugo Martinez
	 * @Date 13/09/2012
	 * @param solicitud
	 */
	void actualizarDocumentos(Solicitud solicitud);
	
	/**
	 * Obtiene la solicitud  con el folio proporcionado
	 * @author Hugo Martinez
	 * @Date 13/09/2012
	 * @param folio
	 * @return Solicitud
	 */
	Solicitud consultarSolicitudPorFolio(String folio);
	
	/**
	 * Actualiza la fecha de conclusi�n de todos los tr�mite
	 * de la solicitud
	 * @author Hugo Martinez
	 * @Date 02/10/2012
	 * @param idSolicitud
	 * @param fechaConclusion
	 */
	void actualizarFechaConclusionDeTramites(Long idSolicitud, Date fechaConclusion);
	
	/**
	 * Obtiene la infoaci�n del tipo de tr�mite almacenada en la base de datos
	 * @author Hugo Martinez
	 * @Date 05/10/2012
	 * @param codigo
	 * @return TipoTramite
	 */
	TipoTramite consultarDetalleTipoTramite(Long codigo);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 16/10/2012
	 * @param solicitud
	 */
	Solicitud publicarDocumentos(Solicitud solicitud);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 18/10/2012
	 * @param solicitud
	 * @return
	 */
	Solicitud obtenerSolicitudAnteriorPorTipo(Solicitud solicitud);
	
	/**
	 * Actualiza los datos concernientes a la tabla 
	 * dit_solicitud
	 * @author Hugo Martinez
	 * @Date 23/10/2012
	 * @param solicitud
	 */
	void actualizarDatosSolicitud(Solicitud solicitud);
	
	/**
	 * Obtiene las solicitud con estatus de INICIADA 
	 * asociadas a un rp.
	 * 
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param idPatronSujetoObligado
	 * @return List<Solicitud>
	 */
	List<Solicitud> listarSolicitudesDeRPEnProceso(Long cveIdSubdelegacion);

	/**
	 * Obtiene las solicitud con estatus de INICIADA 
	 * asociadas a una persona.
	 * 
	 * @author Hugo Martinez
	 * @return List<Solicitud>
	 */
	List<Solicitud> listarSolicitudesDePersonaEnProceso(TipoPersonaFiscal tipoPersonaFiscal, TipoSolicitudEnum tipoSolicitud, Long cveIdSubdelegacion);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 06/03/2013
	 * @param tipoSolicitud
	 * @param estadoSolciitud
	 * @return List<Solicitud>
	 */
	List<Solicitud> listarSolicitudesPorTipoYEstado(List<Long> idsTipoSolicitud, List<Long> idsEstadoSolicitud);
	
	/**
	 * Obtiene el detalle de la solicitud, patron y rp al que pertenece dicha
	 * solicitud a trav&eacute;s del folio de la solicitud
	 * 
	 * @author Marco S@aacute;nchez
	 * @Date 26/06/2013
	 * @param folio
	 * @return Solicitud
	 * @throws SolicitudNoEncontradaException
	 */
	Solicitud consultarDetalle(String folio)
			throws SolicitudNoEncontradaException;
	
	/**
	 * Obtienes todas las solicitudes asociadas al registro patronal proporcionado
	 * @param idPatronSujetoObligado Identificador del RP en la base delta
	 * @return List<Solicitud>
	 */
	List<Solicitud> listarSolicitudesDeRegistroPatronalPorId(
			Long idPatronSujetoObligado);

	/**
	 * Actualiza los estados de solicitud y tramites a cancelado
	 * @param folio
	 */
	void cancelarSolicitudPorFolio(String folio);

	Map<String, Object> listarSolicitudesPropietarioPorFiltro(
			DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtros,
			PropietarioSolicitudUtil propietario);
	
	/**
	 * Obtiene el id de la solicitud de alta del sujeto obligado
	 * @param IdPatron
	 * @return
	 */
	Long obtenerIdSolicitudAlta(Long IdPatron);


	/**
	 * Inserta registro VNDI para solicitudes de alta patronal
	 * void
	 */
	void recibeApVNDI(Long  idSolicitud);

	
	/**
	 * Inserta registro para confirmar el termino de un AP por VNDI
	 * void
	 */
	void confirmaApVNDI(Long  idSolicitud);


}
