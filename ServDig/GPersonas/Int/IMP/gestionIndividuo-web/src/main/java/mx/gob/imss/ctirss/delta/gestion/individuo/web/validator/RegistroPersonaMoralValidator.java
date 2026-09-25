/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Samuel Rodríguez Grajeda
 *  @Proyecto: Gestion de Personas
 *  @Archivo:PersonaFisicaDatosBasicosValidator.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.individuo.web.validator
 *  @Fecha:17/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * @author Samuel Rodríguez Grajeda
 *
 */
public class RegistroPersonaMoralValidator  extends AbstractValidator {
    
    private static final String REGEX_FECHA = "^(0[1-9]|[12][0-9]|3[01])[//](0[1-9]|1[012])[//](19|20)[0-9][0-9]$";
    private static final String FORMATO_FECHA = "dd/MM/yyyy";

    private static final int LENGTH_RFC  = 12;
    private static final String REGEX_RFC_MORAL = "^([a-zA-Z\u0026]{3})\\d{6}([\\w]{3})$";
    
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
		
		Moral pm = (Moral) personaMoral;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rfc", "field.required");
	    if(pm.getRfc() != null && !pm.getRfc().equals("")){
            if(pm.getRfc().length() != LENGTH_RFC){
                errors.rejectValue("rfc", "field.min.length", new Object[] {new Integer(LENGTH_RFC)}, "");
            }else{
                if(!pm.getRfc().matches(REGEX_RFC_MORAL)){
                    errors.rejectValue("rfc", "field.wrong.format");
                }
            }
        }
	    
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "razonSocial", "field.required");
		
		if(pm.getTipoSociedad().getIdTipoSociedad() == -1) {
			errors.rejectValue("tipoSociedad.idTipoSociedad", "field.required", new Object[]{}, "");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actaConstitutiva", "field.required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fechaCreacion", "field.required");
        if(pm.getFechaCreacion() != null) {
            if(!new SimpleDateFormat(FORMATO_FECHA).format(pm.getFechaCreacion()).matches(REGEX_FECHA)){
                errors.rejectValue("fechaCreacion", "field.wrong.format", new Object[]{}, "");
            }
	        if(pm.getFechaCreacion().compareTo(new Date()) > 0){
	        	errors.rejectValue("fechaCreacion", "busqueda.fecha.error.mayor");
	        }
        }

	}

}