package mx.gob.imss.ctirss.sso.admonusuarios.siap.cliente;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import org.apache.axis.message.MessageElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tempuri.ConsultaCURPResponseConsultaCURPResult;
import org.tempuri.WsConsultaCURPLocator;
import org.tempuri.WsConsultaCURPSoapStub;

import mx.gob.imss.ctirss.sso.admonusuarios.siap.modelo.UsuarioNominaResponse;

public class ClienteConsultaCurpSiap {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteConsultaCurpSiap.class);
	
	public UsuarioNominaResponse invocarServicioConsultaCurpSiap(String curp, String matricula) throws MalformedURLException, RemoteException {
		
		log.info("############ INVOCANDO SERVICIO CONSULTA CURP SIAP CON LA CURP [{}] ############", curp);
		WsConsultaCURPLocator wsConsultaCURPLocator = new WsConsultaCURPLocator();
		log.info("############ URL DEL SERVICIO [ " + wsConsultaCURPLocator.getwsConsultaCURPSoapAddress() + " ] ############");
		WsConsultaCURPSoapStub wsConsultaCURPSoapStub = new WsConsultaCURPSoapStub(new URL(wsConsultaCURPLocator.getwsConsultaCURPSoapAddress()), wsConsultaCURPLocator);
		
		ConsultaCURPResponseConsultaCURPResult consultaCURPResponseConsultaCURPResult = new ConsultaCURPResponseConsultaCURPResult();
		consultaCURPResponseConsultaCURPResult = wsConsultaCURPSoapStub.consultaCURP(curp, matricula);
		log.info("############ YA FUE INVOCADO EL SERVICIO CONSULTA CURP SIAP ############");
		
		UsuarioNominaResponse usuarioNominaResponse = null;
		
		if (consultaCURPResponseConsultaCURPResult != null) {
			MessageElement[] listMessageElement = consultaCURPResponseConsultaCURPResult.get_any();
			
			if (listMessageElement != null && listMessageElement.length > 0) {
				String respuesta = listMessageElement[0].toString();
				
				if (respuesta.indexOf("No se encontro el registro.") < 0) {
					usuarioNominaResponse = new UsuarioNominaResponse();
					
					usuarioNominaResponse.setNss(respuesta.substring(respuesta.indexOf("<NSS>") + 5, respuesta.indexOf("</NSS>")));
					log.info("############ NSS [ " + usuarioNominaResponse.getNss() + " ] ############");
					
					usuarioNominaResponse.setCurp(respuesta.substring(respuesta.indexOf("<CURP>") + 6, respuesta.indexOf("</CURP>")));
					log.info("############ CURP [ " + usuarioNominaResponse.getCurp() + " ] ############");
					
					usuarioNominaResponse.setMatricula(respuesta.substring(respuesta.indexOf("<MATRICULA>") + 11, respuesta.indexOf("</MATRICULA>")));
					log.info("############ MATRICULA [ " + usuarioNominaResponse.getMatricula() + " ] ############");
					
					usuarioNominaResponse.setNombre(respuesta.substring(respuesta.indexOf("<NOMBRE>") + 8, respuesta.indexOf("</NOMBRE>")));
					log.info("############ NOMBRE [ " + usuarioNominaResponse.getNombre() + " ] ############");
					
					usuarioNominaResponse.setPuestoDesc(respuesta.substring(respuesta.indexOf("<DECRIPCIONPUESTO>") + 18, respuesta.indexOf("</DECRIPCIONPUESTO>")));
					log.info("############ DECRIPCIONPUESTO [ " + usuarioNominaResponse.getPuestoDesc() + " ] ############");
					
					usuarioNominaResponse.setDepartamentoDesc(respuesta.substring(respuesta.indexOf("<DESCRIPCIONDEPTO>") + 18,	respuesta.indexOf("</DESCRIPCIONDEPTO>")));
					log.info("############ DESCRIPCIONDEPTO [ " + usuarioNominaResponse.getDepartamentoDesc() + " ] ############");

					usuarioNominaResponse.setDelegacionCve(respuesta.substring(respuesta.indexOf("<DEL>") + 5, respuesta.indexOf("</DEL>")));
					log.info("############ DELEGACION [ " + usuarioNominaResponse.getDelegacionCve() + " ] ############");
					
					usuarioNominaResponse.setTipoContratacion(respuesta.substring(respuesta.indexOf("<EMP_KEYPRO>") + 12, respuesta.indexOf("</EMP_KEYPRO>")));
					log.info("############ EMP_KEYPRO [ " + usuarioNominaResponse.getTipoContratacion() + " ] ############");
					
					usuarioNominaResponse.setEstatus(respuesta.substring(respuesta.indexOf("<ESTATUS>") + 9, respuesta.indexOf("</ESTATUS>")));
					log.info("############ ESTATUS [ " + usuarioNominaResponse.getEstatus() + " ] ############");
				}
			} else {
				log.info("############ LA CURP [{}] NO SE ENCUENTRA REGISTRADA EN SIAP  ##########", curp);
				usuarioNominaResponse = null;
			}
		} else {
			log.info("############ LA CURP [{}] NO SE ENCUENTRA REGISTRADA EN SIAP  ##########", curp);
			usuarioNominaResponse = null;
		}
		return usuarioNominaResponse;
	}

}
