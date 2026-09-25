package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaRequest;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaResponse;

@Remote
public interface GraficasSolicitudServiceBusinessRemote {

	List<GraficaResponse> getCifrasGrafica(GraficaRequest request);

}