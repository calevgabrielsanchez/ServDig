package mx.gob.imss.distss.escritorio.virtual.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/procesando/solicitud")
public class ProcesandoSolicitudController extends AbstractController {

	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;

	@RequestMapping(value = "/{folioSolicitud}", method = RequestMethod.GET)
	public String mostrarMensajeEspera(Model model,
			@PathVariable String folioSolicitud) {
		
		this.log.debug(" FolioSolicitud en proceso: " + folioSolicitud);
		setHomoclave(null, folioSolicitud, model);
		
		return "wizardProcesandoSolicitud";
	}

	@RequestMapping(value = "/{folioSolicitud}/{idSolicitud}", method = RequestMethod.GET)
	public String mostrarMensajeEsperaSolicitud(Model model,
			@PathVariable String folioSolicitud, @PathVariable Long idSolicitud) {
		
		this.log.debug(" FolioSolicitud en proceso: " + folioSolicitud);
		setHomoclave(idSolicitud, folioSolicitud, model);
		
		return "wizardProcesandoSolicitud";
	}

	@RequestMapping(value = "/{folioSolicitud}/{idSolicitud}/{idTipoSolicitud}", method = RequestMethod.GET)
	public String mostrarMensajeEsperaSolicitudTipo(Model model,
			@PathVariable String folioSolicitud,
			@PathVariable Long idSolicitud, @PathVariable Long idTipoSolicitud) {
		
		this.log.debug(" FolioSolicitud en proceso: " + folioSolicitud);
		model.addAttribute("idTipoSolicitud", idTipoSolicitud);
		setHomoclave(idSolicitud, folioSolicitud, model);
		
		return "wizardProcesandoSolicitud";
	}

	@RequestMapping(value = "/validar/{folioSolicitud}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, ? extends Object> actualizarSolicitudClasificacion(Model model,
			@PathVariable String folioSolicitud, HttpServletResponse response,
			HttpSession session, Locale locale) {
		
		Map<String, Object> result = new HashMap<String, Object>();

		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioSolicitud);
		try {
			Solicitud solicitudResultado = solicitudBusinessRemote.consultarFolioSinDatosTramite(solicitud);
			result.put("estadoSolicitud", solicitudResultado.getEstadoSolicitud().getIdEstadoSolicitud());
			result.put("folioSolicitud", folioSolicitud);
			result.put("isExitoso", true);
		} catch (SolicitudNoEncontradaException e) {
			result.put("isExitoso", false);
			result.put("folioSolicitud", folioSolicitud);
			result.put("mensajeError", e.getMessage());
		}

		return result;
	}
	
	private void setHomoclave(Long idSolicitud, String folioSolicitud, Model model) {
		String homoClave = null;
		
		if(idSolicitud != null) {
			homoClave = solicitudBusinessRemote.getHomoclaveSolicitud(idSolicitud);
		} else if(folioSolicitud != null){
			homoClave = solicitudBusinessRemote.getHomoclaveSolicitud(folioSolicitud);
		}
		
		model.addAttribute("folioSolicitud", folioSolicitud);
		model.addAttribute("idSolicitud", idSolicitud);
		model.addAttribute("homoClaveSolicitud", homoClave);
	}

}
