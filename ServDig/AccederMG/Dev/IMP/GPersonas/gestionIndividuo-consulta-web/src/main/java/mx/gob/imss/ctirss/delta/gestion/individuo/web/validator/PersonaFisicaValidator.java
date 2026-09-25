/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author vanderluk
 *
 */
public class PersonaFisicaValidator implements Validator {

    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static final String REGEX_FECHA = "^(0[1-9]|[12][0-9]|3[01])[//](0[1-9]|1[012])[//](19|20)[0-9][0-9]$";
	
	@Override
	public boolean supports(Class<?> arg0) {
		return Fisica.class.equals(arg0);
	}
	
	
	@Override
	public void validate(Object personaFisica, Errors errors) {
		
		Fisica pf = (Fisica) personaFisica;

    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fechaNacimiento", "field.required");
		
		if (pf.getSexo().getIdSexo() == null || pf.getSexo().getIdSexo().intValue() == -1) {
        	errors.rejectValue("sexo.idSexo", "field.required");
        }
		
        
		if (pf.getLugarNacimiento().getClave() == null
				|| pf.getLugarNacimiento().getClave().equals("-1")) {
        	errors.rejectValue("lugarNacimiento.clave", "field.required");
        }
        
        
        if(pf.getFechaNacimiento() != null){
	        if(pf.getFechaNacimiento().compareTo(new Date()) > 0){
	        	errors.rejectValue("fechaNacimiento", "busqueda.fecha.error.mayor");
	        }
    	}
        
        if(pf.getFechaNacimiento() != null){
		    if(!new SimpleDateFormat(FORMATO_FECHA).format(pf.getFechaNacimiento()).matches(REGEX_FECHA)){
		        errors.rejectValue("fechaNacimiento", "field.wrong.format", new Object[]{}, "");
		    }
		}
        
        
		
	}

}
