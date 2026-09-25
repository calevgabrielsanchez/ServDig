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

import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;


/**
 * @author SRG
 *
 */
public class PersonaMoralBusquedaValidator extends AbstractValidator {

    private static final String REGEX_RFC_MORAL = "^([a-zA-Z\u0026]{3})\\d{6}([\\w]{3})$";
    private static final int LENGTH_RFC  = 12;
    private static final String REGEX_ID_PERSONA= "^([0-9]{1,9})$";
    private static final int LENGTH_ID_PERSONA  = 9;
    
	public boolean ValidarFormularioVacio(Moral persona) {
		
        if (
                persona.getIdPersona() == null 
                && StringUtils.isBlank(persona.getRazonSocial()) 
                && StringUtils.isBlank(persona.getRfc()) 
                && StringUtils.isBlank(persona.getActaConstitutiva()) 
                && persona.getFechaCreacion() == null
                && Utilerias.isBlank(persona.getTipoSociedad().getIdTipoSociedad())
        ){			
			return true;
		}else{
			return false;
		}
			
	}
	
    @Override
    public void validate(Object personaMoral, Errors errors) {
        
        Moral pm = (Moral) personaMoral;
        
    	if(pm.getFechaCreacion() != null){
	        if(pm.getFechaCreacion().compareTo(new Date()) > 0){
	        	errors.rejectValue("fechaCreacion", "busqueda.fecha.error.mayor");
	        }
    	}
        
        String idPersona = pm.getIdPersona() != null ? pm.getIdPersona().toString() : "";
        if(StringUtils.isNotBlank(idPersona)){
            if(idPersona.length() > LENGTH_ID_PERSONA){
                errors.rejectValue("idPersona", "field.min.length", new Object[] {new Integer(LENGTH_ID_PERSONA)}, "");
            }else{
                if(!idPersona.matches(REGEX_ID_PERSONA)){
                    errors.rejectValue("idPersona", "field.wrong.format");
                }
            }
        }
        
        
        if(!Boolean.parseBoolean(pm.getBusqAprox())){
    		
    		/*
    		 * Si la busqueda no es aproximada (exacta),  se debera de validar la
    		 * longitud del RFC
    		 */
        	 if(pm.getRfc() != null && !pm.getRfc().equals("")){
                 if(pm.getRfc().length() != LENGTH_RFC){
                     errors.rejectValue("rfc", "field.min.length", new Object[] {new Integer(LENGTH_RFC)}, "");
                 }else{
                     if(!pm.getRfc().matches(REGEX_RFC_MORAL)){
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