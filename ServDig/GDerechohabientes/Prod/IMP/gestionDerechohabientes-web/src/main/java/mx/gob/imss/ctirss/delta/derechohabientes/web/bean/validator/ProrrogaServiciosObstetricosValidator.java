package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.validator;

import java.util.Date;

import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.DateUtils;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Obstetrico;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProrrogaServiciosObstetricosValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Obstetrico.class.equals(clazz);
	}

	@Override
	public void validate(Object documento, Errors errors) {
		Obstetrico obstetrico = (Obstetrico) documento;
		Date fechaFin = DateUtils.sumarDiasFecha(obstetrico.getProrroga().getFechaInicioProrroga(), 270);
		if(obstetrico.getMedicoFamiliar()==null || obstetrico.getMedicoFamiliar().getIdMedicoFamiliar()<1){
			errors.rejectValue("medicoFamiliar", "field.required");
		}
		
		if(obstetrico.getProrroga().getFechaFinProrroga().getTime()>fechaFin.getTime()){
			errors.rejectValue("fechaFinCadena","exception.obstetricos.msg01");
		}
		
		if(obstetrico.getProrroga().getFechaFinProrroga().getTime()<obstetrico.getProrroga().getFechaInicioProrroga().getTime()){
			errors.rejectValue("fechaInicioCadena","exception.obstetricos.msg02");
		}
		
		
		
		
	}

}
