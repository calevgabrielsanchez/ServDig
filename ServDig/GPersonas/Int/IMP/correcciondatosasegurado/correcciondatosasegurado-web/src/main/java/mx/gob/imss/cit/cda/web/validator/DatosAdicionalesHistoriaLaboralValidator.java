package mx.gob.imss.cit.cda.web.validator;

import mx.gob.imss.cit.cda.web.vo.DatosAdicionalesHistoriaLaboral;
import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class DatosAdicionalesHistoriaLaboralValidator extends AbstractValidator{
	
	public void validate(Object target, Errors errors) {
		DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral = (DatosAdicionalesHistoriaLaboral)target;		
		valida(errors,datosAdicionalesHistoriaLaboral.getDatosContacto().getTelefonoFijo(),"datosContacto.telefonoFijo",10, "label.solicitud.error.telefonoFijo");
		valida(errors,datosAdicionalesHistoriaLaboral.getDatosContacto().getTelefonoCelular(),"datosContacto.telefonoCelular",10, "label.solicitud.error.telefonoCelular");
		
		

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
