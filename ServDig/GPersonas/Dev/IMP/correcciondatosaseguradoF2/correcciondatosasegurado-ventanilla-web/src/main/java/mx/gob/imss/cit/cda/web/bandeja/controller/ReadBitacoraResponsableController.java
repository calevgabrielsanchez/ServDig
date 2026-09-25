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
import mx.gob.imss.cit.cda.web.bandeja.vo.Bitacora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author yisus
 */
@Controller
public class ReadBitacoraResponsableController
        extends AbstractReadController<Bitacora, Bitacora> {

    @Autowired
    @Qualifier(BeansConstants.READ_BITACORA_HELPER)
    private ReadHelper<Bitacora, Bitacora> service;

    @Override
    public ReadHelper<Bitacora, Bitacora> getHelper() {
        return service;
    }

    @RequestMapping(RequestMappingConstants.READ_BITACORA_ESTATUS)
    @ResponseBody
    @Override
    public ResponseEntity<Bitacora> load(@RequestBody Bitacora input,
            HttpServletRequest request) {
        return super.load(input, request);
    }

}
