package mx.gob.imss.cit.cda.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;

public class CorreccionValidator extends AbstractValidator {

	private static final String REGEX_CURP_FISICA = "^([A-Z]{4})\\d{6}([a-zA-Z]{6}\\d{2})$";
	private static final int LENGTH_CURP = 18;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	protected final Log log = LogFactory.getLog(getClass());

	@Override
	public boolean supports(Class<?> clazz) {
		return Fisica.class.equals(clazz);
	}
	
	public void validateCurp(String curp, Errors errors){
		if (StringUtils.isBlank(curp)) {
			errors.rejectValue("curp", "field.required");
		} else {
			if (curp.length() != LENGTH_CURP) {
				errors.rejectValue("curp", "field.min.length",
						new Object[] { new Integer(LENGTH_CURP) }, "");
			} else if (!curp.matches(REGEX_CURP_FISICA)) {
				errors.rejectValue("curp", "field.wrong.format");
			}
		}
	}

	@Override
	public void validate(Object personaFisica, Errors errors) {
		
		log.debug("Validnado datos de entrada");

		Fisica pf = (Fisica) personaFisica;
		boolean correoValido = false;
		boolean correoConfirmacionValido = false;
		
		
		if (StringUtils.isBlank(pf.getCurp())) {
			errors.rejectValue("curp", "field.required");
		} else {
			if (pf.getCurp().length() != LENGTH_CURP) {
				errors.rejectValue("curp", "field.min.length",
						new Object[] { new Integer(LENGTH_CURP) }, "");
			} else if (!pf.getCurp().matches(REGEX_CURP_FISICA)) {
				errors.rejectValue("curp", "field.wrong.format");
			}
		}
		
		if (pf.getCorreoElectronico() == null
				|| StringUtils.isBlank(pf.getCorreoElectronico().getCorreo())) {
			errors.rejectValue("correoElectronico.correo", "field.required");
		} else if (!pf.getCorreoElectronico().getCorreo()
				.matches(EMAIL_PATTERN)) {
			errors.rejectValue("correoElectronico.correo", "field.wrong.format");

		} else {
			correoValido = true;
		}
		
		if (pf.getCorreoElectronicoFiscal() == null
				|| StringUtils.isBlank(pf.getCorreoElectronicoFiscal().getCorreo())) {
			errors.rejectValue("correoElectronicoFiscal.correo", "field.required");
		} else if (!pf.getCorreoElectronicoFiscal().getCorreo()
				.matches(EMAIL_PATTERN)) {
			errors.rejectValue("correoElectronicoFiscal.correo", "field.wrong.format");
		} else {
			correoConfirmacionValido = true;
		}
		
		/*
		 * Se checa que el correo capturado haya sido confirmado
		 * correctamente, siempre y cuando ambos sean validos
		 */
		if (correoValido && correoConfirmacionValido
				&& !pf.getCorreoElectronico().getCorreo()
						.equalsIgnoreCase(pf.getCorreoElectronicoFiscal().getCorreo())) {
			errors.rejectValue("correoElectronicoFiscal.correo","error.confirmacion.correo");
		}
	}

}