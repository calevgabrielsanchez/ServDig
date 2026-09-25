package mx.gob.imss.dsdir.altapff.batchaltas.service;

import java.util.List;




public interface ActualizaEstadosService {
	/**
		Cambia estado de las solicitudes
	 */
	boolean actualizaEstadoSolicitudes(List<Long> idSolicitudList,
			int estadoSolicitud);

	/**
		Cambia estado de los trámites
	 */
	boolean actualizaEstadoTramites(List<Long> idSolicitudList,
			int estadoTramite);

	/**
	 	Obtiene solicitudes a cancelar
	 */
	List<Long> recuperarSolicitudesACancelar();

	/**
		Obtiene solicitudes a terminar
	 */
	List<Long> recuperarSolicitudesATerminar();
}
