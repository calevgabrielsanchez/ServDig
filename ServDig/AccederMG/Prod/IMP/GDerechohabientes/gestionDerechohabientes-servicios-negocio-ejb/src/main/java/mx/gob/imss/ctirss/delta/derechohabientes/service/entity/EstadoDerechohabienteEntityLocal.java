package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;

@Local
public interface EstadoDerechohabienteEntityLocal {

	EstadoDerechohabiente getEstadoDerechohabiente(Long idEstado) throws DerechohabientesBusinessException;
	SubEstadoDerechohabiente getSubEstadoDerechohabiente(Long idSubEstado) throws DerechohabientesBusinessException;
}
