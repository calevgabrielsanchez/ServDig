package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;


@Remote
public interface ArpBusinessRemote {
	
	byte[] getArpPersona(String folioSolicitud) throws Exception;
	byte[] getArpPersona(String folioSolicitud, Tramite tramite) throws Exception;
	Object getReporeteArpPersona(Solicitud solicitud) throws Exception;
	public Object getReporteArpPersona(Solicitud solicitud, Tramite tramite) throws Exception;
	
	byte[] getTipPersona(String folioSolicitud) throws Exception;
	public byte[] getTipPersona(String folioSolicitud, Tramite tramite) throws Exception;
	Object getReporteTipPersoa(Solicitud solicitud) throws Exception;
	public Object getReporteTipPersona(Solicitud solicitud, Tramite tramite) throws Exception;
	
	byte[] getArpPersonaMoralTest(String folioSolicitud) throws Exception;

	Map<String, Object> getArpModelPersona(String folioSolicitud) throws Exception;
	
	Map<String, Object> getTipModelPersona(String folioSolicitud) throws Exception;
	
	byte[] visualizacionPreviaAltaPatron(TramiteSujetoObligado tramiteSujetoObligado) throws Exception;
		
}
