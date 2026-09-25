package mx.imss.estrados.service.ejb.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.imss.estrados.commons.Constantes;
import mx.imss.estrados.cron.EjecutaTareasCronServiceRemote;
import mx.imss.estrados.dto.DocumentosAdjuntosDTO;
import mx.imss.estrados.dto.NotificacionesDTO;
import mx.imss.estrados.dto.SsoVwUsuarioDTO;
import mx.imss.estrados.dto.StatusDTO;
import mx.imss.estrados.dto.TipodocumentoDTO;
import mx.imss.estrados.entity.NeeCatStatus;
import mx.imss.estrados.entity.NeeCatTipodocumento;
import mx.imss.estrados.entity.NeeDocumentosAdjuntos;
import mx.imss.estrados.entity.NeeNotificaciones;
import mx.imss.estrados.entity.SsoVwUsuario;
import mx.imss.estrados.paginado.dto.PaginadoRequest;
import mx.imss.estrados.paginado.dto.PaginadoResponse;
import mx.imss.estrados.service.ejb.dao.ConsultaInternaDAO;
import mx.imss.estrados.service.ejb.dao.GenericDAO;
import mx.imss.estrados.service.interfaces.ConsultaInternaServiceRemote;
import mx.imss.estrados.utils.DocumentosAdjuntosHelper;
import mx.imss.estrados.utils.NotificacionHelper;
import mx.imss.estrados.utils.SsoVwUsuarioHelper;
import mx.imss.estrados.utils.StatusHelper;
import mx.imss.estrados.utils.TipoDocumentoHelper;

import org.apache.log4j.Logger;

@Stateless(name = "consultaInternaServiceBean", mappedName = "consultaInternaServiceBean")
public class ConsultaInternaServiceBean implements ConsultaInternaServiceRemote {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(ConsultaExternaServiceBean.class);
	
	@EJB
	ConsultaInternaDAO consultaInternaDAO;
	
	@EJB
	GenericDAO<NeeCatTipodocumento> catTipoDocumentoDAO;
	
	@EJB
	GenericDAO<NeeCatStatus> CatStatusDAO;
	
	@EJB
	EjecutaTareasCronServiceRemote ejecutaTareaNotificacionBusiness;
	
	/**
	 * Metodo para obtener la lista paginada de las notificaciones para los internos del IMSS
	 * @param PaginadoRequest
	 * @return PaginadoResponse
	 */
	@Override
	public PaginadoResponse consultaInternaPaginada(PaginadoRequest paginadoRequest) {
		List<NeeNotificaciones> listNeeNotificaciones;
		List<NotificacionesDTO> listNotificacionesDTOs = new ArrayList<NotificacionesDTO>();
		NotificacionHelper notificacionHelper = new NotificacionHelper();
		SsoVwUsuarioHelper ssoVwUsuarioHelper = new SsoVwUsuarioHelper();
		
		//Integer totalRegistros = consultaInternaDAO.contarTotalRegistros();
		Integer totalRegistrosParaMostrar = consultaInternaDAO.contarRegistrosFiltrados(paginadoRequest);
		listNeeNotificaciones = consultaInternaDAO.filtrar(paginadoRequest);
		
		for (NeeNotificaciones neeNotificaciones : listNeeNotificaciones) {
			NotificacionesDTO notificacionesDTO = new NotificacionesDTO();
			SsoVwUsuario ssoVwUsuario = new SsoVwUsuario();
			SsoVwUsuarioDTO ssoVwUsuarioDTO = new SsoVwUsuarioDTO();
			notificacionesDTO = notificacionHelper.setterNotificacionesEntityToNotificacionesDTO(neeNotificaciones);
			
			ssoVwUsuario = consultaInternaDAO.obtenerAreaNormativa(neeNotificaciones.getCveUsuario());
			ssoVwUsuarioDTO = ssoVwUsuarioHelper.setterSsoVwUsuarioEntityToSsoVwUsuarioDTO(ssoVwUsuario);
			notificacionesDTO.setSsoVwUsuarioDTO(ssoVwUsuarioDTO);
			listNotificacionesDTOs.add(notificacionesDTO);
		}
		
		PaginadoResponse paginadoResponse = new PaginadoResponse();
		paginadoResponse.setAaData(listNotificacionesDTOs);
		//paginadoResponse.setiTotalRecords(null);//(totalRegistros);
		paginadoResponse.setiTotalDisplayRecords(totalRegistrosParaMostrar);
		paginadoResponse.setsEcho(paginadoRequest.getEcho());
		
		return paginadoResponse;
	}

