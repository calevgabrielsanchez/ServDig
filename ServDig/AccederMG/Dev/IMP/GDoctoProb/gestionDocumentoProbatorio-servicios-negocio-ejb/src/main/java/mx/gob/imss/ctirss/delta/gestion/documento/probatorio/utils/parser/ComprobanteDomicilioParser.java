package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ComprobanteDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DitComprobanteDomicilio;

public class ComprobanteDomicilioParser {

	public static DitComprobanteDomicilio modelToPersist(
			ComprobanteDomicilio entrada) {
		DitComprobanteDomicilio salida =null;
		if(entrada!=null){
			salida=new DitComprobanteDomicilio();
			salida.setRefFolio(entrada.getFolio());
		}
		return salida;
	}

	public static ComprobanteDomicilio persisToModel(
			DitComprobanteDomicilio entrada) {
		ComprobanteDomicilio salida=null;
		if(entrada!=null){
			salida=new ComprobanteDomicilio();
			salida.setFolio(entrada.getRefFolio());
		}
		return salida;
	}
	

}
