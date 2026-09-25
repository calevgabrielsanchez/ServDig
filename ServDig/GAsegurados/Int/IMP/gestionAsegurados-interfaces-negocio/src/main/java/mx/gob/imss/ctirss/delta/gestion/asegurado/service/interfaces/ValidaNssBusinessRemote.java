package mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces;

import javax.ejb.Remote;

@Remote
public interface ValidaNssBusinessRemote {

	/**
	 * Se verifica si el NSS debe ser consultado en Almacen.
	 * Los nuevos NSS se sincronizan a Almacen hasta la noche.
	 * 
	 * @param nss
	 * @return Indicador de validacion
	 */
	boolean aplicaValidacionNssAlmacen(String nss);
	
}
