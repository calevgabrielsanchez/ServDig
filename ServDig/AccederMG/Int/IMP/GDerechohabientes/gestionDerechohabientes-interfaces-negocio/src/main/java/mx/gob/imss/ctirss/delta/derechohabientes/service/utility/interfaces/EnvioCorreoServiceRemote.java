package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.EnvioCorreoResponse;

@Remote
public interface EnvioCorreoServiceRemote {	

	public EnvioCorreoResponse enviarCorreoPorNssMovil(String nss,String destinatario);

}
