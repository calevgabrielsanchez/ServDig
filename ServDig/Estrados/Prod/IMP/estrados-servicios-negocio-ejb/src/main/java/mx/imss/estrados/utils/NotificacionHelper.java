package mx.imss.estrados.utils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import mx.imss.estrados.dto.AreaRespNotifDTO;
import mx.imss.estrados.dto.DelegacionDTO;
import mx.imss.estrados.dto.DocumentosAdjuntosDTO;
import mx.imss.estrados.dto.NotificacionesDTO;
import mx.imss.estrados.dto.ProcesoDTO;
import mx.imss.estrados.dto.SsoVwUsuarioDTO;
import mx.imss.estrados.dto.StatusDTO;
import mx.imss.estrados.dto.SubdelegacionDTO;
import mx.imss.estrados.dto.SujetoANotificarDTO;
import mx.imss.estrados.dto.TipoAdjuntoDTO;
import mx.imss.estrados.dto.TipodocumentoDTO;
import mx.imss.estrados.entity.NeeDocumentosAdjuntos;
import mx.imss.estrados.entity.NeeNotificaciones;

import org.apache.log4j.Logger;

public class NotificacionHelper {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(NotificacionHelper.class);
	
	/**
	 * Metodo para settear la información de la entidad NeeNotificaciones a el objeto NotificacionesDTO
	 * 
	 * @param NeeNotificaciones
	 * @return NotificacionesDTO
	 */
	public NotificacionesDTO setterNotificacionesEntityToNotificacionesDTO(NeeNotificaciones neeNotificaciones) {
		NotificacionesDTO notificacionesDTO = new NotificacionesDTO();
//		DepartamentoDTO departamentoDTO = new DepartamentoDTO();
		AreaRespNotifDTO areaRespNotifDTO = new AreaRespNotifDTO();
		DelegacionDTO delegacionDTO = new DelegacionDTO();
		SubdelegacionDTO subdelegacionDTO = new SubdelegacionDTO();
		SujetoANotificarDTO sujetoANotificarDTO = new SujetoANotificarDTO();
		StatusDTO statusDTO = new StatusDTO();
		TipodocumentoDTO tipodocumentoDTO = new TipodocumentoDTO();
		List<DocumentosAdjuntosDTO> listDocumentosAdjuntosDTOs = new ArrayList<DocumentosAdjuntosDTO>();
		SsoVwUsuarioDTO ssoVwUsuarioDTO = new SsoVwUsuarioDTO();
		
		//logger.debug("Se inicia el setteo de la entidad NeeNotificaciones al objeto NotificacionesDTO");
		
		if (neeNotificaciones != null) {
		
			notificacionesDTO.setCveNotificaciones(neeNotificaciones.getCveNotificaciones());
			
//			if (neeNotificaciones.getNeeCatDepartamento() != null && neeNotificaciones.getNeeCatDepartamento().getNeeCatAreanormativa() != null && 
//					neeNotificaciones.getNeeCatDepartamento().getNeeCatProceso() != null) {
//				departamentoDTO = setterDepartamentoEntityToDTO(neeNotificaciones);
//				notificacionesDTO.setDepartamentoDTO(departamentoDTO);
//			}
			
			if (neeNotificaciones.getNeeCatAreaRespNotif() != null && neeNotificaciones.getNeeCatAreaRespNotif().getNeeCatProceso() != null) {
				areaRespNotifDTO = setterAreaRespNotiEntityToDTO(neeNotificaciones);
				notificacionesDTO.setAreaRespNotifDTO(areaRespNotifDTO);
			}
			
			if (neeNotificaciones.getNeeCatDelegacion() != null) {
				delegacionDTO = setterDelegacionEntityToDTO(neeNotificaciones);
				notificacionesDTO.setDelegacionDTO(delegacionDTO);
			}
			
			if (neeNotificaciones.getNeeCatSubdelegacion() != null && neeNotificaciones.getNeeCatSubdelegacion().getNeeCatDelegacion() != null) {
				subdelegacionDTO = setterSubdelegacionEntityToDTO(neeNotificaciones);
				notificacionesDTO.setSubdelegacionDTO(subdelegacionDTO);
			}
			
			if (neeNotificaciones.getNeeCatSujetoANotificar() != null) {
				sujetoANotificarDTO = setterSujetoANotificarEntityToDTO(neeNotificaciones);
				notificacionesDTO.setSujetoANotificarDTO(sujetoANotificarDTO);
			}
			
			notificacionesDTO.setRegistroPatronal(neeNotificaciones.getRegistroPatronal());
			notificacionesDTO.setDesNumRegCpa(neeNotificaciones.getDesNumRegCpa());
			notificacionesDTO.setRazonSocial(neeNotificaciones.getRazonSocial());
			notificacionesDTO.setDesDomicilio(neeNotificaciones.getDesDomicilio());
			notificacionesDTO.setIdDomicilio(neeNotificaciones.getIdDomicilio());
			
			if (neeNotificaciones.getNeeCatStatus() != null) {
				statusDTO = setterStatusEntityToDTO(neeNotificaciones);
				notificacionesDTO.setStatusDTO(statusDTO);
			}
			
			if (neeNotificaciones.getNeeCatTipodocumento() != null && neeNotificaciones.getNeeCatTipodocumento().getNeeCatProceso() != null) {
				tipodocumentoDTO = setterTipoDocumentoEntityToDTO(neeNotificaciones);
				notificacionesDTO.setTipodocumentoDTO(tipodocumentoDTO);
			}
			
			notificacionesDTO.setFecPublicacion(neeNotificaciones.getFecPublicacion());
			notificacionesDTO.setFecInicioPublicacion(neeNotificaciones.getFecInicioPublicacion());
			notificacionesDTO.setFecFinPublicacion(neeNotificaciones.getFecFinPublicacion());
			notificacionesDTO.setFecRetiroPublicacion(neeNotificaciones.getFecRetiroPublicacion());
			notificacionesDTO.setCveUsuario(neeNotificaciones.getCveUsuario());
			notificacionesDTO.setFecRegistro(neeNotificaciones.getFecRegistro());
			notificacionesDTO.setDesRefAcuse(neeNotificaciones.getDesRefAcuse());
			notificacionesDTO.setDesRefPublicacion(neeNotificaciones.getDesRefPublicacion());
			notificacionesDTO.setDesRefRetiro(neeNotificaciones.getDesRefRetiro());
			
			if (neeNotificaciones.getFecPublicacion() != null) {
				notificacionesDTO.setFecPublicacionCadena(UtileriaFechas.parseDateToString(neeNotificaciones.getFecPublicacion(), "dd/MM/yyyy"));
			}
			
			if (neeNotificaciones.getListNeeDocumentosAdjuntos() != null && !neeNotificaciones.getListNeeDocumentosAdjuntos().isEmpty()) {
				listDocumentosAdjuntosDTOs = setterDocumentosAdjuntosEntityToDTO(neeNotificaciones);
				notificacionesDTO.setListDocumentosAdjuntosDTOs(listDocumentosAdjuntosDTOs);
			}
		}
		return notificacionesDTO;
	}
	
//	/**
//	 * Metodo para settear la información de la entidad NeeCatDepartamento de NeeNotificaciones a el objeto DepartamentoDTO
//	 * 
//	 * @param NeeNotificaciones
//	 * @return DepartamentoDTO
//	 */
//	public DepartamentoDTO setterDepartamentoEntityToDTO(NeeNotificaciones neeNotificaciones) {
//		DepartamentoDTO departamentoDTO = new DepartamentoDTO();
//		ProcesoDTO procesoDTO = new ProcesoDTO();
//		AreanormativaDTO areanormativaDTO = new AreanormativaDTO();
//		
//		departamentoDTO.setCveDepto(neeNotificaciones.getNeeCatDepartamento().getCveDepto());
//		procesoDTO.setCveProceso(neeNotificaciones.getNeeCatDepartamento().getNeeCatProceso().getCveProceso());
//		procesoDTO.setDesProceso(neeNotificaciones.getNeeCatDepartamento().getNeeCatProceso().getDesProceso());
//		departamentoDTO.setProcesoDTO(procesoDTO);
//		areanormativaDTO.setCveAreanorma(neeNotificaciones.getNeeCatDepartamento().getNeeCatAreanormativa().getCveAreanorma());
//		areanormativaDTO.setDesAreanorma(neeNotificaciones.getNeeCatDepartamento().getNeeCatAreanormativa().getDesAreanorma());
//		departamentoDTO.setAreanormativaDTO(areanormativaDTO);
//		departamentoDTO.setDesDepartamento(neeNotificaciones.getNeeCatDepartamento().getDesDepartamento());
//		
//		return departamentoDTO;
//	}
	
