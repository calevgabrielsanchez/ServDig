/**
 * gestionAsegurados-web 26/06/2012
 * mx.gob.imss.ctirss.delta.web.validator
 * AsentamientoValidator.java
 * 26/06/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Lucio Duran Silva
 * Instituto Mexicano del Seguro Social
 */
public class TramiteAseguradoValidator implements Validator {
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> claz) {
		// TODO Auto-generated method stub
		return TramiteAsegurado.class.equals(claz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fisica.nombre", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fisica.primerApellido", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serie.idSerie", "field.required");
		
		TramiteAsegurado ta = (TramiteAsegurado) arg0;
		
		if(ta.getSerie().getIdSerie().equals(new Long(-1))){
			errors.rejectValue("serie.idSerie", "serie.vacia", new Object[]{}, "");
		}else{
			if(ta.getSerie().getTipoSerie().getIdTipoSerie().equals(new Integer(3))){
				if(ta.getSerie().getAnioNacimiento().equals(new Integer(-1))){
					errors.rejectValue("serie.anioNacimiento", "anioNacimiento.vacio", new Object[]{}, "");
				}
			}
		}
		
	}
	
}
