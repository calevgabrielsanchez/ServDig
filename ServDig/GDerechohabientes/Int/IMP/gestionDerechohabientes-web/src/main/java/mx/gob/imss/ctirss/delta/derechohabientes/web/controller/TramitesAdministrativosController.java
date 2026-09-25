package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitesAdministrativosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto.TramiteDto;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ImpresionReporteDto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteReactivacionDerechohab;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/tramites/admin")
public class TramitesAdministrativosController extends AbstractController {
	
	private final String VIEW_CANDIDATOS = "candidatosTramitesAdministrativos";
	private final String VIEW_CAPTURA_DATOS = "capturaDatosAdministrativos";
	
	private final String REDIRECT_CAPTURA = "/tramites/admin/captura";
	
	private final String KEY_INTEGRANTE = "hijo";
	private final String KEY_SOLICITUD = "solicitudAdministrativaSession";
	private final String KEY_TRAMITE_DTO = "tramiteDtoSession";

	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private TramitesAdministrativosServiceRemote tramitesAdministrativosServiceRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	
	@RequestMapping(value = "/suspension") 
	public String candidatosSuspencion(HttpSession session, HttpServletRequest request) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		List<GrupoFamiliar> candidatos = null;
		
		try {
			candidatos = tramitesAdministrativosServiceRemote.findCandidatosSuspencionAdministrativa(asignacionNSS);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("candidatos", candidatos);
		request.setAttribute("tipoTramiteAdmin", TipoTramiteEnum.SUSPENCION_ADMINISTRATIVA.getCodigo());
		
		return VIEW_CANDIDATOS;
	}
	
	@RequestMapping(value = "/baja") 
	public String candidatosBajaAdministrativa(HttpSession session, HttpServletRequest request) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		List<GrupoFamiliar> candidatos = null;
		
		try {
			candidatos = tramitesAdministrativosServiceRemote.findCandidatosBajaAdministrativa(asignacionNSS);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("candidatos", candidatos);
		request.setAttribute("tipoTramiteAdmin", TipoTramiteEnum.BAJA_ADMINISTRATIVA.getCodigo());
		
		return VIEW_CANDIDATOS;
	}
	
	@RequestMapping(value = "/reactivacion") 
	public String candidatosReactivacion(HttpSession session, HttpServletRequest request) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		List<GrupoFamiliar> candidatos = null;
		
