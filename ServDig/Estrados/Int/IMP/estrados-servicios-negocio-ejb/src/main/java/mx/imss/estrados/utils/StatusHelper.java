package mx.imss.estrados.utils;

import mx.imss.estrados.dto.StatusDTO;
import mx.imss.estrados.entity.NeeCatStatus;

public class StatusHelper {
	
	/**
	 * Metodo para settear la información de la entidad NeeCatStatus a el objeto StatusDTO
	 * 
	 * @param NeeCatStatus
	 * @return StatusDTO
	 */
	public StatusDTO setterStatusEntityToStatusDTO(NeeCatStatus neeCatStatus) {
		StatusDTO statusDTO = new StatusDTO();
		
		statusDTO.setCveStatus(neeCatStatus.getCveStatus());
		statusDTO.setDesEstatus(neeCatStatus.getDesEstatus());
		
		return statusDTO;
	}

}
