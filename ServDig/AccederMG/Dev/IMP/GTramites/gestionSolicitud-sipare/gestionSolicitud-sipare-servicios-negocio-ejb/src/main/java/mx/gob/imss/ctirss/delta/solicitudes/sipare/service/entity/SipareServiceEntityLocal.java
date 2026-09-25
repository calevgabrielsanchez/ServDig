package mx.gob.imss.ctirss.delta.solicitudes.sipare.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.solicitudes.sipare.service.model.GraficaSolicitudes;

@Local
public interface SipareServiceEntityLocal {

	List<GraficaSolicitudes> obtenerUsuariosSipare(FiltroSolicitud filtros);

}
