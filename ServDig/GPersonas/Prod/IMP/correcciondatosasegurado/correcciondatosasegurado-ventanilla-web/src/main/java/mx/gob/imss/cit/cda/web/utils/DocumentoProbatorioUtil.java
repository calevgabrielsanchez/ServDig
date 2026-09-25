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
import javax.servlet.http.HttpSession;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoEnum;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;


@Component
public class DocumentoProbatorioUtil {
    
    @Autowired
    @Qualifier("documentoProbatorioServiceBusiness")
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;
    
    
    private final Logger log = LoggerFactory.getLogger(DocumentoProbatorioUtil.class);
    
    private static final String REPRESENTANTE_LEGAL="REPRESENTANTE_LEGAL";
    private static final String CONYUGUE="CONYUGUE";
    private static final String CONCUBINO="CONCUBINO";
    private static final String DESCENDIENTE="DESCENDIENTE";
    
    public Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentProbNss(){
        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos=getDocumentProb();
        Iterator<TipoDocumentoProbatorio> iterator =doctos.keySet().iterator();
        while(iterator.hasNext()){
            TipoDocumentoProbatorio tipo=iterator.next();
              if (doctos.get(tipo).isEmpty() || tipo.getIdTipoDocumentoProbatorio()!=11) {
                    iterator.remove();
                }  
        }
        return doctos;
    }
    
    public Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentProbAsegurado(){
		Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos=getDocumentProb();
		Iterator<TipoDocumentoProbatorio> iterator =doctos.keySet().iterator();
            while(iterator.hasNext()){
                TipoDocumentoProbatorio tipo=iterator.next();
                  if (doctos.get(tipo).isEmpty() || tipo.getIdTipoDocumentoProbatorio()==11) {
                        iterator.remove();
                    }  
            }
        return doctos;
    }
    
    private Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> getDocumentProb(){
        List<DoctoReqTramite> documentos = getDoctoReqTramite(TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo().longValue());
        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos = new HashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>();
		for (DoctoReqTramite doctoReqTramite : documentos) {
			Integer tipoDocto = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio();
			TipoDocumentoProbatorio currMapKey = null;
			if(doctos.keySet() != null && !doctos.keySet().isEmpty()){
				for (TipoDocumentoProbatorio key : doctos.keySet()) {
					if(tipoDocto.equals(key.getIdTipoDocumentoProbatorio())){
						currMapKey = key;
						log.debug("--CDA-- se encontro la llave: {}",key.getIdTipoDocumentoProbatorio());
					}
				}
			}
				
			if(currMapKey == null){
				currMapKey = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio();
				doctos.put(currMapKey, new ArrayList<DocumentoProbatorio>());
				log.debug("--CDA-- creando llave para tipo: {}",currMapKey.getIdTipoDocumentoProbatorio());
			}
			DocumentoProbatorio docProbatorioVo = new DocumentoProbatorio();
			docProbatorioVo.setTipoDocumento(doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
			log.debug("--CDA-- TIPO DE DOCUMENTO: {}", docProbatorioVo.getTipoDocumento().toString());
			docProbatorioVo.setCveIdDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getCveIdDocumento());
			docProbatorioVo.setDesDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getDesDocumento());
			docProbatorioVo.setIdDocumentoPorTipo(doctoReqTramite.getDocumentoPorTipo().getIdDocumentoPorTipo().longValue());
			log.debug("--CDA-- ID DE DOCUMENTO PROBATORIO POR TIPO: {}", docProbatorioVo.getIdDocumentoPorTipo());
			if(docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_NACIMIENTO.getId() 
					|| (docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()
							&& docProbatorioVo.getCveIdDocumento().intValue() != DocumentoEnum.CURP.getId())
					|| docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()){
				log.debug("--CDA-- se agrega documento: {}, {}",docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
				doctos.get(currMapKey).add(docProbatorioVo);
			}
		}
        return doctos;
    }
    
    
    private TipoDocumentoProbatorio getTipoTramite(Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos,TipoDocumentoProbatorio tipoDocto){
        TipoDocumentoProbatorio currMapKey = null;  
        if (doctos.keySet() != null && !doctos.keySet().isEmpty()) {
                for (TipoDocumentoProbatorio key : doctos.keySet()) {
                    if (tipoDocto.equals(key.getIdTipoDocumentoProbatorio())) {
                        currMapKey = getCurrentMapKey(doctos, tipoDocto.getIdTipoDocumentoProbatorio());
                    }
                }
        }
        if (currMapKey == null) {
            currMapKey = tipoDocto;
            doctos.put(currMapKey, new ArrayList<DocumentoProbatorio>());
        }
        return currMapKey;
    }
    
