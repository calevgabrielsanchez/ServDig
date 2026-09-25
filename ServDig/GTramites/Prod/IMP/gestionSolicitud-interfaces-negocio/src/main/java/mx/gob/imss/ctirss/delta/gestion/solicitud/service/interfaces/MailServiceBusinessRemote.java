package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.base.model.MailComponent;

import javax.mail.MessagingException;

@Remote
public interface MailServiceBusinessRemote {
	void sendMail(MailComponent mailComponent) throws MessagingException;

	void sendTemplateMail(MailComponent mailComponent)
			throws MessagingException;
}
