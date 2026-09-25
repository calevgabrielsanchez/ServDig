package mx.gob.imss.cit.cda.web.validator;

import java.util.List;

import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DatosHistoriaLaboralValidator extends AbstractValidator implements
        Validator {

    private static final Logger log = LoggerFactory
            .getLogger(DatosHistoriaLaboralValidator.class);

    private static final String REGEX_CURP_FISICA = "^([A-Z]{4})\\d{6}([A-Z]{6})([A-Z0-9])\\d{1}$";
    private static final String CURP = "curp";
    private static final int LENGTH_CURP = 18;

    @Override
    public boolean supports(Class<?> clazz) {
        return DatosHistoriaLaboralVO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        validacion(target, errors, null);
    }

    public void validate(Object target, Errors errors,
            List<String> curpsHistoricasBeneficiario) {
        validacion(target, errors, curpsHistoricasBeneficiario);
    }

    private void validacion(Object target, Errors errors,
            List<String> curpsHistoricasBeneficiario) {
        DatosHistoriaLaboralVO datosHistoriaLaboralVO = (DatosHistoriaLaboralVO) target;

        log.debug("##### Entrando Validator #####");

        if (datosHistoriaLaboralVO.getTipoSolicitante() == null) {
            errors.rejectValue("tipoSolicitante", "field.required");
        } else if (datosHistoriaLaboralVO.getTipoSolicitante().equals(
                "REPRESENTANTE_LEGAL")) {
            validateCurp(datosHistoriaLaboralVO.getCurp(), errors,
                    curpsHistoricasBeneficiario);

        } else if (datosHistoriaLaboralVO.getTipoSolicitante().equals("1")) {
            log.debug("ES DEFUNCION");
            if (datosHistoriaLaboralVO.getTipoBeneficiario() == null) {
                log.debug("TIPO BENEFICIARIO NULL");
                errors.rejectValue("tipoBeneficiario", "field.required");
            }
            validateCurp(datosHistoriaLaboralVO.getCurp(), errors,
                    curpsHistoricasBeneficiario);

        }

        if (datosHistoriaLaboralVO.getNSSList() == null) {
            errors.rejectValue("NSSList", "field.NSS.listaVacia");
        }

        if (datosHistoriaLaboralVO.getDocumentoProbatorioList() == null
                || datosHistoriaLaboralVO.getDocumentoProbatorioList()
                        .isEmpty()) {
            log.debug("##### Documentos Probatorios Vacia #####");
            errors.rejectValue("documentoProbatorioList",
                    "field.documentoProbatorio.listaVacia");
        }

        log.debug("Errores Detectados {} ", errors);
    }

    private void validateCurp(String curp, Errors errors,
            List<String> curpsHistoricasBeneficiario) {

        if (StringUtils.isBlank(curp)) {
            errors.rejectValue(CURP, "field.required");
        } else {
            if (curp.length() != LENGTH_CURP) {
                errors.rejectValue(CURP, "field.min.length",
                        new Object[] { Integer.valueOf(LENGTH_CURP) }, "");
            } else if (!curp.matches(REGEX_CURP_FISICA)) {
                errors.rejectValue(CURP, "field.curp.formato.incorrecto");
            } else if (validateCurpHistorica(curp, curpsHistoricasBeneficiario)) {
                errors.rejectValue(CURP, "field.curp.igual.curpFisico");
            }
        }
    }

    private boolean validateCurpHistorica(String curp,
            List<String> curpsHistoricasBeneficiario) {
        for (String curpHistorica : curpsHistoricasBeneficiario) {
            if (curpHistorica.equalsIgnoreCase(curp)) {
                return true;
            }
        }
        return false;
    }

    public void validarNssList(Object target, Errors errors) {
        DatosHistoriaLaboralVO datosHistoriaLaboralVO = (DatosHistoriaLaboralVO) target;
        log.debug("##### Entrando Validator Documentos Probatorios #####");
        if (datosHistoriaLaboralVO == null
                || (datosHistoriaLaboralVO.getNSSList() == null || datosHistoriaLaboralVO
                        .getNSSList().isEmpty())) {
            errors.rejectValue("NSSList", "field.NSS.listaVacia");
        } else {
            for (NSSVO nss : datosHistoriaLaboralVO.getNSSList()) {
                if (nss.getDocumentoProbatorioList() == null
                        || nss.getDocumentoProbatorioList().isEmpty()) {
                    log.debug("Error en el NSS {}", nss);
                    errors.rejectValue("documentoProbatorioList",
                            "field.documentoProbatorio.listaVacia");
                    break;
                }
            }
        }
    }

}
