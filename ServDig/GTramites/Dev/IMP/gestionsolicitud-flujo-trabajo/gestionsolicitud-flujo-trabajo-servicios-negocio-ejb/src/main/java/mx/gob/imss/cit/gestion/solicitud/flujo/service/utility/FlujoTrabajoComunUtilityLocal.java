package mx.gob.imss.cit.gestion.solicitud.flujo.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Asignacion;

@Local
interface FlujoTrabajoComunUtilityLocal {

	void llenarAsignacion(List<Asignacion> asignaciones, final Long bpId, final Long idParticipante, String usuario);

	Asignacion llenarAsignacion(final Long bpId, final Long idParticipante, String usuario);

	Asignacion llenarAsignacion(final Long bpId, final Long idParticipante);

	Asignacion llenarAsignacion(final Long bpId, final String rol);

}
