/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portal
 *  @Archivo:LoginValidator.java
 *  @Paquete:mx.gob.imss.portal.web.validator
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.distss.portal.vigencia.grupo.controller.validator;

import mx.gob.imss.ctirss.delta.model.Usuario;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Lucio Duran Silva
 * 
 */
public class LoginValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object usuario, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"field.required");

	}

}
