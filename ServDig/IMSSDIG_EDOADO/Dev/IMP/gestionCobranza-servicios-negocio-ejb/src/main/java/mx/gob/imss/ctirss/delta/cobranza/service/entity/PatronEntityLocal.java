package mx.gob.imss.ctirss.delta.cobranza.service.entity;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;

@Local
public interface PatronEntityLocal {
	
	List<Patron> findRegPatManMapping(String regPat,String modalidad);
	Patron getPatron(String regPat, String modalida);
	Map<String,String> findAsociados(String regPat, String modalidad);

	int obtenerTotalAdeudosPorRegistroPatronal(String regPatron);
	
}
