package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

@Remote
public interface SolicitudSIEQueueProducerRemote {
	void encolarSolicitudAConcluir(String folioSolicitud);
}
