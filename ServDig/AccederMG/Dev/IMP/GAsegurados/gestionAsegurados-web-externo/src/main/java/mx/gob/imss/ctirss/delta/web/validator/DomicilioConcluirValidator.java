/**
 * gestionAsegurados-web23/04/2012
 * mx.gob.imss.ctirss.delta.web.validator23/04/2012
 * DomicilioConcluirValidator.java
 * 23/04/2012
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
public class DomicilioConcluirValidator  implements
		Validator {

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
	public void validate(Object arg0, Errors errors) {
		
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vialidadPrimaria.nombre", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numExterior1", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vialidadReferenciaPrimaria.nombre", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vialidadReferenciaPrimaria.nombre", "field.required");
		

	}

}
