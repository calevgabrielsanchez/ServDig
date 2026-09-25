package mx.imss.estrados.utils;

import mx.imss.estrados.dto.ProcesoDTO;
import mx.imss.estrados.dto.TipodocumentoDTO;
import mx.imss.estrados.entity.NeeCatTipodocumento;

public class TipoDocumentoHelper {
	
	/**
	 * Metodo para settear la información de la entidad NeeCatTipodocumento a el objeto NeeCatTipodocumento
	 * 
	 * @param NeeCatTipodocumento
	 * @return NeeCatTipodocumento
	 */
	public TipodocumentoDTO setterTipoDocumentoEntityToTipoDocumentoDTO(NeeCatTipodocumento neeCatTipodocumento) {
		TipodocumentoDTO tipodocumentoDTO = new TipodocumentoDTO();
		ProcesoDTO procesoDTO = new ProcesoDTO();
		
		tipodocumentoDTO.setCveTipodocto(neeCatTipodocumento.getCveTipodocto());
		
		procesoDTO.setCveProceso(neeCatTipodocumento.getNeeCatProceso().getCveProceso());
		procesoDTO.setDesProceso(neeCatTipodocumento.getNeeCatProceso().getDesProceso());
		tipodocumentoDTO.setProcesoDTO(procesoDTO);
		
		tipodocumentoDTO.setDesTipodocumento(neeCatTipodocumento.getDesTipodocumento());
		
		return tipodocumentoDTO;
	}

}
