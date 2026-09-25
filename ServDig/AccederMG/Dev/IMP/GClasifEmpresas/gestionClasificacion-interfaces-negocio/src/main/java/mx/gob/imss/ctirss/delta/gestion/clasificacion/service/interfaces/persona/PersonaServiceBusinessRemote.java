/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:PersonaServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.persona
 *  @Fecha:02/07/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.persona;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.clasificacion.TitularSuplente;

@Remote
public interface PersonaServiceBusinessRemote {
	/**
	 * Obtiene lista de Personas disponibles como titular o suplente al generar un CLEM 
	 * @return
	 * @throws Exception
	 */
	List<TitularSuplente> consultaPersona()throws Exception;
	
	/**
	 * Obtiene una Persona
	 * @param id
	 * @return
	 * @throws Exception
	 */
	TitularSuplente consultaPersonaPorId(Long id)throws Exception;
}