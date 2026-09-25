package mx.gob.imss.cit.cda.web.app.common.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Filter;
import mx.gob.imss.cit.cda.web.app.responsable.model.HistoricoSolicitudes;
import mx.gob.imss.cit.cda.web.app.responsable.model.RequestHistoricoSolicitudesPage;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.cda.web.utils.ReadHistoricoSolicitudUtils;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ProcesosNegocioEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author antonio
 */
@Component(BeansConstants.READ_HISTORICO_SOLICITUDES_HELPER)
public class ReadHistoricoSolicitudesHelper
		implements ReadHelper<RequestHistoricoSolicitudesPage, Page<HistoricoSolicitudes>> {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(ReadHistoricoSolicitudesHelper.class);

	@Autowired
	@Qualifier("flujoTrabajoBusiness")
	private FlujoTrabajoRemote flujoTrabajoBusiness;
	
	@Autowired
	@Qualifier("componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	@Autowired
	private ReadHistoricoSolicitudUtils readHistoricoSolicitudUtils;

	@SuppressWarnings("unchecked")
	@Override
	public ReadEvent<Page<HistoricoSolicitudes>> requestEvent(
			RequestReadEvent<RequestHistoricoSolicitudesPage> requestReadEvent) {
		DataPage dataPage = null;
		try {
		LOGGER.debug("---CDA--- Rol {}",requestReadEvent.getUserProfile().getPerfil());        
        dataPage = prepararFiltros(requestReadEvent);
		if(validarBusqueda(requestReadEvent.getData().getFilter())){
			dataPage= realizarBusqueda(requestReadEvent, dataPage);
		}else{
			dataPage.setData(null);
		}
		Page<HistoricoSolicitudes> page = readHistoricoSolicitudUtils.convertirAmodelo(dataPage, requestReadEvent.getUserProfile().getUsuario());
		
			return new ReadEvent<Page<HistoricoSolicitudes>>(requestReadEvent.getKey(), page);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al consultar tramites asignados {}",e);
			return ReadEvent.notFound(requestReadEvent.getKey());
		}

	}
	
	private DataPage realizarBusqueda(RequestReadEvent<RequestHistoricoSolicitudesPage> requestReadEvent, DataPage dataPage){
		List<Long> idsProcesos = new ArrayList<Long>();
        idsProcesos.add(ProcesosNegocioEnum.CDA.getId());
        DataPage dataPageReturn = null;
		if(validarBusquedaPorUsuario(requestReadEvent.getData().getFilter())){
			LOGGER.debug("---CDA--- Buscando por Usuario : {} ",requestReadEvent.getUserProfile().getUsuario());
			dataPageReturn = flujoTrabajoBusiness.obtenerInstanciasHistoricasPorUsuario(dataPage,requestReadEvent.getUserProfile().getUsuario(),idsProcesos);
		}else{
			dataPageReturn = flujoTrabajoBusiness.obtenerInstanciasHistoricasPorSubdelegacion(dataPage,requestReadEvent.getUserProfile().getIdSubdelegacion().toString(),idsProcesos);
		}
		
		return dataPageReturn;
	}
	
	private boolean validarBusqueda(Filter filter){		
		return StringUtils.isBlank(filter.getEstado()) 
				||filter.getEstado().equals("-1")
				||filter.getEstado().equals(EstadoNegocioEnum.ATENDIDA.getDescripcion())
				|| filter.getEstado().equals(EstadoNegocioEnum.ABANDONADA.getDescripcion());		
	}
	
	private boolean validarBusquedaPorUsuario(Filter filter){
		return filter == null || (filter.getFoliosAsociados()!= null && filter.getFoliosAsociados());
	}

	private DataPage prepararFiltros(RequestReadEvent<RequestHistoricoSolicitudesPage> requestReadEvent){
		
		DataPage dataPage = new DataPage();
		dataPage.setCurrentPage(requestReadEvent.getData().getPage());
		dataPage.setPageSize(requestReadEvent.getData().getPageSize());
		
		ArrayList<HashMap<String, String>> listFilter = new ArrayList<HashMap<String, String>>();
		HashMap<String,String> filtros = new HashMap<String, String>();
		
		if(requestReadEvent.getData().getFilter()!=null){
			LOGGER.debug("Filtros Historicos",requestReadEvent.getData().getFilter().toString());
			
			filtros.put("filtroFolio",requestReadEvent.getData().getFilter().getFolio());
			filtros.put("filtroFechaSolicitud",requestReadEvent.getData().getFilter().getFechaSolicitud());
			filtros.put("filtroNss", requestReadEvent.getData().getFilter().getNss());
			
			if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getOrigen()) && !requestReadEvent.getData().getFilter().getOrigen().equals("-1")){
				filtros.put("filtroOrigen",requestReadEvent.getData().getFilter().getOrigen());
			}
			
			if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getResponsable()) && !requestReadEvent.getData().getFilter().getResponsable().equals("-1")){
				filtros.put("filtroResponsable",requestReadEvent.getData().getFilter().getResponsable());
			}
			
			if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getAutorizo()) && !requestReadEvent.getData().getFilter().getAutorizo().equals("-1")){
				filtros.put("filtroAutorizo",requestReadEvent.getData().getFilter().getAutorizo());
			}
			
			if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getEstado()) && !requestReadEvent.getData().getFilter().getEstado().equals("-1")){
				filtros.put("filtroEstado",requestReadEvent.getData().getFilter().getEstado());
			}
			
			filtros.put("filtroCurp", requestReadEvent.getData().getFilter().getCurp());
			
			if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getTramite()) && !requestReadEvent.getData().getFilter().getTramite().equals("-1")){
				filtros.put("filtroTramite",requestReadEvent.getData().getFilter().getTramite());
			}
			filtros.put("filtroFechaActualizacion",requestReadEvent.getData().getFilter().getFechaActualizacion());
			
			if(requestReadEvent.getData().getFilter().getFoliosVencidos() != null 
					&& requestReadEvent.getData().getFilter().getFoliosVencidos()){
				filtros.put("filtroVencido", requestReadEvent.getUserProfile().getUsuario());
			}
			
			if(requestReadEvent.getData().getFilter().getFoliosAsociados() != null 
					&& requestReadEvent.getData().getFilter().getFoliosAsociados()){
				filtros.put("filtroUsuario", requestReadEvent.getUserProfile().getUsuario());
			}		
		}
		listFilter.add(filtros);
		dataPage.setData(listFilter);
		
		return dataPage;
	}

}
