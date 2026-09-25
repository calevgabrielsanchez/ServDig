package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;

@Local
public interface ReporteUtilityServiceLocal {

	byte[] getReporteTipoCobro(Map<String, Object> datosReporte, Patron patron);
	byte[] getReporteTipoCobroRCV(Map<String, Object> datosReporte, Patron patron);
}
