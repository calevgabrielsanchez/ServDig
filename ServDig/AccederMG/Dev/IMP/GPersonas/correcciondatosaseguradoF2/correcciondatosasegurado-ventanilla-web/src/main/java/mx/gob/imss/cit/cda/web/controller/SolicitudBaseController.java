package mx.gob.imss.cit.cda.web.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum.Dependencia;
import mx.gob.imss.cit.cda.web.vo.DomicilioCorto;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Clase que contiene los metodos comunes para el registro de solicitud
 * 
 * @author erik.ramirez
 *
 */
public class SolicitudBaseController extends AbstractController {

    private final Logger logger = LoggerFactory
            .getLogger(SolicitudBaseController.class);
    
    
   

    /**
     *  Obtiene el usuario de sesion
     *     
     * @param HttpSession datos de sesion 
     * @return Datos del funcionario almacenado en sesion
     */
    protected UserProfile getUsuarioEnSesion(HttpSession session) {
        log.info("Recuperando de la sesion la informacion del usuario");

        return (UserProfile) session
                .getAttribute(SessionConstants.USER_PROFILE);
    }

   
    
    @ModelAttribute("sol")
    public Solicitud getSolicitud() {
        return new Solicitud();
    }

    @ModelAttribute("personaCorreccion")
    public Fisica getFisica() {
        return new Fisica();
    }

    
    @RequestMapping(value = "/comunes/limpiarDatos")
    @ResponseBody 
    public Map<String, ? extends Object> comunLimpiarDatos(
            Model model, HttpSession session) {

        model.addAttribute(SessionConstants.ATTR_TRAMITE_SEGURO, "");

        session.removeAttribute(SessionConstants.ATTR_TRAMITE_SEGURO);
        session.removeAttribute(SessionConstants.ATTR_SOLICITUD);
        session.removeAttribute(SessionConstants.ATTR_PERSONA_KEY);
        session.removeAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION);
        session.removeAttribute(SessionConstants.ATTR_CONTINUACION_TRAMITE);

        this.log.info("---CDA--- Datos limpiados de la sesion: OK");
        return null;
    }
    
    
//    @RequestMapping(value = "/folioTramite", method = RequestMethod.GET)
//    public String obtenerFolioTramite(Model model, HttpSession session) {
//        this.log.info("folioTramite");
//        DatosFolioTramiteVO folio = new DatosFolioTramiteVO();
//        model.addAttribute("folio", folio);
//        return RequestMappingConstants.VIEW_FOLIO_TRAMITE;
//    }
    
    /**
     * 
     * 
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/capturarDomicilio", method = RequestMethod.GET)
    public String capturaDomicilioAclaracion(Model model, HttpSession session) {
        DomicilioCorto domicilioAclaracion;
        if (session.getAttribute("domicilioAclaracion") != null) {
            domicilioAclaracion = (DomicilioCorto) session
                    .getAttribute("domicilioAclaracion");
        } else {
            domicilioAclaracion = new DomicilioCorto();
        }
        model.addAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION,
                domicilioAclaracion);
        model.addAttribute(SessionConstants.ATTR_MOTIVOS_ACLARACION_IMSS, TiposAclaracionEnum
                .obtenerMotivosPorDependencia(Dependencia.IMSS));
        model.addAttribute(SessionConstants.ATTR_MOTIVOS_ACLARACION_INFONAVIT, TiposAclaracionEnum
                .obtenerMotivosPorDependencia(Dependencia.INFONAVIT));
        model.addAttribute(SessionConstants.ATTR_MOTIVOS_ACLARACION_AFORE, TiposAclaracionEnum
                .obtenerMotivosPorDependencia(Dependencia.AFORE));
        return RequestMappingConstants.VIEW_CAPTURAR_DOMICILIO;
    }
    
    public String removerDomicilioAclaracion(Model model, HttpSession session) {
        DomicilioCorto
            domicilioAclaracion = new DomicilioCorto();        
        model.addAttribute(SessionConstants.ATTR_DOMICILIO_ACLARACION,
                domicilioAclaracion);
        model.addAttribute(SessionConstants.ATTR_MOTIVOS_ACLARACION_IMSS, TiposAclaracionEnum
                .obtenerMotivosPorDependencia(Dependencia.IMSS));
        model.addAttribute(SessionConstants.ATTR_MOTIVOS_ACLARACION_INFONAVIT, TiposAclaracionEnum
                .obtenerMotivosPorDependencia(Dependencia.INFONAVIT));
        model.addAttribute(SessionConstants.ATTR_MOTIVOS_ACLARACION_AFORE, TiposAclaracionEnum
                .obtenerMotivosPorDependencia(Dependencia.AFORE));
        return RequestMappingConstants.VIEW_CAPTURAR_DOMICILIO;
    }
    
    
    
   protected boolean validarSeguimientoTramite(Solicitud sol) {
        boolean solicitudEstatusEnRegistro = sol.getEstadoSolicitud()
                .getIdEstadoSolicitud()
                .equals(EstadoSolicitudEnum.REGISTRADA.getCodigo());
        boolean tramiteEstatusIniciado = sol.getTramites().get(0)
                .getEstadoTramite().getIdEstadoTramitePersona()
                .equals(EstadoTramiteEnum.INICIADO.getCodigo());
        return solicitudEstatusEnRegistro && tramiteEstatusIniciado;
    }

    protected boolean validarSeguimientoTramiteAtendido(Solicitud sol) {
        boolean solicitudEstatusEnRegistro = sol.getEstadoSolicitud()
                .getIdEstadoSolicitud()
                .equals(EstadoSolicitudEnum.ATENDIDA.getCodigo());
        logger.debug("---CDA--- ORIGEN {}   DESCRIPCION {}", sol
                .getOrigenSolicitud().getIdOrigenSolicitud(), sol
                .getOrigenSolicitud().getDescripcion());
        if (sol.getOrigenSolicitud().getIdOrigenSolicitud()
                .equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())
                && solicitudEstatusEnRegistro) {
            return solicitudEstatusEnRegistro;
        } else
            return false;
    }
    
	protected boolean validarSeguimientoTramiteCancelado(Solicitud sol) {
		boolean solicitudEstatusEnRegistro = false;
		logger.info("-----CDA----- ESTADO {}   DESCRIPCION {}", sol
				.getEstadoSolicitud().getIdEstadoSolicitud(), sol
				.getEstadoSolicitud().getDescripcion());
		logger.info("-----CDA----- ORIGEN {}   DESCRIPCION {}", sol
				.getOrigenSolicitud().getIdOrigenSolicitud(), sol
				.getOrigenSolicitud().getDescripcion());
		if ((sol.getEstadoSolicitud().getIdEstadoSolicitud()
				.equals(EstadoSolicitudEnum.CANCELADA.getCodigo())
				|| sol.getEstadoSolicitud().getIdEstadoSolicitud()
						.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo()))
				&& sol.getOrigenSolicitud().getIdOrigenSolicitud()
						.equals(OrigenSolicitudEnum.VENTANILLA.getId())) {
			solicitudEstatusEnRegistro = true;
		}
		return solicitudEstatusEnRegistro;
	}


}
