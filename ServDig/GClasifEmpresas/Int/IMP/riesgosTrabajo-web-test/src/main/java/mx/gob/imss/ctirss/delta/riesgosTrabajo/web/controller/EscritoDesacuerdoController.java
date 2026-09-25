package mx.gob.imss.ctirss.delta.riesgosTrabajo.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultaEscritoDesacuerdoServiceRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/escrito")
public class EscritoDesacuerdoController extends AbstractController {

	private static final String HOME_DESACUERDO = "homeDesacuerdo";
	private static final String VISTA_INICIO_CONSULTA_VENTANILLA = "consultaEscritoDesacuerdo";
	private static final String VISTA_LISTA_CONSULTA_VENTANILLA = "listaEscritoDesacuerdo";
	private static final String VISTA_DETALLE_CONSULTA_VENTANILLA = "escritoDesacuerdoDetalle";

	private static final Long ID_MATERIA_DET_PRIMA = 2l;
	private static final Long ID_MATERIA_CLA_EMPRESA = 1l;

	private static final Long PERFIL_NORMATIVO = 1l;
	private static final Long PERFIL_DELEGACION = 2l;
	private static final Long PERFIL_SUBDELEGACION = 3l;
	private static final String TIPO_CONSULTA = "tipoConsulta";
	
	@Autowired
	ConsultaEscritoDesacuerdoServiceRemote consultaEscritoDesacuerdoServiceRemote;
	
	@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
	public String homeDesacuerdo( HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
		
		return HOME_DESACUERDO;
	}

	@RequestMapping(value = "/consultar", method = { RequestMethod.GET, RequestMethod.POST })
	public String consultaEscritoDesacuerdo(
			HttpServletResponse response,
			HttpServletRequest request, HttpSession session, Model model) {
		log.debug("Validando perfiles");

		model.addAttribute("ID_MATERIA_DET_PRIMA", ID_MATERIA_DET_PRIMA);
		model.addAttribute("ID_MATERIA_CLA_EMPRESA", ID_MATERIA_CLA_EMPRESA);		
		model.addAttribute("PERFIL_NORMATIVO", PERFIL_NORMATIVO);
		model.addAttribute("PERFIL_DELEGACION", PERFIL_DELEGACION);
		model.addAttribute("PERFIL_SUBDELEGACION", PERFIL_SUBDELEGACION);
		
		return VISTA_INICIO_CONSULTA_VENTANILLA;
	}

