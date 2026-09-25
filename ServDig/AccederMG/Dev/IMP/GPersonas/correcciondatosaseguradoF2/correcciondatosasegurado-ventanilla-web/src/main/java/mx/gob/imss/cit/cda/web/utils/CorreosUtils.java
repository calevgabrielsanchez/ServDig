package mx.gob.imss.cit.cda.web.utils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoNotificacionEnum;
import mx.gob.imss.cit.cda.web.app.responsable.model.ReasignacionSolicitud;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.autorizar.vo.AutorizarSolicitud;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;

@Component
public class CorreosUtils {

    private static final String DATE_MASK = "dd/MM/yyyy";
    private final Logger log = LoggerFactory.getLogger(CorreosUtils.class);
    SimpleDateFormat fecha = new SimpleDateFormat(DATE_MASK);
    Integer diasHabiles = 40;

    @Value("${encabezado}")
    private String ENCABEZADO_CORREOS;

    @Value("${pie.pagina}")
    private String PIE_PAGINA;

    @Value("${contenido.correo.cancelacion}")
    private String CONTENIDO_CORREO_CANCELACION;

    @Value("${contenido.correo.rechazo}")
    private String CONTENIDO_CORREO_RECHAZO;

    @Value("${contenido.correo.solicitarInfo}")
    private String CONTENIDO_CORREO_SOLICITAR_INFO;

    @Value("${contenido.correo.reasignacion}")
    private String CONTENIDO_CORREO_REASIGNACION;

    @Value("${contenido.correo.autorizacion}")
    private String CONTENIDO_CORREO_AUTORIZACION;

    public CorreoElectronicoDTO crearCorreoElectronicoDTO(
            SeguimientoSolicitud seguimientoSolicitud,
            TipoNotificacionEnum tipoNotificacion, Usuario responsable) {
        CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();

        correoElectronicoDTO.setAsunto(StringEscapeUtils
                .unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));

        switch (tipoNotificacion) {
        case CANCELACION:
            correoElectronicoDTO
                    .setCorreoPara(new String[] { seguimientoSolicitud
                            .getInformacionRENAPO().getCorreoElectronico() });
            log.debug("---CDA--- correoDerechohabienteCancelacion: "
                    + seguimientoSolicitud.getInformacionRENAPO()
                            .getCorreoElectronico());
            correoElectronicoDTO
                    .setCuerpoCorreo(contenidoCorreoCancelacion(seguimientoSolicitud));
            log.debug("---CDA--- contenidoCorreoCancelacion: "
                    + correoElectronicoDTO.getCuerpoCorreo());
            break;
        case RECHAZO:
            correoElectronicoDTO.setCorreoPara(new String[] { responsable
                    .getFisica().getCorreoElectronico().getCorreo() });
            log.debug("---CDA--- correoResponsableRechazo: "
                    + responsable.getFisica().getCorreoElectronico()
                            .getCorreo());
            correoElectronicoDTO.setCuerpoCorreo(contenidoCorreoRechazo(
                    seguimientoSolicitud, responsable));
            log.debug("---CDA--- contenidoCorreoRechazo: "
                    + correoElectronicoDTO.getCuerpoCorreo());
            break;
        case SOLICITARINFO:
            correoElectronicoDTO
                    .setCorreoPara(new String[] { seguimientoSolicitud
                            .getInformacionRENAPO().getCorreoElectronico() });
            log.debug("---CDA--- correoDerechohabienteSolicitarInfo: "
                    + seguimientoSolicitud.getInformacionRENAPO()
                            .getCorreoElectronico());
            correoElectronicoDTO
                    .setCuerpoCorreo(contenidoCorreoSolicitarInfo(seguimientoSolicitud));
            log.debug("---CDA--- contenidoCorreoSolicitarInfo: "
                    + correoElectronicoDTO.getCuerpoCorreo());
            break;
        default:
            break;
        }

