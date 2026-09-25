package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;

import java.text.SimpleDateFormat;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ClavesRenapo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 
 * @author Marco Sánchez
 * 
 */

public class PersonaFisicaMDMValidator implements Validator {

	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
	private static final int LENGTH_CURP = 18;
	private static final String REGEX_RFC_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z\\w]{3})$";
	private static final int LENGTH_RFC = 13;

	@Override
	public boolean supports(Class<?> arg0) {
		return Fisica.class.equals(arg0);
	}

	@Override
	public void validate(Object entrada, Errors errors) {

		MDMDatosEntrada mdmDatosEntrada = (MDMDatosEntrada) entrada;

		Fisica pf = (Fisica) mdmDatosEntrada.getPersonaFisica();

		if (mdmDatosEntrada.getIndCapturaDatosRENAPO()) {
			if (mdmDatosEntrada.getIndCapturaNombre()) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre",
						"field.required");
	
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "primerApellido",
						"field.required");
			}

			if (mdmDatosEntrada.getIndCapturaSexo()) {
				if (pf.getSexo().getIdSexo().intValue() == -1) {
					errors.rejectValue("sexo.idSexo", "field.required");
				}
			}

			if (mdmDatosEntrada.getIndCapturaFechaNacimiento()) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors,
						"fechaNacimiento", "field.required");
			}

			if(mdmDatosEntrada.getIndCapturaLugarNacimiento()){
				if (pf.getPais().getIdPais().intValue() == -1) {
					errors.rejectValue("pais.idPais", "field.required");
				}
				
				if(StringUtils.isBlank(pf.getLugarNacimiento().getClave()) 
						|| pf.getLugarNacimiento().getClave().equals("-1")){
					errors.rejectValue("lugarNacimiento.clave", "field.required");
				}
			}
			
			if(mdmDatosEntrada.getIndCapturaCURP()){
				// Validacion de CURP
				if (StringUtils.isNotBlank(pf.getCurp())) {
					if (pf.getCurp().length() != LENGTH_CURP) {
						errors.rejectValue("curp", "field.min.length",
								new Object[] { new Integer(LENGTH_CURP) }, "");
					} else {
						if (!pf.getCurp().matches(REGEX_CURP_FISICA)) {
							errors.rejectValue("curp", "field.wrong.format");
						}
					}
					
					if(mdmDatosEntrada.getIndCapturaSexo()){
						// Se valida que el sexo coincida con el CURP
						if (pf.getSexo() != null && pf.getSexo().getIdSexo().intValue() != -1) {
			
							String sexoCurp = pf.getCurp().substring(10, 11);
							int idSexoCurp = 0;
			
							if (sexoCurp.toUpperCase().equals("H")) {
								idSexoCurp = 1;
							} else if (sexoCurp.toUpperCase().equals("M")) {
								idSexoCurp = 2;
							}
			
							if (pf.getSexo().getIdSexo() != idSexoCurp) {
								errors.rejectValue("sexo.idSexo", "sexo.no.coincide.curp");
							}
						}
					}
		
					if(mdmDatosEntrada.getIndCapturaFechaNacimiento()){
						// Se valida que la fecha de nacimiento coincida con el CURP
						if (pf.getFechaNacimiento() != null) {
							String fechaFormatoCURP = new SimpleDateFormat("yyMMdd")
									.format(pf.getFechaNacimiento());
							String fechaCURP = pf.getCurp().substring(4, 10);
			
							if (!fechaCURP.equals(fechaFormatoCURP)) {
								errors.rejectValue("fechaNacimiento",
										"fechaNacimiento.no.coincide.curp");
							}
						}
					}
		
					if(mdmDatosEntrada.getIndCapturaLugarNacimiento()){
						// Se valida que la entidad de nacimiento coincida con el CURP
						if (pf.getLugarNacimiento() != null 
								&& StringUtils.isNotBlank(pf.getLugarNacimiento().getClave()) 
								&& !pf.getLugarNacimiento().getClave().equals("-1")) {
							String lugarNacimientoCURP = pf.getCurp().substring(11, 13)
									.toUpperCase();
							int cveLugarNacimiento = Integer.valueOf(pf.getLugarNacimiento().getClave());
							
							/*
							 * Debido a que el arreglo que contiene las claves
							 * de los estados, no cuadra con el id del catálogo
							 * en base de datos, se hacen las siguientes
							 * validaciones
							 */
							if (cveLugarNacimiento == ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get("NE")) {
								cveLugarNacimiento = 33;
							} else if (cveLugarNacimiento == ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get("SE")) {
								cveLugarNacimiento = 34;
							}
							
							String lugarNacimiento = ClavesRenapo.DESCRIPCION_ENT_FED_ARRY[cveLugarNacimiento];
			
							if (!lugarNacimiento.equals(lugarNacimientoCURP)) {
								errors.rejectValue("lugarNacimiento.clave",
										"lugarNacimiento.no.coincide.curp");
							}	
						}
					}
				}
			}
			
			if(mdmDatosEntrada.getIndCapturaDocumentoProbatorio()){
				List<DocumentoProbatorio> docsProbatorios = pf.getDocumentosProbatorios();
				
				if (docsProbatorios == null) {
					errors.rejectValue("documentosProbatorios",
							"documentos.probatorios.requeridos");
				} else if (docsProbatorios.isEmpty()) {
					errors.rejectValue("documentosProbatorios",
							"documentos.probatorios.requeridos");
				} else {
					boolean isValid = false;
					for(DocumentoProbatorio docto : docsProbatorios){
						if (docto.getEstadoAdministracionDocto() == null
								|| docto.getEstadoAdministracionDocto()
										.getClave() != EstadoAdministracionEnum.ELIMINADO
										.getClave()) {
							isValid = true;
							break;
						}
					}
					
					if(!isValid){
						errors.rejectValue("documentosProbatorios","documentos.probatorios.requeridos");
					}
				}
			}
		}

		if (mdmDatosEntrada.getIndCapturaDatosSAT()) {
			
			if (mdmDatosEntrada.getIndCapturaRFC()
					|| mdmDatosEntrada.getIndCapturaDomicilioFiscal()
					|| mdmDatosEntrada.getIndCapturaMediosContactoFiscales()){
				if (pf.getRfc() != null && !pf.getRfc().equals("")) {
	
					if (pf.getRfc().length() != LENGTH_RFC) {
						errors.rejectValue("rfc", "field.min.length",
								new Object[] { new Integer(LENGTH_RFC) }, "");
					} else {
						if (!pf.getRfc().matches(REGEX_RFC_FISICA)) {
							errors.rejectValue("rfc", "field.wrong.format");
						}
					}
	
				} else {
					errors.rejectValue("rfc", "field.required");
				}
			}

			if(mdmDatosEntrada.getIndCapturaDomicilioFiscal()){
				if (pf.getDomicilioFiscal() == null) {
					errors.rejectValue("domicilioFiscal",
							"domicilio.fiscal.requerido");
				}
			}
		}

		if (mdmDatosEntrada.getIndCapturaDatosComplementarios()) {
			if(mdmDatosEntrada.getIndCapturaDomicilioParticular()){
				if (pf.getDomicilios() == null){
					errors.rejectValue("domicilios",
							"domicilio.particular.requerido");
				} else if( pf.getDomicilios().isEmpty()) {
					errors.rejectValue("domicilios",
							"domicilio.particular.requerido");
				} else if( pf.getDomicilios().get(0) == null) {
					errors.rejectValue("domicilios",
							"domicilio.particular.requerido");
				}else {
					int numDomPorEliminar = 0;
					for (Domicilio domicilio : pf.getDomicilios()) {
						if (domicilio.getEstadoAdministracionDomicilio() != null) {
							if (domicilio.getEstadoAdministracionDomicilio()
									.getClave() != EstadoAdministracionEnum.ELIMINADO
									.getClave()) {
								break;
							} else {
								numDomPorEliminar++;
							}
						}
					}

					if (numDomPorEliminar == pf.getDomicilios().size()) {
						errors.rejectValue("domicilios",
								"domicilio.particular.requerido");
					}
				}
			}
		}
	}

}
