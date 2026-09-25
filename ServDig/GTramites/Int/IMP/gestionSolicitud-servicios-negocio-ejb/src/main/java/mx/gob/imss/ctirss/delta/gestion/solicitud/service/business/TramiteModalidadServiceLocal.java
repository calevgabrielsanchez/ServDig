package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

@Local
public interface TramiteModalidadServiceLocal {

	List<TipoTramite> getTiposTramiteByModalidad(Modalidad modalidad) throws IllegalArgumentException;
	List<TipoTramite> getTiposTramiteByModalidad(Long idModalidad) throws IllegalArgumentException;
	List<TipoTramite> getTiposTramiteByModalidades(List<Modalidad> modalidades) throws IllegalArgumentException;
	
}
