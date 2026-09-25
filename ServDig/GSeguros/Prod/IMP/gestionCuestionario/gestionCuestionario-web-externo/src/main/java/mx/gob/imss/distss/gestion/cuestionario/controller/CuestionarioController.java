package mx.gob.imss.distss.gestion.cuestionario.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.distss.gestion.cuestionario.excepcion.CuestionarioNoExisteException;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Cuestionario;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Opcion;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Pregunta;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Respuesta;
import mx.gob.imss.distss.gestion.cuestionario.modelo.RespuestasCuestionario;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Seccion;
import mx.gob.imss.distss.gestion.cuestionario.modelo.TipoRespuesta;
import mx.gob.imss.distss.gestion.cuestionario.modelo.TipoRespuestaElementEnum;
import mx.gob.imss.distss.gestion.cuestionario.modelo.TramiteCuestionarioDummy;
import mx.gob.imss.distss.gestion.cuestionario.service.interfaces.CuestionarioServiceBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/cuestionario")
public class CuestionarioController extends AbstractController {

	private static final String MSG_REQUERIDO = "Favor de contestar, pregunta requerida";
	private static final String HTTP_ERROR_CODE = "CODIGO_HTTP";
	private static final String OBJ_RESPUESTA = "RESPUESTA";
	private static final String SUMA_RESPUESTAS = "SUMATORIA";

	@Autowired
	private CuestionarioServiceBusinessRemote cuestionarioServiceBusiness;

