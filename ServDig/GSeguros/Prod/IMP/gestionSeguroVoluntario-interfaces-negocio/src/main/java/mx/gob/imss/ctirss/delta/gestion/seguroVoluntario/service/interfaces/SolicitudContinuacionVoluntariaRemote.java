package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Remote;

@Remote
public interface SolicitudContinuacionVoluntariaRemote {
	boolean existeRechazo(Long idPersona);
}
