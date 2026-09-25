package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;


import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoTramite;



@Remote
public interface ReglasNegocioServiceRemote {
	/**
	 * Revisa las siguientes precondiciones:
	 * 
	 * * Derechohabiente Vigente
	 * 
	 * * Vigencia de la cabeza del grupo familiar
	 * 
	 * * Tipo de trámite Valido.
	 *  
	 * @throws DerechohabientesBusinessException 
	 */

	public Boolean revisaPreCondiciones(GrupoFamiliar derechohabiente, TipoTramite tipoTramite) throws DerechohabientesBusinessException;
	
	/**
	 * Regla de negocio RNGD0001
	 * Los tipos de derechohabientes son:
	 *	Asegurado
	 *	Pensionado
	 *	Beneficiario
	 * @return
	 */
	public Boolean RNGD0001_validaTipoDerechohabiente(GrupoFamiliar derechohabiente) throws DerechohabientesBusinessException;

		
	
}