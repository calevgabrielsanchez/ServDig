package mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility;

import java.io.IOException;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.EstadoAsignacion;

@Local
public interface AsignacionMasivaHandlerLocal {
	void publicarFinProcesamiento(String idRegistroPatronal, Long cveIdUsuario,
			String usuario) throws IOException;

	void publicarResultadoParcialProcesamiento(Integer numExitos,
			Integer numErrores, String idRegistroPatronal, Long cveIdUsuario,
			String usuario) throws IOException;

	void publicarRegistroIndividualProcesamiento(EstadoAsignacion estado,
			String idRegistroPatronal, Long cveIdUsuario, String usuario) throws IOException;
}
