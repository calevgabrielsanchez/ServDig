/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.bandeja.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.bandeja.vo.RequestSolicitudBandejaPage;
import mx.gob.imss.cit.cda.web.bandeja.vo.SolicitudBandeja;
import mx.gob.imss.cit.cda.web.support.model.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author antonio
 */
@Controller
public class ReadHistoricoSolicitudesResponsableController extends AbstractReadController<RequestSolicitudBandejaPage, Page<SolicitudBandeja>> {

    @Autowired
    @Qualifier(BeansConstants.READ_HISTORICO_SOLICITUDES_HELPER)
    private ReadHelper<RequestSolicitudBandejaPage, Page<SolicitudBandeja>> service;

    @Override
    public ReadHelper<RequestSolicitudBandejaPage, Page<SolicitudBandeja>> getHelper() {
        return service;
    }

    @RequestMapping(RequestMappingConstants.READ_HISTORICO_SOLICITUDES)
    @ResponseBody
    @Override
    public ResponseEntity<Page<SolicitudBandeja>> load(@RequestBody RequestSolicitudBandejaPage input,HttpServletRequest request) {
        return super.load(input, request);
    }

}
