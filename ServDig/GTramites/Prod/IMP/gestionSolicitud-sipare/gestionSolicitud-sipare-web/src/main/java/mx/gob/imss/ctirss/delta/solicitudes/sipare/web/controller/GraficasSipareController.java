package mx.gob.imss.ctirss.delta.solicitudes.sipare.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.solicitudes.sipare.service.interfaces.SipareBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.solicitudes.sipare.service.model.GraficaSolicitudesWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/grafica")
public class GraficasSipareController extends AbstractController {

	@Autowired
	private SipareBusinessServiceRemote sipareBusinessService;

	@RequestMapping(value = "/{identificador}/init", method = RequestMethod.GET)
	public @ResponseBody
	List<GraficaSolicitudesWrapper> initGraficas(
			@PathVariable String identificador) {
				
		return sipareBusinessService.obtenerGraficas(identificador, crearObjFiltros());
	}

	@RequestMapping(value = "/{identificador}", method = RequestMethod.GET)
	public @ResponseBody
	GraficaSolicitudesWrapper actualizarGraficas(
			@PathVariable String identificador) {

		return this.sipareBusinessService.obtenerGrafica(identificador, crearObjFiltros());
	}

	private FiltroSolicitud crearObjFiltros() {

		FiltroSolicitud filtros = new FiltroSolicitud();

		Date fechaInicio = DateUtils.dateToDateConFormato("01/09/2014","dd/MM/yyyy");
		Date fechaFin = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaFin);
		
		filtros.setFechaInicioPresentacion(fechaInicio);
		filtros.setFechaFinPresentacion(fechaFin);

		return filtros;
	}

}
