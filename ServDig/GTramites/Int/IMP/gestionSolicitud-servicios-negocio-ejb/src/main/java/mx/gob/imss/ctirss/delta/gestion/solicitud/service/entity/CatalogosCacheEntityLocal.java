package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.catalogos.cache.DicRemitenteCorreo;

@Local
public interface CatalogosCacheEntityLocal {
	
	/**
	 * Servicio que devuelve el objeto de base de datos del  DicRemitenteCorreo von el remitente que reibe
	 * como parametro
	 * @param remitente
	 * @return
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	DicRemitenteCorreo getRemitenteCorreo(String remitente) throws  Exception;
	
	/**
	 * Servicio que recupera el  remitente defualt que tiene el indicador en base de datos
	 * @return
	 * @throws Exception
	 */
	DicRemitenteCorreo getRemitenteCorreoDefault() throws  Exception;
}
