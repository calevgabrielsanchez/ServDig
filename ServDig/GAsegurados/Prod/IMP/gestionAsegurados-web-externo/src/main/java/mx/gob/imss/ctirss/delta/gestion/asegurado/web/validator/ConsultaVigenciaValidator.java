/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Samuel Rodríguez Grajeda
 *  @Proyecto: Gestion de Personas
 *  @Archivo:PersonaFisicaDatosBasicosValidator.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.individuo.web.validator
 *  @Fecha:17/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;

/**
 * @author Samuel Rodríguez Grajeda
 * 
 */
public class ConsultaVigenciaValidator extends AbstractValidator {

	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
	private static final int LENGTH_CURP = 18;
	private static final String REGEX_NSS = "[0-9]*";
	private static final int LENGTH_NSS = 11;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	protected final Log log = LogFactory.getLog(getClass());
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Fisica.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
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
		
		if (StringUtils.isBlank(pf.getNss())) {
			errors.rejectValue("nss", "field.required");
		} else {
			if (pf.getNss().length() != LENGTH_NSS) {
				errors.rejectValue("nss", "field.min.length",
						new Object[] { new Integer(LENGTH_NSS) }, "");
			} else if (!pf.getNss().matches(REGEX_NSS)) {
				errors.rejectValue("nss", "field.wrong.format");
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
		 * correctamente, siempre y cuando ambos sean válidos
		 */
		if (correoValido && correoConfirmacionValido
				&& !pf.getCorreoElectronico().getCorreo()
						.equalsIgnoreCase(pf.getCorreoElectronicoFiscal().getCorreo())) {
			errors.rejectValue("correoElectronico.correo","error.confirmacion.correo");
		}
	}

}