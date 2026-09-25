package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ActualizarRfcBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/wizard/actualizaRfc/")
public class WizardActualizarRfcController extends AbstractController {

	private static final String KEY_ATRIBUTE_ERROR = "error";
	private static final String KEY_SIN_RFC = "SIN_RFC";
	private static final String KEY_ATRIBUTE_MENSAJE = "mensaje";
	private static final String VIEW_ACTUALIZAR_RFC = "actualizarRfcWizard";
	private static final String MSG_RFC_ACTUALIZADO = "Se ha actualizado el RFC.";
	private static final String RFC_EXISTENTE_IMSS_SESION_KEY = "_rfcExisteImss";
	
	@Autowired
	private ActualizarRfcBusinessRemote actualizarRfcBusiness;
	
	@RequestMapping(value = "init/{idPersona}/{rfcRequerido}/{tipoTramite}", method = RequestMethod.GET)
	public String init(Model model, HttpSession session,HttpServletRequest request,
			@PathVariable Long idPersona, @PathVariable boolean rfcRequerido, @PathVariable Integer tipoTramite) {
		
		Fisica fisica = new Fisica();
		fisica.setIdPersona(idPersona);						
		request.setAttribute("idPersona", idPersona);
		request.setAttribute("rfcRequerido", rfcRequerido);
		request.setAttribute("identificadorTipoTramite", tipoTramite);
		model.addAttribute("fisica", fisica);
		
		return VIEW_ACTUALIZAR_RFC;
	}
	
	@RequestMapping(value = "/actualizar/{idPersona}/{rfc}", method = {RequestMethod.GET, RequestMethod.POST })
	public  @ResponseBody Map<String, ? extends Object> actualizarRfc(
			final HttpSession session, HttpServletRequest request,
			@PathVariable Long idPersona, @PathVariable String rfc) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(rfcNoVacio(rfc))
			rfc=rfc.trim().toUpperCase();
		
		if(rfc.equals(KEY_SIN_RFC)){
			result.put(KEY_ATRIBUTE_MENSAJE, "OK.");
		}else{	
			try {
				String rfcExistenteImss = (String)session.getAttribute(RFC_EXISTENTE_IMSS_SESION_KEY);
				if(rfcNoVacio(rfcExistenteImss)){
					rfcExistenteImss = rfcExistenteImss.trim().toUpperCase();
					if(!rfcExistenteImss.equals(rfc)){
						throw new PersonasNoLocalizadasException("El RFC capturado no coincide con el que se encuentra registrado actualmente "
							+ "ante el Instituto Mexicano de Seguro Social, " +
							"acuda a la subdelegación para actualizar su información.");
					}
				}
				
				//Obtener persona por ID
				Fisica fisica = new Fisica();
				fisica.setIdPersona(idPersona);
				fisica.setRfc(rfc);
				actualizarRfcBusiness.actualizarRfc(fisica);
								
				result.put(KEY_ATRIBUTE_MENSAJE, MSG_RFC_ACTUALIZADO);
			} catch (AbstractException e) {
				//Error obtener persona por RFC en SAT
				e.printStackTrace();
				result.put(KEY_ATRIBUTE_ERROR, e.getMessage());		
			}
		}
		return result;
	}
	
	@RequestMapping(value = "validaSolicitarRfc", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> obtenerJsonValidaSolicitarRfc(
			@RequestBody Fisica fisica,
			Model model, final HttpServletRequest request, final HttpSession session) {	
		Map<String, Object> response = actualizarRfcBusiness.solicitarActualizarRfc(fisica);		
		String rfcExistenteImss = (String)response.get("rfcExistenteImss");
		session.setAttribute(RFC_EXISTENTE_IMSS_SESION_KEY, rfcExistenteImss);		
		return response;
	}

	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody Solicitud limpiarSesion(final HttpSession session) {
		session.removeAttribute(RFC_EXISTENTE_IMSS_SESION_KEY);
		
		return null;
	}
	private boolean rfcNoVacio(String rfc){
		if(StringUtils.isNotEmpty(rfc) && StringUtils.isNotBlank(rfc)){
			return true;
		}else{
			return false;
		}
	}
	
}
