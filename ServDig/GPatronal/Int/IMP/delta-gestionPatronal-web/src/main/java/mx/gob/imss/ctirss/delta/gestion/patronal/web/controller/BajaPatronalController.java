package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.SujetoObligadoDataTable;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/afiliacion/baja")
public class BajaPatronalController extends AbstractController {
	public static final Logger LOG = Logger.getLogger(BajaPatronalController.class);
	
	@Autowired
	RegistroPatronalServiceBusinessRemote registroPatronalPatronalBusiness;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String cargarPantallaBajaPatronal(Model model,
			HttpSession session) {
		
		return "vista.baja";
	}
	
	/**
	 * Carga el grid de registros patronales asociados a un rfc
	 * 
	 * @param params
	 * @param session
	 * @return JSON Object
	 */
	@RequestMapping(value = "/paginarRegistrosPatronalesPorRFC", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<SujetoObligado> paginarRegistrosPatronalesPorRFC(
			@RequestBody SujetoObligadoDataTable params, HttpSession session) {
		System.err.println("Cargando registros patronales por RFC.....");
		
		DatosEntradaPaginador<SujetoObligado> input = new DatosEntradaPaginador<SujetoObligado>(); 
		input.setModelo(params.getoForm());
		input.parserArray(params.getAoData());
		System.err.println("Modelo: "+input.getModelo());
		System.err.println("Param: "+params.getoForm());
		DatosSalidaPaginador<SujetoObligado> output = new DatosSalidaPaginador<SujetoObligado>();
		output = registroPatronalPatronalBusiness.paginarRegistrosPatronales(input);
		output.setsEcho(input.getsEcho());
		return output;
	}

	/**
	 * Realiza la baja de un registro patronal
	 * @author Hugo Martinez
	 * @Fecha: 10/01/2013 14:04:09
	 */
	@RequestMapping(value = "/ejecutarBaja", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> finalizarBajaPatronal(
			@RequestBody String numeroRegistroPatronal,
			HttpServletResponse response, HttpSession session) {
		log.debug(" Ejecutando baja de registro patronal");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		this.log.debug("Usuario: " + usuario);
		Map<String, Object> result = new HashMap<String, Object>();
		
		return result;
	}
}
