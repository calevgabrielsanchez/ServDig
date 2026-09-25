package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.integracion.common.ReporteRissWrapper;

@Local
public interface ReporteRissServiceEntityLocal {

	List<ReporteRissWrapper> consultarSolicitudesRiss(Date fechaInicio,
			Date fechaFin);

	List<ReporteRissWrapper> consultarMediosContactoNssRiss();

	List<ReporteRissWrapper> consultarMediosDomicilioNrpRiss();

}
