package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;

@Local
public interface ReportesCobranzaServiceLocal {

	Map<String,Object> getCreditos(Patron patron);
	Map<String, Object> getCreditosRcv(Patron patron);
}
