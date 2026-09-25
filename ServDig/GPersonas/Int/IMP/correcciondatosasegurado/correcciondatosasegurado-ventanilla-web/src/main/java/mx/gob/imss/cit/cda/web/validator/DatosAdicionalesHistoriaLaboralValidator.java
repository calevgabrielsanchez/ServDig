package mx.gob.imss.cit.cda.web.validator;

import mx.gob.imss.cit.cda.web.vo.DatosAdicionalesHistoriaLaboral;
import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class DatosAdicionalesHistoriaLaboralValidator extends AbstractValidator{
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public void validate(Object target, Errors errors) {
		DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral = (DatosAdicionalesHistoriaLaboral)target;	
		valida(errors,datosAdicionalesHistoriaLaboral.getDatosContacto().getTelefonoFijo(),"datosContacto.telefonoFijo",10, "label.solicitud.error.telefonoFijo");
		valida(errors,datosAdicionalesHistoriaLaboral.getDatosContacto().getTelefonoCelular(),"datosContacto.telefonoCelular",10, "label.solicitud.error.telefonoCelular");
		
		
		if (datosAdicionalesHistoriaLaboral.getDatosContacto().getCorreoElectronico() != null && !datosAdicionalesHistoriaLaboral.getDatosContacto().getCorreoElectronico().isEmpty()) {
			if (!datosAdicionalesHistoriaLaboral.getDatosContacto().getCorreoElectronico().matches(EMAIL_PATTERN)){
				errors.rejectValue("datosContacto.correoElectronico", "field.formato.correo.incorrecto");
			}
		}

	}
	
	private void valida(Errors errors, String dato, String campo, int min, String mensaje) {
		if (dato!=null && dato.length() > 0){
			if (dato.length() < min) {
				errors.rejectValue(campo, mensaje, mensaje);
			} else {
				try {
					Long.valueOf(dato);
				} catch (Exception e) {
					errors.rejectValue(campo, mensaje, mensaje);
				}
			}
		}
	}

}
