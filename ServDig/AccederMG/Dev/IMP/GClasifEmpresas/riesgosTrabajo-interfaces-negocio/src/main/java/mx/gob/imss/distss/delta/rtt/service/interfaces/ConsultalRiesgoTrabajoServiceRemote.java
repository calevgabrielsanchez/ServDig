package mx.gob.imss.distss.delta.rtt.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.ReporteRiesgoTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;

@Remote
public interface ConsultalRiesgoTrabajoServiceRemote {

    /**
     * Obtiene los riegos de trabajo
     *
     * @param patron
     * @param origen
     * @throws RiesgosTrabajoException
     * @return
     */
    List<RiesgoTrabajo> obtenerRiesgosTrabajoPatronalesPeriodo(PatronRiesgosTrabajo patron,OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    /**
     * Obtiene los datos del patron
     *
     * @param nrp
     * @param origen
     * @throws RiesgosTrabajoException
     * @return
     */
    PatronRiesgosTrabajo buscarPatron(String nrp,Integer anioPeriodo,Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	PatronRiesgosTrabajo buscarPersona(String rfc, Integer anioPeriodo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    /**
     * Genera documentos de riesgos trabajo
     *
     * @param patron
     * @param tipoArchivo
     * @param origen
     * @throws RiesgosTrabajoException
     * @return
     */
    byte[] generarDocumentoRiesgosTrabajo(PatronRiesgosTrabajo patron, TipoDescargaArchivo tipoArchivo,OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	/**
	 * Genera documentos de riesgos trabajo
	 *
	 * @param rfc
	 * @param tipoArchivo
	 * @param origen
	 * @throws RiesgosTrabajoException
	 * @return
	 */
	byte[] generarDocumentoRiesgosTrabajoRfc(PatronRiesgosTrabajo patron, List<RiesgoTrabajo> riesgosTrabajo, String rfc, TipoDescargaArchivo tipoArchivo,OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    /**
     * 
     * @param patron
     * @return
     * @throws RiesgosTrabajoException
     */
    byte[] aceptarTerminosCondiciones(PatronRiesgosTrabajo patron) throws RiesgosTrabajoException;

	/**
	 * @param patron
	 * @param rfc
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	byte[] aceptarTerminosCondicionesRfc(PatronRiesgosTrabajo patron, String rfc) throws RiesgosTrabajoException;

     /**
     * Validamos si el patron puede relizar una solicitud
     *
     * @param patron
     * @throws RiesgosTrabajoException
     */
     void validarSolicitud(PatronRiesgosTrabajo patron)throws RiesgosTrabajoException;

     /**
	 * Validamos si el patron puede relizar una solicitud
     *
	 * @param rfc
     * @throws RiesgosTrabajoException
     */
	void validarSolicitudRfc(String rfc)throws RiesgosTrabajoException;

	/**
	 * Validamos si el patron puede relizar una solicitud
	 *
	 * @param patron
	 * @param rfc
	 * @throws RiesgosTrabajoException
	 */
	boolean validarSolicitudMes(PatronRiesgosTrabajo patron, String rfc)throws RiesgosTrabajoException;

     /**
      * Busqueda de patron por nombre
      * @param nrs
      * @param ventanilla
      * @return
      * @throws RiesgosTrabajoException
      */
     List<Object[]> buscarPatronPorNombre(String nrs, OrigenSolicitudEnum ventanilla,String tipo, Long idDelegacion, Long idSubdelegacion, Integer periodo) throws RiesgosTrabajoException;

	/**
	 * 
	 * @param cve
	 * @param origen
	 * @return
	 * @throws RiesgosTrabajoException 
	 */
	PatronRiesgosTrabajo buscarPatronPorCveSujeto(long cve, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	/**
	 * 
	 * @param patron
	 * @return
	 * @throws RiesgosTrabajoException 
	 */
	boolean validarTerminosCondiciones(PatronRiesgosTrabajo patron) throws RiesgosTrabajoException;

	/**
	 *
	 * @param rfc
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	boolean validarTerminosCondicionesRfc(String rfc) throws RiesgosTrabajoException;

	/**
	 * 
	 * @param rfc
	 * @return
     * @throws RiesgosTrabajoException 
	 */
	List<Object[]> buscarPatronPorRFC(String rfc, Long idDelegacion, Long idSubdelegacion, Integer periodo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	List<Object[]> buscarRpPorRFC(String rfc, Long idDelegacion, Long idSubdelegacion, Integer periodo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	List<RiesgoTrabajo> buscarRtXLisRp(Integer periodo, String rfc, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	ReporteRiesgoTrabajo buscarReportesXrfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	ReporteRiesgoTrabajo crearReporteRfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	ReporteRiesgoTrabajo actualizaReportesRfc(ReporteRiesgoTrabajo reporte, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	int bajaReporteRfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	int encuentraRFC(String rfc) throws RiesgosTrabajoException;

	int encuentraNRP(String nrp) throws RiesgosTrabajoException;

	List<Delegacion> findDelegacionesActivas();
	
	List<Subdelegacion> findSubDelegacionesActivas(Long idDelegacion);
	
	Subdelegacion getSubdelegacionUsuario(Long idSubdelegacion);
	
	Delegacion getDelegacionUsuario(Long idDelegacion);
	
	/**
     * Obtiene los datos del patron
     * 
     * @param regPatron
     * @return
     * @throws RiesgosTrabajoException
     */
    PatronRiesgosTrabajo findPatron(String regPatron, Long idDelegacion, Long idSubdelegacion) throws RiesgosTrabajoException;

	PatronRiesgosTrabajo findPersona(String regPatron, int tipoPersona) throws RiesgosTrabajoException;

    String buscarPatronGral(String regPatron) throws RiesgosTrabajoException;

	PatronRiesgosTrabajo buscarPatronXRfc(String rfc, Integer anioPeriodo,Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	ReporteRiesgoTrabajo buscarReportesXrfcEstados(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	int actualizaDocumentOnlyRfc(ReporteRiesgoTrabajo reporte, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	ReporteRiesgoTrabajo descargaDocumentOnlyRfc(String rfc, int posicion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	int buscarTamanListaRtxRp(Integer periodo, String rfc, Long idDelegacion, Long idSubdelegacion,  OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

	int guardarxlsGeneradoBack(Integer periodo, String rfc, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum oigen, PatronRiesgosTrabajo patron) throws RiesgosTrabajoException;

	/**
	 * Se valida conexión de Base de Datos
	 */
	boolean validaConexDB();
}
