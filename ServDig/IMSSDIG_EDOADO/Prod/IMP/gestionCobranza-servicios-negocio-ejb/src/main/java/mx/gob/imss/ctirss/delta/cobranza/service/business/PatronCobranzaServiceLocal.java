package mx.gob.imss.ctirss.delta.cobranza.service.business;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;

@Local
public interface PatronCobranzaServiceLocal {
	Patron getPatron(String regPat, String modalidad);
}
