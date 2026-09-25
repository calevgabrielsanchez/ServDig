package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaRequest;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaResponse;

@Local
public interface GraficasSolicitudEntityLocal {

	List<GraficaResponse> getCifrasGraficaBarras(GraficaRequest request);

	List<GraficaResponse> getCifrasGraficaLineas(GraficaRequest request);
}