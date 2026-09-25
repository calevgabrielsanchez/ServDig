package mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator;

import mx.gob.imss.ctirss.delta.gestion.asegurado.web.bean.UploadFileBean;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class CapturaPatronSIEValidator extends AbstractValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return UploadFileBean.class.equals(clazz);
	}
	
	@Override
	public void validate(Object uploadFileBean, Errors errors) {
		UploadFileBean uploadItem = (UploadFileBean) uploadFileBean;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "erpName",
				"field.required");
		
		/* 
		 * Se valida que el NRP sea de por lo menos 10 posiciones para
		 * poder validar la modalidad
		 */
		String nrp = uploadItem.getErpName();
		if (nrp.length() < 10) {
			errors.rejectValue("erpName", "error.nrp.longitud.invalida.sie");
		} else {
			int modalidad = 0;
			
			try {
				modalidad = Integer.parseInt(nrp.substring(8, 10));
			} catch(NumberFormatException e) {
				errors.rejectValue("erpName", "error.nrp.modalidad.invalida.sie");
			}
			
			if (modalidad != 32) {
				errors.rejectValue("erpName", "error.nrp.modalidad.invalida.sie");
			}
		}
	}

}
