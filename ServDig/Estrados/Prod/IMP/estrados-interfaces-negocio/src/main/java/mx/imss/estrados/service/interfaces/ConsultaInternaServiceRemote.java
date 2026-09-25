package mx.imss.estrados.service.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.imss.estrados.dto.DocumentosAdjuntosDTO;
import mx.imss.estrados.dto.SsoVwUsuarioDTO;
import mx.imss.estrados.dto.StatusDTO;
import mx.imss.estrados.dto.TipodocumentoDTO;
import mx.imss.estrados.paginado.dto.PaginadoRequest;
import mx.imss.estrados.paginado.dto.PaginadoResponse;

@Remote
public interface ConsultaInternaServiceRemote {
	
	public PaginadoResponse consultaInternaPaginada(PaginadoRequest paginadoRequest);
	public List<TipodocumentoDTO> obtenerFiltroTipoDocumento(SsoVwUsuarioDTO ssoVwUsuarioDTO);
	public List<StatusDTO> obtenerFiltroStatus(SsoVwUsuarioDTO ssoVwUsuarioDTO);
	public DocumentosAdjuntosDTO obtenerDocumentoAdjunto(DocumentosAdjuntosDTO documentosAdjuntosDTO);
	Integer consultaInternaEjecuta(Integer idEstatus, Date fechaEjecuta, SsoVwUsuarioDTO ssoVwUsuarioDTO);
	Integer ejecutaTareaNotifica(Integer idEstatus, Date fechaRegistro);

}