	/**
	 * Metodo para settear la información de la entidad NeeCatAreaRespNotif de NeeNotificaciones a el objeto DepartamentoDTO
	 * 
	 * @param NeeNotificaciones
	 * @return AreaRespNotifDTO
	 */
	public AreaRespNotifDTO setterAreaRespNotiEntityToDTO(NeeNotificaciones neeNotificaciones) {
		AreaRespNotifDTO areaRespNotifDTO = new AreaRespNotifDTO();
		ProcesoDTO procesoDTO = new ProcesoDTO();
//		AreanormativaDTO areanormativaDTO = new AreanormativaDTO();
		
		procesoDTO.setCveProceso(neeNotificaciones.getNeeCatAreaRespNotif().getNeeCatProceso().getCveProceso());
		procesoDTO.setDesProceso(neeNotificaciones.getNeeCatAreaRespNotif().getNeeCatProceso().getDesProceso());
		areaRespNotifDTO.setProcesoDTO(procesoDTO);
//		areanormativaDTO.setCveAreanorma(neeNotificaciones.getNeeCatAreaRespNotif().getNeeCatAreanormativa().getCveAreanorma());
//		areanormativaDTO.setDesAreanorma(neeNotificaciones.getNeeCatAreaRespNotif().getNeeCatAreanormativa().getDesAreanorma());
//		areaRespNotifDTO.setCveAreaRespNotif(neeNotificaciones.getNeeCatAreaRespNotif().getCveAreaRespNotif());
//		areaRespNotifDTO.setAreanormativaDTO(areanormativaDTO);
		
		return areaRespNotifDTO;
	}
	