        return correoElectronicoDTO;
    }

    public CorreoElectronicoDTO crearCorreoElectronicoDTOReasignacion(
            ReasignacionSolicitud reasignacionSolicitud) {
        CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
        log.info("Enviando Correo {}",
                reasignacionSolicitud.getCorreoElectronico());
        ;

        correoElectronicoDTO.setCorreoPara(new String[] { reasignacionSolicitud
                .getCorreoElectronico() });
        log.debug("---CDA--- correoResponsableReasignacion: "
                + reasignacionSolicitud.getCorreoElectronico());
        correoElectronicoDTO.setAsunto(StringEscapeUtils
                .unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
        correoElectronicoDTO
                .setCuerpoCorreo(contenidoCorreoReasignacion(reasignacionSolicitud));
        log.debug("---CDA--- contenidoCorreoReasignacion: "
                + correoElectronicoDTO.getCuerpoCorreo());

        return correoElectronicoDTO;
    }

    public CorreoElectronicoDTO crearCorreoElectronicoDTOAutorizacion(
            AutorizarSolicitud autorizarSolicitud, Usuario responsable) {
        CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();

        correoElectronicoDTO.setCorreoPara(new String[] { responsable
                .getFisica().getCorreoElectronico().getCorreo() });
        log.debug("---CDA--- correoResponsableAutorizacion: "
                + responsable.getFisica().getCorreoElectronico().getCorreo());
        correoElectronicoDTO.setAsunto(StringEscapeUtils
                .unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
        correoElectronicoDTO.setCuerpoCorreo(contenidoCorreoAutorizacion(
                autorizarSolicitud, responsable));
        log.debug("---CDA--- contenidoCorreoAutorizacion: "
                + correoElectronicoDTO.getCuerpoCorreo());

        return correoElectronicoDTO;
    }

    public String contenidoCorreoCancelacion(
            SeguimientoSolicitud seguimientoSolicitud) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ENCABEZADO_CORREOS);

        stringBuilder
                .append(MessageFormat.format(
                        CONTENIDO_CORREO_CANCELACION,
                        new Object[] {
                                fecha.format(new Date()),
                                seguimientoSolicitud.getFolio() != null ? seguimientoSolicitud
                                        .getFolio() : "",
                                StringEscapeUtils
                                        .escapeHtml(seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getNombre() != null ? seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getNombre() : ""),
                                StringEscapeUtils
                                        .escapeHtml(seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getApellidoPaterno() != null ? seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getApellidoPaterno() : ""),
                                StringEscapeUtils
                                        .escapeHtml(seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getApellidoMaterno() != null ? seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getApellidoMaterno() : ""),
                                convertStringDateFormat(seguimientoSolicitud
                                        .getFechaInicio()),
                                StringEscapeUtils.escapeHtml(seguimientoSolicitud
                                        .getDetalle() != null ? seguimientoSolicitud
                                        .getDetalle() : ""),
                                StringEscapeUtils
                                        .escapeHtml(seguimientoSolicitud
                                                .getSubDelegacion()
                                                .getDescripcion() != null ? seguimientoSolicitud
                                                .getSubDelegacion()
                                                .getDescripcion() : "") }));

        stringBuilder.append(PIE_PAGINA);

        return stringBuilder.toString();
    }

    public String contenidoCorreoRechazo(
            SeguimientoSolicitud seguimientoSolicitud, Usuario responsable) {

        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat fecha = new SimpleDateFormat(DATE_MASK);

        stringBuilder.append(ENCABEZADO_CORREOS);

        try {
            stringBuilder
                    .append(MessageFormat.format(
                            CONTENIDO_CORREO_RECHAZO,
                            new Object[] {
                                    fecha.format(new Date()),
                                    seguimientoSolicitud.getFolio() != null ? seguimientoSolicitud
                                            .getFolio() : "",
                                    StringEscapeUtils
                                            .escapeHtml(responsable.getFisica()
                                                    .getNombre() != null ? responsable
                                                    .getFisica().getNombre()
                                                    : ""),
                                    StringEscapeUtils
                                            .escapeHtml(responsable.getFisica()
                                                    .getPrimerApellido() != null ? responsable
                                                    .getFisica()
                                                    .getPrimerApellido() : ""),
                                    StringEscapeUtils
                                            .escapeHtml(responsable.getFisica()
                                                    .getSegundoApellido() != null ? responsable
                                                    .getFisica()
                                                    .getSegundoApellido() : ""),
                                    seguimientoSolicitud.getFechaInicio(),
                                    seguimientoSolicitud.getInformacionRENAPO()
                                            .getCurp() != null ? seguimientoSolicitud
                                            .getInformacionRENAPO().getCurp()
                                            : "",
                                    seguimientoSolicitud.getNss() != null ? seguimientoSolicitud
                                            .getNss() : "",
                                    StringEscapeUtils
                                            .escapeHtml(seguimientoSolicitud
                                                    .getInformacionRENAPO()
                                                    .getNombre() != null ? seguimientoSolicitud
                                                    .getInformacionRENAPO()
                                                    .getNombre() : ""),
                                    StringEscapeUtils
                                            .escapeHtml(seguimientoSolicitud
                                                    .getInformacionRENAPO()
                                                    .getApellidoPaterno() != null ? seguimientoSolicitud
                                                    .getInformacionRENAPO()
                                                    .getApellidoPaterno() : ""),
                                    StringEscapeUtils
                                            .escapeHtml(seguimientoSolicitud
                                                    .getInformacionRENAPO()
                                                    .getApellidoMaterno() != null ? seguimientoSolicitud
                                                    .getInformacionRENAPO()
                                                    .getApellidoMaterno() : ""),
                                    diasHabiles
                                            - (diasLaborablesEntreFechas(
                                                    convertStringToDate(seguimientoSolicitud
                                                            .getFechaInicio()),
                                                    new Date())) }));
        } catch (ParseException e) {
            log.debug("---CDA--- Error convertStringToDateRechazo {}", e);
        }

        stringBuilder.append(PIE_PAGINA);

        return stringBuilder.toString();
    }

    public String contenidoCorreoSolicitarInfo(
            SeguimientoSolicitud seguimientoSolicitud) {

        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat fecha = new SimpleDateFormat(DATE_MASK);

        stringBuilder.append(ENCABEZADO_CORREOS);

        stringBuilder
                .append(MessageFormat.format(
                        CONTENIDO_CORREO_SOLICITAR_INFO,
                        new Object[] {
                                fecha.format(new Date()),
                                seguimientoSolicitud.getFolio() != null ? seguimientoSolicitud
                                        .getFolio() : "",
                                StringEscapeUtils
                                        .escapeHtml(seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getNombre() != null ? seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getNombre() : ""),
                                StringEscapeUtils
                                        .escapeHtml(seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getApellidoPaterno() != null ? seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getApellidoPaterno() : ""),
                                StringEscapeUtils
                                        .escapeHtml(seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getApellidoMaterno() != null ? seguimientoSolicitud
                                                .getInformacionRENAPO()
                                                .getApellidoMaterno() : ""),
                                convertStringDateFormat(seguimientoSolicitud
                                        .getFechaInicio()),
                                seguimientoSolicitud.getDetalle() != null ? seguimientoSolicitud
                                        .getDetalle() : "",
                                seguimientoSolicitud.getSubDelegacion()
                                        .getDescripcion() != null ? seguimientoSolicitud
                                        .getSubDelegacion().getDescripcion()
                                        : "" }));

        stringBuilder.append(PIE_PAGINA);

        return stringBuilder.toString();
    }

    public String contenidoCorreoReasignacion(
            ReasignacionSolicitud reasignacionSolicitud) {

        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat fecha = new SimpleDateFormat(DATE_MASK);

        stringBuilder.append(ENCABEZADO_CORREOS);

        try {
            stringBuilder
                    .append(MessageFormat
                            .format(CONTENIDO_CORREO_REASIGNACION,
                                    new Object[] {
                                            fecha.format(new Date()),
                                            reasignacionSolicitud.getFolio() != null ? reasignacionSolicitud
                                                    .getFolio() : "",
                                            reasignacionSolicitud
                                                    .getNombreCompleto() != null ? reasignacionSolicitud
                                                    .getNombreCompleto() : "",
                                            convertStringDateFormat(reasignacionSolicitud
                                                    .getFechaInicio()),
                                            reasignacionSolicitud
                                                    .getInformacionRENAPO()
                                                    .getCurp() != null ? reasignacionSolicitud
                                                    .getInformacionRENAPO()
                                                    .getCurp() : "",
                                            reasignacionSolicitud.getNss() != null ? reasignacionSolicitud
                                                    .getNss() : "",
                                            reasignacionSolicitud
                                                    .getInformacionRENAPO()
                                                    .getNombre() != null ? reasignacionSolicitud
                                                    .getInformacionRENAPO()
                                                    .getNombre() : "",
                                            reasignacionSolicitud
                                                    .getInformacionRENAPO()
                                                    .getApellidoPaterno() != null ? reasignacionSolicitud
                                                    .getInformacionRENAPO()
                                                    .getApellidoPaterno() : "",
                                            reasignacionSolicitud
                                                    .getInformacionRENAPO()
                                                    .getApellidoMaterno() != null ? reasignacionSolicitud
                                                    .getInformacionRENAPO()
                                                    .getApellidoMaterno() : "",
                                            diasHabiles
                                                    - (diasLaborablesEntreFechas(
                                                            convertStringToDate(reasignacionSolicitud
                                                                    .getFechaInicio()),
                                                            new Date())) }));
        } catch (ParseException e) {
            log.debug("---CDA--- Error convertStringToDateReasignacion {}", e);
        }

        stringBuilder.append(PIE_PAGINA);

        return stringBuilder.toString();

    }

    public String contenidoCorreoAutorizacion(
            AutorizarSolicitud autorizarSolicitud, Usuario responsable) {

        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat fecha = new SimpleDateFormat(DATE_MASK);

        stringBuilder.append(ENCABEZADO_CORREOS);

        stringBuilder
                .append(MessageFormat.format(
                        CONTENIDO_CORREO_AUTORIZACION,
                        new Object[] {
                                fecha.format(new Date()),
                                autorizarSolicitud.getFolio() != null ? autorizarSolicitud
                                        .getFolio() : "",
                                responsable.getFisica().getNombre() != null ? responsable
                                        .getFisica().getNombre() : "",
                                responsable.getFisica().getPrimerApellido() != null ? responsable
                                        .getFisica().getPrimerApellido() : "",
                                responsable.getFisica().getSegundoApellido() != null ? responsable
                                        .getFisica().getSegundoApellido() : "",
                                convertStringDateFormat(autorizarSolicitud
                                        .getFechaInicio()),
                                autorizarSolicitud
                                        .getNombreCompletoAutorizador() != null ? autorizarSolicitud
                                        .getNombreCompletoAutorizador() : "",
                                autorizarSolicitud.getInformacionRENAPO()
                                        .getCurp() != null ? autorizarSolicitud
                                        .getInformacionRENAPO().getCurp() : "",
                                autorizarSolicitud.getNss() != null ? autorizarSolicitud
                                        .getNss() : "",
                                autorizarSolicitud.getInformacionRENAPO()
                                        .getNombre() != null ? autorizarSolicitud
                                        .getInformacionRENAPO().getNombre()
                                        : "",
                                autorizarSolicitud.getInformacionRENAPO()
                                        .getApellidoPaterno() != null ? autorizarSolicitud
                                        .getInformacionRENAPO()
                                        .getApellidoPaterno() : "",
                                autorizarSolicitud.getInformacionRENAPO()
                                        .getApellidoMaterno() != null ? autorizarSolicitud
                                        .getInformacionRENAPO()
                                        .getApellidoMaterno() : "" }));

        stringBuilder.append(PIE_PAGINA);

        return stringBuilder.toString();

    }

    public String convertStringDateFormat(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_MASK);
        try {
            return simpleDateFormat.format(simpleDateFormat.parse(dateString));
        } catch (ParseException e) {
            return "";
        }

    }

    public Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_MASK);
        return simpleDateFormat.parse(dateString);

    }

    public Integer diasLaborablesEntreFechas(Date fechaInicio, Date fechaFin) {
        Calendar calendarInicio = Calendar.getInstance();
        calendarInicio.setTime(fechaInicio);
        calendarInicio.add(Calendar.DAY_OF_MONTH, 1);

        Calendar calendarFin = Calendar.getInstance();
        calendarFin.setTime(fechaFin);

        int workDays = 0;
        if (calendarInicio.get(Calendar.DAY_OF_YEAR) == calendarFin
                .get(Calendar.DAY_OF_YEAR)) {
            return 0;
        }

        while (calendarInicio.get(Calendar.DAY_OF_YEAR) < calendarFin
                .get(Calendar.DAY_OF_YEAR)) {
            calendarInicio.add(Calendar.DAY_OF_MONTH, 1);
            if (calendarInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                    && calendarInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
        }

        return workDays;
    }

}
