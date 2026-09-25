package mx.gob.imss.ctirss.delta.correccion.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTramiteMensajes;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTramites;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtTramitePresentado;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/widget/cobranza")
public class CobranzaReporteWidgetController extends AbstractController {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;

	@RequestMapping(method = RequestMethod.GET)
	public String initSolicitudessPortlet(Model model,
			HttpServletRequest request) {
		//srequest.getSession().setAttribute("numeroRegistroPatronal", numeroRegistroPatronal);
		return "widgetReporteCobranzaInit";
	}

	@RequestMapping(value = "/resumen/{numeroRegistroPatronal}")
	public String obtenerSolicitudes(HttpSession session,
			HttpServletRequest request, Model model,@PathVariable("numeroRegistroPatronal") String registroPatronal) {
		List listaRes = new ArrayList();
		
		log.debug("Iniciando widget................");
		System.out.println("Iniciando widget................");
		System.out.println("registroPatronal" + registroPatronal);
		log.debug("registroPatronal" + registroPatronal);
		
		//StringTokenizer token = new StringTokenizer(registroPatronal, "-");
		
		String regPat = registroPatronal;
		//String curp = token.nextToken();
		//String rfc = token.nextToken();
		
		log.debug("registroPatronal" + regPat);
	
		
		
		listaRes = catalogoServiceBean.consultaLibrePorClave(0L, "from CrtTramitePresentado  t where t.desRegPatronal = '" +regPat +"'");
		//listaRes = catalogoServiceBean.consultaLibrePorClave(0L, "from CrtTramitePresentado  t where t.desRegPatronal = 'A067241910'");
		if(listaRes.size()==1)
			request.getSession().setAttribute("numeroRegistroPatronal", listaRes.size() + " trámite presentado");
		else if(listaRes.size()==0)
			request.getSession().setAttribute("numeroRegistroPatronal", "Ningún trámite presentado");
		else 
			request.getSession().setAttribute("numeroRegistroPatronal", listaRes.size() + " trámites presentados");
		
		
		request.getSession().setAttribute("registroPatronal", registroPatronal);
				
		return "widgetReporteCobranza";
	}
	
	@RequestMapping(value = "/resumen")
	public String obtenerSolicitudess(HttpSession session,
			HttpServletRequest request, Model model,@PathVariable("numeroRegistroPatronal") String registroPatronal) {
		List listaRes = new ArrayList();
		
		log.debug("Iniciando widget................");
		System.out.println("Iniciando widget................");
		System.out.println("registroPatronal" + registroPatronal);
		log.debug("registroPatronal" + registroPatronal);
		
		//StringTokenizer token = new StringTokenizer(registroPatronal, "-");
		
		String regPat = registroPatronal;
		//String curp = token.nextToken();
		//String rfc = token.nextToken();
		
		log.debug("registroPatronal" + regPat);
	
		
		
		listaRes = catalogoServiceBean.consultaLibrePorClave(0L, "from CrtTramitePresentado  t where t.desRegPatronal = '" +regPat +"'");
		//listaRes = catalogoServiceBean.consultaLibrePorClave(0L, "from CrtTramitePresentado  t where t.desRegPatronal = 'A067241910'");
		if(listaRes.size()==1)
			request.getSession().setAttribute("numeroRegistroPatronal", listaRes.size() + " trámite presentado");
		else if(listaRes.size()==0)
			request.getSession().setAttribute("numeroRegistroPatronal", "Ningún trámite presentado");
		else 
			request.getSession().setAttribute("numeroRegistroPatronal", listaRes.size() + " trámites presentados");
		
		
		request.getSession().setAttribute("registroPatronal", registroPatronal);
				
		return "widgetReporteCobranza";
	}
	
	
	@RequestMapping(value = "/tramites")
	public String consultaTramitesS(HttpSession session,
			HttpServletRequest request, Model model,@PathVariable("numeroRegistroPatronal") String registroPatronal) {
		
		log.debug("Iniciando Carga de Tramites................");
		log.debug("Registro patronal "+registroPatronal);
		
		List listaRes = new ArrayList();
		List<CrtTramitePresentado> lista=null;	
		
		//listaRes = catalogoServiceBean.consultaLibrePorClave(0L, "from CrtTramitePresentado  t where t.desRegPatronal = 'A067241910'");
		listaRes = catalogoServiceBean.consultaLibrePorClave(0L, "from CrtTramitePresentado  t where t.desRegPatronal = '" +registroPatronal +"'");
		request.getSession().setAttribute("registroPatronal", registroPatronal);
		lista=listaRes;
		
		for(CrtTramitePresentado tram:lista){
			tram=recuperaDescripciones(tram);
		}
		request.getSession().setAttribute("tramites", lista);
		return "widgetPortletCorreccion";
	}
	
