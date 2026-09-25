/**
 * delta-gestionPatronal-web08/05/2012
 * mx.gob.imss.ctirss.delta.web.validator08/05/2012
 * EscrituraConstitutivaValidator.java
 * 08/05/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Luci Duran Silva
 * Instituto Mexicano del Seguro Social
 */
public class EscrituraConstitutivaValidator implements Validator{

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return EscrituraConstitutiva.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numEscritura", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numNotaria", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "folioMercantil", "field.required");
	}

}
