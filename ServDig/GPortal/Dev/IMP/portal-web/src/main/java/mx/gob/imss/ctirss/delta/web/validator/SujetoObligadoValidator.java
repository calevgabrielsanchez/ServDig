package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SujetoObligadoValidator implements Validator {

	private static final int LONGITUD_NRP = 11;
	private static final int LONGITUD_CLASIFICACION = 4;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return SujetoObligado.class.equals(arg0);
	}

	@Override
	public void validate(Object sujeto, Errors errors) {
		SujetoObligado sujetoO = (SujetoObligado) sujeto;
		
		if(sujetoO.getNumeroRegistroPatronal() != null && sujetoO.getNumeroRegistroPatronal().trim().length() != 0) {
			if(sujetoO.getNumeroRegistroPatronal().length() != LONGITUD_NRP) {
				errors.rejectValue("numeroRegistroPatronal", "field.length", new Object[] {new Integer(LONGITUD_NRP)}, "");
			}
		} else {
			errors.rejectValue("numeroRegistroPatronal", "field.required");
		}
		
		if(!StringUtils.isBlank(sujetoO.getStringClasificacion())) {
			if(sujetoO.getStringClasificacion().trim().length() != LONGITUD_CLASIFICACION) {
				errors.rejectValue("stringClasificacion", "field.length", new Object[] {new Integer(LONGITUD_CLASIFICACION)}, "");
			}
		} else {
			errors.rejectValue("stringClasificacion", "field.required");
		}
		
		if(sujetoO.getSubdelegacion() == null) {
			errors.rejectValue("subdelegacion.id", "field.required");
			errors.rejectValue("subdelegacion.delegacion.id", "field.required");
		} else {
			if(sujetoO.getSubdelegacion().getId() == -1) {
				errors.rejectValue("subdelegacion.id", "field.required");
			}
			if(sujetoO.getSubdelegacion().getDelegacion() == null) {
				errors.rejectValue("subdelegacion.delegacion.id", "field.required");
			} else {
				if(sujetoO.getSubdelegacion().getDelegacion().getId() == -1) {
					errors.rejectValue("subdelegacion.delegacion.id", "field.required");
				}
			}
		}

	}

}
