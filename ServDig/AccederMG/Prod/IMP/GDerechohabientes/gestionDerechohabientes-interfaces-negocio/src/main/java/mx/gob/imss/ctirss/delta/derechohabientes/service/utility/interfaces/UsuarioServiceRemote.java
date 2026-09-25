package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;


@Remote
public interface UsuarioServiceRemote {
	
	public Usuario getUsuario(String nomUsuario, String password) throws DerechohabientesBusinessException, Exception;

}
