package mx.gob.imss.cit.cda.web.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

public class PersonaRENAPOValidator extends AbstractValidator {

	protected final Log log = LogFactory.getLog(getClass());
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Fisica.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		log.debug("Validnado datos de entrada");

		Fisica pf = (Fisica) target;		
		
		boolean infoCurpInvalida;
		
		infoCurpInvalida = StringUtils.isBlank(pf.getCurp());
		infoCurpInvalida = infoCurpInvalida || StringUtils.isBlank(pf.getNombre());
		infoCurpInvalida = infoCurpInvalida || StringUtils.isBlank(pf.getSegundoApellido());
		infoCurpInvalida = infoCurpInvalida || StringUtils.isBlank(pf.getPrimerApellido());
		infoCurpInvalida = infoCurpInvalida || pf.getSexo() != null ? StringUtils.isBlank(pf.getSexo().getGenero()) : true;
		infoCurpInvalida = infoCurpInvalida || pf.getFechaNacimiento() == null;
		infoCurpInvalida = infoCurpInvalida || pf.getLugarNacimiento() == null;
		infoCurpInvalida = infoCurpInvalida || pf.getPais() != null ? StringUtils.isBlank(pf.getPais().getNacionalidad()):true;
		infoCurpInvalida = infoCurpInvalida || ((pf.getPais().getIdPais())) == 1 ?  pf.getActaNacimiento() == null && pf.getCartaNaturalizacion()== null :false ;
		
		if (infoCurpInvalida) {
			errors.rejectValue("curp", "label.solicitud.mensaje.error.renapo");
		}
		
		super.validate(target, errors);
	}
}

