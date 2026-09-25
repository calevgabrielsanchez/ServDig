/**
 * delta-gestionPatronal-web01/06/2012
 * mx.gob.imss.ctirss.delta.web.validator01/06/2012
 * MaquinariaEquipoValidator.java
 * 01/06/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Luci Duran Silva
 * Instituto Mexicano del Seguro Social
 */
public class EquipoTransporteValidator implements Validator {

	
	private static EquipoTransporteValidator instance = new EquipoTransporteValidator();
	
	/**
	 * @return the instance
	 */
	public static EquipoTransporteValidator getInstance() {
		return instance;
	}


	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return EquipoTransporte.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		EquipoTransporte equipo = ((EquipoTransporte) target);
		validaString(errors, equipo.getDesCapacidadPotencia(), 	"desCapacidadPotencia", 25, true);
		validaString(errors, equipo.getDesNombre(), 			"desNombre", 30, true);
		validaString(errors, equipo.getDesUso(), 				"desUso", 40, true);
		validaNumeroMayorACero(errors, equipo.getNumUnidades(), 			"numUnidades");
	
		TipoCombustible tc = equipo.getTipoCombustible();
		if(tc == null || (tc != null && tc.getClave() == null)
		||(tc != null && tc.getClave() != null && tc.getClave() <= 0)){
			errors.rejectValue("tipoCombustible.desTipoCombustible", "field.required", "field.required");
		}
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
	
	public void validaNumeroMayorACero(Errors errors, BigDecimal valor, String campo){
		Pattern pattern = Pattern.compile("^[0-9]{1,5}");
		Matcher matcher = pattern.matcher(valor == null ? "" : valor.toString());
		if(valor == null || (valor != null && valor.longValue() <= 0)){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, campo, "field.required");
			if(valor != null && valor.longValue() <= 0)
				errors.rejectValue(campo,"field.min.number.value","El valor debe ser mayor a 0");
		}else if(!matcher.matches()){
			errors.rejectValue(campo,"field.wrong.format", "field.wrong.format");
		}
	}
	public void validaString(Errors errors, String valor, String campo, Integer size, boolean isRequerido){
//		Pattern pattern = Pattern.compile("^.{1,"+size+"}");
//		Matcher matcher = pattern.matcher(valor);
		if(isRequerido){
			if(valor == null || (valor != null && StringUtils.isBlank(valor))){
				errors.rejectValue(campo,"field.required", "field.required");
			}else if(valor.length() < 1){
				errors.rejectValue(campo,"field.required", "field.required");
			}
		}else if(valor.length() > size.intValue()){
			errors.rejectValue(campo,"field.max.length",new Object[]{size},"");
		}
//		else if(valor != null && !StringUtils.isBlank(valor) && !matcher.matches()){
//			errors.rejectValue(campo,"field.wrong.data", "field.wrong.data");
//		}
	}
}
