/**
 * gestionAsegurados-web 26/06/2012
 * mx.gob.imss.ctirss.delta.web.validator
 * AsentamientoValidator.java
 * 26/06/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Lucio Duran Silva Instituto Mexicano del Seguro Social
 */
public class TramiteAseguradoValidator implements Validator {
	@Override
	public boolean supports(Class<?> claz) {
		return TramiteAsegurado.class.equals(claz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fisica.nombre",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"fisica.primerApellido", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"asignacionSerieNss.serie.tipoSerie.idTipoSerie",
				"field.required");

		TramiteAsegurado ta = (TramiteAsegurado) arg0;
		Integer idTipoSerie = ta.getAsignacionSerieNss().getSerie()
				.getTipoSerie().getIdTipoSerie();

		if (idTipoSerie == -1) {
			errors.rejectValue(
					"asignacionSerieNss.serie.tipoSerie.idTipoSerie",
					"serie.vacia");
		}
	}
}
