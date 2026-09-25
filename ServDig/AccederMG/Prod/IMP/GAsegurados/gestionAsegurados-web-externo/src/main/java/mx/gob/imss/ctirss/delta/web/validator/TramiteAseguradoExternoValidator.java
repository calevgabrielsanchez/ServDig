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
public class TramiteAseguradoExternoValidator implements Validator{

	
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> claz) {
		// TODO Auto-generated method stub
		return TramiteAsegurado.class.equals(claz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fisica.nombre", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fisica.primerApellido", "field.required");
		
		
	}
	
}
