/**
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author vanderluk
 * 
 */
public class TramiteAseguradoExternoValidator implements Validator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> claz) {
		return TramiteAsegurado.class.equals(claz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fisica.nombre",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"fisica.primerApellido", "field.required");
	}

}
