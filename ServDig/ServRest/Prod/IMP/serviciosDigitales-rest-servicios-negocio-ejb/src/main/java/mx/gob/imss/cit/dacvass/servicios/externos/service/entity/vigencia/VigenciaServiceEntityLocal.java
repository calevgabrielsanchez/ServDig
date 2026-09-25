package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.vigencia;

import javax.ejb.Local;

import mx.gob.imss.cit.dacvass.servicios.externos.persistence.vigencia.MgtInfincasegvig;

@Local
public interface VigenciaServiceEntityLocal {
	
	/**
	 * Metodo para consultar la vigencia de un asegurado en BDTU
	 * @param nss
	 * @return
	 * @throws Exception
	 */
	MgtInfincasegvig getMgtInfincasegvig(String nss) throws Exception;

}
