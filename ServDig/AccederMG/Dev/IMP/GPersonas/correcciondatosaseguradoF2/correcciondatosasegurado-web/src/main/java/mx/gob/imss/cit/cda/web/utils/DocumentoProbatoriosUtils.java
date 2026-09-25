package mx.gob.imss.cit.cda.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.web.enums.TipoSolicitanteEnum;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DocumentoProbatoriosUtils {

    @Autowired
    @Qualifier("documentoProbatorioServiceBusiness")
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;

    private final Logger log = LoggerFactory
            .getLogger(DocumentoProbatoriosUtils.class);

    private final int DOCUMENTOS_NSS = 2;
    private final int DOCUMENTOS_ASEGURADO = 1;
    private final int DOCUMENTOS_BENEFICIARIO = 3;

    private final int INTERESADO_REPRESENTANTE_LEGAL = 6;
    private final int INTERESADO_CONCUBINO = 5;
    private final int INTERESADO_CONYUGE = 2;
    private final int INTERESADO_DESCENDIENTE = 3;

    public Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentProbNss() {
        return getDocumentProbByTipo(DOCUMENTOS_NSS, false, "", "");
    }

    public Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentProbAsegurado(
            boolean defuncion) {
        return getDocumentProbByTipo(DOCUMENTOS_ASEGURADO, defuncion, "", "");
    }

    public Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentProbBeneficiario(
            String tipoSolicitate, String tipoBeneficiario) {
        return getDocumentProbByTipo(DOCUMENTOS_BENEFICIARIO, false,
                tipoSolicitate, tipoBeneficiario);
    }
    
    private void getDocumentProbByTipoStep01(int tipoDocumentos,
      DoctoReqTramite doctoReqTramite, String tipoBeneficiario, String tipoSolicitate, boolean defuncion,
      Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos, TipoDocumentoProbatorio currMapKey ){
      DocumentoProbatorio docProbatorioVo = null;
            switch (tipoDocumentos) {
            case DOCUMENTOS_ASEGURADO:
                docProbatorioVo = getTiposDocumentosAsegurado(doctoReqTramite, defuncion);
                break;
            case DOCUMENTOS_NSS:
                docProbatorioVo = getTiposDocumentosNss(doctoReqTramite);
                break;
            case DOCUMENTOS_BENEFICIARIO:
            	if(doctoReqTramite.getDocumentoPorTipo().getDocumento().getCveIdDocumento()
            			!= DocumentoEnum.ACTA_NACIMIENTO.getId() ||
            					tipoBeneficiario.equals(TipoSolicitanteEnum.DESCENDIENTE
            	                        .getDescripcion()) ){
                docProbatorioVo = getTiposDocumentosBeneficiario(
                        doctoReqTramite, tipoSolicitate, tipoBeneficiario);
            	}
                break;
            default:
                break;
            }
            if (docProbatorioVo != null) {
                doctos.get(currMapKey).add(docProbatorioVo);
            }
    
    }
    
    private void getDocumentProbByTipoStep02(Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos){
      Iterator<TipoDocumentoProbatorio> iterator = doctos.keySet().iterator();
        while (iterator.hasNext()) {
            TipoDocumentoProbatorio tipo = iterator.next();
            if (doctos.get(tipo).isEmpty()) {
                iterator.remove();
            }
        }
    }

    private Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentProbByTipo(
            int tipoDocumentos, boolean defuncion, String tipoSolicitate,
            String tipoBeneficiario) {
        List<DoctoReqTramite> documentos = getDoctoReqTramite(TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO
                .getCodigo().longValue());
        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos = new HashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>();
        for (DoctoReqTramite doctoReqTramite : documentos) {
            Integer tipoDocto = doctoReqTramite.getDocumentoPorTipo()
                    .getTipoDocumentoProbatorio()
                    .getIdTipoDocumentoProbatorio();
            TipoDocumentoProbatorio currMapKey = null;
            if (doctos.keySet() != null && !doctos.keySet().isEmpty()) {
                for (TipoDocumentoProbatorio key : doctos.keySet()) {
                    if (tipoDocto.equals(key.getIdTipoDocumentoProbatorio())) {
                        currMapKey = key;
                        log.debug("--CDA-- se encontro la llave: {}",
                                key.getIdTipoDocumentoProbatorio());
                    }
                }
            }

            if (currMapKey == null) {
                currMapKey = doctoReqTramite.getDocumentoPorTipo()
                        .getTipoDocumentoProbatorio();
                doctos.put(currMapKey, new ArrayList<DocumentoProbatorio>());
                log.debug("--CDA-- creando llave para tipo: {}",
                        currMapKey.getIdTipoDocumentoProbatorio());
            }
            getDocumentProbByTipoStep01(tipoDocumentos, doctoReqTramite, tipoBeneficiario,
                    tipoSolicitate, defuncion, doctos, currMapKey);
        }

        getDocumentProbByTipoStep02(doctos);
        return doctos;
    }

    private DocumentoProbatorio getTiposDocumentosNss(
            DoctoReqTramite doctoReqTramite) {
        int tipoDocumento = doctoReqTramite.getDocumentoPorTipo()
                .getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio()
                .intValue();
        DocumentoProbatorio documento = null;
        if (tipoDocumento == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS
                .getId()) {
            documento = convertDocumtProVO(doctoReqTramite);
        }
        return documento;
    }

    private DocumentoProbatorio getTiposDocumentosAsegurado(
            DoctoReqTramite doctoReqTramite, boolean defuncion) {
        int cveIdDocumento = doctoReqTramite.getDocumentoPorTipo()
                .getDocumento().getCveIdDocumento().intValue();
        int tipoDocumento = doctoReqTramite.getDocumentoPorTipo()
                .getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio()
                .intValue();
        DocumentoProbatorio documento = null;
        if (cveIdDocumento == DocumentoEnum.ACTA_NACIMIENTO.getId()
                || (tipoDocumento == TipoDocumentoProbatorioEnum.IDENTIFICACION
                        .getId() && cveIdDocumento != DocumentoEnum.CURP
                        .getId())) {
            documento = convertDocumtProVO(doctoReqTramite);
        }
        if (defuncion
                && cveIdDocumento == DocumentoEnum.ACTA_CERTIFICADA_DEFUNCION
                        .getId()) {
            documento = convertDocumtProVO(doctoReqTramite);
        }
        return documento;
    }

    private Integer getTiposDocumentosBeneficiarioStep01(
      String tipoSolicitante,
            String tipoBeneficiario ){
      Integer interesado =tipoSolicitante.equals(TipoSolicitanteEnum.REPRESENTANTE_LEGAL
                    .getDescripcion())? INTERESADO_REPRESENTANTE_LEGAL:0;
            if (tipoBeneficiario != null) {
                if (tipoBeneficiario.equals(TipoSolicitanteEnum.CONCUBINO
                        .getDescripcion())) {
                    interesado = INTERESADO_CONCUBINO;
                }
                if (tipoBeneficiario.equals(TipoSolicitanteEnum.CONYUGE
                        .getDescripcion())) {
                    interesado = INTERESADO_CONYUGE;
                }
                if (tipoBeneficiario.equals(TipoSolicitanteEnum.DESCENDIENTE
                        .getDescripcion())) {
                    interesado = INTERESADO_DESCENDIENTE;
                }
            }
            return interesado;
    }
    
    private DocumentoProbatorio getTiposDocumentosBeneficiarioStep02(Integer interesado,
      int cveIdDocumento, DoctoReqTramite doctoReqTramite ){
      DocumentoProbatorio documento = null;
      
      switch (interesado) {
            case INTERESADO_CONYUGE:
                if (cveIdDocumento == DocumentoEnum.ACTA_MATRIMONIO.getId()) {
                    log.debug("--CDA-- se agrega ACTA DE MATRIMONIO EN BENE: {}",
                            cveIdDocumento);
                    documento = convertDocumtProVO(doctoReqTramite);
                }
                break;
            case INTERESADO_DESCENDIENTE:
                if (cveIdDocumento == DocumentoEnum.ACTA_NACIMIENTO.getId()) {
                    log.debug("--CDA-- se agrega documento hijo: {}",
                            cveIdDocumento);
                    documento = convertDocumtProVO(doctoReqTramite);
                }
                break;
            
            default:
            }
      
      return documento;
    }
    private DocumentoProbatorio getTiposDocumentosBeneficiarioStep03(Integer interesado,
      int cveIdDocumento, DoctoReqTramite doctoReqTramite ){
      DocumentoProbatorio documento = null;
      
      switch (interesado) {
            case INTERESADO_CONCUBINO:
                if (cveIdDocumento == DocumentoEnum.CONSTANCIA_CONCUBINATO.getId()) {
                    log.debug("--CDA-- se agrega documento concubino: {}",
                            cveIdDocumento);
                    documento = convertDocumtProVO(doctoReqTramite);
                }
                break;
            case INTERESADO_REPRESENTANTE_LEGAL:
                if (cveIdDocumento == DocumentoEnum.PODER_NOTARIAL.getId()) {
                    log.debug("--CDA-- se agrega documento representante: {}",
                            cveIdDocumento);
                    documento = convertDocumtProVO(doctoReqTramite);
                }
                break;
            default:
            }
      
      return documento;
    }
    
    private DocumentoProbatorio getTiposDocumentosBeneficiario(
            DoctoReqTramite doctoReqTramite, String tipoSolicitante,
            String tipoBeneficiario) {
        int cveIdDocumento = doctoReqTramite.getDocumentoPorTipo()
                .getDocumento().getCveIdDocumento().intValue();
        DocumentoProbatorio documento = null;
        Integer interesado = 0;
        if(tipoSolicitante!=null){
            interesado = getTiposDocumentosBeneficiarioStep01(tipoSolicitante,
                    tipoBeneficiario);
            
            documento = getTiposDocumentosBeneficiarioStep02(interesado, cveIdDocumento,
                    doctoReqTramite);
            if( documento == null ){
              documento = getTiposDocumentosBeneficiarioStep03(interesado, cveIdDocumento,
                    doctoReqTramite);
            }
            
        }
        return documento;
    }

    private List<DoctoReqTramite> getDoctoReqTramite(long idTramite) {
        return documentoProbatorioServiceBusiness
                .getDocumentosRequeridosPorTipoTramite(idTramite);
    }

    private DocumentoProbatorio convertDocumtProVO(
            DoctoReqTramite doctoReqTramite) {
        DocumentoProbatorio docProbatorioVo = new DocumentoProbatorio();
        docProbatorioVo.setTipoDocumento(doctoReqTramite.getDocumentoPorTipo()
                .getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
        docProbatorioVo.setCveIdDocumento(doctoReqTramite.getDocumentoPorTipo()
                .getDocumento().getCveIdDocumento());
        docProbatorioVo.setDesDocumento(doctoReqTramite.getDocumentoPorTipo()
                .getDocumento().getDesDocumento());
        docProbatorioVo.setIdDocumentoPorTipo(doctoReqTramite
                .getDocumentoPorTipo().getIdDocumentoPorTipo().longValue());
        return docProbatorioVo;
    }
}
