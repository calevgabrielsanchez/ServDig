package mx.gob.imss.cit.cda.web.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoSolicitanteEnum;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoEnum;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class DocumentosProbatoriosValidator extends AbstractValidator {

    private static final Logger log = LoggerFactory
            .getLogger(DocumentosProbatoriosValidator.class);
    private static final String DOC_PROB_LIST = "documentoProbatorioList";
    private static final String FALTAN_OBLIGATORIOS = "field.documentoProbatorio.faltanObligatorios";
    
    private final int INT_ASEGURADO=0;
    private final int INT_CONYUGE=2;
    private final int INT_DESCENDIENTE=3;
    private final int INT_PADRES=4;
    private final int INT_CONCUBINO=5;
    private final int INT_REPRESENTANTE_LEGAL=6;
    private final int INT_DOCUMENTOS_NSS=7;
    
    public void validateDocumentosNSS(Object target, Errors errors,String labelReject) {
        validarDocumentosProbatorios((List<DocumentoProbatorio>) target,
                errors, INT_DOCUMENTOS_NSS, false,labelReject);
    }

    public void validate(Object target, Errors errors, boolean isDefuncion,String labelReject) {
        validarDocumentosProbatorios((List<DocumentoProbatorio>) target,
                errors, INT_ASEGURADO, isDefuncion,labelReject);
    }
    
    public void validateBeneficiario(Object target, Errors errors, String tipoSolicitante,
            String beneficiario,String labelReject) {
        validarDocumentosProbatorios((List<DocumentoProbatorio>) target,
                errors, getTipoValidacion(tipoSolicitante, beneficiario), false,labelReject);
    }

    private void validarDocumentosProbatorios(List<DocumentoProbatorio> list,
            Errors errors, Integer intTipoValidacion, boolean isDefuncion, String valueReject) {
        log.debug("--CDA-- ##### validando Documentos obligatorios #####");
        
        String labelReject= valueReject != null ? valueReject: DOC_PROB_LIST;
        
        Collection<Long> idDocumentos = CollectionUtils.collect(list,
                new BeanToPropertyValueTransformer("cveIdDocumento"));
        log.debug("ID DOCUMENTOS {}", idDocumentos);
        
        if (isDefuncion) {
            log.debug("es defuncion y contiene " + idDocumentos);
            if (!idDocumentos.contains(Long
                    .valueOf(DocumentoEnum.ACTA_CERTIFICADA_DEFUNCION.getId()))) {
                log.debug("No hay doc de defuncion");
                errors.rejectValue(labelReject, FALTAN_OBLIGATORIOS);
            }
        }
        if(intTipoValidacion!=null){
            validacion(intTipoValidacion, idDocumentos, labelReject, errors);
        }else{
            log.error("No se encontro pudo validar los documentos");
//            errors.rejectValue(labelReject, FALTAN_OBLIGATORIOS);
        }
        

    }
    
    private void validacion(Integer intTipoValidacion,Collection<Long> idDocumentos,String labelReject,Errors errors){
        Collection<Long> docsObligatoriosCollection;
        Collection<Long> tiposDocsFaltantes = null;
        switch (intTipoValidacion) {
        case INT_CONYUGE:
            log.debug("--CDA-- Docs conyuge");
            if (idDocumentos.contains(Long
                    .valueOf(DocumentoEnum.ACTA_MATRIMONIO.getId()))) {
                docsObligatoriosCollection = CollectionUtils.intersection(
                        idDocumentos, listaDocumentosProbatoriosInteresado()
                                .keySet());
                tiposDocsFaltantes = compararListas(docsObligatoriosCollection,
                        listaDocumentosProbatoriosInteresado(),
                        getTiposDocumentosObligatorioInteresado());
            } else {
                errors.rejectValue(labelReject, FALTAN_OBLIGATORIOS);
            }
            break;
        case INT_DESCENDIENTE:
            log.debug("--CDA-- HIJO ");
            docsObligatoriosCollection = CollectionUtils.intersection(
                    idDocumentos, listaDocumentosProbatoriosVentanilla()
                            .keySet());
            tiposDocsFaltantes = compararListas(docsObligatoriosCollection,
                    listaDocumentosProbatoriosVentanilla(),
                    getTiposDocumentosObligatoriosVentanilla());
            break;
        case INT_PADRES:
            docsObligatoriosCollection = CollectionUtils.intersection(
                    idDocumentos, listaDocumentosProbatoriosInteresado()
                            .keySet());
            tiposDocsFaltantes = compararListas(docsObligatoriosCollection,
                    listaDocumentosProbatoriosInteresado(),
                    getTiposDocumentosObligatorioInteresado());
            break;
        case INT_CONCUBINO:
            log.debug("--CDA-- CONCUBINO");
            log.debug("contine " + idDocumentos);
            if (idDocumentos.contains(Long
                    .valueOf(DocumentoEnum.CONSTANCIA_CONCUBINATO.getId()))) {
                docsObligatoriosCollection = CollectionUtils.intersection(
                        idDocumentos, listaDocumentosProbatoriosConcubinato()
                                .keySet());
                tiposDocsFaltantes = compararListas(docsObligatoriosCollection,
                        listaDocumentosProbatoriosConcubinato(),
                        getTipoDocumentosObligatoriosCocubino());
            } else {
                errors.rejectValue(labelReject, FALTAN_OBLIGATORIOS);
            }
            break;
        case INT_REPRESENTANTE_LEGAL:
            log.debug("--CDA-- REPRESENTANTES");

            docsObligatoriosCollection = CollectionUtils.intersection(
                    idDocumentos, listaDocumentosProbatoriosRepresentante()
                            .keySet());
            tiposDocsFaltantes = compararListas(docsObligatoriosCollection,
                    listaDocumentosProbatoriosRepresentante(),
                    getTipoDocumentosObligatoriosVentanillaMasJudicial());
            if (tiposDocsFaltantes.isEmpty()) {
                docsObligatoriosCollection = CollectionUtils.intersection(
                        idDocumentos, listaDocumentosProbatoriosInteresado()
                                .keySet());
                tiposDocsFaltantes = compararListas(docsObligatoriosCollection,
                        listaDocumentosProbatoriosInteresado(),
                        getTiposDocumentosObligatorioInteresado());
            } else {
                errors.rejectValue(labelReject, FALTAN_OBLIGATORIOS);
            }
            break;
        case INT_DOCUMENTOS_NSS:
            log.debug("--CDA-- nss");
            docsObligatoriosCollection = CollectionUtils.intersection(
                    idDocumentos, listaDocumentosObligatoriosNSSInternet().keySet());
            
            tiposDocsFaltantes = compararListas(docsObligatoriosCollection,
                    listaDocumentosObligatoriosNSSInternet(),
                    getTiposDocumentosObligatoriosNSS());
           
            break;
        default:
            docsObligatoriosCollection = CollectionUtils.intersection(
                    idDocumentos, listaDocumentosProbatoriosVentanilla()
                            .keySet());
            tiposDocsFaltantes = compararListas(docsObligatoriosCollection,
                    listaDocumentosProbatoriosVentanilla(),
                    getTiposDocumentosObligatoriosVentanilla());
        }
        log.debug("tiposDocsFaltantes " + tiposDocsFaltantes);
        if (tiposDocsFaltantes != null && !tiposDocsFaltantes.isEmpty()) {
            errors.rejectValue(labelReject, FALTAN_OBLIGATORIOS);
        }
    }

    private Map<Long, Long> listaDocumentosObligatoriosInternet() {
        Map<Long, Long> documentosProbatorios = new HashMap<Long, Long>();
        /** Acta **/
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.ACTA_NACIMIENTO.getId()),
                TipoDocumentoProbatorioEnum.ACTAS.getId());
        /** Identificacion Oficial **/
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.CREDENCIAL_ELECTOR.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.PASAPORTE.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.CARTILLA_MILITAR.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.CEDULA_PROFESIONAL.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.MATRICULA_CONSULAR.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.IDENTIDAD_EXTRANJEROS.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.DOCUMENTO_MIGRATORIO.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(Long.valueOf(DocumentoEnum.CURP.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        return documentosProbatorios;
    }

    private Map<Long, Long> listaDocumentosObligatorioInteresado() {
        Map<Long, Long> documentosProbatorios = new HashMap<Long, Long>();

        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.CREDENCIAL_ELECTOR.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.PASAPORTE.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.CARTILLA_MILITAR.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.CEDULA_PROFESIONAL.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.MATRICULA_CONSULAR.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.IDENTIDAD_EXTRANJEROS.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.DOCUMENTO_MIGRATORIO.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        documentosProbatorios.put(Long.valueOf(DocumentoEnum.CURP.getId()),
                TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
        return documentosProbatorios;
    }

    private Collection<Long> getTiposDocumentosObligatoriosVentanilla() {
        List<Long> tipoDocumentoObligatorio = new ArrayList<Long>();
        tipoDocumentoObligatorio.add(TipoDocumentoProbatorioEnum.ACTAS.getId());
        tipoDocumentoObligatorio.add(TipoDocumentoProbatorioEnum.IDENTIFICACION
                .getId());
        return tipoDocumentoObligatorio;
    }

    private Collection<Long> getTiposDocumentosObligatorioInteresado() {
        List<Long> tipoDocumentoObligatorio = new ArrayList<Long>();
        tipoDocumentoObligatorio.add(TipoDocumentoProbatorioEnum.IDENTIFICACION
                .getId());
        return tipoDocumentoObligatorio;
    }

    private Collection<Long> getTipoDocumentosObligatoriosVentanillaMasJudicial() {
        List<Long> tipoDocumentoObligatorio = new ArrayList<Long>();
        tipoDocumentoObligatorio
                .add(TipoDocumentoProbatorioEnum.DOCUMENTO_PROBATORIO_DEL_REPRESENTANTE_LEGAL
                        .getId());
        return tipoDocumentoObligatorio;
    }

    private Collection<Long> getTipoDocumentosObligatoriosCocubino() {
        List<Long> tipoDocumentoObligatorio = new ArrayList<Long>();
        tipoDocumentoObligatorio
                .add(TipoDocumentoProbatorioEnum.DOCUMENTO_PROBATORIO_JUDICIAL
                        .getId());
        return tipoDocumentoObligatorio;
    }

    private Map<Long, Long> listaDocumentosProbatoriosVentanilla() {
        Map<Long, Long> documentosProbatorios = new HashMap<Long, Long>();
        documentosProbatorios
                .putAll(this.listaDocumentosObligatoriosInternet());
        /** Formato de solicitud **/
        return documentosProbatorios;
    }

    private Map<Long, Long> listaDocumentosProbatoriosInteresado() {
        Map<Long, Long> documentosProbatorios = new HashMap<Long, Long>();
        documentosProbatorios.putAll(this
                .listaDocumentosObligatorioInteresado());
        return documentosProbatorios;
    }

    private Map<Long, Long> listaDocumentosProbatoriosRepresentante() {
        Map<Long, Long> documentosProbatorios = new HashMap<Long, Long>();
        documentosProbatorios
                .put(Long.valueOf(DocumentoEnum.PODER_NOTARIAL.getId()),
                        TipoDocumentoProbatorioEnum.DOCUMENTO_PROBATORIO_DEL_REPRESENTANTE_LEGAL
                                .getId());
        return documentosProbatorios;
    }

    private Map<Long, Long> listaDocumentosProbatoriosConcubinato() {
        Map<Long, Long> documentosProbatorios = new HashMap<Long, Long>();
        documentosProbatorios.put(Long
                .valueOf(DocumentoEnum.CONSTANCIA_CONCUBINATO.getId()),
                TipoDocumentoProbatorioEnum.DOCUMENTO_PROBATORIO_JUDICIAL
                        .getId());
        return documentosProbatorios;
    }

    private Collection<Long> compararListas(Collection<Long> idsDocsAdjuntos,
            Map<Long, Long> mapaTipoDocsObligatorios,
            Collection<Long> tiposObligatorios) {
        log.debug("DOCS ADJUNTOS {}", idsDocsAdjuntos);
        Collection<Long> tiposRequeridos = new ArrayList<Long>();
        for (Long idDoctoRequerido : idsDocsAdjuntos) {
            tiposRequeridos.add(mapaTipoDocsObligatorios.get(idDoctoRequerido));
        }
        log.debug("Tipos requeridos {}", tiposRequeridos);
        log.debug("tipos obligatorios {}", tiposObligatorios);
        Collection<Long> tiposObligatoriosFaltantes = tiposObligatorios;
        tiposObligatoriosFaltantes.removeAll(tiposRequeridos);
        log.debug("Tipo requerido: ", tiposObligatoriosFaltantes);
        return tiposObligatoriosFaltantes;
    }

    private Map<Long, Long> listaDocumentosObligatoriosNSSInternet() {
        Map<Long, Long> documentosProbatorios = new HashMap<Long, Long>();

        /** Documentos con NSS **/
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.AVISOS_AFILIATORIOS.getId()),
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.TARJETA_AFILIACION.getId()),
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.CERTIFICADO_DE_INCAPACIDAD.getId()),
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.CITAS_MEDICAS.getId()),
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.CREDENCIAL_ADIMSS.getId()),
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.LIQUIDACIONES_PAGADAS.getId()),
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.COMPROBANTES_DE_PAGO.getId()),
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.CARTA_RENUNCIA.getId()),
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
        documentosProbatorios.put(
                Long.valueOf(DocumentoEnum.CUENTA_AFORE.getId()),
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
        documentosProbatorios.put(Long.valueOf(DocumentoEnum.OTROS.getId()),
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
        return documentosProbatorios;
    }

    private void validarDocumentosProbNSS(List<DocumentoProbatorio> list,
            Errors errors) {
        log.debug("--CDA-- ##### Validando Documentos obligatorios de NSS#####");
        Collection<Long> documentos = CollectionUtils.collect(list,
                new BeanToPropertyValueTransformer("cveIdDocumento"));
        @SuppressWarnings("unchecked")
        Collection<Long> documentosObligatorios = CollectionUtils.intersection(
                documentos, listaDocumentosObligatoriosNSSInternet().keySet());
        log.debug("Documentos obligatorios de NSS {}" + documentosObligatorios);
        if (documentosObligatorios == null || documentosObligatorios.isEmpty()) {
            errors.rejectValue(DOC_PROB_LIST,
                    "field.documentoProbatorio.listaVacia");
        } else {
            @SuppressWarnings("unchecked")
            Collection<Long> tiposRequeridos = new ArrayList<Long>();
            for (Long idDoctoRequerido : documentosObligatorios) {
                tiposRequeridos.add(listaDocumentosObligatoriosNSSInternet()
                        .get(idDoctoRequerido));
            }
            log.debug("Tipos requeridos {}", tiposRequeridos);
            Collection<Long> requeridos = getTiposDocumentosObligatoriosNSS();
            requeridos.removeAll(tiposRequeridos);
            log.debug("Tipo requerido: ", requeridos);
            if (!requeridos.isEmpty()) {
                errors.rejectValue(DOC_PROB_LIST,
                        "field.documentoProbatorio.listaVacia");
            }
        }
    }

   
    private Collection<Long> getTiposDocumentosObligatoriosNSS() {
        List<Long> tipoDocumentoObligatorio = new ArrayList<Long>();
        tipoDocumentoObligatorio
                .add(TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
        return tipoDocumentoObligatorio;
    }
    
    private Integer getTipoValidacion(String tipoSolicitante, String tipoBeneficiario){
        Integer tipoValidacion=null;
        if(tipoSolicitante!=null && tipoBeneficiario!=null){
            if(TipoSolicitanteEnum.BENEFICIARIO.getDescripcion().equals(tipoSolicitante)){
                TipoSolicitanteEnum enum1=TipoSolicitanteEnum.getTipoSolicitudEnumByDescripcion(tipoBeneficiario);
                tipoValidacion=enum1.getId();
            }else if(TipoSolicitanteEnum.REPRESENTANTE_LEGAL.getDescripcion().equals(tipoSolicitante)){
                TipoSolicitanteEnum enum1=TipoSolicitanteEnum.getTipoSolicitudEnumByDescripcion(tipoSolicitante);
                tipoValidacion=enum1.getId();
            }
        }
        return tipoValidacion;
    }

}
