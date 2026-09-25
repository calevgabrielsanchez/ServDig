package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ConsultaPersonaMoralValidator implements Validator {
	
    private static final String REGEX_RFC_MORAL = "^([a-zA-Z]{3})\\d{6}([a-zA-Z0-9]{3})$";
    private static final int LENGTH_RFC  = 12;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return Moral.class.equals(arg0);
	}

	@Override
	public void validate(Object personaMoral, Errors errors) {
		
		Moral pf = (Moral) personaMoral;

        
        //Validacion de RFC
        if(pf.getRfc() != null && !pf.getRfc().equals("")){
        	
        	if(pf.getRfc().length() != LENGTH_RFC){
                errors.rejectValue("rfc", "field.min.length", new Object[] {new Integer(LENGTH_RFC)}, "");
            }else{
                if(!pf.getRfc().matches(REGEX_RFC_MORAL)){
                    errors.rejectValue("rfc", "field.wrong.format");
                }
            }
            
        }else{
        	errors.rejectValue("rfc", "field.required");
        }
        
        //Validacion de FECHA DE CREACION
        if(pf.getFechaCreacion() == null){
        	errors.rejectValue("fechaCreacion", "field.required");
        }
        
        if(pf.getTipoSociedad() == null ){
        	errors.rejectValue("tipoSociedad.idTipoSociedad", "field.required");
        }
		
		
        
        //Validaciones de Raz—n Social
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "razonSocial", "field.required");
        
        //Validacion de primer Acta Constitutiva
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actaConstitutiva", "field.required");
        
		
	}

}
