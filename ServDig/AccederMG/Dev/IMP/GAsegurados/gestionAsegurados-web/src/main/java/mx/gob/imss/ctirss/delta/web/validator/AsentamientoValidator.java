/**
 * gestionAsegurados-web04/04/2012
 * mx.gob.imss.ctirss.delta.web.validator04/04/2012
 * AsentamientoValidator.java
 * 04/04/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Lucio Duran Silva Instituto Mexicano del Seguro Social
 */
public class AsentamientoValidator implements Validator {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> claz) {
		return Asentamiento.class.equals(claz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"localidad.municipio.entidadFederativa.clave",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"localidad.municipio.clave", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clave",
				"field.required");

	}
}
