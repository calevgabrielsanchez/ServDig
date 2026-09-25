package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.identificador.PersonaSinIdentificadoresException;
import mx.gob.imss.ctirss.delta.persistence.DitIdentificadorMoral;

/**
 * Clase que contiene los métodos necesarios para la administración de los
 * identificadores de una persona moral
 * 
 * @author Marco Sánchez
 * @date 19/03/2013
 * 
 */
@Local
public interface IdentificadoresPersonaMoralServiceEntityLocal {

	/**
	 * Metodo encargado de realizar el registro de identificadores asociados a
	 * una persona moral
	 * 
	 * @param DitIdentificadorMoral
	 */
	void registrar(DitIdentificadorMoral ditIdentificadorMoral);

	/**
	 * Metodo encargado de realizar la actualizacion de los identificadores
	 * asociados a una persona moral
	 * 
	 * @param DitIdentificadorMoral
	 */
	void actualizar(DitIdentificadorMoral ditIdentificadorMoral);

	/**
	 * Método encargado de expirar un identificador
	 * 
	 * @param DitIdentificadorMoral
	 */
	void expirarIdentificador(DitIdentificadorMoral ditIdentificadorMoral);

	/**
	 * Método que obtiene los identificadores de una persona moral
	 * 
	 * @param cvePersonaMoral
	 * @return
	 * @throws PersonaSinIdentificadoresException
	 */
	List<DitIdentificadorMoral> obtenerIdentificadoresPersona(Long cvePersonaMoral)
			throws PersonaSinIdentificadoresException;

}
