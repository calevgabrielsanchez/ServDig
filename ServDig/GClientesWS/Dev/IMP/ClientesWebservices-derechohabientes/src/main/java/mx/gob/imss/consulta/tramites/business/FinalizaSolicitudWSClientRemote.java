package mx.gob.imss.consulta.tramites.business;

import javax.ejb.Remote;


@Remote
public interface FinalizaSolicitudWSClientRemote {

	/**
	 * Método para invocar el WS de finalizar solicitud
	 * @param mensaje
	 * @return Respuesta del WS
	 * @throws Exception
	 */
	Respuesta finalizarSolicitud(Mensaje mensaje) throws Exception;
}
