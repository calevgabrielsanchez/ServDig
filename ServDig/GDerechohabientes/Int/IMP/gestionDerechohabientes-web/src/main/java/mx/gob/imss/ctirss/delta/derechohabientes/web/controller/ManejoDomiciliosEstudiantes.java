package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CatalogosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EstudiantesServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/estudiantes/")
public class ManejoDomiciliosEstudiantes extends AbstractController {
	
	private final String KEY_INTEGRANTE_ASEGURADO = "miGrupoFamiliar";
	private final String KEY_ASIGNACION_NSS = "AsignacionNSS";
	private final String VIEW_ASIGNACION = "asignacionDomicilioEstudiante";
	private final String VIEW_RESULTADO = "finalizaAsignacionDomicilio";
	
	@Autowired
	private EstudiantesServiceRemote estudiantesServiceRemote;
	@Autowired
	private CatalogosServiceRemote catalogosServiceRemote;
	
	@RequestMapping(value = "/test/")
	public String test(HttpSession session, Model model) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(KEY_ASIGNACION_NSS);
		GrupoFamiliar estudiante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_ASEGURADO);
		
		model.addAttribute("tramiteCorreccion", new TramiteCorreccionDerechohabiente());
		
		return VIEW_ASIGNACION;
	}
	
	@RequestMapping(value = "/asignacion/domicilio/finalizar", method = RequestMethod.POST)
	public String guardaAsignacion(@ModelAttribute TramiteCorreccionDerechohabiente tramiteCorreccion, Model model, HttpSession session, HttpServletRequest request) {
		
		Map<String, Object> result = null;
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(KEY_ASIGNACION_NSS);
		GrupoFamiliar estudiante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_ASEGURADO);
		Boolean registrado = estudiante.getIndRegistrado().equals(1);
		CabezaGrupoFamiliar cabezaGrupo = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		asignacionNSS.setEstudiante(cabezaGrupo.getEsEstudiante());
		List<Modalidad> modalidades = this.obtenerModalidadesPatrones(session);
		
		if(!asignacionNSS.isEstudiante()) {
			request.setAttribute("exception", "Error");
			request.setAttribute("error", "Los datos no corresponden con un estudiante");
			return "internalError";
		}
		tramiteCorreccion.setUsuario(usuario);
		tramiteCorreccion.setPersona(asignacionNSS);
		tramiteCorreccion.setIdAsignacionNss(asignacionNSS.getIdAsignacionNSS());
		
		if(tramiteCorreccion.getTipoTramite() == null || tramiteCorreccion.getTipoTramite().getIdTipoTramite() == null) {
			TipoTramite tipoTramite = catalogosServiceRemote.getCatalogoTipoTramite(TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH.getCodigo().longValue());
			tramiteCorreccion.setTipoTramite(tipoTramite);
		}
		
		try {
			result  = estudiantesServiceRemote.finalizaAsignacionDomicilioUmfEstudiante(tramiteCorreccion, estudiante, cabezaGrupo, modalidades);
			
			estudiante = (GrupoFamiliar) result.get("integrante");
			estudiante.setIndRegistrado(1);
			session.setAttribute(KEY_INTEGRANTE_ASEGURADO, estudiante);
			Solicitud solicitud = (Solicitud) result.get("solicitud");
			
			model.addAttribute("cambioClinica", registrado);
			model.addAttribute("solicitud", solicitud);
			model.addAttribute("verDocumentos", false);
			
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			request.setAttribute("exception", e.getMessage());
			request.setAttribute("error", e.getSituacion());
			return "internalError";
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
			request.setAttribute("exception", e.getMessage());
			request.setAttribute("error", e.getSituacion());
			return "internalError";
		}
		
		return VIEW_RESULTADO;
	}
	
	@RequestMapping(value = "/clinica/cambio")
	public String inicioCambioClinica(HttpSession session, Model model, HttpServletRequest request) {
		
		GrupoFamiliar estudiante = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE_ASEGURADO);
		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(KEY_ASIGNACION_NSS);
		CabezaGrupoFamiliar cabezaGrupo = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		asignacionNSS.setEstudiante(cabezaGrupo.getEsEstudiante());
		TramiteCorreccionDerechohabiente datosActuales = TramiteUtil.convetirGrupoCorreccion(estudiante);
		
		TipoTramite tipoTramite = catalogosServiceRemote.getCatalogoTipoTramite(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());
		datosActuales.setTipoTramite(tipoTramite);
		
		if(!asignacionNSS.isEstudiante()) {
			request.setAttribute("exception", "Error");
			request.setAttribute("error", "Los datos no corresponden con un estudiante");
			return "internalError";
		}
		
		model.addAttribute("tramiteCorreccion", datosActuales);
		model.addAttribute("cambioClinica", true);
		
		return VIEW_ASIGNACION;
	}

	private List<Modalidad> obtenerModalidadesPatrones(HttpSession session) {
		List<SujetoObligado> sujetos = (List<SujetoObligado>) session.getAttribute("patrones");
		CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		List<Modalidad> modalidades = new ArrayList<Modalidad>();
		
		if(sujetos != null && !sujetos.isEmpty()) {
			for(SujetoObligado sujeto: sujetos) {
				modalidades.add(sujeto.getModalidad());
			}
		} else {
			modalidades.add(cabeza.getPatronSujetoObligado().getModalidad());
		}
		
		return modalidades;
	}
}
