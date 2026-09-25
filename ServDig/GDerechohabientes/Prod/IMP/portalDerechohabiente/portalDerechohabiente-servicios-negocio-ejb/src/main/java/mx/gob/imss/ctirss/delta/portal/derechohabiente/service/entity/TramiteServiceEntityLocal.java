/**
 *
 *
 **/
package mx.gob.imss.ctirss.delta.portal.derechohabiente.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Lucio Duran Silva
 * @Proyecto: delta
 * @Archivo: TramiteServiceEntityLocal.java
 * @Paquete: mx.gob.imss.ctirss.delta.tramite.service.entity
 * @Fecha: 17:58:12
 */
@Local
public interface TramiteServiceEntityLocal {

	/**
	 * Obtiene los <DitTramite> tramites asociados a una persona fisica por
	 * identificador.
	 * 
	 * @param persona
	 * @return
	 */
	public List<DitTramite> getTramitesPorPersona(Fisica persona);

}
