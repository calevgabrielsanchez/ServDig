package mx.gob.imss.cit.cda.web.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.cit.cda.web.vo.HistoriaLaboralVO;
import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class HistoriaLaboralValidator extends AbstractValidator {

	private static final Logger log = LoggerFactory.getLogger(HistoriaLaboralValidator.class);
	private static final String REGEX_NRP = "^[A-Z0-9][0-9]{9,10}$";
	private static final String FORMATO_FECHA_ANIO = "yyyy";
	private static final String FORMATO_FECHA_MES_ANIO = "MM/yyyy";
	private static final String FORMATO_FECHA_DIA_MES_ANIO = "dd/MM/yyyy";
	private static final String FORMATO_FECHA_NULA = "0";
	private static final String SIN_NRP = "N/D";
	private static final String VIGENTEALAFECHA = "Vigente a la fecha";
	
	public void validate(Object target, Errors errors) {
		HistoriaLaboralVO historiaLaboralVO = (HistoriaLaboralVO) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombrePatron", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entidadFederativa", "field.required");
		validateFechaNula(historiaLaboralVO.getFechaInscripcion(), errors, "fechaInscripcion");	
                if(!VIGENTEALAFECHA.equals(historiaLaboralVO.getFechaBaja())){
                    validateFechaNula(historiaLaboralVO.getFechaBaja(), errors, "fechaBaja");
                    validateFormatoFecha(historiaLaboralVO.getFechaBaja(), errors, "fechaBaja");
                    comparDate(historiaLaboralVO.getFechaInscripcion(),historiaLaboralVO.getFechaBaja(),errors,"fechaBaja");
                    compareDateActual(historiaLaboralVO.getFechaBaja(),errors,"fechaBaja","field.invalid.date.baja");
                }
		compareDateActual(historiaLaboralVO.getFechaInscripcion(),errors,"fechaInscripcion","field.invalid.date.inicio");
		
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "domicilioEmpresa", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actividadEmpresa", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numeroRegistroPatronal", "field.required");
		if(!errors.hasFieldErrors("numeroRegistroPatronal")){
			validateFormatoNRP(errors, historiaLaboralVO.getNumeroRegistroPatronal());
		}
	}
	
	private void compareDateActual(String fecha, Errors errors, String elemento, String msj){
		if(!FORMATO_FECHA_NULA.equals(fecha)){
			Date fin = convertStringToDate(fecha);
			Date fActual = new Date();
			if(fin.after(fActual)){
				errors.rejectValue(elemento, msj);
			} 
		}
	}
	
	private void comparDate(String fInicio, String fFin, Errors errors, String elemento){
		if(!FORMATO_FECHA_NULA.equals(fInicio) && !FORMATO_FECHA_NULA.equals(fFin)){			
			Date inicio = convertStringToDate(fInicio);
			Date fin = convertStringToDate(fFin);
			if(inicio.after(fin)){
				errors.rejectValue(elemento, "field.invalid.date");
			}
		}		
	}
	
	private Date convertStringToDate(String fecha){
		Date date = new Date();
		SimpleDateFormat sdf;
		String formatoFecha = FORMATO_FECHA_DIA_MES_ANIO;
		if(fecha.length() == 4){
			formatoFecha = FORMATO_FECHA_ANIO;
		}else if(fecha.length()>4 && fecha.length()<8 ){
			formatoFecha = FORMATO_FECHA_MES_ANIO;
		}
		sdf = new SimpleDateFormat(formatoFecha);
		try {
    		date = sdf.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private void validateFormatoFecha(String valor, Errors errors,
			String elemento) {
		if (!FORMATO_FECHA_NULA.equals(valor)) {
			try {
				
				String formatoFecha = FORMATO_FECHA_DIA_MES_ANIO;
				if(valor.length() == 4){
					formatoFecha = FORMATO_FECHA_ANIO;
				}else if(valor.length()>4 && valor.length()<8 ){
					formatoFecha = FORMATO_FECHA_MES_ANIO;
				}
				
				Date fechaFormato = new SimpleDateFormat(formatoFecha).parse(valor);
				log.debug("###########Formato Fecha {}",fechaFormato);
				log.debug("###########Fecha Controller {}",valor);
				log.debug("Tamanio {}",valor.length());
				log.debug("formatoFecha {}",formatoFecha);
				if(!new SimpleDateFormat(formatoFecha).format(fechaFormato).equals(valor)){
					errors.rejectValue(elemento, "field.wrong.format");
				}
			} catch (IllegalArgumentException iAE) {
				log.error("Error {}", iAE);
				errors.rejectValue(elemento, "field.wrong.format");
			} catch (ParseException pE) {
				log.error("Error {}", pE);
				errors.rejectValue(elemento, "field.wrong.format");
			}
		}

	}

	private void validateFechaNula(String valor, Errors errors, String elemento) {		
			if (FORMATO_FECHA_NULA.equals(valor)) {
				errors.rejectValue(elemento, "field.required");
			}
	}
	
	private void validateFormatoNRP(Errors errors, String nrp){
		if(StringUtils.isNotEmpty(nrp) && !nrp.equals(SIN_NRP)){
			if(!nrp.matches(REGEX_NRP)){			
				errors.rejectValue("numeroRegistroPatronal", "field.NRP.formatoIncorrecto");
			}
		}
		
	}

}
