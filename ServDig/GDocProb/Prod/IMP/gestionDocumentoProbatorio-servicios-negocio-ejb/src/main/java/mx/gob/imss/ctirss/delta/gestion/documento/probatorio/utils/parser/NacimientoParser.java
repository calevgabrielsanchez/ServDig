package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;


import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;

import mx.gob.imss.ctirss.delta.persistence.DitNacimiento;

public class NacimientoParser {

	public static DitNacimiento modelToPersist(Nacimiento entrada) {
		DitNacimiento salida=null;
		if(entrada!=null){
			salida=new DitNacimiento();
			salida.setCveCrip(entrada.getCrip());

			if(entrada.getTomo()==null){
				entrada.setTomo("null");
			}
			//salida.setRefNumTomo(entrada.getTomo());
			
			if(entrada.getAnio()==null){
				entrada.setAnio(1900);
			}
			salida.setNumAnio(entrada.getAnio().intValue());
		}
		return salida;
	}
	
	public static Nacimiento persistToModel(DitNacimiento entrada) {
		Nacimiento salida = null;
		
		if(entrada!= null) {
			Acta acta=ActaParser.persistToModel(entrada.getDitActa());
			salida = new Nacimiento();
			if(acta!=null){
				salida.setFechaSuceso(acta.getFechaSuceso());
				salida.setNoActa(acta.getNoActa());
				salida.setNoFoja(acta.getNoFoja());
				salida.setNoJuzgado(acta.getNoJuzgado());
				salida.setNoLibro(acta.getNoLibro());
				salida.setMunicipio(acta.getMunicipio());
				salida.setTomo(acta.getTomo());
			}
			
			salida.setIdDocumentoProbatorio(new Integer(""+entrada.getCveIdDocumentoProbatorio()));
			salida.setCrip(entrada.getCveCrip());
			salida.setAnio(entrada.getNumAnio());
			
		}
		return salida;
	}

}
