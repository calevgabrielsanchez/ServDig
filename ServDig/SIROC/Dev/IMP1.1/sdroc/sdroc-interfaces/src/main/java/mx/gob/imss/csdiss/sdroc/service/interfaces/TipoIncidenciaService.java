package mx.gob.imss.csdiss.sdroc.service.interfaces;

import java.util.List;

import mx.gob.imss.csdiss.sdroc.dto.TipoIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;




/**
 * 
 * Interface que define los metodos del servicio que proporciona los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */

public interface TipoIncidenciaService{
	
	public List<TipoIncidenciaDTO> consultarTodosTiposIncidencias() throws BusinessException;	

}
