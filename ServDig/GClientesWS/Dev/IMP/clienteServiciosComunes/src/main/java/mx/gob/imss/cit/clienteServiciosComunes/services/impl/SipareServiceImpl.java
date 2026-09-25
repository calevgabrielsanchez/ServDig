package mx.gob.imss.cit.clienteServiciosComunes.services.impl;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.cit.clienteServiciosComunes.helper.SipareHelper;
import mx.gob.imss.cit.clienteServiciosComunes.services.SipareService;
import mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCapturaServiceLocator;
import mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.GeneraLineaCapturaSoapBindingStub;
import mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.modelo.SipareRequest;
import mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.modelo.SipareResponse;

public class SipareServiceImpl implements SipareService {
	
	@Autowired
	private SipareHelper sipareHelper;
	
	private String urlSipareService;

	public SipareResponse generaLineaCaptura(SipareRequest sipareRequest) {
		SipareResponse sipareResponse = null;
		
		try {
			GeneraLineaCapturaServiceLocator generaLineaCapturaServiceLocator = new GeneraLineaCapturaServiceLocator();
			GeneraLineaCapturaSoapBindingStub generaLineaCapturaSoapBindingStub = new GeneraLineaCapturaSoapBindingStub(new URL(urlSipareService), generaLineaCapturaServiceLocator);
			System.out.println("Incocando Web Service SIPARE");
			sipareResponse = sipareHelper.parseRespuesta(generaLineaCapturaSoapBindingStub.getFormato5(sipareRequest.getLineaSua(), sipareRequest.getTipoPatron()));
		} catch (Exception ex) {
			System.out.println("Error Exception");
			ex.printStackTrace();			
		}
		return sipareResponse;
	}

	public String getUrlSipareService() {
		return urlSipareService;
	}

	public void setUrlSipareService(String urlSipareService) {
		this.urlSipareService = urlSipareService;
	}
	
	

}
