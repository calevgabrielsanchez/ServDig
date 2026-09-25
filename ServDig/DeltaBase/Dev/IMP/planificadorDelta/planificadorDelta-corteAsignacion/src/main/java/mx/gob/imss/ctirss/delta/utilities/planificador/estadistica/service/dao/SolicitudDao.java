package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.service.dao;

import java.util.List;

import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.EstadisticasAsegurados;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.EstadisticasAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio.FiltroEstadisticaAsignacion;

public interface SolicitudDao {
	List<EstadisticasAsignacion> findEstadisticaAsignacion(FiltroEstadisticaAsignacion filtro);

	List<EstadisticasAsegurados> findEstadisticasAsegurados(FiltroEstadisticaAsignacion filtro);
}
