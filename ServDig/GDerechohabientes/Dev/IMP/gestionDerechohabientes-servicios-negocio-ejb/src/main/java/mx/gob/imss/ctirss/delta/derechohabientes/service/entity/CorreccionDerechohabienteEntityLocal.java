package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Local
public interface CorreccionDerechohabienteEntityLocal {
	
	void saveCorreccionDerechohabiente(TramiteCorreccionDerechohabiente correccion) throws DerechohabientesBusinessException, Exception;

}
