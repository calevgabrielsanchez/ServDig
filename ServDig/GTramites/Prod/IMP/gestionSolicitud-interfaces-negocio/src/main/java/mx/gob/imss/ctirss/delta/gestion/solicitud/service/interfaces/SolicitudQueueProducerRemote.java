package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

@Remote
public interface SolicitudQueueProducerRemote {
	void encolarSolicitudAConcluir(String folioSolicitud);
}
