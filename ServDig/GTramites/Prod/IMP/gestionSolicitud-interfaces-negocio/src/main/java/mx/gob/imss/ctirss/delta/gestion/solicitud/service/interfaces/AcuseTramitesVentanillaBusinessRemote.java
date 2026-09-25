package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Remote
public interface AcuseTramitesVentanillaBusinessRemote {

	byte[] generarAcuseTramiteVentanilla(Solicitud solicitud, Tramite tramite, Long idTipoTramite);
	
}