	/**
	 * Metodo para settear la información de la entidad NeeCatDelegacion de NeeNotificaciones a el objeto DelegacionDTO
	 * 
	 * @param NeeNotificaciones
	 * @return DelegacionDTO
	 */
	public DelegacionDTO setterDelegacionEntityToDTO(NeeNotificaciones neeNotificaciones) {
		DelegacionDTO delegacionDTO = new DelegacionDTO();
		
		delegacionDTO.setCveIdDelegacion(neeNotificaciones.getNeeCatDelegacion().getCveIdDelegacion());
		delegacionDTO.setDesDeleg(neeNotificaciones.getNeeCatDelegacion().getDesDeleg());
		delegacionDTO.setAnioIniOper(neeNotificaciones.getNeeCatDelegacion().getAnioIniOper());
		delegacionDTO.setClaveDelegacion(neeNotificaciones.getNeeCatDelegacion().getClaveDelegacion());
		delegacionDTO.setTipDelegacion(neeNotificaciones.getNeeCatDelegacion().getTipDelegacion());
		delegacionDTO.setFecRegistroAlta(neeNotificaciones.getNeeCatDelegacion().getFecRegistroAlta());
		delegacionDTO.setFecRegistroBaja(neeNotificaciones.getNeeCatDelegacion().getFecRegistroBaja());
		delegacionDTO.setFecRegistroActualizado(neeNotificaciones.getNeeCatDelegacion().getFecRegistroActualizado());
		delegacionDTO.setDomicilioId(neeNotificaciones.getNeeCatDelegacion().getDomicilioId());
		delegacionDTO.setCveCiz(neeNotificaciones.getNeeCatDelegacion().getCveCiz());
		delegacionDTO.setDesRIMSSDelegacion(neeNotificaciones.getNeeCatDelegacion().getDesRIMSSDelegacion());
		
		return delegacionDTO;
	}
	
