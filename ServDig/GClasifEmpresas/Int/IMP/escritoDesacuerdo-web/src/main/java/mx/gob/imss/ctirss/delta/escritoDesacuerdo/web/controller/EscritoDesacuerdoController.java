package mx.gob.imss.ctirss.delta.escritoDesacuerdo.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import mx.gob.imss.ctirss.delta.model.derechohabiente.DiasFestivos;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.DomicilioEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.MotivosDesacuerdo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultaEscritoDesacuerdoServiceRemote;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultalRiesgoTrabajoServiceRemote;

import org.apache.commons.lang.StringUtils;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/escrito")
public class EscritoDesacuerdoController extends AbstractController {

	private static final String HOME_DESACUERDO = "homeDesacuerdo";
	private static final String VISTA_INICIO_CONSULTA_VENTANILLA = "consultaEscritoDesacuerdo";
	private static final String VISTA_LISTA_CONSULTA_VENTANILLA = "listaEscritoDesacuerdo";
	private static final String VISTA_DETALLE_CONSULTA_VENTANILLA = "escritoDesacuerdoDetalle";

	private static final String MASIVA = "B\u00FAsqueda masiva";
	private static final Long ID_MATERIA_DET_PRIMA = 2l;
	private static final Long ID_MATERIA_CLA_EMPRESA = 1l;

	private static final Long PERFIL_NORMATIVO = 1l;
	private static final Long PERFIL_DELEGACION = 2l;
	private static final Long PERFIL_SUBDELEGACION = 3l;
	private static final String TIPO_CONSULTA = "tipoConsulta";

	private static final Logger LOGGER = LoggerFactory.getLogger(EscritoDesacuerdoController.class);

	@Autowired
	private ConsultalRiesgoTrabajoServiceRemote consultaHistRTTService;

	@Autowired
	ConsultaEscritoDesacuerdoServiceRemote consultaEscritoDesacuerdoServiceRemote;
	List<TramiteEscritoDesacuerdo> listaEscrito;

	@RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
	public String homeDesacuerdo(HttpServletResponse response, HttpServletRequest request, SessionStatus sessionStatus, HttpSession session, Model model) {

		LOGGER.debug("Validando perfiles");

		//Se recupera los datos del funcionario
		UsuarioSSO usuario = procesarUsuarioSSO(request);
		LOGGER.debug("El usuario []", usuario);
		session.setAttribute("funcionario", usuario);
		session.setAttribute("usuario", convertUsuarioSSO(usuario));

		return HOME_DESACUERDO;
	}

	private Usuario convertUsuarioSSO(UsuarioSSO usuarioSSO) {
		Usuario usuario = null;
		Map<Long, String> perfilPantalla = new HashMap<Long, String>();
		perfilPantalla.put(1L, "CENTRAL");
		perfilPantalla.put(2L, "DELEGACI&Oacute;N");
		perfilPantalla.put(3L, "SUBDELEGACI&Oacute;N");


		if(usuarioSSO != null){
			usuario = new Usuario();
			usuario.setUsuario(usuarioSSO.getNombre());
			usuario.setPerfilUsuario(new PerfilUsuario());
			Long idPerfilUsuario = null;
			Integer idDelegacion = usuarioSSO.getDelegacion();
			Integer idSubdelegacion = usuarioSSO.getSubdelegacion();

			//si el usuario no trae ni subdelegacion ni delegacion, es normativo
			if(idDelegacion == null && idSubdelegacion == null) {
				idPerfilUsuario = 1L;
			} else if(idDelegacion != null && idSubdelegacion == null) {
				idPerfilUsuario = 2L;
			} else {
				idPerfilUsuario = 3L;
			}

			usuario.getPerfilUsuario().setIdPerfilUsuario(idPerfilUsuario);
			usuario.getPerfilUsuario().setDescripcion(perfilPantalla.get(idPerfilUsuario));

			if(usuario.getPerfilUsuario().getIdPerfilUsuario() != null) {
				long idPerfil = usuario.getPerfilUsuario().getIdPerfilUsuario();
				UsuarioFuncionario usuarioF = new UsuarioFuncionario();
				if(idPerfil == 2) {
					Delegacion delegacion = consultaHistRTTService.getDelegacionUsuario(usuarioSSO.getDelegacion().longValue());
					usuarioF.setDelegacion(delegacion);
				} else if(idPerfil == 3) {
					Subdelegacion subdelegacion = consultaHistRTTService.getSubdelegacionUsuario(usuarioSSO.getSubdelegacion().longValue());
					usuarioF.setSubdelegacion(subdelegacion);
					usuarioF.setDelegacion(subdelegacion.getDelegacion());
				}
				usuario.setUsuarioFuncionario(usuarioF);

			}
		}

		return usuario;
	}

