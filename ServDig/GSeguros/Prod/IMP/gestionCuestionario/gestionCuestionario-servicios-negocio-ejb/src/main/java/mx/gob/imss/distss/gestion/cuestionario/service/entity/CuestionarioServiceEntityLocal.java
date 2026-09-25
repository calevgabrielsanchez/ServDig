package mx.gob.imss.distss.gestion.cuestionario.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DicCuestionario;
import mx.gob.imss.distss.gestion.cuestionario.excepcion.CuestionarioNoExisteException;

@Local
public interface CuestionarioServiceEntityLocal {

	DicCuestionario obtenerCuestionario(int tipoCuestionario)
			throws CuestionarioNoExisteException;

	List<Integer> obtenerIdPreguntasDependientesCuestionario(
			int idTipoCuestionario);
}