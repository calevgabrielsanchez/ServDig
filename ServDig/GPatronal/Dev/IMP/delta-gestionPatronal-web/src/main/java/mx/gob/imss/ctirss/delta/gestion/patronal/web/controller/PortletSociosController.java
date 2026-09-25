package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.SociosDataTable;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/portlet/empresas/socios")
public class PortletSociosController extends AbstractController {

	@Autowired
	private SocioServiceBusinessRemote socioServiceBusinessRemote;
	
	
	private static final String VIEW_SOCIOS_INIT = "portletSociosInit";
	private static final String VIEW_SOCIOS_CONT = "portletSociosContenido";
	
	@RequestMapping("/init/{idPersona}")
	public String initPersonasAutorizadas(Model model, @PathVariable Long idPersona, 
			HttpServletRequest request,HttpSession session) {
		Socio socio = new Socio();
		socio.setIdPersonaMoralPatron(idPersona);
		model.addAttribute("socio",socio);		
		return VIEW_SOCIOS_INIT;
	}
	
	@RequestMapping("/detalle/{idPersona}")
	public String muestraPersonasAutorizadasAsociadas(Model model, @PathVariable Long idPersona,
			HttpServletRequest request, HttpSession session) {
		Socio socio = new Socio();
		socio.setIdPersonaMoralPatron(idPersona);
		model.addAttribute("socio",socio);
		return VIEW_SOCIOS_CONT;
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<Socio> pagina(@RequestBody SociosDataTable aoData ) {
		log.info("Consulta SOCIOS - idPersona " + aoData.getoForm().getIdPersona());
		DatosEntradaPaginador<Socio> send = new DatosEntradaPaginador<Socio>();
        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());
        DatosSalidaPaginador<Socio> reply = socioServiceBusinessRemote.paginarSocios(send);
		reply.setsEcho(send.getsEcho());
		return reply;
	}
	
}
