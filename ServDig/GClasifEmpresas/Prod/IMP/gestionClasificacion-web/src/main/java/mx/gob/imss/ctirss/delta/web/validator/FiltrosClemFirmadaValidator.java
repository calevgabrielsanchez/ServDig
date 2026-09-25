/**
 * FiltrosAnalisisConsultaValidator.java
 * mx.gob.imss.ctirss.delta.clasificacion.empresa.web.validation
 * clasificacionEmpresas-web
 */
package mx.gob.imss.ctirss.delta.web.validator;

import java.util.Date;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosAnalisisConsulta;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author JSM
 * 11/09/2020
 */
public class FiltrosClemFirmadaValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return FirmaClemDTO.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object form, Errors errors) {
		
		FirmaClemDTO filtro = (FirmaClemDTO)form;
		
		boolean validaPeriodo = false;
		boolean validaFechaFirma = false;
		
		if(filtro.getRegistroPatronal() == null || filtro.getRegistroPatronal().trim().length() == 0){ // si el rp es nulo el periodo es requerido
			validaPeriodo = true;
		}else{ // si el rp viene con valor
			if( filtro.getRegistroPatronal().length() != 11){
				errors.rejectValue("registroPatronal", "field.registro.patronal.wrong");
			}
			if ( !ifEmptyOrWhitespace(filtro.getStrPeriodoInicio()) || !ifEmptyOrWhitespace(filtro.getStrPeriodoFin()) ||
					!ifEmptyOrWhitespace(filtro.getStrPerIniF()) || !ifEmptyOrWhitespace(filtro.getStrPerFinF()) ) {
				errors.rejectValue("strPeriodoInicio", "field.wrong.filtros");				
			}
		} 

		if(validaPeriodo){
			if ( !ifEmptyOrWhitespace(filtro.getStrPeriodoInicio()) || !ifEmptyOrWhitespace(filtro.getStrPeriodoFin())) {
				if ( !ifEmptyOrWhitespace(filtro.getStrPerIniF()) || !ifEmptyOrWhitespace(filtro.getStrPerFinF())) {
					validaPeriodo = false;
					errors.rejectValue("strPeriodoInicio", "field.wrong.filtros");
				}
			}
		}

		if(validaPeriodo){
			//se valida que los 2 campos del perido no esten vacios
			
			// si la fecha de periodo determinado no viene vacia
			if ( !ifEmptyOrWhitespace(filtro.getStrPeriodoInicio()) || !ifEmptyOrWhitespace(filtro.getStrPeriodoFin())) {
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strPeriodoInicio", "field.fecha.inicial.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strPeriodoFin", "field.fecha.final.required");
				
				if (!errors.hasErrors()) {
					final Date periodoInicio = filtro.getStrPeriodoInicio();
					final Date periodoFin = filtro.getStrPeriodoFin();
					
					if (null == periodoInicio) {
						errors.rejectValue("strPeriodoInicio", "field.fecha.inicial.format");
					}
					if (null == periodoFin) {
						errors.rejectValue("strPeriodoFin", "field.fecha.final.format");
					}
					if (!errors.hasErrors()) {
						final String val = Utiles.validaPeriodo(Constantes.DATE_FORMAT_YYYY_MM_DD.format(periodoInicio),
								Constantes.DATE_FORMAT_YYYY_MM_DD.format(periodoFin), 3);				
						if ("true".equals(val)) {
							filtro.setStrPeriodoInicio(periodoInicio);
							filtro.setStrPeriodoFin(periodoFin);
						} else if("errorPeriodoMayor".equals(val)) {
							errors.rejectValue("strPeriodoInicio", "field.periodo.mayor", new Object[] {new Integer(3)}, "");					
						} else if("errorFechaFinalMayor".equals(val))	{		
							errors.rejectValue("strPeriodoFin", "field.fecha.final.menor");
					    }
					}
					
					if (!errors.hasErrors()) {
						final String val = Utiles.validaFechaFinMayor(Constantes.DATE_FORMAT_YYYY_MM_DD.format(periodoInicio),
								Constantes.FECHA_PIVOTE_FIRMA);	
						if ("true".equals(val)) {
							filtro.setStrPeriodoInicio(periodoInicio);
							filtro.setStrPeriodoFin(periodoFin);
						} else {
							errors.rejectValue("strPeriodoInicio", "field.periodo.menor.pivote", new Object[] {Constantes.FECHA_PIVOTE_FIRMA}, "");					
						}				
					}			
				}				
				
				
			}else{
				validaFechaFirma = true;
			}
			
			// si no vienen datos en periodo determinado se valida fecha de firma
			if(validaFechaFirma){
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strPerIniF", "field.fecha.inicial.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strPerFinF", "field.fecha.final.required");

				if (!errors.hasErrors()) {
					final Date periodoInicio = filtro.getStrPerIniF();
					final Date periodoFin = filtro.getStrPerFinF();
					
					if (null == periodoInicio) {
						errors.rejectValue("strPerIniF", "field.fecha.inicial.format");
					}
					if (null == periodoFin) {
						errors.rejectValue("strPerFinF", "field.fecha.final.format");
					}
					if (!errors.hasErrors()) {
						final String val = Utiles.validaPeriodo(Constantes.DATE_FORMAT_YYYY_MM_DD.format(periodoInicio),
								Constantes.DATE_FORMAT_YYYY_MM_DD.format(periodoFin), 3);				
						if ("true".equals(val)) {
							filtro.setStrPerIniF(periodoInicio);
							filtro.setStrPerFinF(periodoFin);
						} else if("errorPeriodoMayor".equals(val)) {
							errors.rejectValue("strPerIniF", "field.periodo.mayor", new Object[] {new Integer(3)}, "");					
						} else if("errorFechaFinalMayor".equals(val))	{		
							errors.rejectValue("strPerFinF", "field.fecha.final.menor");
					    }
					}					
					if (!errors.hasErrors()) {
						final String val = Utiles.validaFechaFinMayor(Constantes.DATE_FORMAT_YYYY_MM_DD.format(periodoInicio),
								Constantes.FECHA_PIVOTE_FIRMA);	
						if ("true".equals(val)) {
							filtro.setStrPerIniF(periodoInicio);
							filtro.setStrPerFinF(periodoFin);
						} else {
							errors.rejectValue("strPerIniF", "field.periodo.menor.pivote", new Object[] {Constantes.FECHA_PIVOTE_FIRMA}, "");					
						}				
					}			
				}								
				
			}
								
		}//validaPeriodo
	}
	
	private boolean ifEmptyOrWhitespace(Object value) {
		if (value == null ||!StringUtils.hasText(value.toString())) {
			return true;
		}
		return false;
	}
	
}
