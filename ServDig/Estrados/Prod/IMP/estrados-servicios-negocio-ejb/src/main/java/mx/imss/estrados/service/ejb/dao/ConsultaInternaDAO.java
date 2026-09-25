package mx.imss.estrados.service.ejb.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.imss.estrados.dto.SsoVwUsuarioDTO;
import mx.imss.estrados.entity.NeeCatStatus;
import mx.imss.estrados.entity.NeeCatTipodocumento;
import mx.imss.estrados.entity.NeeDocumentosAdjuntos;
import mx.imss.estrados.entity.NeeNotificaciones;
import mx.imss.estrados.entity.SsoVwUsuario;
import mx.imss.estrados.paginado.dto.PaginadoRequest;

@Local
public interface ConsultaInternaDAO {
	
	public Integer contarTotalRegistros();
	public Integer contarRegistrosFiltrados(PaginadoRequest paginadoRequest);
	public List<NeeNotificaciones> filtrar(PaginadoRequest paginadoRequest);
	public List<NeeCatTipodocumento> obtenerCatalogoTipoDocumento();
	public List<NeeCatStatus> obtenerCatalogoStatus();
	public NeeDocumentosAdjuntos obtenerDocumentoAdjunto(NeeDocumentosAdjuntos neeDocumentosAdjuntos);
	public SsoVwUsuario obtenerAreaNormativa(String cveUsuario);
	public Integer contarRegistrosFiltradosManual(PaginadoRequest paginadoRequest);
	Integer contarRegistrosFiltrados(Integer idEstatus, Date fechaEjecuta, SsoVwUsuarioDTO ssoVwUsuarioDTO);
	
}
