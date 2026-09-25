package mx.gob.imss.cit.cda.web.common.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.autorizador.model.FiltroResponsables;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.common.model.Combo;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Controlador para llenado de combo autorizadores por subdelegacion filtros
 * bandeja de solicitudes responsable y autorizador
 */
@Controller
public class ReadComboAutorizadoresController extends AbstractReadController<FiltroResponsables, ArrayList<Combo>> {

    @Autowired
    @Qualifier(BeansConstants.READ_COMBO_AUTORIZADORES_HELPER)
    private ReadHelper<FiltroResponsables, ArrayList<Combo>> service;

    @Override
    public ReadHelper<FiltroResponsables, ArrayList<Combo>> getHelper() {
        return service;
    }

    @RequestMapping(RequestMappingConstants.READ_COMBO_AUTORIZADORES)
    @ResponseBody
    @Override
    public ResponseEntity<ArrayList<Combo>> load(@RequestBody FiltroResponsables input, HttpServletRequest request) {
        return super.load(input, request);
    }
}
