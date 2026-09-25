package mx.gob.imss.cit.cda.service.interfaces;

import javax.ejb.Remote;

@Remote
public interface EnvioCorreoMovimientosSindoRemote {

	void enviarCorreosMovimientoSindoCDA();
	
}
