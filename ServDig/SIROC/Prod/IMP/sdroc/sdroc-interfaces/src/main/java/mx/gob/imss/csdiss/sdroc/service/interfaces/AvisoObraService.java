package mx.gob.imss.csdiss.sdroc.service.interfaces;

import java.util.List;

import mx.gob.imss.csdiss.sdroc.dto.AvisoObraDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;




/**
 * 
 * Interface que define los metodos del servicio que proporciona los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */

public interface AvisoObraService{
		
	public AvisoObraDTO consultarAvisoObraPorCveAvisoObra(String cveAvisoObra) throws BusinessException;
	
	public AvisoObraDTO consultarAvisoObraPorCveAvisoObra(String cveAvisoObra, String rfc, String registroPatronal) throws BusinessException;
	
	public List<AvisoObraDTO> consultarAvisosObraPorCveRfc(String cveRfc) throws BusinessException;
		
	public AvisoObraDTO insertarAvisoObra(AvisoObraDTO avisoObraDTO) throws BusinessException;
	
	public void actualizarAvisoObra(Long cveAvisoObra) throws BusinessException;
}