	/**
	 * Busca los escritos de desacuerdo y los muestra en pantalla
	 *
	 * @param model
	 * @param sessionStatus
	 * @param session
	 * @param rp
	 * @return
	 */
	// /{folio} //@PathVariable
	@RequestMapping(value = "/consultar/findEscritoDesacuerdoFolio", method = {RequestMethod.GET, RequestMethod.POST })
	public ModelAndView findEscritoDesacuerdoFolio( HttpServletResponse response, HttpServletRequest request, @RequestParam(required = true) String folio) {
		ModelAndView model = new ModelAndView(VISTA_DETALLE_CONSULTA_VENTANILLA);
		TramiteEscritoDesacuerdo escritoDetalle = null;
		try {
			escritoDetalle = consultaEscritoDesacuerdoServiceRemote.findEscritoDesacuerdoFolio(folio);
			if(escritoDetalle!=null){
				model.addObject("escritoDetalle", escritoDetalle);
			}
		} catch (RiesgosTrabajoException e) {
			e.printStackTrace();
		}

		if(escritoDetalle == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		model.addObject(TIPO_CONSULTA, "findEscritoDesacuerdoFolio");
		return model;
	}

	@RequestMapping(value = "/consultar/findEscritoDesacuerdoRegPatron", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String findEscritoDesacuerdoRegPatron(Model model,
			HttpServletResponse response, HttpServletRequest request,
			HttpSession session,
			@RequestParam(required = true) String registroPatronal,
			@RequestParam(required = false) String fechaInicio,
			@RequestParam(required = false) String fechaFin,
			@RequestParam(required = false) Long delegacion,
			@RequestParam(required = false) Long subdelegacion) {

		Usuario usuario = (Usuario) session.getAttribute("usuario");
		List<TramiteEscritoDesacuerdo> listaEscrito = null;
		Date fechaInicioDate = null;
		Date fechaFinDate = null;
		
		try {
			if(StringUtils.isNotBlank(fechaInicio) && !fechaInicio.equals("0"))
				fechaInicioDate = FORMATO_FECHA.parse(fechaInicio);
			if(StringUtils.isNotBlank(fechaFin) && !fechaFin.equals("0"))
				fechaFinDate = FORMATO_FECHA.parse(fechaFin);
		} catch (ParseException e) {
			log.error("No fue posible parsear la fecha", e);
		}
		
		Subdelegacion subdelegacionVO = usuario.getUsuarioFuncionario().getSubdelegacion();
		Delegacion delegacionVO = usuario.getUsuarioFuncionario().getDelegacion();
		

		try {
			listaEscrito = consultaEscritoDesacuerdoServiceRemote.findEscritoDesacuerdoRegPatron(registroPatronal, delegacionVO, subdelegacionVO, fechaInicioDate, fechaFinDate);
			
		} catch (RiesgosTrabajoException e) {
			e.printStackTrace();
		}
		model.addAttribute("listaEscrito", listaEscrito);
		
		model.addAttribute("registroPatronal", registroPatronal);

		model.addAttribute(TIPO_CONSULTA, "findEscritoDesacuerdoRegPatron");

		return VISTA_LISTA_CONSULTA_VENTANILLA;
	}

	
	@RequestMapping(value = "/consultar/findEscritoDesacuerdoPeriodo", method = {RequestMethod.GET, RequestMethod.POST })
	public String findEscritoDesacuerdoPeriodo(Model model,
			HttpServletResponse response, HttpServletRequest request,
			@RequestParam(required = true) String fechaInicio,
			@RequestParam(required = true) String fechaFin,
			@RequestParam(required = false) Long delegacion,
			@RequestParam(required = false) Long subdelegacion) {

		List<TramiteEscritoDesacuerdo> listaEscrito = null;

		try {
			Delegacion delegacionVO = new Delegacion();
			Subdelegacion subdelegacionVO = new Subdelegacion();
			delegacionVO.setId(getIdDelegacionSubdelegacion(delegacion));
			subdelegacionVO.setId(getIdDelegacionSubdelegacion(subdelegacion));

			Date fechaInicioDate = FORMATO_FECHA.parse(fechaInicio);
			Date fechaFinDate = FORMATO_FECHA.parse(fechaFin);

			listaEscrito = consultaEscritoDesacuerdoServiceRemote
					.findEscritoDesacuerdoPeriodo(fechaInicioDate,
							fechaFinDate, delegacionVO, subdelegacionVO);
		} catch (RiesgosTrabajoException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
		model.addAttribute("fechaInicio", fechaInicio);
		model.addAttribute("fechaFin", fechaFin);
		model.addAttribute("listaEscrito", listaEscrito);
		model.addAttribute(TIPO_CONSULTA, "findEscritoDesacuerdoPeriodo");

		return VISTA_LISTA_CONSULTA_VENTANILLA;
	}


	private Long getIdDelegacionSubdelegacion(Long id){
		
		boolean diferenteZero= id!=null && !id.equals(0l);
		
		if(diferenteZero){
			return id;
		}
		return null;
	}

	private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat(
			"dd/MM/yyyy");

	@RequestMapping(value = "/consultar/comunes/getPeriodos", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> getPeriodos() {
		Map<String, Object> periodos = new HashMap<String, Object>();
		Calendar fecha = Calendar.getInstance();

		periodos.put("actual", FORMATO_FECHA.format(fecha.getTime()));
		return periodos;
	}

}