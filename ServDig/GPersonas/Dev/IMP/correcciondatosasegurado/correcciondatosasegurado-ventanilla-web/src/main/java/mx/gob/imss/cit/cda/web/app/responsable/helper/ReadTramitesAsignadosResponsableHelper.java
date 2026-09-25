package mx.gob.imss.cit.cda.web.app.responsable.helper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Filter;
import mx.gob.imss.cit.cda.web.app.responsable.model.RequestTramitesAsignadosPage;
import mx.gob.imss.cit.cda.web.app.responsable.model.TramitesAsignados;
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
 *
 * @author yisus
 */
@Component(BeansConstants.READ_TRAMITES_RESPONSABLE_HELPER)
public class ReadTramitesAsignadosResponsableHelper implements ReadHelper<RequestTramitesAsignadosPage, Page<TramitesAsignados>> {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(ReadTramitesAsignadosResponsableHelper.class);
	private static final String USUARIO_ERROR_SINDO = "SINDO";
	
	@Autowired
	@Qualifier("componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	@Autowired
	@Qualifier("responsablesDelegacionBusiness")
	private ResponsablesDelegacionRemote responsablesDelegacionBusiness;

	@Autowired
	@Qualifier("flujoTrabajoBusiness")
	private FlujoTrabajoRemote flujoTrabajoBusiness;

	@Autowired
	private PersonaBusinessRemote personaBusiness;
	
	@Autowired
	private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;
	
	@SuppressWarnings("unchecked")
	@Override
	public ReadEvent<Page<TramitesAsignados>> requestEvent(
			RequestReadEvent<RequestTramitesAsignadosPage> requestReadEvent) {
		
		LOGGER.info("---CDA--- Usuario a buscar {}",requestReadEvent.getUserProfile().getPerfil().equals(RolUsuarioEnum.VENTANILLA.getRol())?requestReadEvent.getUserProfile().getUsuario():(FlujoTrabajoConstants.BPM_ADMIN+requestReadEvent.getUserProfile().getIdSubdelegacion()));
		LOGGER.info("---CDA--- Rol {}",requestReadEvent.getUserProfile().getPerfil());
		
		DataPage dataPage = prepararFiltros(requestReadEvent);
		
		
		if(validarBusqueda(requestReadEvent.getData().getFilter())){
			dataPage = realizarBusqueda(requestReadEvent, dataPage);
		}else{
			dataPage.setData(null);
		}	
		
		LOGGER.debug("---CDA--- registros a regresar {}",dataPage!= null && dataPage.getData()!= null?dataPage.getData().size():0 );
		
		Page<TramitesAsignados> page = null;
		try {
			page = convertirAmodelo(dataPage,requestReadEvent.getUserProfile().getUsuario());
						
			return new ReadEvent<Page<TramitesAsignados>>(requestReadEvent.getKey(), page);
		} catch (Exception e) {
			LOGGER.error("---CDA---",e);
			return ReadEvent.notFound(requestReadEvent.getKey());
		}

	}
	
	private DataPage realizarBusqueda(RequestReadEvent<RequestTramitesAsignadosPage> requestReadEvent, DataPage dataPage){
		List<Long> idsProcesos = new ArrayList<Long>();
        idsProcesos.add(ProcesosNegocioEnum.CDA.getId());
		DataPage dataPageReturn = null;
		if(requestReadEvent.getUserProfile().getPerfil().equals(RolUsuarioEnum.VENTANILLA.getRol())){			
			dataPageReturn = obtenerTareasResponsable(requestReadEvent,dataPage,idsProcesos);			
		}else{
			dataPageReturn = flujoTrabajoBusiness.obtenerTareasPorSubdelegacion(dataPage,requestReadEvent.getUserProfile().getIdSubdelegacion().toString(),idsProcesos);			
		}
		
		return  dataPageReturn;
	}
	
	private boolean validarBusqueda(Filter filter){		
		return StringUtils.isBlank(filter.getEstado()) || (!filter.getEstado().equals(EstadoNegocioEnum.ATENDIDA.getDescripcion())
				&& !filter.getEstado().equals(EstadoNegocioEnum.ABANDONADA.getDescripcion()));		
	}


	@SuppressWarnings("unchecked")
	private Page<TramitesAsignados> convertirAmodelo(DataPage dataPage, String usuario) {

		DeltaUtils deltaUtils = new DeltaUtils();
		
		TramitesAsignados tramitesAsignados = null;
		Page<TramitesAsignados> page = new Page<TramitesAsignados>();
		List<TramitesAsignados> list = new ArrayList<TramitesAsignados>();
		String nombreResponsable = "", nombreAutorizador= "";
		Map<String, String> funcionarios = new HashMap<String, String>();
		
		if(dataPage != null && dataPage.getData() != null){
			
			for (TareaBandeja bandeja : (Collection<TareaBandeja>) dataPage.getData()) {
				
				if(StringUtils.isNotBlank(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()))){
					nombreResponsable = this.nombrePersona(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()), funcionarios);
					LOGGER.debug("---CDA--- Responsable a buscar por curp {} ",bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
					
				}	
				
				if(StringUtils.isNotBlank(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()))){
					nombreAutorizador = this.nombrePersona(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()), funcionarios);
					LOGGER.debug("---CDA--- Autorizador a buscar por curp {} ",bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()));
					
				}	
				
