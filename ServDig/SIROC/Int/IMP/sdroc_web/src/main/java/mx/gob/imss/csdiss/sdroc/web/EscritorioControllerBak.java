/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.csdiss.sdroc.service.RegistroObraService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.BloqueoObraService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionObraService;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author daniel.hernandez
 *
 */
@Controller
public class EscritorioControllerBak {
	
	/**
	 * logger EscritorioController
	 */
	private static final Logger logger = Logger.getLogger(EscritorioControllerBak.class); 
	
	@Autowired
	RegistroObraService registroObraService;
	
	@Autowired
	SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote;
	
	@Autowired
	MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	
	@Autowired
	BloqueoObraService bloqueObraService;
	
	@Autowired
	InformacionObraService informacionObraService;

	@RequestMapping(value = {"","/"}, method = RequestMethod.GET)
	public String home(final ModelMap modelMap, HttpSession session) {
		
		return redirecPatrones(modelMap, session);
	}
	
	/**
	 * Redireccion a la pagina de inicio [Escritorio patrones]
	 * @param model
	 * @param session
	 * @return view
	 */
	public String redirecPatrones(ModelMap model, HttpSession session) {
		
		return "escritorio";
		
	}
	
	
	/**
	 * Escritorio principal
	 * Muestra los registros de obra que corresponden al patron filtrando por RFC y RP
	 * 
	 * @param rfc
	 * @param rp
	 * @param rs
	 * @param model
	 * @param session
	 * @return view
	 * @throws PersonaFisicaNoEncontradaException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/escritorioBack"})
	public ModelAndView redirectEscritorio(ModelAndView model, HttpSession session, HttpServletRequest request, @RequestParam(value="rfc",required=false) String cveRfc, @RequestParam(value="nrp",required=false) String cveRp, @RequestParam(value="cveIdPersona",required=false) Long cveIdPersona, @RequestParam(value="cveTipoPersona",required=false) Long cveTipoPersona
			, @RequestParam(value="prot",required=false) String prot, @RequestParam(value="fqdn",required=false) String fqdn) throws PersonaFisicaNoEncontradaException,IOException {
		
		model = new ModelAndView("/escritorio");
		
		logger.info("XXXXXXXXXXX INFO : LOGGER");
		logger.error("XXXXXXXXXXX ERROR : LOGGER");
		logger.debug("XXXXXXXXXXX DEBUG : LOGGER");
		logger.fatal("XXXXXXXXXXX FATAL : LOGGER");
		logger.trace("XXXXXXXXXXX TRACE : LOGGER");
		
		String idSession=(String) request.getSession().getAttribute("ID_SESSION_BLOQUEO");
		bloqueObraService.liberaObras(idSession);
		Persona persona = null;
		String rs = null;
		String parametroCveRfc = null;
		String parametroCveRp = null; 
		String cveDelegacion = null;
		String desDelegacion = null;
		String cveSubDelegacion = null;
		String desSubDelegacion = null;
		String domicilioFiscal = null;		
		String correoEnvio = null;
		String cveCurp = null;
		String refNombre = null;
		String refApellidoPaterno = null;
		String refApellidoMaterno = null;
		Long parametroCveTipoPersona = null;
		List<MedioContacto> mediosPatron = null;
		SujetoObligado patron = null;
		
		
		if(cveRfc != null && cveRp != null && cveIdPersona != null && cveTipoPersona != null )
		{			
			session.setAttribute("rfc", cveRfc);
			session.setAttribute("rp", cveRp);
			session.setAttribute("cveIdPersona", cveIdPersona);
			session.setAttribute("cveTipoPersona", cveTipoPersona);
			
			parametroCveRfc = cveRfc;
			parametroCveRp = cveRp;
			parametroCveTipoPersona =  	cveTipoPersona;
			//en caso de que vengan los atributos quitamos la persona de la session
			session.removeAttribute("patron");
		}else{
			parametroCveRfc = (String) session.getAttribute("rfc");
			parametroCveRp = (String) session.getAttribute("rp");
			parametroCveTipoPersona = (Long) session.getAttribute("cveTipoPersona");
			patron = (SujetoObligado)session.getAttribute("patron");
			mediosPatron = (List<MedioContacto>) session.getAttribute("mediosPatron");
		}
		
		//verificamos si tenemos a la persona en session para que ya no la busquemos y asi ahorrarnos
		//los kilos de consulta de Fisica y Moral
		if(patron != null) {
			if(parametroCveTipoPersona != null && parametroCveTipoPersona.equals(new Long(1))) {
				persona = patron.getFisica();
				rs = patron.getFisica().getNombreCompleto();
				refNombre = patron.getFisica().getNombre();
				refApellidoMaterno = patron.getFisica().getPrimerApellido();
				refApellidoMaterno = patron.getFisica().getSegundoApellido();
				cveCurp = patron.getFisica().getCurp();
			} else {
				persona = patron.getMoral();
				rs = patron.getMoral().getRazonSocial();
			}
		} else {
			
			try {
				patron = sujetoObligadoServiceBusinessRemote.consultarPorNumeroRegistroPatronal(cveRp);
				logger.error("El patron es por clase " + patron.getClasificacion().getIndRegPatClase());
				CentroTrabajo centro = new CentroTrabajo();
				centro.setCveIdPatronSujetoObligado(patron.getCveIdSujetoObligado());
				mediosPatron = mediosContactoServiceBusinessRemote.consultarMedioContactoDeCentroTrabajo(centro);
				session.setAttribute("patron", patron);
				session.setAttribute("mediosPatron", mediosPatron);
				session.setAttribute("patronRPC", patron.getClasificacion().getIndRegPatClase());
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		//para este punto ya se debe tener el patron ya sea de session o consultado, y obtenemos a la persona
		persona = patron.getFisica() != null ? patron.getFisica() : patron.getMoral();
		//obtenemos el nombre o razon social dependiendo de si es patron fisica o moral
		rs = patron.getFisica() != null ? patron.getFisica().getNombreCompleto() : patron.getMoral().getRazonSocial();
		logger.error("el rfc de la persona es " + persona.getRfc());
		cveRfc = persona.getRfc();
		parametroCveRfc = cveRfc;
		session.setAttribute("rfc", cveRfc);
		//Seteamos los datos de delegacion
		cveDelegacion =  patron.getSubdelegacion().getDelegacion().getId().toString();
		desDelegacion = patron.getSubdelegacion().getDelegacion().getDescripcion();
		//seteamos los datos de la subdelegacion
		cveSubDelegacion = patron.getSubdelegacion().getId().toString();
		desSubDelegacion = patron.getSubdelegacion().getDescripcion();
		//obtenemos el coreo del centro de trabajo
		correoEnvio = this.getCorreo(mediosPatron);		
		
		if( persona != null && persona.getDomicilioFiscal() != null )
		{
			
			domicilioFiscal = persona.getDomicilioFiscal().getCalle();
			if(persona.getDomicilioFiscal().getNumExterior1() != null)
				domicilioFiscal.concat(", ").concat(persona.getDomicilioFiscal().getNumExteriorAlf().toString());
			if(persona.getDomicilioFiscal().getColonia() != null)		
				domicilioFiscal.concat(", ").concat(", ").concat(persona.getDomicilioFiscal().getColonia());
			if(persona.getDomicilioFiscal().getCodigoPostal() != null)
				domicilioFiscal.concat(", ").concat(persona.getDomicilioFiscal().getCodigoPostal().toString());
			if(persona.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio() != null)
				domicilioFiscal.concat(", ").concat(persona.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getNombre());
			if(persona.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null)
				domicilioFiscal.concat(", ").concat(persona.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
			
		
		}else{
			domicilioFiscal = "No Disponible";
		}
		
		session.setAttribute("cveCurp", cveCurp);
		session.setAttribute("refNombre", refNombre);
		session.setAttribute("refApellidoPaterno", refApellidoPaterno);
		session.setAttribute("refApellidoMaterno", refApellidoMaterno);
		session.setAttribute("domicilioFiscal", domicilioFiscal);
		/*
		 * Datos Delegacion y Subdelegacion		
		 */
		session.setAttribute("cveDelegacion", cveDelegacion);
		session.setAttribute("desDelegacion", desDelegacion);
		session.setAttribute("cveSubDelegacion", cveSubDelegacion);
		session.setAttribute("desSubDelegacion", desSubDelegacion);
		
