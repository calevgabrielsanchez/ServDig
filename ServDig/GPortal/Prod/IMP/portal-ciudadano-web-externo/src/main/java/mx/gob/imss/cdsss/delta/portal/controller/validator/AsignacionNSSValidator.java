package mx.gob.imss.cdsss.delta.portal.controller.validator;

import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AsignacionNSSValidator extends AbstractValidator implements
		Validator {

	private final int LENGTH_NSS = 11;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return AsignacionNSS.class.equals(arg0);
	}

	@Override
	public void validate(Object asignacion, Errors errors) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS) asignacion;
		
		if(asignacionNSS.getNss() == null || asignacionNSS.getNss().trim().length() == 0) {
			errors.rejectValue("nss", "field.required");
		} else if(asignacionNSS.getNss().trim().length() < 11) {
			errors.rejectValue("nss", "field.min.length",
					new Object[] { new Integer(LENGTH_NSS) }, "");
		}
	}

}
