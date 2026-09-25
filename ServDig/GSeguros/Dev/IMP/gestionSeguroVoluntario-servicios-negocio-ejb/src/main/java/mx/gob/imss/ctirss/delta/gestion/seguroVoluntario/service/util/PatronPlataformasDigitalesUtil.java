package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import mx.gob.imss.ctirss.delta.model.gestion.seguro.PatronPlataformasDigitales;
import mx.gob.imss.ctirss.delta.persistence.PptPatronPlataforma;

public class PatronPlataformasDigitalesUtil {

	
	public static PatronPlataformasDigitales transformaPatronPlataformaDigToModel(PptPatronPlataforma patronPlataformaBD){
		
		PatronPlataformasDigitales patronPlataformasDigitales = new PatronPlataformasDigitales();
		patronPlataformasDigitales.setCveModalidad(patronPlataformaBD.getCveModalidad());
		patronPlataformasDigitales.setCveRegPatron(patronPlataformaBD.getCveRegPatron());
		patronPlataformasDigitales.setNumDigVer(patronPlataformaBD.getNumDigVer());
		patronPlataformasDigitales.setFecAlta(patronPlataformaBD.getFecAlta());
		patronPlataformasDigitales.setFecBaja(patronPlataformaBD.getFecBaja());
		patronPlataformasDigitales.setFecRegistroAlta(patronPlataformaBD.getFecRegistroAlta());
		
		return patronPlataformasDigitales;
	}
	
}
