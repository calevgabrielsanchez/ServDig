package mx.gob.imss.csdiss.sdroc.service.interfaces;

import java.util.List;

import mx.gob.imss.csdiss.sdroc.dto.SubDelegacionDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;




/**
 * 
 * Interface que define los metodos del servicio que proporciona los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */

public interface SubdelegacionService{
	
	public SubDelegacionDTO consultarSubdelegacionPorCveCodigo(Long cveCodigo) throws BusinessException;
	
	public List<SubDelegacionDTO> consultarTodasSubdelegaciones() throws BusinessException;	
	
	public List<SubDelegacionDTO> consultarSubdelegacionesImssByCp(String cveCodigoPostal) throws BusinessException;
}
