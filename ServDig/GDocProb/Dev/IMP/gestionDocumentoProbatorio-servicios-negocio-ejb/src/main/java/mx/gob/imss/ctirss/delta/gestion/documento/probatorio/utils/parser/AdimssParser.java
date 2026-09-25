package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Adimss;
import mx.gob.imss.ctirss.delta.persistence.DitAdimss;


public class AdimssParser {

	public static DitAdimss modelToPersist( Adimss entrada) {
		DitAdimss salida = null;
		
		if(entrada != null) {
			salida = new DitAdimss();
			salida.setRefFolio(entrada.getFolio());
			salida.setFecExpedicion(entrada.getFechaExpedicion());
		}
		
		return salida;
	}
	
	public static Adimss persistToModel(DitAdimss entrada) {
		Adimss salida = null;
		
		if(entrada != null) {
			salida =new Adimss();
			salida.setIdDocumentoProbatorio(new Long(entrada.getCveIdDocumentoProbatorio()).intValue());
			salida.setFechaExpedicion(entrada.getFecExpedicion());
			salida.setFolio(entrada.getRefFolio());
		}
		
		return salida;
	}
}
