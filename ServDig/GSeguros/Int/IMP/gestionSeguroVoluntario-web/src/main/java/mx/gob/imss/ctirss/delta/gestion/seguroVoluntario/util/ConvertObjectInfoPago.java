package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util;

import com.google.gson.Gson;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.vo.InfoPagoObject;

public class ConvertObjectInfoPago {

	public String getJsonInfoPago(InfoPagoObject infoPagoObject){
		Gson gson = new Gson();

		return gson.toJson(infoPagoObject);
	}
	
}
