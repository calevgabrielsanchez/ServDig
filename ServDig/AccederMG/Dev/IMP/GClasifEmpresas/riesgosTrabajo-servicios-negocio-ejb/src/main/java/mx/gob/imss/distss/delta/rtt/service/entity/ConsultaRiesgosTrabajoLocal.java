package mx.gob.imss.distss.delta.rtt.service.entity;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.ReporteRiesgoTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;

@Local
public interface ConsultaRiesgosTrabajoLocal {


    /**
     * Metodo para buscar los riesgos de trabajo por el registro patronal en un periodo
     *
     * @param regPatron
     * @param fechaInicio
     * @param fechaFin
     * @param origen
     * @return
     * @throws RiesgosTrabajoException
     */
    List<RiesgoTrabajo> findRiesgoTrabajoRegPatronPeriodo(String regPatron, Date fechaInicio, Date fechaFin, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;


    /**
     * Obtiene los datos del patron
     *
     * @param regPatron
     * @return
     * @throws RiesgosTrabajoException
     */
    PatronRiesgosTrabajo findPatron(String regPatron, Long idDelegacion, Long idSubdelegacion) throws RiesgosTrabajoException;

    /**
     * Obtiene los datos del patron
     *
     * @param rfc
     * @return
     * @throws RiesgosTrabajoException
     */
    PatronRiesgosTrabajo findPersona(String rfc, int tipoPersona) throws RiesgosTrabajoException;

    /**
     * Obtiene las solicitudes de un patron
     *
     * @param idPatron
     * @return
     * @throws RiesgosTrabajoException
     */
    List<Solicitud> findSolicitud(long idPatron, TipoSolicitudEnum tipo, EstadoSolicitudEnum estado, Long tipoTramite, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException;

    /**
     * Obtiene las solicitudes de un RFC
     *
     * @param rfc
     * @param tipo
     * @param estado
     * @param tipoTramite
     * @return
     * @throws RiesgosTrabajoException
     */
    List<Solicitud> findSolicitudesRFC(String rfc, TipoSolicitudEnum tipo, EstadoSolicitudEnum estado, Long tipoTramite, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException;

    /**
     * Buscar patron por nombre
     *
     * @param nrs
     * @return
     */
    List<Object[]> findPatronByNombre(String nrs, String tipo, Long idDelegacion, Long idSubdelegacion, Integer periodo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;


    /**
     * @param cveSujetoObligado
     * @return
     * @throws RiesgosTrabajoException
     */
    PatronRiesgosTrabajo findPatronBySujeto(long cveSujetoObligado) throws RiesgosTrabajoException;


    /**
     * @param rfc
     * @return
     */
    List<Object[]> findPatronByRFC(String rfc, Long idDelegacion, Long idSubdelegacion, Integer periodo, OrigenSolicitudEnum origen);

    List<Object[]> findRpPorRFC(String rfc, Long idDelegacion, Long idSubdelegacion, Integer periodo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    List<RiesgoTrabajo> findRtXLisRp(Integer periodo, String rfc, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    ReporteRiesgoTrabajo findReportesXrfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    ReporteRiesgoTrabajo createReporteRfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    ReporteRiesgoTrabajo updateReportesRfc(ReporteRiesgoTrabajo reporte, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    int downReporteRfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    int findExiRfc(String rfc);

    int findExiNrp(String nrp);

    List<Delegacion> findDelegacionesActivas();

    List<Subdelegacion> findSubDelegacionesActivas(Long idDelegacion);

    Subdelegacion getSubdelegacionUsuario(Long idSubdelegacion);

    Delegacion getDelegacionUsuario(Long idDelegacion);

    String findPatronGeneral(String patron) throws RiesgosTrabajoException;

    PatronRiesgosTrabajo findPatronXRfc(String nrs, Long idDelegacion, Long idSubdelegacion) throws RiesgosTrabajoException;

    ReporteRiesgoTrabajo findReportesXrfcEstados(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    int updateDocumentOnlyRfc(ReporteRiesgoTrabajo reporte, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    ReporteRiesgoTrabajo downloadDocumentOnlyRfc(String rfc, int posicion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    int findTamanRiesgosxRps(Integer periodo, String rfc, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException;

    boolean validateConectDB();
}
