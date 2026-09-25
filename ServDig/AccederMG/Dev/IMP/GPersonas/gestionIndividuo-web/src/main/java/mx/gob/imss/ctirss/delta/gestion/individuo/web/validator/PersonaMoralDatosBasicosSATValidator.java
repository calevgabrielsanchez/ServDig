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

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * @author Samuel Rodríguez Grajeda
 *
 */
public class PersonaMoralDatosBasicosSATValidator extends AbstractValidator {

	private static final int LENGTH_RFC  = 12;
	private static final String REGEX_RFC_MORAL = "^([a-zA-Z\u0026]{3})\\d{6}([\\w]{3})$";
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Moral.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object personaMoral, Errors errors) {
	
		Moral pm = (Moral) personaMoral;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rfcSat", "field.required");
		
		if(pm.getRfcSat() != null && !pm.getRfcSat().equals("")){
			if(pm.getRfcSat().length() != LENGTH_RFC){
				errors.rejectValue("rfcSat", "field.min.length", new Object[] {new Integer(LENGTH_RFC)}, "");
			}else{
				if(! pm.getRfcSat().matches(REGEX_RFC_MORAL)){
					errors.rejectValue("rfcSat", "field.wrong.format");
				}
			}
		}
	}

}