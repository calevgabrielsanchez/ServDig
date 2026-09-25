package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;

@Remote
public interface EMailProducer {
	/**
	 * Agrega a la cola de mensajes de env�o de correo la informaci�n proporcionada
	 * @param emailRequest Informaci�n de remitente y mensaje de correo
	 */
	void agendarCorreoElectronico(EmailPayloadType emailRequest);
	void agendarCorreoElectronicoAcute(EmailPayloadType emailRequest);
}
