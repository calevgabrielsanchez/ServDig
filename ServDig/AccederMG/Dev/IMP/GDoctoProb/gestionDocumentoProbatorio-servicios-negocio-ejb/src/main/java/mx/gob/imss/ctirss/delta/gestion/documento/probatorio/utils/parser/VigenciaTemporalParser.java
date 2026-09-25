package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.VigenciaTemporal;
import mx.gob.imss.ctirss.delta.persistence.DitVigenciaTemporal;

public class VigenciaTemporalParser {

	public static DitVigenciaTemporal modelToPersist(VigenciaTemporal entrada) {
		DitVigenciaTemporal salida= null;
		if(entrada!=null){
			salida=new DitVigenciaTemporal();
			salida.setRefFolio(entrada.getNoFolio());
			
		}
		return salida;
	}

	public static VigenciaTemporal persisToModel(
			DitVigenciaTemporal entrada) {
		VigenciaTemporal salida=null;
		if(entrada!=null){
			salida=new VigenciaTemporal();
			salida.setIdDocumentoProbatorio(new Integer(""+entrada.getCveIdDocumentoProbatorio()));
			salida.setNoFolio(entrada.getRefFolio());
		}
		return salida;
	}

}
