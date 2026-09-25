package mx.imss.estrados.service.ejb.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.imss.estrados.dto.NotificacionesDTO;
import mx.imss.estrados.dto.SsoVwUsuarioDTO;
import mx.imss.estrados.entity.NeeNotificaciones;
import mx.imss.estrados.entity.SsoVwUsuario;
import mx.imss.estrados.paginado.dto.PaginadoRequest;
import mx.imss.estrados.paginado.dto.PaginadoResponse;
import mx.imss.estrados.service.ejb.dao.ConsultaExternaDAO;
import mx.imss.estrados.service.interfaces.ConsultaExternaServiceRemote;
import mx.imss.estrados.utils.NotificacionHelper;
import mx.imss.estrados.utils.SsoVwUsuarioHelper;

import org.apache.log4j.Logger;

@Stateless(name = "consultaExternaServiceBean", mappedName = "consultaExternaServiceBean")
public class ConsultaExternaServiceBean implements ConsultaExternaServiceRemote {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(ConsultaExternaServiceBean.class);
	
	@EJB
	ConsultaExternaDAO consultaExternaDAO;
	
	/**
	 * Metodo para obtener la lista paginada de las notificaciones para el publico
	 * @param PaginadoRequest
	 * @return Paginado
	 */
	@Override
	public PaginadoResponse consultaExternaPaginada(PaginadoRequest paginadoRequest) {
		List<NeeNotificaciones> listNeeNotificaciones;
		List<NotificacionesDTO> listNotificacionesDTOs = new ArrayList<NotificacionesDTO>();
		NotificacionHelper notificacionHelper = new NotificacionHelper();
		SsoVwUsuarioHelper ssoVwUsuarioHelper = new SsoVwUsuarioHelper();
		
		//Integer totalRegistros = consultaExternaDAO.contarTotalRegistros();
		Integer totalRegistrosParaMostrar = consultaExternaDAO.contarRegistrosFiltrados(paginadoRequest);
		listNeeNotificaciones = consultaExternaDAO.filtrar(paginadoRequest);
		
		for (NeeNotificaciones neeNotificaciones : listNeeNotificaciones) {
			NotificacionesDTO notificacionesDTO = new NotificacionesDTO();
			SsoVwUsuario ssoVwUsuario = new SsoVwUsuario();
			SsoVwUsuarioDTO ssoVwUsuarioDTO = new SsoVwUsuarioDTO();
			notificacionesDTO = notificacionHelper.setterNotificacionesEntityToNotificacionesDTO(neeNotificaciones);
			
			ssoVwUsuario = consultaExternaDAO.obtenerSsoVwUsuario(notificacionesDTO.getCveUsuario());
			ssoVwUsuarioDTO = ssoVwUsuarioHelper.setterSsoVwUsuarioEntityToSsoVwUsuarioDTO(ssoVwUsuario);
			notificacionesDTO.setSsoVwUsuarioDTO(ssoVwUsuarioDTO);
			listNotificacionesDTOs.add(notificacionesDTO);
		}
		
		PaginadoResponse paginadoResponse = new PaginadoResponse();
		paginadoResponse.setAaData(listNotificacionesDTOs);
		//paginadoResponse.setiTotalRecords(totalRegistros);
		paginadoResponse.setiTotalDisplayRecords(totalRegistrosParaMostrar);
		paginadoResponse.setsEcho(paginadoRequest.getEcho());
		
		return paginadoResponse;
	}

}
