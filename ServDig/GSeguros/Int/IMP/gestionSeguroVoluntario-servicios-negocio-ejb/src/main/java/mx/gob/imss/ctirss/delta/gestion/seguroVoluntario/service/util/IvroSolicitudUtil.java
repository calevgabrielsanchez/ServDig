package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import java.util.ArrayList;
import java.util.List;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoIssfEnum;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;

import mx.gob.imss.digital.modelo.solicitud.EstadoSolicitud;
import mx.gob.imss.digital.modelo.solicitud.FirmaElectronica;
import mx.gob.imss.digital.modelo.solicitud.OrigenSolicitud;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;
import mx.gob.imss.digital.modelo.solicitud.TipoSolicitud;
import mx.gob.imss.digital.modelo.tramite.EstadoTramite;
import mx.gob.imss.digital.modelo.tramite.TipoTramite;
import mx.gob.imss.digital.modelo.tramite.Tramite;

public class IvroSolicitudUtil {

    private final static int TIPO_TRABAJADOR = 1;
    public static final Solicitud transformarSolicitudDeltaASolicitud(
            mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitudPortal) {

        Solicitud solicitudImssDigitalModelo = new Solicitud();

        solicitudImssDigitalModelo.setFirmadaDigitalmente(solicitudPortal.isFirmadaDigitalmente());
        solicitudImssDigitalModelo
                .setEstadoSolicitud(transformarEstadoSolicitudDeltaAIDM(solicitudPortal
                        .getEstadoSolicitud()));
        solicitudImssDigitalModelo.setFechaRegistro(solicitudPortal.getFechaPresentacion());
        solicitudImssDigitalModelo.setFirmaElectronica(transformarFirmaDeltaAIDM(solicitudPortal
                .getFirmaElectronica()));
        solicitudImssDigitalModelo.setIdSolicitud(solicitudPortal.getSolicitudId());
        solicitudImssDigitalModelo.setNumeroSerieCertificado(solicitudPortal
                .getNumeroSerieCertificado());
        solicitudImssDigitalModelo.setNumSolicitud(solicitudPortal.getNoFolioSolicitud());
        solicitudImssDigitalModelo
                .setOrigenSolicitud(transformarOrigenSolicitudDeltaAIDM(solicitudPortal
                        .getOrigenSolicitud()));
        solicitudImssDigitalModelo.setSecuenciaDeNotaria(solicitudPortal.getSecuenciaDeNotaria());
        solicitudImssDigitalModelo.setSelloDigital(solicitudPortal.getSelloDigital());
        solicitudImssDigitalModelo
                .setTipoSolicitud(transformarTipoSolicitudDeltaAIDM(solicitudPortal
                        .getTipoSolicitud()));
        solicitudImssDigitalModelo
                .setUsuario(solicitudPortal.getSolicitante() != null ? solicitudPortal
                        .getSolicitante().getUsuario() : null);
        solicitudImssDigitalModelo.setTramite(convertirTramitesDeltaAIDM(solicitudPortal
                .getTramites()));

        return solicitudImssDigitalModelo;
    }

