package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.validator;

import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class CorreccionDerechohabienteValidator implements Validator {

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class type) {
		// TODO Auto-generated method stub
		return GrupoFamiliar.class.isAssignableFrom(type);
	}

	@Override
	public void validate(Object command, Errors errors) {
		// TODO Auto-generated method stub
		GrupoFamiliar correccionDatos = (GrupoFamiliar) command;
		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "asegurado.asignacionNSS.nss", "error.nss","*Requerido");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "parentesco.idParentesco", "error.parentesco","*Requerido");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "calidad", "error.calidad","*Requerido");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "asignacionNSS.nss", "error.nss","*Requerido");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "derechohabiente.nombre", "error.nombre","*Requerido");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "derechohabiente.primerApellido", "error.primerApellido", "*Requerido");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "derechohabiente.segundoApellido", "error.segundoApellido", "*Requerido");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "derechohabiente.curp", "error.curp", "*Requerido");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "derechohabiente.fechaNacimiento", "error.fechaNacimiento", "*Requerido");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "derechohabiente.entidadFederativa.idEntidadFederativa", "error.entidadFederativa", "*Requerido");
		
		
	}


}
