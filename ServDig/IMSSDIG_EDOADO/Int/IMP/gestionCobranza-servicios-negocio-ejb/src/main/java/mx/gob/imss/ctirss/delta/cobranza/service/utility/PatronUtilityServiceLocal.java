package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.DCopPatrone;

@Local
public interface PatronUtilityServiceLocal {

	Patron convertEntityToModel(DCopPatrone dPatron); 
}
