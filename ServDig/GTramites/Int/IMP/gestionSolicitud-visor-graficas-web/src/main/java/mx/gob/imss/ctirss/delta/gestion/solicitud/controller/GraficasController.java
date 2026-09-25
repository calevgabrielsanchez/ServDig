package mx.gob.imss.ctirss.delta.gestion.solicitud.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.GraficasSolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.util.GraficasConfig;
import mx.gob.imss.ctirss.delta.gestion.solicitud.util.GraficasUtil;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaRequest;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/grafica")
public class GraficasController extends AbstractController {
	
	@Autowired
	private GraficasSolicitudServiceBusinessRemote graficasSolicitudServiceBusiness;

	@RequestMapping(value = "/{identificador}/init", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> initGraficas(
			@PathVariable String identificador) {
		
		List<String> idsGraficas = Arrays.asList(GraficasConfig.getProperty(identificador + ".graficas").split("\\|"));
		
		List<Map<String, Object>> graficas = new LinkedList<Map<String,Object>>();
	
		for (String idGrafica : idsGraficas) {
			graficas.add(generarGrafica(idGrafica));
		}
		
		return graficas;
	}
	
	@RequestMapping(value = "/{identificador}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> actualizarGraficas(
			@PathVariable String identificador) {
		
		return generarGrafica(identificador);
		
	}
	
	private Map<String, Object> generarGrafica(String idGrafica) {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, Object> grafica = null;
		
		GraficaRequest request = new GraficaRequest();
		try { 
			String tipoAgrupacion = GraficasConfig.getProperty(idGrafica + ".tipoAgrupacion");
			
			if (StringUtils.isNotBlank(tipoAgrupacion)
					&& tipoAgrupacion.equals("diaMesActual")) {
				Date fechaActual = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fechaActual);
				calendar.set(Calendar.DATE, 1);

				request.setFechaInicio(calendar.getTime());
			} else {
				request.setFechaInicio(df.parse(GraficasConfig.getProperty(idGrafica + ".fechaInicio")));
			}
			
			request.setIdGrafica(idGrafica);
			request.setTipoGrafica(Integer.parseInt(GraficasConfig.getProperty(idGrafica + ".tipo")));
			request.setFechaFin(new Date());
			request.setTipoAgrupacion(tipoAgrupacion);
			request.setOrigenes(this.obtenerListaPropiedadesInteger(idGrafica + ".origenes"));
			request.setEstados(this.obtenerListaPropiedadesInteger(idGrafica + ".estados"));
			request.setTramites(this.obtenerListaPropiedadesInteger(idGrafica + ".tramites"));
		
			request.setLlaveEjeX(GraficasConfig.getProperty(idGrafica + ".xkey"));
			request.setLabelsEjeX(GraficasConfig.getProperty(idGrafica + ".xkey.labels"));
			request.setCondicionesEjeX(GraficasConfig.getProperty(idGrafica + ".xkey.conditions"));
			
			request.setLlaveEjeY(GraficasConfig.getProperty(idGrafica + ".ykeys"));
			request.setLabelsEjeY(GraficasConfig.getProperty(idGrafica + ".ykeys.labels"));
			request.setCondicionesEjeY(GraficasConfig.getProperty(idGrafica + ".ykeys.conditions"));
			
			grafica = new LinkedHashMap<String, Object>();
			grafica.put("identificador", idGrafica);
			grafica.put("tipo", Integer.parseInt(GraficasConfig.getProperty(idGrafica + ".tipo")));
			grafica.put("nombre", GraficasConfig.getProperty(idGrafica + ".nombre"));
			grafica.put("descripcion", GraficasConfig.getProperty(idGrafica + ".descripcion"));
			grafica.put("url", generarUrl(idGrafica));
			grafica.put("xKey", GraficasConfig.getProperty(idGrafica + ".xkey"));
			grafica.put("yKeys", this.obtenerListaPropiedadesString(idGrafica + ".ykeys"));
			grafica.put("labels", this.obtenerListaPropiedadesString(idGrafica + ".ykeys.labels"));
			grafica.put("tipoFormatoEjeX", Integer.parseInt(GraficasConfig.getProperty(idGrafica + ".tipoFormatoEjeX")));
			grafica.put("mostrarDetalleDia", Boolean.parseBoolean(GraficasConfig.getProperty(idGrafica + ".mostrarDetalleDia")));
			
			List<GraficaResponse> datos = this.graficasSolicitudServiceBusiness.getCifrasGrafica(request);
			
			if (CollectionUtils.isEmpty(datos)) {
				grafica.put("errorFormGeneral", "SIN INFORMACIÓN PARA MOSTRAR");
			} else {
				grafica.put("data", GraficasUtil.getJson(request, datos));
			}
			
		} catch (ParseException e) {
			this.log.error(e);
		}
		
		return grafica;	
	}
	
	private List<Integer> obtenerListaPropiedadesInteger(String key) {

		List<Integer> propiedades = null;

		if (StringUtils.isNotBlank(GraficasConfig.getProperty(key))) {
			propiedades = new ArrayList<Integer>();
		
			for (String propiedad : GraficasConfig.getProperty(key).split("\\|")) {
				propiedades.add(Integer.valueOf(propiedad));
			}
		}

		return propiedades;
	}

	private List<String> obtenerListaPropiedadesString(String key) {
		List<String> propiedades = null;

		if (StringUtils.isNotBlank(GraficasConfig.getProperty(key))) {
			propiedades = new ArrayList<String>();

			for (String propiedad : GraficasConfig.getProperty(key).split("\\|")) {
				propiedades.add(propiedad);
			}
		}

		return propiedades;
	}
	
	private String generarUrl(String id) {
		String url = GraficasConfig.getProperty(id + ".url");
		
		if(StringUtils.isBlank(url)) {
			url = "/gestionSolicitud-visor-graficas-web/grafica/" + id;
		}
		
		return url;
	}
}