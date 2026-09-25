package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.integracion.common.EmailDataWrapper;

@Local
public interface EmailServiceUtilityLocal {

	/**
	 * Servicio para enviar correo electrónico simple, es decir, sin adjuntos.
	 * <br><br>
	 * Para enviar a más de un destinatario basta con separar por comas cada uno
	 * de ellos (correo1@test.com, correo2@test.com, ....)
	 * 
	 * @param emailData
	 */
	void enviarCorreoSimple(EmailDataWrapper emailData);

	/**
	 * Servicio para enviar correo electrónico con archivos adjuntos.
	 * <br><br>
	 * Los archivos se deben descomponen en tres listas, la primera con los
	 * nombres de los archivos, la segunda debe contener el arreglo de bytes y
	 * la tercera el content-type de cada uno de los archivos
	 * <br><br>
	 * Para enviar a más de un destinatario basta con separar por comas cada uno
	 * de ellos (correo1@test.com, correo2@test.com, ....)
	 * 
	 * @param emailData
	 */
	void enviarCorreoAdjuntos(EmailDataWrapper emailData);

}
