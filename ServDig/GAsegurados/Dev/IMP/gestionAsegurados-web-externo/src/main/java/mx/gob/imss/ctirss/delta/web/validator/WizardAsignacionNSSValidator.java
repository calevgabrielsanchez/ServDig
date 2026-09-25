package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class WizardAsignacionNSSValidator implements Validator {
	
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
    private static final int LENGTH_CURP  = 18;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Fisica.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		Fisica fisica = (Fisica) object;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "curp", "field.required");
		
		if(StringUtils.isNotBlank(fisica.getCurp())){
            if(fisica.getCurp().length() != LENGTH_CURP){
                errors.rejectValue("curp", "field.min.length", new Object[] {new Integer(LENGTH_CURP)}, "");
            }else{
                if(!fisica.getCurp().matches(REGEX_CURP_FISICA)){
                    errors.rejectValue("curp", "field.wrong.format");
                }
            }
        }
		
		// Se valida que se haya capturado el correo electrónico
		boolean isCorreo = false;
		if (fisica.getMediosContacto() != null) {
			for (MedioContacto medio : fisica.getMediosContacto()) {
				if (medio instanceof CorreoElectronico || (medio.getTipoMedioContacto() != null &&
						medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO))){
					isCorreo = true;
					break;
				}
			}
		}
		
		if(!isCorreo) {
			errors.rejectValue("mediosContacto", "correo.requerido");
		}
		
		// Se valida que se haya capturado el domicilio
		boolean isDomicilio = false;
		if (fisica.getDomicilios() != null && !fisica.getDomicilios().isEmpty()) {
			Domicilio domicilio = fisica.getDomicilios().get(0);
			
			if (domicilio != null && domicilio.getAsentamiento() != null) {
				isDomicilio = true;
			}
		}
		
		if (!isDomicilio) {
			errors.rejectValue("domicilios", "domicilio.requerido");
		}
	}
}