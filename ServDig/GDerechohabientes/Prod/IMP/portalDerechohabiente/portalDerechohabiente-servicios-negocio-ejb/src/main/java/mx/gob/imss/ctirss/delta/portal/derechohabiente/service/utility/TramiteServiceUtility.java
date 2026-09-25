/**
 *
 *
 **/
package mx.gob.imss.ctirss.delta.portal.derechohabiente.service.utility;

import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Lucio Duran Silva
 * @Proyecto: IMSS Digital
 * @Archivo: TramiteServiceUtility.java
 * @Paquete: mx.gob.imss.ctirss.delta.tramite.service.utility
 * @Fecha: 09:42:23
 */
@Stateless
public class TramiteServiceUtility extends AbstractServiceUtility implements
		TramiteServiceUtilityLocal {

	@Override
	public List<Tramite> convertirListaEntityToModel(List<DitTramite> tramites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tramite convertirEntidadAModelo(DitTramite ditTramite) {
		// TODO Auto-generated method stub
		return null;
	}

}
