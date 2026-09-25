package mx.gob.imss.cit.cda.web.common.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.model.FiltroDocumentoProbatorio;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.utils.DocumentoProbatorioUtil;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(BeansConstants.OBTENER_COMBO_DOCUMENTO_HELPER)
public class ReadComboDocumentoProbatoriosHelper implements ReadHelper<FiltroDocumentoProbatorio, Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> {
    
    private final int COMBOS_DOCUMENTO_ASEGURADO=1;
    private final int COMBOS_DOCUMENTO_BENEFICIARIO=2;
    
    @Autowired
    private DocumentoProbatorioUtil documentoProbatorioUtil;

    @Override
    public ReadEvent<Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> requestEvent(
            RequestReadEvent<FiltroDocumentoProbatorio> requestReadEvent) {
        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> comboDocumetosProbatorios=new HashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>();
        switch (requestReadEvent.getData().getIdTipo()) {
        case COMBOS_DOCUMENTO_ASEGURADO:
            comboDocumetosProbatorios = documentoProbatorioUtil
            .getDocumentProbAsegurado(requestReadEvent.getData().isDefuncion());
            break;
        case COMBOS_DOCUMENTO_BENEFICIARIO:
            comboDocumetosProbatorios = documentoProbatorioUtil
            .getDocumentProbBeneficiario(requestReadEvent.getData().getTipoSolicitante(), requestReadEvent.getData().getTipoBeneficiario());
            break;
        default:
            comboDocumetosProbatorios = documentoProbatorioUtil.getDocumentProbNss();
            break;
        }
        try {
            return new ReadEvent<Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>>>(
                    requestReadEvent.getKey(), comboDocumetosProbatorios);
        } catch (Exception e) {
            return ReadEvent.notFound(requestReadEvent.getKey());
        }
    }

}
