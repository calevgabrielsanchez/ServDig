/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.vo.CorreoElectronicoVo;


public interface EnvioEmail {

	abstract JavaMailSenderImpl getPropertiesMail(String rutaProperties) throws Exception;
	abstract String getDefaultMailSender();
	abstract void enviarCorreo(CorreoElectronicoVo correoElectronicoVo,String rutaProperties) throws Exception;
}