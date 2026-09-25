package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.identificador.PersonaSinIdentificadoresException;
import mx.gob.imss.ctirss.delta.persistence.DitIdentificador;

/**
 * 111012
 * 
 * @author ICCSRG
 * 
 */
@Local
public interface IdentificadoresPersonaFisicaServiceEntityLocal {

	/**
	 * Metodo encargado de realizar el registro de los tramites asociados a una
	 * persona fisica en BDU
	 * 
	 * @param ditIdentificador
	 */
	void registrar(DitIdentificador ditIdentificador);

	/**
	 * Metodo encargado de realizar la actualizacion de los tramites asociados a
	 * una persona fisica en BDU
	 * 
	 * @param ditIdentificador
	 */
	void actualizar(DitIdentificador ditIdentificador);

	/**
	 * Método encargado de expirar un identificador
	 * 
	 * @param ditIdentificador
	 */
	void expirarIdentificador(DitIdentificador ditIdentificador);

	/**
	 * Método que obtiene los identificadores de una persona
	 * @param cvePersona
	 * @return
	 * @throws PersonaSinIdentificadoresException
	 */
	List<DitIdentificador> obtenerIdentificadoresPersona(Long cvePersona)
			throws PersonaSinIdentificadoresException;

}
