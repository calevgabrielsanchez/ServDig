package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;

@Local
public interface ProrrogaDerechohabienteEntityLocal {
	public void insert(TramiteProrroga tramiteProrroga);
}
