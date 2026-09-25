package mx.gob.imss.cit.cda.web.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.cit.cda.web.vo.HistoriaLaboralVO;
import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class HistoriaLaboralValidator extends AbstractValidator {

    private static final Logger log = LoggerFactory
            .getLogger(HistoriaLaboralValidator.class);
    private static final String REGEX_NRP = "^[A-Z0-9][0-9]{9,10}$";
    private static final String FORMATO_FECHA_ANIO = "yyyy";
    private static final String FORMATO_FECHA_MES_ANIO = "MM/yyyy";
    private static final String FORMATO_FECHA_DIA_MES_ANIO = "dd/MM/yyyy";
    private static final String FORMATO_FECHA_NULA = "0";
    private static final String SIN_NRP = "N/D";
    private static final String VIGENTEALAFECHA = "Vigente a la fecha";
    private static final String FECHA_BAJA = "fechaBaja";
    private static final String FIEL_REQUIRED = "field.required";
    private static final Integer MINIMO = 4;
    private static final Integer MAXIMO = 8;

    public void validate(Object target, Errors errors) {
        HistoriaLaboralVO historiaLaboralVO = (HistoriaLaboralVO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombrePatron",
                FIEL_REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entidadFederativa",
                FIEL_REQUIRED);
        validateFechaNula(historiaLaboralVO.getFechaInscripcion(), errors,
                "fechaInscripcion");
        /***/
        validateFormatoFecha(historiaLaboralVO.getFechaInscripcion(), errors,
                "fechaInscripcion");
        /***/
        
        if (!VIGENTEALAFECHA.equals(historiaLaboralVO.getFechaBaja())) {
            validateFechaNula(historiaLaboralVO.getFechaBaja(), errors,
                    FECHA_BAJA);
            validateFormatoFecha(historiaLaboralVO.getFechaBaja(), errors,
                    FECHA_BAJA);
            comparDate(historiaLaboralVO.getFechaInscripcion(),
                    historiaLaboralVO.getFechaBaja(), errors, FECHA_BAJA);
            compareDateActual(historiaLaboralVO.getFechaBaja(), errors,
                    FECHA_BAJA, "field.invalid.date.baja");
        }
        compareDateActual(historiaLaboralVO.getFechaInscripcion(), errors,
                "fechaInscripcion", "field.invalid.date.inicio");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "domicilioEmpresa",
                FIEL_REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actividadEmpresa",
                FIEL_REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "numeroRegistroPatronal", FIEL_REQUIRED);
        if (!errors.hasFieldErrors("numeroRegistroPatronal")) {
            validateFormatoNRP(errors,
                    historiaLaboralVO.getNumeroRegistroPatronal());
        }
    }

    private void compareDateActual(String fecha, Errors errors,
            String elemento, String msj) {
        if (!FORMATO_FECHA_NULA.equals(fecha)) {
            Date fin = convertStringToDate(fecha);
            Date fActual = new Date();
            if (fin.after(fActual)) {
                errors.rejectValue(elemento, msj);
            }
        }
    }

    private void comparDate(String fInicio, String fFin, Errors errors,
            String elemento) {
        if (!FORMATO_FECHA_NULA.equals(fInicio)
                && !FORMATO_FECHA_NULA.equals(fFin)) {
            Date inicio = convertStringToDate(fInicio);
            Date fin = convertStringToDate(fFin);
            if (inicio.after(fin)) {
                errors.rejectValue(elemento, "field.invalid.date");
            }
        }
    }

    private Date convertStringToDate(String fecha) {
        Date date = new Date();
        SimpleDateFormat sdf;
        String formatoFecha = FORMATO_FECHA_DIA_MES_ANIO;
        if (fecha.length() == MINIMO) {
            formatoFecha = FORMATO_FECHA_ANIO;
        } else if (fecha.length() > MINIMO && fecha.length() < MAXIMO) {
            formatoFecha = FORMATO_FECHA_MES_ANIO;
        }
        sdf = new SimpleDateFormat(formatoFecha);
        try {
            date = sdf.parse(fecha);
        } catch (ParseException e) {
            log.error("ParseException {} ", e);
        }
        return date;
    }

    private void validateFormatoFecha(String valor, Errors errors,
            String elemento) {
        if (!FORMATO_FECHA_NULA.equals(valor)) {
            try {

                String formatoFecha = FORMATO_FECHA_DIA_MES_ANIO;
                if (valor.length() == MINIMO) {
                    formatoFecha = FORMATO_FECHA_ANIO;
                } else if (valor.length() > MINIMO && valor.length() < MAXIMO) {
                    formatoFecha = FORMATO_FECHA_MES_ANIO;
                }

                Date fechaFormato = new SimpleDateFormat(formatoFecha)
                        .parse(valor);
                log.debug("###########Formato Fecha {}", fechaFormato);
                log.debug("###########Fecha Controller {}", valor);
                log.debug("Tamanio {}", valor.length());
                log.debug("formatoFecha {}", formatoFecha);
                if (!new SimpleDateFormat(formatoFecha).format(fechaFormato)
                        .equals(valor)) {
                    errors.rejectValue(elemento, "field.wrong.format");
                }
            } catch (IllegalArgumentException iAE) {
                log.error("Error {}", iAE);
                errors.rejectValue(elemento, "field.wrong.format");
            } catch (ParseException pE) {
                log.error("Error {}", pE);
                errors.rejectValue(elemento, "field.wrong.format");
            }
        }

    }

    private void validateFechaNula(String valor, Errors errors, String elemento) {
        if (FORMATO_FECHA_NULA.equals(valor)) {
            errors.rejectValue(elemento, FIEL_REQUIRED);
        }
    }

    private void validateFormatoNRP(Errors errors, String nrp) {
        if (StringUtils.isNotEmpty(nrp) && !nrp.equals(SIN_NRP)
                && !nrp.matches(REGEX_NRP)) {
            errors.rejectValue("numeroRegistroPatronal",
                    "field.NRP.formatoIncorrecto");
        }

    }

}
