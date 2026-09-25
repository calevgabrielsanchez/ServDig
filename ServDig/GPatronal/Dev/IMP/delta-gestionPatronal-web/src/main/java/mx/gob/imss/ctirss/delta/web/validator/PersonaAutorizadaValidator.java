package mx.gob.imss.ctirss.delta.web.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.framework.base.web.ObjectError;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PersonaAutorizadaValidator implements Validator{
	private static final String REGEX_RFC_FISICA = "^([a-zA-Z\u00D1\u00F1]{4})\\d{6}([a-zA-Z\\w]{3})$";
    private static final int LENGTH_RFC  = 13;
    protected static final String KEY_CODE_ERROR_FIELDS = "erroresCaptura";
    private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
    private static final int LENGTH_CURP  = 18;

	@Override
	public boolean supports(Class<?> clazz) {
		return Fisica.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	}

	
	
	public Map<String, Object> validate(Object target, Errors errors, 
			int indicePersonaAut, HttpServletResponse response,
			MessageSource messageSource) {
		Fisica pf =(Fisica)target;
		List<ObjectError > erroresList = new ArrayList<ObjectError>();
		Map<String, Object> result=new HashMap<String, Object>();
		if(StringUtils.isBlank(pf.getRfc())){
			ObjectError e = new ObjectError();
            e.setCampo("rfcPA"+indicePersonaAut);
            e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
            erroresList.add(e);
		
		}else if(pf.getRfc().length() != LENGTH_RFC) {
			ObjectError e = new ObjectError();
            e.setCampo("rfcPA"+indicePersonaAut);
            e.setMensaje(messageSource.getMessage("field.length", new Object[] {new Integer(LENGTH_RFC)},Locale.getDefault()));
            erroresList.add(e);
		
    		
    	} else if(!pf.getRfc().matches(REGEX_RFC_FISICA)){
    		ObjectError e = new ObjectError();
            e.setCampo("rfcPA"+indicePersonaAut);
            e.setMensaje(messageSource.getMessage("field.wrong.format", null,Locale.getDefault()));
            erroresList.add(e);
		
		}
		
		if(StringUtils.isNotBlank(pf.getCurp())){
            if(pf.getCurp().length() != LENGTH_CURP){
            	ObjectError e = new ObjectError();
                e.setCampo("curpPA"+indicePersonaAut);
                e.setMensaje(messageSource.getMessage("field.length", new Object[] {new Integer(LENGTH_CURP)},Locale.getDefault()));
                erroresList.add(e);
    		
            }else{
                if(!pf.getCurp().matches(REGEX_CURP_FISICA)){
                	ObjectError e = new ObjectError();
                    e.setCampo("curpPA"+indicePersonaAut);
                    e.setMensaje(messageSource.getMessage("field.wrong.format", null,Locale.getDefault()));
                    erroresList.add(e);
        		
                }
            }
        }else{
        	ObjectError e = new ObjectError();
            e.setCampo("curpPA"+indicePersonaAut);
            e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
            erroresList.add(e);
        }
    
		
		
		if(!erroresList.isEmpty()){
			result.put("mensajeError", "La información proporcionada presenta errores, favor de verificarla.");
			result.put(KEY_CODE_ERROR_FIELDS, erroresList);
			response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED );
		}
		return result;
	}


}
