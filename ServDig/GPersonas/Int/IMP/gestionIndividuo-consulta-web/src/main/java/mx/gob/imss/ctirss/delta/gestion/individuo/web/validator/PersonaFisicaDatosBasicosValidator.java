package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PersonaFisicaDatosBasicosValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Fisica.class.equals(arg0);
	}

	@Override
	public void validate(Object personaFisica, Errors errors) {

        //Validaciones de nombre
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "field.required");
        
        //Validacion de primer apellido
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "primerApellido", "field.required");
        
        //Validacion de segundo apellido
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "segundoApellido", "field.required");
        
		
	}

}
