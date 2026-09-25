/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Samuel Rodr�guez Grajeda
 *  @Proyecto: Gestion de Personas
 *  @Archivo:PersonaFisicaDatosBasicosValidator.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.individuo.web.validator
 *  @Fecha:17/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * @author Samuel Rodr�guez Grajeda
 *
 */
public class PersonaFisicaDatosBasicosRENAPOValidator extends AbstractValidator {

	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
	private static final int LENGTH_CURP  = 18;

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Fisica.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object personaFisica, Errors errors) {
		
		Fisica pf = (Fisica) personaFisica;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "curpRenapo", "field.required");
		if(StringUtils.isNotBlank(pf.getCurpRenapo())){
			if(pf.getCurpRenapo().length() != LENGTH_CURP){
				errors.rejectValue("curpRenapo", "field.min.length", new Object[] {new Integer(LENGTH_CURP)}, "");
			}else{
				if(!pf.getCurpRenapo().matches(REGEX_CURP_FISICA)){
					errors.rejectValue("curpRenapo", "field.wrong.format");
				}
			}
		}
		
	}

}