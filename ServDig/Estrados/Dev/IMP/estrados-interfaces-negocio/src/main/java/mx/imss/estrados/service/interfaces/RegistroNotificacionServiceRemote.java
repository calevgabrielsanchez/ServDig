package mx.imss.estrados.service.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.imss.estrados.dto.AreaRespNotifDTO;
import mx.imss.estrados.dto.DocumentosAdjuntosDTO;
import mx.imss.estrados.dto.NotificacionesDTO;
import mx.imss.estrados.dto.SsoVwUsuarioDTO;
import mx.imss.estrados.dto.TipodocumentoDTO;
import vo.InfoPatronSalida;

@Remote
public interface RegistroNotificacionServiceRemote  {
	
	@SuppressWarnings("rawtypes")
	public List<List> consultaDiasInhabiles();
	public List<AreaRespNotifDTO> recuperaAreasResponsables(String curp) throws RuntimeException;
	public InfoPatronSalida validaRegistroPatronal(String registroPatronal);
	public List<TipodocumentoDTO> recuperaTiposDocumento(int cveProceso);
	public NotificacionesDTO guardaParcialNotificacion(NotificacionesDTO notificacion);
	public DocumentosAdjuntosDTO guardarArchivo(DocumentosAdjuntosDTO archivo);
//	public List<DocumentosAdjuntosDTO> recuperaListaDocOtrosAdjuntos(NotificacionesDTO notificacion);
	public DocumentosAdjuntosDTO eliminarArchivAdjunto(DocumentosAdjuntosDTO documento);
	public Date agregaDias(Date fecha,int dias);
	public SsoVwUsuarioDTO recuperaHeader(String curp);
	public void eliminarNotificacion(long cveNotificaciones, SsoVwUsuarioDTO ssoVwUsuarioDTO);
	public DocumentosAdjuntosDTO validaNumeroOficio(DocumentosAdjuntosDTO adjuntosDTO);

}
