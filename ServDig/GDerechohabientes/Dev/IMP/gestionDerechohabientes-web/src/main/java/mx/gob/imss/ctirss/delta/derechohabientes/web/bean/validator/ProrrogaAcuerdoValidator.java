/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.validator;

import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acuerdo;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author ghdolores
 *
 */
public class ProrrogaAcuerdoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Acuerdo.class.equals(clazz);
	}

	@Override
	public void validate(Object documento, Errors errors) {
		// TODO Auto-generated method stub
		Acuerdo acuerdo = (Acuerdo)documento;
		Date hoy= new Date();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "noAcuerdo", "field.required");
	    
		if(acuerdo.getProrroga().getFechaFinProrroga().getTime()<acuerdo.getProrroga().getFechaInicioProrroga().getTime()){
			errors.rejectValue("fechaFin", "error.acuerdo.msg01");
			errors.rejectValue("fechaInicio", "error.acuerdo.msg02");
		}
		if(acuerdo.getProrroga().getFechaFinProrroga().getTime()<hoy.getTime()){
			errors.rejectValue("fechaFin", "error.acuerdo.msg03");
		}
		
		if(acuerdo.getFechaExpedicion().getTime()>acuerdo.getProrroga().getFechaFinProrroga().getTime()){
			errors.rejectValue("fechaFin", "error.acuerdo.msg04");
		}
				
	}
	

}
