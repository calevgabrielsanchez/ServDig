/**
 * delta-gestionPatronal-web 25/04/2012
 * mx.gob.imss.ctirss.delta.gestion.patronal.web.utils 25/04/2012
 * SocioValidator.java
 * 13/04/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.utils;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Luci Duran Silva
 * Instituto Mexicano del Seguro Social
 */
public class SocioValidator implements Validator {
	
	private static SocioValidator instance = new SocioValidator();
	
	/**
	 * @return the instance
	 */
	public static SocioValidator getInstance() {
		return instance;
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> arg0) {
		return RepresentanteLegal.class.equals(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object arg0, Errors arg1) {
		Socio obj = (Socio) arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "idPersona", "field.required");
		boolean isNacional = obj.getEsNacional()==null ? false : obj.getEsNacional();
		if (isNacional){
//		Para socios residentes en Territorio Nacional, el RFC es obligatorio y 
//		Para socios residentes en el Extranjero el RFC no es obligatorio
			ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "rfc", "field.required");
			
		} 
		
	}

}
