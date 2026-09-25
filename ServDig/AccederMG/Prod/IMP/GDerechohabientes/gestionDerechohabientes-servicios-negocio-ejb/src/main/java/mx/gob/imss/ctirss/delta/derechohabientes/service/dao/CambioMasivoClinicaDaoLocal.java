package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import mx.gob.imss.ctirss.delta.persistence.DitCambioMasivoClinica;

public interface CambioMasivoClinicaDaoLocal {

	public abstract DitCambioMasivoClinica save(
			DitCambioMasivoClinica cambioMasivoClinica);

}