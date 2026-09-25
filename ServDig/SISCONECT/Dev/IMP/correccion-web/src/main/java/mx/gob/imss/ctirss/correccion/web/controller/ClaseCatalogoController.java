/**
 * ClaseCatalogoController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.gob.imss.ctirss.correccion.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;


import mx.gob.imss.ctirss.correccion.base.paginador.model.ClaseWrapperDataTable;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.menu.model.Menu;
import mx.gob.imss.ctirss.correccion.menu.service.interfaces.MenuService;
import mx.gob.imss.ctirss.correccion.model.Clase;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 29/08/2011
 */
@Controller
@RequestMapping(value="/catalogo/clase")
public class ClaseCatalogoController extends AbstractController {

	@Autowired
	private ICatalogoService<Clase> catalogoServiceBean;
	
	@Autowired
	private MenuService<Menu> menuServiceBean;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute(new Clase());
		
		 return "catalogos/clase/claseMain";
	}
	
	@RequestMapping(value="/modificar" , method=RequestMethod.POST)
	public @ResponseBody Clase modify(@RequestBody Clase clase, HttpServletResponse response) {
		this.catalogoServiceBean.actualizar(clase);
		return clase;
	}	
	
	@RequestMapping(value="/eliminar" , method=RequestMethod.POST)
	public @ResponseBody Clase delete(@RequestBody Clase clase, HttpServletResponse response) {
		this.catalogoServiceBean.eliminar(clase);
		return clase;
	}		

	
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody Clase create(@RequestBody Clase clase,HttpServletRequest request) {
		UserSession usrSession = new UserSession();
//		usrSession.setMenu(menuServiceBean.consultar(new Menu()), request);
		System.out.println(".-. ingresa en agregar");
		  this.catalogoServiceBean.agregar(clase);
		return clase;
	}
	
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<Clase> pagina(@RequestBody ClaseWrapperDataTable aoData ) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<Clase> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<Clase> reply = this.catalogoServiceBean.pagina(send);
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@RequestMapping(value="/consultaPorClave")
	public @ResponseBody Clase consultaPorClave(@RequestBody Clase clase){
		System.out.println("CVE ID A BUSCAR:"+clase.getCveIdClase());
		if(clase.getCveIdClase()>0){
			clase = catalogoServiceBean.consultaPorClave(clase);	
		}
		return clase;
	}		
	
	
	private Map<String, String> validationMessages(Set<ConstraintViolation<Clase>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<Clase> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}
	
}
