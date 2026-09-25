package mx.gob.imss.ctirss.delta.solicitudes.sipare.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.solicitudes.sipare.service.model.GraficaSolicitudesWrapper;

@Remote
public interface SipareBusinessServiceRemote {

	List<GraficaSolicitudesWrapper> obtenerGraficas(String identificador,
			FiltroSolicitud filtros);

	GraficaSolicitudesWrapper obtenerGrafica(String identificador,
			FiltroSolicitud filtros);

}
