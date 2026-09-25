package mx.gob.imss.cit.cda.web.app.autorizador.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.HistoricoSolicitudes;
import mx.gob.imss.cit.cda.web.app.responsable.model.RequestHistoricoSolicitudesPage;
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
public class ReadHistoricoSolicitudesAutorizadorController
		extends AbstractReadController<RequestHistoricoSolicitudesPage, Page<HistoricoSolicitudes>> {

	@Autowired
	@Qualifier(BeansConstants.READ_HISTORICO_SOLICITUDES_HELPER)
	ReadHelper<RequestHistoricoSolicitudesPage, Page<HistoricoSolicitudes>> service;

	@Override
	public ReadHelper<RequestHistoricoSolicitudesPage, Page<HistoricoSolicitudes>> getHelper() {
		return service;
	}

	@RequestMapping(RequestMappingConstants.READ_HISTORICO_SOLICITUDES_AUTORIZADOR)
	@ResponseBody
	@Override
	public ResponseEntity<Page<HistoricoSolicitudes>> load(@RequestBody RequestHistoricoSolicitudesPage input, HttpServletRequest request){
	    return super.load(input, request);
	}

}
