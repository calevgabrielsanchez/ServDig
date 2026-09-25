package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UsuarioCurpValidator implements Validator{
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
    private static final int LENGTH_CURP  = 18;

	@Override
	public boolean supports(Class<?> arg0) {
		return Fisica.class.equals(arg0);
	}

	@Override
	public void validate(Object usuario, Errors errors) {
		
		Usuario usuarioV = (Usuario) usuario;

    	
		//Validacion de CURP
        if(StringUtils.isNotBlank(usuarioV.getUsuario())){
            if(usuarioV.getUsuario().length() != LENGTH_CURP){
                errors.rejectValue("usuario", "field.min.length", new Object[] {new Integer(LENGTH_CURP)}, "");
            }else{
                if(!usuarioV.getUsuario().matches(REGEX_CURP_FISICA)){
                    errors.rejectValue("usuario", "field.wrong.format");
                }
            }
        }else{
        	errors.rejectValue("usuario", "field.required");
        }
	}
}
