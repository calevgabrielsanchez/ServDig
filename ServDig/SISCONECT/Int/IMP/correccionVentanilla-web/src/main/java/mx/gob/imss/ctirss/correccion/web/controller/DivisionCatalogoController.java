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

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.model.Division;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;

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
@RequestMapping(value="/catalogo/division")
public class DivisionCatalogoController extends AbstractController {

	@Autowired
	private ICatalogoService<Division> catalogoServiceBean;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute(new Division());
		 return "catalogos/division/divisionMain";
	}
	
	@RequestMapping(value="/modificar" , method=RequestMethod.POST)
	public @ResponseBody Division modify(@RequestBody Division division, HttpServletResponse response) {
		System.out.println(".--. EN MODIFICAR");
		this.catalogoServiceBean.actualizar(division);
		System.out.println(".--. MODIFICO");
		return division;
	}	
	
	@RequestMapping(value="/eliminar" , method=RequestMethod.POST)
	public @ResponseBody Division delete(@RequestBody Division division, HttpServletResponse response) {
		System.out.println(".--. EN eliminar");
		this.catalogoServiceBean.eliminar(division);
		System.out.println("ELIMINO");
		return division;
	}		

	
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody Division create(@RequestBody Division division, HttpServletResponse response) {
		System.out.println(".--. entre a agregar");
		this.catalogoServiceBean.agregar(division);
		System.out.println(".--. sale de agregar");
		return division;
	}
	
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<Division> pagina(@RequestBody List aoData ) {
		DatosEntradaPaginador<Division> send = new DatosEntradaPaginador<Division>();
		send.parserArray(aoData);
		System.out.println(".-.-.-.-.-.catalogoServiceBean.paginaDiv(send);");
		DatosSalidaPaginador<Division> reply = this.catalogoServiceBean.paginaDiv(send);
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@RequestMapping(value="/consultaPorClave")
	public @ResponseBody Division consultaPorClave(@RequestBody Division division){
		System.out.println("CVE ID A BUSCAR:"+division.getCveIdDivision());
		if(division.getCveIdDivision()>0){
			division = catalogoServiceBean.consultaPorClave(division);	
		}
		return division;
	}		
	
	
	private Map<String, String> validationMessages(Set<ConstraintViolation<Division>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<Division> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}
	
}
