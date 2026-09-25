package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DicOrigenSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DitCitaSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;

@Local
public interface SolicitudConversorLocal {

    Solicitud convertirEntityToModel(DitSolicitud ditSolicitud, boolean obtenerDatosXML);
    Solicitud convertirEntityToModelDatosBase(DitSolicitud ditSolicitud);
    Solicitud convertirEntityToModelBasico(DitSolicitud ditSolicitud);
    SujetoObligado convertirEntityToModelSujetoObligadoSolicitud(DitSolicitud entity);
	DicOrigenSolicitud convertirOrigenSolicitud(OrigenSolicitud origenSolicitud);
	public RazonResultado convertirRazonResultado(DicRazonResultado dicRazonResultado);
	
	CitaSolicitud parserCitaEntityToModel(DitCitaSolicitud citaBd);
}