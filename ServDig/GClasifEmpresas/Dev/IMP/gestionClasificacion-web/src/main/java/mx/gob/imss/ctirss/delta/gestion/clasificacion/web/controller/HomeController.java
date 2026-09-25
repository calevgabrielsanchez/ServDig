/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: gestionClasificacion
 *  @Archivo:HomeController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Lucio Duran Silva
 *
 */
@Controller
@RequestMapping(value="/home")
public class HomeController extends AbstractController {

	@RequestMapping(method = RequestMethod.GET)
	public String login(HttpSession session, Model model) {
		if (null != session.getAttribute("grupoTramite")) {
			session.removeAttribute("grupoTramite");
		}
		if (null != session.getAttribute("tipoMovimientoGrupoTramite")) {
			session.removeAttribute("tipoMovimientoGrupoTramite");
		}
		return "bienvenida";
	}
	
}
