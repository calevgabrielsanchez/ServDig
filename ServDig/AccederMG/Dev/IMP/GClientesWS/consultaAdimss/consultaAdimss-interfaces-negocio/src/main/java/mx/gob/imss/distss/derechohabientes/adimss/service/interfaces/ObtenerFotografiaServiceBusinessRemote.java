package mx.gob.imss.distss.derechohabientes.adimss.service.interfaces;

import javax.ejb.Remote;



@Remote
public interface ObtenerFotografiaServiceBusinessRemote {
	
	Object obtenerFotografiaAsegurado(String nss, Integer calidad) throws Exception;
		
	Object obtenerFotografiaDerechohabiente(String nss,Integer calidad,String nombre, String apPaterno,String apMaterno) throws Exception;
	
}
