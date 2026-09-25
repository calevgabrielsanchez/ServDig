package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.persistence.DicTurno;

public interface TurnoDaoLocal {

	DicTurno getTurno(Long idTurno);
	
	Turno getTurnoByID(Long idTurno)throws DerechohabientesBusinessException;
	List<Turno> getTurnos() throws DerechohabientesBusinessException;

}