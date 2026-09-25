package  mx.gob.imss.cit.cda.web.app.reportes.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.model.Combo;
import mx.gob.imss.cit.cda.web.app.common.model.FiltroCombo;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.reportes.dto.VariableDTO;
import mx.gob.imss.cit.cda.web.app.reportes.model.VariablesReportes;
import mx.gob.imss.cit.cda.web.app.responsable.model.RequestTramitesReportesPage;
import mx.gob.imss.cit.cda.web.constants.FlujoTrabajoConstants;
import mx.gob.imss.cit.cda.web.constants.RolUsuarioEnum;
import mx.gob.imss.cit.cda.web.support.model.Page;

import org.springframework.beans.factory.annotation.Autowired;
import mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean;
import mx.gob.imss.ctirss.delta.service.interfaces.ISelectService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(BeansConstants.VARIABLE_ELEGIDA_HELPER)
public class ObtenerEstadisticaHelper implements ReadHelper<RequestTramitesReportesPage, Page<VariablesReportes>> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ObtenerEstadisticaHelper.class);
	
	@Autowired
	private ISelectService componentComboService;
	private final int MIN = 0;
	private final int MAX = 100;
	
//	private static final TipoTramiteEnum [] ORIGEN_INVALIDOS = {OrigenSolicitudEnum.MOVILES,OrigenSolicitudEnum.PORTAL_CIUDADANO,OrigenSolicitudEnum.VENTANILLA_UNICA,OrigenSolicitudEnum.ECONOMIA};

	@SuppressWarnings("unchecked")
	@Override
	public ReadEvent<Page<VariablesReportes>> requestEvent(
			RequestReadEvent<RequestTramitesReportesPage> requestReadEvent) {
		Page<VariablesReportes> page = new Page<VariablesReportes>();
		List<VariablesReportes> list = new ArrayList<VariablesReportes>();
		LOGGER.debug("data:"+(requestReadEvent.getData() == null));
		LOGGER.debug("filter:"+(requestReadEvent.getData().getFilter() == null));
		LOGGER.debug("variable:"+(requestReadEvent.getData().getFilter().getVariable() == null));
		LOGGER.debug("var:"+requestReadEvent.getData().getFilter().getVariable());
		
		if(requestReadEvent.getData().getFilter().getVariable() != null && !requestReadEvent.getData().getFilter().getVariable().equals("-1")){
			VariablesReportes variableReportes;
			
			Random r = new Random();
			
			for(int i=0; i< 10; i++){
				variableReportes = new VariablesReportes();
				variableReportes.setDescripcion("Descipcioon:"+(i+1));
				variableReportes.setCantidad(""+ (r.nextInt((MAX - MIN) + 1) + MAX));
				list.add(variableReportes);
			}
			page.setTotalOfRecords(10L);
		}else{
			LOGGER.debug("No se selecciono ningun valor");
		}
		
		page.setCurrentPage(1L);
		page.setPageSize(100L);
		page.setData(list);
		return new ReadEvent<Page<VariablesReportes>>(requestReadEvent.getKey(), page);
	}

}

