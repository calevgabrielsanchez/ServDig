package mx.gob.imss.cit.cda.web.app.autorizador.helper;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.gob.imss.cit.cda.core.events.CreateEvent;
import mx.gob.imss.cit.cda.core.events.CreatedEvent;
import mx.gob.imss.cit.cda.core.helper.CreateHelper;
import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.service.interfaces.ManejadorReportesRemote;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.web.app.autorizador.utils.GenerarCertificacionUtil;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.CorreccionDatosAseguradoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(BeansConstants.GENERAR_CERTIFICACION_HELPER)
public class GenerarCertificacionHelper implements CreateHelper<SeguimientoSolicitud, byte[]> {
	private final Logger log = LoggerFactory.getLogger(GenerarCertificacionHelper.class);
	
	@Autowired
	private RegistroSolicitudCorreccionDatosAseguradoRemote registroBusiness;
	
	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;
	
	@Autowired
	@Qualifier("manejadorReportesBusiness")
	private ManejadorReportesRemote manejadorReportesBusiness;
	
	@Autowired 
	private GenerarCertificacionUtil generarCertificacionUtil;
	
	@Autowired
	@Qualifier("flujoTrabajoBusiness")
	private FlujoTrabajoRemote flujoTrabajoBusiness;
	
	@Autowired
	@Qualifier("cuentaIndividualBusiness")
	private CuentaIndividualRemote cuentaIndividualBusiness;
	
	@Autowired
	@Qualifier("envioCorreoElectronicoBusiness")
	private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;
	
	@Value("${url.envio.correo.seguimiento.ciudadano}")
    private String URL_ENVIO_CORREO_SEGUIMIENTO_CIUDADANO;
	
	@Autowired
	@Qualifier("bovedaBusiness")
	private BovedaRemote bovedaRemote;	
	
	@Override
	public CreatedEvent<byte[]> requestEvent(CreateEvent<SeguimientoSolicitud> requestCreateEvent) {
		log.debug("---CDA--- Generando Solicitud {}",requestCreateEvent.getData().getIdSolicitud());
		byte[] documentoCertificado = null;
		
		try {
			Solicitud sol = solicitudBusiness.consultarPorIdTramite(Long.parseLong(requestCreateEvent.getData().getIdTramite()));
			log.debug("---CDA--- Estado Solicitud {} - {}",sol.getEstadoSolicitud().getDescripcion(),sol.getEstadoSolicitud().getIdEstadoSolicitud());
			
			boolean enviaCorreoAsegurado = finalizarTramiteCDA(sol, requestCreateEvent);
			log.debug("---CDA--- ID_CERTIFICADOR {}",((TramiteCorreccionCurp)sol.getTramites().get(0)).getIdDocumentoCertificacion());
			
			
			documentoCertificado = recuperarCertificacion(sol);

			if(documentoCertificado == null){
				
				log.info("---CDA--- No se encontro el documento se procede a crear un nuevo documento Solicitud ID",sol.getSolicitudId());
			
				documentoCertificado = generarCertificacion(sol, requestCreateEvent);
				
				if(enviaCorreoAsegurado){
					log.debug("---CDA--- enviando correo de asegurado con certificacion {}", sol.getNoFolioSolicitud());
					enviarCorreoAsegurado(sol, documentoCertificado);
				}	
				
				subirDocumento(sol,documentoCertificado);
				
			}			
			return prepararRespuesta(null, documentoCertificado,requestCreateEvent);
		} catch (Exception e) {			
			return prepararRespuesta(e, documentoCertificado, requestCreateEvent);
		}
	}
	
	private boolean finalizarTramiteCDA(Solicitud sol,CreateEvent<SeguimientoSolicitud> requestCreateEvent) throws Exception{
		boolean enviaCorreoAsegurado = false;
		if(!sol.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())){
			registroBusiness.finalizaTramiteCorreccionDatosBasicosAsegurado(sol,requestCreateEvent.getData().getIdTarea(),requestCreateEvent.getUserProfile().getUsuario());
			enviaCorreoAsegurado = true;
		}		
		
