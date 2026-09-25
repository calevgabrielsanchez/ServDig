package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import javax.ejb.Remote;
import mx.gob.imss.ctirss.delta.model.email.EmailPayloadType;

@Remote
public interface EMailProducerRemote {

	public boolean registrarCorreoElectronico(EmailPayloadType emailRequest);

}
