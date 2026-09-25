package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.Busqueda;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.OpcionesProperties;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;


@Controller
@RequestMapping(value="/homeNormativo*")
public class HomeNormativoCEController extends AbstractController {

	@Autowired
	private OpcionesProperties opcionesProperties;
	@Autowired 
	private UmfServiceRemote umfServiceRemote;
	@Autowired 
	private PersonaBusinessRemote personaBusinessRemote;
	@Autowired 
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	
	@Autowired 
	private SessionControler sessionControler;
	
	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	
	
	
	
	@RequestMapping(value = "/asignaPerfil",  method = RequestMethod.POST)
    public String asignaPerfilNormativoCE(Busqueda busqueda, HttpSession session,HttpServletRequest request, Model model) {
		String forward = Constants.BUSQUEDA_PRINCIPAL_FORDWARD;;
		try {
			
			
			UsuarioSSO usr = this.procesarUsuarioSSO(request);
			
			Usuario usuario = sessionControler.validarSesionUsuarioNormativo(session, usr, busqueda);
		} catch(Exception e) {
			log.error("Ocurrio un error al setear el perfil para el nomativo", e);
			request.setAttribute("exception", "exception.general");
			request.setAttribute("error", e.getCause().getMessage());
			return "internalError";
		}
		//opciones de menus
		request.setAttribute("opciones", opcionesProperties.getOpciones());
		//Establecemos los datos de la busqueda en el model para enviarlos a pantalla
		model.addAttribute(Busqueda.REQ_NAME, session.getAttribute("usuario"));
		return forward;
    }
	
	
	
	@RequestMapping(value = "/cambiaPerfil")
    public String cambiaPerfilNormativoCE(HttpSession session,HttpServletRequest request, Model model) {
		
		String forward = Constants.BUSQUEDA_PRINCIPAL_FORDWARD;;
		
		try {
			UsuarioSSO usr = this.procesarUsuarioSSO(request);
			Usuario usuario = sessionControler.validarSesionUsuario(session, usr);
			
			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("exception", "exception.general");
			request.setAttribute("error", e.getCause().getMessage());
			return "internalError";
		}
		//opciones de menus
		request.setAttribute("opciones", opcionesProperties.getOpciones());
		//Establecemos los datos de la busqueda en el model para enviarlos a pantalla
		model.addAttribute(Busqueda.REQ_NAME, session.getAttribute("usuario"));
		return forward;
    }
	
	
	
	 /**
	  * Metodo encargado de 
	  * @return
	  */
	 @RequestMapping(value = "/combo/getDelegaciones", method = RequestMethod.POST)
	    public @ResponseBody Map<String,  ? extends  Object> getDelegaciones() {
	    	Map<String, Object> periodos = new HashMap<String, Object>();
	    	List<Delegacion> delegaciones = domicilioServiceBusiness.findDelegacionesActivas();
	    	periodos.put("delegaciones", delegaciones);
	    	
	    	return periodos;
	    }
	    
	 @RequestMapping(value = "/combo/getSubdelegaciones/{idDelegacion}", method = RequestMethod.POST)
	    public @ResponseBody Map<String,  ? extends  Object> getSubDelegaciones(@PathVariable Long idDelegacion) {
	    	Map<String, Object> periodos = new HashMap<String, Object>();
	    	List<Subdelegacion> subdelegaciones = domicilioServiceBusiness.findSubDelegacionesActivas(idDelegacion.longValue());
	    	periodos.put("subdelegaciones", subdelegaciones);
	    	
	    	return periodos;
	    }
	 
	 @RequestMapping(value = "/combo/getClinicas/{idSubDelegacion}", method = RequestMethod.POST)
		public @ResponseBody  Map<String,  ? extends  Object> getUMFBySubdelegacion(@PathVariable Long idSubDelegacion){
			
		 	Map<String, Object> periodos = new HashMap<String, Object>();
		 	List<UnidadMedicaFamiliar> unidades= null;
			
		 	try {
			 	unidades = umfServiceRemote.findUmfbySubDelagacionDelegacion(idSubDelegacion);
			 	if(unidades == null) {
			 		unidades = new ArrayList<UnidadMedicaFamiliar>();
			 	}
			 }catch(Exception e) {
		 		log.error("ocurio un error al consultar las clinicas por subdelgacion [" +idSubDelegacion +"]", e);
				unidades = new ArrayList<UnidadMedicaFamiliar>();
		 	}
		 	periodos.put("clinicas", unidades);
			
			return periodos;
		}
	    
	@RequestMapping(value = "/asignaOpcion",  method = RequestMethod.POST)
    public String asignaOpcion(@RequestParam("opcion") Integer opcion, HttpSession session,HttpServletRequest request, Model model) {
		String forward = "";
		try {
			if(opcion == 1) {
				forward = Constants.HOME_NORMATIVO_CE;
			}else{
				forward = Constants.HOME_NORMATIVO_CE_REPORTES;
			}
			
		} catch(Exception e) {
			log.error("Ocurrio un error al setear la opcion para el nomativo", e);
			request.setAttribute("exception", "exception.general");
			request.setAttribute("error", e.getCause().getMessage());
			return "internalError";
		}
		return forward;
    }
    
	@RequestMapping(value = "/asignaNivel",  method = RequestMethod.POST)
    public String asignaNivel(@RequestParam("nivelreporte") Integer nivelreporte,@RequestParam("delegacion") Integer delegacion , @RequestParam("subdelegacion") Integer subdelegacion, Busqueda busqueda, HttpSession session,HttpServletRequest request, Model model) {
		String forward = Constants.HOME_JEFE_DEPTO_SUPER;
		UsuarioSSO usr = this.procesarUsuarioSSO(request);
		Usuario usuario = new Usuario();
	try {
			usuario.setUsuarioFuncionario(new UsuarioFuncionario());
			usuario.getUsuarioFuncionario().setDelegacion(new Delegacion());
			if (nivelreporte == 1 ) {
				usuario.getUsuarioFuncionario().getDelegacion().setId(Integer.valueOf("0").longValue());
				usuario.setCveIdSubdelegacion(Integer.valueOf("0").longValue());
			} else {
				usuario.getUsuarioFuncionario().getDelegacion().setId(delegacion.longValue());
				usuario.setCveIdSubdelegacion(subdelegacion.longValue());
			}
		} catch(Exception e) {
			log.error("Ocurrio un error al setear el perfil para el nomativo", e);
			request.setAttribute("exception", "exception.general");
			request.setAttribute("error", e.getCause().getMessage());
			return "internalError";
		}
		model.addAttribute("nivelreporte", nivelreporte);
		model.addAttribute("delegacion", delegacion);
		model.addAttribute("subdelegacion", subdelegacion);
		session.setAttribute("usuarioreporte", usuario);
//		session.setAttribute("nivelreporte", nivelreporte);
		return forward;
    }
	
		
	@RequestMapping(value = "/limpiarSesion")
	public @ResponseBody Boolean limpiarSesion(Model model, HttpServletRequest request, HttpSession session) {
		session.invalidate();
		return null;
	}
	
		 
	 
}
