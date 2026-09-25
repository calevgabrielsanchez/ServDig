/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: TramiteServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.tramite
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.tramite;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

@Stateless
public class TramiteServiceUtility extends AbstractServiceUtility implements
		TramiteServiceUtilityLocal {

	@Override
	public Tramite convertirEntityToModel(DitTramite ditTramite)throws Exception{
		Tramite tramite = new Tramite();
		
		tramite.setTramiteId(ditTramite.getCveIdTramite());
		tramite.setObservacion(ditTramite.getRefObservacion());
		
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(ditTramite.getDicTipoTramite().getCveIdTipoTramite().intValue());
		tipoTramite.setDescripcion(ditTramite.getDicTipoTramite().getDesTipoTramite());
		tramite.setTipoTramite(tipoTramite);
		
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(ditTramite.getDicEstadoTramite().getCveIdEstadoTramite().intValue());
		estadoTramite.setDescripcion(ditTramite.getDicEstadoTramite().getDesEstadoTramite());
		tramite.setEstadoTramite(estadoTramite);
		
		//tramite.setFechaTramite(ditTramite.getFecTramite());
		tramite.setFechaTramite(ditTramite.getFecRegistroAlta());
		tramite.setFechaPresentacion(ditTramite.getFecPresentacion());
		tramite.setFechaEfecto(ditTramite.getFecEfecto());
		
		return tramite;
	}
}
