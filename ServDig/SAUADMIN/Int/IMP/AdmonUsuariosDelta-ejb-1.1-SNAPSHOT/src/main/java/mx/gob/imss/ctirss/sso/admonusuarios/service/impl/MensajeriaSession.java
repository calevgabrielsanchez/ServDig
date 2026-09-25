/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.selloDigital.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.sso.admonusuarios.service.MensajeriaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.SolicitudServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.soap.encoding.soapenc.Base64;

/**
 * @author cesarAgustin
 *
 */
@Stateless(name = "mensajeriaSession", mappedName = "mensajeriaSession")
public class MensajeriaSession implements MensajeriaSessionLocal {

	/** Log de la clase */
	private static Log logger = LogFactory.getLog(MensajeriaSession.class);

	@EJB
	private SolicitudServiceLocal solicitudCriteria;

	@EJB
	private AdmonUsuariosSessionLocal admonUsuarios;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.sso.admonusuarios.services.MensajeriaSessionRemote#
	 * enviarCorreo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean enviarCorreo(String origen, String destino, String mensaje, String titulo,
			SolicitudDTO datosSolicitud, String tipoAcuse) throws AdmonUsuariosException {
		logger.info("::: Enviando correo... " + datosSolicitud.getNombreCompleto());

		String password = "";

		if (datosSolicitud.getDelDTO() != null && datosSolicitud.getDelDTO().getCveDelegacion() > 0) {
			datosSolicitud.getDelDTO().setNombreDelegacion(
					solicitudCriteria.getDelegacionDesc(datosSolicitud.getDelDTO().getCveDelegacion()));
		} else {
			if (datosSolicitud.getDelDTO() == null) {
				datosSolicitud.setDelDTO(new DelegacionDTO());
			}
			datosSolicitud.getDelDTO().setNombreDelegacion("");
		}
		if (datosSolicitud.getSubdelDTO() != null && datosSolicitud.getSubdelDTO().getCveSubelegacion() > 0) {
			datosSolicitud.getSubdelDTO().setNombreSubelegacion(
					solicitudCriteria.getSubdeleagcionDesc(datosSolicitud.getSubdelDTO().getCveSubelegacion()));
		} else {
			if (datosSolicitud.getSubdelDTO() == null) {
				datosSolicitud.setSubdelDTO(new SubdelegacionDTO());
			datosSolicitud.getSubdelDTO().setNombreSubelegacion("");
			}
		}
		if (datosSolicitud.getDptoDTO().getCveSsodepto() > 0) {
			datosSolicitud.getDptoDTO()
					.setDesDepartamento(solicitudCriteria.getDeptoDesc(datosSolicitud.getDptoDTO().getCveSsodepto()));
		}
		if (datosSolicitud.getPuestoDTO().getCvePuesto() > 0) {
			datosSolicitud.getPuestoDTO()
					.setNombrePuesto(solicitudCriteria.getPuestoDesc(datosSolicitud.getPuestoDTO().getCvePuesto()));
		}

		String cadenaOriginal = generaCadenaOriginal(datosSolicitud, tipoAcuse);
		RespuestaFirmadoSimple firma = solicitudCriteria
				.getSelloDigital(generaCadenaOriginal(datosSolicitud, tipoAcuse), null, null);

		File archivoADjunto = generaReporteFinal(tipoAcuse, cadenaOriginal, firma.getSello(), mensaje, datosSolicitud);

		logger.debug("::: El sello es " + firma.getSello());

		try {
			UsuarioDTO usuario = admonUsuarios.obtenUsuario(datosSolicitud.getDesUsrCurp());
			if (usuario != null) {
				logger.info("########## NOMBRES [" + usuario.getNombres() + "] ##########");
				logger.info("########## APELLIDO PATERNO [" + usuario.getApellidoPaterno() + "] ##########");
				logger.info("########## APELLIDO MATERNO [" + usuario.getApellidoMaterno() + "] ##########");
				logger.info("########## UID [" + usuario.getUid() + "] ##########");
				logger.info("########## CURP [" + usuario.getCurp() + "] ##########");
				logger.info("########## SERIAL [" + usuario.getSerial() + "] ##########");
				logger.info("########## ACTIVO [" + usuario.isActivo() + "] ##########");

				password = usuario.getSerial();
			}

		} catch (AdmonUsuariosException e1) {
			e1.printStackTrace();
		}

		mensaje = generaMensaje(mensaje, datosSolicitud, password, null);
		logger.info("::: Mensaje usuario [\n" + mensaje + "\n]");

		try {
			// Propiedades de la conexin
			Properties props = new Properties();
			// Nombre del host de correo, es smtp.gmail.com
			// props.setProperty("mail.smtp.host", "172.16.23.18");
			props.setProperty("mail.smtp.host", "relay.imss.gob.mx");
			logger.info("ENVIANDO CORREO DESDE enviarCorreo++++++++++++++++++++++++++++++++++++++++++++++++++++");
			// Puerto para envio de correos
			props.setProperty("mail.smtp.port", "25");
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", "false");

			// SMTPAuthenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props);
			session.setDebug(true);

			String rutaOriginal = archivoADjunto.getAbsolutePath();
			MimeBodyPart texto = new MimeBodyPart();
			texto.setText(mensaje, "UTF-8");

			if (rutaOriginal == null) {
				rutaOriginal = "";
			}
			MimeBodyPart adjunto = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(rutaOriginal);
			adjunto.setDataHandler(new DataHandler(fds));
			adjunto.setFileName(fds.getName());

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			if (rutaOriginal != null && !rutaOriginal.equals("")) {
				multiParte.addBodyPart(adjunto);
			}

			MimeMessage message = new MimeMessage(session);
			// Quien envia el correo
			message.setFrom(new InternetAddress(origen));

			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
			message.setSubject(titulo, "utf-8");
			message.setText(mensaje);
			message.setContent(multiParte);
			Transport.send(message, message.getAllRecipients());
			/*
			 * //****SE AGREGA UN SEGUNDO ENVIO A LA CUENTA ORIGINARIA PARA
			 * REGISTRO DE MOVIMIENTOS MimeMessage message2 = new
			 * MimeMessage(session); // Quien envia el correo
			 * message2.setFrom(new InternetAddress(origen));
			 * 
			 * // A quien va dirigido
			 * message2.addRecipient(Message.RecipientType.TO, new
			 * InternetAddress(origen)); message2.setSubject(titulo,"utf-8");
			 * message2.setText(mensajeAprobador);
			 * message2.setContent(multiParte); Transport.send(message2,
			 * message2.getAllRecipients());
			 * //***************************************************************
			 * *********************+
			 * 
			 */

			logger.info("CORREO ENVIADO DESDE enviarCorreo ++++++++++++++++++++++++++++++++++++++++++++++++++++");
			return true;
		} catch (AddressException e) {
			logger.error("ERROR AL ENVIAR EL CORREO  ADDRESS :: " + e);
			return false;
		} catch (MessagingException e) {
			logger.error("ERROR AL ENVIAR EL CORREO :: " + e);
			return false;
		} finally {
			if (archivoADjunto != null) {
				archivoADjunto.deleteOnExit();
			}
		}
	}

