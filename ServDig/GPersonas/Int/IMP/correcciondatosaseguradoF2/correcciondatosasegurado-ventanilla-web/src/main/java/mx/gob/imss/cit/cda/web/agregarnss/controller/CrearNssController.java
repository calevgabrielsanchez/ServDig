package mx.gob.imss.cit.cda.web.agregarnss.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractUpdateController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.DocumentosNss;
import mx.gob.imss.cit.cda.web.app.responsable.model.NSSAdicional;
import mx.gob.imss.cit.cda.web.support.model.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CrearNssController extends AbstractUpdateController<NSSAdicional,Page<DocumentosNss>> {

    @Autowired
    @Qualifier(BeansConstants.UPDATE_NSS_HELPER)
    private UpdateHelper<NSSAdicional, Page<DocumentosNss>> service;

    @RequestMapping(RequestMappingConstants.REQUEST_UPDATE_NSS)
    @ResponseBody
    @Override
    public ResponseEntity<Page<DocumentosNss>> update(@RequestBody NSSAdicional input, HttpServletRequest request) {
        return super.update(input, request);
    }

    @Override
    public UpdateHelper<NSSAdicional, Page<DocumentosNss>> getHelper() {
        return this.service;
    }

}
