/**
 * gestionDomicilios-web04/04/2012
 * mx.gob.imss.ctirss.delta.web.validator04/04/2012
 * AsentamientoValidator.java
 * 04/04/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Lucio Duran Silva
 * Instituto Mexicano del Seguro Social
 */
public class AsentamientoValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> claz) {
		return Asentamiento.class.equals(claz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		
		Asentamiento a = (Asentamiento)arg0;
		
		if(StringUtils.isBlank(a.getLocalidad().getMunicipio().getEntidadFederativa().getClave()) 
				|| a.getLocalidad().getMunicipio().getEntidadFederativa().getClave().equals("-1")){
			errors.rejectValue("localidad.municipio.entidadFederativa.clave", "field.required");
		}
		
		if(StringUtils.isBlank(a.getLocalidad().getMunicipio().getClave()) 
				|| a.getLocalidad().getMunicipio().getClave().equals("-1")){
			errors.rejectValue("localidad.municipio.clave", "field.required");
		}
		
		if(StringUtils.isBlank(a.getClave()) 
				|| a.getClave().equals("-1")){
			errors.rejectValue("clave", "field.required");
		}
	}
}