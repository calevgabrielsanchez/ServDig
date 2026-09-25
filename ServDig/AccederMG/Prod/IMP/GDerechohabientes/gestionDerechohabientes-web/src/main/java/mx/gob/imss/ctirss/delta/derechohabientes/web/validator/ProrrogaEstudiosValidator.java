/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.validator;

import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author ghdolores
 * 
 */
public class ProrrogaEstudiosValidator implements Validator {

	/**
	 * 
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return ConstanciaEstudio.class.equals(clazz);
	}

	@Override
	public void validate(Object documentacionTramite, Errors errors) {

		ConstanciaEstudio constancia = (ConstanciaEstudio) documentacionTramite;

		Date fechaActual = new Date();
			
		validarCadenas("nombreEscuela",constancia.getNombreEscuela(),errors);
		validarCadenas("noIncorporacion",constancia.getNoIncorporacion(),errors);
		validarCadenas("claveEscuela",constancia.getClaveEscuela(),errors);
		validarCadenas("gradoEscolar",constancia.getGradoEscolar(),errors);
		
		if(constancia.getFechaInicioPeriodo().getTime()>constancia.getFechaFinPeriodo().getTime()){
			errors.rejectValue("fechaInicioPeriodo", "error.fechaInicioMayor");
		}
		
		if(constancia.getFechaFinPeriodo().getTime()< fechaActual.getTime()){
			errors.rejectValue("fechaFinPeriodo", "error.fechaFinMenorActual");
		}
		}
	
	private void validarCadenas(String campo,String cadena,Errors errors){
		if(cadena==null || cadena.trim().equals("") ){
			errors.rejectValue(campo, "label.campoRequerido");
		}
		
	}
	
}
