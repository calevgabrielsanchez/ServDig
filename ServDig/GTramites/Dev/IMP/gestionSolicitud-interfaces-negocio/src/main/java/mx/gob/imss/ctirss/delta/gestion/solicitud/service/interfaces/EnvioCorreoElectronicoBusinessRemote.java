package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;

@Remote
public interface EnvioCorreoElectronicoBusinessRemote {
	
	void enviarCorreo(CorreoElectronicoDTO correoElectronicoDTO) throws Exception;
	
	void enviarCorreo(CorreoElectronicoDTO correoElectronicoDTO, String from) throws Exception;
	
	/**
	 * Metodo que setea las propiedades para el envio de correo de la base de datos para unificar las fuetnes de envio
	 * en caso de no tener remitente setea los valores por default
	 * @param remitente
	 * @return
	 * @throws Exception
	 */
	JavaMailSenderImpl getPropertiesMail(String remitente) throws Exception;
	
	/**
	 * Metodo que devuelve el correo defual del remitente
	 * @return
	 * @throws Exception
	 */
	String getDefaultMailSender() throws Exception;

}
