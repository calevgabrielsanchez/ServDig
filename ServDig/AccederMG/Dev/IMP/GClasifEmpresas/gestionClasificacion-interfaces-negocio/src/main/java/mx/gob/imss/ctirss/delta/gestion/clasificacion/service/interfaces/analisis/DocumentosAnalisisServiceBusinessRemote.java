/**
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:DocumentosAnalisisServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis
 *  @Fecha:29/10/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.clasificacion.DocumentosAnalisis;

@Remote
public interface DocumentosAnalisisServiceBusinessRemote {

	/**
	 * Arma el objeto DocumentoAnalisis según la existencia o no de los diferentes
	 * tipos de documentos de análisis.
	 * @param cveIdSolicitud
	 * @return DocumentoAnalisis
	 * @throws Exception
	 */
	DocumentosAnalisis validaExistenciaDocumentos(Long cveIdSolicitud) throws Exception;
	
	/**
	 * Obtiene el arreglo de Bytes de un documento de análisis según su tipo.
	 * @param id, tipoDocumento
	 * @return byte[]
	 * @throws Exception
	 */
	byte[] obtenRefDocumento(Long id, int tipoDocumento) throws Exception;
}
