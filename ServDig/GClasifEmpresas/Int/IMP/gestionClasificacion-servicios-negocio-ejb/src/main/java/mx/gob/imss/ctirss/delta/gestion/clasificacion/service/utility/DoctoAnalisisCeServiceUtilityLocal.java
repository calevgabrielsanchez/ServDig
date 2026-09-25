/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: DoctoAnalisisCeServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility
 *  @Fecha: 01/11/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.DoctoAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoAnalisisCe;

@Local
public interface DoctoAnalisisCeServiceUtilityLocal {

	/**
	 * Genera un objeto DitDoctoAnalisisCe a partir de un objeto DoctoAnalisisCe
	 * como parámetro
	 * 
	 * @param doctoAnalisisCe
	 * @return ditDoctoAnalisisCe
	 */
	DitDoctoAnalisisCe convertirModelToEntity(
			final DoctoAnalisisCe doctoAnalisisCe);

}
