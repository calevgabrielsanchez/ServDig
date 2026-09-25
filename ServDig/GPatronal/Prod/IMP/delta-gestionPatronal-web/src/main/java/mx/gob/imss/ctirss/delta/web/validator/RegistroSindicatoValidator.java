/**
 * delta-gestionPatronal-web14/05/2012
 * mx.gob.imss.ctirss.delta.web.validator14/05/2012
 * RegistroSindicatoValidator.java
 * 14/05/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Luci Duran Silva
 * Instituto Mexicano del Seguro Social
 */
public class RegistroSindicatoValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return RegistroSindicato.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numReferenciadocRegistro", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autoridadLaboral", "field.required");
	}

}
