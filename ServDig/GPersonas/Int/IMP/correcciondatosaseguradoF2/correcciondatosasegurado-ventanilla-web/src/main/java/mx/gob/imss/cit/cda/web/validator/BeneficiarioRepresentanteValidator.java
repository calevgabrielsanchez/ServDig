/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class BeneficiarioRepresentanteValidator extends AbstractValidator {

    private static final String REGEX_CURP_FISICA = "^([A-Z]{4})\\d{6}([A-Z]{6})([A-Z0-9])\\d{1}$";
    private static final String CURP = "curp";
    private static final int LENGTH_CURP = 18;
    private static final Logger LOG = LoggerFactory
            .getLogger(BeneficiarioRepresentanteValidator.class);

    public void validateCurp(Object object, Errors errors) {
        LOG.debug("---CDA--- Validaciones de solicitud por responsable *****: ");
        Fisica personaFisica = (Fisica) object;
        if (StringUtils.isNotBlank(personaFisica.getCurp())) {
            validateCurp(personaFisica.getCurp(), errors);
            LOG.debug(" ---CDA--- Validacion del curp result {}",
                    errors.getErrorCount());
        } else {
            errors.rejectValue(CURP, "field.required");
        }
    }

    public void validateTipoBeneficiario(Object object, Errors errors) {
        LOG.debug("---CDA--- Validaciones de solicitud por responsable *****: ");
        Fisica personaFisica = (Fisica) object;

        if (validarPersonaCapturadaNull(personaFisica)) {
            errors.rejectValue(CURP, "field.required");
        } else {
            LOG.debug("****Beneficiario********");
            LOG.debug("******** nombre ********" + personaFisica.getNombre());
            LOG.debug("******** apellido ********"
                    + personaFisica.getPrimerApellido());
            LOG.debug("******** apellido ********"
                    + personaFisica.getSegundoApellido());
            LOG.debug("******** fecha ********"
                    + personaFisica.getFechaNacimiento());
            LOG.debug("******** lugar ********"
                    + personaFisica.getLugarNacimiento());
            LOG.debug("******** sexo ********" + personaFisica.getSexo());
            LOG.debug("curp vacio datos basicos llenos");
        }
    }

    private void validateCurp(String curp, Errors errors) {
        if (curp.length() != LENGTH_CURP) {
            errors.rejectValue(CURP, "field.min.length",
                    new Object[] { Integer.valueOf(LENGTH_CURP) }, "");
        } else if (!curp.matches(REGEX_CURP_FISICA)) {
            errors.rejectValue(CURP, "field.curp.formato.incorrecto");
        }
    }

    private boolean validarPersonaCapturadaNull(Fisica fisica) {

        boolean personaValida = StringUtils.isBlank(fisica.getNombre());

        personaValida = personaValida
                && StringUtils.isBlank(fisica.getPrimerApellido());

        // personaValida = personaValida &&
        // StringUtils.isBlank(fisica.getSegundoApellido());

        personaValida = personaValida && fisica.getFechaNacimiento() == null;

        personaValida = personaValida
                && fisica.getLugarNacimiento().getClave().equals("-1");

        personaValida = personaValida
                && !(fisica.getSexo() != null && fisica.getSexo().getIdSexo() != null);

        return personaValida;
    }

    @Override
    public boolean supports(Class<?> type) {
        return Fisica.class.equals(type);
    }

}
