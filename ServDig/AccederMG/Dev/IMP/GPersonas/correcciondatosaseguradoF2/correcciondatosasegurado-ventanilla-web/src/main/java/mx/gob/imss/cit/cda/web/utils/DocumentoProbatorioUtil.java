/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoSolicitanteEnum;
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
public class DocumentoProbatorioUtil {

    @Autowired
    @Qualifier("documentoProbatorioServiceBusiness")
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;

    private final Logger log = LoggerFactory
            .getLogger(DocumentoProbatorioUtil.class);

    private final int DOCUMENTOS_NSS = 2;
    private final int DOCUMENTOS_ASEGURADO = 1;
    private final int DOCUMENTOS_BENEFICIARIO = 3;

    private final int INTERESADO_REPRESENTANTE_LEGAL = 6;
    private final int INTERESADO_CONCUBINO = 5;
    private final int INTERESADO_CONYUGE = 2;
    private final int INTERESADO_DESCENDIENTE = 3;

    /**
     * Obtiene los documentos probatorios pertenecientes al nss que incluye
     * tipos de documentos nss.
     * 
     * @return
     */
    public Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentProbNss() {
        return getDocumentProbByTipo(DOCUMENTOS_NSS, false, "", "");
    }

    /**
     * Obtiene los documentos probatorios pertenecientes al asegurado que
     * incluye tipos de documentos acta(si es defuncion se agrega la acta de
     * defuncion) y identificacion.
     * 
     * @param defuncion
     *            Bandera que determina si es defuncion o no true:se agrega acta
     *            defuncion false: sin acta de defuncion
     * @return
     */
    public Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentProbAsegurado(
            boolean defuncion) {
        return getDocumentProbByTipo(DOCUMENTOS_ASEGURADO, defuncion, "", "");
    }

    /**
     * Obtiene los documentos probatorios pertenecientes a los beneficiario que
     * incluye tipos de documentos acta y identificación, dependiendo del tipo
     * de beneficiario se omitiran/agregaran documentos de acta.
     * 
     * @param tipoSolicitate
     *            Son ASEGURADO, REPRESENTANTE_LEGAL y BENEFICIARIO
     * @param tipoBeneficiario
     *            Son cuatro tipos CONYUGE,DESCENDIENTES, PADRES Y CONCUBINO
     * @return
     */
    public Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentProbBeneficiario(
            String tipoSolicitate, String tipoBeneficiario) {
        return getDocumentProbByTipo(DOCUMENTOS_BENEFICIARIO, false,
                tipoSolicitate, tipoBeneficiario);
    }

    /**
     * Metodo que obtiene infomacion de BD y construye los catalogos de
     * documento probatorio por tipo de documento y/o casos especificos
     * 
     * @param tipoDocumentos
     *            Numeracion que va a determinar que tipo de documentos se
     *            obtentra
     * @param defuncion
     *            Determinan si se aregara acta de de defuncion alos documentos
     * @param tipoSolicitate
     *            Determina que tipos de documentos debe cargar (Beneficiario,
     *            representante o asegurado)
     * @param tipoBeneficiario
     *            Determina si se agregaran/omitiran algunos documentos de tipo
     *            Acta
     * @return
     */
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
            DocumentoProbatorio docProbatorioVo = null;
            switch (tipoDocumentos) {
            case DOCUMENTOS_ASEGURADO:
                docProbatorioVo = getTiposDocumentosAsegurado(doctoReqTramite,
                        defuncion);
                break;
            case DOCUMENTOS_NSS:
                docProbatorioVo = getTiposDocumentosNss(doctoReqTramite);
                break;
            case DOCUMENTOS_BENEFICIARIO:
            	if(doctoReqTramite.getDocumentoPorTipo().getDocumento().getCveIdDocumento()
            			!= DocumentoEnum.ACTA_NACIMIENTO.getId() ||
            					(tipoBeneficiario !=null && tipoBeneficiario.equals(TipoSolicitanteEnum.DESCENDIENTE
            	                        .getDescripcion())) ){
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

        Iterator<TipoDocumentoProbatorio> iterator = doctos.keySet().iterator();
        while (iterator.hasNext()) {
            TipoDocumentoProbatorio tipo = iterator.next();
            if (doctos.get(tipo).isEmpty()) {
                iterator.remove();
            }
        }
        return doctos;
    }

    /**
     * Obtiene los documentos necesarios para nss
     * 
     * @param doctoReqTramite
     * @return
     */
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

    /**
     * Obtiene los documentos necesarios para el asegurado
     * 
     * @param doctoReqTramite
     * @param defuncion
     * @return
     */
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

    /**
     * Obtiene los tipos de documentos de beneficiario o representante legal y
     * su tipo de beneficiario
     * 
     * @param doctoReqTramite
     * @param tipoSolicitante
     * @param tipoBeneficiario
     * @return
     */
    private DocumentoProbatorio getTiposDocumentosBeneficiario(
            DoctoReqTramite doctoReqTramite, String tipoSolicitante,
            String tipoBeneficiario) {
        int cveIdDocumento = doctoReqTramite.getDocumentoPorTipo()
                .getDocumento().getCveIdDocumento().intValue();
        int tipoDocumento = doctoReqTramite.getDocumentoPorTipo()
                .getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio()
                .intValue();
        DocumentoProbatorio documento = null;
        Integer interesado = 0;
        if (cveIdDocumento == DocumentoEnum.ACTA_NACIMIENTO.getId()
                || (tipoDocumento == TipoDocumentoProbatorioEnum.IDENTIFICACION
                        .getId() && cveIdDocumento != DocumentoEnum.CURP
                        .getId())) {
            documento = convertDocumtProVO(doctoReqTramite);
        }
        if (tipoSolicitante != null) {
            interesado = tipoSolicitante
                    .equals(TipoSolicitanteEnum.REPRESENTANTE_LEGAL
                            .getDescripcion()) ? INTERESADO_REPRESENTANTE_LEGAL
                    : 0;
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
            switch (interesado) {
            case INTERESADO_CONYUGE:
                if (cveIdDocumento == DocumentoEnum.ACTA_MATRIMONIO.getId()) {
                    log.debug(
                            "--CDA-- se agrega ACTA DE MATRIMONIO EN BENE: {}",
                            cveIdDocumento);
                    documento = convertDocumtProVO(doctoReqTramite);
                }
                break;
            case INTERESADO_CONCUBINO:
                if (cveIdDocumento == DocumentoEnum.CONSTANCIA_CONCUBINATO
                        .getId()) {
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
        }
        return documento;
    }

    /**
     * Obtiene los objetos que seran transfromados en catalogos de documentos
     * probatorios
     * 
     * @param idTramite
     *            Identificador en bd del tramite
     * @return
     */
    private List<DoctoReqTramite> getDoctoReqTramite(long idTramite) {
        return documentoProbatorioServiceBusiness
                .getDocumentosRequeridosPorTipoTramite(idTramite);
    }

    /**
     * Convierte un objeto de del modelo en objeto de vista para ser mostrado y
     * manipulado el a vista en catalogos
     * 
     * @param doctoReqTramite
     * @return
     */
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
