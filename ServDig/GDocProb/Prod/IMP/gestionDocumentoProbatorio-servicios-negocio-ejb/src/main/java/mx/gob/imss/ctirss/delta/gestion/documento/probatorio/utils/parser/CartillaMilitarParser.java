package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CartillaMilitar;
import mx.gob.imss.ctirss.delta.persistence.DitCartillaMilitar;

public class CartillaMilitarParser {

	public static DitCartillaMilitar modelToPersist(CartillaMilitar entrada) {
		DitCartillaMilitar salida=null;
		if(entrada!=null){
			salida=new DitCartillaMilitar();
	
			salida.setNumMatricula(entrada.getNoMatricula());
		
		}
		return salida;
	}
	static public CartillaMilitar persistToModel(DitCartillaMilitar entrada){
		CartillaMilitar salida=null;
		if(entrada!=null){
			salida=new CartillaMilitar();
			salida.setIdDocumentoProbatorio(new Long(entrada.getCveIdDocumentoProbatorio()).intValue());
			salida.setNoMatricula(entrada.getNumMatricula());
	
		}
		return salida;
	}

}
