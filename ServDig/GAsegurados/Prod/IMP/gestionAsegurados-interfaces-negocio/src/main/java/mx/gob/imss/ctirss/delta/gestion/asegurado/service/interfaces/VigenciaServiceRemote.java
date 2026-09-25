package mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.EnvioCorreoResponse;

@Remote
public interface VigenciaServiceRemote {
	
	public EnvioCorreoResponse consultaVigenciaMovilesEnvioCorreo(String curp, String nss, String correoElectronico);

}
