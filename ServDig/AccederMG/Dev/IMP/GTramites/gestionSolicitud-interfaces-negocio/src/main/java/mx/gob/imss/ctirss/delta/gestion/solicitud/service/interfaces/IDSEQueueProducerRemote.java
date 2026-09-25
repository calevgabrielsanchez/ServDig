package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

@Remote
public interface IDSEQueueProducerRemote {
	void encolarMensajeIDSE(String folioSolicitud);
}
