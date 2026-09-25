package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sau.Usuario;

@Remote
public interface ISAUServiciosDigitalesServiceRemote {

	Usuario buscarUsuarioPorUid(String curp) throws ServiciosRestException;

	List<Usuario> buscarUsuario(Usuario curp)
			throws ServiciosRestException;
	
	List<Usuario> buscarUsuarioGenral(Usuario curp)
			throws ServiciosRestException;
}
