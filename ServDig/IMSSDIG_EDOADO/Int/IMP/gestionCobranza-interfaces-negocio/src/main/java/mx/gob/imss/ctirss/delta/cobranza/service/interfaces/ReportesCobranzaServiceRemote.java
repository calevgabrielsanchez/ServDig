package mx.gob.imss.ctirss.delta.cobranza.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;

@Remote
public interface ReportesCobranzaServiceRemote {

	byte[] getReporteTipoCobro(String nrp) throws EstadoAdeudoException;
	byte[] getReporteTipoCobroRcv(String nrp) throws EstadoAdeudoException;
	Map<String , Object> getDatosReporteEstadoCuentaTrabajoAdeudoTipoCobroRCV(Patron patron) throws EstadoAdeudoException;
	Map<String , Object> getDatosReporteEstadoCuentaTrabajoAdeudoSituacionCobroRCV(Patron patron) throws EstadoAdeudoException;
	Map<String , Object> getDatosReporteEstadoCuentaTrabajoAdeudoTipoCobro(Patron patron) throws EstadoAdeudoException;
	Map<String , Object> getDatosReporteEstadoCuentaTrabajoAdeudoSituacionCobro(Patron patron) throws EstadoAdeudoException;
}
