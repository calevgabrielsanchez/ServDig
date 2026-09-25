package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.estado.EstadosNoExistentesException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * 181012
 * @author ICCSRG
 *
 */
@Remote
public interface EstadosPersonaFisicaServiceBusinessRemote {
	
	/**
	 * Metodo encargado de realizar el registro de los estados asociados a una persona fisica
	 * @param fisica
	 * @throws EstadosNoExistentesException 
	 */
	void registrar(Fisica fisica) throws EstadosNoExistentesException;
	
	/**
	 * Metodo encargado de realizar la actualizacion de los estados asociados a una persona fisica
	 * @param fisica
	 * @throws EstadosNoExistentesException
	 */
	void actualizar(Fisica fisica) throws EstadosNoExistentesException;

}
