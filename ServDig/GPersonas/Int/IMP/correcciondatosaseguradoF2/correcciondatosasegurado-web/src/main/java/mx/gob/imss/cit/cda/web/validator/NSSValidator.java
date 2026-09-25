package mx.gob.imss.cit.cda.web.validator;

import java.util.Arrays;
import java.util.List;

import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.web.utils.DeltaUtils;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class NSSValidator extends AbstractValidator {

    private static final String[] NUMEROS_NSS_CERTIFICADOR_POSICIONES_1_2 = {
            "33", "77", "79", "80", "97" };
    private static final String NUMERO_NSS_CERTIFICADOR_POSICIONES_1_2_CASO_2 = "89";
    private static final String FIELD_NSS_FORMATO_INCORRECTO = "field.NSS.formatoIncorrecto";
    private static final String NSS = "NSS";
    private static final String[] NUMEROS_NSS_CERTIFICADOR_COMBINACION_POSICIONES_3_4 = {
            "97", "98", "99", "00", "01", "02" };
    private static final int INICIO_CASO_1 = 0;
    private static final int FIN_CASO_1 = 2;
    private static final int INICIO_CASO_2 = 2;
    private static final int FIN_CASO_2 = 4;
    private static final int NUMERO_MAXIMO_NSS = 1;
    private static final int TAMANIO_NSS = 11;

    @Autowired
    private DeltaUtils deltaUtils;

    @Autowired
    @Qualifier("registroSolicitudCorreccionDatosAseguradoBusiness")
    private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;

    private static final Logger loggerConsole = LoggerFactory
            .getLogger(NSSValidator.class);

    public void validate(Object target, Errors errors) {

        NSSVO nSSVO = (NSSVO) target;
        loggerConsole.debug("DigitoVerificador");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, NSS,
                "field.NSS.listaVacia");
        loggerConsole.debug("DigitoVerificador1");
        if (!errors.hasErrors()) {
            if (nSSVO.getNSS().length() == TAMANIO_NSS) {
                validateFormatoNSS(errors, nSSVO);
                validateNSSdesbloqueado(errors, nSSVO);

            } else {
                errors.rejectValue(NSS, "field.NSS.invalido");
            }
        }
        loggerConsole.debug("DigitoVerificador2");
    }
    
    public void validateExisteNssInList(Object target, List<NSSVO> listNSSVO, Errors errors) {
        NSSVO nSSVO = (NSSVO) target;
        if (listNSSVO != null && !listNSSVO.isEmpty()) {
            for(NSSVO nssExistente:listNSSVO){
                if(nssExistente.getNSS().equals(nSSVO.getNSS())){
                    errors.rejectValue(NSS, "field.NSS.enLista");
                }
            }
        }
        loggerConsole.debug("DigitoVerificador2");
    }

    public void validateAsociado(Object target, Errors errors) {

        NSSVO nSSVO = (NSSVO) target;
        loggerConsole.debug("DigitoVerificador");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, NSS,
                "field.required");
        loggerConsole.debug("DigitoVerificador1");
        if (!errors.hasErrors()) {
            if (nSSVO.getNSS().length() == TAMANIO_NSS) {
                validateFormatoNSS(errors, nSSVO);

            } else {
                errors.rejectValue(NSS, "field.NSS.invalido");
            }
        }
        loggerConsole.debug("DigitoVerificador2");
    }

    public void validateLengthListaNSS(Errors errors, Integer lengthList) {
        if (lengthList >= NUMERO_MAXIMO_NSS) {
            if (NUMERO_MAXIMO_NSS == 1) {
                errors.rejectValue(NSS, "field.NSS.numeroMaximo",
                        new Object[] {  Integer.valueOf(NUMERO_MAXIMO_NSS) }, "");
            } else {
                errors.rejectValue(NSS, "field.NSS.numeroMaximos",
                        new Object[] {  Integer.valueOf(NUMERO_MAXIMO_NSS) }, "");
            }
        }
    }

    private void validateFormatoNSS(Errors errors, NSSVO nSSVO) {
        if (nSSVO.getNSS().equals("00000000000")) {
            errors.rejectValue(NSS, FIELD_NSS_FORMATO_INCORRECTO);
        }
        if (!errors.hasErrors()) {
            try {
                Integer digitoVerificador = Character.getNumericValue(nSSVO
                        .getNSS().charAt(nSSVO.getNSS().length() - 1));
                loggerConsole.debug("DigitoVerificador {}", digitoVerificador);
                loggerConsole.debug("Tamanio NSS {} ", nSSVO.getNSS().length());
                loggerConsole.debug("Digit oVerificador Generado {}",
                        deltaUtils.generaDigitoVerificador(nSSVO.getNSS()));
                if (!digitoVerificador.equals(deltaUtils
                        .generaDigitoVerificador(nSSVO.getNSS()))
                        || nSSVO.getNSS().length() != TAMANIO_NSS) {
                    errors.rejectValue(NSS, FIELD_NSS_FORMATO_INCORRECTO);
                }
            } catch (NumberFormatException nfe) {
                loggerConsole.warn("nfe {}", nfe);
                errors.rejectValue(NSS, FIELD_NSS_FORMATO_INCORRECTO);
            }
        }
    }

    private void validateNSSCertificador(Errors errors, NSSVO nSSVO) {
        if (!errors.hasErrors()) {
            if (Arrays
                    .asList(NUMEROS_NSS_CERTIFICADOR_POSICIONES_1_2)
                    .contains(
                            nSSVO.getNSS().substring(INICIO_CASO_1, FIN_CASO_1))) {
                errors.rejectValue(NSS, FIELD_NSS_FORMATO_INCORRECTO);
            } else if (NUMERO_NSS_CERTIFICADOR_POSICIONES_1_2_CASO_2
                    .equalsIgnoreCase(nSSVO.getNSS().substring(INICIO_CASO_1,
                            FIN_CASO_1))
                    && Arrays
                            .asList(NUMEROS_NSS_CERTIFICADOR_COMBINACION_POSICIONES_3_4)
                            .contains(
                                    nSSVO.getNSS().substring(INICIO_CASO_2,
                                            FIN_CASO_2))) {
                errors.rejectValue(NSS, FIELD_NSS_FORMATO_INCORRECTO);
            }
        }

    }

    private void validateNSSdesbloqueado(Errors errors, NSSVO nSSVO) {
        if (registroSolicitudCorreccionDatosAseguradoBusiness
                .isNSSBloqueado(nSSVO.getNSS())) {
            loggerConsole.debug(
                    "---CDA--- El NSS se encauntra bloqueado NSS {} ",
                    nSSVO.getNSS());
            errors.rejectValue(NSS, "field.NSS.bloqueado");
        }
    }

}