	/**
	 * Metodo para obtener los distintos tipos de documentos de las notificaciones existentes
	 * y llenar el filtro de la tabla de consulta interna
	 * @return List<TipodocumentoDTO>
	 */
	@Override
	public List<TipodocumentoDTO> obtenerFiltroTipoDocumento(SsoVwUsuarioDTO ssoVwUsuarioDTO) {
		List<NeeCatTipodocumento> listNeeCatTipodocumentos = new ArrayList<NeeCatTipodocumento>();
		List<TipodocumentoDTO> listTipodocumentoDTOs = new ArrayList<TipodocumentoDTO>();
		TipoDocumentoHelper tipoDocumentoHelper = new TipoDocumentoHelper();
//		listNeeCatTipodocumentos = consultaInternaDAO.obtenerCatalogoTipoDocumento();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT distinct ");
		query.append("tipoDoc ");
		query.append("FROM ");
		query.append("NeeCatTipodocumento tipoDoc, NeeNotificaciones noti ");
		query.append("where ");
		query.append("tipoDoc.cveTipodocto = noti.neeCatTipodocumento.cveTipodocto");
		if (ssoVwUsuarioDTO.getIdDelegacion() != null) {
			query.append(" AND noti.neeCatDelegacion.cveIdDelegacion = "+ssoVwUsuarioDTO.getIdDelegacion());
		}
		if (ssoVwUsuarioDTO.getIdSubdelegacion() != null) {
			query.append(" AND noti.neeCatSubdelegacion.cveIdSubdelegacion = "+ssoVwUsuarioDTO.getIdSubdelegacion());
		}
		listNeeCatTipodocumentos = catTipoDocumentoDAO.getByQuery(query.toString());
		
		for (NeeCatTipodocumento neeCatTipodocumento : listNeeCatTipodocumentos) {
			TipodocumentoDTO tipodocumentoDTO = new TipodocumentoDTO();
			tipodocumentoDTO = tipoDocumentoHelper.setterTipoDocumentoEntityToTipoDocumentoDTO(neeCatTipodocumento);
			listTipodocumentoDTOs.add(tipodocumentoDTO);
		}
		return listTipodocumentoDTOs;
	}
	
	/**
	 * Metodo para obtener los distintos status de las notificaciones existentes
	 * y llenar el filtro de la tabla de consulta interna
	 * @return List<StatusDTO>
	 */
	@Override
	public List<StatusDTO> obtenerFiltroStatus(SsoVwUsuarioDTO ssoVwUsuarioDTO) {
		List<NeeCatStatus> listNeeCatStatus = new ArrayList<NeeCatStatus>();
		List<StatusDTO> listStatusDTOs = new ArrayList<StatusDTO>();
		StatusHelper statusHelper = new StatusHelper();
//		listNeeCatStatus = consultaInternaDAO.obtenerCatalogoStatus();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT ");
		query.append("catStatus ");
		query.append("FROM ");
		query.append("NeeCatStatus catStatus, NeeNotificaciones noti ");
		query.append("WHERE ");
		query.append("catStatus.cveStatus = noti.neeCatStatus.cveStatus");
		if (ssoVwUsuarioDTO.getIdDelegacion() != null) {
			query.append(" AND noti.neeCatDelegacion.cveIdDelegacion = "+ssoVwUsuarioDTO.getIdDelegacion());
		}
		if (ssoVwUsuarioDTO.getIdSubdelegacion() != null) {
			query.append(" AND noti.neeCatSubdelegacion.cveIdSubdelegacion = "+ssoVwUsuarioDTO.getIdSubdelegacion());
		}
		listNeeCatStatus = CatStatusDAO.getByQuery(query.toString());
		
		for (NeeCatStatus neeCatStatus : listNeeCatStatus) {
			StatusDTO statusDTO = new StatusDTO();
			statusDTO = statusHelper.setterStatusEntityToStatusDTO(neeCatStatus);
			listStatusDTOs.add(statusDTO);
		}
		return listStatusDTOs;
	}

