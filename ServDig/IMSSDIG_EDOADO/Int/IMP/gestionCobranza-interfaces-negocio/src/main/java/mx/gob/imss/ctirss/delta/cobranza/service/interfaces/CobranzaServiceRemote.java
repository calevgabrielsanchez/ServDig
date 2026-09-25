package mx.gob.imss.ctirss.delta.cobranza.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Factor;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;
import mx.gob.imss.ctirss.delta.cobranza.modelo.ResumenEdoAdeudo;

@Remote
public interface CobranzaServiceRemote {

	ResumenEdoAdeudo getAdeudo(String nrp) throws EstadoAdeudoException;
	Map<String, Object> getTotalesCreditosImss(Patron patron);
	Map<String, Object> getTotalesCreditosRCVImss(Patron patron);
	Factor getFactorByPeriodo(String periodo);
}
