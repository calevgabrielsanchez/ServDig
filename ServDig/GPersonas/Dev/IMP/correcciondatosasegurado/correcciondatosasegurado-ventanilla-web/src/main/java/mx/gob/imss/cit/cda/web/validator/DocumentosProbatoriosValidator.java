package mx.gob.imss.cit.cda.web.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoEnum;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class DocumentosProbatoriosValidator{
	
	private static final Logger log = LoggerFactory.getLogger(DocumentosProbatoriosValidator.class);

	private void validarDocumentosProbatorios(List<DocumentoProbatorio> list, Errors errors, Integer tipoSolicitante) {		
		log.debug("--CDA-- ##### validando Documentos obligatorios #####");
			
		Collection<Long> idDocumentos = CollectionUtils.collect(list, new BeanToPropertyValueTransformer("cveIdDocumento"));
		Collection<Long> tipoDocumentos = CollectionUtils.collect(list, new BeanToPropertyValueTransformer("tipoDocumento"));
		int actas = Collections.frequency(tipoDocumentos, 2);
		int ids = Collections.frequency(tipoDocumentos, 1 );
		log.debug("ID DOCUMENTOS {}", idDocumentos);
		log.debug("TIPO DOCUMENTOS {}", tipoDocumentos);
		Collection<Long> docsObligatoriosCollection;
		Collection<Long> tiposDocsFaltantes = null;
		switch (tipoSolicitante){
		//conyugue
		case 2:
			log.debug("--CDA-- Docs conyugue"); 
			if (actas == 3 && ids == 2 && idDocumentos.contains(new Long(DocumentoEnum.ACTA_MATRIMONIO.getId())) 
					&& idDocumentos.contains(new Long(DocumentoEnum.ACTA_NACIMIENTO.getId())) && idDocumentos.contains(new Long(DocumentoEnum.ACTA_CERTIFICADA_DEFUNCION.getId()))){
				docsObligatoriosCollection =  CollectionUtils.intersection(idDocumentos, listaDocumentosProbatoriosConyugue().keySet());
				tiposDocsFaltantes = compararListas(docsObligatoriosCollection, listaDocumentosProbatoriosConyugue(), getTiposDocumentosObligatoriosVentanilla());
			} else {
				errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.faltanObligatorios");
			}
			break;
		//hijos
		case 3:
			log.debug("--CDA-- HIJO "); 
			if (actas==3 && ids == 2 && Collections.frequency(idDocumentos, 1L) == ids 
					&& idDocumentos.contains(new Long(DocumentoEnum.ACTA_CERTIFICADA_DEFUNCION.getId()))){
				log.debug("--CDA-- Docs hijo"); 
				docsObligatoriosCollection = CollectionUtils.intersection(idDocumentos, listaDocumentosProbatoriosDefuncion().keySet());
				tiposDocsFaltantes = compararListas(docsObligatoriosCollection, listaDocumentosProbatoriosDefuncion(), 
						getTiposDocumentosObligatoriosVentanilla());
			}
			else {
				errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.faltanObligatorios");
			}
			break;
		//padres
		case 4:
			log.debug("--CDA-- PADRES");
			if (actas == 2 && ids == 2 && idDocumentos.contains(new Long(DocumentoEnum.ACTA_NACIMIENTO.getId())) 
					&& idDocumentos.contains(new Long(DocumentoEnum.ACTA_CERTIFICADA_DEFUNCION.getId()))){
				docsObligatoriosCollection = CollectionUtils.intersection(idDocumentos, listaDocumentosProbatoriosDefuncion().keySet());
				tiposDocsFaltantes = compararListas(docsObligatoriosCollection, listaDocumentosProbatoriosDefuncion(), 
						getTiposDocumentosObligatoriosVentanilla());
			} else {
				errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.faltanObligatorios");
			}
			break;
		//concubino
		case 5:
			log.debug("--CDA-- CONCUBINO");
			if(actas == 2 && ids == 2 && idDocumentos.contains(new Long(DocumentoEnum.ACTA_NACIMIENTO.getId())) 
					&& idDocumentos.contains(new Long(DocumentoEnum.ACTA_CERTIFICADA_DEFUNCION.getId()))){
				docsObligatoriosCollection =  CollectionUtils.intersection(idDocumentos, listaDocumentosProbatoriosConcubinato().keySet());
				tiposDocsFaltantes = compararListas(docsObligatoriosCollection, listaDocumentosProbatoriosConcubinato(), 
						getTipoDocumentosObligatoriosVentanillaMasJudicial());
			} else {
				errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.faltanObligatorios");
			}
			break;
		//representante
		case 6:
			log.debug("--CDA-- REPRESENTANTES");
			if(ids == 2){
				docsObligatoriosCollection =  CollectionUtils.intersection(idDocumentos, listaDocumentosProbatoriosRepresentante().keySet());
				tiposDocsFaltantes = compararListas(docsObligatoriosCollection, listaDocumentosProbatoriosRepresentante(), 
						getTipoDocumentosObligatoriosVentanillaMasJudicial());
			} else {
				errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.faltanObligatorios");
			}
			break;
		//Asegurado
		default:
			docsObligatoriosCollection =  CollectionUtils.intersection(idDocumentos,listaDocumentosProbatoriosVentanilla().keySet());
			tiposDocsFaltantes = compararListas(docsObligatoriosCollection, listaDocumentosProbatoriosVentanilla(), 
					getTiposDocumentosObligatoriosVentanilla());
		}
		if (tiposDocsFaltantes != null && !tiposDocsFaltantes.isEmpty()){
			errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.faltanObligatorios");
		}
		
	}
	
	private Map<Long, Long> listaDocumentosObligatoriosInternet(){
		Map<Long, Long> documentosProbatorios = new HashMap<Long, Long>();
		/**Acta**/
		documentosProbatorios.put(new Long(DocumentoEnum.ACTA_NACIMIENTO.getId()), new Long(TipoDocumentoProbatorioEnum.ACTAS.getId()));
		/**Identificacion Oficial**/
		documentosProbatorios.put(new Long(DocumentoEnum.CREDENCIAL_ELECTOR.getId()),new Long(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.PASAPORTE.getId()), new Long(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.CARTILLA_MILITAR.getId()),new Long(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.CEDULA_PROFESIONAL.getId()),new Long(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.MATRICULA_CONSULAR.getId()),new Long(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.IDENTIDAD_EXTRANJEROS.getId()),new Long(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.DOCUMENTO_MIGRATORIO.getId()),new Long(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.CURP.getId()),new Long(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()));
		/**Documentos con NSS**/
		documentosProbatorios.put(new Long(DocumentoEnum.AVISOS_AFILIATORIOS.getId()),new Long(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.TARJETA_AFILIACION.getId()),new Long(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.CERTIFICADO_DE_INCAPACIDAD.getId()),new Long(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.CITAS_MEDICAS.getId()),new Long(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.CREDENCIAL_ADIMSS.getId()),new Long(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.LIQUIDACIONES_PAGADAS.getId()),new Long(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.COMPROBANTES_DE_PAGO.getId()),new Long(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.CARTA_RENUNCIA.getId()),new Long(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.CUENTA_AFORE.getId()),new Long(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()));
		documentosProbatorios.put(new Long(DocumentoEnum.OTROS.getId()),new Long(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()));
		return documentosProbatorios;
	}
	
	private  Collection<Long> getTiposDocumentosObligatoriosVentanilla(){
		List<Long> tipoDocumentoObligatorio = new ArrayList<Long>();
		tipoDocumentoObligatorio.add(TipoDocumentoProbatorioEnum.ACTAS.getId());
		tipoDocumentoObligatorio.add(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
		return tipoDocumentoObligatorio;
	}
	
	private Collection<Long> getTipoDocumentosObligatoriosVentanillaMasJudicial(){
		List<Long> tipoDocumentoObligatorio = new ArrayList<Long>();
		tipoDocumentoObligatorio.addAll(this.getTiposDocumentosObligatoriosVentanilla());
		tipoDocumentoObligatorio.add(TipoDocumentoProbatorioEnum.DOCUMENTO_PROBATORIO_DEL_REPRESENTANTE_LEGAL.getId());
		return tipoDocumentoObligatorio;
	}
	
	private Map<Long, Long> listaDocumentosProbatoriosVentanilla(){
		Map<Long, Long> documentosProbatorios = new HashMap<Long,Long>();
		documentosProbatorios.putAll(this.listaDocumentosObligatoriosInternet());
		/**Formato de solicitud**/
		return documentosProbatorios;
	}
	
	private Map<Long, Long> listaDocumentosProbatoriosRepresentante(){
		Map<Long, Long> documentosProbatorios = new HashMap<Long, Long>();
		documentosProbatorios.putAll(this.listaDocumentosProbatoriosVentanilla());
		documentosProbatorios.put(new Long(DocumentoEnum.PODER_NOTARIAL.getId()), TipoDocumentoProbatorioEnum.DOCUMENTO_PROBATORIO_DEL_REPRESENTANTE_LEGAL.getId());
		return documentosProbatorios;
	}
	
	private Map<Long, Long> listaDocumentosProbatoriosDefuncion(){
		Map<Long, Long> documentosProbatorios = new HashMap<Long, Long>();
		documentosProbatorios.putAll(this.listaDocumentosProbatoriosVentanilla());
		documentosProbatorios.put(new Long(DocumentoEnum.ACTA_DEFUNCION.getId()), TipoDocumentoProbatorioEnum.ACTAS.getId());
		return documentosProbatorios;
	}
	
	private Map<Long, Long> listaDocumentosProbatoriosConyugue(){
		Map<Long, Long> documentosProbatorios = this.listaDocumentosProbatoriosDefuncion();
		documentosProbatorios.put(new Long(DocumentoEnum.ACTA_MATRIMONIO.getId()),TipoDocumentoProbatorioEnum.ACTAS.getId());
		return documentosProbatorios;
	}
	
	private Map<Long, Long> listaDocumentosProbatoriosConcubinato(){
		Map<Long, Long> documentosProbatorios = this.listaDocumentosProbatoriosDefuncion();
		documentosProbatorios.put(new Long(DocumentoEnum.CONSTANCIA_CONCUBINATO.getId()), TipoDocumentoProbatorioEnum.DOCUMENTO_PROBATORIO_DEL_REPRESENTANTE_LEGAL.getId());
		return documentosProbatorios;
	}
	
	private Collection<Long> compararListas (Collection<Long> idsDocsAdjuntos, Map<Long, Long> mapaTipoDocsObligatorios, Collection<Long> tiposObligatorios){
		log.debug("DOCS ADJUNTOS {}", idsDocsAdjuntos);		
		Collection<Long> tiposRequeridos = new ArrayList<Long>();
		for (Long idDoctoRequerido : idsDocsAdjuntos) {
			tiposRequeridos.add(mapaTipoDocsObligatorios.get(idDoctoRequerido));
		}
		log.debug("Tipos requeridos {}",  tiposRequeridos);
		log.debug("tipos obligatorios {}", tiposObligatorios);
		Collection<Long> tiposObligatoriosFaltantes = tiposObligatorios;
		tiposObligatoriosFaltantes.removeAll(tiposRequeridos);
		log.debug("Tipo requerido: ",tiposObligatoriosFaltantes);
		return tiposObligatoriosFaltantes;
	}
	
	public void validate(Object target, Errors errors, Integer tipoSolicitante) {
		validarDocumentosProbatorios((List<DocumentoProbatorio>)target, errors, tipoSolicitante);
	}

	
	
	

}
