/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta-gestionPatronal
 *  @Archivo:LoginValidator.java
 *  @Paquete:mx.gob.imss.delta-gestionPatronal.web.validator
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
//import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;

import org.springframework.validation.Errors;
//import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class NombreComercialValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return SujetoObligado.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object usuario, Errors errors) {
//		if(((SujetoObligado)usuario).getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL))
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "moral.nombreComercial", "field.required");
//		else
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fisica.nombreComercial", "field.required");
	}

}
