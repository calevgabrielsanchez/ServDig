/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Samuel Rodríguez Grajeda
 *  @Proyecto: Gestion de Personas
 *  @Archivo:PersonaFisicaDatosBasicosValidator.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.individuo.web.validator
 *  @Fecha:17/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * @author Samuel Rodríguez Grajeda
 *
 */
public class PersonaFisicaDatosBasicosSATValidator extends AbstractValidator {

	private static final String REGEX_RFC_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z\\w]{3})$";
	private static final int LENGTH_RFC  = 13;
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Fisica.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object personaFisica, Errors errors) {
		
		Fisica pf = (Fisica) personaFisica;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rfc", "field.required");
		if(pf.getRfc() != null && !pf.getRfc().equals("")){
			if(pf.getRfc().length() != LENGTH_RFC){
				errors.rejectValue("rfc", "field.min.length", new Object[] {new Integer(LENGTH_RFC)}, "");
			}else{
				if(! pf.getRfc().matches(REGEX_RFC_FISICA)){
					errors.rejectValue("rfc", "field.wrong.format");
				}
			}
		}
	}

}