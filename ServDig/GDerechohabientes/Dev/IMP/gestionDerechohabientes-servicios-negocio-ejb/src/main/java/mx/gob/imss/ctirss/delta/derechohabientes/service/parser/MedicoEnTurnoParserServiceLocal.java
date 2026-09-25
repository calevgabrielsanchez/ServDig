package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsTurnoMedico;

@Local
public interface MedicoEnTurnoParserServiceLocal {
	
	DitUmfConsTurnoMedico modelToPersist(MedicoEnTurno entrada) throws DerechohabientesBusinessException;
	MedicoEnTurno persisToModel(DitUmfConsTurnoMedico entrada) throws DerechohabientesBusinessException;
	List<MedicoEnTurno> persisToModelList(List<DitUmfConsTurnoMedico> entrada) throws DerechohabientesBusinessException;
}
