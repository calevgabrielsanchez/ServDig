package mx.gob.imss.ctirss.delta.gestion.solicitud.service.global.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.global.model.SolicitudTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Local
public interface DeltaSolicitudConversorLocal {
	
	
	SolicitudTO transformarSolicitudAModeloGlobal(Solicitud deltaModel);
	
	String obtenerDomicilioFiscalCompleto(Solicitud solicitud);
	
	String obtenerDomicilioFiscalCompleto(Persona persona);
}
