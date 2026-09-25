/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:SolicitudDocumentoEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.solicitud
 *  @Fecha:25/10/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.solicitud;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.DocumentosAnalisis;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;

@Local
public interface SolicitudDocumentoEntityLocal{
	DocumentosAnalisis obtenerDocumento(Long idSolicitud);
	
	void pruebaaInsertarDocumentoProbatorio();
	
	DocumentoProbatorio pruebaaObtenerDocumentoProbatorio();
}
