/**
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:DocumentosAnalisisServiceEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis
 *  @Fecha:29/10/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

@Local
public interface DocumentosAnalisisServiceEntityLocal{
	
	/**
	 * Obtiene el id de un documento de análisis según su tipo.
	 * @param idTramite, idDocumentoPorTipo
	 * @return Long
	 * @throws PersistenceException
	 */
	Long getIdDocumentoPorTipoIdTramite(Long idTramite, Long idDocumentoPorTipo);

	/**
	 * Obtiene el id de un documento de análisis según su tipo.
	 * @param idTramite, idDocumentoPorTipo
	 * @return byte[]
	 * @throws PersistenceException
	 */
	byte[] getRefDocumentoPorTipoIdTramite(Long idTramite, int idDocumentoPorTipo);
	
	/**
	 * Obtiene el arreglo de Bytes de un documento de análisis según su tipo.
	 * @param cveIdSolicitud, tipoDocumento
	 * @return Long
	 * @throws PersistenceException
	 */
	String obtenIdDocumento(Long cveIdSolicitud, int tipoDocumento) throws PersistenceException;

	/**
	 * Obtiene el arreglo de Bytes de un documento de análisis según su tipo.
	 * @param id, tipoDocumento
	 * @return byte[]
	 * @throws PersistenceException
	 */
	byte[] obtenRefDocumento(Long id, int tipoDocumento) throws PersistenceException;
	
}
