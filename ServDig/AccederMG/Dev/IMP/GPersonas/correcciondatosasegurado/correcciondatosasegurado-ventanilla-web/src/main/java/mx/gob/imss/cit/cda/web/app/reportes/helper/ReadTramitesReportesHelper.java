package mx.gob.imss.cit.cda.web.app.reportes.helper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.reportes.transformer.DatapageTramiteReporteTransformer;
import mx.gob.imss.cit.cda.web.app.responsable.model.FilterReporte;
//import mx.gob.imss.cit.cda.web.app.responsable.model.RequestTramitesAsignadosPage;
import mx.gob.imss.cit.cda.web.app.responsable.model.RequestTramitesReportesPage;
//import mx.gob.imss.cit.cda.web.app.responsable.model.TramitesAsignados;
import mx.gob.imss.cit.cda.web.app.reportes.model.TramitesReportes;
import mx.gob.imss.cit.cda.web.constants.FlujoTrabajoConstants;
import mx.gob.imss.cit.cda.web.constants.RolUsuarioEnum;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.cda.web.utils.DeltaUtils;
import mx.gob.imss.cit.cda.web.utils.WorkFlowDataUtil;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ProcesosNegocioEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionSolicitudCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author antonio
 */
@Component(BeansConstants.READ_TRAMITES_REPORTES_HELPER)
public class ReadTramitesReportesHelper implements ReadHelper<RequestTramitesReportesPage, Page<TramitesReportes>> {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(ReadTramitesReportesHelper.class);
	
	@Autowired
	@Qualifier("flujoTrabajoBusiness")
	private FlujoTrabajoRemote flujoTrabajoBusiness;