	/**
	 * Metodo para settear la información de la entidad NeeCatSubdelegacion de NeeNotificaciones a el objeto SubdelegacionDTO
	 * 
	 * @param NeeNotificaciones
	 * @return SubdelegacionDTO
	 */
	public SubdelegacionDTO setterSubdelegacionEntityToDTO(NeeNotificaciones neeNotificaciones) {
		SubdelegacionDTO subdelegacionDTO = new SubdelegacionDTO();
		DelegacionDTO delegacionDTO = new DelegacionDTO();
		
		subdelegacionDTO.setCveIdSubdelegacion(neeNotificaciones.getNeeCatSubdelegacion().getCveIdSubdelegacion());
		delegacionDTO.setCveIdDelegacion(neeNotificaciones.getNeeCatSubdelegacion().getNeeCatDelegacion().getCveIdDelegacion());
		delegacionDTO.setDesDeleg(neeNotificaciones.getNeeCatSubdelegacion().getNeeCatDelegacion().getDesDeleg());
		delegacionDTO.setAnioIniOper(neeNotificaciones.getNeeCatSubdelegacion().getNeeCatDelegacion().getAnioIniOper());
		delegacionDTO.setClaveDelegacion(neeNotificaciones.getNeeCatSubdelegacion().getNeeCatDelegacion().getClaveDelegacion());
		delegacionDTO.setTipDelegacion(neeNotificaciones.getNeeCatSubdelegacion().getNeeCatDelegacion().getTipDelegacion());
		delegacionDTO.setFecRegistroAlta(neeNotificaciones.getNeeCatSubdelegacion().getNeeCatDelegacion().getFecRegistroAlta());
		delegacionDTO.setFecRegistroBaja(neeNotificaciones.getNeeCatSubdelegacion().getNeeCatDelegacion().getFecRegistroBaja());
		delegacionDTO.setFecRegistroActualizado(neeNotificaciones.getNeeCatSubdelegacion().getNeeCatDelegacion().getFecRegistroActualizado());
		delegacionDTO.setDomicilioId(neeNotificaciones.getNeeCatSubdelegacion().getNeeCatDelegacion().getDomicilioId());
		delegacionDTO.setCveCiz(neeNotificaciones.getNeeCatSubdelegacion().getNeeCatDelegacion().getCveCiz());
		delegacionDTO.setDesRIMSSDelegacion(neeNotificaciones.getNeeCatDelegacion().getDesRIMSSDelegacion());
		subdelegacionDTO.setDelegacionDTO(delegacionDTO);
		subdelegacionDTO.setDesSubdelegacion(neeNotificaciones.getNeeCatSubdelegacion().getDesSubdelegacion());
		subdelegacionDTO.setAnioIniOper(neeNotificaciones.getNeeCatSubdelegacion().getAnioIniOper());
		subdelegacionDTO.setClaveSubdelegacion(neeNotificaciones.getNeeCatSubdelegacion().getClaveSubdelegacion());
		subdelegacionDTO.setFecRegistroAlta(neeNotificaciones.getNeeCatSubdelegacion().getFecRegistroAlta());
		subdelegacionDTO.setFecRegistroBaja(neeNotificaciones.getNeeCatSubdelegacion().getFecRegistroBaja());
		subdelegacionDTO.setFecRegistroActualizado(neeNotificaciones.getNeeCatSubdelegacion().getFecRegistroActualizado());
		subdelegacionDTO.setDomicilioId(neeNotificaciones.getNeeCatSubdelegacion().getDomicilioId());
		subdelegacionDTO.setDesRIMSSSubDelegacion(neeNotificaciones.getNeeCatSubdelegacion().getDesRIMSSSubDelegacion());
		
		return subdelegacionDTO;
	}
	
	/**
	 * Metodo para settear la información de la entidad NeeCatSujetoANotificar de NeeNotificaciones a el objeto SujetoANotificarDTO
	 * 
	 * @param NeeNotificaciones
	 * @return SujetoANotificarDTO
	 */
	public SujetoANotificarDTO setterSujetoANotificarEntityToDTO(NeeNotificaciones neeNotificaciones) {
		SujetoANotificarDTO sujetoANotificarDTO = new SujetoANotificarDTO();
		SujetoANotificarDTO sujetoANotificarDTOHijo = new SujetoANotificarDTO();
		
		sujetoANotificarDTO.setCveSujetoANotificar(neeNotificaciones.getNeeCatSujetoANotificar().getCveSujetoANotificar());
		sujetoANotificarDTO.setDesDirigidoA(neeNotificaciones.getNeeCatSujetoANotificar().getDesDirigidoA());
		sujetoANotificarDTOHijo.setCveSujetoANotificar(neeNotificaciones.getNeeCatSujetoANotificar().getNeeCatSujetoANotificar().getCveSujetoANotificar());
		sujetoANotificarDTOHijo.setDesDirigidoA(neeNotificaciones.getNeeCatSujetoANotificar().getNeeCatSujetoANotificar().getDesDirigidoA());					
		sujetoANotificarDTO.setSujetoANotificarDTO(sujetoANotificarDTOHijo);
		
		return sujetoANotificarDTO;
	}
	
