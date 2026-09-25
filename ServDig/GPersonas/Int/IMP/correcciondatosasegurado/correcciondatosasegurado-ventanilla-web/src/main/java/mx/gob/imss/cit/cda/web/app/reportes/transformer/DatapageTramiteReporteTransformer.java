package mx.gob.imss.cit.cda.web.app.reportes.transformer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.reportes.helper.ReadTramitesReportesHelper;
import mx.gob.imss.cit.cda.web.app.reportes.model.TramitesReportes;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.cda.web.utils.DeltaUtils;
import mx.gob.imss.cit.cda.web.utils.WorkFlowDataUtil;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionSolicitudCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DatapageTramiteReporteTransformer {

	protected static final Logger LOGGER = LoggerFactory.getLogger(DatapageTramiteReporteTransformer.class);
	private static final String USUARIO_ERROR_SINDO = "SINDO";
	
	@Autowired
	private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;
	
	@Autowired
	@Qualifier("responsablesDelegacionBusiness")
	private ResponsablesDelegacionRemote responsablesDelegacionBusiness;
	
	public Page<TramitesReportes> convertirAmodelo(DataPage dataPage) {
		
		DeltaUtils deltaUtils = new DeltaUtils();

		TramitesReportes tramitesAsignados = null;
		Page<TramitesReportes> page = new Page<TramitesReportes>();
		List<TramitesReportes> list = new ArrayList<TramitesReportes>();
		Map<String, String> funcionarios = new HashMap<String, String>();

		if(dataPage != null && dataPage.getData() != null){
			for (TareaBandeja bandeja : (Collection<TareaBandeja>) dataPage.getData()) {
				String nombreResponsable = "";
				if(StringUtils.isNotBlank(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()))){
					nombreResponsable = this.nombrePersona(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()), funcionarios);
					LOGGER.debug("---CDA--- Responsable a buscar por curp {} ",bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
				}
				tramitesAsignados = new TramitesReportes();
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
					tramitesAsignados.setEstatus(EstadoNegocioEnum.obtenerDescripcionNegocio(estado.getIdEstadoTramitePersona()));					agregarObservaciones(tramitesAsignados,estado,bandeja.getIdTramite().longValue());

				}

				tramitesAsignados.setResponsable(nombreResponsable != null && ! nombreResponsable.isEmpty() ? 
						nombreResponsable : bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
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

				list.add(tramitesAsignados);
			}
		}
		page.setData(list);
		page.setCurrentPage(dataPage.getCurrentPage());
		page.setTotalOfRecords(dataPage.getTotalOfRecords());
		page.setPageSize(dataPage.getPageSize());

		return page;
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
			} catch (ClienteWebserviceResponsablesSubdelegacionException e) {
				LOGGER.error("ClienteWebserviceResponsablesSubdelegacionException {} ",e);
			}
			funcionarios.put(curp, nombreCompleto);
			LOGGER.debug("---CDA--- Se agrega funcionario en cache {}", curp);
		}
		return nombreCompleto;
	}
	
	private void agregarObservaciones(TramitesReportes tramitesAsignados,EstadoTramite estado, long idTramite){
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
	
}
