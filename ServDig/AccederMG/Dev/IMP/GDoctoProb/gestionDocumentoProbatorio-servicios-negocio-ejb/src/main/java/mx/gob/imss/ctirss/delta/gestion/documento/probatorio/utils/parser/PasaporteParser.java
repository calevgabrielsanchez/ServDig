package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Pasaporte;
import mx.gob.imss.ctirss.delta.persistence.DitPasaporte;

public class PasaporteParser {

	public static DitPasaporte modelToPersist(Pasaporte entrada) {
		DitPasaporte salida=null;
		if(entrada!=null){
			salida=new DitPasaporte();
			salida.setFecCaducidad(entrada.getFechaCaducidad());
			salida.setNumPasaporte(entrada.getNoPasaporte());
		}
		return salida;
	}
	public static Pasaporte persisToModel(DitPasaporte entrada){
		Pasaporte salida=null;
		if(entrada!=null){
			salida = new Pasaporte();
			
			salida.setIdDocumentoProbatorio(new Integer(""+entrada.getCveIdDocumentoProbatorio()));
			salida.setFechaCaducidad(entrada.getFecCaducidad());
			salida.setNoPasaporte(entrada.getNumPasaporte());
			
		}
		return salida;
	}

}
