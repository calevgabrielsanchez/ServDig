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

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

/**
 * @author Samuel Rodr�guez Grajeda
 *
 */
public class PersonaFisicaRegistro2Validator extends AbstractValidator {
	
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
	private static final int LENGTH_CURP  = 18;
	private static final String REGEX_RFC_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z\\w]{3})$";
	private static final int LENGTH_RFC  = 13;
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
	    
	    
	    if(errors.getErrorCount() == 0){
	    
		    if(!(
			       StringUtils.isNotBlank(pf.getCurp()) || 
			       StringUtils.isNotBlank(pf.getRfc()) ||
			       StringUtils.isNotBlank(pf.getNombre()) ||
			       StringUtils.isNotBlank(pf.getPrimerApellido()) ||
				   (pf.getSexo().getIdSexo() != null && pf.getSexo().getIdSexo() != -1) || 
				   pf.getFechaNacimiento() != null ||
				   (pf.getLugarNacimiento().getClave() != null && !pf.getLugarNacimiento().getClave().equals("-1"))
			   )){
		    	errors.rejectValue("errorFormGeneral", "msg.error.formulario.vacio", new Object[]{}, "");
		    }else{
		    
		    	if(pf.getFechaNacimiento() != null){
			        if(pf.getFechaNacimiento().compareTo(new Date()) > 0){
			        	errors.rejectValue("fechaNacimiento", "busqueda.fecha.error.mayor");
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
					if(pf.getRfc().length() != LENGTH_RFC){
						errors.rejectValue("rfc", "field.min.length", new Object[] {new Integer(LENGTH_RFC)}, "");
					}else{
						if(! pf.getRfc().matches(REGEX_RFC_FISICA)){
							errors.rejectValue("rfc", "field.wrong.format");
						}
					}
				}
				
				if(pf.getFechaNacimiento() != null){
				    if(!new SimpleDateFormat(FORMATO_FECHA).format(pf.getFechaNacimiento()).matches(REGEX_FECHA)){
				        errors.rejectValue("fechaNacimiento", "field.wrong.format", new Object[]{}, "");
				    }
				}
	
		    }
		    
		}else{
			errors.rejectValue("errorFormGeneral", "msg.error.general", new Object[] {errors.getFieldErrors()}, "");
		}
	    
	}
	
}