	@Override
	public Boolean enviarCorreoAprobador(String origen, String destino, String mensaje, String titulo,
			SolicitudDTO datosSolicitud, String tipoAcuse) throws AdmonUsuariosException {
		logger.info("::: Enviando correo aprobador... " + datosSolicitud.getNombreCompleto());

		String password = "";

		if (datosSolicitud.getDelDTO() != null && datosSolicitud.getDelDTO().getCveDelegacion() > 0) {
			datosSolicitud.getDelDTO().setNombreDelegacion(
					solicitudCriteria.getDelegacionDesc(datosSolicitud.getDelDTO().getCveDelegacion()));
		} else {
			if (datosSolicitud.getDelDTO() == null) {
				datosSolicitud.setDelDTO(new DelegacionDTO());
			}
			datosSolicitud.getDelDTO().setNombreDelegacion("");
		}
		if (datosSolicitud.getSubdelDTO() != null && datosSolicitud.getSubdelDTO().getCveSubelegacion() > 0) {
			datosSolicitud.getSubdelDTO().setNombreSubelegacion(
					solicitudCriteria.getSubdeleagcionDesc(datosSolicitud.getSubdelDTO().getCveSubelegacion()));
		} else {
			if (datosSolicitud.getSubdelDTO() == null) {
				datosSolicitud.setSubdelDTO(new SubdelegacionDTO());
			}
			datosSolicitud.getSubdelDTO().setNombreSubelegacion("");
		}
		if (datosSolicitud.getDptoDTO().getCveSsodepto() > 0) {
			datosSolicitud.getDptoDTO()
					.setDesDepartamento(solicitudCriteria.getDeptoDesc(datosSolicitud.getDptoDTO().getCveSsodepto()));
		}
		if (datosSolicitud.getPuestoDTO().getCvePuesto() > 0) {
			datosSolicitud.getPuestoDTO()
					.setNombrePuesto(solicitudCriteria.getPuestoDesc(datosSolicitud.getPuestoDTO().getCvePuesto()));
		}

		String cadenaOriginal = generaCadenaOriginal(datosSolicitud, tipoAcuse);
		RespuestaFirmadoSimple firma = solicitudCriteria
				.getSelloDigital(generaCadenaOriginal(datosSolicitud, tipoAcuse), null, null);

		File archivoADjunto = generaReporteFinal(tipoAcuse, cadenaOriginal, firma.getSello(), mensaje, datosSolicitud);

		logger.debug("::: El sello es: " + firma.getSello());

		try {
			UsuarioDTO usuario = admonUsuarios.obtenUsuario(datosSolicitud.getDesUsrCurp());
			if (usuario != null) {
				logger.info("########## NOMBRES [" + usuario.getNombres() + "] ##########");
				logger.info("########## APELLIDO PATERNO [" + usuario.getApellidoPaterno() + "] ##########");
				logger.info("########## APELLIDO MATERNO [" + usuario.getApellidoMaterno() + "] ##########");
				logger.info("########## UID [" + usuario.getUid() + "] ##########");
				logger.info("########## CURP [" + usuario.getCurp() + "] ##########");
				logger.info("########## SERIAL [" + usuario.getSerial() + "] ##########");
				logger.info("########## ACTIVO [" + usuario.isActivo() + "] ##########");

				password = usuario.getSerial();
			}

		} catch (AdmonUsuariosException e1) {
			e1.printStackTrace();
		}

		mensaje = generaMensajeAprobador(mensaje, datosSolicitud, password, null);
		logger.info("::: Mensaje usuario [\n" + mensaje + "\n]");

		try {
			// Propiedades de la conexin
			Properties props = new Properties();
			// Nombre del host de correo, es smtp.gmail.com
			// props.setProperty("mail.smtp.host", "172.16.23.18");
			props.setProperty("mail.smtp.host", "relay.imss.gob.mx");
			logger.info("ENVIANDO CORREO DESDE enviarCorreoAprobador ++++++++++++++++++++++++++++++++++++++++++++++++++++");
			// Puerto para envio de correos
			props.setProperty("mail.smtp.port", "25");
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", "false");

			// SMTPAuthenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props);
			session.setDebug(true);

			String rutaOriginal = archivoADjunto.getAbsolutePath();
			MimeBodyPart texto = new MimeBodyPart();
			texto.setText(mensaje, "UTF-8");

			if (rutaOriginal == null) {
				rutaOriginal = "";
			}
			MimeBodyPart adjunto = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(rutaOriginal);
			adjunto.setDataHandler(new DataHandler(fds));
			adjunto.setFileName(fds.getName());

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			if (rutaOriginal != null && !rutaOriginal.equals("")) {
				multiParte.addBodyPart(adjunto);
			}

			MimeMessage message = new MimeMessage(session);
			// Quien envia el correo
			message.setFrom(new InternetAddress(origen));

			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
			message.setSubject(titulo, "utf-8");
			message.setText(mensaje);
			message.setContent(multiParte);
			Transport.send(message, message.getAllRecipients());
			/*
			 * //****SE AGREGA UN SEGUNDO ENVIO A LA CUENTA ORIGINARIA PARA
			 * REGISTRO DE MOVIMIENTOS MimeMessage message2 = new
			 * MimeMessage(session); // Quien envia el correo
			 * message2.setFrom(new InternetAddress(origen));
			 * 
			 * // A quien va dirigido
			 * message2.addRecipient(Message.RecipientType.TO, new
			 * InternetAddress(origen)); message2.setSubject(titulo,"utf-8");
			 * message2.setText(mensaje); message2.setContent(multiParte);
			 * Transport.send(message2, message2.getAllRecipients());
			 * //***************************************************************
			 * *********************+
			 */

			logger.info("CORREO ENVIADO DESDE enviarCorreoAprobador ++++++++++++++++++++++++++++++++++++++++++++++++++++");

			// ************************************************************************************+
			return true;
		} catch (AddressException e) {
			logger.error("ERROR AL ENVIAR EL CORREO  ADDRESS :: " + e);
			return false;
		} catch (MessagingException e) {
			logger.error("ERROR AL ENVIAR EL CORREO :: " + e);
			return false;
		} finally {
			if (archivoADjunto != null) {
				archivoADjunto.deleteOnExit();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.MensajeriaSessionRemote#
	 * enviarCorreo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean enviarCorreoConconfirmacion(String origen, String destino, String mensaje, String titulo,
			SolicitudDTO datosSolicitud, String tipoAcuse, String link) throws AdmonUsuariosException {
		logger.info("::: Enviando correo confirmacion... " + datosSolicitud.getNombreCompleto());

		String password = "";

		if (datosSolicitud.getDelDTO() != null && datosSolicitud.getDelDTO().getCveDelegacion() > 0) {
			datosSolicitud.getDelDTO().setNombreDelegacion(
					solicitudCriteria.getDelegacionDesc(datosSolicitud.getDelDTO().getCveDelegacion()));
		} else {
			if (datosSolicitud.getDelDTO() == null) {
				datosSolicitud.setDelDTO(new DelegacionDTO());
			}
			datosSolicitud.getDelDTO().setNombreDelegacion("");
		}
		if (datosSolicitud.getSubdelDTO() != null && datosSolicitud.getSubdelDTO().getCveSubelegacion() > 0) {
			datosSolicitud.getSubdelDTO().setNombreSubelegacion(
					solicitudCriteria.getSubdeleagcionDesc(datosSolicitud.getSubdelDTO().getCveSubelegacion()));
		} else {
			if (datosSolicitud.getSubdelDTO() == null) {
				datosSolicitud.setSubdelDTO(new SubdelegacionDTO());
			}
			datosSolicitud.getSubdelDTO().setNombreSubelegacion("");
		}
		if (datosSolicitud.getDptoDTO().getCveSsodepto() > 0) {
			datosSolicitud.getDptoDTO()
					.setDesDepartamento(solicitudCriteria.getDeptoDesc(datosSolicitud.getDptoDTO().getCveSsodepto()));
		}
		if (datosSolicitud.getPuestoDTO().getCvePuesto() > 0) {
			datosSolicitud.getPuestoDTO()
					.setNombrePuesto(solicitudCriteria.getPuestoDesc(datosSolicitud.getPuestoDTO().getCvePuesto()));
		}

		String cadenaOriginal = generaCadenaOriginal(datosSolicitud, tipoAcuse);

		RespuestaFirmadoSimple firma = solicitudCriteria
				.getSelloDigital(generaCadenaOriginal(datosSolicitud, tipoAcuse), null, null);

		File archivoADjunto = generaReporteFinal(tipoAcuse, cadenaOriginal, firma.getSello(), mensaje, datosSolicitud);

		logger.debug("::: El sello es: " + firma.getSello());

		try {
			UsuarioDTO usuario = admonUsuarios.obtenUsuario(datosSolicitud.getDesUsrCurp());
			if (usuario != null) {
				logger.info("########## NOMBRES [" + usuario.getNombres() + "] ##########");
				logger.info("########## APELLIDO PATERNO [" + usuario.getApellidoPaterno() + "] ##########");
				logger.info("########## APELLIDO MATERNO [" + usuario.getApellidoMaterno() + "] ##########");
				logger.info("########## UID [" + usuario.getUid() + "] ##########");
				logger.info("########## CURP [" + usuario.getCurp() + "] ##########");
				logger.info("########## SERIAL [" + usuario.getSerial() + "] ##########");
				logger.info("########## ACTIVO [" + usuario.isActivo() + "] ##########");

				password = usuario.getSerial();
			}

		} catch (AdmonUsuariosException e1) {
			e1.printStackTrace();
		}

		mensaje = generaMensaje(mensaje, datosSolicitud, password, link);
		logger.info("::: Mensaje usuario [\n" + mensaje + "\n]");

		try {
			// Propiedades de la conexion
			Properties props = new Properties();
			// Nombre del host de correo, es smtp.gmail.com
			// props.setProperty("mail.smtp.host", "172.16.23.18");
			props.setProperty("mail.smtp.host", "relay.imss.gob.mx");
			logger.info("ENVIANDO CORREO DESDE enviarCorreoConConfirmacion ++++++++++++++++++++++++++++++++++++++++++++++++++++");
			// Puerto para envio de correos
			props.setProperty("mail.smtp.port", "25");
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", "false");

			// SMTPAuthenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props);
			session.setDebug(true);

			String rutaOriginal = archivoADjunto.getAbsolutePath();
			MimeBodyPart texto = new MimeBodyPart();
			texto.setText(mensaje, "UTF-8");

			if (rutaOriginal == null) {
				rutaOriginal = "";
			}
			MimeBodyPart adjunto = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(rutaOriginal);
			adjunto.setDataHandler(new DataHandler(fds));
			adjunto.setFileName(fds.getName());

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			if (rutaOriginal != null && !rutaOriginal.equals("")) {
				multiParte.addBodyPart(adjunto);
			}

			MimeMessage message = new MimeMessage(session);
			// Quien envia el correo
			message.addHeader("Content-Type", "text/html; charset=UTF-8");
			message.setFrom(new InternetAddress(origen));

			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
			message.setSubject(titulo, "utf-8");
			message.setText(mensaje, "text/html");
			message.setContent(multiParte, "text/html");
			Transport.send(message, message.getAllRecipients());

			// ****SE AGREGA UN SEGUNDO ENVIO A LA CUENTA ORIGINARIA PARA
			// REGISTRO DE MOVIMIENTOS

			// ************************************************************************************+

			logger.info("CORREO ENVIADO DESDE enviarCorreoConConfirmacion ++++++++++++++++++++++++++++++++++++++++++++++++++++");

			return true;
		} catch (AddressException e) {
			logger.error("ERROR AL ENVIAR EL CORREO  ADDRESS :: " + e);
			return false;
		} catch (MessagingException e) {
			logger.error("ERROR AL ENVIAR EL CORREO :: " + e);
			return false;
		} finally {
			if (archivoADjunto != null) {
				archivoADjunto.deleteOnExit();
			}
			logger.debug("Termina enviar correo confirmacion");
		}

	}

	@Override
	public Boolean enviarCorreoConconfirmacionAprobador(String origen, String destino, String mensaje, String titulo,
			SolicitudDTO datosSolicitud, String tipoAcuse, String link) throws AdmonUsuariosException {
		logger.info("::: Enviando correo confirmacion aprobador... " + datosSolicitud.getNombreCompleto());

		String password = "";

		if (datosSolicitud.getDelDTO() != null && datosSolicitud.getDelDTO().getCveDelegacion() > 0) {
			datosSolicitud.getDelDTO().setNombreDelegacion(
					solicitudCriteria.getDelegacionDesc(datosSolicitud.getDelDTO().getCveDelegacion()));
		} else {
			if (datosSolicitud.getDelDTO() == null) {
				datosSolicitud.setDelDTO(new DelegacionDTO());
			}
			datosSolicitud.getDelDTO().setNombreDelegacion("");
		}
		if (datosSolicitud.getSubdelDTO() != null && datosSolicitud.getSubdelDTO().getCveSubelegacion() > 0) {
			datosSolicitud.getSubdelDTO().setNombreSubelegacion(
					solicitudCriteria.getSubdeleagcionDesc(datosSolicitud.getSubdelDTO().getCveSubelegacion()));
		} else {
			if (datosSolicitud.getSubdelDTO() == null) {
				datosSolicitud.setSubdelDTO(new SubdelegacionDTO());
			}
			datosSolicitud.getSubdelDTO().setNombreSubelegacion("");
		}
		if (datosSolicitud.getDptoDTO().getCveSsodepto() > 0) {
			datosSolicitud.getDptoDTO()
					.setDesDepartamento(solicitudCriteria.getDeptoDesc(datosSolicitud.getDptoDTO().getCveSsodepto()));
		}
		if (datosSolicitud.getPuestoDTO().getCvePuesto() > 0) {
			datosSolicitud.getPuestoDTO()
					.setNombrePuesto(solicitudCriteria.getPuestoDesc(datosSolicitud.getPuestoDTO().getCvePuesto()));
		}

		String cadenaOriginal = generaCadenaOriginal(datosSolicitud, tipoAcuse);

		RespuestaFirmadoSimple firma = solicitudCriteria
				.getSelloDigital(generaCadenaOriginal(datosSolicitud, tipoAcuse), null, null);

		File archivoADjunto = generaReporteFinal(tipoAcuse, cadenaOriginal, firma.getSello(), mensaje, datosSolicitud);

		logger.debug("::: El sello es: " + firma.getSello());

		try {
			UsuarioDTO usuario = admonUsuarios.obtenUsuario(datosSolicitud.getDesUsrCurp());
			if (usuario != null) {
				logger.info("########## NOMBRES [" + usuario.getNombres() + "] ##########");
				logger.info("########## APELLIDO PATERNO [" + usuario.getApellidoPaterno() + "] ##########");
				logger.info("########## APELLIDO MATERNO [" + usuario.getApellidoMaterno() + "] ##########");
				logger.info("########## UID [" + usuario.getUid() + "] ##########");
				logger.info("########## CURP [" + usuario.getCurp() + "] ##########");
				logger.info("########## SERIAL [" + usuario.getSerial() + "] ##########");
				logger.info("########## ACTIVO [" + usuario.isActivo() + "] ##########");

				password = usuario.getSerial();
			}

		} catch (AdmonUsuariosException e1) {
			e1.printStackTrace();
		}

		mensaje = generaMensajeAprobadorHTML(mensaje, datosSolicitud, password, link);
		logger.info("::: Mensaje usuario [\n" + mensaje + "\n]");

		try {
			// Propiedades de la conexion
			Properties props = new Properties();
			// Nombre del host de correo, es smtp.gmail.com
			// props.setProperty("mail.smtp.host", "172.16.23.18");
			props.setProperty("mail.smtp.host", "relay.imss.gob.mx");
			logger.info("ENVIANDO CORREO DESDE enviarCorreoConConfirmacionAprobador ++++++++++++++++++++++++++++++++++++++++++++++++++++");
			// Puerto para envio de correos
			props.setProperty("mail.smtp.port", "25");
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", "false");

			// SMTPAuthenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props);
			session.setDebug(true);

			String rutaOriginal = archivoADjunto.getAbsolutePath();
			MimeBodyPart texto = new MimeBodyPart();
			texto.setText(mensaje, "UTF-8");

			if (rutaOriginal == null) {
				rutaOriginal = "";
			}
			MimeBodyPart adjunto = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(rutaOriginal);
			adjunto.setDataHandler(new DataHandler(fds));
			adjunto.setFileName(fds.getName());

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			if (rutaOriginal != null && !rutaOriginal.equals("")) {
				multiParte.addBodyPart(adjunto);
			}

			MimeMessage message = new MimeMessage(session);
			// Quien envia el correo
			message.setFrom(new InternetAddress(origen));
			message.addHeader("Content-Type", "text/html; charset=UTF-8");
			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
			message.setSubject(titulo, "utf-8");
			message.setText(mensaje, "text/html");

			message.setContent(multiParte, "text/html");

			Transport.send(message, message.getAllRecipients());
			// ****SE AGREGA UN SEGUNDO ENVIO A LA CUENTA ORIGINARIA PARA
			// REGISTRO DE MOVIMIENTOS

			// ************************************************************************************+

			logger.info("CORREO ENVIADO DESDE enviarCorreoConConfirmacionAprobador ++++++++++++++++++++++++++++++++++++++++++++++++++++");

			return true;
		} catch (AddressException e) {
			logger.error("ERROR AL ENVIAR EL CORREO  ADDRESS :: " + e);
			return false;
		} catch (MessagingException e) {
			logger.error("ERROR AL ENVIAR EL CORREO :: " + e);
			return false;
		} finally {
			if (archivoADjunto != null) {
				archivoADjunto.deleteOnExit();
			}
			logger.debug("Termina enviar correo confirmacion");
		}

	}

	private String generaMensaje(String mensaje, SolicitudDTO sol, String password, String link) {
		String msg = mensaje + "\n\n";
		msg = msg + "Los datos de la cuenta se detallan como:\n";
		msg = msg + "Usuario:  " + sol.getDesUsrCurp() + "\n";
		msg = msg + "Contrase\u00F1a:  " + password + "\n\n";
		if (sol.getPerfilesDTO() != null && sol.getPerfilesDTO().size() > 0) {
			msg = msg + "Los grupos definidos en la cuenta:\n";
			for (PerfilDTO per : sol.getPerfilesDTO()) {
				msg = msg + "- " + per.getPuestoDTO().getNombrePuesto() + "\n";
			}
		}
		if (sol.getModulosDTO() != null && sol.getModulosDTO().size() > 0) {
			msg = msg + "Los m\u00F3dulos definidos en la cuenta:\n";
			for (ModuloDTO mod : sol.getModulosDTO()) {
				msg = msg + "- " + mod.getDesModulo() + "\n";
			}
		}
		if (link != null) {
			msg = msg + "\n\n\n\n";
			msg = msg + "Para activar su cuenta favor de ingresar al siguiente url:";
			msg = msg + "\n";
			msg = msg + link;
		}
		return msg;
	}

	private String generaMensajeAprobador(String mensaje, SolicitudDTO sol, String password, String link) {
		String msg = mensaje + "\n\n";
		msg = msg + "Los datos de la cuenta se detallan como:\n";
		msg = msg + "Usuario:  " + sol.getDesUsrCurp() + "\n";
		if (sol.getPerfilesDTO() != null && sol.getPerfilesDTO().size() > 0) {
			msg = msg + "Los grupos definidos en la cuenta:\n";
			for (PerfilDTO per : sol.getPerfilesDTO()) {
				msg = msg + "- " + per.getPuestoDTO().getNombrePuesto() + "\n";
			}
		}
		if (sol.getModulosDTO() != null && sol.getModulosDTO().size() > 0) {
			msg = msg + "Los m\u00F3dulos definidos en la cuenta:\n";
			for (ModuloDTO mod : sol.getModulosDTO()) {
				msg = msg + "- " + mod.getDesModulo() + "\n";
			}
		}
		return msg;
	}

	private String generaMensajeHTML(String mensaje, SolicitudDTO sol, String password, String link) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<html>");
		stringBuilder.append("<body>");
		stringBuilder.append("<div style='width: 95%; background-color: lightgray; padding: 40px;'>");
		stringBuilder.append("<div style='background-color: white; margin: 0px auto; height: auto;'>");
		stringBuilder.append("<table align='center' style='width: 80%'>");
		stringBuilder.append("<tbody>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='center'>");
		stringBuilder.append("<h2 style='font-size:30px;'>Instituto Mexicano del Seguro Social</h2>");
		stringBuilder.append("<br/>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");

		stringBuilder.append("<tr>");
		stringBuilder.append("<td align='center'>");
		stringBuilder.append("<a style='line-height:40px;font-size:11px;display:inline;margin-left:5px;color:#999;' href='http://www.imss.gob.mx' title='Portal IMSS'>");
		stringBuilder.append("Visita el portal oficial del Instituto Mexicano del Seguro Social");
		stringBuilder.append("</a>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");

		stringBuilder.append("<tr>");
		stringBuilder.append("<td>");
		stringBuilder.append("<p style='font-size: 16px !important; text-align: justify;'>");
		stringBuilder.append("<br/>");

		stringBuilder.append("<br/>" + sol.getNombreCompleto().toUpperCase());
		stringBuilder.append("<br/>" + sol.getPuestoDTO().getNombrePuesto().toUpperCase());

		stringBuilder.append("<br/>");
		if (sol.getUmfId() > 0) {
			stringBuilder.append("<br/> &#193;rea UMF");
			stringBuilder.append("<br/> " + sol.getDelDTO().getNombreDelegacion());
			stringBuilder.append("<br/> " + sol.getSubdelDTO().getNombreSubelegacion());
			stringBuilder.append("<br/> " + sol.getUmfDTO().getNombreUmf());
		} else if (sol.getSubdelegacionId() > 0) {
			stringBuilder.append("<br/> &#193;rea Subdelegaci&#243;n");
			stringBuilder.append("<br/> " + sol.getDelDTO().getNombreDelegacion());
			stringBuilder.append("<br/> " + sol.getSubdelDTO().getNombreSubelegacion());
		} else if (sol.getDelegacionId() > 0) {
			stringBuilder.append("<br/> &#193;rea Delegaci&#243;n");
			stringBuilder.append("<br/> " + sol.getDelDTO().getNombreDelegacion());
		} else {
			stringBuilder.append("<br/> &#193;rea Central");
		}
		stringBuilder.append("<br/>");
		stringBuilder.append(
				"<br/>Se le informa que el d&#237;a de hoy " + formateaFecha(new Date()) + " su cuenta de usuario "
						+ sol.getDesUsrCurp() + " " + mensaje + " en el Sistema de Administraci&#243;n de Usuarios "
						+ "de los Servicios Digitales, con los siguientes grupos asociados a la cuenta:");

		stringBuilder.append("<br/>");

		stringBuilder.append("<br/>Usuario:  " + sol.getDesUsrCurp());
		stringBuilder.append("<br/>Contrase&#241;a:  " + remplazarAcentosHTML(password));

		if (sol.getPerfilesDTO() != null && sol.getPerfilesDTO().size() > 0) {
			stringBuilder.append("<br/><br/>Los grupos definidos en la cuenta:");
			for (PerfilDTO per : sol.getPerfilesDTO()) {
				stringBuilder.append("<br/> - " + per.getPuestoDTO().getNombrePuesto());
			}
		}
		if (sol.getModulosDTO() != null && sol.getModulosDTO().size() > 0) {
			stringBuilder.append("<br/><br/>Los m&#243;dulos definidos en la cuenta:");
			for (ModuloDTO mod : sol.getModulosDTO()) {
				stringBuilder.append("<br/>- " + mod.getDesModulo());
			}
		}
		if (link != null) {
			stringBuilder.append("          ");
			stringBuilder.append("<br/><br/>");
			stringBuilder.append("<br/>Para activar su cuenta favor de ingresar a la siguiente url");
			stringBuilder.append("<br/><br/>");
			stringBuilder.append("<br/>" + link);
		}

		stringBuilder.append("<br/>");
		stringBuilder.append("<br/>Se adjunta acuse con la carta responsiva de la cuenta de usuario, misma que deber&#225; ser firmada aut&#243;grafamente y entregada al personal responsable en su &#225;rea. De conformidad con el articulo 22 de la Ley del Seguro Social"
				+ ", recuerda que est&#225;s obligado a no compartir, divulgar, ni dar a conocer a terceros ajenos a la operaci&#243;n,  la documentaci&#243;n, datos e informes, a que tenga acceso en los sistemas institucionales utilizando la Clave de Usuario descrita con anterioridad.");
		stringBuilder.append("<br/>");

		stringBuilder.append("</p>");
		stringBuilder.append("<br/>");
		stringBuilder.append("</td>");
		stringBuilder.append("</tr>");

		stringBuilder.append("<tfoot style='width: 90%'>");
		stringBuilder.append("<tr>");
		stringBuilder.append("<th width='50%'>");
		stringBuilder.append("<div style='text-align: center; padding-bottom: 30px;'>");
		stringBuilder.append("<h4 style='font-size: 14px !important; '>Gracias por usar los servicios digitales creados para usted por el Instituto Mexicano del Seguro Social.</h4>");
		stringBuilder.append("<br/>");
		stringBuilder.append("<br/>");
		stringBuilder.append("<p style='text-align: center; font-size: 10px;'> Reforma 476, Col. Ju&#224;rez, Ciudad de M&#233;xico - Tel&#233;fono: 800 623 2323 - <br/> ALGUNOS DERECHOS RESERVADOS IMSS - 2012</p>");
		stringBuilder.append("</div>");
		stringBuilder.append("</th>");
		stringBuilder.append("</tr>");
		stringBuilder.append("</tfoot>");

		stringBuilder.append("</tbody>");
		stringBuilder.append("</table>");
		stringBuilder.append("</div>");
		stringBuilder.append("</div>");
		stringBuilder.append("</body>");
		stringBuilder.append("</html>");
		return stringBuilder.toString();
	}

	public String remplazarAcentosHTML(String stringAcentos) {
		if (stringAcentos != null && stringAcentos.trim().length() > 0) {
			if (stringAcentos.contains("á")) {
				stringAcentos = stringAcentos.replace("á", "&#225;");
			}
			if (stringAcentos.contains("é")) {
				stringAcentos = stringAcentos.replace("é", "&#233;");
			}
			if (stringAcentos.contains("í")) {
				stringAcentos = stringAcentos.replace("í", "&#237;");
			}
			if (stringAcentos.contains("ó")) {
				stringAcentos = stringAcentos.replace("ó", "&#243;");
			}
			if (stringAcentos.contains("ú")) {
				stringAcentos = stringAcentos.replace("ú", "&#250;");
			}
			if (stringAcentos.contains("ñ")) {
				stringAcentos = stringAcentos.replace("ñ", "&#241;");
			}
			if (stringAcentos.contains("Á")) {
				stringAcentos = stringAcentos.replace("Á", "&#193;");
			}
			if (stringAcentos.contains("É")) {
				stringAcentos = stringAcentos.replace("É", "&#201;");
			}
			if (stringAcentos.contains("Í")) {
				stringAcentos = stringAcentos.replace("Í", "&#205;");
			}
			if (stringAcentos.contains("Ó")) {
				stringAcentos = stringAcentos.replace("Ó", "&#211;");
			}
			if (stringAcentos.contains("Ú")) {
				stringAcentos = stringAcentos.replace("Ú", "&#218;");
			}
			if (stringAcentos.contains("Ñ")) {
				stringAcentos = stringAcentos.replace("Ñ", "&#209;");
			}
		}
		return stringAcentos;
	}

	private String generaMensajeAprobadorHTML(String mensaje, SolicitudDTO sol, String password, String link) {
		String msg = "Instituto Mexicano del Seguro Social" + "\n" + "\n";

		msg = msg + "Visita el portal oficial del Instituto Mexicano del Seguro Social - http://www.imss.gob.mx" + "\n"
				+ "\n" + "\n";

		msg = msg + sol.getNombreCompleto().toUpperCase() + "\n" + sol.getPuestoDTO().getNombrePuesto().toUpperCase()
				+ "\n" + "\n";

		if (sol.getUmfId() > 0) {
			msg = msg + "\u00C1rea UMF" + "\n" + sol.getDelDTO().getNombreDelegacion() + "\n"
					+ sol.getSubdelDTO().getNombreSubelegacion() + "\n" + sol.getUmfDTO().getNombreUmf() + "\n";
		} else if (sol.getSubdelegacionId() > 0) {
			msg = msg + "\u00C1rea Subdelegaci\u00F3n" + "\n" + sol.getDelDTO().getNombreDelegacion() + "\n"
					+ sol.getSubdelDTO().getNombreSubelegacion() + "\n";
		} else if (sol.getDelegacionId() > 0) {
			msg = msg + "\u00C1rea Delegaci\u00F3n" + "\n" + sol.getDelDTO().getNombreDelegacion() + "\n";
		} else
			msg = msg + "\u00C1rea Central" + "\n" + "\n";

		msg = msg + "Se le informa que el d\u00EDa de hoy " + formateaFecha(new Date()) + " su cuenta de usuario "
				+ sol.getDesUsrCurp() + " " + mensaje + " en el Sistema de Administraci\u00F3n de Usuarios "
				+ "de los Servicios Digitales, con los siguientes grupos asociados a la cuenta:" + "\n" + "\n";

		msg = msg + "Usuario:  " + sol.getDesUsrCurp() + "\n" + "\n";

		if (sol.getPerfilesDTO() != null && sol.getPerfilesDTO().size() > 0) {
			msg = msg + "Los grupos definidos en la cuenta:";
			for (PerfilDTO per : sol.getPerfilesDTO()) {
				msg = msg + "\n- " + per.getPuestoDTO().getNombrePuesto() + "\n" + "\n";
			}
		}
		if (sol.getModulosDTO() != null && sol.getModulosDTO().size() > 0) {
			msg = msg + "Los m\u00F3dulos definidos en la cuenta:";
			for (ModuloDTO mod : sol.getModulosDTO()) {
				msg = msg + "\n- " + mod.getDesModulo() + "\n" + "\n";
			}
		}

		msg = msg + "Se adjunta acuse con la carta responsiva de la cuenta de usuario, misma que deber\u00E1 ser firmada aut\u00F3grafamente y entregada al personal responsable en su \u00E1rea. De conformidad con el art\u00EDculo 22 de la Ley del Seguro Social"
				+ ", recuerda que est\u00E1s obligado a no compartir, divulgar, ni dar a conocer a terceros ajenos a la operaci\u00F3n, la documentaci\u00F3n, datos e informes, a que tenga acceso en los sistemas institucionales utilizando la Clave de Usuario descrita con anterioridad."
				+ "\n" + "\n" + "\n";

		msg = msg + "Gracias por usar los servicios digitales creados para usted por el Instituto Mexicano del Seguro Social."
				+ "\n" + "\n" + "Reforma 476, Col. Ju\u00E1rez, Ciudad de M\u00E9xico - Tel\u00E9fono: 800 623 2323" + " - ";

		msg = msg + "\n" + "ALGUNOS DERECHOS RESERVADOS IMSS - 2012";

		return msg;
	}

	@Override
	public Boolean enviarCorreoContrasena(String origen, String destino, UsuarioDTO user, String contrasenia)
			throws AdmonUsuariosException {
		logger.info("ENVIANDO CORREO DESDE enviarCorreoContrasena ++++++++++++++++++++++++++++++++++++++++++++++++++++");
		try {
			String titulo = "Recuperaci\u00F3n de contrase\u00F1a";
			String cuerpo = "La recuperaci\u00F3n de credenciales de la cuenta de usuario: \n  Cuenta a nombre de : \t"
					+ user.getNombres() + " " + user.getApellidoPaterno() + " " + user.getApellidoMaterno()
					+ " \n CURP: \t" + user.getCurp() + " \n Contrase\u00F1a: \t" + user.getPassword();
			logger.info("::: Mensaje cuerpo [\n" + cuerpo + "\n]");
			// Propiedades de la conexin
			Properties props = new Properties();
			// Nombre del host de correo, es smtp.gmail.com
			// props.setProperty("mail.smtp.host", "172.16.23.18");
			props.setProperty("mail.smtp.host", "relay.imss.gob.mx");
			// Puerto para envio de correos
			props.setProperty("mail.smtp.port", "25");
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", "false");

			// SMTPAuthenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props);
			session.setDebug(true);

			MimeBodyPart texto = new MimeBodyPart();
			texto.setText(cuerpo, "UTF-8");
			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);

			MimeMessage message = new MimeMessage(session);
			// Quien envia el correo
			message.setFrom(new InternetAddress(origen));

			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
			message.setSubject(titulo, "utf-8");
			message.setText(cuerpo);
			message.setContent(multiParte);

			Transport.send(message, message.getAllRecipients());

			// MimeMessage message2 = new MimeMessage(session);
			// // Quien envia el correo
			// message2.setFrom(new InternetAddress(origen));
			//
			// // A quien va dirigido
			// message2.addRecipient(Message.RecipientType.TO, new
			// InternetAddress(origen));
			// message2.setSubject(titulo,"utf-8");
			// message2.setText(cuerpo);
			// message2.setContent(multiParte);
			//
			// Transport.send(message2);
			logger.info("CORREO ENVIADO DESDE enviarCorreoContrasena ++++++++++++++++++++++++++++++++++++++++++++++++++++");
			return true;
		} catch (AddressException e) {
			logger.error("ERROR AL ENVIAR EL CORREO  ADDRESS :: " + e);
			return false;
		} catch (MessagingException e) {
			logger.error("ERROR AL ENVIAR EL CORREO :: " + e);
			return false;
		}
	}

	public Boolean enviarCorreo(String origen, String destino, String mensaje, String titulo)
			throws AdmonUsuariosException {
		try {
			// Propiedades de la conexin

			// Propiedades de la conexin
			Properties props = new Properties();
			// Nombre del host de correo, es smtp.gmail.com
			// props.setProperty("mail.smtp.host", "172.16.23.18");
			props.setProperty("mail.smtp.host", "relay.imss.gob.mx");
			logger.info("ENVIANDO CORREO DESDE enviarCorreo ++++++++++++++++++++++++++++++++++++++++++++++++++++");
			// Puerto para envio de correos
			props.setProperty("mail.smtp.port", "25");
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", "false");

			// SMTPAuthenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props);
			session.setDebug(true);

			String rutaOriginal = null;
			MimeBodyPart texto = new MimeBodyPart();
			texto.setText(mensaje, "UTF-8");

			if (rutaOriginal == null) {
				rutaOriginal = "";
			}
			MimeBodyPart adjunto = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(rutaOriginal);
			adjunto.setDataHandler(new DataHandler(fds));
			adjunto.setFileName(fds.getName());

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			if (rutaOriginal != null && !rutaOriginal.equals("")) {
				multiParte.addBodyPart(adjunto);
			}

			MimeMessage message = new MimeMessage(session);
			// Quien envia el correo
			message.setFrom(new InternetAddress(origen));

			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
			message.setSubject(titulo, "utf-8");
			message.setText(mensaje);
			message.setContent(multiParte);
			Transport.send(message, message.getAllRecipients());
			// ****SE AGREGA UN SEGUNDO ENVIO A LA CUENTA ORIGINARIA PARA
			// REGISTRO DE MOVIMIENTOS
			MimeMessage message2 = new MimeMessage(session);
			// Quien envia el correo
			message2.setFrom(new InternetAddress(origen));

			// A quien va dirigido
			message2.addRecipient(Message.RecipientType.TO, new InternetAddress(origen));
			message2.setSubject(titulo, "utf-8");
			message2.setText(mensaje);
			message2.setContent(multiParte);
			Transport.send(message2, message2.getAllRecipients());
			// ************************************************************************************+

			logger.info("CORREO ENVIADO DESDE enviarCorreo ++++++++++++++++++++++++++++++++++++++++++++++++++++");
			return true;
		} catch (AddressException e) {
			logger.error("ERROR AL ENVIAR EL CORREO  ADDRESS :: " + e);
			return false;
		} catch (MessagingException e) {
			logger.error("ERROR AL ENVIAR EL CORREO :: " + e);
			return false;
		}
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("", "");
			// return new PasswordAuthentication("cesar.soto@novutek.com", "NVKm2XKV7");
		}
	}

