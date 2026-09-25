package mx.gob.imss.csdiss.sdroc.service.interfaces;

import mx.gob.imss.csdiss.sdroc.dto.ReporteBimestralObraDTO;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;

/**
 * 
 * Interface que define los metodos del servicio que proporciona los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */

public interface ReporteBimestralService{
	
	public ReporteBimestralObraDTO consultarUltimoReporteBimestralPorCveInformacionObra(Long cveInformacionObra) throws BusinessException;
	
}
