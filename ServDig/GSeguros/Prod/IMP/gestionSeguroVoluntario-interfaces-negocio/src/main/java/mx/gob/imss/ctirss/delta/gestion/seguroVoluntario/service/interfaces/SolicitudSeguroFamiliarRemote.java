package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Remote;

@Remote
public interface SolicitudSeguroFamiliarRemote {
	boolean existeRechazo(Long idPersona);
}
