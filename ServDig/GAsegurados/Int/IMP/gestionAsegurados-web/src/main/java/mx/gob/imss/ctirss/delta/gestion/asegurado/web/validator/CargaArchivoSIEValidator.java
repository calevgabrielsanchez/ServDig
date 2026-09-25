package mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator;

import mx.gob.imss.ctirss.delta.gestion.asegurado.web.bean.UploadFileBean;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class CargaArchivoSIEValidator extends AbstractValidator {
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UploadFileBean.class.equals(clazz);
	}

	@Override
	public void validate(Object uploadFileBean, Errors errors) {
		UploadFileBean uploadItem = (UploadFileBean) uploadFileBean;

		if (uploadItem.getCorreosContacto() == null
				|| uploadItem.getCorreosContacto().isEmpty()) {
			errors.rejectValue("correosContacto[0].correo", "field.required");
		} else {
			CorreoElectronico correo =  null;
			for (int i = 0; i < uploadItem.getCorreosContacto().size(); i++) {
				correo = uploadItem.getCorreosContacto().get(i);
				
				if (StringUtils.isNotBlank(correo.getCorreo())) {
					if (!correo.getCorreo().matches(EMAIL_PATTERN)) {
						errors.rejectValue("correosContacto[" + i + "].correo",
								"field.wrong.format");
					}
				} else if (i == 0) {
					errors.rejectValue("correosContacto[0].correo", "field.required");
				}
			}
		}
				
		if (uploadItem.getFileData() == null
				|| StringUtils.isEmpty(uploadItem.getFileData().getFileItem()
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
