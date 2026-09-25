package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PersonaRFCValidator implements Validator {
	
    private static final String REGEX_RFC_FISICA = "^([a-zA-Z\u00E1\u00E9\u00ED\u00F3\u00FA\u00C1\u00C9\u00CD\u00D3\u00DA" +
    	"\u00E4\u00EB\u00EF\u00F6\u00FC\u00C4\u00CB\u00CF\u00D6\u00DC\u00D1\u00F1]{4})\\d{6}([a-zA-Z\\w]{3})$";
    private static final String REGEX_RFC_MORAL = "^([a-zA-Z\u00E1\u00E9\u00ED\u00F3\u00FA\u00C1\u00C9\u00CD\u00D3\u00DA" +
    	"\u00E4\u00EB\u00EF\u00F6\u00FC\u00C4\u00CB\u00CF\u00D6\u00DC\u0026\u00D1\u00F1\u005F]{3})\\d{6}([\\w]{3})$";
    private static final int LENGTH_RFC  = 13;
    private static final int LENGTH_RFC_MORAL  = 12;

	@Override
	public boolean supports(Class<?> arg0) {
		return Persona.class.equals(arg0);
	}

	@Override
	public void validate(Object persona, Errors errors) {
		
		Persona pf = (Persona) persona;
		//Validacion de RFC
		if(StringUtils.isNotBlank(pf.getRfc())){
        	pf.setRfc(pf.getRfc().toUpperCase().trim());
        	if(pf.getTipoPersona() != null && pf.getTipoPersona().getIdTipoPersona() != null) {
	        	if(pf.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
	        		if(pf.getRfc().length() != LENGTH_RFC) {
	        			errors.rejectValue("rfc", "field.length", new Object[] {new Integer(LENGTH_RFC)}, "");
	        		} else {
	        			if(!pf.getRfc().matches(REGEX_RFC_FISICA)){
	                        errors.rejectValue("rfc", "field.wrong.format");
	                    }
	        		}
	            } else{
	            	if(pf.getRfc().length() != LENGTH_RFC_MORAL) {
	            		errors.rejectValue("rfc", "field.length", new Object[] {new Integer(LENGTH_RFC_MORAL)}, "");
	            	} else {
	        			if(!pf.getRfc().matches(REGEX_RFC_MORAL)){
	                        errors.rejectValue("rfc", "field.wrong.format");
	                    }
	        		}
	            }
        	}
        }else{
        	errors.rejectValue("rfc", "field.required");
        }		
	}
	

}