				tramitesAsignados = new TramitesAsignados();
				tramitesAsignados.setEsPropietario(usuario.equalsIgnoreCase(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion())));
				tramitesAsignados.setNombreCompleto(nombreResponsable != null && ! nombreResponsable.isEmpty() ? 
						nombreResponsable : bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
				tramitesAsignados.setNombreCompletoAutorizo(nombreAutorizador.equals("") ? "SIN AUTORIZADOR": nombreAutorizador);
				tramitesAsignados.setIdTramite(bandeja.getIdTramite().toString());
				tramitesAsignados.setIdTarea(bandeja.getIdTareaUsuario().toString());
				tramitesAsignados.setFolio(bandeja.getInicioTramite().getFolio());
					
				try {
					tramitesAsignados.setFechaSolicitud(deltaUtils.cambiarFormatoFecha("dd/MM/yyyy", bandeja.getInicioTramite().getFechaSolicitud(), "dd/MM/yyyy"));
				} catch (ParseException e1) {
					
					Log.error("-- CDA: Error Fecha Solicitud. ", e1);
				}
				
				tramitesAsignados.setEstatus(bandeja.getInicioTramite().getEstatus().toUpperCase());
				
				//obtener el estado del tramite si es procesado SINDO mostrarlo en la bandeja ya que este o se modifica en la instancia 
				//debido a que la rutina de ODI no tiene la capacidad de modificar el xml de la instancia.
				EstadoTramite estado = registroSolicitudCorreccionDatosAseguradoBusiness.consultarEstadoTramiteById(bandeja.getIdTramite().longValue());
				
				LOGGER.debug("---CDA--- Estado {} ",estado.getIdEstadoTramitePersona());
				
				if(estado.getIdEstadoTramitePersona().equals(EstadoTramiteEnum.PROCESADO_SINDO.getCodigo()) 
						|| estado.getIdEstadoTramitePersona().equals(EstadoTramiteEnum.ERROR_SINDO.getCodigo())){
					agregarObservaciones(tramitesAsignados,estado,bandeja.getIdTramite().longValue());
					tramitesAsignados.setEstatus(EstadoNegocioEnum.obtenerDescripcionNegocio(estado.getIdEstadoTramitePersona()));
					
				}
				
				tramitesAsignados.setResponsable(nombreResponsable != null && ! nombreResponsable.isEmpty() ? 
						nombreResponsable : bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
				tramitesAsignados.setAutorizo(nombreAutorizador.equals("") ? "SIN AUTORIZADOR": nombreAutorizador);
				if (!bandeja.getInicioTramite().getData().equals("")) {
					Map<String, Object> datos =WorkFlowDataUtil.generarJavaDataWf(bandeja.getInicioTramite().getData());
					tramitesAsignados.setNssInvolucrados((String)datos.get("nssInvolucrados"));
					tramitesAsignados.setOrigen((String)datos.get("origen"));
				}
				if(StringUtils.isNotBlank(bandeja.getInicioTramite().getData())){
					Map<String, Object> datos =WorkFlowDataUtil.generarJavaDataWf(bandeja.getInicioTramite().getData());
					LOGGER.debug("datos {}",datos);
					LOGGER.info("tipo Regularizacion {}",datos.get("tipoRegularizacion"));
					tramitesAsignados.setTipo(datos.get("tipoRegularizacion")!= null ?TipoRegularizacionSolicitudCDAEnum.fromId(((Integer)datos.get("tipoRegularizacion"))).getDescripcion():"SIN TIPO");
				}else{
					tramitesAsignados.setTipo("SIN TIPO");
				}
				
				try {
					tramitesAsignados.setUltimaActualizacion(deltaUtils.cambiarFormatoFecha("dd/MM/yyyy", bandeja.getInicioTramite().getFechaActualizacion(), "dd/MM/yyyy"));
				} catch (ParseException e1) {
					
					Log.error("-- CDA: Error Fecha Actualizacion. ", e1);
				}
				
				tramitesAsignados.setCurp("FALTA CURP");
				
				list.add(tramitesAsignados);
			}
		}
		page.setData(list);
		page.setCurrentPage(dataPage.getCurrentPage());
		page.setTotalOfRecords(dataPage.getTotalOfRecords());
		page.setPageSize(dataPage.getPageSize());

		return page;
	}

	
