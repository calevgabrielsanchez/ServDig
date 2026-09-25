package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;

@Local
public interface TipoTramiteConversorLocal {

	DicTipoTramite modelToEntity(TipoTramite tipoTramite);
	TipoTramite entityToModel(DicTipoTramite dicTipoTramite);
	List<TipoTramite> entityToModelList(List<DicTipoTramite> dicTiposTramite);
	
}
