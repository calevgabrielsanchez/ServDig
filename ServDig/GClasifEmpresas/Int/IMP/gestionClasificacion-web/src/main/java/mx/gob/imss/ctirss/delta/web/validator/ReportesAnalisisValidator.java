/**
 * ReportesAnalisisValidator.java
 * mx.gob.imss.ctirss.delta.clasificacion.empresa.web.validation
 * clasificacionEmpresas-web
 */
package mx.gob.imss.ctirss.delta.web.validator;

import java.util.Date;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosReportes;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Jonathan Sanchez Montiel
 * 26/01/2012
 */
public class ReportesAnalisisValidator  implements Validator {
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return ReportesAnalisisValidator.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
		FiltrosReportes filtrosReportes = (FiltrosReportes)target;
		boolean validaPeriodo = false;
		
		if(filtrosReportes.getRegistroPatronal() == null || filtrosReportes.getRegistroPatronal().trim().length() == 0){ // si el rp es nulo el periodo es requerido
			validaPeriodo = true;
		}else{ // si el rp viene con valor - el periodo no es requerido a menos que la fecha inicial o final tengan valor
			if ((null != filtrosReportes.getStrPeriodoInicio() && filtrosReportes
					.getStrPeriodoInicio().trim().length() > 0)
					|| (null != filtrosReportes.getStrPeriodoFin() && filtrosReportes
							.getStrPeriodoFin().trim().length() > 0))
				validaPeriodo = true;											
		}

		if(validaPeriodo){
			//se valida que los 2 campos del perido no esten vacios
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strPeriodoInicio", "field.fecha.inicial.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strPeriodoFin", "field.fecha.final.required");
			if (!errors.hasErrors()) {
				final Date periodoInicio = Utiles.parseStringToDate(filtrosReportes.getStrPeriodoInicio());
				final Date periodoFin = Utiles.parseStringToDate(filtrosReportes.getStrPeriodoFin());
				if (null == periodoInicio) {
					errors.rejectValue("strPeriodoInicio", "field.fecha.inicial.format");
				}
				if (null == periodoFin) {
					errors.rejectValue("strPeriodoFin", "field.fecha.final.format");
				}
				
				filtrosReportes.setPeriodoInicio(periodoInicio);
				filtrosReportes.setPeriodoFin(periodoFin);				
			}						
		}
	}
	
}
