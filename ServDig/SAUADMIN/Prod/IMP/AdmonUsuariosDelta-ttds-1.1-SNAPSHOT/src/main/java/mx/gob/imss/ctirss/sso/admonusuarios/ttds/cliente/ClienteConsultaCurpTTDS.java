package mx.gob.imss.ctirss.sso.admonusuarios.ttds.cliente;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import org.apache.axis.message.MessageElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tempuri.ConsultaCURPResponseConsultaCURPResult;
import org.tempuri.WsConsultaCURPLocatorTTD;
import org.tempuri.WsConsultaCURPSoapStubTTD;

import mx.gob.imss.ctirss.sso.admonusuarios.ttds.modelo.UsuarioTTD;

public class ClienteConsultaCurpTTDS {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteConsultaCurpTTDS.class);
	
	public UsuarioTTD invocarServicioConsultaCurpTTDS(String curp, String matricula) throws MalformedURLException, RemoteException {
		
		log.info("############ INVOCANDO SERVICIO CONSULTA CURP TTDS CON LA CURP [{}] ############", curp);
		WsConsultaCURPLocatorTTD wsConsultaCURPLocator = new WsConsultaCURPLocatorTTD();
		log.info("############ URL DEL SERVICIO [ " + wsConsultaCURPLocator.getwsConsultaCURPSoapAddressTTD() + " ] ############");
		WsConsultaCURPSoapStubTTD wsConsultaCURPSoapStub = new WsConsultaCURPSoapStubTTD(new URL(wsConsultaCURPLocator.getwsConsultaCURPSoapAddressTTD()), wsConsultaCURPLocator);
		
		ConsultaCURPResponseConsultaCURPResult consultaCURPResponseConsultaCURPResult = new ConsultaCURPResponseConsultaCURPResult();
		consultaCURPResponseConsultaCURPResult = wsConsultaCURPSoapStub.consultaCURP(curp, matricula);
		log.info("############ YA FUE INVOCADO EL SERVICIO CONSULTA CURP TTDS ############");
		
		UsuarioTTD usuarioTTD = null;
		
		if (consultaCURPResponseConsultaCURPResult != null) {
			MessageElement[] listMessageElement = consultaCURPResponseConsultaCURPResult.get_any();
			
			if (listMessageElement != null && listMessageElement.length > 0) {
				String respuesta = listMessageElement[0].toString();
				
				if (respuesta.indexOf("No se encontro el registro.") < 0) {
					usuarioTTD = new UsuarioTTD();
					
					usuarioTTD.setNss(respuesta.substring(respuesta.indexOf("<NSS>") + 5, respuesta.indexOf("</NSS>")));
					log.info("############ NSS [ " + usuarioTTD.getNss() + " ] ############");
					
					usuarioTTD.setCurp(respuesta.substring(respuesta.indexOf("<CURP>") + 6, respuesta.indexOf("</CURP>")));
					log.info("############ CURP [ " + usuarioTTD.getCurp() + " ] ############");
					
					usuarioTTD.setMatricula(respuesta.substring(respuesta.indexOf("<MATRICULA>") + 11, respuesta.indexOf("</MATRICULA>")));
					log.info("############ MATRICULA [ " + usuarioTTD.getMatricula() + " ] ############");
					
					usuarioTTD.setNombre(respuesta.substring(respuesta.indexOf("<NOMBRE>") + 8, respuesta.indexOf("</NOMBRE>")));
					log.info("############ NOMBRE [ " + usuarioTTD.getNombre() + " ] ############");
					
					usuarioTTD.setPuestoDesc(respuesta.substring(respuesta.indexOf("<DECRIPCIONPUESTO>") + 18, respuesta.indexOf("</DECRIPCIONPUESTO>")));
					log.info("############ DECRIPCIONPUESTO [ " + usuarioTTD.getPuestoDesc() + " ] ############");
					
					usuarioTTD.setDepartamentoDesc(respuesta.substring(respuesta.indexOf("<DESCRIPCIONDEPTO>") + 18,	respuesta.indexOf("</DESCRIPCIONDEPTO>")));
					log.info("############ DESCRIPCIONDEPTO [ " + usuarioTTD.getDepartamentoDesc() + " ] ############");

					usuarioTTD.setDelegacionCve(respuesta.substring(respuesta.indexOf("<DEL>") + 5, respuesta.indexOf("</DEL>")));
					log.info("############ DELEGACION [ " + usuarioTTD.getDelegacionCve() + " ] ############");
					
					usuarioTTD.setTipoContratacion(respuesta.substring(respuesta.indexOf("<EMP_KEYPRO>") + 12, respuesta.indexOf("</EMP_KEYPRO>")));
					log.info("############ EMP_KEYPRO [ " + usuarioTTD.getTipoContratacion() + " ] ############");
					
					usuarioTTD.setEstatus(respuesta.substring(respuesta.indexOf("<ESTATUS>") + 9, respuesta.indexOf("</ESTATUS>")));
					log.info("############ ESTATUS [ " + usuarioTTD.getEstatus() + " ] ############");
				}
			} else {
				log.info("############ LA CURP [{}] NO SE ENCUENTRA REGISTRADA EN TTDS  ##########", curp);
				usuarioTTD = null;
			}
		} else {
			log.info("############ LA CURP [{}] NO SE ENCUENTRA REGISTRADA EN TTDS  ##########", curp);
			usuarioTTD = null;
		}
		return usuarioTTD;
	}

}
