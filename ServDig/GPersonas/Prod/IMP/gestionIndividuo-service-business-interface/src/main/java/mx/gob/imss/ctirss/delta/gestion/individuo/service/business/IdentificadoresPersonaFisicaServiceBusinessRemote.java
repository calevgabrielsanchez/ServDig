package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.identificador.IdentificadoresNoExistentesException;
import mx.gob.imss.ctirss.delta.exception.individuo.identificador.PersonaSinIdentificadoresException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;

/**
 * Servicios relacionados a la administración de los identificadores de una
 * persona moral
 * 
 * @author Marco Sánchez
 * @date 19/03/2013
 */
@Remote
public interface IdentificadoresPersonaFisicaServiceBusinessRemote {

	/**
	 * Metodo encargado de registrar el identificador de una persona fisica en
	 * BDU
	 * 
	 * @param fisica
	 */
	void registrar(Fisica fisica)
			throws IdentificadoresNoExistentesException;

	/**
	 * Metodo encargado de registrar el identificador de una persona fisica en
	 * BDU
	 * 
	 * @param fisica
	 */
	void actualizar(Fisica fisica)
			throws IdentificadoresNoExistentesException;

	/**
	 * Servicio enardado de expirar un identificador
	 * 
	 * @param identificador
	 */
	void expirarIdentificador(Identificador identificador);

	/**
	 * Servicio para obtener los identificadores de una persona
	 * 
	 * @param cvePersona
	 * @return
	 * @throws PersonaSinIdentificadoresException
	 */
	List<Identificador> obtenerIdentificadoresPersona(Long cvePersona)
			throws PersonaSinIdentificadoresException;

}
