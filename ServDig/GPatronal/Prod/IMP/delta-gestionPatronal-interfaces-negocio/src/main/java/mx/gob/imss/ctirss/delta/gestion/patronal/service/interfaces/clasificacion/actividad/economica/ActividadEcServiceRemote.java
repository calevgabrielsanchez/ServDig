package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AdjuntosClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.BuzonClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Remote
public interface ActividadEcServiceRemote {
	
	Clasificacion obtenerClasificacionPorSujetoObligado(Long cveIdSujetoObligado) throws GestionPatronalBusinessException;
	
	/**
	 * 
	 * @param idFraccion
	 * @return
	 */
	Fraccion obtenerFraccionPorIdentificador(Long idFraccion);
	
	/**
	 * 
	 * @param clasificacion
	 * @throws GestionPatronalBusinessException
	 */
	void asignarNuevaClasificacion(Clasificacion clasificacion, Long cveCausa) throws GestionPatronalBusinessException;
	
	/**
	 * Obtiene la solicitud asociada al tr�mite de de tipo clasificacion
	 * @author Hugo Martinez
	 * @Date 14/06/2012
	 * @param cveIdPatronSujetoObligado
	 * @return
	 */
	SujetoObligado obtenerClasificacionEnTramitePorPatron(Long cveIdPatronSujetoObligado);
	
	/**
	 * Se almacena la clasificaci�n anterior en el detalle del tr�mite y guarda la nueva clasificaci�n y procesos, personal,
	 * bienes, productos, equipo, transporte, materia prima material, procesos, actividades complementarias.
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param SujetoObligado
	 * @param idSolicitud
	 * @return SujetoObligado
	 */
	SujetoObligado afectarClasificacionActividadEconomica(SujetoObligado so, Solicitud solicitud) throws GestionPatronalBusinessException;
	
	/**
	 * Se almacena la clasificaci�n anterior en el detalle del tr�mite y guarda la nueva clasificaci�n y procesos, personal,
	 * bienes, productos, equipo, transporte, materia prima material, procesos, actividades complementarias.
	 * 
	 * Este metodo se publica para optimizar los procesos de afectaci�n al permitir la conclusi�n
	 * sin consultar nuevamente la solicitud.
	 * 
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param SujetoObligado
	 * @param solcitud objeto solicitud obtenido me diante el metodo consultar de solicitudBusiness
	 * @return SujetoObligado
	 */
	SujetoObligado concluirSolicitudModificacionSRT(SujetoObligado so, Solicitud solicitud ) throws GestionPatronalBusinessException;
	
	
	/**
	 * 
	 * Guarda la nueva clasificaci�n, procesos, personal,
	 * bienes, productos, equipo, transporte, materia prima material, procesos, actividades complementarias.
	 * Asi como el centro de trabajo seg�n sea el caso. Para los tramites d emodificaci�n dispara el movimiento
	 * a sindo 06, mientras que en un alta no se dispara ning�n movimiento pues esto se debe ralizar por separado
	 * 
	 * Este metodo se publica para optimizar los procesos de afectaci�n al permitir la conclusi�n
	 * sin consultar nuevamente la solicitud.
	 * 
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param SujetoObligado
	 * @param solcitud objeto solicitud obtenido me diante el metodo consultar de solicitudBusiness
	 * @return SujetoObligado
	 */
	SujetoObligado afectarTramiteModificacionPatronal(SujetoObligado so, Tramite tramite );
	
	
	/**
	 * Se obtiene la clasificaci�n con los valores equivalentes en la BDTU.
	 * Debido a que los identificadores proporcionados por el componente del 
	 * clasificador no son los mismos que en la base BDTU se obtienen los 
	 * correspondientes y se asignan los valores al objeto de clasificacion.
	 * 
	 * Esto se realiza mediante la comparaci�n de los identificadores proporcionados
	 * por el clasificador y los atributos num_xxx de cada entidad.
	 * @author Hugo Martinez
	 * @Date 10/07/2012
	 * @param clasificacion
	 * @return Clasificacion
	 */
	Clasificacion obtenerClasificacionEquivalente(Clasificacion clasificacion);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 28/09/2012
	 * @param numeroRegistroPatronal
	 */
	Solicitud crearSolicitudAnexoV(String numeroRegistroPatronal, Usuario usuario);
	
