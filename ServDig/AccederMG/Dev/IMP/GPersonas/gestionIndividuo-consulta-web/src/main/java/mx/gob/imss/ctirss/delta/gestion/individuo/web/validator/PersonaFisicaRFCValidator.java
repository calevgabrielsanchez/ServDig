package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PersonaFisicaRFCValidator implements Validator {

    private static final String REGEX_RFC_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z\\w]{3})$";
    private static final int LENGTH_RFC  = 13;

	@Override
	public boolean supports(Class<?> arg0) {
		return Fisica.class.equals(arg0);
	}

	@Override
	public void validate(Object personaFisica, Errors errors) {
		
		Fisica pf = (Fisica) personaFisica;

    	
		
        //Validacion de RFC
        if(pf.getRfc() != null && !pf.getRfc().equals("")){
        	
        	if(pf.getRfc().length() != LENGTH_RFC){
                errors.rejectValue("rfc", "field.min.length", new Object[] {new Integer(LENGTH_RFC)}, "");
            }else{
                if(!pf.getRfc().matches(REGEX_RFC_FISICA)){
                    errors.rejectValue("rfc", "field.wrong.format");
                }
            }
            
        }else{
        	errors.rejectValue("rfc", "field.required");
        }
		
		
        
        
		
	}

}
