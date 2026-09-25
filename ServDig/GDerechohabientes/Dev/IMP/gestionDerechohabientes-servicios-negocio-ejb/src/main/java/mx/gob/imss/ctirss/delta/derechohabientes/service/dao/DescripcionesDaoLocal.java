package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;

@Local
public interface DescripcionesDaoLocal {

	public Sexo getSexo(Long idSexo) throws DerechohabientesBusinessException;
	public Parentesco getParentesco(Long idParentesco) throws DerechohabientesBusinessException;
	public EstadoCivil getEstadoCivil(Long idEstadoCivil) throws DerechohabientesBusinessException;
	public EntidadFederativa getEntidadFederativa(String idEntidadFederativa) throws DerechohabientesBusinessException;
	public Asentamiento getAsentamiento(Asentamiento asentamiento) throws DerechohabientesBusinessException;
	public Vialidad getVialidad(Integer idVialidad) throws DerechohabientesBusinessException;
	public MedicoEnTurno getMedicoEnTurno(MedicoEnTurno medico) throws DerechohabientesBusinessException;
}
