package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.vo.CorreoElectronicoVo;

@Component
public class EnvioEmailServiceImpl implements EnvioEmail {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(EnvioEmailServiceImpl.class);

    @Override
    public JavaMailSenderImpl getPropertiesMail(String rutaProperties) throws Exception {
        Properties prop = new Properties();
        JavaMailSenderImpl mail = new JavaMailSenderImpl();
        Properties envio = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream((rutaProperties != null || !rutaProperties.isEmpty()) ? rutaProperties : "configuracionEmail.properties");
            prop.load(input);

            LOGGER.info("las propiedades seteadas son use [" + prop.getProperty("USER_CORREO")
                    + "] pass [" + prop.getProperty("PASSWORD_CORREO") + "] autenticacion ["
                    + prop.getProperty("AUTENTICACION") + "] HOST [" + prop.getProperty("HOST_MEXICO") + "] puerto ["
                    + prop.getProperty("PUERTO") + "] protocolo ["
                    + prop.getProperty("PROTOCOLO") + "] ");

            Boolean autenticacion = new Boolean(prop.getProperty("AUTENTICACION"));
            if (autenticacion) {
                mail.setUsername(prop.getProperty("USER_CORREO"));
                mail.setPassword(prop.getProperty("PASSWORD_CORREO"));
                envio.setProperty("mail.smtps.auth", "true");
            } else {
                envio.setProperty("mail.smtps.auth", "false");
            }
            mail.setHost(prop.getProperty("HOST_MEXICO"));
            mail.setPort(new Integer(prop.getProperty("PUERTO")).intValue());
            mail.setProtocol(prop.getProperty("PROTOCOLO"));
            envio.setProperty("mail.transport.protocol", prop.getProperty("PROTOCOLO"));
            mail.setJavaMailProperties(envio);

        } catch (Exception e) {
            LOGGER.error("error al setear las propiedades");
            throw e;
        }
        return mail;

    }

    public String getDefaultMailSender() {
        Properties prop = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("configuracionEmail.properties");
            prop.load(input);
            return prop.getProperty("FROMIMSS_CORREO");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error al setear las propiedades");
        }

        return null;

    }

    @Async
    @Override
    public void enviarCorreo(CorreoElectronicoVo correoElectronicoVo, String rutaProperties) throws Exception {
        try {
            LOGGER.info("desde servicio: " + correoElectronicoVo.toString());
            LOGGER.info("rutaProperties : " + rutaProperties);

            JavaMailSenderImpl mail = this.getPropertiesMail(rutaProperties);
            MimeMessage message = mail.createMimeMessage();

            message.setFrom(new InternetAddress(this.getDefaultMailSender()));

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(correoElectronicoVo.getAsunto());
            helper.setText(correoElectronicoVo.getCuerpoCorreo(), correoElectronicoVo.isFormatoHMTL());
            helper.setTo(correoElectronicoVo.getDestinatario());

            if (correoElectronicoVo.getCorreoCopia() != null) {
                helper.setCc(correoElectronicoVo.getCorreoCopia());
            }

            ByteArrayResource byteAr;
            if (correoElectronicoVo.getAdjuntos() != null && !correoElectronicoVo.getAdjuntos().isEmpty()) {
                for (Map.Entry<String, byte[]> adjunto : correoElectronicoVo.getAdjuntos().entrySet()) {
                    byteAr = new ByteArrayResource(adjunto.getValue());
                    helper.addAttachment(adjunto.getKey(), byteAr);
                }
            }
            mail.send(message);
        } catch (Exception e) {
            LOGGER.error("error al enviar el corrreo", e);
        }
    }

}
