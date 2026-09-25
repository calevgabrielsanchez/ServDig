package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.validator;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.VigenciaTemporal;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class ProrrogaVigenciaTemporalValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		
		return VigenciaTemporal.class.equals(clazz);
	}

	@Override
	public void validate(Object documento, Errors errors) {
		VigenciaTemporal vigencia = (VigenciaTemporal) documento;
		
		if(vigencia.getFechaExpedicion()==null){
			errors.rejectValue("fechaExpedicion", "field.required");
		}
		
		ValidationUtils.rejectIfEmpty(errors, "noFolio", "field.required");
		
		
	}

}
