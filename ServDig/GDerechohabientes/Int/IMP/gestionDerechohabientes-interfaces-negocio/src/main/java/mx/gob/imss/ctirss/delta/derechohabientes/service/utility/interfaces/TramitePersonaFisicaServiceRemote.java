package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Tramite;



/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */

@Remote
public interface TramitePersonaFisicaServiceRemote {
	
	/**
	 * Inserta la información referente a un trámite
	 * @param tramite
	 */
	public void insertTramite(Tramite tramite);
	
	/**
	 * Regresa la descripci\u00F3n de un tipo de tr\u00E1mite.
	 * @param tramiteId
	 * @return String
	 * @throws Exception 
	 * @throws DerechohabientesBusinessException 
	 */
	public String getDescripcionTipoTramite(Long tramiteId) throws DerechohabientesBusinessException, Exception;

	/**
	 * Regresa la descripci�n del tipo de tramite desde el diccionario.
	 * @param idTramite
	 * @return String
	 * @throws Exception 
	 */
	public String getDescripcionTipoTramiteFromDic(Long idTipoTramite) throws Exception;
	
}
