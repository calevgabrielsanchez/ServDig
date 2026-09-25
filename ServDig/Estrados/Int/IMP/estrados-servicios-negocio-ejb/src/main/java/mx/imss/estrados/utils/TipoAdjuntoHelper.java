package mx.imss.estrados.utils;

import mx.imss.estrados.dto.TipoAdjuntoDTO;
import mx.imss.estrados.entity.NeeCatTipoAdjunto;

public class TipoAdjuntoHelper {
	
	/**
	 * Metodo para settear la información de la entidad NeeCatTipoAdjunto a el objeto TipoAdjuntoDTO
	 * 
	 * @param NeeCatTipoAdjunto
	 * @return TipoAdjuntoDTO
	 */
	public TipoAdjuntoDTO setterTipoAdjuntoEntityToTipoAdjuntoDTO(NeeCatTipoAdjunto neeCatTipoAdjunto) {
		TipoAdjuntoDTO tipoAdjuntoDTO = new TipoAdjuntoDTO();
		
		tipoAdjuntoDTO.setCveTipoAdjunto(neeCatTipoAdjunto.getCveTipoAdjunto());
		tipoAdjuntoDTO.setDesTipoAdjunto(neeCatTipoAdjunto.getDesTipoAdjunto());
		
		return tipoAdjuntoDTO;
	}

}
