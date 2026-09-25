package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;

@Stateless( name = "tipoTramiteConversor", mappedName = "tipoTramiteConversor")
public class TipoTramiteConversor extends AbstractServiceUtility implements
		TipoTramiteConversorLocal {

	@Override
	public DicTipoTramite modelToEntity(TipoTramite tipoTramite) {
		DicTipoTramite salida = null;
		
		if(tipoTramite != null) {
			salida = new DicTipoTramite();
			salida.setCveIdTipoTramite(tipoTramite.getIdTipoTramite().longValue());
			if(tipoTramite.getDescripcion() != null) {
				salida.setDesTipoTramite(tipoTramite.getDescripcion());
			}
		}
		
		return salida;
	}

	@Override
	public TipoTramite entityToModel(DicTipoTramite dicTipoTramite) {
		
		TipoTramite salida = null;
		
		if(dicTipoTramite != null) {
			salida = new TipoTramite();
			salida.setIdTipoTramite(dicTipoTramite.getCveIdTipoTramite().intValue());
			salida.setDescripcion(dicTipoTramite.getDesTipoTramite());
		}
		return salida;
	}

	@Override
	public List<TipoTramite> entityToModelList(
			List<DicTipoTramite> dicTiposTramite) {
		
		List<TipoTramite> salida = null;
		
		if(dicTiposTramite != null && !dicTiposTramite.isEmpty()) {
			salida = new ArrayList<TipoTramite>();
			
			for(DicTipoTramite dicTT: dicTiposTramite) {
				salida.add(this.entityToModel(dicTT));
			}
		}
		
		return salida;
	}

}
