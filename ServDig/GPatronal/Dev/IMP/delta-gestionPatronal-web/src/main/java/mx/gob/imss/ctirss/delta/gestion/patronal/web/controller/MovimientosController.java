package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/movimientos/patronales")
public class MovimientosController {
	
	@Autowired
	ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	
	
	@RequestMapping(value = "/inicio")
	public String inicio(HttpSession session){
		return "movimientos";
	}
	
	@RequestMapping(value = "/buscarNSS")
	public @ResponseBody Fisica movimientosPatronales(HttpSession session, @RequestBody String nss){
		Fisica personaEncontrada= null;
		try {
			personaEncontrada=serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSSbyNSS(nss);
		} catch (PersonaFisicaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersonasNoLocalizadasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NssRelacionadoVariasPersonasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return personaEncontrada;
		
		
	}
	

}
