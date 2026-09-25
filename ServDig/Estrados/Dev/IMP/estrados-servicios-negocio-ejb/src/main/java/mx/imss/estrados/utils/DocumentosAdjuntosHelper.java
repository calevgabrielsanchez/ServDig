package mx.imss.estrados.utils;

import mx.imss.estrados.dto.DocumentosAdjuntosDTO;
import mx.imss.estrados.dto.NotificacionesDTO;
import mx.imss.estrados.dto.TipoAdjuntoDTO;
import mx.imss.estrados.entity.NeeDocumentosAdjuntos;

public class DocumentosAdjuntosHelper {
	
	/**
	 * Metodo para settear la información de la entidad NeeDocumentosAdjuntos a el objeto DocumentosAdjuntosDTO
	 * @param NeeDocumentosAdjuntos
	 * @return DocumentosAdjuntosDTO
	 */
	public DocumentosAdjuntosDTO setterDocumentosAdjuntosEntityToDocumentosAdjuntosDTO(NeeDocumentosAdjuntos neeDocumentosAdjuntos) {
		DocumentosAdjuntosDTO documentosAdjuntosDTO = new DocumentosAdjuntosDTO();
		NotificacionHelper notificacionHelper = new NotificacionHelper();
		NotificacionesDTO notificacionesDTO = new NotificacionesDTO();
		TipoAdjuntoHelper tipoAdjuntoHelper = new TipoAdjuntoHelper();
		TipoAdjuntoDTO tipoAdjuntoDTO = new TipoAdjuntoDTO();
		
		documentosAdjuntosDTO.setCveDoctoAdjunto(neeDocumentosAdjuntos.getCveDoctoAdjunto());
		
		notificacionesDTO = notificacionHelper.setterNotificacionesEntityToNotificacionesDTO(neeDocumentosAdjuntos.getNeeNotificaciones());
		documentosAdjuntosDTO.setNotificacionesDTO(notificacionesDTO);
		
		documentosAdjuntosDTO.setDesNumOficio(neeDocumentosAdjuntos.getDesNumOficio());
		documentosAdjuntosDTO.setDesNombreArchivo(neeDocumentosAdjuntos.getDesNombreArchivo());
		documentosAdjuntosDTO.setDesRefFilesystem(neeDocumentosAdjuntos.getDesRefFilesystem());
		
		tipoAdjuntoDTO= tipoAdjuntoHelper.setterTipoAdjuntoEntityToTipoAdjuntoDTO(neeDocumentosAdjuntos.getNeeCatTipoAdjunto());
		documentosAdjuntosDTO.setTipoAdjuntoDTO(tipoAdjuntoDTO);
		
		return documentosAdjuntosDTO;
	}

}
