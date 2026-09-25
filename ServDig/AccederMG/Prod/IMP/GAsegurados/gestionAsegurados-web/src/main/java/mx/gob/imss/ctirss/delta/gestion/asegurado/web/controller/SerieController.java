package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAsignarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorCrearFoliosDeSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorGuardarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NumeroDeSeriePorAnioRegistroExisteException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SerieNoExisteException;
import mx.gob.imss.ctirss.delta.exception.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.web.validator.RegistroSeriesValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/serie")
public class SerieController extends AbstractController {

	@Autowired
	private transient PersonaBusinessRemote personaBusiness;
	@Autowired
	private transient SerieServiceBusinessRemote serieBusiness;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;

	/**
	 * Metodo para obtener una lista de series para llenar el combo de la
	 * pantalla de datos complementarios de asegurados
	 * 
	 * @param codigo
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/get/series", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> getSeries(@RequestParam String delegacion,
			@RequestParam String subdelegacion, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();

		Long d = delegacion.equals("") ? null : Long.getLong(delegacion);
		Long sd = subdelegacion.equals("") ? null : Long.getLong(subdelegacion);

		try {
			List<Serie> series = personaBusiness.getSeriesNss(d, sd);

			if (series != null && series.size() > 0) {
				this.log.debug("series[*** " + series + "***]");
				result.put("series", series);
			} else {
				throw new SeriesNoLocalizadasException("No se haigaron Series");
			}

		} catch (SeriesNoLocalizadasException e) {
			this.procesarErrorDeNegocio(e, result, response);
		}

		return result;
	}

	/**
	 * Metodo para obtener una sola serie a partir de un idSerie contenido en
	 * otro objeto Serie
	 * 
	 * @param codigo
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/get/serie", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> getSeries(@RequestParam Long idSerie,
			HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			Serie serieSolicitada = new Serie();
			serieSolicitada.setIdSerie(idSerie);

			Serie serie = personaBusiness.getSerie(serieSolicitada);

			if (serie != null) {
				this.log.debug("serie --> " + serie);
				result.put("serie", serie);
			} else {
				throw new SeriesNoLocalizadasException(
						"No se haigo ninguna Serie");
			}

		} catch (SeriesNoLocalizadasException e) {
			this.procesarErrorDeNegocio(e, result, response);
		}

		return result;
	}

	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public String consultarSeriesInicio(final Model model,
			HttpSession session, HttpServletRequest request) {

		AsignacionSerieNSS asignacionSerie = new AsignacionSerieNSS();
		List<AsignacionSerieNSS> listAsignacionInicial = new ArrayList<AsignacionSerieNSS>();

		try {
			listAsignacionInicial = serieBusiness.obtenerSeriesActivas(null, null, null, null);
		} catch (Exception e) {
			log.error(e);
		}

		model.addAttribute("listAsignacion", listAsignacionInicial);
		model.addAttribute("asignacionSerie", asignacionSerie);

		return "serie.inicio";
	}

	@RequestMapping(value = "/inicio", method = RequestMethod.POST)
	public String consultarSeries(
			final @ModelAttribute("asignacionSerie") AsignacionSerieNSS asignacionSerie,
			BindingResult result, Model modelo, HttpSession session) {
		List<AsignacionSerieNSS> listAsignacionInicial = new ArrayList<AsignacionSerieNSS>();

		String strCveDelegacion = asignacionSerie.getDelegacion().getClave();
		Long cveDelegacion = null;
		if (StringUtils.isNotBlank(strCveDelegacion)
				&& !strCveDelegacion.equals("-1")) {
			cveDelegacion = Long.valueOf(strCveDelegacion);
		}

		String strCveSubdelegacion = asignacionSerie.getSubdelegacion()
				.getClave();
		Long cveSubdelegacion = null;
		if (StringUtils.isNotBlank(strCveSubdelegacion)
				&& !strCveSubdelegacion.equals("-1")) {
			cveSubdelegacion = Long.valueOf(strCveSubdelegacion);
		}

		Integer intAnioRegistro = asignacionSerie.getSerie().getAnioRegistro();
		Long anioRegistro = null;
		if (intAnioRegistro != null && !intAnioRegistro.equals(-1)) {
			anioRegistro = Long.valueOf(intAnioRegistro);
		}

		Integer idTipoSerie = null;
		if (asignacionSerie.getSerie().getTipoSerie().getIdTipoSerie() != -1) {
			idTipoSerie = asignacionSerie.getSerie().getTipoSerie().getIdTipoSerie();
		}
		
		try {
			listAsignacionInicial = serieBusiness.obtenerSeriesActivas(
					cveDelegacion, cveSubdelegacion, anioRegistro, idTipoSerie);
		} catch (Exception e) {
			log.error(e);
		}

		modelo.addAttribute("listAsignacion", listAsignacionInicial);
		modelo.addAttribute("asignacionSerie", asignacionSerie);

		return "serie.inicio";
	}

	@RequestMapping(value = "/crearSerie/inicio", method = RequestMethod.GET)
	public String crearSeriesInicio(final Model modelo) {
		AsignacionSerieNSS asignacionSerie = new AsignacionSerieNSS();
		List<AsignacionSerieNSS> listAsignacionInicial = new ArrayList<AsignacionSerieNSS>();

		try {
			listAsignacionInicial = serieBusiness.obtenerSeriesActivas(null,
					null, null, null);
		} catch (Exception e) {
			log.error(e);
		}

		modelo.addAttribute("listAsignacion", listAsignacionInicial);
		modelo.addAttribute("asignacionSerie", asignacionSerie);

		return "serie.crear.inicio";
	}

	@RequestMapping(value = "/crearSerie/crear", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> crearSeries(
			final @RequestBody AsignacionSerieNSS asignacionSerie,
			HttpSession session,
			HttpServletResponse response) {

		Map<String, Object> resultado = new HashMap<String, Object>();
		
		StringBuffer mensaje = new StringBuffer();

		Delegacion delegacion = asignacionSerie.getDelegacion(); 
		Subdelegacion subdelegacion = asignacionSerie.getSubdelegacion();
		
		if (delegacion != null && StringUtils.isNotBlank(delegacion.getClave())
				&& !delegacion.getClave().equals("-1")) {
			asignacionSerie.getDelegacion().setId(
					Long.valueOf(delegacion.getClave()));
		} else {
			asignacionSerie.setDelegacion(null);
		}
		
		if (subdelegacion != null
				&& StringUtils.isNotBlank(subdelegacion.getClave())
				&& !subdelegacion.getClave().equals("-1")) {
			asignacionSerie.getSubdelegacion().setId(
					Long.valueOf(subdelegacion.getClave()));
		} else {
			asignacionSerie.setSubdelegacion(null);
		}
		
		final Errors errors = new BindException(asignacionSerie, "model");
		new RegistroSeriesValidator().validate(asignacionSerie, errors);

		if (!errors.hasErrors()) {
			try {
				Serie serieNueva = serieBusiness.crearSerie(asignacionSerie);

				mensaje.append("La serie ").append(serieNueva.getIdSerie())
						.append(" se ha creado exitosamente");
				
				resultado.put("MENSAJE_EXITO", mensaje.toString());
			} catch (NumeroDeSeriePorAnioRegistroExisteException e) {
				this.procesarErrorDeNegocio(e, resultado, response);
			} catch (ErrorGuardarSerieException e) {
				this.procesarErrorDeNegocio(e, resultado, response);
			} catch (ErrorCrearFoliosDeSerieException e) {
				this.procesarErrorDeNegocio(e, resultado, response);
			} catch (ErrorAsignarSerieException e) {
				this.procesarErrorDeNegocio(e, resultado, response);
			}
		} else {
			this.procesaErroresDeCaptura(errors, resultado, response);
		}

		return resultado;
	}

	@RequestMapping(value = "/busqueda/{idSerie}", method = RequestMethod.GET)
	public String buscarDetalleSerie(final @PathVariable Long idSerie,
			final Model modelo) {
		StringBuffer mensaje = new StringBuffer();

		Serie serie = new Serie();
		serie.setIdSerie(idSerie);
		AsignacionSerieNSS serieFound = null;

		try {

			serieFound = serieBusiness.obtenerDetalleDeSerie(serie);
		} catch (SerieNoExisteException e) {
			mensaje.append("Ocurrio un error al buscar el detalle de la serie ")
					.append(idSerie);

			modelo.addAttribute("mensaje", mensaje);
			log.error(e);
		}

		modelo.addAttribute("asignacion", serieFound);

		return "serie.detalle";
	}

	@ModelAttribute("listAnioRegistro")
	public Map<String, String> populateAnioRegistroList() {
		Map<String, String> listAnioRegistro = new LinkedHashMap<String, String>();

		for (Integer index = 0; index < 10; index++) {
			StringBuffer sb = new StringBuffer();
			sb.append("0").append(index);
			listAnioRegistro.put(sb.toString(), sb.toString());
		}
		for (Integer index = 10; index < 100; index++) {
			listAnioRegistro.put(index.toString(), index.toString());
		}

		return listAnioRegistro;
	}

	@ModelAttribute("listNumeroSerie")
	public Map<String, String> populateNumeroSerieList() {
		Map<String, String> listNumeroSerie = new LinkedHashMap<String, String>();

		for (Integer index = 1; index < 10; index++) {
			StringBuffer sb = new StringBuffer();
			sb.append("0").append(index);
			listNumeroSerie.put(sb.toString(), sb.toString());
		}
		for (Integer index = 10; index < 100; index++) {
			listNumeroSerie.put(index.toString(), index.toString());
		}

		return listNumeroSerie;
	}
}
