package mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ActualizacionDatosNssValidator implements Validator {

	private static final int LENGTH_NSS = 11;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Fisica.class.equals(clazz);
	}

	@Override
	public void validate(Object personaFisica, Errors errors) {
		
		Fisica pf = (Fisica) personaFisica;
		
		if(StringUtils.isBlank(pf.getNss())) {
			errors.rejectValue("nss", "field.required", new Object[] {}, "");
		} else {
			if(pf.getNss().length() != LENGTH_NSS) {
				errors.rejectValue("nss", "field.min.length", new Object[] { new Integer(LENGTH_NSS) }, "");
			}
		}

	}

}
