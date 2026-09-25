package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PersonaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Persona.class.equals(clazz);
	}

	@Override
	public void validate(Object persona, Errors errores) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errores, "rfc", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errores, "tipoPersona.idTipoPersona", "field.required");
	}

}
