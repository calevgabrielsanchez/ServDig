package mx.gob.imss.vigenciaderechos;

import java.util.AbstractMap.SimpleEntry;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ComprobanteVigenciaDerechosDTO;

@Remote
public interface VigenciaDerechosWSClientRemote {

	/**
	 * Constancia de vigencia de grupo familiar por nss
	 * 
	 * @param nss
	 * @param cpId
	 * @return
	 */
	ComprobanteVigenciaDerechosDTO getInfo(String nss) throws DerechohabientesWebSserviceException ;
	
	
	/**
	 * Constancia de vigencia de grupo familiar por nss
	 * 
	 * @param nss
	 * @param cpId
	 * @return
	 */
	ComprobanteVigenciaDerechosDTO getInfo(String nss, String cpId) throws DerechohabientesWebSserviceException ;
	
	GrupoFamiliar getInfoAsegurado(String nss)  throws DerechohabientesWebSserviceException ;
	String getAgregadoMedico(String nss, Long idPersona) throws DerechohabientesWebSserviceException;
	
	
	SimpleEntry<Integer, String> validarConsistencia(String nss) throws DerechohabientesWebSserviceException ;
}
