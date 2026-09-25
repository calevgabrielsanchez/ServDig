package mx.gob.imss.ctirss.delta.framework.util;

import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

public class RegexValidatorUtil extends AbstractValidator{
	
	private static final String REGEX_CURP = "^([a-zA-Z]{4})\\d{6}([hmHM]{1}[a-zA-Z]{2}" +
			"[b-df-hj-np-tv-zB-DF-HJ-NP-TV-Z]{3}[a-zA-Z0-9]{1}[0-9]{1})$";
	public static final int LONGITUD_CURP = 18;
	
	private static final String REGEX_RFC_FISICA = "^([a-zA-Z\u00D1\u00F1]{4})\\d{6}([a-zA-Z\\w]{3})$";
	public static final int LONGITUD_RFC_FISICA = 13;
	
	private static final String REGEX_RFC_MORAL = "^([a-zA-Z\u0026]{3})\\d{6}([\\w]{3})$";
	public static final int LONGITUD_RFC_MORAL  = 12;
	
	public static final String REGEX_FECHA_RFC = "(0?[1-9]|1[012])(0?[1-9]|[12][0-9]|3[01])";
	private static final String REGEX_NSS = "^\\d{11}$";
	public static final int LONGITUD_NSS = 11;
    
    private static final String ERROR_LONGITUD_CURP = "La CURP tiene que ser de " + LONGITUD_CURP + " caracteres";
    private static final String ERROR_FORMATO_CURP = "La CURP no cumple con el formato requerido";
    
    private static final String ERROR_LONGITUD_RFC_FISICA = "El RFC tiene que ser de " + LONGITUD_RFC_FISICA + " caracteres";
    private static final String ERROR_FORMATO_RFC_FISICA= "El RFC no cumple con el formato requerido";
    
    private static final String ERROR_LONGITUD_RFC_MORAL = "El RFC tiene que ser de " + LONGITUD_RFC_MORAL + " caracteres";
    private static final String ERROR_FORMATO_RFC_MORAL= "El RFC no cumple con el formato requerido";
    
    
	/**
	 * Metodo que valida que el CURP sea valido devuelve un mensaje con los
	 * disintos mensajes de error
	 * 
	 * @param curp
	 *            String con el valor de la curp
	 * @return String con el mensaje de validacion o vacio si es que es correcta
	 *         la CURP
	 */
	public static String validarCURP(String curp) {

		if (curp.length() != LONGITUD_CURP) {
			return ERROR_LONGITUD_CURP;
		} else if (!curp.matches(REGEX_CURP)) {
			return ERROR_FORMATO_CURP;
		} else {
			return "";
		}

	}

	/**
	 * Metodo que valida que el RFC sea valido devuelve un mensaje con los
	 * disintos mensajes de error
	 * 
	 * @param curp
	 *            String con el valor de la RFC
	 * @return String con el mensaje de validacion o vacio si es que es correcta
	 *         la RFC
	 */
	public static String validarRFCFisica(String rfc) {

		if (rfc.length() != LONGITUD_RFC_FISICA) {
			return ERROR_LONGITUD_RFC_FISICA;
		} else if (!rfc.matches(REGEX_RFC_FISICA)) {
			return ERROR_FORMATO_RFC_FISICA;
		} else {
			return "";
		}

	}

	/**
	 * Metodo que valida que el RFC sea valido devuelve un mensaje con los
	 * disintos mensajes de error
	 * 
	 * @param curp
	 *            String con el valor de la RFC
	 * @return String con el mensaje de validacion o vacio si es que es correcta
	 *         la RFC
	 */
	public static String validarRFCMoral(String rfc) {

		if (rfc.length() != LONGITUD_RFC_MORAL) {
			return ERROR_LONGITUD_RFC_MORAL;
		} else if (!rfc.matches(REGEX_RFC_MORAL)) {
			return ERROR_FORMATO_RFC_MORAL;
		} else {
			return "";
		}

	}

