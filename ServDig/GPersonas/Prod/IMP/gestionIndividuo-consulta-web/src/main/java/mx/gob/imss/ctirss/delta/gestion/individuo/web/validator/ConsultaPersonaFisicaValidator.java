package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ConsultaPersonaFisicaValidator implements Validator {
	
	
	
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
    private static final int LENGTH_CURP  = 18;
    private static final String REGEX_RFC_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z\\w]{3})$";
    private static final int LENGTH_RFC  = 13;
    private static final String REGEX_FECHA = "^(0[1-9]|[12][0-9]|3[01])[//](0[1-9]|1[012])[//](19|20)[0-9][0-9]$";
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    
    private int TIPO_CONSULTA = 1;

	@Override
	public boolean supports(Class<?> arg0) {
		return Fisica.class.equals(arg0);
	}

	
	
	public void validateRenapo(Object personaFisica, Errors errors) {
		this.TIPO_CONSULTA = 1;
		this.validate(personaFisica, errors);
	}
	
	
	public void validateSat(Object personaFisica, Errors errors) {
		this.TIPO_CONSULTA = 2;
		this.validate(personaFisica, errors);
	}
	
	
	@Override
	public void validate(Object personaFisica, Errors errors) {
		
		Fisica pf = (Fisica) personaFisica;

    	
		//Validacion de CURP
        if(StringUtils.isNotBlank(pf.getCurp())){
            if(pf.getCurp().length() != LENGTH_CURP){
                errors.rejectValue("curp", "field.min.length", new Object[] {new Integer(LENGTH_CURP)}, "");
            }else{
                if(!pf.getCurp().matches(REGEX_CURP_FISICA)){
                    errors.rejectValue("curp", "field.wrong.format");
                }
            }
        }else{
        	errors.rejectValue("curp", "field.required");
        }
        
        
        /**
         * Si el tipo de consulta es 2 se debe de validar el RFC ya que se requiere para
         * la consulta al SAT.
         */
        if(this.TIPO_CONSULTA == 2){
            	
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
        
        
       
		
		
        
        //Validaciones de nombre
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "field.required");
        
        //Validacion de primer apellido
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "primerApellido", "field.required");
        
        //Validacion de segundo apellido
        
        /*
         * Se comenta la validacion del segundo apellido ya que es opcional....
         */
        
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "segundoApellido", "field.required");
        
        
        
        /*Validacion de Fecha de nacimiento*/
        if(pf.getFechaNacimiento() != null){
	        if(pf.getFechaNacimiento().compareTo(new Date()) > 0){
	        	errors.rejectValue("fechaNacimiento", "busqueda.fecha.error.mayor");
	        }
	        else if(!new SimpleDateFormat(FORMATO_FECHA).format(pf.getFechaNacimiento()).matches(REGEX_FECHA)){
		        errors.rejectValue("fechaNacimiento", "field.wrong.format", new Object[]{}, "");
		    }
    	}else{
    		errors.rejectValue("fechaNacimiento", "field.required");
    	}
        
        
        
        /*Validacion  de lugar de nacimiento*/
        if(pf.getLugarNacimiento().getClave() == null || pf.getLugarNacimiento().getClave().equals("-1") ){
        	errors.rejectValue("lugarNacimiento.clave", "field.required");
        }
        
        /*Validacion de Sexo*/
        if(pf.getSexo().getIdSexo() == null || pf.getSexo().getIdSexo() == -1){
        	errors.rejectValue("sexo.idSexo", "field.required");
        }
        
		
	}

}
