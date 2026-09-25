/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:DatosClemServiceEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem
 *  @Fecha:17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.SubdelegacionRimss;

@Local
public interface DatosClemServiceEntityLocal {

	/**
	 * Metodo para agregar un elemento al catalogo.
	 * @param model
	 * @return
	 * @throws DatosClemException 
	 */
	DatosClem crear(DatosClem model, Boolean insert) throws PersistenceException;
	
	/**
	 * Metodo para modificar un elemento del catalogo
	 * @param model
	 * @return
	 */
	DatosClem actualiza(DatosClem model) throws PersistenceException;
	
	
	/**
	 * Metodo para eliminar un elemento del catalogo
	 * @param model
	 */
	void elimina(Long cveIdClem) throws PersistenceException;
	
	/**
	 * Metodo para realizar consulta por clave
	 * @param model
	 * @return
	 */
	DatosClem consultaPorClave(DatosClem model) throws PersistenceException;
	
	/**
	 * Metodo para asignarle el valor de 
	 * @param model
	 * @return
	 * @throws PersistenceException
	 */
	DatosClem actualizaEstadosClem(DatosClem model) throws PersistenceException;
	
	DatosClem modificaClem(DatosClem model) throws PersistenceException;
	
	/**
	 * Método para guardar en el Histórico de CLEM
	 * @param datosClem, cveIdTipoCausa
	 * @return
	 */
	void generaHistDatosClem(DatosClem datosClem, Long cveIdTipoCausa);

	/**
	 * Metodo para actualizar la clem con datos de la firma electronica
	 * @param model
	 */
	void actualizaDatosFirmaClem(FirmaClemDTO firmaClemDTO) throws PersistenceException;
	
	/**
	 * Metodo para obtener valor de la subdelegacion RIMSS
	 * @param model
	 * @return
	 */
	SubdelegacionRimss consultaSubdelegacionRIMSS(SubdelegacionRimss model);	
}