package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.GraficasSolicitudEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.GraficasSolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaRequest;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaResponse;

@Stateless(mappedName = "graficasSolicitudServiceBusiness")
public class GraficasSolicitudServiceBusiness extends AbstractServiceBusiness
		implements GraficasSolicitudServiceBusinessRemote {

	@EJB
	private GraficasSolicitudEntityLocal graficasSolicitudEntity;

	@Override
	public List<GraficaResponse> getCifrasGrafica(GraficaRequest request) {

		List<GraficaResponse> response = null;

		if (request.getTipoGrafica() == 1) {
			response = this.graficasSolicitudEntity.getCifrasGraficaBarras(request);
		} else if (request.getTipoGrafica() == 2) {
			response = this.graficasSolicitudEntity.getCifrasGraficaLineas(request);
		}

		return response;
	}
}
