/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.helper;

import java.util.List;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;
import mx.gob.imss.cit.cda.web.app.responsable.utils.ConfirmarSolicitudUtil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.enums.OrigenConsultaNssEnum;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;

import org.apache.commons.lang.StringEscapeUtils;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author antonio
 */
@Component(BeansConstants.CONFIRMAR_SOLICITUD_HELPER)
public class ConfirmarSolicitudHelper implements UpdateHelper<Solicitud, Solicitud>{
	
  @Autowired
  @Qualifier("responsableTareaBusiness")
  private ResponsableTareaRemote responsableTareaBusiness;
  
  @Autowired
  @Qualifier("responsablesDelegacionBusiness")
  private ResponsablesDelegacionRemote responsablesDelegacionRemote;
  
  @Autowired
  private ConfirmarSolicitudUtil confirmarSolicitudUtil;
  
  @Autowired
  @Qualifier("envioCorreoElectronicoBusiness")
  private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;

	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	
	@Autowired
	@Qualifier("solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;
	
  private static final Logger logger = LoggerFactory.getLogger(ConfirmarSolicitudHelper.class);
  

  @SuppressWarnings("unchecked")
  @Override
  public UpdatedEvent<Solicitud> requestEvent(UpdateEvent<Solicitud> requestUpdateEvent) {	  
	  logger.debug("---CDA Ventanilla--- Confirmando los datos de la Solicitud id {} Tramite id {} Tarea id {}",
			  new Object [] { requestUpdateEvent.getData().getId(),
			  requestUpdateEvent.getData().getIdTramite(),
			  requestUpdateEvent.getData().getIdTarea()});
	  logger.debug("---CDA Ventanilla--- SolicitudgetTipoRegularizacion: {}", requestUpdateEvent.getData().getTipoRegularizacion().getTipoRegularizacionId());
	  logger.debug("---CDA Ventanilla--- Solicitud.getTotalOfRecords: {}", requestUpdateEvent.getData().getGridNSS().getTotalOfRecords());
	  logger.debug("---CDA Ventanilla--- Solicitud.getNss: {}", requestUpdateEvent.getData().getGridNSS().getData().get(0).getNss());
	  logger.debug("---CDA Ventanilla--- Solicitud.getTipoCorreccion: {}", requestUpdateEvent.getData().getGridNSS().getData().get(0).getTipoCorreccion());
	  try {
		  mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol = confirmarSolicitudUtil.crearSolicitudConfirmacionDatos(
					requestUpdateEvent);
		  responsableTareaBusiness.avanzarTareaResponsable(sol, requestUpdateEvent.getData().getIdTarea());
		 //Mandar correo a autorizadores
		  List<Fisica> personasFuenteNSS = null;
		  personasFuenteNSS =serviceBusiness.getAseguradoByNSSLegadosyBDTU(requestUpdateEvent.getData().getGridNSS().getData().get(0).getNss(),true);
		  CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
		  //Origen movimiento
		  String origenMovimiento =null;
		  for (Fisica personaNSS : personasFuenteNSS) {
			for (Identificador identificador : personaNSS.getIdentificadores()) {
				OrigenConsultaNssEnum enumOrigen = OrigenConsultaNssEnum
						.obtenerEnumById(Long.valueOf(identificador.getIdIdentificador()).intValue());
				origenMovimiento= enumOrigen.getDescripcion();
				}
			}
	  		logger.debug("---CDA Ventanilla--- EL MOVIMIENTO ES: {}", origenMovimiento);
		  //Obtuvo origen
			  try{
				 sol = solicitudBusiness.consultarPorIdTramite(Long.parseLong(requestUpdateEvent.getData().getIdTramite()));
					List<Fisica> autorizadores = responsablesDelegacionRemote.consultarAutorizadoresDelegacion(requestUpdateEvent.getUserProfile().getIdDelegacion().intValue(), requestUpdateEvent.getUserProfile().getIdSubdelegacion().intValue());
					for(Fisica autorizador:autorizadores){
						correoElectronicoDTO = new CorreoElectronicoDTO();
						
						if (autorizador.getCorreoElectronico() != null && autorizador.getCorreoElectronico().getCorreo() != null){
							correoElectronicoDTO.setCorreoPara(new String[1]);
							correoElectronicoDTO.getCorreoPara()[0] = autorizador.getCorreoElectronico().getCorreo();
							correoElectronicoDTO.setAsunto(StringEscapeUtils.unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
							correoElectronicoDTO.setCuerpoCorreo(confirmarSolicitudUtil.contenidoCorreoAtencionAutorizador(sol,autorizador.getNombreCompleto(),origenMovimiento));
							logger.debug("---CDA--- PARA: {}, CORREO AUTORIZADOR: {}", autorizador.getCorreoElectronico().getCorreo(), correoElectronicoDTO.getCuerpoCorreo());
							try {
								envioCorreoElectronicoBusinessRemote.enviarCorreo(correoElectronicoDTO, EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
							} catch (Exception e) {
								logger.error("---CDA--- Error al enviar correo Autorizador" , e);
							}
						}
					}
				}catch (ClienteWebserviceResponsablesSubdelegacionException e) {
					logger.error("---CDA--- Error al obtener autorizadores" , e);				
				}
			
		return new UpdatedEvent<Solicitud>(requestUpdateEvent.getKey(), 
				requestUpdateEvent.getData());
	} catch (Exception e) {		
		logger.error("Hubo un error al confirmar la solicitud id -  {} tramite id - {}", 
				new Object [] { requestUpdateEvent.getData().getId(),
				requestUpdateEvent.getData().getFolio()});
		logger.error("Error {}",e);
	}		
    
    return UpdatedEvent.notUpdated(requestUpdateEvent.getKey());
  }
  
  

  
}
