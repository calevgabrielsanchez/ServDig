/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: gestionAsegurados
 *  @Archivo:LoginValidator.java
 *  @Paquete:mx.gob.imss.gestionAsegurados.web.validator
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.Usuario;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Lucio Duran Silva
 * 
 */
public class LoginValidator implements Validator {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.equals(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object usuario, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"field.required");
	}
}