private DataPage prepararFiltros(RequestReadEvent<RequestTramitesAsignadosPage> requestReadEvent){
		
		DataPage dataPage = new DataPage();
		dataPage.setCurrentPage(requestReadEvent.getData().getPage());
		dataPage.setPageSize(requestReadEvent.getData().getPageSize());
		
		ArrayList<HashMap<String, String>> listFilter = new ArrayList<HashMap<String, String>>();
		HashMap<String,String> filtros = new HashMap<String, String>();
		
		if(requestReadEvent.getData().getFilter()!=null){
			LOGGER.debug("Filtros de Vista",requestReadEvent.getData().getFilter().toString());
			
			filtros.put("filtroFolio",requestReadEvent.getData().getFilter().getFolio());
			
			if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getFechaSolicitud())){
				filtros.put("filtroFechaSolicitud",requestReadEvent.getData().getFilter().getFechaSolicitud());
			}
			
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
			if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getCurp())){
				filtros.put("filtroCurp", requestReadEvent.getData().getFilter().getCurp());
			}
			if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getTramite()) && !requestReadEvent.getData().getFilter().getTramite().equals("-1")){
				filtros.put("filtroTramite",requestReadEvent.getData().getFilter().getTramite());
			}
			if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getFechaActualizacion())){
				filtros.put("filtroFechaActualizacion",requestReadEvent.getData().getFilter().getFechaActualizacion());
			}
			if(requestReadEvent.getData().getFilter().getFoliosAsociados() != null 
					&& requestReadEvent.getData().getFilter().getFoliosAsociados()){
				filtros.put("filtroUsuario", requestReadEvent.getUserProfile().getUsuario());
			}
			if(requestReadEvent.getData().getFilter().getFoliosVencidos() != null 
					&& requestReadEvent.getData().getFilter().getFoliosVencidos()){
				filtros.put("filtroVencido", requestReadEvent.getUserProfile().getUsuario());
			}
			
		}
		listFilter.add(filtros);
		dataPage.setData(listFilter);
		
		return dataPage;
	}

	/**
	*
	* Consulta por default o por filtros
	*/
	private DataPage obtenerTareasResponsable(RequestReadEvent<RequestTramitesAsignadosPage> requestReadEvent,DataPage dataPage, List<Long> idsProcesos){
		DataPage dataPageReturn = null;
		LOGGER.debug("---CDA---Filtros {}",requestReadEvent.getData().getFilter());
		if(validarBusquedaPorUsuario(requestReadEvent.getData().getFilter())){
			LOGGER.debug("---CDA--- Buscando por Usuario : {} ",requestReadEvent.getUserProfile().getUsuario());
			dataPageReturn = flujoTrabajoBusiness.obtenerTareasPorUsuarioResponsable(dataPage,requestReadEvent.getUserProfile().getUsuario(),idsProcesos);
		}else{
			LOGGER.debug("---CDA---Buscando por Subdelegacion {} ",requestReadEvent.getUserProfile().getIdSubdelegacion().toString());
			LOGGER.debug("---CDA---Con filtro de usuario {} ",requestReadEvent.getUserProfile().getUsuario());			
			dataPageReturn = flujoTrabajoBusiness.obtenerTareasPorSubdelegacion(dataPage,requestReadEvent.getUserProfile().getIdSubdelegacion().toString(),idsProcesos);
		}
		return dataPageReturn;
	}
	
	/**
	*
	* Valida si la consulta va sin filtros y/o asignadas las solicitudes al responsable
	*/
	private boolean validarBusquedaPorUsuario(Filter filter){
		return filter == null || (filter.getFoliosAsociados()!= null && filter.getFoliosAsociados());
	}
	
	private void agregarObservaciones(TramitesAsignados tramitesAsignados,EstadoTramite estado, long idTramite){
		LOGGER.debug("---CDA--- Estado Tramite Asignado {} ",tramitesAsignados.getEstatus());
		LOGGER.debug("---CDA--- Estado Tramite {} ",EstadoNegocioEnum.obtenerDescripcionNegocio(estado.getIdEstadoTramitePersona()));
		if(!tramitesAsignados.getEstatus().equalsIgnoreCase(EstadoNegocioEnum.obtenerDescripcionNegocio(estado.getIdEstadoTramitePersona()))){
			try {
				registroSolicitudCorreccionDatosAseguradoBusiness.agregarObservacionesSubdelegacion(idTramite, USUARIO_ERROR_SINDO,tramitesAsignados.getIdTarea());
			} catch (SolicitudNoEncontradaException e) {
				LOGGER.error("Error {}",e);
			} catch (TramiteNoEncontradoException e) {
				LOGGER.error("Error {}",e);
			} catch (IllegalArgumentException e) {
				LOGGER.error("Error {}",e);
			}
						
		}
		
	}
	
	private String nombrePersona(String curp, Map<String, String> funcionarios){
		String nombreCompleto = "" ;
		if(funcionarios.containsKey(curp)){
			nombreCompleto = funcionarios.get(curp);
			LOGGER.debug("---CDA--- Funcionario exite en cache {}", curp);
		}else{
			try {			
				Usuario nombre = responsablesDelegacionBusiness.recuperaUsuarioEsquemaSeguridadByCURP(curp);
				if(nombre != null && nombre.getFisica() != null && nombre.getFisica().getNombreCompleto() != null){
					nombreCompleto = nombre.getFisica().getNombreCompleto();
				}
			}  catch (ClienteWebserviceResponsablesSubdelegacionException e) {
				LOGGER.error("ClienteWebserviceResponsablesSubdelegacionException {} ",e);
			}
			funcionarios.put(curp, nombreCompleto);
			LOGGER.debug("---CDA--- Se agrega funcionario en cache {}", curp);
		}
		return nombreCompleto;
	}
}
