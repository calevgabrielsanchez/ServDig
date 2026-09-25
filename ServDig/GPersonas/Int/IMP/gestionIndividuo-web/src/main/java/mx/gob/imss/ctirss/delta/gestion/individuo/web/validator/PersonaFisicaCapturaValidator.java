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
 * @author Samuel Rodr�guez Grajeda
 *
 */
public class PersonaFisicaCapturaValidator extends AbstractValidator {
	
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";	
	private static final int LENGTH_CURP  = 18;
	
	private static final String REGEX_RFC_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z\\w]{3})$";
	private static final int LENGTH_RFC  = 13;

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
		
    	if(pf.getFechaNacimiento() != null){
	        if(pf.getFechaNacimiento().compareTo(new Date()) > 0){
	        	errors.rejectValue("fechaNacimiento", "busqueda.fecha.error.mayor");
	        }
    	}
    	
		if(pf.getCurp() != null && !pf.getCurp().equals("")){
			if(pf.getCurp().length() != LENGTH_CURP){
				errors.rejectValue("curp", "field.min.length", new Object[] {new Integer(LENGTH_CURP)}, "");
			}else{
				if(! pf.getCurp().matches(REGEX_CURP_FISICA)){
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

		/* Los datos basicos S� deben ser registrados */
		if(verificarExistenciaDatosBasicos(pf)){
			if(pf.getSexo().getIdSexo().equals(-1) || pf.getSexo().getIdSexo().equals(0)){
			    errors.rejectValue("sexo.idSexo", "field.select", new Object[]{}, "");
			}
			
	        if(pf.getLugarNacimiento().getClave().equals("-1") || pf.getLugarNacimiento().getClave().equals("0")){
	            errors.rejectValue("lugarNacimiento.clave", "field.select", new Object[]{}, "");
	        }
		}else{
			errors.rejectValue("errorFormGeneral", "msg.error.formulario.datosBasicos.incompletos", new Object[]{}, "");
		}
	}
	
	/**
	 * Cuando se ingresa alguno de los datos basicos, se debe verificar la existencia de los otros
	 * @param pf
	 * @return
	 */
	public boolean verificarExistenciaDatosBasicos(Fisica pf){
		if( StringUtils.isNotBlank(pf.getNombre()) &&
			StringUtils.isNotBlank(pf.getPrimerApellido()) &&
			(pf.getSexo().getIdSexo() != null && pf.getSexo().getIdSexo() != -1) && 
		 	 pf.getFechaNacimiento() != null &&
		     pf.getLugarNacimiento().getClave() != null
		  ){
			return true;
		}else{
			return false;
		}
	}

}