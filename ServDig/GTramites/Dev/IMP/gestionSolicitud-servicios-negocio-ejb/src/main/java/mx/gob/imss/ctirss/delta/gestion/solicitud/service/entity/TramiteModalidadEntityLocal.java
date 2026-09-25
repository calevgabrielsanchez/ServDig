package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

@Local
public interface TramiteModalidadEntityLocal {

	List<TipoTramite> getTipoTramiteByModalidades(List<Modalidad> modalidades);
	
}
