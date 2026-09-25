package mx.gob.imss.ctirss.delta.comet.service;

import java.io.IOException;
import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.comet.model.NotificacionFinSesionCometData;

@Remote
public interface SessionHandlerLocal {

	/**
	 * Servicio que publica en el canal del comet que notifica a portal que una
	 * sesion se ha cerrado. Una sesion puede cerrarse por inactvidad o por
	 * Logout El canal es /comet/delta/channels/server/session/{CURP}
	 * 
	 * @param data
	 * @throws IOException
	 */
	void publicarComet(NotificacionFinSesionCometData data) throws IOException;
}