	/**
	 * Metodo que valida que la estructura de la CURP concida con la expresion
	 * regular definida por RENAPO
	 * 
	 * @param strCurp
	 *            String con la CURP a evaluar
	 * @return true o false si concide o no la CURP
	 */
	public static boolean curpMatches(String strCurp) {
		boolean validaCurp = false;
		if (StringUtils.isNotBlank(strCurp) && strCurp.matches(REGEX_CURP)) {
			validaCurp = true;
		}
		
		return validaCurp;
	}

	/**
	 * Metodo que valida que la estructura de la RFC fisica concida con la
	 * expresion regular definida por RENAPO
	 * 
	 * @param strCurp
	 *            String con la RFC a evaluar
	 * @return true o false si concide o no la RFC
	 */
	public static boolean rfcFisicaMatches(String strRfc) {
		boolean validaRfc = false;
		if (StringUtils.isNotBlank(strRfc) && strRfc.matches(REGEX_RFC_FISICA)) {
			validaRfc = true;
		}
		return validaRfc;
	}

	/**
	 * Metodo que valida que la estructura de la RFC fisica concida con la
	 * expresion regular definida por RENAPO
	 * 
	 * @param strCurp
	 *            String con la RFC a evaluar
	 * @return true o false si concide o no la RFC
	 */
	public static boolean rfcMoralMatches(String strRfc) {
		boolean validaRfc = false;
		if (StringUtils.isNotBlank(strRfc) && strRfc.matches(REGEX_RFC_MORAL)) {
			validaRfc = true;
		}
		return validaRfc;
	}

	public static void validaCURPVista(String elemento, Errors errors,
			String curp) {

		if (StringUtils.isNotBlank(curp)) {
			if (curp.length() != LONGITUD_CURP) {
				errors.rejectValue(elemento, "field.min.length",
						new Object[] { new Integer(LONGITUD_CURP) }, "");
			} else {
				if (!curp.matches(REGEX_CURP)) {
					errors.rejectValue(elemento, "field.wrong.format");
				}
			}
		} else {
			errors.rejectValue(elemento, "field.required");
		}
	}

	public static void validaRFCFisicaVista(String elemento, Errors errors,
			String rfc) {

		if (StringUtils.isNotBlank(rfc)) {
			if (rfc.length() != LONGITUD_RFC_FISICA) {
				errors.rejectValue(elemento, "field.max.length",
						new Object[] { new Integer(LONGITUD_RFC_FISICA) }, "");
			} else {
				if (!rfc.matches(REGEX_RFC_FISICA)) {
					errors.rejectValue(elemento, "field.wrong.format");
				}
			}
		} else {
			errors.rejectValue(elemento, "field.required");
		}
	}

	public static void validaRFCMoralVista(String elemento, Errors errors,
			String rfcMoral) {

		if (StringUtils.isNotBlank(rfcMoral)) {
			if (rfcMoral.length() != LONGITUD_RFC_MORAL) {
				errors.rejectValue(elemento, "field.max.length",
						new Object[] { new Integer(LONGITUD_RFC_MORAL) }, "");
			} else {
				if (!rfcMoral.matches(REGEX_RFC_MORAL)) {
					errors.rejectValue(elemento, "field.wrong.format");
				}
			}
		} else {
			errors.rejectValue(elemento, "field.required");
		}
	}    
	
	public static void validaNSSVista(String elemento, Errors errors,
			String nss) {

		if (StringUtils.isNotBlank(nss)) {
			if (nss.length() != LONGITUD_NSS) {
				errors.rejectValue(elemento, "field.min.length",
						new Object[] { new Integer(LONGITUD_NSS) }, "");
			} else {
				if (!nss.matches(REGEX_NSS)) {
					errors.rejectValue(elemento, "field.wrong.format");
				}
			}
		} else {
			errors.rejectValue(elemento, "field.required");
		}
	}
	
	public static void validaFechaRFC(String elemento, Errors errors,
			String rfc) {
		if(!rfc.matches(REGEX_FECHA_RFC)){
			errors.rejectValue(elemento, "field.date.rfc.format");
		}
		
	}
}
    