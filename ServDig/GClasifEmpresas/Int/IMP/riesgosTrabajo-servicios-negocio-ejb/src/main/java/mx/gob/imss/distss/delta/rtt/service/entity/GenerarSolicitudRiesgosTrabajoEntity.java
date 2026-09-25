package mx.gob.imss.distss.delta.rtt.service.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsignacionMasiva;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "generarSolicitudRiesgosTrabajoEntity", mappedName = "generarSolicitudRiesgosTrabajoEntity")
public class GenerarSolicitudRiesgosTrabajoEntity implements GenerararSolicitudRiesgosTrabajoLocal {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(GenerarSolicitudRiesgosTrabajoEntity.class);

    @EJB(name = "firmaDigitalBusiness", mappedName = "firmaDigitalBusiness")
    private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;

    @EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusinessRemote;

    @Override
    public Solicitud crearSolicitudRiesgosTrabajoPatronales(PatronRiesgosTrabajo patron, List<RiesgoTrabajo> listaRiesgosT, OrigenSolicitudEnum origenSolicitud, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException {

        LOGGER.debug("Creamos la solicitud");

        Solicitud solicitud = new Solicitud();
        TramiteSujetoObligado tramite = new TramiteSujetoObligado();
        Calendar cal = Calendar.getInstance();
        Date fechaConclusion = cal.getTime();
        Date fechaActual = new Date();

        //Seteamos los datos de la solicitud																		
        solicitud.setFechaSolicitud(fechaActual);
        solicitud.setFechaPresentacion(fechaActual);
        solicitud.setFechaConclusion(fechaConclusion);
        solicitud.setTipoSolicitud(new TipoSolicitud());
        solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS.getValor().longValue());
        solicitud.setEstadoSolicitud(new EstadoSolicitud());
        solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
        solicitud.setObservacion(patron.getNrp());

        if (origenSolicitud != null) {
            solicitud.setOrigenSolicitud(new OrigenSolicitud());
            solicitud.getOrigenSolicitud().setIdOrigenSolicitud(origenSolicitud.getId());
        }
        //Seteamos la subdelegacion
        Subdelegacion delegacion = new Subdelegacion();
        delegacion.setId(patron.getDelegacion());
        solicitud.setSubdelegacion(delegacion);

        //Seteamos los datos del tramite
        tramite.setEstadoTramite(new EstadoTramite());

        tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
        tramite.getEstadoTramite().setDescripcion(EstadoTramiteEnum.CERRADO.getDescripcion());
        tramite.setTipoTramite(new TipoTramite());
        tramite.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS.getCodigo());
        tramite.setFechaTramite(fechaActual);
        tramite.setFechaPresentacion(fechaActual);
        tramite.setFechaConclusion(fechaConclusion);
        tramite.setObservacion(Long.toString(tipoArchivo.getId()));
        SujetoObligado persona = new SujetoObligado();
        persona.setCveIdSujetoObligado(patron.getIdPersona());
        tramite.setSujetoObligado(persona);
        //Seteamos el tramite a la solicitud
        solicitud.getTramites().add(tramite);

        //Creamos la solicitud
        try {
            solicitud = solicitudBusinessRemote.crear(solicitud);
        } catch (SolicitudNoValidaException e) {
            LOGGER.error("Error al guardar la solicitud {}", e);
            throw new RiesgosTrabajoException(ERROR_SOLICITUD);
        }

        LOGGER.debug("Se creo la solicitud");

