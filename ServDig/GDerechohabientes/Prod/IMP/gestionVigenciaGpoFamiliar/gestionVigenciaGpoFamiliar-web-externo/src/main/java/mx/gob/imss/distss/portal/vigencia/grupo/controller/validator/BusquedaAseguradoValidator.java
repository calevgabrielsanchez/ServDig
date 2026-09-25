package mx.gob.imss.distss.portal.vigencia.grupo.controller.validator;

import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BusquedaAseguradoValidator implements Validator {

	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
    private static final int LENGTH_CURP  = 18;
    
	@Override
	public boolean supports(Class<?> arg0) {
		return AsignacionNSS.class.equals(arg0);
	}

	@Override
	public void validate(Object form, Errors errors) {
		AsignacionNSS asignacion = (AsignacionNSS) form;
		
		//Verificamos que se haya llenado al menos uno
		if(StringUtils.isBlank(asignacion.getNss())) {
			errors.rejectValue("nss", "field.required");
		} else {
				if(asignacion.getNss().length() < 10) {
					 errors.rejectValue("nss", "field.min.length", new Object[] {new Integer(10)}, "");
				}
		}
	}

}
