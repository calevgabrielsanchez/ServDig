package mx.gob.imss.ctirss.delta.util;

import java.lang.reflect.Field;



public class DeltaUtils {
	
	public Object getValorCampodeObjeto(Object obj, String sPropiedad) 
			throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException{
		if(!(obj != null && sPropiedad != null && !sPropiedad.equals("")))
			throw new IllegalArgumentException("El objeto o la propiedad (campo) estan vacias.");

		Object objRes 	= obj;
		Field fRes 		= null;

		String[] asObjetosPropiedad = sPropiedad.split("\\.");
		if(asObjetosPropiedad.length>1){
			for(int iCont= 0; iCont<asObjetosPropiedad.length-1; iCont++){
				Field f = objRes.getClass().getSuperclass().getDeclaredField(asObjetosPropiedad[iCont]);
				boolean bModificarAcceso = false;
				if(!f.isAccessible()){
					f.setAccessible(true);
					bModificarAcceso = true;
				}
				objRes = f.get(objRes);
				if(bModificarAcceso)
				    f.setAccessible(false);
			}//for
		}//if(asObjetosPropiedad.length
		fRes = objRes.getClass().getField(asObjetosPropiedad[asObjetosPropiedad.length-1]);
		objRes = fRes.get(objRes);
		return objRes;
	}//getCampodeObjeto		

}
