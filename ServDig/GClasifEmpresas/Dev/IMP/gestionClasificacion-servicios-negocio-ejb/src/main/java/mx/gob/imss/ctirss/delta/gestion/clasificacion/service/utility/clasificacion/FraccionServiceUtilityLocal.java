/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: FraccionServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.persistence.DicClase;
import mx.gob.imss.ctirss.delta.persistence.DicDivision;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicFraccionClase;
import mx.gob.imss.ctirss.delta.persistence.DicGrupo;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;

@Local
public interface FraccionServiceUtilityLocal {
	
	Fraccion convertirEntityToModel(DicFraccion entity) throws Exception;
	
	/**
	 * Genera un objeto Fraccion a partir del objeto enviado como parámetro
	 * 
	 * @param dicFraccion
	 * @return fraccion
	 */
    Fraccion convertirEntityToModelFraccion(final DicFraccion dicFraccion);
 
	/**
	 * Genera un objeto Clasificacion a partir del objeto enviado como parámetro
	 * 
	 * @param ditClasificacion
	 * @return clasificacion
	 */
	Clasificacion convertirEntityToModel(final DitClasificacion ditClasificacion);
	
	Fraccion convertirEntityToModelFraccion(final DicFraccionClase dicFraccionClase);
	
	Clase convertirEntityToModelClase(final DicClase dicClase);
	
	Division convertirEntityToModelDivision(final DicDivision dicDivision);
	
	Grupo convertirEntityToModelGrupo(final DicGrupo dicGrupo);
	
	Fraccion convertirEntityToModelFraccionClaseActiva(final DicFraccion dicFraccion);
		
	
}
 