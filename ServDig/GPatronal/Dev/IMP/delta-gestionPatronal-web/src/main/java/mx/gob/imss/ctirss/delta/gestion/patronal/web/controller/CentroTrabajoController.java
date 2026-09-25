/**
 * delta-gestionPatronal-web31/05/2012
 * mx.gob.imss.ctirss.delta.gestion.patronal.web.controller31/05/2012
 * CentroTrabajoController.java
 * 31/05/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: CentroTrabajoController.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.web.controller
 *  @Fecha: 10:42:09
 */
@Controller
@RequestMapping(value="/centroTrabajo")
public class CentroTrabajoController extends AbstractController {
	
	/**
	 * Metodo para obtener los productos de la solicitud.
	 * @param idSolicitud
	 * @param model
	 * @return
	 */
    @RequestMapping(method=RequestMethod.POST)
	public String inicio (Model model, @RequestParam("idRegistroPatronal") Long idRegistroPatronal, HttpSession session) {
    	System.out.println("ESTOY EN EL ACTION******************");
    	model.addAttribute("centroTrabajo", new CentroTrabajo());
    	
    	return "centroTrabajoRP";
    }
	
    @RequestMapping(value="/contacto/concluirSolicitud", method=RequestMethod.POST)
	public String concluirSolicitud(Model model, @ModelAttribute CentroTrabajo sujetoObligado, HttpSession session) {
    	System.out.println("ESTOY EN EL ACTION DE CONCLUIR******************");
    	model.addAttribute("sujetoObligado", sujetoObligado);
    	
    	return "centroTrabajoRP";
    }
	
}
