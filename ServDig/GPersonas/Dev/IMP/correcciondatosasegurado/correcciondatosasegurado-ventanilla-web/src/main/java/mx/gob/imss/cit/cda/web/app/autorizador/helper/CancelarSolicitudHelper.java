/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.autorizador.helper;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoNotificacionEnum;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.utils.CancelarSolicitudUtils;
import mx.gob.imss.cit.cda.web.utils.CorreosUtils;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
/**
 *
 * @author antonio
 */
@Component(BeansConstants.CANCELAR_SOLICITUD_AUTORIZACION_HELPER)
public class CancelarSolicitudHelper implements UpdateHelper<SeguimientoSolicitud, SeguimientoSolicitud> {

	@Autowired
	@Qualifier("responsableTareaBusiness")
	private ResponsableTareaRemote responsableTareaBusiness;

	@Autowired
	private CancelarSolicitudUtils cancelarSolicitudUtils;
	
	@Autowired
	private CorreosUtils correosUtils;
	
	@Autowired
	@Qualifier("envioCorreoElectronicoBusiness")
	private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;
	
	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;
	
	private  final Logger log = LoggerFactory.getLogger(CancelarSolicitudHelper.class);

	@SuppressWarnings("unchecked")
	@Override
  public UpdatedEvent<SeguimientoSolicitud> requestEvent(UpdateEvent<SeguimientoSolicitud> requestUpdateEvent) {
	  try{
		  log.debug("CDA Cancelar idPersona [{}] idTarea [{}]", requestUpdateEvent.getUserProfile().getIdPersona(),
					requestUpdateEvent.getData().getIdTarea());
		  log.debug("Solicitud {}",requestUpdateEvent.getData().getIdSolicitud());
		  //Persona que cancelara la solicitud de seguimiento
		  Fisica fisica = new Fisica(requestUpdateEvent.getUserProfile().getIdPersona());
		  fisica.setCurp(requestUpdateEvent.getUserProfile().getUsuario());
		  Solicitud solicitud = solicitudBusiness.consultarPorIdTramite(Long.parseLong(requestUpdateEvent.getData().getIdTramite()));
		  responsableTareaBusiness.cancelarTareaAutorizador(cancelarSolicitudUtils.crearSolicitudCancelacion(solicitud, requestUpdateEvent.getData(),fisica), requestUpdateEvent.getData().getIdTarea(),requestUpdateEvent.getUserProfile().getUsuario());
		  if (requestUpdateEvent.getData().getInformacionRENAPO().getCorreoElectronico()!=null){
			  envioCorreoElectronicoBusinessRemote.enviarCorreo(correosUtils.crearCorreoElectronicoDTO(requestUpdateEvent.getData(),TipoNotificacionEnum.CANCELACION, null),EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
		  }
		  log.debug("---------------------Cancelacion Exitosa---------------------");
		  return new UpdatedEvent<SeguimientoSolicitud>(requestUpdateEvent.getKey(), requestUpdateEvent.getData());
	  }catch(Exception e){
		  log.error("---------------------Error al cancelar---------------------{}", e);
	  }
    
	  return UpdatedEvent.notUpdated(requestUpdateEvent.getKey());
  }
    
}