	@RequestMapping(value = "/consultar", method = {RequestMethod.GET, RequestMethod.POST})
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
	 * @param folio
	 * @return
	 */
	// /{folio} //@PathVariable
	@RequestMapping(value = "/consultar/findEscritoDesacuerdoFolio", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView findEscritoDesacuerdoFolio(HttpServletResponse response, HttpServletRequest request, @RequestParam(required = true) String folio) {
		ModelAndView model = new ModelAndView(VISTA_DETALLE_CONSULTA_VENTANILLA);
		TramiteEscritoDesacuerdo escritoDetalle = null;
		try {
			escritoDetalle = consultaEscritoDesacuerdoServiceRemote.findEscritoDesacuerdoFolio(folio);
			if (escritoDetalle != null) {
				if(escritoDetalle.getAnVigencia() != null){
					LOGGER.debug("Se consulta si tiene motivo id: "+escritoDetalle.getMotivosDesacuerdo().getIdMotivoDes());
				try {
					List<MotivosDesacuerdo> optMotivos = consultaEscritoDesacuerdoServiceRemote.getMotivosDesacuerdoList((escritoDetalle.getMotivosDesacuerdo().getIdMotivoDes()).intValue());
					escritoDetalle.getMotivosDesacuerdo().setDescMotivoDes(optMotivos.get(0).getDescMotivoDes());
				}catch (RiesgosTrabajoException e) {
					e.printStackTrace();
				}
				}

				model.addObject("escritoDetalle", escritoDetalle);

				DomicilioEscritoDesacuerdo domiEsc= consultaEscritoDesacuerdoServiceRemote.findDomEscritoDesacuerdo(Long.valueOf(escritoDetalle.getTramiteId()));
				if(domiEsc != null){
					model.addObject("domicilioEscrito", domiEsc);
				}
				LOGGER.debug("Se termino la consulta por folio");
			}
		} catch (RiesgosTrabajoException e) {
			log.error("Error al recuperar la consulta por folio: {}" + e.getMessage());
			e.printStackTrace();
		}

		if (escritoDetalle == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			LOGGER.debug("falló la consulta por folio");
		}

		model.addObject(TIPO_CONSULTA, "findEscritoDesacuerdoFolio");
		return model;
	}

	@RequestMapping(value = "/consultar/findEscritoDesacuerdoRegPatron", method = {
			RequestMethod.GET, RequestMethod.POST})
	public String findEscritoDesacuerdoRegPatron(Model model,
			 HttpServletResponse response, HttpServletRequest request,
			 HttpSession session,
			 @RequestParam(required = false) String registroPatronal,
			 @RequestParam(required = false) String fechaInicio,
			 @RequestParam(required = false) String fechaFin,
			 @RequestParam(required = false) Long delegacion,
			 @RequestParam(required = false) Long subdelegacion) {

		Usuario usuario = (Usuario) session.getAttribute("usuario");

		listaEscrito = null;
		Date fechaInicioDate = null;
		Date fechaFinDate = null;

		try {
			if (StringUtils.isNotBlank(fechaInicio) && !fechaInicio.equals("0"))
				fechaInicioDate = FORMATO_FECHA.parse(fechaInicio);
			if (StringUtils.isNotBlank(fechaFin) && !fechaFin.equals("0"))
				fechaFinDate = FORMATO_FECHA.parse(fechaFin);
		} catch (ParseException e) {
			log.error("No fue posible parsear la fecha", e);
		}

		PerfilUsuario perfilUsuarioVO = usuario.getPerfilUsuario();
		Delegacion delegacionVO = null;
		Subdelegacion subdelegacionVO = null;

		if (usuario.getUsuarioFuncionario().getDelegacion() != null && usuario.getUsuarioFuncionario().getDelegacion().getId() != null) {
			delegacionVO = usuario.getUsuarioFuncionario().getDelegacion();
		}

		if (usuario.getUsuarioFuncionario().getSubdelegacion() != null && usuario.getUsuarioFuncionario().getSubdelegacion().getId() != null) {
			subdelegacionVO = usuario.getUsuarioFuncionario().getSubdelegacion();
		}

		try {
			listaEscrito = consultaEscritoDesacuerdoServiceRemote.findEscritoDesacuerdoRegPatronGral(registroPatronal, delegacionVO, subdelegacionVO, fechaInicioDate, fechaFinDate, perfilUsuarioVO);
		} catch (RiesgosTrabajoException e) {
			e.printStackTrace();
		}
		model.addAttribute("listaEscrito", listaEscrito);

		if (registroPatronal.isEmpty()) {
			model.addAttribute("registroPatronal", MASIVA);
		}else {
			model.addAttribute("registroPatronal", registroPatronal);
		}

		model.addAttribute(TIPO_CONSULTA, "findEscritoDesacuerdoRegPatron");

		return VISTA_LISTA_CONSULTA_VENTANILLA;
	}


	@RequestMapping(value = "/consultar/findEscritoDesacuerdoPeriodo", method = {RequestMethod.GET, RequestMethod.POST})
	public String findEscritoDesacuerdoPeriodo(Model model,
											   HttpServletResponse response, HttpServletRequest request,
											   @RequestParam(required = true) String fechaInicio,
											   @RequestParam(required = true) String fechaFin,
											   @RequestParam(required = false) Long delegacion,
											   @RequestParam(required = false) Long subdelegacion) {

		listaEscrito = null;

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


	private Long getIdDelegacionSubdelegacion(Long id) {

		boolean diferenteZero = id != null && !id.equals(0l);

		if (diferenteZero) {
			return id;
		}
		return null;
	}

	private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat(
			"dd/MM/yyyy");

	@RequestMapping(value = "/consultar/comunes/getPeriodos", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> getPeriodos() {
		Map<String, Object> periodos = new HashMap<String, Object>();
		Calendar fecha = Calendar.getInstance();

		periodos.put("actual", FORMATO_FECHA.format(fecha.getTime()));
		return periodos;
	}

	@RequestMapping(value = "/consultar/generaReporteExcelEscrtoDesacuerdo")
	public void generaReporteEscritosDesacuerdos(HttpServletResponse response) throws Exception {
		byte[] reporteExcel;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			reporteExcel = consultaEscritoDesacuerdoServiceRemote.generaReporteEscritoDesacuerdo(listaEscrito);
			InputStream inputStream = new ByteArrayInputStream(reporteExcel);
			response.addHeader("Content-Disposition", "attachment; filename=ReporteEscritosDesacuerdo" + "_"
					+ sdf.format(new Date()) + ".xlsx");
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		} catch(IOException e){
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/diasFestivos", method = RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object> getDiasFestivos() throws RiesgosTrabajoException {
		Map<String, Object> festivos = new HashMap<String, Object>();
		List<DiasFestivos> diasFestivos = new ArrayList<DiasFestivos>();

		diasFestivos = consultaEscritoDesacuerdoServiceRemote.getDiasFestivos();
		festivos.put("actual", diasFestivos);
		return festivos;
	}
}
