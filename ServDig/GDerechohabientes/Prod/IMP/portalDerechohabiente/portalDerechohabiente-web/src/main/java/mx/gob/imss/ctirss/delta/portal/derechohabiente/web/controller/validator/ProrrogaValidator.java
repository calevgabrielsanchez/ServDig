package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller.validator;

import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.ProrrogasDto;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProrrogaValidator extends AbstractValidator implements Validator {

	private static final int LENGTH_OBSERVACIONES = 255;
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
	private static final int LENGTH_CURP = 18;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return TramiteProrroga.class.equals(arg0);
	}
	
	public void validateCiudadado(Object datos, Errors errors){
		TramiteProrroga tramite = (TramiteProrroga) datos;
		Fisica pf = tramite.getPersona();
		
		if(pf != null) {
			if (StringUtils.isBlank(pf.getCurp())) {
				errors.rejectValue("prorroga.persona.curp", "field.required");
			} else {
				if (pf.getCurp().length() != LENGTH_CURP) {
					errors.rejectValue("persona.curp", "field.min.length",new Object[] { new Integer(LENGTH_CURP) }, "");
				} else if (!pf.getCurp().matches(REGEX_CURP_FISICA)) {
					errors.rejectValue("persona.curp", "field.wrong.format");
				}
			}
		} else {
			errors.rejectValue("persona.curp", "field.required");
		}
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		TramiteProrroga prorroga = (TramiteProrroga) arg0;
		
		if(prorroga.getCaracter() == null || prorroga.getCaracter().getIdCaracter() == null || prorroga.getCaracter().getIdCaracter().equals(new Long(-1)) ) {
			errors.rejectValue("caracter.idCaracter", "field.required");
		}
		
		if(prorroga.getFechaInicioProrroga() == null) {
			errors.rejectValue("fechaInicioProrroga", "field.required");
		}

		if(prorroga.getFechaFinProrroga() == null) {
			errors.rejectValue("fechaFinProrroga", "field.required");
		}
		
		if(StringUtils.isBlank(prorroga.getObservaciones())) {
			errors.rejectValue("observaciones", "field.required");
		} else {
			if(prorroga.getObservaciones().length() > LENGTH_OBSERVACIONES) {
				 errors.rejectValue("observaciones", "field.max.length", new Object[] {new Integer(LENGTH_OBSERVACIONES)}, "");
			}
		}
	}

}
