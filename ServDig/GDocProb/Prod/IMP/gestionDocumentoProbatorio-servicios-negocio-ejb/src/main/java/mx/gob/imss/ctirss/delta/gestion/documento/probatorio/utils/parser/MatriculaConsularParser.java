package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.MatriculaConsular;
import mx.gob.imss.ctirss.delta.persistence.DitMatriculaConsular;


public class MatriculaConsularParser {

	public static DitMatriculaConsular modelToPersist( MatriculaConsular entrada) {
		DitMatriculaConsular salida = null;
		
		if(entrada != null) {
			salida = new DitMatriculaConsular();
			salida.setAutoridadEmiteMat(entrada.getAutoridadEmiteMat());
			salida.setCalidadMigratoria(entrada.getCalidadMigratoria());
			salida.setFecExpedicion(entrada.getFechaExpedicion());
			salida.setFecVencimiento(entrada.getFechaVencimiento());
			salida.setNumeroDoc(entrada.getNumeroDoc());
		}
		
		return salida;
	}
	
	public static MatriculaConsular persistToModel(DitMatriculaConsular entrada) {
		MatriculaConsular salida = null;
		
		if(entrada != null) {
			salida =new MatriculaConsular();
			salida.setIdDocumentoProbatorio(new Long(entrada.getCveIdDocumentoProbatorio()).intValue());
			salida.setAutoridadEmiteMat(entrada.getAutoridadEmiteMat());
			salida.setCalidadMigratoria(entrada.getCalidadMigratoria());
			salida.setFechaExpedicion(entrada.getFecExpedicion());
			salida.setFechaVencimiento(entrada.getFecVencimiento());
			salida.setNumeroDoc(entrada.getNumeroDoc());
		}
		
		return salida;
	}
}
