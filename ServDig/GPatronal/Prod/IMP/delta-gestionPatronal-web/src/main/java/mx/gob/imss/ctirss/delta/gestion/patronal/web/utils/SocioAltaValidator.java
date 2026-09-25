package mx.gob.imss.ctirss.delta.gestion.patronal.web.utils;

import mx.gob.imss.ctirss.delta.framework.util.RegexValidatorUtil;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SocioAltaValidator implements Validator {
	
	private int tipoValidacion;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Socio.class.equals(clazz);
	}

	@Override
	public void validate(Object socio, Errors errores) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errores, "tipoSocio.idTipoPersona", "field.required");
		if(tipoValidacion>0){
			Socio socioHelper = (Socio) socio;
			if (tipoValidacion == 1) {//Fisica
				RegexValidatorUtil.validaRFCFisicaVista("rfc", errores,	socioHelper.getRfc());
				RegexValidatorUtil.validaCURPVista("curp", errores, socioHelper.getCurp());				
			} else if (tipoValidacion == 2) {//Moral
				RegexValidatorUtil.validaRFCMoralVista("rfc", errores,	socioHelper.getRfc());
			}
		}
	}
	
	public void setTipoValidacion(int tipoValidacion) {
		this.tipoValidacion = tipoValidacion;
	}

}
