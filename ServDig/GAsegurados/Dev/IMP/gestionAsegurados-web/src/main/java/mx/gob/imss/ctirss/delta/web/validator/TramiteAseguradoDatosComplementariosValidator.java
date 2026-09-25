package mx.gob.imss.ctirss.delta.web.validator;

import java.util.List;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class TramiteAseguradoDatosComplementariosValidator implements Validator {

	private boolean fromSIME; 
	
	@Override
	public boolean supports(Class<?> claz) {
		return TramiteAsegurado.class.equals(claz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		
		TramiteAsegurado tramiteAseg = (TramiteAsegurado) arg0;
		
		if (!fromSIME) {
			// Se valida que se tenga el domicilio
			List<Domicilio> domicilios = tramiteAseg.getFisica().getDomicilios();
			if (domicilios == null || domicilios.isEmpty()) {
				errors.rejectValue("fisica.domicilios", "field.required");
			} else {
				Domicilio domicilio = domicilios.get(0);
				
				if (StringUtils.isBlank(domicilio.getAsentamiento().getClave())) {
					errors.rejectValue("fisica.domicilios", "field.required");
				}
			}
		}
				
		// Se valida que se haya elegido una UMF
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fisica.umf.idUMF",
				"field.required");
	}

	public boolean isFromSIME() {
		return fromSIME;
	}

	public void setFromSIME(boolean fromSIME) {
		this.fromSIME = fromSIME;
	}
	
	
}