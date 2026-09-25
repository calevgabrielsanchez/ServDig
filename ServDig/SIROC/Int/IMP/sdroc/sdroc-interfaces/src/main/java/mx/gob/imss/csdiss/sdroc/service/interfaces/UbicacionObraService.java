package mx.gob.imss.csdiss.sdroc.service.interfaces;

import mx.gob.imss.csdiss.sdroc.exception.BusinessException;




/**
 * 
 * Interface que define los metodos del servicio que proporciona los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */

public interface UbicacionObraService{
		
	boolean isValidoCpUbicacionObra(String cveRp,String cveCodigoPostal) throws BusinessException;
	boolean validCircunscripcion(String codigoPostal, Long idDelegacion, Long idSubdelegacion) throws BusinessException;
	
}
