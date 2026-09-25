package mx.gob.imss.ctirss.delta.gestion.solicitud.controller;

import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.DashboardBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/dashboard")
@Controller
public class DashboardController extends AbstractController {

	@Autowired
	private DashboardBusinessServiceRemote dashboardBusinessService;

	@RequestMapping(method = RequestMethod.GET)
	public String init() {
		return "dashboard.home";
	}

	@RequestMapping(value = "/getGranTotal", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getGranTotal() {

		return this.dashboardBusinessService
				.obtenerGranTotal();
	}
	
	@RequestMapping(value = "/getTotalPorcentaje", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getTotalPorcentaje(
			@RequestBody GraficaRequest request) {

		this.log.debug("Se recibe JSON -> " + request);

		return this.dashboardBusinessService
				.obtenerTotalPorcentajesTramite(request);
	}

	@RequestMapping(value = "/getCifrasOrigen", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCifrasOrigen(
			@RequestBody GraficaRequest request) {

		this.log.debug("Se recibe JSON -> " + request);

		return this.dashboardBusinessService.obtenerCifrasPorOrigen(request);
	}
	
	@RequestMapping(value = "/getRankingTramites", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getRankingTramites(
			@RequestBody GraficaRequest request) {

		this.log.debug("Se recibe JSON -> " + request);

		return this.dashboardBusinessService.obtenerRankingTramites(request);
	}
	
	@RequestMapping(value = "/getCifrasPorEstado", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCifrasPorEstado(
			@RequestBody GraficaRequest request) {

		this.log.debug("Se recibe JSON -> " + request);

		return this.dashboardBusinessService.obtenerCifrasPorEstado(request);
	}
	
	@RequestMapping(value = "/getCifrasAtendidasPeriodos", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCifrasAtendidasPeriodos(
			@RequestBody GraficaRequest request) {

		this.log.debug("Se recibe JSON -> " + request);

		return this.dashboardBusinessService.obtenerCifrasAtendidasPeriodo(request);
	}
	
	@RequestMapping(value = "/getCifrasTipoTramiteOrigen", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCifrasTipoTramiteOrigen(
			@RequestBody GraficaRequest request) {

		this.log.debug("Se recibe JSON -> " + request);

		return this.dashboardBusinessService.obtenerCifrasTipoTramiteOrigen(request);
	}
}