	/**
	 * Obtiene la solicitud previa en base al identificador 
	 * de solicitud proporcionado.
	 * Solo se consideran las solicitudes cuyo 
	 * tipo de solicitud es: ACTUALIZACION_DE_CLASIFICACION
	 * 
	 * 
	 * @author Hugo Martinez
	 * @Date 18/10/2012
	 * @param solicitudBase [solicitudId requerido]
	 * @return Solicitud
	 * @throws GestionPatronalBusinessException
	 */
	Solicitud obtenerSolicitudAnterior(Solicitud solicitudBase) throws GestionPatronalBusinessException;
	
	/**
	 * Obtiene la clasificaci�n anterior almacenada en el xml del detalle de tr�mite
	 * @author Hugo Martinez
	 * @Date 18/10/2012
	 * @param idSolicitudActual
	 * @return SujetoObligado
	 */
	SujetoObligado obtenerClasificacionActividadEconomicaAnteriorPorSolicitud(Long idSolicitudActual) throws GestionPatronalBusinessException;
	
	
	/**
	 * Se almacena la clasificaci�n anterior en el detalle del tr�mite y guarda la nueva clasificaci�n y procesos, personal,
	 * bienes, productos, equipo, transporte, materia prima material, procesos, actividades complementarias.
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param SujetoObligado
	 * @param idSolicitud
	 * @return SujetoObligado
	 */
	SujetoObligado concluirClasificacionActividadEconomica(SujetoObligado so, Solicitud solicitud) throws GestionPatronalBusinessException;
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 05/03/2013
	 * @param numeroRegistroPatronal
	 * @param usuario
	 * @return
	 */
	Solicitud crearSolicitudAltaPatronal(String numeroRegistroPatronal, Usuario usuario);
	
	/**
	 * Afecta la clasificaci�n (fracci�n asociada al patr�n) asi como la bit�cora
	 * de clasificaci�n
	 * @author Hugo Martinez
	 * @Date 07/03/2013
	 * @param clasificacion
	 */
	void actualizarClasificacion(Clasificacion clasificacion, Integer cveCausa, Integer tpoMovimiento) throws GestionPatronalBusinessException;
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 08/03/2013
	 * @param noFolio
	 * @param so
	 * @param cveCausa
	 * @param cveAplicacion
	 * @param tipoMovto
	 * @param origenMvto
	 */
	void ejecutarProcesoSincronizacionSINDO(String noFolio, SujetoObligado so, Date fechaEfecto, Long cveCausa, Integer cveAplicacion, Integer tipoMovto, Integer origenMvto);

    /**
     * 
     * @author Roberto Bernabe
     * @Date 08/03/2013
     * @param cveIdPatronSujetoObligado id del patron sujeto obligado que registro una baja patronal
     */
    void bajaPatronal(String numeroRegistroPatronal) throws GestionPatronalBusinessException;
    
    /**
     * 
     * @author Hugo Martinez
     * @Date 23/04/2013
     * @param cveIdFraccion
     * @return
     */
    Fraccion obtenerFraccionClaseActiva(Long cveIdFraccion) throws GestionPatronalBusinessException;
    
    /**
     * Genera el documento (PDF) de aviso de modificacion y lo almacena en la tabla dit_solicitud documento
     * dentro de la columna REF_COMPROBANTE_TRAMITE, adicionalmente retorna el documento 
     * generado como un byte[]
     * @param idSolicitud id de la solicitud asociada al documento
     * @return byte[]
     */
    byte[] generarAvisoModificacionSRT(Long idSolicitud) throws GestionPatronalBusinessException;
    
