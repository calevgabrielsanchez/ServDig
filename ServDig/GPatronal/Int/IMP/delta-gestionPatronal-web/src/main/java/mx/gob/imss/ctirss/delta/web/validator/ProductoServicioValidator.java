/**
 * delta-gestionPatronal-web22/05/2012
 * mx.gob.imss.ctirss.delta.web.validator22/05/2012
 * ProductoServicioValidator.java
 * 22/05/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Luci Duran Silva
 * Instituto Mexicano del Seguro Social
 */
public class ProductoServicioValidator implements
Validator{
	
	@Override
	public boolean supports(Class<?> arg0) {
		return Producto.class.equals(arg0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Producto porducto = (Producto) target;
		validaString(errors, porducto.getDescripcion(), "descripcion", 300);
	}
	
	public void validaNumero(Errors errors, BigDecimal valor, String campo){
		Pattern pattern = Pattern.compile("^[0-9]{1,5}");
		Matcher matcher = pattern.matcher(valor == null ? "" : valor.toString());
		if(valor == null || (valor != null && valor.longValue() <= 0)){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, campo, "field.required");
		}else if(!matcher.matches()){
			errors.rejectValue(campo,"field.wrong.format", "field.wrong.format");
		}
	}
	
	public void validaString(Errors errors, String valor, String campo, Integer size){
//		Pattern pattern = Pattern.compile("^.{1,"+size+"}");
//		Matcher matcher = pattern.matcher(valor);
		if(valor == null || (valor != null && StringUtils.isBlank(valor))){
			errors.rejectValue(campo,"field.required", "field.required");
		}else if(valor.length() < 1){
			errors.rejectValue(campo,"field.required", "field.required");
		}else if(valor.length() > size.intValue()){
			errors.rejectValue(campo,"field.max.length",new Object[]{size},"");
		}
//		else if(!matcher.matches()){
//			errors.rejectValue(campo,"field.wrong.data", "field.wrong.data");
//		}
	}

}
