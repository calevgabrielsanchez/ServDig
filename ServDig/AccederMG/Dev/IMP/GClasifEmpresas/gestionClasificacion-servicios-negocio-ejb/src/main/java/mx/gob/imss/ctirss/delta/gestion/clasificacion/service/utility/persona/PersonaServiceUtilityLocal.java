/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo: PersonaServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.persona
 *  @Fecha: 17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.persona;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.TitularSuplente;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitUsuarioFuncionario;

@Local
public interface PersonaServiceUtilityLocal{
	TitularSuplente convertirEntityToModel(DitUsuarioFuncionario ditFuncionario)throws Exception;
	
	TitularSuplente convertirEntityToModel(DitPersona ditPersona)throws Exception;
	
}
