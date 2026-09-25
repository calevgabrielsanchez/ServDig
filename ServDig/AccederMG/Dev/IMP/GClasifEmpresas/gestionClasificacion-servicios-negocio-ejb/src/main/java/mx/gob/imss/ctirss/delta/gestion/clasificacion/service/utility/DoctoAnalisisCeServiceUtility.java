/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: DoctoAnalisisCeServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility
 *  @Fecha: 01/11/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.clasificacion.DoctoAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DicTipoDoctoAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoAnalisisCe;

@Stateless
public class DoctoAnalisisCeServiceUtility extends AbstractServiceUtility implements
		DoctoAnalisisCeServiceUtilityLocal {

	/**
	 * {@inheritDoc}
	 * @see DoctoAnalisisCeServiceUtilityLocal#convertirModelToEntity(DoctoAnalisisCe)
	 */
	@Override
	public DitDoctoAnalisisCe convertirModelToEntity(
			final DoctoAnalisisCe doctoAnalisisCe) {
		final DitAnalisisCe ditAnalisisCe = new DitAnalisisCe();
		ditAnalisisCe.setCveIdAnalisis(doctoAnalisisCe.getCveIdAnalisis().longValue());
		
		final DicTipoDoctoAnalisisCe dicTipoDoctoAnalisisCe = new DicTipoDoctoAnalisisCe();
		dicTipoDoctoAnalisisCe.setCveTipoDoctoAnalisisCe(doctoAnalisisCe.getCveTipoDoctoAnalisisCe().longValue());
		
		final DitDoctoAnalisisCe ditDoctoAnalisisCe = new DitDoctoAnalisisCe();
		if (null != doctoAnalisisCe.getCveIdDoctoAnalisisCe()) {
			ditDoctoAnalisisCe.setCveIdDoctoAnalisisCe(doctoAnalisisCe.getCveIdDoctoAnalisisCe().longValue());
		}
		ditDoctoAnalisisCe.setDitAnalisisCe(ditAnalisisCe);
		ditDoctoAnalisisCe.setDicTipoDoctoAnalisisCe(dicTipoDoctoAnalisisCe);
		ditDoctoAnalisisCe.setRefDocumento(doctoAnalisisCe.getRefDocumento());
		
		return ditDoctoAnalisisCe;
	}

}
