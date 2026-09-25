package mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator;

import mx.gob.imss.ctirss.delta.gestion.asegurado.web.bean.UploadFileBean;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ProcesoSIMEValidator extends AbstractValidator {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return UploadFileBean.class.equals(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
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
			
			if (modalidad != 33) {
				errors.rejectValue("erpName", "error.nrp.modalidad.invalida.sie");
			}
		}

		if (StringUtils.isEmpty(uploadItem.getFileData().getFileItem()
				.getName())) {
			errors.rejectValue("fileData", "field.required");
		} else {

			validateFileExtention(errors, uploadItem);
		}
	}

	private void validateFileExtention(Errors errors, UploadFileBean uploadItem) {
		CommonsMultipartFile fileData = uploadItem.getFileData();
		String fileName = fileData.getOriginalFilename();

		if (!getFileExtensionName(fileName).equalsIgnoreCase("xml")) {
			errors.rejectValue("fileData", "field.file.invalid");
		}
	}

	private String getFileExtensionName(String fileName) {
		if (fileName.indexOf(".") == -1) {
			return "";
		} else {
			return fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length());
		}
	}
}
