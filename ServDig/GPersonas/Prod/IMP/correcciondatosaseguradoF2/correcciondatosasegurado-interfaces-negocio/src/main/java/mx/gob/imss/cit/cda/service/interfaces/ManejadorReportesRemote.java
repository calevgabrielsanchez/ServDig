package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.TramitesReportes;



@Remote
public interface ManejadorReportesRemote {
	
	byte[] ejecutaReporte(Map<String, Object> parametros1,Map<String, Object> parametros2);
	byte[] ejecutaReporteCertificacion(Map<String, Object> parametros);
	byte[] generarReportePDF(String nombreReporte, Map<String, Integer> mapVariables, Map<Integer, Integer> mapOrigenes, String delegacion, String subdelegacion, String tipoVariable);
	byte[] generaReporteXLS(List<TramitesReportes> datos);
	
}
