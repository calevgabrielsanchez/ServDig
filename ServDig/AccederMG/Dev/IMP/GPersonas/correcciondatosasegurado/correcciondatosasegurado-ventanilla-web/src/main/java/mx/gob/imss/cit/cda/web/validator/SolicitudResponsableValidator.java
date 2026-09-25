package mx.gob.imss.cit.cda.web.validator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Component
public class SolicitudResponsableValidator extends AbstractValidator {
	
	private static final String REGEX_CURP_FISICA = "^([A-Z]{4})\\d{6}([A-Z]{6})([A-Z0-9])\\d{1}$";
	private static final int LENGTH_CURP = 18;
	private static final Logger LOG = LoggerFactory.getLogger(SolicitudResponsableValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Fisica.class.equals(clazz);
	}
		
	public void validate(Object object, Errors errors){
		LOG.debug("---CDA--- Validaciones de solicitud por responsable *****: ");
		Fisica personaFisica = (Fisica) object;		
		if(StringUtils.isNotBlank(personaFisica.getCurp())){
			validateCurp(personaFisica.getCurp(), errors);
			LOG.debug(" ---CDA--- Validacion del curp result {}",errors.getErrorCount());			
		}else if(validarPersonaCapturadaNull(personaFisica)){	
			errors.rejectValue("curp", "field.required");
		}else{
			LOG.debug("******** nombre ********" + personaFisica.getNombre() );
			LOG.debug("******** apellido ********" + personaFisica.getPrimerApellido() );
			LOG.debug("******** apellido ********" + personaFisica.getSegundoApellido() );
			LOG.debug("******** fecha ********" + personaFisica.getFechaNacimiento() );
			LOG.debug("******** lugar ********" + personaFisica.getLugarNacimiento() );
			LOG.debug("******** sexo ********" + personaFisica.getSexo());
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "field.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "primerApellido", "field.required");
			if (personaFisica.getFechaNacimiento()==null ){
				errors.rejectValue("fechaNacimiento", "field.required");
			}	
			if (personaFisica.getLugarNacimiento()!= null && personaFisica.getLugarNacimiento().getClave().equals("-1")){
				errors.rejectValue("lugarNacimiento.clave", "field.required");
			}
			if (!(personaFisica.getSexo()!=null && personaFisica.getSexo().getIdSexo()!=null)){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sexo.idSexo", "field.required");
			}			
			LOG.debug("curp vacio datos basicos llenos");
		}
	}


	private void validateCurp(String curp, Errors errors){
			if (curp.length() != LENGTH_CURP) {
				errors.rejectValue("curp", "field.min.length",
						new Object[] { new Integer(LENGTH_CURP) }, "");
			} else if (!curp.matches(REGEX_CURP_FISICA)) {
				errors.rejectValue("curp", "field.curp.formato.incorrecto");
			}		
	}
	
	private boolean validarPersonaCapturadaNull(Fisica fisica){
		
		boolean personaValida = StringUtils.isBlank(fisica.getNombre());
		
		personaValida = personaValida && StringUtils.isBlank(fisica.getPrimerApellido());
		
		personaValida = personaValida && StringUtils.isBlank(fisica.getSegundoApellido());
		
		personaValida = personaValida && fisica.getFechaNacimiento()==null;
		
		personaValida = personaValida && fisica.getLugarNacimiento().getClave().equals("-1");
		
		personaValida = personaValida && !(fisica.getSexo()!=null && fisica.getSexo().getIdSexo()!=null);
		
		return personaValida;
	}
}
