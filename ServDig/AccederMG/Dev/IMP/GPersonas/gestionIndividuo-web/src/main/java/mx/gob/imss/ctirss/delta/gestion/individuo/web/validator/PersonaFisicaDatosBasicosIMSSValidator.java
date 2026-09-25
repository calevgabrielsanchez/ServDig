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

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * @author Samuel Rodríguez Grajeda
 *
 */
public class PersonaFisicaDatosBasicosIMSSValidator extends AbstractValidator {
    
    private static final String REGEX_FECHA = "^(0[1-9]|[12][0-9]|3[01])[//](0[1-9]|1[012])[//](19|20)[0-9][0-9]$";
    private static final String FORMATO_FECHA = "dd/MM/yyyy";

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
	    
    	if(pf.getFechaNacimiento() != null){
	        if(pf.getFechaNacimiento().compareTo(new Date()) > 0){
	        	errors.rejectValue("fechaNacimiento", "busqueda.fecha.error.mayor");
	        }
    	}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "primerApellido", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sexo.idSexo", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lugarNacimiento.clave", "field.required");
		if(pf.getSexo().getIdSexo() == null || pf.getSexo().getIdSexo() == -1){
			errors.rejectValue("sexo.idSexo", "field.required", new Object[]{}, "");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fechaNacimiento", "field.required");
		if(pf.getFechaNacimiento() != null){
		    if(!new SimpleDateFormat(FORMATO_FECHA).format(pf.getFechaNacimiento()).matches(REGEX_FECHA)){
		        errors.rejectValue("fechaNacimiento", "field.wrong.format", new Object[]{}, "");
		    }
		}

		if(Utilerias.isBlank(Integer.parseInt(pf.getLugarNacimiento().getClave()))) {
			errors.rejectValue("lugarNacimiento.clave", "field.required", new Object[]{}, "");
		}
		
	}

}