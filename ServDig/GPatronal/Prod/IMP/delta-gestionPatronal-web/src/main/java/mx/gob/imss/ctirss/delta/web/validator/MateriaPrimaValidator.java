/**
 * delta-gestionPatronal-web22/05/2012
 * mx.gob.imss.ctirss.delta.web.validator22/05/2012
 * MateriaPrimaValidator.java
 * 22/05/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Luci Duran Silva
 * Instituto Mexicano del Seguro Social
 */
public class MateriaPrimaValidator implements Validator {

	private static MateriaPrimaValidator instance = new MateriaPrimaValidator();
	
	/**
	 * @return the instance
	 */
	public static MateriaPrimaValidator getInstance() {
		return instance;
	}

	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return MateriaPrima.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		MateriaPrima materia = (MateriaPrima) target;
		validaString(errors, materia.getDescripcion(), "descripcion", 300);
	}
	
	
	public void validaString(Errors errors, String valor, String campo, Integer size){
//		Pattern pattern = Pattern.compile("^.{1,"+size+"}");
//		Matcher matcher = pattern.matcher(valor);
		if(valor.length() > size.intValue()){
			errors.rejectValue(campo,"field.max.length",new Object[]{size},"");
		}
//		else if(!matcher.matches()){
//			errors.rejectValue(campo,"field.wrong.data", "field.wrong.data");
//		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, campo, "field.required");
	}

}
