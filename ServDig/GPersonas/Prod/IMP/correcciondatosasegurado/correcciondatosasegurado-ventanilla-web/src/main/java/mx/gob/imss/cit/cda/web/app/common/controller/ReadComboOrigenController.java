package mx.gob.imss.cit.cda.web.app.common.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.model.Combo;
import mx.gob.imss.cit.cda.web.app.common.model.FiltroCombo;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;

/**
*
* Controlador para llenado de combo origen filtros bandeja de solicitudes responsable y autorizador
*/
@Controller
public class ReadComboOrigenController  extends AbstractReadController<FiltroCombo, ArrayList<Combo>> {

	@Autowired
	@Qualifier(BeansConstants.READ_COMBO_ORIGEN_HELPER)
	ReadHelper<FiltroCombo, ArrayList<Combo>> service;

	@Override
	public ReadHelper<FiltroCombo, ArrayList<Combo>> getHelper() {
		return service;
	}

	@RequestMapping(RequestMappingConstants.READ_COMBO_ORIGEN)
	@ResponseBody
	@Override
	public ResponseEntity<ArrayList<Combo>> load(@RequestBody FiltroCombo input, HttpServletRequest request){
	    return super.load(input, request);
	}
	
}