    public static Tramite[] convertirTramitesDeltaAIDM(
            List<mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite> tramites) {
        if (tramites == null || (tramites != null && tramites.isEmpty())) {
            return null;
        }

        List<Tramite> tsidm = new ArrayList<Tramite>();
        for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tdelta : tramites) {
            Tramite tidm = convertirTramiteDeltaATramiteIDM(tdelta);
            tsidm.add(tidm);
        }
        return tsidm.toArray(new Tramite[tsidm.size()]);
    }

    public static Tramite convertirTramiteDeltaATramiteIDM(
            mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramitePortal) {
        Tramite tramite = new Tramite();
        tramite.setDetalleTramiteXml(tramitePortal.getDetalleTramiteXml());
        tramite.setEstadoTramite(new EstadoTramite());
        tramite.getEstadoTramite().setIdEstadoTramitePersona(
                tramitePortal.getEstadoTramite().getIdEstadoTramitePersona().longValue());
        tramite.getEstadoTramite()
                .setDescripcion(tramitePortal.getEstadoTramite().getDescripcion());
        tramite.setTramiteId(tramitePortal.getTramiteId());
        tramite.setFechaConclusion(tramitePortal.getFechaConclusion());
        tramite.setFechaEfecto(tramitePortal.getFechaEfecto());
        tramite.setFechaPresentacion(tramitePortal.getFechaPresentacion());
        tramite.setFechaRegistroActualizacion(tramitePortal.getFechaRegistroActualizacion());
        tramite.setFechaTramite(tramitePortal.getFechaTramite());
        tramite.setIndRatificado(tramitePortal.getIndRatificado());
        tramite.setObservacion(tramitePortal.getObservacion());
        tramite.setPasoTramite(tramitePortal.getPasoTramite());
        tramite.setTipoTramite(new TipoTramite());
        tramite.getTipoTramite().setDescripcion(tramitePortal.getTipoTramite().getDescripcion());
        tramite.getTipoTramite()
                .setIdTipoTramite(tramitePortal.getTipoTramite().getIdTipoTramite());

        return tramite;
    }

    public static final EstadoSolicitud transformarEstadoSolicitudDeltaAIDM(
            mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud estadoPortal) {
        if (estadoPortal == null) {
            return null;
        }

        EstadoSolicitud esid = new EstadoSolicitud();
        esid.setIdEstadoSolicitud(estadoPortal.getIdEstadoSolicitud());
        esid.setDescripcion(estadoPortal.getDescripcion());

        return esid;
    }

    public static final OrigenSolicitud transformarOrigenSolicitudDeltaAIDM(
            mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud origenPortal) {
        if (origenPortal == null) {
            return null;
        }

        OrigenSolicitud osid = new OrigenSolicitud();
        osid.setIdOrigenSolicitud(origenPortal.getIdTipoSolicitud());
        osid.setDescripcion(origenPortal.getDescripcion());

        return osid;
    }

    public static final TipoSolicitud transformarTipoSolicitudDeltaAIDM(
            mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud tipoPortal) {
        if (tipoPortal == null) {
            return null;
        }

        TipoSolicitud tsid = new TipoSolicitud();
        tsid.setIdTipoSolicitud(tipoPortal.getIdTipoSolicitud());
        tsid.setDescripcion(tipoPortal.getDescripcion());

        return tsid;
    }

    public static final FirmaElectronica transformarFirmaDeltaAIDM(
            mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica firmaPortal) {
        if (firmaPortal == null) {
            return null;
        }

        FirmaElectronica fe = new FirmaElectronica();
        fe.setCadenaOriginal(firmaPortal.getCadenaOriginal());
        fe.setCodRespuesta(firmaPortal.getCodRespuesta());
        fe.setCurp(firmaPortal.getCurp());
        fe.setFechaElectronica(firmaPortal.getFechaElectronica());
        fe.setFinVigenciaCertificado(firmaPortal.getFinVigenciaCertificado());
        fe.setFirmarArchivo(firmaPortal.isFirmarArchivo());
        fe.setIdSolicitud(firmaPortal.getIdSolicitud());
        fe.setIniciaVigenciaCertificado(firmaPortal.getIniciaVigenciaCertificado());
        fe.setMsgRespuesta(firmaPortal.getMsgRespuesta());
        fe.setNombreCompleto(firmaPortal.getNombreCompleto());
        fe.setRecibo(firmaPortal.getRecibo());
        fe.setReciboNotarial(firmaPortal.getReciboNotarial());
        fe.setRegistroPatronal(firmaPortal.getRegistroPatronal());
        fe.setRfc(firmaPortal.getRfc());
        fe.setSecuenciaNotaria(firmaPortal.getSecuenciaNotaria());
        fe.setSerialCertificado(firmaPortal.getSerialCertificado());
        fe.setsPKCS7(firmaPortal.getsPKCS7());
        fe.setStrFinVigenciaCertificado(firmaPortal.getStrFinVigenciaCertificado());
        fe.setStrIniciaVigenciaCertificado(firmaPortal.getStrIniciaVigenciaCertificado());
        fe.setTipoCertificado(firmaPortal.getTipoCertificado());
        fe.setUrlAcuseFirma(firmaPortal.getUrlAcuseFirma());

        return fe;
    }
    
    /**
     *
     * @param empleado
     * @param idModalidad
     * @return
     */
    public static int obtenTipoTrabajdor(EmpleadoCuota empleado, Integer idModalidad) {
        switch (idModalidad) {
            case 15:
                return empleado.getIndividual()?7:ParentescoIssfEnum.obternerEnumById(empleado.getParentesco().getIdParentesco().intValue()).getTipoDeTrabajador();
            default:
                return TIPO_TRABAJADOR;
        }
    }
}
