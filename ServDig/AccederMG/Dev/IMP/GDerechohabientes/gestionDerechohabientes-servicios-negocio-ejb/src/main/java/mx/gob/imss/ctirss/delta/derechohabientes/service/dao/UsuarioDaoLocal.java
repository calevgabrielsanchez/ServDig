package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;


import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioOrdinario;


@Local
public interface UsuarioDaoLocal {

	public Usuario getUsuario(String nomUsuario) throws DerechohabientesBusinessException, Exception;
	public UsuarioOrdinario getUsuarioOrdinario(long idUsuario) throws DerechohabientesBusinessException, Exception;
	public UsuarioFuncionario getUsuarioFuncionario(long idUsuario) throws DerechohabientesBusinessException, Exception;
	public AsignacionNSS getAsignacionNss(long idPersona) throws DerechohabientesBusinessException, Exception;

}