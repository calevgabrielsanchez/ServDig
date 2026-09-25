package sdroc_web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;

@ContextConfiguration("classpath:/spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CorreoTEST {

	@Autowired
	EmailServiceRemote emailServiceRemote;
	
	@Test
	public void metodo() {
		
		try {
			emailServiceRemote.enviaCorreoConDocumentoAdjunto("","","", "", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String cuerpoCorreo(){
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder contenido = new StringBuilder();
		contenido.append("Se remite Acuse de recibo, Registro de Obra de Construcción con número de registro de obra: ")
				.append("C0000234").append(", de fecha ").append(format.format(new Date()));

		return null;
	}

	/**
	 * @return the emailServiceRemote
	 */
	public EmailServiceRemote getEmailServiceRemote() {
		return emailServiceRemote;
	}

	/**
	 * @param emailServiceRemote the emailServiceRemote to set
	 */
	public void setEmailServiceRemote(EmailServiceRemote emailServiceRemote) {
		this.emailServiceRemote = emailServiceRemote;
	}
}
