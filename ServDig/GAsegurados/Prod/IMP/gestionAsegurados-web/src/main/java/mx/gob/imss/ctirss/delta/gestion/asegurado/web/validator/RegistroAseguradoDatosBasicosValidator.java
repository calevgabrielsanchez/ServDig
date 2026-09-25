/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Samuel Rodríguez Grajeda
 *  @Proyecto: Gestion de Personas
 *  @Archivo:PersonaFisicaDatosBasicosValidator.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.individuo.web.validator
 *  @Fecha:17/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

/**
 * @author Samuel Rodríguez Grajeda
 * 
 */
public class RegistroAseguradoDatosBasicosValidator extends AbstractValidator {
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
	private static final int LENGTH_CURP = 18;
	private static final String REGEX_FECHA = "^(0[1-9]|[12][0-9]|3[01])[//](0[1-9]|1[012])[//](19|20)[0-9][0-9]$";
	private static final String FORMATO_FECHA = "dd/MM/yyyy";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Fisica.class.equals(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(Object personaFisica, Errors errors) {
		Fisica pf = (Fisica) personaFisica;
		int numDatosBasicos = contarDatosBasicos(pf, errors);

		if (StringUtils.isBlank(pf.getCurp())) {
			if (numDatosBasicos == 0) {
				errors.rejectValue("errorFormGeneral",
						"msg.error.formulario.vacio", new Object[] {}, "");
			} else {
				validateDatosBasicosCompletos(pf, errors);
			}
		} else {
			if (pf.getCurp().length() != LENGTH_CURP) {
				errors.rejectValue("curp", "field.min.length",
						new Object[] { new Integer(LENGTH_CURP) }, "");
			} else if (!pf.getCurp().matches(REGEX_CURP_FISICA)) {
				errors.rejectValue("curp", "field.wrong.format");
			}

			if (numDatosBasicos != 0) {
				validateDatosBasicosCompletos(pf, errors);
			}
		}
	}

	private int contarDatosBasicos(Fisica personaFisica, Errors errors) {
		int numeroCampos = 0;

		if (StringUtils.isNotBlank(personaFisica.getNombre())) {
			numeroCampos++;
		}
		if (StringUtils.isNotBlank(personaFisica.getPrimerApellido())) {
			numeroCampos++;
		}
		if (StringUtils.isNotBlank(personaFisica.getSegundoApellido())) {
			numeroCampos++;
		}
		if (personaFisica.getSexo().getIdSexo() != null
				&& personaFisica.getSexo().getIdSexo() != -1) {
			numeroCampos++;
		}
		if (personaFisica.getFechaNacimiento() != null) {
			if (!new SimpleDateFormat(FORMATO_FECHA).format(
					personaFisica.getFechaNacimiento()).matches(REGEX_FECHA)) {
				errors.rejectValue("fechaNacimiento", "field.wrong.format",
						new Object[] {}, "");
			} else if (personaFisica.getFechaNacimiento().after(new Date())) {
				errors.rejectValue("fechaNacimiento", "msg.error.fecha.nacimiento.despues.actual");
			}

			numeroCampos++;
		}
		if (!personaFisica.getLugarNacimiento().getClave().equals("")
				&& !personaFisica.getLugarNacimiento().getClave().equals("-1")) {
			numeroCampos++;
		}

		return numeroCampos;
	}

	private boolean validateDatosBasicosCompletos(Fisica personaFisica,
			Errors errors) {
		boolean isDatosCompletos = true;

		if (StringUtils.isBlank(personaFisica.getNombre())) {
			errors.rejectValue("nombre", "field.required", new Object[] {}, "");
			isDatosCompletos = false;
		}
		
		if (StringUtils.isBlank(personaFisica.getPrimerApellido())) {
			errors.rejectValue("primerApellido", "field.required",
					new Object[] {}, "");
			isDatosCompletos = false;
		}
		
		if (personaFisica.getSexo().getIdSexo() == null
				|| personaFisica.getSexo().getIdSexo() == -1) {
			errors.rejectValue("sexo.idSexo", "field.required",
					new Object[] {}, "");
			isDatosCompletos = false;
		}
		
		if (errors.getFieldError("fechaNacimiento") == null
				&& personaFisica.getFechaNacimiento() == null) {
			errors.rejectValue("fechaNacimiento", "field.required",
					new Object[] {}, "");
			isDatosCompletos = false;
		} 
		
		if (personaFisica.getLugarNacimiento().getClave().equals("-1")
				|| personaFisica.getLugarNacimiento().getClave().equals("")) {
			errors.rejectValue("lugarNacimiento.clave", "field.required",
					new Object[] {}, "");
			isDatosCompletos = false;
		}

		return isDatosCompletos;
	}
}