package mx.gob.imss.cit.cda.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;



public class RegistroValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> arg0) {
		return Domicilio.class.equals(arg0);
	}

	@Override
	public void validate(Object object, Errors errors) {
		Domicilio domicilio = (Domicilio) object;
//		this.validarDomicilio(domicilio, errors);
	}
	
	
	private void validarDomicilio(String cp, Errors errors) {
		if(cp != null) {
			if(cp.length()<5) {
				errors.rejectValue("domicilio.codigoPostal.codigoPostal"," ","El formato del C\u00F3digo Postal no es vu00E1lido.");
			}
		
		} 
	}

}
