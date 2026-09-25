package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaRequest;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaResponse;

@Local
public interface DashboardEntityServiceLocal {

	List<GraficaResponse> getGranTotal();

	List<GraficaResponse> getRankingTramites(GraficaRequest request,
			int numResultados);

	List<GraficaResponse> getContadores(GraficaRequest request);

	List<GraficaResponse> getContadoresPorOrigen(GraficaRequest request);

	List<GraficaResponse> getContadoresPorEstado(GraficaRequest request);

	List<GraficaResponse> getContadoresAtendidasPeriodos(GraficaRequest request);

	List<GraficaResponse> getContadoresTipoTramiteOrigen(GraficaRequest request);

}
