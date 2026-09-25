/**
 * Permite controlar la pantalla principal del sguimiento.
 * @author Marco A Nieto Plett
 */
package mx.imss.ctirss.web.controller.seguimiento.estudioCorreccion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.imss.ctirss.framework.base.controller.AbstractController;
import mx.imss.ctirss.web.controller.vo.seguimiento.estudioCorreccion.SeguimientoVO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Controlador que permite gestionar las actividades
 * de carga del seguimiento del estudio de correcci�n.
 * 
 * @see incluir controladores 
 * 
 * @author Marco Antonio Nieto Plett
 * @version 1.0.0
 */
@Controller
@RequestMapping(value="/seguimiento/controlPrincipal")
public class SeguimientoController extends AbstractController{
	
	/**
	 * M�todo utilizado por el menu, carga inicial de la
	 * consulta.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @version 1.0.0
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute(new SeguimientoVO());
	    return "seguimiento/controlPrincipal/seguimientoMain";
	}
	
	/**
	 * Permite gestionar la b�squeda para realizar el seguimiento
	 * del estudio de correcci�n.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @version 1.0.0
	 */
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public @ResponseBody SeguimientoVO modify(@RequestBody SeguimientoVO seguimientoVO, HttpServletRequest request, HttpServletResponse response) {
				
		return null;
	}	
	
			
}
