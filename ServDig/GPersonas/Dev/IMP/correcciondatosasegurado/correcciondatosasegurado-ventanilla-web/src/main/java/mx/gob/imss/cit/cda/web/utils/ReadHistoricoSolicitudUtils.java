package mx.gob.imss.cit.cda.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.common.helper.ReadHistoricoSolicitudesHelper;
import mx.gob.imss.cit.cda.web.app.responsable.model.HistoricoSolicitudes;
import mx.gob.imss.cit.cda.web.constants.FlujoTrabajoConstants;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionSolicitudCDAEnum;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReadHistoricoSolicitudUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReadHistoricoSolicitudesHelper.class);
	
	@Autowired
	@Qualifier("componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	@Autowired
	@Qualifier("responsablesDelegacionBusiness")
	private ResponsablesDelegacionRemote responsablesDelegacionBusiness;

	@SuppressWarnings("unchecked")
	public Page<HistoricoSolicitudes> convertirAmodelo(DataPage dataPage, String usuario) {
		Page<HistoricoSolicitudes> page = new Page<HistoricoSolicitudes>();
		List<HistoricoSolicitudes> list = new ArrayList<HistoricoSolicitudes>();
		Map<String, String> funcionarios = new HashMap<String, String>();

		for (TareaBandeja bandeja : (Collection<TareaBandeja>) dataPage.getData()) {
			
			String nombreResponsable = "";
			
			if(StringUtils.isNotBlank(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()))){
				nombreResponsable = this.nombrePersona(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()), funcionarios);
				LOGGER.debug("---CDA--- Responsable a buscar por curp {} ",bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
				LOGGER.debug("---CDA--- Nombre obtenido{} ", nombreResponsable);	
			}
			
			HistoricoSolicitudes historico = procesarTareasBandeja(bandeja, usuario, nombreResponsable, funcionarios);
			
			historico.setNombreCompletoResponsable(nombreResponsable != null && !nombreResponsable.isEmpty() ? 
					nombreResponsable : bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
			if( bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()) != null && (bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()).contains(FlujoTrabajoConstants.BPM_ADMIN) 					
					||bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()).equals(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()))) ) {
				historico.setAutorizo("");
				historico.setNombreCompletoAutorizo("");
			} else {
				historico.setAutorizo(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()));
				String nombreAutorizador ="";
				if(StringUtils.isNotBlank(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()))){
					nombreAutorizador=this.nombrePersona(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()),funcionarios);
					LOGGER.debug("---CDA--- Autorizador a buscar {} ",bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()));
					LOGGER.debug("---CDA--- Nombre encontrado {} ",nombreAutorizador);
				}
				
				historico.setNombreCompletoAutorizo(nombreAutorizador != null && !nombreAutorizador.isEmpty() ? 
						nombreAutorizador : bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()));
				
			}
			
			if(bandeja.getInicioTramite() != null && StringUtils.isNotBlank(bandeja.getInicioTramite().getData())) {
				Map<String, Object> datos = WorkFlowDataUtil.generarJavaDataWf(bandeja.getInicioTramite().getData());
				LOGGER.info("---CDA--- Mapa {}",datos);
				LOGGER.info("tipo Regularizacion {}", datos.get("tipoRegularizacion"));
				historico.setTipo(datos.get("tipoRegularizacion")!= null ?TipoRegularizacionSolicitudCDAEnum.fromId(((Integer)datos.get("tipoRegularizacion"))).getDescripcion():"SIN TIPO");
			} else {
				historico.setTipo("SIN TIPO");
			}
			
			list.add(historico);
		}
		
		page.setData(list);
		page.setCurrentPage(dataPage.getCurrentPage());
		page.setTotalOfRecords(dataPage.getTotalOfRecords());
		page.setPageSize(dataPage.getPageSize());
		
		return page;
	}
	
	private HistoricoSolicitudes procesarTareasBandeja(TareaBandeja bandeja, String usuario, String nombreResponsable, Map<String, String> funcionarios) {
			
		HistoricoSolicitudes historico = new HistoricoSolicitudes();
		
		historico.setEsPropietario(usuario.equalsIgnoreCase(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion())) ||
				usuario.equalsIgnoreCase(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion())));
		historico.setIdTramite(bandeja.getIdTramite().toString());
		historico.setFolio(bandeja.getInicioTramite().getFolio());
		
		historico.setFechaSolicitud(formateaFecha(bandeja.getInicioTramite().getFechaSolicitud()));
		
		historico.setEstatus(StringUtils.isNotBlank(bandeja.getInicioTramite().getEstatus())? bandeja.getInicioTramite().getEstatus().toUpperCase() : "");
		
		if (!bandeja.getInicioTramite().getData().equals("")) {
			Map<String, Object> datos = WorkFlowDataUtil.generarJavaDataWf(bandeja.getInicioTramite().getData());
			historico.setNssInvolucrados((String)datos.get("nssInvolucrados"));
			historico.setOrigen((String)datos.get("origen"));
		}
		
		historico.setUltimaActualizacion(formateaFecha(bandeja.getInicioTramite().getFechaActualizacion()));
		historico.setResponsable(
				bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
		
		return historico;
	}
	
	private String formateaFecha(String fechaAFormatear) {
		String fechaFormateada = null;
		try {
			fechaFormateada = new DeltaUtils().cambiarFormatoFecha("dd/MM/yyyy", fechaAFormatear, "dd/MM/yyyy");
		} catch (ParseException e1) {
			LOGGER.error("-- CDA: Error Fecha Solicitud. ", e1);
		}
		
		return fechaFormateada;
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
				LOGGER.error("---CDA--- ErrorClienteWebserviceResponsablesSubdelegacionException {} ",e);
			}
			funcionarios.put(curp, nombreCompleto);
			LOGGER.debug("---CDA--- Se agrega funcionario en cache {}", curp);
		}
		return nombreCompleto;
	}
}