	private String generaCadenaOriginal(SolicitudDTO datosSolicitud, String tipoAcuse) {

		StringBuilder cadena = new StringBuilder();
		if (datosSolicitud != null) {
			cadena.append("||");
			cadena.append("Acuse:ADMINSSO|");
			cadena.append("Invocante:ADMINSSO|");
			if (tipoAcuse != null && tipoAcuse.trim().length() > 0) {
				cadena.append("Tramite:" + tipoAcuse + "|");
			}
			cadena.append("Fecha:" + formateaFechaHora(new Date()) + "|");
			if (datosSolicitud.getCveMatricula() != null && datosSolicitud.getCveMatricula().trim().length() > 0) {
				cadena.append("Matricula:" + datosSolicitud.getCveMatricula() 
					+ "|");
				cadena.append("Nss:" + datosSolicitud.getNssNom() 
					+ "|");
			}
			cadena.append("Curp:" + datosSolicitud.getDesUsrCurp() 
					+ "|");
			cadena.append("Nombre:" + datosSolicitud.getNomNombre() 
					+ "|");
			cadena.append("Paterno:" + datosSolicitud.getNomPaterno() 
					+ "|");
			cadena.append("Materno:" + datosSolicitud.getNomMaterno() 
					+ "|");
			cadena.append("Area normativa:" + (datosSolicitud.getAreaNorm() != null ? datosSolicitud.getAreaNorm().getDesAreanorma() : "") 
					+ "|");
			cadena.append("Delegacion:" + (datosSolicitud.getDelDTO() != null ? datosSolicitud.getDelDTO().getNombreDelegacion() : "")
					+ "|");
			cadena.append("Subdelegacion:" + (datosSolicitud.getSubdelDTO() != null ? datosSolicitud.getSubdelDTO().getNombreSubelegacion() : "") 
					+ "|");
			cadena.append("Departamento:" + (datosSolicitud.getDptoDTO() != null ? datosSolicitud.getDptoDTO().getDesDepartamento() : "")
					+ "|");
			cadena.append("Puesto:" + (datosSolicitud.getPuestoDTO() != null ? datosSolicitud.getPuestoDTO().getNombrePuesto() : ""));
		}
		cadena.append("||");
		logger.info("::: Cadena original: " + cadena.toString());

		return cadena.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public File generaReporte(String cadenaOriginal, String selloDigital, String mensaje, SolicitudDTO datosSolicitud) {

		File archivoAdjunto = null;
		String acusePdf = null;
		Map parameters = new HashMap();

		parameters.put("cadenaOriginal", cadenaOriginal);
		parameters.put("selloDigital", selloDigital);
		parameters.put("mensaje", mensaje);
		parameters.put("leyenda", datosSolicitud.getDptoDTO().getDesLeyendaAcuse());
		logger.debug("La leyenda a mostrar en el formato pdf es: " + datosSolicitud.getDptoDTO().getDesLeyendaAcuse());
		String curp = "";
		if (datosSolicitud != null) {
			parameters.put("curp", datosSolicitud.getDesUsrCurp());
			curp = datosSolicitud.getDesUsrCurp();
		}

		try {
			UsuarioDTO usuario = admonUsuarios.obtenUsuario(curp);
			if (usuario != null) {
				parameters.put("password", usuario.getSerial());
			}

			StringBuffer modulos = new StringBuffer();
			if (!datosSolicitud.getModulosDTO().isEmpty()) {
				for (ModuloDTO mod : datosSolicitud.getModulosDTO()) {
					modulos.append(mod.getDesModulo() + ",");
				}
				parameters.put("modulos", modulos.toString());
			} else {
				parameters.put("modulos", "SIN MODULOS");
			}

			StringBuffer puestos = new StringBuffer();
			if (!datosSolicitud.getPuestosDTO().isEmpty()) {
				for (PuestoDTO puesto : datosSolicitud.getPuestosDTO()) {
					puestos.append(puesto.getNombrePuesto() + ",");
				}
				parameters.put("grupos", puestos.toString());
			} else {
				parameters.put("grupos", "SIN GRUPOS");
			}

		} catch (AdmonUsuariosException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] reporte = null;

		InputStream is = MensajeriaSession.class.getResourceAsStream("/acuseCorreo.jasper");

		try {
			reporte = JasperRunManager.runReportToPdf(is, parameters, new JREmptyDataSource());
			is.close();

			acusePdf = org.apache.soap.encoding.soapenc.Base64.encode(reporte);

			byte[] bytesArch = Base64.decode(acusePdf);

			File pdf = File.createTempFile("adjunto", ".pdf");
			OutputStream out = new FileOutputStream(pdf.getAbsolutePath());
			out.write(bytesArch);
			out.close();
			archivoAdjunto = pdf;

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// logger.debug("vaaa");
		// String ruta = (MensajeriaSession.class).getResource("").getPath();
		// ruta = ruta.substring(0, ruta.indexOf("WEB-INF")) +
		// "resources/archivos";
		// String rutaOriginal = ruta;
		// logger.debug("ruta original "+rutaOriginal);
		return archivoAdjunto;

	}

	@SuppressWarnings("static-access")
	public String formateaFecha(Date f) {
		String fec = "";
		if (f != null) {
			Calendar fecha = Calendar.getInstance();
			fecha.setTime(f);

			if (fecha.get(fecha.DAY_OF_MONTH) < 10) {
				fec = "0" + fecha.get(fecha.DAY_OF_MONTH) + " de ";
			} else {
				fec = fecha.get(fecha.DAY_OF_MONTH) + " de ";
			}
			if (fecha.get(fecha.MONTH) == 0)
				fec = fec + "Enero";
			if (fecha.get(fecha.MONTH) == 1)
				fec = fec + "Febrero";
			if (fecha.get(fecha.MONTH) == 2)
				fec = fec + "Marzo";
			if (fecha.get(fecha.MONTH) == 3)
				fec = fec + "Abril";
			if (fecha.get(fecha.MONTH) == 4)
				fec = fec + "Mayo";
			if (fecha.get(fecha.MONTH) == 5)
				fec = fec + "Junio";
			if (fecha.get(fecha.MONTH) == 6)
				fec = fec + "Julio";
			if (fecha.get(fecha.MONTH) == 7)
				fec = fec + "Agosto";
			if (fecha.get(fecha.MONTH) == 8)
				fec = fec + "Septiembre";
			if (fecha.get(fecha.MONTH) == 9)
				fec = fec + "Octubre";
			if (fecha.get(fecha.MONTH) == 10)
				fec = fec + "Noviembre";
			if (fecha.get(fecha.MONTH) == 11)
				fec = fec + "Diciembre";
	
			fec = fec + " de " + fecha.get(fecha.YEAR);

		}
		return fec;
	}

	@SuppressWarnings("static-access")
	public String formateaFechaHora(Date f) {
		String fec = "";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		if (f != null) {
			Calendar fecha = Calendar.getInstance();
			fecha.setTime(f);

			if (fecha.get(fecha.DAY_OF_MONTH) < 10) {
				fec = "0" + fecha.get(fecha.DAY_OF_MONTH) + " de ";
			} else {
				fec = fecha.get(fecha.DAY_OF_MONTH) + " de ";
			}
			if (fecha.get(fecha.MONTH) == 0)
				fec = fec + "Enero";
			if (fecha.get(fecha.MONTH) == 1)
				fec = fec + "Febrero";
			if (fecha.get(fecha.MONTH) == 2)
				fec = fec + "Marzo";
			if (fecha.get(fecha.MONTH) == 3)
				fec = fec + "Abril";
			if (fecha.get(fecha.MONTH) == 4)
				fec = fec + "Mayo";
			if (fecha.get(fecha.MONTH) == 5)
				fec = fec + "Junio";
			if (fecha.get(fecha.MONTH) == 6)
				fec = fec + "Julio";
			if (fecha.get(fecha.MONTH) == 7)
				fec = fec + "Agosto";
			if (fecha.get(fecha.MONTH) == 8)
				fec = fec + "Septiembre";
			if (fecha.get(fecha.MONTH) == 9)
				fec = fec + "Octubre";
			if (fecha.get(fecha.MONTH) == 10)
				fec = fec + "Noviembre";
			if (fecha.get(fecha.MONTH) == 11)
				fec = fec + "Diciembre";

			fec = fec + " de " + fecha.get(fecha.YEAR);

			fec = fec + ", " + sdf.format(f);

		}
		return fec;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public File generaReporteFinal(String tipoAcuse, String cadenaOriginal, String selloDigital, String mensaje,
			SolicitudDTO datosSolicitud) throws AdmonUsuariosException {
		File archivoAdjunto = null;
		try {

			String acusePdf = null;
			Map parameters = new HashMap();
			parameters.put("lugarFecha", "A " + formateaFecha(new Date()));
			parameters.put("nombreUsuario", datosSolicitud.getNombreCompleto());
			parameters.put("leyenda", datosSolicitud.getDptoDTO().getDesLeyendaAcuse());

			if (datosSolicitud.getCveMatricula() != null) {
				parameters.put("matricula", datosSolicitud.getCveMatricula());
			} else {
				parameters.put("matricula", "");
			}
			if (datosSolicitud.getNssNom() != null) {
				parameters.put("nss", datosSolicitud.getNssNom());
			} else {
				parameters.put("nss", "");
			}
			parameters.put("deptodesc", datosSolicitud.getDptoDTO().getDesDepartamento());

			String curp = "";
			if (datosSolicitud != null) {
				parameters.put("curp", datosSolicitud.getDesUsrCurp());
				parameters.put("user", datosSolicitud.getDesUsrCurp());
				curp = datosSolicitud.getDesUsrCurp();
			}

			parameters.put("rutaImagen", MensajeriaSession.class.getResource("/logo_gob.jpg").toString());
			parameters.put("rutaImagen2", MensajeriaSession.class.getResource("/logo_gob_2.jpg").toString());
			parameters.put("aprobador", datosSolicitud.getNombreAprobador());
			parameters.put("cadena", cadenaOriginal);
			parameters.put("sello", selloDigital);
			parameters.put("tipoAcuse", tipoAcuse);

			byte[] reporte = null;
			InputStream is = null;
			if (datosSolicitud.getCveMatricula() != null && datosSolicitud.getCveMatricula().trim().length() > 0) {
				is = MensajeriaSession.class.getResourceAsStream("/acuseCuenta.jasper");
			} else {
				is = MensajeriaSession.class.getResourceAsStream("/acuseCuentaTTD.jasper");
			}

			reporte = JasperRunManager.runReportToPdf(is, parameters, new JREmptyDataSource());
			is.close();

			acusePdf = org.apache.soap.encoding.soapenc.Base64.encode(reporte);

			byte[] bytesArch = Base64.decode(acusePdf);

			File pdf = File.createTempFile("adjunto", ".pdf");
			OutputStream out = new FileOutputStream(pdf.getAbsolutePath());
			out.write(bytesArch);
			out.close();
			archivoAdjunto = pdf;

		} catch (JRException e) {
			e.printStackTrace();
			throw new AdmonUsuariosException("Error al generar el archivo pdf de confirmacion");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AdmonUsuariosException("Error al generar el archivo pdf de confirmacion");
		}
		return archivoAdjunto;
	}

}
