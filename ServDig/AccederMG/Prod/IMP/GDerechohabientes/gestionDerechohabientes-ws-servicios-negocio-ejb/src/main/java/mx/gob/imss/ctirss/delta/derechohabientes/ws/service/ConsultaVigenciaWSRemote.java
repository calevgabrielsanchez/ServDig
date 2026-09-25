/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.ws.service;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta1;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta2;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta3;

/**
 * @author JUAN MANUEL MARQUEZ
 *
 */
@Remote
public interface ConsultaVigenciaWSRemote {
	
	/**
	 * 
	 * @param nss
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	public Respuesta3 consultaVigencia3(String nss) throws DerechohabientesBusinessException;
	
	/**
	 * 
	 * @param nss
	 * @param UMF
	 * @param Delegacion
	 * @param CPID
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	public Respuesta2 consultaVigencia2(String nss,String umf,String delegacion, String cpid) throws DerechohabientesBusinessException;
	
	/**
	 * 
	 * @param nss
	 * @param UMF
	 * @param Delegacion
	 * @param CPID
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	public Respuesta1 consultaVigencia1(String nss,String umf,String delegacion, String cpid) throws DerechohabientesBusinessException;

}
