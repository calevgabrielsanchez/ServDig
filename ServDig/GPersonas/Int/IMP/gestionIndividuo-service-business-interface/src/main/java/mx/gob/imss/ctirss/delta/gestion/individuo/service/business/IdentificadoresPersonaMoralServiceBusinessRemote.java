package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.identificador.IdentificadoresNoExistentesException;
import mx.gob.imss.ctirss.delta.exception.individuo.identificador.PersonaSinIdentificadoresException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

/**
 * Servicios relacionados a la administración de los identificadores de una
 * persona moral
 * 
 * @author Marco Sánchez
 * @date 19/03/2013
 * 
 */
@Remote
public interface IdentificadoresPersonaMoralServiceBusinessRemote {

	/**
	 * Metodo encargado de registrar el identificador de una persona moral en
	 * BDU
	 * 
	 * @param moral
	 */
	void registrar(Moral moral)
			throws IdentificadoresNoExistentesException;

	/**
	 * Metodo encargado de actualizar el identificador de una persona moral en
	 * BDU
	 * 
	 * @param moral
	 */
	void actualizar(Moral moral)
			throws IdentificadoresNoExistentesException;

	/**
	 * Servicio encargadado de expirar un identificador
	 * 
	 * @param identificador
	 */
	void expirarIdentificador(Identificador identificador);

	/**
	 * Servicio para obtener los identificadores de una persona moral
	 * 
	 * @param cvePersonaMoral

	 * @throws PersonaSinIdentificadoresException
	 */
	List<Identificador> obtenerIdentificadoresPersona(Long cvePersonaMoral)
			throws PersonaSinIdentificadoresException;

}
