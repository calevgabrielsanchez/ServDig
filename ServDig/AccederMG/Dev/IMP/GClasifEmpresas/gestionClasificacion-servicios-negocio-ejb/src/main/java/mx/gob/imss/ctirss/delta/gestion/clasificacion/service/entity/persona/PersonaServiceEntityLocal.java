/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:PersonaServiceEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.persona
 *  @Fecha:17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.persona;

import javax.ejb.Local;

import java.util.List;

import mx.gob.imss.ctirss.delta.model.clasificacion.TitularSuplente;

@Local
public interface PersonaServiceEntityLocal {
	List<TitularSuplente> consultaPersonaFuncionario()throws Exception;
	
	TitularSuplente consultaPersonaPorId(Long id)throws Exception;
}
