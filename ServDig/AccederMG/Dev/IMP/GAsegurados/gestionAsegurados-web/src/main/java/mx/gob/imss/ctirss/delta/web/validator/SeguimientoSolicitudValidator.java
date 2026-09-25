/**
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author José Carlos Ortega Romano
 * Instituto Mexicano del Seguro Social
 */
public class SeguimientoSolicitudValidator implements Validator {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Solicitud.class.equals(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object solicitud, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "noFolioSolicitud", "field.required");
	}
}
