package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Notificacion;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.persistence.DitNotificacion;

@Local
public interface NotificacionServiceUtilityLocal {

	Notificacion transformarNotificacion(DitNotificacion entity)
			throws TransformacionException;

	DitNotificacion transformarNotificacion(Notificacion model)
			throws TransformacionException;

	void generarDetalleCambiosICA(TramiteCambioInformacionPersona tramite,
			StringBuffer htmlDetalle);

	void generarDetalleCambiosModificacionManual(
			TramiteCambioInformacionPersona tramite, StringBuffer htmlDetalle);
}
