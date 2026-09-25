package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RegistroSeriesValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return AsignacionSerieNSS.class.equals(clazz);
	}

	@Override
	public void validate(Object asignacionSerieNSS, Errors errors) {
		AsignacionSerieNSS model = (AsignacionSerieNSS) asignacionSerieNSS;

		validateTipoSerie(model, errors);
		validateAnioRegistro(model, errors);
		validateNumeroSerie(model, errors);
	}

	private void validateTipoSerie(AsignacionSerieNSS model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"serie.tipoSerie.idTipoSerie", "field.required");

		Integer idTipoSerie = model.getSerie().getTipoSerie().getIdTipoSerie();
		if (idTipoSerie == null || idTipoSerie < 0) {
			errors.rejectValue("serie.tipoSerie.idTipoSerie", "field.required");
		}
	}

	private void validateAnioRegistro(AsignacionSerieNSS model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serie.anioRegistro",
				"field.required");

		Integer anioRegistro = model.getSerie().getAnioRegistro();
		if (anioRegistro < 0) {
			errors.rejectValue("serie.anioRegistro", "field.required");
		}
	}

	private void validateNumeroSerie(AsignacionSerieNSS model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serie.numSerie",
				"field.required");

		Long numeroSerie = model.getSerie().getNumSerie();
		if (numeroSerie < 0) {
			errors.rejectValue("serie.numSerie", "field.required");
		}
	}
}
