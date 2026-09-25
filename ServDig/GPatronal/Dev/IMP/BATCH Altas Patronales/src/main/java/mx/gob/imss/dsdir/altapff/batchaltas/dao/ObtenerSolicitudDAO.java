package mx.gob.imss.dsdir.altapff.batchaltas.dao;

import java.util.List;


public interface ObtenerSolicitudDAO {

	List<Long> findEnProcesoSolicitudes();
	List<Long> findEnProcesoSolicitudesConDocs();
	
	boolean updateEstadoSolicitud(List<Long> listaSolicitdes, int estadoSolicitud);
	boolean updateEstadoTramite(List<Long> listaSolicitudes, int estadoTramite);
}
