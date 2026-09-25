/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:DelegacionServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;

@Local
public interface DelegacionServiceEntityLocal {

	/**
	 * Consulta delegacion por id
	 * @param delegacion
	 * @return
	 * @throws Exception
	 */
	Delegacion consultaPorId(Delegacion delegacion) throws Exception;
	
	List<Delegacion> consultaDelegaciones(Long cveIdDelegacion);
}
