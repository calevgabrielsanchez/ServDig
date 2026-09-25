package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaRequest;

@Remote
public interface DashboardBusinessServiceRemote {

	Map<String, Object> obtenerGranTotal();
	
	Map<String, Object> obtenerTotalPorcentajesTramite(GraficaRequest request);

	Map<String, Object> obtenerCifrasPorOrigen(GraficaRequest request);

	Map<String, Object> obtenerRankingTramites(GraficaRequest request);
	
	Map<String, Object> obtenerCifrasPorEstado(GraficaRequest request);

	Map<String, Object> obtenerCifrasAtendidasPeriodo(GraficaRequest request);

	Map<String, Object> obtenerCifrasTipoTramiteOrigen(GraficaRequest request);
}