	@RequestMapping(value = "/obtener", method = RequestMethod.POST)
	public String obtenerCuestionario(Model model,
			@RequestParam Integer idCuestionario,
			HttpServletResponse response) {

		Cuestionario cuestionario = null;

		try {
			cuestionario = cuestionarioServiceBusiness
					.obtenerCuestionario(idCuestionario);

		} catch (CuestionarioNoExisteException e) {
			cuestionario = new Cuestionario();
			cuestionario.setErrorFormGeneral(e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		model.addAttribute("cuestionario", cuestionario);

		return "crearCuestionario";
	}

	@RequestMapping(value = "/validar", method = RequestMethod.POST)
	public @ResponseBody RespuestasCuestionario calificarCuestionario(
			@RequestBody RespuestasCuestionario respuestasCuestionario,
			HttpServletResponse response) {

		this.log.debug("Respuestas -> " + respuestasCuestionario);

		int sumaRespuestas = 0;
		int responseStatus = 0;
		int responseStatusAux = 0;
		List<Respuesta> respuestas = respuestasCuestionario.getRespuestas();
		Map<String, Object> validacion = null;
		Cuestionario cuestionario = null;
		Respuesta respuesta = null;
		List<Pregunta> preguntas = new ArrayList<Pregunta>();
		
		try {
			cuestionario = this.cuestionarioServiceBusiness
					.obtenerCuestionario(respuestasCuestionario
							.getTipoCuestionario());

			for (Seccion seccion : cuestionario.getSecciones()) {
				preguntas.addAll(seccion.getPreguntas());
			}

			this.log.debug("Total de preguntas a calificar " + preguntas.size()
					+ " para el tipo de cuestionario "
					+ respuestasCuestionario.getTipoCuestionario());

			for (Pregunta pregunta : preguntas) {
				
				respuesta = obtenerRespuesta(respuestas, pregunta.getClave());
				
				validacion = validarRespuestaPregunta(pregunta, respuesta);
				responseStatusAux = (Integer)validacion.get(HTTP_ERROR_CODE);
				
				if (responseStatusAux > 0) {
					respuesta = (Respuesta) validacion.get(OBJ_RESPUESTA);
					respuestas.add(respuesta);
					responseStatus = responseStatusAux;
				} else {
					sumaRespuestas += (Integer) validacion.get(SUMA_RESPUESTAS);
					
					/*
					 * Se busca si alguna(s) de las opciones de respuesta
					 * tiene(n) preguntas dependientes, de ser así se checa si
					 * son obligatorias para validarse siempre y cuando la
					 * opción de respuesta a la pregunta principal haya sido
					 * contestada la que contiene las dependencias
					 */
					for (Opcion opcion : pregunta.getOpciones()) {
						if (respuesta != null) {
							for (Opcion opcRespuesta : respuesta.getValores()) {
								if (opcRespuesta.getClave()== opcion.getClave() 
									&& opcion.isHabilitarDependencia()
									&& !CollectionUtils.isEmpty(opcion.getDependencias())) {
									Respuesta respDep = null;
									for (Pregunta preguntaDependiente : opcion.getDependencias()) {
										if (preguntaDependiente.isObligatoria()) {
											respDep = obtenerRespuesta(respuestas, preguntaDependiente.getClave());
											
											validacion = validarRespuestaPregunta(preguntaDependiente, respDep);
											responseStatus = (Integer)validacion.get(HTTP_ERROR_CODE);
											
											if (responseStatus > 0) {
												respDep = (Respuesta) validacion.get(OBJ_RESPUESTA);
												respuestas.add(respDep);
											} else {
												sumaRespuestas += (Integer) validacion.get(SUMA_RESPUESTAS);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (CuestionarioNoExisteException e) {
			this.log.error(e);
			responseStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}

		if (responseStatus > 0) {
			response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
		} else {
			respuestasCuestionario.setSumatoriaRespuestas(sumaRespuestas);
		}

		return respuestasCuestionario;
	}

	@RequestMapping(value = "/test")
	public String initCuestionarioTest(Model model) {

		model.addAttribute("tramiteDummy", new TramiteCuestionarioDummy());
		
		return "test";
	}
	
	@RequestMapping(value = "/test/calificar")
	public String calificarCuestionarioTest(Model model,
			@ModelAttribute TramiteCuestionarioDummy tramiteDummy) {
		
		this.log.debug("Forma para calificar cuestionario -> " + tramiteDummy);
		
		model.addAttribute("tramiteDummy", tramiteDummy);
		
		return "test";
	}
	
	private Respuesta obtenerRespuesta(List<Respuesta> respuestas,
			int clavePregunta) {
		
		Respuesta respuesta = new Respuesta();
		respuesta.setCvePregunta(clavePregunta);
		int index = respuestas.indexOf(respuesta);

		if (index > -1) {
			respuesta = respuestas.get(index);
		} else {
			respuesta = null;
		}
		
		return respuesta;
	}
	
	private Map<String, Object> validarRespuestaPregunta(Pregunta pregunta,
			Respuesta respuesta) {

		int sumaRespuestas = 0;
		int responseStatus = 0;
		
		Map<String, Object> validacion = new HashMap<String, Object>();
		TipoRespuesta tipoRespuesta = pregunta.getTipoRespuesta();
		
		if (tipoRespuesta.getClave() == TipoRespuestaElementEnum.BOOLEANA.getClave()
				|| tipoRespuesta.getClave() == TipoRespuestaElementEnum.MULTIPLE_BOOLEANA.getClave()) {

			if (pregunta.isObligatoria() && respuesta == null) {
				respuesta = new Respuesta();
				respuesta.setCvePregunta(pregunta.getClave());
				respuesta.setErrorFormGeneral(MSG_REQUERIDO);
				responseStatus = HttpServletResponse.SC_PRECONDITION_FAILED;
			} else if (respuesta != null) {
				/*
				 * Para este tipo de preguntas siempre es sólo un valor
				 * de respuesta
				 */
				sumaRespuestas += respuesta.getValores().get(0).getValor();
			}
		} else if (tipoRespuesta.getClave() == TipoRespuestaElementEnum.MULTIPLE_PLURAL.getClave()) {
			if (pregunta.isObligatoria() && respuesta == null) {
				respuesta = new Respuesta();
				respuesta.setCvePregunta(pregunta.getClave());
				respuesta.setErrorFormGeneral(MSG_REQUERIDO);
				responseStatus = HttpServletResponse.SC_PRECONDITION_FAILED;
			} else if (respuesta != null) {
				/*
				 * Para este tipo de preguntas la respuesta puede
				 * contener más de un valor
				 */
				for (Opcion valor : respuesta.getValores()) {
					sumaRespuestas += valor.getValor();
				}
			}
		} else if (tipoRespuesta.getClave() == TipoRespuestaElementEnum.SELECCION.getClave()) {
			if (pregunta.isObligatoria() && respuesta == null) {
				respuesta = new Respuesta();
				respuesta.setCvePregunta(pregunta.getClave());
				respuesta.setErrorFormGeneral(MSG_REQUERIDO);
				responseStatus = HttpServletResponse.SC_PRECONDITION_FAILED;
			} else if (respuesta != null) {
				/*
				 * Para este tipo de preguntas siempre es sólo un valor de
				 * respuesta
				 */
				int valor = respuesta.getValores().get(0).getValor();
				if (valor != -1) {
					sumaRespuestas += valor;
				}
			}
		}
		
		validacion.put(OBJ_RESPUESTA, respuesta);
		validacion.put(HTTP_ERROR_CODE, responseStatus);
		validacion.put(SUMA_RESPUESTAS, sumaRespuestas);
		
		return validacion;
	}
}