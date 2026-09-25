package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.persistence.DitUmfConsTurnoMedico;

@Local
public interface MedicoEnTurnoDaoLocal {

	List<DitUmfConsTurnoMedico> getMedicosEnTurnobyUmf(Long idUmf) throws Exception;
	List<MedicoEnTurno> getMedicosPoblacionByUmfTurno(Long idUmf, Long idTurno) throws Exception;
	MedicoEnTurno getMedicoEnTurnoById(Long idMedicoEnTurno);
	Long getPoblacionByIdConsturnoMedico(Long idUmfConsTurno);
}