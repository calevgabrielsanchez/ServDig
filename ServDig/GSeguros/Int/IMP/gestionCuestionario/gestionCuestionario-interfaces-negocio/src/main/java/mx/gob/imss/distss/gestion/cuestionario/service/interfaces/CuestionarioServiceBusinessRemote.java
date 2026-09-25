package mx.gob.imss.distss.gestion.cuestionario.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.distss.gestion.cuestionario.excepcion.CuestionarioNoExisteException;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Cuestionario;

@Remote
public interface CuestionarioServiceBusinessRemote {

	/**
	 * Consulta y arma los objetos de modelo para presentar un cuestionario.
	 * 
	 * @param tipoCuestionario
	 * @return
	 * @throws CuestionarioNoExisteException
	 */
	Cuestionario obtenerCuestionario(int idTipoCuestionario)
			throws CuestionarioNoExisteException;

}
