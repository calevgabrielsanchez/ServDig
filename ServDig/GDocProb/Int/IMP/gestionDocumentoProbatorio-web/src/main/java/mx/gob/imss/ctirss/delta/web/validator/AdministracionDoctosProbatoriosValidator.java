package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.beans.DocumentoProbatorioFormWrapper;
import mx.gob.imss.ctirss.delta.model.enums.DocumentosEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AdministracionDoctosProbatoriosValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return DocumentoProbatorio.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {

		DocumentoProbatorioFormWrapper form = (DocumentoProbatorioFormWrapper) arg0;
		int cveTipoDoc = form.getTipoDocumentoProbatorio()
				.getIdTipoDocumentoProbatorio();

		// Se validan los campos obligatorios de acuerdo a cada tipo de documento
		if (cveTipoDoc == DocumentosEnum.ACTA_NACIMIENTO
				.getId().intValue()) {

			Nacimiento nacimiento = form.getActaNacimiento();

			if (nacimiento.getAnio() == null) {
				errors.rejectValue("actaNacimiento.anio", "field.required");
			}
			
			if (nacimiento.getNoLibro() == null) {
				errors.rejectValue("actaNacimiento.noLibro", "field.required");
			}	
			
			if(nacimiento.getMunicipio().getEntidadFederativa().getClave().equals("-1")){
				errors.rejectValue("actaNacimiento.municipio.entidadFederativa.clave", "field.required");
			}
			
			if(nacimiento.getMunicipio().getClave().equals("-1")){
				errors.rejectValue("actaNacimiento.municipio.clave", "field.required");
			}
			
		} else {
			CURP documentoProbatorioRENAPO = form
					.getDocProbRENAPO();

			if (cveTipoDoc == DocumentosEnum.DOCUMENTO_MIGRATORIO
					.getId().intValue()) {
				if (documentoProbatorioRENAPO == null) {
					errors.rejectValue("docProbRENAPO.numFolioExtranjero",
							"field.required");
					errors.rejectValue("docProbRENAPO.noActa",
							"field.required");
				} else {
					if (StringUtils.isBlank(documentoProbatorioRENAPO
							.getNumFolioExtranjero())
							&& StringUtils.isBlank(documentoProbatorioRENAPO
									.getNoActa())) {
						errors.rejectValue(
								"docProbRENAPO.numFolioExtranjero",
								"field.required");
						errors.rejectValue(
								"docProbRENAPO.noActa",
								"field.required");
					}
				}
			} else if (cveTipoDoc == DocumentosEnum.CARTA_NATURALIZACION
					.getId().intValue()) {
				if (documentoProbatorioRENAPO == null) {
					errors.rejectValue("docProbRENAPO.anioRegistro", "field.required");
					errors.rejectValue("docProbRENAPO.numFolioExtranjero", "field.required");
				} else {
					if (documentoProbatorioRENAPO.getAnioRegistro() == null
							&& StringUtils.isBlank(documentoProbatorioRENAPO
									.getNumFolioExtranjero())) {
						errors.rejectValue("docProbRENAPO.anioRegistro", "field.required");
						errors.rejectValue("docProbRENAPO.numFolioExtranjero", "field.required");
					}
				}
			} else if (cveTipoDoc == DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO
					.getId().intValue()) {
				if (documentoProbatorioRENAPO == null) {
					errors.rejectValue("docProbRENAPO.numFolioExtranjero", "field.required");
				} else {
					if (StringUtils.isBlank(documentoProbatorioRENAPO.getNumFolioExtranjero())) {
						errors.rejectValue("docProbRENAPO.numFolioExtranjero", "field.required");
					}
				}
			} else if (cveTipoDoc == DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA
					.getId().intValue()) {
				if (documentoProbatorioRENAPO == null) {
					errors.rejectValue("docProbRENAPO.anioRegistro", "field.required");
					errors.rejectValue("docProbRENAPO.numFolioExtranjero", "field.required");
				} else {
					if (documentoProbatorioRENAPO.getAnioRegistro() == null && 
							StringUtils.isBlank(documentoProbatorioRENAPO.getNumFolioExtranjero())) {
						errors.rejectValue("docProbRENAPO.anioRegistro", "field.required");
						errors.rejectValue("docProbRENAPO.numFolioExtranjero", "field.required");
					}
				}
			} else if (cveTipoDoc == DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO
					.getId().intValue()) {
				if (documentoProbatorioRENAPO == null) {
					errors.rejectValue("docProbRENAPO.numFolioExtranjero", "field.required");
				} else {
					if (StringUtils.isBlank(documentoProbatorioRENAPO.getNumFolioExtranjero())) {
						errors.rejectValue("docProbRENAPO.numFolioExtranjero", "field.required");
					}
				}
			} else if (cveTipoDoc == DocumentosEnum.FORMA_MIGRATORIA_TURISTA
					.getId().intValue()) {
				if (documentoProbatorioRENAPO == null) {
					errors.rejectValue("docProbRENAPO.numFolioExtranjero", "field.required");
				} else {
					if (StringUtils.isBlank(documentoProbatorioRENAPO.getNumFolioExtranjero())) {
						errors.rejectValue("docProbRENAPO.numFolioExtranjero", "field.required");
					}
				}
			}
		}
	}
}