/**
 * ReporteBitacoraValidator.java
 * mx.gob.imss.ctirss.delta.clasificacion.empresa.web.validation
 * clasificacionEmpresas-web
 */
package mx.gob.imss.ctirss.delta.web.validator;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosBitacoras;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Lucio Duran Silva
 * 13/01/2012
 */
public class ReporteBitacoraValidator extends AbstractValidator implements
		Validator {
	 
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return ReporteBitacoraValidator.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		FiltrosBitacoras filtros = (FiltrosBitacoras)target;
		boolean validaPeriodo = false;
		
		if(filtros.getUsuario() == null || filtros.getUsuario().trim().length() == 0){ // si el rp es nulo el periodo es requerido
			System.out.println("el periodo es requerido");
			validaPeriodo = true;
		}else{ // si el rp viene con valor - el periodo no es requerido a menos que la fecha inicial o final tengan valor
			if ((null != filtros.getStrPeriodoInicio() && filtros
					.getStrPeriodoInicio().trim().length() > 0)
					|| (null != filtros.getStrPeriodoFin() && filtros
							.getStrPeriodoFin().trim().length() > 0))
				validaPeriodo = true;
		}
		
		if(validaPeriodo){
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
				
				filtros.setPeriodoInicio(periodoInicio);
				filtros.setPeriodoFin(periodoFin);				
			}
		}
	}

}
