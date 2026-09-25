package mx.gob.imss.cit.cda.web.app.common.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoNotificacionEnum;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.utils.CorreosUtils;
import mx.gob.imss.cit.cda.web.utils.SolicitudUpdateUtils;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

@Component(BeansConstants.RECHAZAR_SOLICITUD_HELPER)
public class RechazarSolicitudHelper implements UpdateHelper<SeguimientoSolicitud, SeguimientoSolicitud> {

	@Autowired
	@Qualifier("responsableTareaBusiness")
	private ResponsableTareaRemote responsableTareaBusiness;

	@Autowired
	private SolicitudUpdateUtils solicitudUpdateUtils;
	
	@Autowired
	private CorreosUtils correosUtils;
	
	@Autowired
	@Qualifier("envioCorreoElectronicoBusiness")
	private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;
	
	@Autowired
	@Qualifier("personaBusiness")
	private  PersonaBusinessRemote personaBusinessRemote;
	
	@Autowired
	@Qualifier("mediosContactoServiceBusiness")
	MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	
	@Autowired
	@Qualifier("componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	@Autowired
	@Qualifier("responsablesDelegacionBusiness")
	private ResponsablesDelegacionRemote responsablesDelegacionBusiness;
	
	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;

	private final Logger log = LoggerFactory.getLogger(RechazarSolicitudHelper.class);

	@SuppressWarnings("unchecked")
	@Override
	public UpdatedEvent<SeguimientoSolicitud> requestEvent(UpdateEvent<SeguimientoSolicitud> requestUpdateEvent) {
		try {
			log.debug("---CDA--- Rechazar idPersona [{}] idTarea [{}]", requestUpdateEvent.getUserProfile().getIdPersona(),
					requestUpdateEvent.getData().getIdTarea());	
			 Solicitud solicitud = solicitudBusiness.consultarPorIdTramite(Long.parseLong(requestUpdateEvent.getData().getIdTramite()));
			 
			//Persona que cancelara la solicitud de seguimiento
			  Fisica fisica = new Fisica(requestUpdateEvent.getUserProfile().getIdPersona());
			  fisica.setCurp(requestUpdateEvent.getUserProfile().getUsuario());
			responsableTareaBusiness.rechazarSolicitud(
					solicitudUpdateUtils.rechazarSolicitud(solicitud, requestUpdateEvent.getData(),
							fisica),
					requestUpdateEvent.getData().getIdTarea(),requestUpdateEvent.getUserProfile().getUsuario());
			log.debug("---CDA--- CURPResponsable: " + requestUpdateEvent.getData().getResponsable());
			mx.gob.imss.ctirss.delta.model.Usuario responsable  = responsablesDelegacionBusiness.recuperaUsuarioEsquemaSeguridadByCURP(requestUpdateEvent.getData().getResponsable());
			if (responsable.getFisica().getCorreoElectronico() != null && responsable.getFisica().getCorreoElectronico().getCorreo() != null){
				envioCorreoElectronicoBusinessRemote.enviarCorreo(correosUtils.crearCorreoElectronicoDTO(requestUpdateEvent.getData(),TipoNotificacionEnum.RECHAZO, responsable),EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
			}
			log.debug("---CDA--- Rechazo de la solicitud por ventanilla ");
			return new UpdatedEvent<SeguimientoSolicitud>(requestUpdateEvent.getKey(), requestUpdateEvent.getData());
		} catch (ClienteWebserviceResponsablesSubdelegacionException e) {
			// TODO Auto-generated catch block
			log.debug("---CDA--- Error ClienteWebserviceResponsablesSubdelegacionException {}",e);
		} catch (Exception e) {
			log.error("---------------------CDA Error al rechazar---------------------{}", e);
		}

		return UpdatedEvent.notUpdated(requestUpdateEvent.getKey());
	}

}
