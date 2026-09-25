package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudNssCorreo;

@Local
public interface SolicitudNssCorreoServiceUtilityLocal {

	SolicitudNssCorreo transformarFromEntity(DitSolicitudNssCorreo entity)
			throws TransformacionException;

	DitSolicitudNssCorreo transformarFromModel(SolicitudNssCorreo model)
			throws TransformacionException;

}
