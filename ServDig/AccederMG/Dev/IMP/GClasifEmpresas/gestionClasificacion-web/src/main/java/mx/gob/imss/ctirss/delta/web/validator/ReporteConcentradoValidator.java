/**
 * ReporteConcentradoValidator.java
 * mx.gob.imss.ctirss.delta.web.validator
 * clasificacionEmpresas-web
 */
package mx.gob.imss.ctirss.delta.web.validator;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosConcentrado;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Jonathan Sanchez Montiel
 * 20/10/2014
 */
public class ReporteConcentradoValidator extends AbstractValidator implements
		Validator {
	 
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return ReporteConcentradoValidator.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		FiltrosConcentrado filtros = (FiltrosConcentrado)target;
		
		//se valida que los 2 campos del perido no esten vacios
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strPeriodoInicio", "field.fecha.inicial.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strPeriodoFin", "field.fecha.final.required");
		
		if (!errors.hasErrors()) {
			final Date periodoInicio = Utiles.parseStringToDate(filtros.getStrPeriodoInicio());
			final Date periodoFin = Utiles.parseStringToDate(filtros.getStrPeriodoFin());
			if (null == periodoInicio) {
				errors.rejectValue("strPeriodoInicio", "field.fecha.inicial.format");
			}
			if (null == periodoFin) {
				errors.rejectValue("strPeriodoFin", "field.fecha.final.format");
			}
		}
		
	}

}
