package mx.gob.imss.cit.cda.web.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoEnum;

@Component
public class DocumentosProbatoriosValidator extends AbstractValidator implements
Validator{
	
	private static final Logger log = LoggerFactory.getLogger(DocumentosProbatoriosValidator.class);
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return List.class.equals(clazz);
	}

	private void validarDocumentosProbatorios(List<DocumentoProbatorio> list, Errors errors) {		
            log.debug("--CDA-- ##### validando Documentos obligatorios #####");

            Collection<Long> documentos = CollectionUtils.collect(list, new BeanToPropertyValueTransformer("cveIdDocumento"));
            @SuppressWarnings("unchecked")
            Collection<Long> documentosObligatorios = CollectionUtils.intersection(documentos, listaDocumentosObligatoriosInternetAsegurado().keySet());
            log.debug("Documentos obligatorios {}" + documentosObligatorios);
            if(documentosObligatorios == null || documentosObligatorios.isEmpty()){
                    errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.listaVacia");
            }else{
                    //Tipos de Documentos
                    @SuppressWarnings("unchecked")
                    Collection<Long> tiposRequeridos = new ArrayList<Long>();
                    for (Long idDoctoRequerido : documentosObligatorios) {
                            tiposRequeridos.add(listaDocumentosObligatoriosInternetAsegurado().get(idDoctoRequerido));
                    }
                    log.debug("Tipos requeridos {}",  tiposRequeridos);
                    Collection<Long> requeridos = tiposDocumentosObligatoriosInternet();
                                    requeridos.removeAll(tiposRequeridos);
                                    log.debug("Tipo requerido: ",requeridos);
                    if(!requeridos.isEmpty()){
                            errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.listaVacia");
                    }
            }	
	}
	
	private  Collection<Long> tiposDocumentosObligatoriosInternet(){
		List<Long> tipoDocumentoObligatorio = new ArrayList<Long>();
		tipoDocumentoObligatorio.add(TipoDocumentoProbatorioEnum.ACTAS.getId());
		tipoDocumentoObligatorio.add(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
		return tipoDocumentoObligatorio;
	}
	
	private Map<Long, Long> listaDocumentosObligatoriosInternetAsegurado(){
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
		
		return documentosProbatorios;
	}
	
	private  Map<Long, String> getTiposDocumentosObligatoriosVentanilla(){
		Map<Long, String> tipoDocumentoObligatorio = new HashMap<Long,String>();
		tipoDocumentoObligatorio.put(TipoDocumentoProbatorioEnum.ACTAS.getId(), TipoDocumentoProbatorioEnum.ACTAS.toString());
		tipoDocumentoObligatorio.put(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId(), TipoDocumentoProbatorioEnum.IDENTIFICACION.toString());
		tipoDocumentoObligatorio.put(TipoDocumentoProbatorioEnum.COMPROBANTES_DE_DOMICILIO.getId(), TipoDocumentoProbatorioEnum.IDENTIFICACION.toString());
		return tipoDocumentoObligatorio;
	}
        
        private Map<Long, Long> listaDocumentosObligatoriosNSSInternet(){
		Map<Long, Long> documentosProbatorios = new HashMap<Long, Long>();
		
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
	

        @Override
	public void validate(Object target, Errors errors) {
		validarDocumentosProbatorios((List<DocumentoProbatorio>)target, errors);
	}

	public void validateDocumentosNSS(Object target, Errors errors) {
		validarDocumentosProbNSS((List<DocumentoProbatorio>)target, errors);
	}
        
        private  Collection<Long> tiposDocumentosObligatoriosNSSInternet(){
		List<Long> tipoDocumentoObligatorio = new ArrayList<Long>();
		tipoDocumentoObligatorio.add(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
		return tipoDocumentoObligatorio;
	}
	
	private void validarDocumentosProbNSS(List<DocumentoProbatorio> list, Errors errors) {		
            log.debug("--CDA-- ##### Validando Documentos obligatorios de NSS#####");
            Collection<Long> documentos = CollectionUtils.collect(list, new BeanToPropertyValueTransformer("cveIdDocumento"));
            @SuppressWarnings("unchecked")
            Collection<Long> documentosObligatorios = CollectionUtils.intersection(documentos, listaDocumentosObligatoriosNSSInternet().keySet());
            log.debug("Documentos obligatorios de NSS {}" + documentosObligatorios);
            if(documentosObligatorios == null || documentosObligatorios.isEmpty()){
                    errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.listaVacia");
            }else{
                //Tipos de Documentos
                @SuppressWarnings("unchecked")
                Collection<Long> tiposRequeridos = new ArrayList<Long>();
                for (Long idDoctoRequerido : documentosObligatorios) {
                        tiposRequeridos.add(listaDocumentosObligatoriosNSSInternet().get(idDoctoRequerido));
                }
                log.debug("Tipos requeridos {}",  tiposRequeridos);
                Collection<Long> requeridos = tiposDocumentosObligatoriosNSSInternet();
                requeridos.removeAll(tiposRequeridos);
                log.debug("Tipo requerido: ",requeridos);
                if(!requeridos.isEmpty()){
                        errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.listaVacia");
                }
            }	
	}

}
