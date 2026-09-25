/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:SolicitudDocumentoBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud
 *  @Fecha:25/10/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.clasificacion.DocumentosAnalisis;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;

@Remote
public interface SolicitudDocumentoBusinessRemote{
	DocumentosAnalisis obtenerDocumento(Long idSolicitud);
	
	/**
	 * Genera un Documento para insertar directamente a BD (Servicio utilizado para Pruebas Unitarias)
	 */
	void pruebaInsertarDocumentoProbatorio();
	
	/**
	 * Obtiene el Documento Generado por pruebaInsertarDocumentoProbatorio() (Servicio utilizado para Pruebas Unitarias)
	 * @return
	 */
	DocumentoProbatorio pruebaaObtenerDocumentoProbatorio();
	
	/**
	 * Prueba la correcta funcionalidad de la Regla RPC (Servicio utilizado para Pruebas Unitarias)
	 * @param rfc
	 * @param clase
	 * @param registroPatronal
	 * @return
	 */
	//String pruebaReglaRPC(String rfc, Long clase, String registroPatronal);
}