        return solicitud;
    }

    @Override
    public FirmaElectronica obtenerDatosSellado(List<RiesgoTrabajo> riesgosTrabajoList, Solicitud solicitud, PatronRiesgosTrabajo patron, String rfc) throws RiesgosTrabajoException {

        LOGGER.debug("Creamos la cadena original");

        FirmaElectronica firmaElectronica = null;
        Locale locMEX = new Locale("es", "MX");
        Date fechaDelReporte = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
        StringBuilder sbCadenaOriginal = new StringBuilder();

        //Generamos la Firma electronica
        sbCadenaOriginal.append("||Invocante:portalimssdigital").append("|Tipo de Tramite:").append("Consulta de riesgos de trabajo terminados")
                .append("|Fecha del Tramite:").append(sdf.format(fechaDelReporte)).append("|Folio:").append(solicitud.getNoFolioSolicitud()).append("|Nombre o Razon Social:").append(patron.getRazonSocial());

        //Generamos la firma digital
        LOGGER.debug("Generamos la firma electronica");
        RespuestaFirmadoSimple selloDigital = new RespuestaFirmadoSimple();
        if(rfc.equals("0") || rfc  == "0"){
            sbCadenaOriginal.append("|Numero Registro Patronal:").append(patron.getNrp()).append("||");
        }else {
            sbCadenaOriginal.append("|Registro Federeal de Contribuyentes:").append(rfc).append("||");
            patron.setNrp(rfc);
        }

        selloDigital = firmaDigitalBusinessRemote.getSelloDigital(sbCadenaOriginal.toString(), null, patron.getNrp());

        if (selloDigital != null) {
            if (StringUtils.isBlank(selloDigital.getSello())) {
                throw new RiesgosTrabajoException("No se pudo firmar la solicitud");
            }

            // Se crea el objeto de firma digital
            firmaElectronica = new FirmaElectronica();
            firmaElectronica.setCadenaOriginal(sbCadenaOriginal.toString());
            firmaElectronica.setReciboNotarial(selloDigital.getTramite());
            firmaElectronica.setSecuenciaNotaria(selloDigital.getTramite());
            firmaElectronica.setSerialCertificado(selloDigital.getNoSerie());
            firmaElectronica.setRecibo(selloDigital.getSello());
            firmaElectronica.setUrlAcuseFirma("");
            firmaElectronica.setIniciaVigenciaCertificado(new Date());
            firmaElectronica.setFinVigenciaCertificado(new Date());
        } else {
            LOGGER.error("Error al generar la firma electronica");
            throw new RiesgosTrabajoException(ERROR_SOLICITUD);
        }

        LOGGER.debug("Se creo correctamente la firma electronica");
        return firmaElectronica;
    }

    @Override
    public FirmaElectronica obtenerFirmaElectronica(String cadenaOriginal, Solicitud solicitud, PatronRiesgosTrabajo patron) throws RiesgosTrabajoException {

        LOGGER.debug("Creamos la cadena original");

        FirmaElectronica firmaElectronica = null;

        //Generamos la firma digital
        LOGGER.debug("Generamos la firma electronica");
        RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal, null, patron.getNrp());

        if (selloDigital != null) {
            if (StringUtils.isBlank(selloDigital.getSello())) {
                throw new RiesgosTrabajoException("No se pudo firmar la solicitud");
            }

            // Se crea el objeto de firma digital
            firmaElectronica = new FirmaElectronica();
            firmaElectronica.setCadenaOriginal(cadenaOriginal);
            firmaElectronica.setReciboNotarial(selloDigital.getTramite());
            firmaElectronica.setSecuenciaNotaria(selloDigital.getTramite());
            firmaElectronica.setSerialCertificado(selloDigital.getNoSerie());
            firmaElectronica.setRecibo(selloDigital.getSello());
            firmaElectronica.setUrlAcuseFirma("");
            firmaElectronica.setIniciaVigenciaCertificado(new Date());
            firmaElectronica.setFinVigenciaCertificado(new Date());
        } else {
            LOGGER.error("Error al generar la firma electronica");
            throw new RiesgosTrabajoException(ERROR_SOLICITUD);
        }

        LOGGER.debug("Se creo correctamente la firma electronica");
        return firmaElectronica;
    }

    @Override
    public Solicitud crearSolicitudTerminosCondiciones(PatronRiesgosTrabajo patron, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException {

        LOGGER.debug("Creamos la solicitud");

        Solicitud solicitud = new Solicitud();
        TramiteSujetoObligado tramite = new TramiteSujetoObligado();
        Calendar cal = Calendar.getInstance();
        Date fechaConclusion = cal.getTime();
        Date fechaActual = new Date();

        //Seteamos los datos de la solicitud																		
        solicitud.setFechaSolicitud(fechaActual);
        solicitud.setFechaPresentacion(fechaActual);
        solicitud.setFechaConclusion(fechaConclusion);
        solicitud.setTipoSolicitud(new TipoSolicitud());
        solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS.getValor().longValue());
        solicitud.setEstadoSolicitud(new EstadoSolicitud());
        solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
        solicitud.setObservacion(patron.getNrp());

        //Seteamos la subdelegacion
        Subdelegacion delegacion = new Subdelegacion();
        delegacion.setId(patron.getDelegacion());
        solicitud.setSubdelegacion(delegacion);

        //Seteamos los datos del tramite
        tramite.setEstadoTramite(new EstadoTramite());

        tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.ACTIVO.getCodigo());
        tramite.getEstadoTramite().setDescripcion(EstadoTramiteEnum.ACTIVO.getDescripcion());
        tramite.setTipoTramite(new TipoTramite());
        tramite.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.ACEPTACION_TERMINOS_CONDICIONES.getCodigo());
        tramite.setFechaTramite(fechaActual);
        tramite.setFechaPresentacion(fechaActual);
        tramite.setFechaConclusion(fechaConclusion);
        tramite.setObservacion(Long.toString(tipoArchivo.getId()));
        SujetoObligado persona = new SujetoObligado();
        persona.setCveIdSujetoObligado(patron.getIdPersona());
        tramite.setSujetoObligado(persona);
        //Seteamos el tramite a la solicitud
        solicitud.getTramites().add(tramite);

        //Creamos la solicitud
        try {
            solicitud = solicitudBusinessRemote.crear(solicitud);
            LOGGER.debug("Solicitud TYC generada");
        } catch (SolicitudNoValidaException e) {
            LOGGER.error("Error al guardar la solicitud {}", e);
            throw new RiesgosTrabajoException(ERROR_SOLICITUD);
        }
        return solicitud;
    }

    @Override
    public Solicitud crearSolicitudRTRFC(PatronRiesgosTrabajo patron, List<RiesgoTrabajo> listaRiesgosT, OrigenSolicitudEnum origenSolicitud, String rfc, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException {
        LOGGER.debug("Se crea la solicitud x RFC");
        Solicitud solicitud = new Solicitud();
        TramiteMoral tramiteMoral = new TramiteMoral();
        TramiteFisica tramiteFisica = new TramiteFisica();
        Calendar cal = Calendar.getInstance();
        Date fechaConclusion = cal.getTime();
        Date fechaActual = new Date();

        //Seteamos los datos de la solicitud
        solicitud.setFechaSolicitud(fechaActual);
        solicitud.setFechaPresentacion(fechaActual);
        solicitud.setFechaConclusion(fechaConclusion);
        solicitud.setTipoSolicitud(new TipoSolicitud());
        solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS.getValor().longValue());
        solicitud.setEstadoSolicitud(new EstadoSolicitud());
        solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
        solicitud.setObservacion(rfc);

        if (origenSolicitud != null) {
            solicitud.setOrigenSolicitud(new OrigenSolicitud());
            solicitud.getOrigenSolicitud().setIdOrigenSolicitud(origenSolicitud.getId());
        }
        //Seteamos la subdelegacion
        Subdelegacion delegacion = new Subdelegacion();
        delegacion.setId(patron.getDelegacion());
        solicitud.setSubdelegacion(delegacion);

        //Seteamos los datos del tramite
        LOGGER.debug("Seteamos los datos del tramite RFC");
        LOGGER.debug("RFC: "+rfc + " y su tamanho: "+rfc.length());
        if(rfc.length() == 12){
            tramiteMoral.setEstadoTramite(new EstadoTramite());
            tramiteMoral.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
            tramiteMoral.getEstadoTramite().setDescripcion(EstadoTramiteEnum.CERRADO.getDescripcion());
            tramiteMoral.setTipoTramite(new TipoTramite());
            tramiteMoral.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS.getCodigo());
            tramiteMoral.setFechaTramite(fechaActual);
            tramiteMoral.setFechaPresentacion(fechaActual);
            tramiteMoral.setFechaConclusion(fechaConclusion);
            tramiteMoral.setObservacion(Long.toString(tipoArchivo.getId()));
            Moral moral = new Moral();
            moral.setIdPersona(patron.getIdPersona());
            tramiteMoral.setMoral(moral);
            //Seteamos el tramite a la solicitud
            solicitud.getTramites().add(tramiteMoral);
        }else if (rfc.length() == 13){
            tramiteFisica.setEstadoTramite(new EstadoTramite());
            tramiteFisica.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
            tramiteFisica.getEstadoTramite().setDescripcion(EstadoTramiteEnum.CERRADO.getDescripcion());
            tramiteFisica.setTipoTramite(new TipoTramite());
            tramiteFisica.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS.getCodigo());
            tramiteFisica.setFechaTramite(fechaActual);
            tramiteFisica.setFechaPresentacion(fechaActual);
            tramiteFisica.setFechaConclusion(fechaConclusion);
            tramiteMoral.setObservacion(Long.toString(tipoArchivo.getId()));
            Fisica fisica = new Fisica();
            fisica.setIdPersona(patron.getIdPersona());
            tramiteFisica.setFisica(fisica);
            //Seteamos el tramite a la solicitud
            solicitud.getTramites().add(tramiteFisica);
        }

        //Creamos la solicitud
        try {
            solicitud = solicitudBusinessRemote.crear(solicitud);
        } catch (SolicitudNoValidaException e) {
            LOGGER.error("Error al guardar la solicitud {}", e);
            throw new RiesgosTrabajoException(ERROR_SOLICITUD);
        }
        LOGGER.debug("Se creo la solicitud x RFC");
        return solicitud;
    }

    @Override
    public Solicitud crearSolicitudTerminosCondicionesRfc(PatronRiesgosTrabajo patron, String rfc, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException {
        LOGGER.debug("Creamos la solicitud");
        Solicitud solicitud = new Solicitud();
        TramiteMoral tramiteMoral = new TramiteMoral();
        TramiteFisica tramiteFisica = new TramiteFisica();
        Calendar cal = Calendar.getInstance();
        Date fechaConclusion = cal.getTime();
        Date fechaActual = new Date();

        //Seteamos los datos de la solicitud
        solicitud.setFechaSolicitud(fechaActual);
        solicitud.setFechaPresentacion(fechaActual);
        solicitud.setFechaConclusion(fechaConclusion);
        solicitud.setTipoSolicitud(new TipoSolicitud());
        solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS.getValor().longValue());
        solicitud.setEstadoSolicitud(new EstadoSolicitud());
        solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
        solicitud.setObservacion(rfc);

        //Seteamos la subdelegacion
        Subdelegacion delegacion = new Subdelegacion();
        delegacion.setId(null);
        solicitud.setSubdelegacion(delegacion);

        //Seteamos los datos del tramite
        if(rfc.length() == 12){
            tramiteMoral.setEstadoTramite(new EstadoTramite());
            tramiteMoral.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.ACTIVO.getCodigo());
            tramiteMoral.getEstadoTramite().setDescripcion(EstadoTramiteEnum.ACTIVO.getDescripcion());
            tramiteMoral.setTipoTramite(new TipoTramite());
            tramiteMoral.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.ACEPTACION_TERMINOS_CONDICIONES.getCodigo());
            tramiteMoral.setFechaTramite(fechaActual);
            tramiteMoral.setFechaPresentacion(fechaActual);
            tramiteMoral.setFechaConclusion(fechaConclusion);
            tramiteMoral.setObservacion(Long.toString(tipoArchivo.getId()));
            Moral moral = new Moral();
            moral.setIdPersona(patron.getIdPersona());
            tramiteMoral.setMoral(moral);
            //Seteamos el tramite a la solicitud
            solicitud.getTramites().add(tramiteMoral);
        }else if (rfc.length() == 13){
            tramiteFisica.setEstadoTramite(new EstadoTramite());
            tramiteFisica.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.ACTIVO.getCodigo());
            tramiteFisica.getEstadoTramite().setDescripcion(EstadoTramiteEnum.ACTIVO.getDescripcion());
            tramiteFisica.setTipoTramite(new TipoTramite());
            tramiteFisica.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.ACEPTACION_TERMINOS_CONDICIONES.getCodigo());
            tramiteFisica.setFechaTramite(fechaActual);
            tramiteFisica.setFechaPresentacion(fechaActual);
            tramiteFisica.setFechaConclusion(fechaConclusion);
            tramiteFisica.setObservacion(Long.toString(tipoArchivo.getId()));
            Fisica fisica = new Fisica();
            fisica.setIdPersona(patron.getIdPersona());
            tramiteFisica.setFisica(fisica);
            //Seteamos el tramite a la solicitud
            solicitud.getTramites().add(tramiteFisica);
        }

        //Creamos la solicitud
        try {
            solicitud = solicitudBusinessRemote.crear(solicitud);
            LOGGER.debug("Solicitud TYC generada");
        } catch (SolicitudNoValidaException e) {
            LOGGER.error("Error al guardar la solicitud {}", e);
            throw new RiesgosTrabajoException(ERROR_SOLICITUD);
        }
        return solicitud;
    }

}