		try {
			candidatos = tramitesAdministrativosServiceRemote.findCandidatosReactivacionAdministrativa(asignacionNSS);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("candidatos", candidatos);
		request.setAttribute("tipoTramiteAdmin", TipoTramiteEnum.REACTIVACION_ADMINISTRATIVA.getCodigo());
		
		return VIEW_CANDIDATOS;
	}
	
	
	@RequestMapping(value="/captura")
	public Object capturaDatosSuspencion(HttpSession session) {
		
		TramiteDto tramite = (TramiteDto) session.getAttribute(KEY_TRAMITE_DTO);
		
		if(tramite == null) {
			return new RedirectView("/inicio/grupoFamiliar", true);
		}
		
		return VIEW_CAPTURA_DATOS;
	}

	
	@RequestMapping(value = "/crear", method = RequestMethod.POST)
	public Object crearSuspencion(HttpSession session, HttpServletRequest request,@ModelAttribute GrupoFamiliar integrante) {
		
		
		return crearTramiteAdministrativo(session, integrante.getDerechohabiente().getIdPersona(), integrante.getTipoProrroga().getIdTipoTramite());
	}
	
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public Object finalizarTramiteAdministrativo(@ModelAttribute TramiteDto tramite, HttpSession session, HttpServletRequest request) {
		
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		//Obtenemos el usuario que genera el tramite
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		//soliciud
		Solicitud solicitudAdmin = null;
		//se recupera al integrante,
		GrupoFamiliar intetranteTramite = (GrupoFamiliar) session.getAttribute(KEY_INTEGRANTE);
		//
		Integer tipoTramite = tramite.getIdTipoTramite().intValue();

		try {
			if(tipoTramite.equals(TipoTramiteEnum.BAJA_ADMINISTRATIVA.getCodigo()) || tipoTramite.equals(TipoTramiteEnum.SUSPENCION_ADMINISTRATIVA.getCodigo())) {
				TipoTramiteEnum tipoBaja = TipoTramiteEnum.obternerEnumById(tipoTramite);
				solicitudAdmin = tramitesAdministrativosServiceRemote.crearSolicitudBajaSuspencion(intetranteTramite, asignacionNSS, usuario, OrigenSolicitudEnum.VENTANILLA, tipoBaja);
			} else {
				solicitudAdmin = tramitesAdministrativosServiceRemote.crearSolicitudReactivacion(intetranteTramite, asignacionNSS, usuario);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TramiteBajaDerechohabiente tramiteBajaXml = null;
		TramiteReactivacionDerechohab tramiteReactivacionXml = null;
		Tramite tramiteSol = solicitudAdmin.getTramites().get(0);
		ImpresionReporteDto reporte = new ImpresionReporteDto();
		
		//bandera de error 
		Boolean existeError = false;
		String exception = null;
		String error = null;
		
		if(tramiteSol instanceof TramiteBajaDerechohabiente) {
			tramiteBajaXml = (TramiteBajaDerechohabiente) tramiteSol;
			tramiteBajaXml.setMotivo(tramite.getMotivo());
			tramiteBajaXml.setMatricula(tramite.getMatricula());
			tramiteBajaXml.setFundamentoLegal(tramite.getFundamentoLegal());
		} else {
			tramiteReactivacionXml = (TramiteReactivacionDerechohab) tramiteSol;
			tramiteReactivacionXml.setMotivo(tramite.getMotivo());
			tramiteReactivacionXml.setMatricula(tramite.getMatricula());
			tramiteReactivacionXml.setFundamentoLegal(tramite.getFundamentoLegal());
		}
		
		solicitudAdmin.setTramites(new ArrayList<Tramite>());
		if(tramiteBajaXml != null) {
			solicitudAdmin.getTramites().add(tramiteBajaXml);
		} else {
			solicitudAdmin.getTramites().add(tramiteReactivacionXml);
		}
		
		
		reporte.setIdPersona(tramite.getIdPersona());
		reporte.setIdTramite(tramite.getIdTramite());
		reporte.setRechazado(false);
		reporte.setTipoTramite(tramiteSol.getTipoTramite());
		
		log.debug("La matricula capturada es: " + tramite.getMatricula());
		log.debug("EL motivo es: " + tramite.getMotivo());
		log.debug("El fundamento legal del tramites es: " + tramite.getFundamentoLegal());
		log.debug("El tipo de tramite a realizar es: " + tramite.getIdTipoTramite() + " y el que viene en el tramite es: ["+tramiteSol.getTipoTramite().getIdTipoTramite() +
				"," + tramiteSol.getTipoTramite().getDescripcion()+"]");
		
		try {
			
			if(tramiteBajaXml != null) {
				tramitesAdministrativosServiceRemote.finalizarTramiteBajaSuspencionAdministrativa(solicitudAdmin, asignacionNSS);
			} else {
				tramitesAdministrativosServiceRemote.finalizarReactivacionAdministrativa(solicitudAdmin, asignacionNSS);
			}
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
			existeError = true;
			exception = e.getMessage();
		} catch (SolicitudException e) {
			e.printStackTrace();
			existeError = true;
			exception = e.getMessage();
		} catch (ImpactaAlmacenesWSException e) {
			e.printStackTrace();
			existeError = true;
			exception= "Ocurri&oacute; un error al calcular la vigencia";
			/*if(e.getCodigo() != null) {
				exception += " (" +e.getCodigo() + " , " + e.getSituacion() + " ).";
			}*/
			error = "Por favor int&eacute;ntelo m&aacute;s tarde realizando nuevamente la solicitud, ya que por ahora no es posible calcular la vigencia.";
		}
		
		if(existeError) {
			session.setAttribute("mostrarBoton", true);
			session.setAttribute("exception", exception);
			session.setAttribute("error", error);
			try {
				solicitudBusinessRemote.cancelarSolicitud(solicitudAdmin.getSolicitudId(), null, null, usuario.getUsuario(), exception);
			} catch (SolicitudException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new RedirectView("/solicitud/errorFinalizado", true);
		
		}
		//los ponemos en session ya que al hacer el redireccionamiento 
		//Se sobreescribe el recuest y el model
		session.setAttribute("reporte", reporte);	
		session.setAttribute("solicitud", solicitudAdmin);
		
		return new RedirectView("/solicitud/finalizada", true);
	}
	
	private RedirectView crearTramiteAdministrativo(HttpSession session, Long idBeneficiario, Integer tipoTramite) {
		//obtenemos la asignacion de NSS
		AsignacionNSS asignacionNSS = (AsignacionNSS)session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		GrupoFamiliar asegurado = (GrupoFamiliar)  session.getAttribute("miGrupoFamiliar");
		//seteamos la bandera para saber si es pensionado y poder usarla en el reporte
		asignacionNSS.setPensionado(asegurado.getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()));
		
		//integrante a dar de baja
		GrupoFamiliar intetranteTramite = null;
		
		TramiteDto tramiteDto = new TramiteDto();
		
		log.debug("Se manda como tipo de tramite el " + tipoTramite);
		
		try {
			intetranteTramite = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(asignacionNSS.getIdAsignacionNSS(), idBeneficiario);
		} catch (Exception e) {
			log.error("error al consultar al derechohabiente con vigencia" , e);
		}
		
		if(intetranteTramite == null){
			try {
			intetranteTramite = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(asignacionNSS.getIdAsignacionNSS(), idBeneficiario);
			}catch(Exception e){
				log.error("error al consultar al derechohabiente sin vigencia" , e);
			}
		}
	
		tramiteDto.setIdPersona(intetranteTramite.getDerechohabiente().getIdPersona());
		//tramiteDto.setIdSolicitud(solicitudAdmin.getSolicitudId());
		tramiteDto.setIdTipoTramite(tipoTramite.longValue());
		
		session.setAttribute(KEY_INTEGRANTE, intetranteTramite);
		//session.setAttribute(KEY_SOLICITUD, solicitudAdmin);
		session.setAttribute(KEY_TRAMITE_DTO, tramiteDto);
		
		return new RedirectView(REDIRECT_CAPTURA, true);
	}

}
