package mx.gob.imss.cit.cda.web.validator;

import mx.gob.imss.cit.cda.web.vo.DatosAdicionales;
import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class DatosAdicionalesValidator extends AbstractValidator{
	
	private static final Logger log = LoggerFactory.getLogger(DatosAdicionalesValidator.class);
	private static final String REGEX_NRP = "^[A-Z0-9][0-9]{9,10}$";
	
	public void validate(Object target, Errors errors) {
		
		log.debug("Entrando Validator DatosAdicionales {}",target);
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numeroRegistroPatronal",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "domicilioEmpresa",
				"field.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actividadEmpresa",
				"field.required");
		
		validateFormatoNRP(errors, ((DatosAdicionales) target).getNumeroRegistroPatronal());
		
	}
	
	private void validateFormatoNRP(Errors errors, String nrp){
		if(StringUtils.isNotEmpty(nrp)){
			if(!nrp.matches(REGEX_NRP)){			
				errors.rejectValue("numeroRegistroPatronal", "field.NRP.formatoIncorrecto");
			}
		}
		
	}

}
