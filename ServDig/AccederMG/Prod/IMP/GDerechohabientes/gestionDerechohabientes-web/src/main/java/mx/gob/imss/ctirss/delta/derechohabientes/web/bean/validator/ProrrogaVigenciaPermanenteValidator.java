/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.validator;

import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author ghdolores
 *
 */
public class ProrrogaVigenciaPermanenteValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Acta.class.equals(clazz);
	}

	@Override
	public void validate(Object documento, Errors errors) {
		// TODO Auto-generated method stub
		Acta acta = (Acta)documento;
		Date hoy= new Date();
		
		/*if(acta.getMunicipio()==null || acta.getMunicipio().getIdMuniciio()==null
				|| acta.getMunicipio().getIdMuniciio()<1){
			errors.rejectValue("idMunicipio", "field.required");
		}
		
		if(acta.getEntidadFederativa()==null ||
				acta.getEntidadFederativa().getIdEntidadFederativa().isEmpty() ||
				acta.getEntidadFederativa().getIdEntidadFederativa().equals("-1")){
			errors.rejectValue("idEntidadFederativa", "field.required");
		}*/
		
		if(acta.getFechaExpedicion().getTime()<acta.getFechaSuceso().getTime()){
			errors.rejectValue("fechaExpedicionCadena", "exception.permanente.msg01");
		}
	}
	

}
