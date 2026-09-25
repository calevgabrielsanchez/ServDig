

/**
 * gestionMediosContacto-web15/05/2012
 * mx.gob.imss.ctirss.delta.web.validator15/05/2012
 * MedioContactoValidator.java
 * 15/05/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.beans.MedioContactoFormWrapper;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Lucio Duran Silva 
 * Instituto Mexicano del Seguro Social
 */
public class MedioContactoValidator implements Validator {
	
	
	 private static final String EMAIL_PATTERN = 
             "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return MedioContactoFormWrapper.class.equals(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {

//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
//				"telefonoFijo.numero", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
//				"telefonoFijo.claveLada", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
//				"telefonoFijo.extension", "field.required");
//
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
//				"telefonoMovil.numero", "field.required");
//
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
//				"correoElectronico.correo", "field.required");
		
			
		MedioContactoFormWrapper medios = (MedioContactoFormWrapper)arg0;
		
		//Validamos que el correo sea valido
		if(medios.getCorreoElectronico() != null){
			if(medios.getCorreoElectronico().getCorreo() != null && !medios.getCorreoElectronico().getCorreo().isEmpty()){
				
				if(!medios.getCorreoElectronico().getCorreo().matches(EMAIL_PATTERN)){
					errors.rejectValue("correoElectronico.correo", "field.wrong.format");
				}
				
			}
		}

	}

}