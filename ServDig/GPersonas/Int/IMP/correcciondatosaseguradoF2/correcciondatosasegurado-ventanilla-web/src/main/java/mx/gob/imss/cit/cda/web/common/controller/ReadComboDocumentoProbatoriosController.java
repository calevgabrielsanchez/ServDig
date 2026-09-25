package mx.gob.imss.cit.cda.web.common.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.common.model.FiltroDocumentoProbatorio;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReadComboDocumentoProbatoriosController extends AbstractReadController<FiltroDocumentoProbatorio, HashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> {

    @Autowired
    @Qualifier(BeansConstants.OBTENER_COMBO_DOCUMENTO_HELPER)
    private ReadHelper<FiltroDocumentoProbatorio, HashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> service;

    @Override
    public ReadHelper<FiltroDocumentoProbatorio, HashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> getHelper() {
        return service;
    }

    @RequestMapping(RequestMappingConstants.READ_COMBO_DOCUMENTO_NSS)
    @ResponseBody
    @Override
    public ResponseEntity<HashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>> load(@RequestBody FiltroDocumentoProbatorio input, HttpServletRequest request) {
        return super.load(input, request);
    }
}
