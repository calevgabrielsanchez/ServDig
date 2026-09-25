package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.AcuerdoDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ImpresionReporteDto;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAcuerdoDh;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/tramite/registroAcuerdo")
public class AcuerdoDerechohabienteController extends AbstractController {

	@Autowired
	private AcuerdoDerechohabienteServiceRemote acuerdoServiceRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	
	//redirects
	private final String REDIRECT_PRINCIPAL = "/tramite/registroAcuerdo";
	private final String REDIRECT_CANDIDATOS = "/tramite/registroAcuerdo/candidatos";
	private final String REDIRECT_CAPTURA = "/tramite/registroAcuerdo/captura";
	private final String REDIRECT_FINALIZADA = "/solicitud/finalizada";
	private final String REDIRECT_ERROR_FIN = "/solicitud/errorFinalizado";
	
	//vistas
	private final String VIEW_CANDIDATOS = "candidatosAcuerdoPadres";
	private final String VIEW_CAPTURA = "capturaAcuerdo";
	
	//objetos de session
	private final String KEY_CANDIDATOS = "candidatos";
	private final String KEY_CANDIDATO = "hijo";
	
	@RequestMapping(value = {"","/"})
	public Object findCandidatosAcuerdo(Model model, HttpSession session) {
		
		session.removeAttribute(KEY_CANDIDATOS);
		session.removeAttribute(KEY_CANDIDATO);
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		List<GrupoFamiliar> candidatos = null;
		
		try {
			candidatos = acuerdoServiceRemote.findCandidatosAcuerdo(asignacionNSS.getIdAsignacionNSS());
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		}
		
		session.setAttribute(KEY_CANDIDATOS, candidatos);
		return new RedirectView(REDIRECT_CANDIDATOS, true);
	}
	
	@RequestMapping("/candidatos")
	public Object muestraCandidatosAcuerdo(Model model, HttpSession session) {
		
		return VIEW_CANDIDATOS;
	}
	
	@RequestMapping(value = "/iniciar", method = RequestMethod.POST)
	public Object iniciarTramite(Model model, HttpSession session, @ModelAttribute GrupoFamiliar integrante) {
		
		session.removeAttribute(KEY_CANDIDATOS);
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
	
		try {
			integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(asignacionNSS.getIdAsignacionNSS(), integrante.getDerechohabiente().getIdPersona());
		} catch (Exception e) {
			log.error("error al consultar al derechohabiente con vigencia" , e);
		}
		
		session.setAttribute(KEY_CANDIDATO, integrante);
			
		return new RedirectView(REDIRECT_CAPTURA,true);
	}
	
	@RequestMapping("/captura")
	public Object capturaDatosAcuerdo(Model model, HttpSession session) {
		
		return validaSession(KEY_CANDIDATO, session, VIEW_CAPTURA, REDIRECT_PRINCIPAL);
	}
	
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public Object finalizaSolicitud(@ModelAttribute TramiteAcuerdoDh tramite,Model model, HttpSession session) {
		
		GrupoFamiliar candidato = (GrupoFamiliar) session.getAttribute(KEY_CANDIDATO);
		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		Solicitud solicitud = null;
		
		try {
			tramite.setIdAsignacionNSS(asignacionNSS.getIdAsignacionNSS());
			solicitud = acuerdoServiceRemote.crearSolicitudAcuerdo(candidato,tramite, usuario, OrigenSolicitudEnum.VENTANILLA);
			tramite = (TramiteAcuerdoDh) solicitud.getTramites().get(0);
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			acuerdoServiceRemote.finalizarSolicitudAcuerdo(solicitud, asignacionNSS);
		} catch (SolicitudNoValidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolicitudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ImpresionReporteDto reporte = new ImpresionReporteDto();

		tramite.setIdAsignacionNSS(asignacionNSS.getIdAsignacionNSS());
		tramite.setPersona(candidato.getDerechohabiente());
		
		reporte.setIdPersona(tramite.getPersona().getIdPersona());
		reporte.setIdTramite(tramite.getTramiteId());
		reporte.setRechazado(false);
		reporte.setTipoTramite(tramite.getTipoTramite());
		
		session.setAttribute("reporte", reporte);
		session.setAttribute("solicitud", solicitud);
		
		return new RedirectView(REDIRECT_FINALIZADA, true);
	}
	
	private Object validaSession(String code, HttpSession session, Object vista, String redirect ) {
		
		return session.getAttribute(code) != null ? vista : new RedirectView(redirect,true);
		
	}
}