    /**
     * Genera el documento (PDF) de acuse de tramite de modificacion y lo almacena en la tabla dit_solicitud documento
     * dentro de la columna REF_ACUSE_RECIBO, adicionalmente retorna el documento 
     * generado como un byte[]
     * @param idSolicitud id de la solicitud asociada al documento
     * @return byte[]
     */
    byte[] generarAcuseModificacionSRT(Long idSolicitud) throws GestionPatronalBusinessException;
    
    byte[] generarComprobanteCitaInternet(Solicitud solicitud) throws GestionPatronalBusinessException;
    
    byte[] generarAcuseCancelacion(Solicitud solicitud) throws GestionPatronalBusinessException;
    
    /**
     * Retorna el documento solicitado basandose en el tipo de documento,
     * en caso de que el documento no exista lo genera y lo almacena en la base de datos
     * si el doucmento fue generado previamente se devuelve el documento que se encuentra
     * en ese momento almacenado en la base de datos.
     * Verifica si el tipo de documento solicitado
     * @param idSolicitud identificador de la solicitud en la base delta
     * @param idTipoDocumento Tipo de documento (ACUSE o  COMPROBANTE DE TRAMITE)
     * @return PDF como byte[]
     */
    byte[] obtenerDocumentoModificacionSRTPorSolicitud(Long idSolicitud, Integer idTipoDocumento) throws GestionPatronalBusinessException;
    
    /**
     * Retorna el documento solicitado basandose en el tipo de documento,
     * en caso de que el documento no exista lo genera y lo almacena en la base de datos
     * si el doucmento fue generado previamente se devuelve el documento que se encuentra
     * en ese momento almacenado en la base de datos.
     * Verifica si el tipo de documento solicitado
     * @param solicitud solicitud previamente consultada mediante el servicio consultar de solicitudBusiness
     * @param idTipoDocumento Tipo de documento (ACUSE o  COMPROBANTE DE TRAMITE)
     * @return PDF como byte[]
     */
    byte[] obtenerDocumentoModificacionSRTPorSolicitud(Solicitud solicitud, Integer idTipoDocumento) throws GestionPatronalBusinessException;
    
    byte[] obtenerCartaTerminosFiel(Solicitud solicitud) throws GestionPatronalBusinessException;
    
    /**
     * Obtiene la informaci�n de fracci�n y clase activa de la base de datos mediante
     * el nu ero de fracci�n completa representado
     * por numDivision, numGrupo y numFraccion
     * La fracci�n completa puede ser de 3 o 4 digitos
     * @param fraccionCompleta
     * @return Fraccion
     * @throws GestionPatronalBusinessException
     */
    Fraccion obtenerFraccionPornumFraccionCompleta(String fraccionCompleta)throws GestionPatronalBusinessException;
    
	/**
	 * Guarda referencia en la base de datos de la ruta en File System donde se depositan los archivos adjuntos al tramite 
	 * @param adjuntosClasificacion
	 * @return
	 * @throws Exception 
	 */
    AdjuntosClasificacion guardarArchivoAdjunto(AdjuntosClasificacion adjuntosClasificacion) throws Exception;

	/**
	 * Marca como borrado el archivo al tramite 
	 * @param folio
	 * @param nombreArchivo
	 * @return
	 * @throws Exception 
	 */
	void quitarArchivoAdjunto(String folio, String nombreArchivo) throws Exception;

	/**
	 * Consulta archivos adjuntos al retomar el tramite 
	 * @param folio
	 * @return
	 * @throws Exception 
	 */
	List<AdjuntosClasificacion> consultarArchivoAdjunto(String folio) throws Exception;    
	

	/**
     *  Busca solicitudes con base a nrp, tipo de tramite y fecha surte efecto
     * 
     * @param  nrp
     * @param  cveIdTipoTramite
     * @param  fechaSurteEfecto
     * @return 
     */	
	String buscaSolicitudesSimilares(String nrp, String cveIdTipoTramite, String fechaSurteEfecto);

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
