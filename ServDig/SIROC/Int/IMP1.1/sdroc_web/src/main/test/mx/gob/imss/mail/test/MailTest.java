/**
 * 
 */
package mx.gob.imss.mail.test;

import java.io.File;

import javax.mail.MessagingException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import mx.gob.imss.csdiss.sdroc.model.CuerpoCorreoDTO;
import mx.gob.imss.csdiss.sdroc.service.MailerServices;

/**
 * @author daniel.hernandez
 *
 */
public class MailTest {

	public static void main(String[] args) {
		String[] listaCorreos = {"juan"};
		StringBuffer contenidoCorreo = new StringBuffer();
		contenidoCorreo.append("Este es un  correo de prueba :D");		
		File adjunto = new File("C:\\Users\\daniel.hernandez\\Desktop\\adjunto.txt");
		
		CuerpoCorreoDTO correo  =  new CuerpoCorreoDTO();
		//File adjunto = new File(nombreReporte);
		
		StringBuilder contenido = new StringBuilder();			
		contenido.append("contenido de ejemplo");
		
		String[] destinatarios = {"daniel.hernandezr@softtek.com","brian.hernandez@softtek.com", "frodriguez@softtek.com"};
		String remitente = "brian.hernandez@imss.gob.mx";
		String titulo = "Correo de Prueba para Registro de Obra";
		
		correo.setAdjunto(adjunto);
		correo.setContenido(contenido);
		correo.setDestinatario(destinatarios);
		correo.setRemitente(remitente);
		correo.setTitulo(titulo);
		
		try {
			enviarCorreoDos(correo);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void enviarCorreoDos(CuerpoCorreoDTO correo) throws MessagingException{
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContextTest.xml");
		MailerServices mailSenderService = (MailerServices) context.getBean("mailSenderService");
		
		mailSenderService.enviarCorreo(correo);
	}

	
	public static void enviaCorreoUno(CuerpoCorreoDTO correo) throws MessagingException{
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContextTest.xml");
		MailerServices mailSenderService = (MailerServices) context.getBean("mailSenderService");
		
		mailSenderService.enviarCorreo(correo);
//		job.enviarCorreo("brian.hernandez@imss.gob.mx", listaCorreos, "titulo de correo", contenidoCorreo , adjunto);
	}
}
