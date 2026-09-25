package mx.gob.imss.ctirss.delta.cobranza.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;

@Remote
public interface PatronCobranzaServiceRemote {
	
	Patron getPatron(String regPat,String modalida);
	
	/**
	 * Servicio para validar si el regitro patronal
	 * tiene adeudos o no pago a tiempo.
	 * 
	 * @param regPatron
	 * @return	(TRUE si tiene adeudos, FALSE si no tiene adeudos)
	 */
	boolean tieneAdeudos(String regPatron);
	
}
