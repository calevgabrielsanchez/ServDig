package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;


public class CodigoPostalParser {
	public static CodigoPostal persistToModel(DgCodigosPostale entrada) {
		CodigoPostal salida=null;
		if(entrada!=null){
			salida=new CodigoPostal();
			salida.setCodigoPostal(entrada.getId().getCodigo());
		}
		
		return salida;
	}


	

}
