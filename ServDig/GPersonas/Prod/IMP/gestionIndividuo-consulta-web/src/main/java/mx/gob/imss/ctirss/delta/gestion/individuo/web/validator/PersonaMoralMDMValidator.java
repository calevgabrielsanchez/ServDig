package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 
 * @author Marco Sánchez
 * 
 */

public class PersonaMoralMDMValidator implements Validator {

	private static final String REGEX_RFC_MORAL = "^([a-zA-Z]{3})\\d{6}([a-zA-Z0-9]{3})$";
	private static final int LENGTH_RFC = 12;

	@Override
	public boolean supports(Class<?> arg0) {
		return Moral.class.equals(arg0);
	}

	@Override
	public void validate(Object entrada, Errors errors) {

		MDMDatosEntrada mdmDatosEntrada = (MDMDatosEntrada) entrada;

		Moral pm = (Moral) mdmDatosEntrada.getPersonaMoral();

		if (mdmDatosEntrada.getIndCapturaDatosSAT()) {
			
			if(mdmDatosEntrada.getIndCapturaRazonSocial()){
				// Validaciones de Razón Social
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "razonSocial",
						"field.required");
			}

			if(mdmDatosEntrada.getIndCapturaFechaConstitucion()){
				// Validacion de FECHA DE CREACION
				if (pm.getDatosPersonaSAT() == null) {
					errors.rejectValue("datosPersonaSAT.fechaConstitucion", "field.required");
				} else if (pm.getDatosPersonaSAT().getFechaConstitucion() == null) {
					errors.rejectValue("datosPersonaSAT.fechaConstitucion", "field.required");
				} 
			}

			if(mdmDatosEntrada.getIndCapturaTipoSociedad()){
				if (pm.getTipoSociedad() == null) {
					errors.rejectValue("tipoSociedad.idTipoSociedad",
							"field.required");
				} else if (pm.getTipoSociedad().getIdTipoSociedad() == -1){
					errors.rejectValue("tipoSociedad.idTipoSociedad",
							"field.required");
				}
			}

			if (mdmDatosEntrada.getIndCapturaRFC()
					|| mdmDatosEntrada.getIndCapturaDomicilioFiscal()
					|| mdmDatosEntrada.getIndCapturaMediosContactoFiscales()) {
				// Validacion de RFC
				if (pm.getRfc() != null && !pm.getRfc().equals("")) {
	
					if (pm.getRfc().length() != LENGTH_RFC) {
						errors.rejectValue("rfc", "field.min.length",
								new Object[] { new Integer(LENGTH_RFC) }, "");
					} else {
						if (!pm.getRfc().matches(REGEX_RFC_MORAL)) {
							errors.rejectValue("rfc", "field.wrong.format");
						}
					}
	
				} else {
					errors.rejectValue("rfc", "field.required");
				}
			}
			
			if(mdmDatosEntrada.getIndCapturaDomicilioFiscal()){
				if(pm.getDomicilioFiscal() == null){
					errors.rejectValue("domicilioFiscal", "domicilio.fiscal.requerido");
				}
			}
		}
		
		if(mdmDatosEntrada.getIndCapturaDatosComplementarios()){
			if(mdmDatosEntrada.getIndCapturaActaConstitutiva()){
				if(pm.getEscrituraConstitutiva() == null){
					errors.rejectValue("escrituraConstitutiva", "escritura.constitutiva.requerida");
				}else{
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.numEscritura",
							"field.required");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.numNotaria",
							"field.required");
					
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.fechaExpedicion",
							"field.required");
					
					if(pm.getEscrituraConstitutiva().getLugarExpedicion().getEntidadFederativa().getClave().equals("-1")){
						errors.rejectValue("escrituraConstitutiva.lugarExpedicion.entidadFederativa.clave", "field.required");
						errors.rejectValue("escrituraConstitutiva.lugarExpedicion.clave", "field.required");
					}else if(pm.getEscrituraConstitutiva().getLugarExpedicion().getClave().equals("-1")){
						errors.rejectValue("escrituraConstitutiva.lugarExpedicion.clave", "field.required");
					}
					
					if(StringUtils.isBlank(pm.getEscrituraConstitutiva().getFolioMercantil())){
						if(!StringUtils.isBlank(pm.getEscrituraConstitutiva().getSeccion()) || !StringUtils.isBlank(pm.getEscrituraConstitutiva().getPartida()) ||
								!StringUtils.isBlank(pm.getEscrituraConstitutiva().getVolumen()) || !StringUtils.isBlank(pm.getEscrituraConstitutiva().getFoja())){
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.seccion",
									"field.required");
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.partida",
									"field.required");
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.volumen",
									"field.required");
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.foja",
									"field.required");
						} else{
							
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.folioMercantil",
									"field.required");
							
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.seccion",
									"field.required");
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.partida",
									"field.required");
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.volumen",
									"field.required");
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "escrituraConstitutiva.foja",
									"field.required");
						}
					}
				}
			}
			
			if(mdmDatosEntrada.getIndCapturaRegistroSindicato()){
				if(pm.getRegistroSindicato() == null){
					errors.rejectValue("registroSindicato", "registro.sindicato.requerido");
				}else {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "registroSindicato.numReferenciadocRegistro",
							"field.required");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "registroSindicato.fechaRegistro",
							"field.required");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "registroSindicato.autoridadLaboral",
							"field.required");
				}
			}
		}
	}

}
