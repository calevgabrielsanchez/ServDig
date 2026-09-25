/**
 * delta-gestionPatronal-web13/04/2012
 * mx.gob.imss.ctirss.delta.gestion.patronal.web.utils13/04/2012
 * RepresentanteLegalValidator.java
 * 13/04/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.utils;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Luci Duran Silva
 * Instituto Mexicano del Seguro Social
 */
public class RepresentanteLegalValidator implements Validator {
	
	private static RepresentanteLegalValidator instance = new RepresentanteLegalValidator();
	
	/**
	 * @return the instance
	 */
	public static RepresentanteLegalValidator getInstance() {
		return instance;
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return RepresentanteLegal.class.equals(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors arg1) {
//		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "rupa", "field.required");
	}

}
