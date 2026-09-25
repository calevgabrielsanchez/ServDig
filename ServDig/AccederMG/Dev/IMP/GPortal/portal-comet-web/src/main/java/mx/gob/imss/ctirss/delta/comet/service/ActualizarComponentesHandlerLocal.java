package mx.gob.imss.ctirss.delta.comet.service;

import java.io.IOException;

import mx.gob.imss.ctirss.delta.comet.model.RefrescarCometData;

public interface ActualizarComponentesHandlerLocal {

	/**
	 * Servicio que de acuerdo al tipo de trámite recibido publica sólo en los
	 * portlets y/o widgets necesarios.
	 * 
	 * @param data
	 * @throws IOException 
	 */
	void publicarComet(RefrescarCometData data) throws IOException;

}