		return enviaCorreoAsegurado;
	}
	
	private byte[] recuperarCertificacion(Solicitud sol){
		byte[] documentoCertificado = null;
		try{
			documentoCertificado = bovedaRemote.recuperarDocumento(sol,TipoDocumentoCDAEnum.CERTIFICADO,((TramiteCorreccionCurp)sol.getTramites().get(0)).getIdDocumentoCertificacion());
		}catch (BovedaCDAException bce){
			log.error(bce.getSituacion(),bce);
		}
		
		return documentoCertificado;
	}
	
	private byte [] generarCertificacion(Solicitud sol,CreateEvent<SeguimientoSolicitud> requestCreateEvent) throws CorreccionDatosAseguradoException, NoExisteTareaUsuarioException, NumberFormatException{
		Set<List<PeriodoMovimientoAfiliatorio>> parametrosCuentas = new LinkedHashSet<List<PeriodoMovimientoAfiliatorio>>();
		
		parametrosCuentas.add(cuentaIndividualBusiness.consultarMovimientosCuentaIndividual(((TramiteCorreccionCurp)sol.getTramites().get(0)).getPersonaRENAPO().getNss()));
		
		parametrosCuentas.add(cuentaIndividualBusiness.consultarUltimoMovimientoCuentaIndividual(((TramiteCorreccionCurp)sol.getTramites().get(0)).getPersonaRENAPO().getNss()));
		
		FirmaElectronica firma = registroBusiness.selloDigitalCertificacion(((TramiteCorreccionCurp)sol.getTramites().get(0)).getPersonaRENAPO(),sol, parametrosCuentas );
		sol.setFirmaElectronica(firma);
		Map<String , String> participantes;
		if(StringUtils.isNotBlank(requestCreateEvent.getData().getIdTarea())){
			 participantes = flujoTrabajoBusiness.obtenerParticipantesUltimaTarea(Long.parseLong(requestCreateEvent.getData().getIdTarea()));
		}else{
			participantes = flujoTrabajoBusiness.obtenerParticipantesUltimaTareaByTramite(Long.parseLong(requestCreateEvent.getData().getIdTramite()));
		}
		
		return manejadorReportesBusiness.ejecutaReporteCertificacion(generarCertificacionUtil.generarParametrosReporte(sol,participantes, parametrosCuentas));
	}
	
	private void subirDocumento (Solicitud sol,byte[] documentoCertificado) throws TramiteNoEncontradoException, IllegalArgumentException, BovedaCDAException{
		String idDocumento = bovedaRemote.subirDocumento(documentoCertificado, sol,TipoDocumentoCDAEnum.CERTIFICADO);
		if(idDocumento != null){
			solicitudBusiness.actualizarXmlTramite(agregarIdDocumento(sol, idDocumento));
		}else{
			log.warn("---CDA--- Ocurrio un error al subir el documento no regreso idDocumento");
		}
		
	}
	
	private String getCorreoElectronico(Fisica persona){
		String correoValido = "";
		
		
		if(persona.getMediosContacto() != null && !persona.getMediosContacto().isEmpty()){
			for(MedioContacto med : persona.getMediosContacto()){
				if(med instanceof CorreoElectronico){
					correoValido = med != null ?((CorreoElectronico)med).getCorreo():"";	
				}
				
			}
		}
		return correoValido;
	}
	
	
	public void enviarCorreoAsegurado(Solicitud sol, byte[] certificacion){
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)sol.getTramites().get(0);
		CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
		String url= "http://"+URL_ENVIO_CORREO_SEGUIMIENTO_CIUDADANO;
		//Enviar correo electronico al asegurado
		String correoAsegurado = getCorreoElectronico(tramite.getPersonaRENAPO()); 
		if(!correoAsegurado.isEmpty()){						
				correoElectronicoDTO.setCorreoPara(new String[1]);
				correoElectronicoDTO.getCorreoPara()[0] = correoAsegurado;
				correoElectronicoDTO.setAsunto(StringEscapeUtils.unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
				correoElectronicoDTO.setCuerpoCorreo(generarCertificacionUtil.contenidoCorreoAtendida(sol,url));
				if(certificacion != null){
					Map<String,byte[]> adjuntosMap = new HashMap<String, byte[]>();
					//TODO Cambiar nombre en la llave del mapa con el nombre del documento que sera adjuntado.
					adjuntosMap.put("Certificado.pdf", certificacion);
					correoElectronicoDTO.setAdjuntos(adjuntosMap);
				}
				
				log.debug("CORREO CERTIFICACION....... {}",correoElectronicoDTO.getCuerpoCorreo());
				try {
					envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO, EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
				} catch (Exception e) {
					log.debug("---CDA--- Error al enviar correo al asegurado" , e);
				}
		}
		//Termino de enviar correo
	}
	
	private TramiteCorreccionCurp agregarIdDocumento(Solicitud solicitud ,String idDocumento){
		
		TramiteCorreccionCurp tramiteCorreccionCurp = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
		
		tramiteCorreccionCurp.setIdDocumentoCertificacion(idDocumento);
				
		return tramiteCorreccionCurp;
		
	}
	
	private CreatedEvent<byte[]> prepararRespuesta (Exception e, byte[] documentoCertificado,CreateEvent<SeguimientoSolicitud> requestCreateEvent){
		if(documentoCertificado != null && documentoCertificado.length != 0){
			return new CreatedEvent<byte[]>(requestCreateEvent.getKey(), documentoCertificado);
		}		
		if(e instanceof BovedaCDAException){
			BovedaCDAException bce = (BovedaCDAException)e;
			log.error("---CDA--- Error {} {}",bce.getSituacion(), bce);
			return CreatedEvent.error(requestCreateEvent.getKey(), bce.getSituacion(),bce.getMensajeError());
		}else{
			log.error("---CDA---Error: {}", e);			
			log.error(e.getMessage()+" "+e.getCause().toString());
			return CreatedEvent.error(requestCreateEvent.getKey(),e.getMessage()+" "+e.getCause().toString(),MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002.getCodigo()));
		}
		
	}

}
