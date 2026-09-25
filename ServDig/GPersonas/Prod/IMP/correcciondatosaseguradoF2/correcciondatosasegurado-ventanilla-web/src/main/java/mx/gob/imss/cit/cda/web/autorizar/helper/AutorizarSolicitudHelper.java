package mx.gob.imss.cit.cda.web.autorizar.helper;

import java.util.Arrays;
import java.util.Map;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.AutorizarSolicitudRemote;
import mx.gob.imss.cit.cda.service.interfaces.CorreccionDatosRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.autorizar.utils.AutorizarSolicitudUtils;
import mx.gob.imss.cit.cda.web.autorizar.vo.AutorizarSolicitud;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.utils.CorreosUtils;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component(BeansConstants.AUTORIZAR_SOLICITUD_HELPER)
public class AutorizarSolicitudHelper implements UpdateHelper<AutorizarSolicitud, AutorizarSolicitud> {

    private final Logger log = LoggerFactory.getLogger(AutorizarSolicitudHelper.class);

    @Autowired
    private AutorizarSolicitudRemote autorizarSolicitudBusiness;
    
    @Autowired
    private AutorizarSolicitudUtils autorizarSolicitudUtils;

    @Autowired
    private SolicitudBusinessRemote solicitudBusiness;

    @Autowired
    @Qualifier("envioCorreoElectronicoBusiness")
    private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;

    @Autowired
    @Qualifier("parametrosServiceBusiness")
    private ParametrosServiceBusinessRemote parametrosServiceBusiness;

    @Autowired
    private CorreosUtils correosUtils;

    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionBusiness;
    
    @Autowired
    @Qualifier("correccionDatosBusiness")
    private CorreccionDatosRemote correccionDatosBussines;

    @Autowired
    protected MessageSource messageSource;

    @Override
    public UpdatedEvent<AutorizarSolicitud> requestEvent(UpdateEvent<AutorizarSolicitud> requestUpdateEvent) {

        try {
            //Se agrega validacion para evitar que se autoricen tramites sin un tipo de correccion
            if (!correccionDatosBussines.validarTipoMovimientos(requestUpdateEvent.getData().getFolio())){
                requestUpdateEvent.getData().setError(messageSource.getMessage("error.tramite.sin.tipo.movimientos", new Object[] { requestUpdateEvent.getData().getFolio() }, null));
                return new UpdatedEvent<AutorizarSolicitud>(requestUpdateEvent.getKey(), requestUpdateEvent.getData());
            }

            Solicitud solicitud = solicitudBusiness.consultarPorFolioSolicitud(requestUpdateEvent.getData().getFolio());
            log.info("Procesando solicitud {" + solicitud.getNoFolioSolicitud() + "}");
            //Se agrega validacion para cambiar la solicitud a operada en homonimia o invasion cuando no se marca ningun perido para mover de un NSS a otro
            Map<String, String> tramitesTareas = getAutorizarSolicitudUtils().armarTareasTramites(requestUpdateEvent.getData().getTareasTramites());
            if (!correccionDatosBussines.validarCambioAOperada(solicitud, requestUpdateEvent.getUserProfile().getUsuario(), tramitesTareas)) {
                //Se valida si se debe aplicar un blanqueamiento de CURP
                correccionDatosBussines.validarBlanqueamientoCURP(solicitud);
                //Se actualiza la solicitud a Autorizada
                getAutorizarSolicitudBusiness().enviaCertificacionSINDO(getAutorizarSolicitudUtils()
                                .crearSolicitudAutorizacion(solicitud, requestUpdateEvent.getData(), requestUpdateEvent.getUserProfile().getUsuario()),
                        tramitesTareas, requestUpdateEvent.getUserProfile().getUsuario());
            }

            //Se agrega paso para validar si aplica la seperacion de personas (cuando los NSS involucrados pertenecen al mismo id persona)
            String[] parametros = parametrosServiceBusiness.obtenerParametroDeConfiguracion("PARAMETROS_SEPARACION_PERSONAS_CDAV2").split("\\|");
            log.info("Subdelegacion: " + requestUpdateEvent.getUserProfile().getSubdelegacion() + " ID: " + requestUpdateEvent.getUserProfile().getIdSubdelegacion());
            if (Arrays.asList(parametros).contains(requestUpdateEvent.getUserProfile().getIdSubdelegacion().toString()) || Arrays.asList(parametros).contains("ALL")) {
                log.info("Inicia validacion para la separacion de personas. Subdelegacion permitida");
                correccionDatosBussines.validarSeparacionPersonas(requestUpdateEvent.getUserProfile().getUsuario(), solicitud);
            }

            mx.gob.imss.ctirss.delta.model.Usuario responsable = responsablesDelegacionBusiness.recuperaUsuarioEsquemaSeguridadByCURP((solicitud.getSolicitante().getUsuario()));
            if (responsable.getFisica().getCorreoElectronico() != null && responsable.getFisica().getCorreoElectronico().getCorreo() != null) {
                envioCorreoElectronicoBusinessRemote.enviarCorreo(correosUtils.crearCorreoElectronicoDTOAutorizacion(requestUpdateEvent.getData(), responsable),
                        EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
            }

            return new UpdatedEvent<AutorizarSolicitud>(requestUpdateEvent.getKey(), requestUpdateEvent.getData());
            
        } catch (ClienteWebserviceResponsablesSubdelegacionException e) {
            log.debug("---CDA--- Error ClienteWebserviceResponsablesSubdelegacionException {0}", e);
        } catch (Exception e) {
            log.error("---------------------Error al concluirSolicitud---------------------{0}", e);
        }
        
        return UpdatedEvent.notUpdated(requestUpdateEvent.getKey());
    }

    public AutorizarSolicitudRemote getAutorizarSolicitudBusiness() {
        return autorizarSolicitudBusiness;
    }

    public AutorizarSolicitudUtils getAutorizarSolicitudUtils() {
        return autorizarSolicitudUtils;
    }

}
