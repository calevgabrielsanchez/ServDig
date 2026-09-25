package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.gestion.medio.contacto.web.beans.MedioContactoFormWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AdministracionMediosContactoValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return MedioContactoFormWrapper.class.equals(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {

		MedioContactoFormWrapper form = (MedioContactoFormWrapper) arg0;

		if (form.getTipoMedioContacto().getIdTipoMedioContacto()
				.equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"correoElectronico.correo", "field.required");

			if (form.getCorreoElectronico().getCorreo() != null
					&& !form.getCorreoElectronico().getCorreo().isEmpty()) {

				if (!form.getCorreoElectronico().getCorreo()
						.matches(EMAIL_PATTERN)) {
					errors.rejectValue("correoElectronico.correo",
							"field.wrong.format");
				}
			}
		} else if (form.getTipoMedioContacto().getIdTipoMedioContacto()
				.equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"telefonoFijo.numero", "field.required");
		} else if (form.getTipoMedioContacto().getIdTipoMedioContacto()
				.equals(TipoMedioContacto.TIPO_TELEFONO_MOVIL)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"telefonoMovil.numero", "field.required");
		} else if (form.getTipoMedioContacto().getIdTipoMedioContacto()
				.equals(TipoMedioContacto.TIPO_FACEBOOK)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"facebook.cuenta", "field.required");
		} else if (form.getTipoMedioContacto().getIdTipoMedioContacto()
				.equals(TipoMedioContacto.TIPO_TWITTER)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "twitter.cuenta",
					"field.required");
		}
	}

}