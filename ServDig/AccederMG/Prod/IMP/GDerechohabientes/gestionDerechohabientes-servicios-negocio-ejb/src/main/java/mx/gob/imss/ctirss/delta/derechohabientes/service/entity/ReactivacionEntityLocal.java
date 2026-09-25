package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteReactivacionDerechohab;

@Local
public interface ReactivacionEntityLocal {
	
	TramiteReactivacionDerechohab insertFromTramiteReactivacion(TramiteReactivacionDerechohab tramite);
}
