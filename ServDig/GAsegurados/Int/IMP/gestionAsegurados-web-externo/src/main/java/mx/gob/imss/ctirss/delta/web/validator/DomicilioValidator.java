/**
 * gestionAsegurados-web04/04/2012
 * mx.gob.imss.ctirss.delta.web.validator04/04/2012
 * DomicilioValidator.java
 * 04/04/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Lucio Duran Silva
 * Instituto Mexicano del Seguro Social
 */
public class DomicilioValidator implements Validator{

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Domicilio.class.equals(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object data, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codigoPostal.codigoPostal", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "asentamiento.clave", "field.required");
	}

}
