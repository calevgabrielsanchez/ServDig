package mx.gob.imss.ctirss.delta.gestion.beneficio.web.controller.validator;

import mx.gob.imss.ctirss.delta.framework.util.RegexValidatorUtil;
import mx.gob.imss.ctirss.delta.gestion.beneficio.web.formModel.DatosEntradaRiss;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RissValidator implements Validator {

	private int tipoValidacion;

	@Override
	public boolean supports(Class<?> clazz) {
		return DatosEntradaRiss.class.equals(clazz);
	}

	@Override
	public void validate(Object datosEntrada, Errors errors) {

		DatosEntradaRiss datosEntradaRiss = (DatosEntradaRiss) datosEntrada;

		if (tipoValidacion == 1) {
			if (datosEntradaRiss.getOpcRISS().equals(DatosEntradaRiss.opcRissRfc)) {
				RegexValidatorUtil.validaRFCFisicaVista("rfc", errors,
						datosEntradaRiss.getRfc());
			} else if (datosEntradaRiss.getOpcRISS().equals(
					DatosEntradaRiss.opcRissNss)) {
				RegexValidatorUtil.validaNSSVista("nss", errors,
						datosEntradaRiss.getNss());
			}
		} else if (tipoValidacion == 2) {
			RegexValidatorUtil.validaRFCFisicaVista("rfc", errors,
					datosEntradaRiss.getRfc());
		}

	}

	public void setTipoValidacion(int tipoValidacion) {
		this.tipoValidacion = tipoValidacion;
	}

}
