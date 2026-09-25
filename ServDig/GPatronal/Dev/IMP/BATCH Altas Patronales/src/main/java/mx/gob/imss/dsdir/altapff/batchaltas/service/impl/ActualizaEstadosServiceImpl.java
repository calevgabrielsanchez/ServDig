package mx.gob.imss.dsdir.altapff.batchaltas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.dsdir.altapff.batchaltas.dao.ObtenerSolicitudDAO;
import mx.gob.imss.dsdir.altapff.batchaltas.service.ActualizaEstadosService;




@Component
public class ActualizaEstadosServiceImpl implements ActualizaEstadosService {

	@Autowired
//	@Qualifier("ObtenerSolicitudDAOImpl")
	private ObtenerSolicitudDAO obtenerSolicitudDAO;
	
//	@Autowired
//	private SolicitudesRepository solicitudesRepository;
	
	@Override
	public boolean actualizaEstadoSolicitudes(List<Long> idSolicitudList, int estadoSolicitud) {
		return obtenerSolicitudDAO.updateEstadoSolicitud(idSolicitudList, estadoSolicitud);
	}

	@Override
	public boolean actualizaEstadoTramites(List<Long> idSolicitudList, int estadoTramite) {
		return obtenerSolicitudDAO.updateEstadoTramite(idSolicitudList, estadoTramite);
	}

	@Override
	public List<Long> recuperarSolicitudesACancelar() {

		return obtenerSolicitudDAO.findEnProcesoSolicitudes();		
	}

	@Override
	public List<Long> recuperarSolicitudesATerminar() {
		return obtenerSolicitudDAO.findEnProcesoSolicitudesConDocs();
	}


}