	@RequestMapping(value = "/tramites/{numeroRegistroPatronal}")
	public String consultaTramites(HttpSession session,
			HttpServletRequest request, Model model,@PathVariable("numeroRegistroPatronal") String registroPatronal) {
		
		log.debug("Iniciando Carga de Tramites................");
		log.debug("Registro patronal "+registroPatronal);
		
		List listaRes = new ArrayList();
		List<CrtTramitePresentado> lista=null;	
		
		//listaRes = catalogoServiceBean.consultaLibrePorClave(0L, "from CrtTramitePresentado  t where t.desRegPatronal = 'A067241910'");
		listaRes = catalogoServiceBean.consultaLibrePorClave(0L, "from CrtTramitePresentado  t where t.desRegPatronal = '" +registroPatronal +"'");
		request.getSession().setAttribute("registroPatronal", registroPatronal);
		lista=listaRes;
		
		for(CrtTramitePresentado tram:lista){
			tram=recuperaDescripciones(tram);
		}
		request.getSession().setAttribute("tramites", lista);
		return "widgetPortletCobranza";
	}
	
	@RequestMapping(value = "/tramites/portlet/{numeroRegistroPatronal}")
	public String consultaTramitesPortlet(HttpSession session,
			HttpServletRequest request, Model model,@PathVariable("numeroRegistroPatronal") String registroPatronal) {
		
		log.debug("Iniciando Carga de Tramites................");
		log.debug("Registro patronal "+registroPatronal);
		
		List listaRes = new ArrayList();
		List<CrtTramitePresentado> lista=null;	
		
		//listaRes = catalogoServiceBean.consultaLibrePorClave(0L, "from CrtTramitePresentado  t where t.desRegPatronal = 'A067241910'");
		listaRes = catalogoServiceBean.consultaLibrePorClave(0L, "from CrtTramitePresentado  t where t.desRegPatronal = '" +registroPatronal +"'");
		request.getSession().setAttribute("registroPatronal", registroPatronal);
		lista=listaRes;
		
		for(CrtTramitePresentado tram:lista){
			tram=recuperaDescripciones(tram);
		}
		request.getSession().setAttribute("tramites", lista);
		return "widgetPortletCorreccion";
	}
	
	
	
	private CrtTramitePresentado recuperaDescripciones(CrtTramitePresentado pre){		
		List lis=catalogoServiceBean.consultaLibrePorClave(0L, "from CrcTramites  t where t.cveTramite = " +pre.getCveTramite());
		CrcTramites tr=(CrcTramites) lis.get(0);
		List lista=catalogoServiceBean.consultaLibrePorClave(0L, "from CrcTramiteMensajes  t where t.cveMensaje = " +pre.getCveMensaje());
		CrcTramiteMensajes ms= (CrcTramiteMensajes) lista.get(0);
		pre.setDesMensaje(tr.getDesTramite());
		pre.setDesTramite(ms.getDesMensajes());
		return pre;
	}
	
}
