/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Samuel Rodríguez Grajeda
 *  @Proyecto: Gestion de Personas
 *  @Archivo:PersonaMoralDatosBasicosValidator.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.individuo.web.validator
 *  @Fecha:17/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * @author Samuel Rodríguez Grajeda
 *
 */
public class PersonaMoralCapturaValidator extends AbstractValidator {
	
	private static final String REGEX_RFC_MORAL = "^([a-zA-Z\u0026]{3})\\d{6}([\\w]{3})$";
	private static final int LENGTH_RFC  = 12;

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Moral.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object personaMoral, Errors errors) {
		
		Moral pf = (Moral) personaMoral;
			
		if(pf.getRfc() != null && !pf.getRfc().equals("")){
			if(pf.getRfc().length() != LENGTH_RFC){
				errors.rejectValue("rfc", "field.min.length", new Object[] {new Integer(LENGTH_RFC)}, "");
			}else{
				if(! pf.getRfc().matches(REGEX_RFC_MORAL)){
					errors.rejectValue("rfc", "field.wrong.format");
				}
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actaConstitutiva", "field.required");
		
		if(Utilerias.isBlank(pf.getTipoSociedad().getIdTipoSociedad())) {
		    errors.rejectValue("idTipoSociedad", "field.select", new Object[]{}, "");
		}
		
	}

}