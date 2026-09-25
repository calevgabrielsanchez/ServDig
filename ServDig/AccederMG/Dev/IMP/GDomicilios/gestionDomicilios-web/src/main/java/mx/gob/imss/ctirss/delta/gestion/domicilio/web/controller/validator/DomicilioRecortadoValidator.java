package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller.validator;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.UmfDomicilioDTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DomicilioRecortadoValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return UmfDomicilioDTO.class.equals(arg0);
	}

	public void validateCodigoPostal(Object object , Errors errors) {
		UmfDomicilioDTO umfDomicilio = (UmfDomicilioDTO) object;
		
		Domicilio domicilio = umfDomicilio.getDomicilio();
		
		if(domicilio != null && domicilio.getCodigoPostal() != null && domicilio.getCodigoPostal().getCodigoPostal() != null &&
				!domicilio.getCodigoPostal().getCodigoPostal().isEmpty()) {
			String codigo = domicilio.getCodigoPostal().getCodigoPostal();
			
			if(codigo.length() < 5){
				errors.rejectValue("domicilio.codigoPostal.codigoPostal", "", "Este campo es obligatorio" );
			}
			
			try {
				Integer.valueOf(codigo);
			} catch (Exception e) {
				errors.rejectValue("domicilio.codigoPostal.codigoPostal", "" , "Debe ser num\u00E9rico." );
			}
		} else {
			errors.rejectValue("domicilio.codigoPostal.codigoPostal","", "Este campo es obligatorio"  );
		}
	}
	
	@Override
	public void validate(Object object, Errors errors) {
		UmfDomicilioDTO umfDomicilio = (UmfDomicilioDTO) object;
		//Validamos los datos del domicilio
		this.validarDomicilio(umfDomicilio.getDomicilio(), errors);
	}
	
	private void validarDomicilio(Domicilio domicilio, Errors errors) {
		if(domicilio != null) {
			
			if(domicilio.getCodigoPostal() == null || StringUtils.isBlank(domicilio.getCodigoPostal().getCodigoPostal())) {
				errors.rejectValue("domicilio.codigoPostal.codigoPostal", "field.required");
			}
			
			if(StringUtils.isBlank(domicilio.getCalle())) {
				errors.rejectValue("domicilio.calle", "field.required");
			}
			
			if(StringUtils.isBlank(domicilio.getNumExteriorAlf())) {
				errors.rejectValue("domicilio.numExteriorAlf", "field.required");
			}
			
			if(domicilio.getAsentamiento() == null || domicilio.getAsentamiento().getClave().equals("-1")) {
				errors.rejectValue("domicilio.asentamiento.clave", "field.required");
			}
			
			
		
		} else {
			errors.rejectValue("domicilio.vialidadPrimaria.nombre", "field.required");
			errors.rejectValue("domicilio.numExterior1", "field.required");
			errors.rejectValue("domicilio.asentamiento.clave", "field.required");
			errors.rejectValue("domicilio.codigoPostal.codigoPostal", "field.required");
		}
	}

}
