package mx.gob.imss.ctirss.delta.portal.web.validator;

import mx.gob.imss.ctirss.delta.framework.util.RegexValidatorUtil;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.portal.web.model.FiltrosBusqueda;
import mx.gob.imss.ctirss.delta.portal.web.model.TipoFiltroEnum;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class BusquedaValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return FiltroSolicitud.class.equals(arg0);
	}

	@Override
	public void validate(Object entrada, Errors errors) {

		FiltrosBusqueda filtros = (FiltrosBusqueda) entrada;
		
		if (filtros.getTipoFiltro() == TipoFiltroEnum.CURP.getId()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "curp",
					"field.required");
			if (!errors.hasErrors()) {
				RegexValidatorUtil.validaCURPVista("curp", errors, filtros.getCurp());
			}
		} else if (filtros.getTipoFiltro() == TipoFiltroEnum.RFC_FISICA.getId()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rfc",
					"field.required");
			if (!errors.hasErrors()) {
				RegexValidatorUtil.validaRFCFisicaVista("rfc", errors, filtros.getRfc());
				if(!errors.hasErrors())
					RegexValidatorUtil.validaFechaRFC("rfc",errors,filtros.getRfc().substring(6, 10));
			}
		} else if (filtros.getTipoFiltro() == TipoFiltroEnum.RFC_MORAL.getId()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rfc",
					"field.required");
			if (!errors.hasErrors()) {
				RegexValidatorUtil.validaRFCMoralVista("rfc", errors, filtros.getRfc());
				if(!errors.hasErrors())
					RegexValidatorUtil.validaFechaRFC("rfc",errors,filtros.getRfc().substring(5, 9));
			}
		} else if (filtros.getTipoFiltro() == TipoFiltroEnum.NSS.getId()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nss",
					"field.required");
		} else if (filtros.getTipoFiltro() == TipoFiltroEnum.NRP.getId()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nrp",
					"field.required");
		}
	}
}