	@Autowired
	@Qualifier("componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	@Autowired
	private PersonaBusinessRemote personaBusiness;
	
	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;
	
	@Autowired
	private DatapageTramiteReporteTransformer datapageTramiteReporteTransformer;

	@SuppressWarnings("unchecked")
	@Override
	public ReadEvent<Page<TramitesReportes>> requestEvent(
			RequestReadEvent<RequestTramitesReportesPage> requestReadEvent) {
		LOGGER.debug("---PAGINA {}", requestReadEvent.getData().getPage());
		LOGGER.debug("---REPORTES CDA--- Usuario a buscar {}",requestReadEvent.getUserProfile().getPerfil().equals(RolUsuarioEnum.VENTANILLA.getRol())?requestReadEvent.getUserProfile().getUsuario():(FlujoTrabajoConstants.BPM_ADMIN+requestReadEvent.getUserProfile().getIdSubdelegacion()));
		LOGGER.debug("---REPORTES CDA--- Usuario {}",requestReadEvent.getUserProfile().getUsuario());
		LOGGER.debug("---REPORTES CDA--- Rol {}",requestReadEvent.getUserProfile().getPerfil());
		
		DataPage dataPage = prepararFiltros(requestReadEvent);
		
		if(validarBusqueda(requestReadEvent.getData().getFilter())){
			LOGGER.debug("por realizar busqueda");
			dataPage = realizarBusqueda(requestReadEvent, dataPage);
		}else{
			LOGGER.debug("set data null");
			dataPage.setData(null);
		}		
		Page<TramitesReportes> page = null;
		try {
			page = convertirAmodelo2(dataPage, requestReadEvent.getData().getFilter().getFolio());
			LOGGER.debug("return pagina");
			return new ReadEvent<Page<TramitesReportes>>(requestReadEvent.getKey(), page);
		} catch (Exception e) {
			LOGGER.error("---CDA---",e);
			return ReadEvent.notFound(requestReadEvent.getKey());
		}

	}
	
	private DataPage realizarBusqueda(RequestReadEvent<RequestTramitesReportesPage> requestReadEvent, DataPage dataPage){
		List<Long> idsProcesos = new ArrayList<Long>();
        idsProcesos.add(ProcesosNegocioEnum.CDA.getId());
		DataPage dataPageReturn = null;
		if(requestReadEvent.getUserProfile().getPerfil().equals(RolUsuarioEnum.VENTANILLA.getRol())){	
			LOGGER.debug("---VENTANILLA");
			dataPageReturn = obtenerTareasResponsable(requestReadEvent,dataPage,idsProcesos);			
		}else{
			LOGGER.debug("---ELSE");
			dataPageReturn = flujoTrabajoBusiness.obtenerTareasPorSubdelegacion(dataPage,requestReadEvent.getUserProfile().getIdSubdelegacion().toString(),idsProcesos);			
		}
		
		return  dataPageReturn;
	}


	@SuppressWarnings("unchecked")
	private Page<TramitesReportes> convertirAmodelo2(DataPage dataPage, String folio) {
		Log.debug("is Folio null? "+ folio == null);
		
		DeltaUtils deltaUtils = new DeltaUtils();

		TramitesReportes tramitesAsignados = null;
		Page<TramitesReportes> page = new Page<TramitesReportes>();
		List<TramitesReportes> list = new ArrayList<TramitesReportes>();
		//Map<String, String> funcionarios = new HashMap<String, String>();
		
		//if(dataPage != null && dataPage.getData() != null){
			//for (TareaBandeja bandeja : (Collection<TareaBandeja>) dataPage.getData()) {
		for(int i=0; i< 10; i++){
				
				tramitesAsignados = new TramitesReportes();
				tramitesAsignados.setIdTramite("1234567");
				tramitesAsignados.setIdTarea("222222");
				tramitesAsignados.setFolio("123456789987654321");
				//try {
					tramitesAsignados.setFechaSolicitud("12/11/2018");
				//} catch (ParseException e1) {
					
				//	Log.error("-- CDA: Error Fecha Solicitud. ", e1);
				//}
				
				//tramitesAsignados.setEstatus(bandeja.getInicioTramite().getEstatus().toUpperCase());
				
				//obtener el estado del tramite si es procesado SINDO mostrarlo en la bandeja ya que este o se modifica en la instancia 
				//debido a que la rutina de ODI no tiene la capacidad de modificar el xml de la instancia.
				//EstadoTramite estado = registroSolicitudCorreccionDatosAseguradoBusiness.consultarEstadoTramiteById(bandeja.getIdTramite().longValue());
				
				//LOGGER.debug("---CDA--- Estado {} ",estado.getIdEstadoTramitePersona());
				
				//if(estado.getIdEstadoTramitePersona().equals(EstadoTramiteEnum.PROCESADO_SINDO.getCodigo()) 
				//		|| estado.getIdEstadoTramitePersona().equals(EstadoTramiteEnum.ERROR_SINDO.getCodigo())){
					//agregarObservaciones(tramitesAsignados,estado,bandeja.getIdTramite().longValue());
					tramitesAsignados.setEstatus("ESTATUS");					
					//agregarObservaciones(tramitesAsignados,estado,bandeja.getIdTramite().longValue());
					
				//}
				
				tramitesAsignados.setResponsable("RESPONSABLE");
				//if (!bandeja.getInicioTramite().getData().equals("")) {
					//Map<String, Object> datos =WorkFlowDataUtil.generarJavaDataWf(bandeja.getInicioTramite().getData());
					tramitesAsignados.setNssInvolucrados("NSS");
					tramitesAsignados.setOrigen((String)"origen");
				//}			
				
				//if(StringUtils.isNotBlank(bandeja.getInicioTramite().getData())){
					//Map<String, Object> datos =WorkFlowDataUtil.generarJavaDataWf(bandeja.getInicioTramite().getData());
					//LOGGER.debug("datos {}",datos);
					//LOGGER.info("tipo Regularizacion {}",datos.get("tipoRegularizacion"));
					//tramitesAsignados.setTipo(datos.get("tipoRegularizacion")!= null ?TipoRegularizacionSolicitudCDAEnum.fromId(((Integer)datos.get("tipoRegularizacion"))).getDescripcion():"SIN TIPO");
				//}else{
					tramitesAsignados.setTipo("SIN TIPO");
				//}
				
				
				//try {
					tramitesAsignados.setUltimaActualizacion("01/02/2018");
				//} catch (ParseException e1) {
					
					//Log.error("-- CDA: Error Fecha Actualizacion. ", e1);
				//}			
				
				list.add(tramitesAsignados);
			//}
		}
		page.setData(list);
		//page.setCurrentPage(dataPage.getCurrentPage());
		//page.setTotalOfRecords(dataPage.getTotalOfRecords());
		//page.setPageSize(dataPage.getPageSize());
		
		page.setCurrentPage(3L);
		page.setTotalOfRecords(125L);
		page.setPageSize(10L);

		return page;
	}
	
	private boolean validarBusqueda(FilterReporte filter){		
		return StringUtils.isBlank(filter.getEstado()) || (!filter.getEstado().equals(EstadoNegocioEnum.ATENDIDA.getDescripcion())
				&& !filter.getEstado().equals(EstadoNegocioEnum.ABANDONADA.getDescripcion()));		
	}
	
	private DataPage prepararFiltros(RequestReadEvent<RequestTramitesReportesPage> requestReadEvent){
		
		DataPage dataPage = new DataPage();
		dataPage.setCurrentPage(requestReadEvent.getData().getPage());
		dataPage.setPageSize(requestReadEvent.getData().getPageSize());
		
		ArrayList<HashMap<String, String>> listFilter = new ArrayList<HashMap<String, String>>();
		HashMap<String,String> filtros = new HashMap<String, String>();
		
		if(requestReadEvent.getData().getFilter()!=null){
			if(requestReadEvent.getData().getFilter().getDelegacion()!=null){
				filtros.put("delegacion",requestReadEvent.getData().getFilter().getDelegacion());
			}
			if(requestReadEvent.getData().getFilter().getSubdelegacion()!=null){
			filtros.put("subdelegacion",requestReadEvent.getData().getFilter().getSubdelegacion());
			}
			if(requestReadEvent.getData().getFilter().getAutorizo()!=null){
			filtros.put("autorizo",requestReadEvent.getData().getFilter().getAutorizo());
			}
			if(requestReadEvent.getData().getFilter().getResponsable()!=null){
			filtros.put("responsable",requestReadEvent.getData().getFilter().getResponsable());
			}
			if(requestReadEvent.getData().getFilter().getOrigen()!=null){
			filtros.put("origen",requestReadEvent.getData().getFilter().getOrigen());
			}
			if(requestReadEvent.getData().getFilter().getFolio()!=null){
			filtros.put("folio",requestReadEvent.getData().getFilter().getFolio());
			}
			if(requestReadEvent.getData().getFilter().getCurp()!=null){
			filtros.put("curp",requestReadEvent.getData().getFilter().getCurp());
			}
			if(requestReadEvent.getData().getFilter().getNssInvolucrado()!=null){
			filtros.put("nssInvolucrado",requestReadEvent.getData().getFilter().getNssInvolucrado());
			}
			if(requestReadEvent.getData().getFilter().getTipoTramite()!=null){
			filtros.put("tipoTramite",requestReadEvent.getData().getFilter().getTipoTramite());
			}
			if(requestReadEvent.getData().getFilter().getEstado()!=null){
			filtros.put("estado",requestReadEvent.getData().getFilter().getEstado());
			}
			if(requestReadEvent.getData().getFilter().getCurpBeneficiario()!=null){
			filtros.put("curpBeneficiario",requestReadEvent.getData().getFilter().getCurpBeneficiario());
			}
			if(requestReadEvent.getData().getFilter().isVencida()){
			filtros.put("vencida",String.valueOf(requestReadEvent.getData().getFilter().isVencida()));
			}
			if(requestReadEvent.getData().getFilter().getFechaSolicitudDesde()!=null){
			filtros.put("fechaSolicitudDesde",requestReadEvent.getData().getFilter().getFechaSolicitudDesde().toString());
			}
			if(requestReadEvent.getData().getFilter().getFechaSolicitudHasta()!=null){
			filtros.put("fechaSolicitudHasta",requestReadEvent.getData().getFilter().getFechaSolicitudHasta().toString());
			}
			if(requestReadEvent.getData().getFilter().getFechaFinalizacionDesde()!=null){
			filtros.put("fechaFinalizacionDesde",requestReadEvent.getData().getFilter().getFechaFinalizacionDesde().toString());
			}
			if(requestReadEvent.getData().getFilter().getFechaFinalizacionHasta()!=null){
			filtros.put("fechaFinalizacionHasta",requestReadEvent.getData().getFilter().getFechaFinalizacionHasta().toString());
			}
			if(requestReadEvent.getData().getFilter().getFechaActualizacionDesde()!=null){
			filtros.put("fechaActualizacionDesde",requestReadEvent.getData().getFilter().getFechaActualizacionDesde().toString());
			}
			if(requestReadEvent.getData().getFilter().getFechaActualizacionHasta()!=null){
			filtros.put("fechaActualizacionHasta",requestReadEvent.getData().getFilter().getFechaActualizacionHasta().toString());
			}
				
		}
		listFilter.add(filtros);
		dataPage.setData(listFilter);
		
		return dataPage;
	}

	private DataPage obtenerTareasResponsable(RequestReadEvent<RequestTramitesReportesPage> requestReadEvent,DataPage dataPage, List<Long> idsProcesos){
		DataPage dataPageReturn = null;
		if(requestReadEvent.getData().getFilter().isVencida()){
			LOGGER.debug("---POR USUARIO");
			dataPageReturn = flujoTrabajoBusiness.obtenerTareasPorUsuario(dataPage,requestReadEvent.getUserProfile().getUsuario(),idsProcesos);
		}else{
			LOGGER.debug("---POR SUBDELEGACION");
			dataPageReturn = flujoTrabajoBusiness.obtenerTareasPorSubdelegacion(dataPage,requestReadEvent.getUserProfile().getIdSubdelegacion().toString(),idsProcesos);
		}
		return dataPageReturn;
	}
	
}
