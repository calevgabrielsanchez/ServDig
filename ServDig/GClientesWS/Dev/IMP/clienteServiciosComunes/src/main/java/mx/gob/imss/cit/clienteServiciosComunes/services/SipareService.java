package mx.gob.imss.cit.clienteServiciosComunes.services;

import mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.modelo.SipareRequest;
import mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.modelo.SipareResponse;

public interface SipareService {
	
	SipareResponse generaLineaCaptura(SipareRequest sipareRequest);

}
