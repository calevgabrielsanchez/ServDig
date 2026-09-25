/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:SubDelegacionServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;

@Local
public interface SubDelegacionServiceEntityLocal {
	
	/**
	 * Consulta subdelegacion por id
	 * @param subdelegacion
	 * @return
	 * @throws Exception
	 */
	Subdelegacion consultaPorId(Subdelegacion subDelegacion) throws Exception;
		
}