	/**
	 * Metodo para obtener el documento adjunto de una notificacion
	 * @param DocumentosAdjuntosDTO
	 * @return DocumentosAdjuntosDTO
	 */
	@Override
	public DocumentosAdjuntosDTO obtenerDocumentoAdjunto(DocumentosAdjuntosDTO documentosAdjuntosDTO) {
		NeeDocumentosAdjuntos neeDocumentosAdjuntos = new NeeDocumentosAdjuntos();
		NeeNotificaciones neeNotificaciones = new NeeNotificaciones();
		
		neeDocumentosAdjuntos.setCveDoctoAdjunto(documentosAdjuntosDTO.getCveDoctoAdjunto());
		
		neeNotificaciones.setCveNotificaciones(documentosAdjuntosDTO.getNotificacionesDTO().getCveNotificaciones());
		neeDocumentosAdjuntos.setNeeNotificaciones(neeNotificaciones);
		
		neeDocumentosAdjuntos = consultaInternaDAO.obtenerDocumentoAdjunto(neeDocumentosAdjuntos);
		DocumentosAdjuntosHelper documentosAdjuntosHelper = new DocumentosAdjuntosHelper();
		documentosAdjuntosDTO = documentosAdjuntosHelper.setterDocumentosAdjuntosEntityToDocumentosAdjuntosDTO(neeDocumentosAdjuntos);
		
		return documentosAdjuntosDTO;
	}

	/**
	 * Metodo para obtener la lista paginada de las notificaciones para los internos del IMSS filtrada por fecha 
	 * para ejecutar la tarea de registros por esa fecha
	 * @param 
	 * @return 
	 */
	@Override
	public Integer consultaInternaEjecuta(Integer idEstatus, Date fechaEjecuta, SsoVwUsuarioDTO ssoVwUsuarioDTO) {

		Integer totalRegistros = consultaInternaDAO.contarTotalRegistros();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaEjecuta);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		fechaEjecuta = cal.getTime();
		Integer totalRegistrosParaMostrar = consultaInternaDAO.contarRegistrosFiltrados(idEstatus, fechaEjecuta, ssoVwUsuarioDTO);

		
		return totalRegistrosParaMostrar;
	}

	/**
	 * Metod para ejecutar las tarea de registro por fecha
	 */
	@Override
	public Integer ejecutaTareaNotifica(Integer idEstatus, Date fechaRegistro) {
		
		Integer idBuscar = 0;
		Integer idCambio = 0;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaRegistro);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		fechaRegistro = cal.getTime();
		
		if (idEstatus == Constantes.ESTATUS.PUBLICADA.getStatus()) {
			idBuscar = Constantes.ESTATUS.REGISTRADA.getStatus();
			idCambio = Constantes.ESTATUS.PUBLICADA.getStatus();
		} else if (idEstatus == Constantes.ESTATUS.RETIRADA.getStatus()) {
			idBuscar = Constantes.ESTATUS.PUBLICADA.getStatus();
			idCambio = Constantes.ESTATUS.RETIRADA.getStatus();
		}
		
		Integer totalRegistros = ejecutaTareaNotificacionBusiness.modificaNotificacionesPublicadasPorFecha(idBuscar, idCambio, fechaRegistro);
		
		return totalRegistros;
	}

}
