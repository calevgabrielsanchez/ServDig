package mx.gob.imss.cit.cda.web.app.autorizador.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.AutorizarSolicitud;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.utils.CorreosUtils;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;

@Component(BeansConstants.AUTORIZAR_SOLICITUD_HELPER)
public class AutorizarSolicitudHelper implements UpdateHelper<AutorizarSolicitud, AutorizarSolicitud> {

	private final Logger log = LoggerFactory.getLogger(AutorizarSolicitudHelper.class);
	
	@Autowired
	private RegistroSolicitudCorreccionDatosAseguradoRemote registroBusiness;
	
	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;
	
	@Autowired
	@Qualifier("envioCorreoElectronicoBusiness")
	private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;
	
	@Autowired
	private CorreosUtils correosUtils;
	
	@Autowired
	@Qualifier("componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	@Autowired
	@Qualifier("responsablesDelegacionBusiness")
	private ResponsablesDelegacionRemote responsablesDelegacionBusiness;

	
	@Override
	public UpdatedEvent<AutorizarSolicitud> requestEvent(UpdateEvent<AutorizarSolicitud> requestUpdateEvent) {
		
		try{ 
			Solicitud sol = solicitudBusiness.consultarPorIdTramite(Long.parseLong(requestUpdateEvent.getData().getIdTramite()));
			//Se actualiza debido a que no lo regresa el sistema
			sol.getSolicitante().setCveIdUsuario(requestUpdateEvent.getData().getCurpResponsable());
			log.debug("---CDA--- Responsable {}",sol.getSolicitante().getCveIdUsuario());
			registroBusiness.enviaCertificacionSINDO(sol,requestUpdateEvent.getData().getIdTarea(),requestUpdateEvent.getUserProfile().getUsuario());
			mx.gob.imss.ctirss.delta.model.Usuario responsable  = responsablesDelegacionBusiness.recuperaUsuarioEsquemaSeguridadByCURP((sol.getSolicitante().getUsuario()).toString());
			if(responsable.getFisica().getCorreoElectronico()!=null && responsable.getFisica().getCorreoElectronico().getCorreo() != null){
				envioCorreoElectronicoBusinessRemote.enviarCorreo(correosUtils.crearCorreoElectronicoDTOAutorizacion(requestUpdateEvent.getData(),responsable),EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
			}
			return new UpdatedEvent<AutorizarSolicitud>(requestUpdateEvent.getKey(), requestUpdateEvent.getData());
		} catch (ClienteWebserviceResponsablesSubdelegacionException e) {
			// TODO Auto-generated catch block
			log.debug("---CDA--- Error ClienteWebserviceResponsablesSubdelegacionException {}",e);
		} catch(Exception e){      
			log.error("---------------------Error al concluirSolicitud---------------------{}", e);
	    } 
		return UpdatedEvent.notUpdated(requestUpdateEvent.getKey());
	}

}
