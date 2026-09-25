package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.service.dao;

import java.util.List;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.EstadisticasAsegurados;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.EstadisticasAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.FiltroEstadisticaAsignacion;

public interface SolicitudDao {
	List<EstadisticasAsignacion> findEstadisticaAsignacion(FiltroEstadisticaAsignacion filtro);

	List<EstadisticasAsegurados> findEstadisticasAsegurados(FiltroEstadisticaAsignacion filtro);
}