    private int valorarTiposolicitante(String tipoSolicitante,String tipoBeneficiario){
        int tiposolicitante =0;
        if(tipoSolicitante.equals(REPRESENTANTE_LEGAL)){
            tiposolicitante=6;
        }else if(tipoSolicitante.equals("1") && tipoBeneficiario!=null){
            if(tipoBeneficiario.equals(CONYUGUE)){
                tiposolicitante=2;
            }else if(tipoBeneficiario.equals(CONCUBINO)){
                tiposolicitante=5;
            }else if(tipoBeneficiario.equals(DESCENDIENTE)){
                tiposolicitante=3;
            }
        }
        return tiposolicitante;
    }
    
    private Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> addDocumentoPorTipoBeneficiario(Integer tipoSolicitante,
            Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos,
            DoctoReqTramite doctoReqTramite, TipoDocumentoProbatorio currMapKey) {
        
        Integer tipoDocumento = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio();
        Long cveIdDocumento = doctoReqTramite.getDocumentoPorTipo().getDocumento().getCveIdDocumento();
        DocumentoProbatorio docProbatorioVo = convertDocumtProVO(doctos, doctoReqTramite, currMapKey);
        if (tipoDocumento.longValue() == TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()
                && cveIdDocumento.intValue() != DocumentoEnum.CURP.getId()) {
            doctos.get(currMapKey).add(docProbatorioVo);
        }
        switch (tipoSolicitante) {
            //Conyuge
            case 2:
                if (cveIdDocumento.intValue() == DocumentoEnum.ACTA_MATRIMONIO.getId()) {
//				log.debug("--CDA-- se agrega ACTA DE MATRIMONIO EN BENE: {}, {}",docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
                    doctos.get(currMapKey).add(docProbatorioVo);
                }
                break;
            //Descendiente
            case 3:
                if (docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_NACIMIENTO.getId()) {
//				log.debug("--CDA-- se agrega documento hijo: {}, {}",docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
                    doctos.get(currMapKey).add(docProbatorioVo);
                }
                break;
            //Concubino
            case 5:
                if (docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.CONSTANCIA_CONCUBINATO.getId()) {
//				log.debug("--CDA-- se agrega documento concubino: {}, {}",docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
                    doctos.get(currMapKey).add(docProbatorioVo);
                }
                break;
            //Representante
            case 6:
                if (docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.PODER_NOTARIAL.getId()) {
//				log.debug("--CDA-- se agrega documento representante: {}, {}",
//						docProbatorioVo.getCveIdDocumento(),docProbatorioVo.getDesDocumento());
                    doctos.get(currMapKey).add(docProbatorioVo);
                }
                break;
            default:

        }

        return doctos;
    }
    
        private TipoDocumentoProbatorio getCurrentMapKey(Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos, Integer tipoDocto){
		TipoDocumentoProbatorio currMapKey = null;
		for (TipoDocumentoProbatorio key : doctos.keySet()) {
			if(tipoDocto.equals(key.getIdTipoDocumentoProbatorio())){
				currMapKey = key;
                                break;
			}
		}
		return currMapKey;
	}
    
    private List<DoctoReqTramite> getDoctoReqTramite(long idTramite){
         return documentoProbatorioServiceBusiness.getDocumentosRequeridosPorTipoTramite(idTramite);
    }
    
    private DocumentoProbatorio convertDocumtProVO(Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos,DoctoReqTramite doctoReqTramite,TipoDocumentoProbatorio currMapKey){
        DocumentoProbatorio docProbatorioVo = new DocumentoProbatorio();
        docProbatorioVo.setTipoDocumento(doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
        docProbatorioVo.setCveIdDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getCveIdDocumento());
        docProbatorioVo.setDesDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getDesDocumento());
        docProbatorioVo.setIdDocumentoPorTipo(doctoReqTramite.getDocumentoPorTipo().getIdDocumentoPorTipo().longValue());
        return docProbatorioVo;
    }
}
