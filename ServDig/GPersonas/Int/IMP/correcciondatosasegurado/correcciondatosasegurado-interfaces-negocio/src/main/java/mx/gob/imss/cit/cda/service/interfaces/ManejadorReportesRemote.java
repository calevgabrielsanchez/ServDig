package mx.gob.imss.cit.cda.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

@Remote
public interface ManejadorReportesRemote {
	
	byte[] ejecutaReporte(Map<String, Object> parametros1,Map<String, Object> parametros2);
	byte[] ejecutaReporteCertificacion(Map<String, Object> parametros);
	
}
