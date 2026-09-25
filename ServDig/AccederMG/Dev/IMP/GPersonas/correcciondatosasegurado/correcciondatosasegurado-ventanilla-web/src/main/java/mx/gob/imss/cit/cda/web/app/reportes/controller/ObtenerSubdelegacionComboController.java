package mx.gob.imss.cit.cda.web.app.reportes.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.common.model.Combo;
import mx.gob.imss.cit.cda.web.app.common.model.FiltroCombo;
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
 * @author antonio
 */
@Controller
public class ObtenerSubdelegacionComboController extends AbstractReadController<FiltroCombo, ArrayList<Combo>> {

	@Autowired
	@Qualifier(BeansConstants.SUBDELEGACION_COMBO_HELPER)
	ReadHelper<FiltroCombo, ArrayList<Combo>> service;

	@Override
	public ReadHelper<FiltroCombo, ArrayList<Combo>> getHelper() {
		return service;
	}

	@RequestMapping(RequestMappingConstants.SUBDELEGACION_COMBO)
	@ResponseBody
	@Override
	public ResponseEntity<ArrayList<Combo>> load(@RequestBody FiltroCombo input, HttpServletRequest request){
	    return super.load(input, request);
	}

}

