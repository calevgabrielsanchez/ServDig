/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Samuel Rodr�guez Grajeda
 *  @Proyecto: Gestion de Personas
 *  @Archivo:PersonaFisicaDatosBasicosValidator.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.individuo.web.validator
 *  @Fecha:17/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

/**
 * @author SRG
 * 
 */
public class PersonaFisicaBusquedaValidator extends AbstractValidator {
    
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
    private static final int LENGTH_CURP  = 18;
    private static final String REGEX_RFC_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z\\w]{3})$";
    private static final int LENGTH_RFC  = 13;
    private static final String REGEX_ID_PERSONA= "^([0-9]{1,9})$";
    private static final int LENGTH_ID_PERSONA  = 9;
//    private static final String REGEX_NSS= "^([0-9]{11})$";
//    private static final int LENGTH_NSS  = 11;

    /**
     * Metodo que valida que el formulario de busqueda de personas fisicas al menos tenga 1 filtro de busqueda
     * @param personaFisica
     * @return
     */
    public boolean validarFormularioVacio(Object personaFisica) {
        
        Fisica persona = (Fisica) personaFisica;
		
        if (persona.getIdPersona() == null 
                && StringUtils.isBlank(persona.getCurp()) 
                && StringUtils.isBlank(persona.getNombre()) 
                && StringUtils.isBlank(persona.getRfc()) 
                && StringUtils.isBlank(persona.getPrimerApellido()) 
                && StringUtils.isBlank(persona.getSegundoApellido()) 
                && (persona.getSexo().getIdSexo().equals(-1) || persona.getSexo().getIdSexo().equals(0)) 
                && persona.getFechaNacimiento() == null
                && (persona.getLugarNacimiento().getClave().equals("-1") || persona.getLugarNacimiento().getClave().equals("0"))
        ) {
            return true;
        }else{ 
            return false;
        }
        
    }
    
    @Override
    public void validate(Object personaFisica, Errors errors) {
        
    	Fisica pf = (Fisica) personaFisica;

    	if(pf.getFechaNacimiento() != null){
	        if(pf.getFechaNacimiento().compareTo(new Date()) > 0){
	        	errors.rejectValue("fechaNacimiento", "busqueda.fecha.error.mayor");
	        }
    	}
        
	    String idPersona = pf.getIdPersona() != null ? pf.getIdPersona().toString() : "";
        if(StringUtils.isNotBlank(idPersona)){
            if(idPersona.length() > LENGTH_ID_PERSONA){
                errors.rejectValue("idPersona", "field.min.length", new Object[] {new Integer(LENGTH_ID_PERSONA)}, "");
            }else{
                if(!idPersona.matches(REGEX_ID_PERSONA)){
                    errors.rejectValue("idPersona", "field.wrong.format");
                }
            }
        }
               
        if(StringUtils.isNotBlank(pf.getCurp())){
            if(pf.getCurp().length() != LENGTH_CURP){
                errors.rejectValue("curp", "field.min.length", new Object[] {new Integer(LENGTH_CURP)}, "");
            }else{
                if(!pf.getCurp().matches(REGEX_CURP_FISICA)){
                    errors.rejectValue("curp", "field.wrong.format");
                }
            }
        }
        
        if(pf.getRfc() != null && !pf.getRfc().equals("")){
        	
        	if(!Boolean.parseBoolean(pf.getBusqAprox())){
        		
        		/*
        		 * Si la busqueda no es aproximada (exacta),  se debera de validar la
        		 * longitud del RFC
        		 */
        		if(pf.getRfc().length() != LENGTH_RFC){
                    errors.rejectValue("rfc", "field.min.length", new Object[] {new Integer(LENGTH_RFC)}, "");
                }else{
                    if(!pf.getRfc().matches(REGEX_RFC_FISICA)){
                        errors.rejectValue("rfc", "field.wrong.format");
                    }
                }
        		
        	}
        	
            
        }
       
    }    

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return false;
    }

}