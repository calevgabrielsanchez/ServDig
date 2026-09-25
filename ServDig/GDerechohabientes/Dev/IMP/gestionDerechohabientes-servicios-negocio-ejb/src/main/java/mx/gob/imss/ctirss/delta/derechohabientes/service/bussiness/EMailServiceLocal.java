package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabientes.MailProperties;
@Local
public interface EMailServiceLocal {
	
	public abstract void sendSimpleMail(MailProperties mailProperties) throws Exception;
	public abstract void sendMailConAdjunto(MailProperties mailProperties, byte[]adjunto) throws Exception;
	
	/**
	 * Metodo encargado de setear las propiedades de configuración del envio de correo
	 * Host servidor, remitente
	 * @return MailProperties con la información seteada 
	 * @throws Exception
	 */
	MailProperties getDefaultMailProperties() throws Exception;
	
	/**
	 * Metodo encargado de enviar un correo electronico con el reporte de vigencia
	 * @param asignacionNSS con la info del asegurado incluyendo el correo electronico
	 * @param atachDocto array de bytes con el pdf 
	 * @param url en caso de que se utilice la URL de descarga de documentos
	 * @throws Exception
	 */
	void enviaCorreoReporteVigenciaDerechos(AsignacionNSS asignacionNSS, byte[] atachDocto, String url) throws Exception;
	/**
	 * Metodo encargado de enviar un correo electronico con adjunto de prueba para consumir desde el EjbRemoto
	 * @param toEmail a quien se le notifica por correo
	 * @param ccEmail con copia a quien se notifica
	 * @param subject asunto del correo
	 * @param body contenido html del correo
	 * @param adjuntos documentos Adjuntos
	 */
	void enviaCorreoConDocumentoAdjunto(final String toEmail, final String ccEmail, final String subject, final String body, final Map<String,byte[]> adjuntos) throws Exception;
	/**
	 * Metodo para enviar correo de baja nbormativa con los documentos resultantes como adjuntos
	 * @param derechohabiente
	 * @param documentos
	 * @throws Exception
	 */
	void enviarCorreoBajaNormativa(Derechohabiente derechohabiente, byte[] documentos) throws Exception;
	
	void enviarCorreoCambioRegistroClinicaByQueue(final String toEmail,
			final String ccEmail, final String subject,
			final Map<String, byte[]> adjuntos, Map<String, String> mailAttr);
}