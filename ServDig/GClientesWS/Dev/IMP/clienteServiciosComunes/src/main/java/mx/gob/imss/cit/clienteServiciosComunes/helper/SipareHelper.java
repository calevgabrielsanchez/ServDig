package mx.gob.imss.cit.clienteServiciosComunes.helper;

import mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.bean.Formato5;
import mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.modelo.SipareResponse;

import org.springframework.stereotype.Component;

@Component
public class SipareHelper {
	
	public SipareResponse parseRespuesta(Formato5 formato5) {
		SipareResponse sipareResponse = new SipareResponse();
		
		sipareResponse.setListExcepciones(formato5.getExcepciones());
		sipareResponse.setLineaCaptura(formato5.getLineaCaptura());
		sipareResponse.setPdf(formato5.getPdf());
		
		return sipareResponse;
	}
	
}