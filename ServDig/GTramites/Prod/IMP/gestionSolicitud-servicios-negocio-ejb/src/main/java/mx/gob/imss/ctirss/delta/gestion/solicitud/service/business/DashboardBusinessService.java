package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.DashboardEntityServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.DashboardBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaRequest;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaResponse;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.PeriodoDashboardEnum;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;

@Stateless(mappedName = "dashboardBusinessService")
public class DashboardBusinessService extends AbstractServiceBusiness implements
		DashboardBusinessServiceRemote {
	
	private static final String TOTAL_KEY = "total";
	private static final String REGISTRADAS_KEY = "registradas";
	private static final String ATENDIADAS_KEY = "atendidas";
	private static final String CANCELADAS_KEY = "canceladas";
	private static final String EN_PROCESO_KEY = "enProceso";
	private static final String RECHAZADAS_KEY = "rechazadas";
	
	private static final String PORCENTAJE_VS_GRAN_TOTAL = "porcentajeVsGranTotal";
	private static final String PORCENTAJE_REGISTRADAS_KEY = "porcentajeRegistradas";
	private static final String PORCENTAJE_ATENDIADAS_KEY = "porcentajeAtendidas";
	private static final String PORCENTAJE_CANCELADAS_KEY = "porcentajeCanceladas";
	private static final String PORCENTAJE_EN_PROCESO_KEY = "porcentajeEnProceso";
	private static final String PORCENTAJE_RECHAZADAS_KEY = "porcentajeRechazadas";
	
	private static final int NUM_DECIMALES = 2;
	
	@EJB
	private DashboardEntityServiceLocal dashboardEntityService;

	@Override
	public Map<String, Object> obtenerGranTotal() {
		Map<String, Object> respuesta = new LinkedHashMap<String, Object>();
	
		List<GraficaResponse> contadores = this.dashboardEntityService
				.getGranTotal();
		
		long total = 0;
		for(GraficaResponse contador : contadores) {
			total += contador.getTotal();
		}
		
		for(GraficaResponse contador : contadores) {
			contador.setPorcentaje(obtenerPorcentaje(contador.getTotal(), total, NUM_DECIMALES));
			contador.setPorcentajeCerrado((int)obtenerPorcentaje(contador.getTotal(), total, 0));
		}
		
		respuesta.put("data", contadores);
		respuesta.put("total", total);
		
		List<GraficaResponse> contadoresTop5 = this.dashboardEntityService
				.getRankingTramites(null, 5);
		
		respuesta.put("dataTop5", contadoresTop5);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("es-MX"));
		respuesta.put("hora", df.format(new Date()));
		
		
		return respuesta;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> obtenerTotalPorcentajesTramite(
			GraficaRequest request) {
		Map<String, Object> respuesta = new LinkedHashMap<String, Object>();
		
		List<GraficaResponse> contadores = this.dashboardEntityService
				.getContadores(request);
			
		Map<String, Object> detalleGlobal = new LinkedHashMap<String, Object>();
		detalleGlobal.put(TOTAL_KEY, new Long(0));
		detalleGlobal.put(REGISTRADAS_KEY, new Long(0));
		detalleGlobal.put(ATENDIADAS_KEY, new Long(0));
		detalleGlobal.put(CANCELADAS_KEY, new Long(0));
		detalleGlobal.put(EN_PROCESO_KEY, new Long(0));
		detalleGlobal.put(RECHAZADAS_KEY, new Long(0));
		
		respuesta.put(request.getIdGrafica(), detalleGlobal);
		
		String descTramite = null;
		
		for(GraficaResponse contador : contadores) {
			Map<String, Object> detalle = null;
			
			if(respuesta.get(Integer.toString(contador.getTramite())) == null) {
				detalle = new LinkedHashMap<String, Object>();
				detalle.put(TOTAL_KEY, new Long(0));
				detalle.put(REGISTRADAS_KEY, new Long(0));
				detalle.put(ATENDIADAS_KEY, new Long(0));
				detalle.put(CANCELADAS_KEY, new Long(0));
				detalle.put(EN_PROCESO_KEY, new Long(0));
				detalle.put(RECHAZADAS_KEY, new Long(0));		
								
				descTramite = contador.getDescTipoTramite().replace("DE DERECHOHABIENTE ", "");
				descTramite = descTramite.replace("E INSCRIPCIÓN EN EL SEGURO DE RIESGOS DE TRABAJO SRT", "PERSONA FÍSICA");
				descTramite = descTramite.replace("E INSCRIPCIÓN DE PERSONA MORAL EN EL SEGURO DE RIESGOS DE TRABAJO SRT", "PERSONA MORAL");
				descTramite = descTramite.replace("NÚMERO DE SEGURIDAD SOCIAL", "NSS");
				
				detalle.put("descTramite", descTramite);
				
				respuesta.put(Integer.toString(contador.getTramite()),
						detalle);
			} else {
				detalle = (Map<String, Object>) respuesta.get(Integer.toString(contador.getTramite()));
			}
			
			Long total = (Long) detalle.get(TOTAL_KEY);
			Long registradas = (Long) detalle.get(REGISTRADAS_KEY);
			Long atendidas = (Long) detalle.get(ATENDIADAS_KEY);
			Long canceladas = (Long) detalle.get(CANCELADAS_KEY);
			Long enProceso = (Long) detalle.get(EN_PROCESO_KEY);
			Long rechazadas = (Long) detalle.get(RECHAZADAS_KEY);
			
			Long totalGlobal = (Long) detalleGlobal.get(TOTAL_KEY);
			Long registradasGlobal = (Long) detalleGlobal.get(REGISTRADAS_KEY);
			Long atendidasGlobal = (Long) detalleGlobal.get(ATENDIADAS_KEY);
			Long canceladasGlobal = (Long) detalleGlobal.get(CANCELADAS_KEY);
			Long enProcesoGlobal = (Long) detalleGlobal.get(EN_PROCESO_KEY);
			Long rechazadasGlobal = (Long) detalleGlobal.get(RECHAZADAS_KEY);
			
			total += contador.getTotal();
			totalGlobal += contador.getTotal();
			
			if (contador.getEstado() == EstadoSolicitudEnum.REGISTRADA.getCodigo().intValue()) {
				registradas += contador.getTotal();
				registradasGlobal += contador.getTotal();
			} else if (contador.getEstado() == EstadoSolicitudEnum.ATENDIDA.getCodigo().intValue()) {
				atendidas += contador.getTotal();
				atendidasGlobal += contador.getTotal();
			} else if (contador.getEstado() == EstadoSolicitudEnum.CANCELADA.getCodigo().intValue()) {
				canceladas += contador.getTotal();
				canceladasGlobal += contador.getTotal();
			} else if (contador.getEstado() == EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().intValue()) {
				enProceso += contador.getTotal();
				enProcesoGlobal += contador.getTotal();
			} else if (contador.getEstado() == EstadoSolicitudEnum.RECHAZADA.getCodigo().intValue()) {
				rechazadas += contador.getTotal();
				rechazadasGlobal += contador.getTotal();
			}
			
			detalle.put(TOTAL_KEY, total);
			detalle.put(REGISTRADAS_KEY, registradas);
			detalle.put(ATENDIADAS_KEY, atendidas);
			detalle.put(CANCELADAS_KEY, canceladas);
			detalle.put(EN_PROCESO_KEY, enProceso);
			detalle.put(RECHAZADAS_KEY, rechazadas);
			
			detalleGlobal.put(TOTAL_KEY, totalGlobal);
			detalleGlobal.put(REGISTRADAS_KEY, registradasGlobal);
			detalleGlobal.put(ATENDIADAS_KEY, atendidasGlobal);
			detalleGlobal.put(CANCELADAS_KEY, canceladasGlobal);
			detalleGlobal.put(EN_PROCESO_KEY, enProcesoGlobal);
			detalleGlobal.put(RECHAZADAS_KEY, rechazadasGlobal);
		}
				
		for(Map.Entry<String, Object> entry : respuesta.entrySet()) {
			Map<String, Object> detalle = (Map<String, Object>) entry.getValue();
			long total = (Long) detalle.get(TOTAL_KEY);
			long granTotal = (Long) detalleGlobal.get(ATENDIADAS_KEY);
			
			float porcentajeVsGranTotal = 0;
			float porcentajeRegistradas = 0;
			float porcentajeAtendidas = 0;
			float porcentajeCanceladas = 0;
			float porcentajeEnProceso = 0;
			float porcentajeRechazadas = 0;
						
			for(Map.Entry<String, Object> entry2 : detalle.entrySet()) {
				if(entry2.getKey().equals(REGISTRADAS_KEY)) {
					porcentajeRegistradas = obtenerPorcentaje((Long) entry2.getValue(), total, NUM_DECIMALES);
				} else if(entry2.getKey().equals(ATENDIADAS_KEY)) {
					porcentajeAtendidas = obtenerPorcentaje((Long) entry2.getValue(), total, NUM_DECIMALES);
					porcentajeVsGranTotal = obtenerPorcentaje((Long) entry2.getValue(), granTotal, NUM_DECIMALES);
				} else if(entry2.getKey().equals(CANCELADAS_KEY)) {
					porcentajeCanceladas = obtenerPorcentaje((Long) entry2.getValue(), total, NUM_DECIMALES);
				} else if(entry2.getKey().equals(EN_PROCESO_KEY)) {
					porcentajeEnProceso = obtenerPorcentaje((Long) entry2.getValue(), total, NUM_DECIMALES);
				} else if(entry2.getKey().equals(RECHAZADAS_KEY)) {
					porcentajeRechazadas = obtenerPorcentaje((Long) entry2.getValue(), total, NUM_DECIMALES);
				} 
			}
			
			detalle.put(PORCENTAJE_VS_GRAN_TOTAL, porcentajeVsGranTotal);
			detalle.put(PORCENTAJE_REGISTRADAS_KEY, porcentajeRegistradas);
			detalle.put(PORCENTAJE_ATENDIADAS_KEY, porcentajeAtendidas);
			detalle.put(PORCENTAJE_CANCELADAS_KEY, porcentajeCanceladas);
			detalle.put(PORCENTAJE_EN_PROCESO_KEY, porcentajeEnProceso);
			detalle.put(PORCENTAJE_RECHAZADAS_KEY, porcentajeRechazadas);
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("es-MX"));
			detalle.put("hora", df.format(new Date()));
		}
		
		this.log.debug("Respuesta para dashboard -> " + respuesta.toString());
		
		return respuesta;
	}
	
	@Override
	public Map<String, Object> obtenerCifrasPorOrigen(GraficaRequest request) {
		
		Map<String, Object> respuesta = new LinkedHashMap<String, Object>();
		
		if (request.getPeriodo() == PeriodoDashboardEnum.ANUAL.getId()) {
			request.setTipoAgrupacion("meses");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.MENSUAL.getId()
				|| request.getPeriodo() == PeriodoDashboardEnum.SEMANAL.getId()) {
			request.setTipoAgrupacion("dias");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.HOY.getId()) {
			request.setTipoAgrupacion("dia");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			DateTime inicio = new DateTime(request.getFechaInicio().getTime());
			DateTime fin = new DateTime(request.getFechaFin().getTime());
			
			int diff = Days.daysBetween(inicio, fin).getDays();
			
			this.log.debug(diff + " días de diferencia entre fecha "
					+ request.getFechaInicio() + " y "
					+ request.getFechaFin());
			
			if (diff == 0) {
				// Es el mismo día
				request.setTipoAgrupacion("dia");
			} else if (diff > 0 && diff <= 31) {
				// Se elige un mes completo
				request.setTipoAgrupacion("dias");
			} else if (diff > 31 && diff < 540) {
				// Se eligió de un mes hasta año y medio
				request.setTipoAgrupacion("meses");
			} else {
				request.setTipoAgrupacion("anios");
			}
		}
		
		List<GraficaResponse> contadores = this.dashboardEntityService
				.getContadoresPorOrigen(request);
		this.agruparCifrasPorOrigen(contadores, request.getTipoAgrupacion(), respuesta);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("es-MX"));
		respuesta.put("hora", df.format(new Date()));
		
		return respuesta;
	}
	
	@Override
	public Map<String, Object> obtenerRankingTramites(GraficaRequest request) {
		
		Map<String, Object> respuesta = new LinkedHashMap<String, Object>(2);
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		Set<String> labels = new LinkedHashSet<String>();
		
		List<GraficaResponse> contadores = this.dashboardEntityService
				.getRankingTramites(request, 5);
		long total = 0L;
		
		for(GraficaResponse contador : contadores) {
			total += contador.getTotal();
		}
		
		for(GraficaResponse contador : contadores) {
			Map<String, Object> detalle = new LinkedHashMap<String, Object>();
			detalle.put("total", contador.getTotal());
			detalle.put("porcentaje",
					obtenerPorcentaje(contador.getTotal(), total, NUM_DECIMALES));
			data.put(contador.getDescTipoTramite(), detalle);
			labels.add(contador.getDescTipoTramite());
		}
		
		respuesta.put("data", data);
		respuesta.put("labels", labels);
		respuesta.put("total", total);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("es-MX"));
		respuesta.put("hora", df.format(new Date()));
		
		return respuesta;
	}
	
	@Override
	public Map<String, Object> obtenerCifrasPorEstado(GraficaRequest request) {
		
		Map<String, Object> respuesta = new LinkedHashMap<String, Object>(2);
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		Set<String> labels = new LinkedHashSet<String>();
		
		List<GraficaResponse> contadores = this.dashboardEntityService
				.getContadoresPorEstado(request);
		long total = 0L;
		
		for(GraficaResponse contador : contadores) {
			total += contador.getTotal();
		}
		
		for(GraficaResponse contador : contadores) {
			Map<String, Object> detalle = new LinkedHashMap<String, Object>();
			detalle.put("total", contador.getTotal());
			detalle.put("porcentaje",
					obtenerPorcentaje(contador.getTotal(), total, NUM_DECIMALES));
			data.put(contador.getDescEstadoSolicitud(), detalle);
			labels.add(contador.getDescEstadoSolicitud());
		}
		
		respuesta.put("data", data);
		respuesta.put("labels", labels);
		respuesta.put("total", total);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("es-MX"));
		respuesta.put("hora", df.format(new Date()));
		
		return respuesta;
	}
	
	@Override
	public Map<String, Object> obtenerCifrasAtendidasPeriodo(
			GraficaRequest request) {
		
		Map<String, Object> respuesta = new LinkedHashMap<String, Object>(2);
		
		if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
									
			DateTime inicio = new DateTime(request.getFechaInicio().getTime());
			DateTime fin = new DateTime(request.getFechaFin().getTime());
			
			Date fechaInicioAnterior = null;
			Date fechaFinAnterior = null;
			
			if (inicio.equals(inicio.dayOfYear().withMinimumValue())
					&& fin.equals(fin.dayOfYear().withMaximumValue())) {
				/*
				 * Se eligio el inicio y fin de año, se obtiene la diferencia
				 * en anios y se calcula el periodo anterior
				 */
				int diff = Years.yearsBetween(inicio, fin.plusDays(1)).getYears();
				
				this.log.debug(diff + " anios de diferencia entre fecha "
						+ request.getFechaInicio() + " y "
						+ request.getFechaFin());
									
				fechaInicioAnterior = inicio.plusYears(diff * -1).dayOfMonth().withMinimumValue().toDate();
				fechaFinAnterior = fin.plusYears(diff * -1).dayOfMonth().withMaximumValue().toDate();					
			} else if(inicio.equals(inicio.dayOfMonth().withMinimumValue()) 
					&& fin.equals(fin.dayOfMonth().withMaximumValue())){
				/*
				 * Se eligio el inicio y fin de mes, se obtiene la diferencia
				 * en meses y se calcula el periodo anterior
				 */					
				int diff = Months.monthsBetween(inicio, fin.plusDays(1)).getMonths();
				
				this.log.debug(diff + " meses de diferencia entre fecha "
						+ request.getFechaInicio() + " y "
						+ request.getFechaFin());
				
				fechaInicioAnterior = inicio.plusMonths(diff * -1).dayOfMonth().withMinimumValue().toDate();
				fechaFinAnterior = fin.plusMonths(diff * -1).dayOfMonth().withMaximumValue().toDate();					
			} else {
				/*
				 * Se eligio un periodo diferente, se calcula la diferencia
				 * en días para obtener le periodo anterior
				 */
				
				int diff = Days.daysBetween(inicio, fin).getDays();
				
				this.log.debug(diff + " días de diferencia entre fecha "
						+ request.getFechaInicio() + " y "
						+ request.getFechaFin());
				
				if (diff == 0) {
					fechaInicioAnterior = inicio.plusDays(-1).toDate();
					fechaFinAnterior = fechaInicioAnterior;
				} else {
					fechaInicioAnterior = inicio.plusDays((diff + 1) * -1).toDate();
					fechaFinAnterior = inicio.plusDays(-1).toDate();
				}
			}
			
			this.log.debug("fechaInicioAnterior -> " + fechaInicioAnterior);
			this.log.debug("fechaFinAnterior -> " + fechaFinAnterior);
			
			request.setFechaInicioAnterior(fechaInicioAnterior);
			request.setFechaFinAnterior(fechaFinAnterior);
			
			respuesta.put("fechaInicioAnterior", fechaInicioAnterior);
			respuesta.put("fechaFinAnterior", fechaFinAnterior);
		}
		
		List<GraficaResponse> contadores = this.dashboardEntityService
				.getContadoresAtendidasPeriodos(request);
				
		for(GraficaResponse contador : contadores) {
			respuesta.put(contador.getPeriodo(), contador.getTotal());
		}
		
		respuesta.put(
				"porcentaje",
				obtenerPorcentaje((Long) respuesta.get("ACTUAL"),
						(Long) respuesta.get("ANTERIOR"), 0));
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("es-MX"));
		respuesta.put("hora", df.format(new Date()));
		
		return respuesta;
	}
	
	@Override
	public Map<String, Object> obtenerCifrasTipoTramiteOrigen(
			GraficaRequest request) {
		
		Map<String, Object> respuesta = new LinkedHashMap<String, Object>();
		
		List<GraficaResponse> contadores = this.dashboardEntityService
				.getContadoresTipoTramiteOrigen(request);
		
		long total = 0;
		for(GraficaResponse contador : contadores) {
			total += contador.getTotal();
		}
		
		for(GraficaResponse contador : contadores) {
			contador.setPorcentaje(obtenerPorcentaje(contador.getTotal(), total, NUM_DECIMALES));
		}
		
		respuesta.put("data", contadores);
		respuesta.put("total", total);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("es-MX"));
		respuesta.put("hora", df.format(new Date()));
		
		return respuesta;
	
	}
	
	private static float obtenerPorcentaje(long n, long total, int numDecimales) {
		if (total > 0) {
		    float proporcion = ((float) n) / ((float) total);
		    
		    BigDecimal bd = new BigDecimal(Float.toString(proporcion * 100));
	        bd = bd.setScale(numDecimales, BigDecimal.ROUND_HALF_UP);
	        
	        return bd.floatValue();
		} else {
			return 100F;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void agruparCifrasPorOrigen(
			List<GraficaResponse> contadores, String tipoAgrupacion,
			Map<String, Object> respuesta) {
				
		Set<String> labels = new LinkedHashSet<String>();
		Set<String> origenes = new LinkedHashSet<String>();
		
		if (tipoAgrupacion.equals("anios") 
				|| tipoAgrupacion.equals("meses")) {
			for(GraficaResponse contador : contadores) {
				String key = contador.getFecha().trim();
				String origen = contador.getOrigen() == 6 ? "P. CIUDADANO (CURP)"
						: OrigenSolicitudEnum.getById(
								Long.valueOf(contador.getOrigen())).getDesc();
				
				labels.add(key);
				origenes.add(origen);
				
				Map<String, Long> periodo = null;
				
				if (respuesta.get(key) == null) {
					periodo = new LinkedHashMap<String, Long>();
				} else {
					periodo = (Map<String, Long>) respuesta.get(key);
				}
				periodo.put(origen, contador.getTotal());
				
				respuesta.put(key, periodo);
			}
			respuesta.put("labels", labels);
			respuesta.put("origenes", origenes);
		} else if (tipoAgrupacion.equals("dias")) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			for(GraficaResponse contador : contadores) {
				String key = df.format(contador.getFechaConclusion());
				String origen = contador.getOrigen() == 6 ? "P. CIUDADANO (CURP)"
						: OrigenSolicitudEnum.getById(
								Long.valueOf(contador.getOrigen())).getDesc();
				labels.add(key);
				origenes.add(origen);
				
				Map<String, Long> periodo = null;
				
				if (respuesta.get(key) == null) {
					periodo = new LinkedHashMap<String, Long>();
				} else {
					periodo = (Map<String, Long>) respuesta.get(key);
				}
				periodo.put(origen, contador.getTotal());
				
				respuesta.put(key, periodo);
			}
			respuesta.put("labels", labels);
			respuesta.put("origenes", origenes);
		} else if (tipoAgrupacion.equals("dia")) {
			for(GraficaResponse contador : contadores) {
				String origen = contador.getOrigen() == 6 ? "P. CIUDADANO (CURP)"
						: OrigenSolicitudEnum.getById(
								Long.valueOf(contador.getOrigen())).getDesc();
				String key = origen;
				labels.add(key);
				origenes.add(origen);
				
				Map<String, Long> periodo = null;
				
				if (respuesta.get(key) == null) {
					periodo = new LinkedHashMap<String, Long>();
				} else {
					periodo = (Map<String, Long>) respuesta.get(key);
				}
				periodo.put(origen, contador.getTotal());
				
				respuesta.put(key, periodo);
			}
			respuesta.put("labels", labels);
			respuesta.put("origenes", origenes);
		} 
		
		respuesta.put("agrupado", tipoAgrupacion);
	}
}