	/**
	 * Metodo para settear la información de la entidad NeeCatStatus de NeeNotificaciones a el objeto StatusDTO
	 * 
	 * @param NeeNotificaciones
	 * @return StatusDTO
	 */
	public StatusDTO setterStatusEntityToDTO(NeeNotificaciones neeNotificaciones) {
		StatusDTO statusDTO = new StatusDTO();
		
		statusDTO.setCveStatus(neeNotificaciones.getNeeCatStatus().getCveStatus());
		statusDTO.setDesEstatus(neeNotificaciones.getNeeCatStatus().getDesEstatus());
		
		return statusDTO;
	}
	
	/**
	 * Metodo para settear la información de la entidad NeeCatTipodocumento de NeeNotificaciones a el objeto TipodocumentoDTO
	 * 
	 * @param NeeNotificaciones
	 * @return TipodocumentoDTO
	 */
	public TipodocumentoDTO setterTipoDocumentoEntityToDTO(NeeNotificaciones neeNotificaciones) {
		TipodocumentoDTO tipodocumentoDTO = new TipodocumentoDTO();
		ProcesoDTO procesoDTO = new ProcesoDTO();
		
		tipodocumentoDTO.setCveTipodocto(neeNotificaciones.getNeeCatTipodocumento().getCveTipodocto());
		procesoDTO.setCveProceso(neeNotificaciones.getNeeCatTipodocumento().getNeeCatProceso().getCveProceso());
		procesoDTO.setDesProceso(neeNotificaciones.getNeeCatTipodocumento().getNeeCatProceso().getDesProceso());
		tipodocumentoDTO.setProcesoDTO(procesoDTO);
		tipodocumentoDTO.setDesTipodocumento(neeNotificaciones.getNeeCatTipodocumento().getDesTipodocumento());
		
		return tipodocumentoDTO;
	}
	
	/**
	 * Metodo para settear la información de la lista de entidades NeeDocumentosAdjuntos de NeeNotificaciones a la lista de objetos DocumentosAdjuntosDTO
	 * 
	 * @param NeeNotificaciones
	 * @return List<DocumentosAdjuntosDTO>
	 */
	public List<DocumentosAdjuntosDTO> setterDocumentosAdjuntosEntityToDTO(NeeNotificaciones neeNotificaciones) {
		List<DocumentosAdjuntosDTO> listDocumentosAdjuntosDTOs = new ArrayList<DocumentosAdjuntosDTO>();
		List<NeeDocumentosAdjuntos> listNeeDocumentosAdjuntos = neeNotificaciones.getListNeeDocumentosAdjuntos();
		
		for (NeeDocumentosAdjuntos neeDocumentosAdjuntos : listNeeDocumentosAdjuntos) {
			DocumentosAdjuntosDTO documentosAdjuntosDTO = new DocumentosAdjuntosDTO();
			TipoAdjuntoDTO tipoAdjuntoDTO = new TipoAdjuntoDTO();
			
			documentosAdjuntosDTO.setCveDoctoAdjunto(neeDocumentosAdjuntos.getCveDoctoAdjunto());
//			documentosAdjuntosDTO.setNotificacionesDTO(neeDocumentosAdjuntos.getNeeNotificaciones());
			documentosAdjuntosDTO.setDesNumOficio(neeDocumentosAdjuntos.getDesNumOficio());
			documentosAdjuntosDTO.setDesNombreArchivo(neeDocumentosAdjuntos.getDesNombreArchivo());
//			documentosAdjuntosDTO.setDesRefFilesystem(neeDocumentosAdjuntos.getDesRefFilesystem());
			
			if (neeDocumentosAdjuntos.getNeeCatTipoAdjunto() != null) {
				tipoAdjuntoDTO.setCveTipoAdjunto(neeDocumentosAdjuntos.getNeeCatTipoAdjunto().getCveTipoAdjunto());
				tipoAdjuntoDTO.setDesTipoAdjunto(neeDocumentosAdjuntos.getNeeCatTipoAdjunto().getDesTipoAdjunto());
				documentosAdjuntosDTO.setTipoAdjuntoDTO(tipoAdjuntoDTO);
			}
			
			listDocumentosAdjuntosDTOs.add(documentosAdjuntosDTO);
		}
		
		return listDocumentosAdjuntosDTOs;
	}

}