		session.setAttribute("correoEnvio", correoEnvio);
		session.setAttribute("rs", rs);	
		
		
		model.addObject("lstObrasRegistradas", registroObraService.consultarObrasPorRFCyRP(parametroCveRfc,parametroCveRp));

		return model;
	}
	
	
	
	/**
	 * Busqueda de medios de contacto por persona que se encuentra en la sesion
	 * @param mediosContacto
	 * @return String
	 */
	private String getCorreo(List<MedioContacto> mediosContacto) {
		String correoEnvio = null;
		
		if(mediosContacto != null) {
			//en caso de tener medios de contacto los recorremos
			for(MedioContacto medioContacto : mediosContacto) {
				//y buscamos el primero que sea de tipo 1 (Correo electronico)
				if(medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(1L)) {
					//en caso de encontrar un correo lo seteamos en la variable y terminsamos el ciclo
					correoEnvio = medioContacto.getDesFormaContacto();
					break;
				}
			}
		}
		
		return correoEnvio;
	}
	
	@RequestMapping("/getPatronSession")
	@ResponseBody
	public SujetoObligado getPatronSession(HttpSession session) {
		SujetoObligado patron = (SujetoObligado) session.getAttribute("patron");
		
		return patron;
	} 
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getCentro")
	@ResponseBody
	public List<MedioContacto> getCentroTrabajo(HttpSession session) {
		return (List<MedioContacto>) session.getAttribute("mediosPatron");
	} 
}