package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller.validator;

import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TramiteBajaValidator extends AbstractValidator implements Validator{

	private static final int LENGTH_OBSERVACIONES = 255;
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
	private static final int LENGTH_CURP = 18;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return TramiteBajaDerechohabiente.class.equals(arg0);
	}

	public void validateCiudadado(Object baja, Errors errors){
		TramiteBajaDerechohabiente tramite = (TramiteBajaDerechohabiente) baja;
		Fisica pf = tramite.getPersona();
		
		log.debug("El tipo de tramite es: " + tramite.getTipoTramite().getIdTipoTramite());
		if(tramite.getTipoTramite() == null  || tramite.getTipoTramite().getIdTipoTramite() == null) {
			errors.rejectValue("tipoTramite.idTipoTramite", "field.required");
		}
		
		if(pf != null) {
			if (StringUtils.isBlank(pf.getCurp())) {
				errors.rejectValue("persona.curp", "field.required");
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
		TramiteBajaDerechohabiente tramite = (TramiteBajaDerechohabiente) arg0;
		
		log.debug("El tipo de tramite es: " + tramite.getTipoTramite().getIdTipoTramite());
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.BAJA_DEFUNCION.getCodigo())) {
			log.debug("La fecha de defuncion es: " + tramite.getFechaDefuncion());
			if(tramite.getFechaDefuncion() == null) {
				errors.rejectValue("fechaDefuncion", "field.required");
			}
		}
		
		if(StringUtils.isBlank(tramite.getObservaciones())) {
			errors.rejectValue("observaciones", "field.required");
		} else {
			if(tramite.getObservaciones().length() > LENGTH_OBSERVACIONES) {
				 errors.rejectValue("observaciones", "field.max.length", new Object[] {new Integer(LENGTH_OBSERVACIONES)}, "");
			}
		}
	}

}
