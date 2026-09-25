/**
 * delta-gestionPatronal-web08/05/2012
 * mx.gob.imss.ctirss.delta.web.validator08/05/2012
 * EscrituraConstitutivaValidator.java
 * 08/05/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import java.util.regex.Pattern;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Luci Duran Silva Instituto Mexicano del Seguro Social
 */
public class DatosContactoValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return SujetoObligado.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		SujetoObligado so = ((SujetoObligado) target);
		if (TipoPersonaFiscal.FISICA.equals(so.getTipoPersonaFiscal())) {
			Fisica f = so.getFisica();
			if(valida(errors, f.getTelefonoFijo().getNumero(), "fisica.telefonoFijo.numero", 10)){// 10 dijo Eric
				valida(errors, f.getTelefonoFijo().getExtension(), "fisica.telefonoFijo.extension", 0);
			}else{
				if(f.getTelefonoFijo().getNumero()!=null && f.getTelefonoFijo().getNumero().length()==0)
					errors.rejectValue("fisica.telefonoFijo.extension", "field.invalid", "field.invalid");
			}
			valida(errors, f.getTelefonoMovil().getNumero(), "fisica.telefonoMovil.numero", 10);
			validaMail(errors, f.getCorreoElectronico().getCorreo(), "fisica.correoElectronico.correo");
		} else {
			Moral m = so.getMoral();
			if(valida(errors, m.getTelefonoFijo().getNumero(), "moral.telefonoFijo.numero", 10)){
				if(!valida(errors, m.getTelefonoFijo().getExtension(), "moral.telefonoFijo.extension", 0)){
					m.getTelefonoFijo().setExtension("");
				}
			}else{
				if(m.getTelefonoFijo().getExtension()!=null && m.getTelefonoFijo().getExtension().length()!=0){
					errors.rejectValue("moral.telefonoFijo.extension", "field.invalid", "field.invalid");
				}else{
					m.getTelefonoFijo().setExtension("");
				}
				m.getTelefonoFijo().setNumero("");
			}
			valida(errors, m.getTelefonoMovil().getNumero(), "moral.telefonoMovil.numero", 10);
			validaMail(errors, m.getCorreoElectronico().getCorreo(), "moral.correoElectronico.correo");
		}
	}

	private void validaMail(Errors errors, String correo, String campo) {
		if(correo!=null && correo.length()>0){
			correo=correo.toLowerCase();
			if(!Pattern.compile("[a-z]+[a-z0-9\\._-]*@[a-z0-9]+(\\.[a-z]{2,3}){1,2}").matcher(correo).matches()){
				errors.rejectValue(campo, "field.invalid", "field.invalid");
			}
		}
	}

	private boolean valida(Errors errors, String dato, String campo, int min) {
		if (dato!=null && dato.length() > 0){
			if (dato.length() < min) {
				errors.rejectValue(campo, "field.invalid", "field.invalid");
			} else {
				try {
					Long.valueOf(dato);
				} catch (Exception e) {
					errors.rejectValue(campo, "field.invalid", "field.invalid");
				}
			}
		}else{
			return false;
		}
		return true;
	}
}
