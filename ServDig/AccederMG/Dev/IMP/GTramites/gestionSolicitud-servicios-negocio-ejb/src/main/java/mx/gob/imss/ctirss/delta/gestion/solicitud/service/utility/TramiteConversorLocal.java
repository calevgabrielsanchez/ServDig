package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteInfo;
import mx.gob.imss.ctirss.delta.persistence.DicTramiteInfo;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

public interface TramiteConversorLocal {

	DitTramite convertirModelToEntity(Tramite tramite);

	DitDetalleTramite construirEntityDetalleTramite(Tramite tramite,
			DitTramite ditTramite, Date fechaAlta);

	Tramite convertirEntityToXmlModel(DitTramite ditTramite);

	Tramite convertirEntityToModel(DitTramite ditTramite);

	TramiteSolicitud convertirTramiteSujetoObligado(DitTramite tramite);

	TramiteInfo convertirEntityToModel(DicTramiteInfo entity);
}
