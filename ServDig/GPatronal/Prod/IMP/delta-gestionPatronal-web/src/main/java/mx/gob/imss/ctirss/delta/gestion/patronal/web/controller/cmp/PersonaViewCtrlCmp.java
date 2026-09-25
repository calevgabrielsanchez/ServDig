package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.cmp;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.ReporteParam;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Hugo Martinez
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/cmp/afiliacion")
public class PersonaViewCtrlCmp extends AbstractController{
	
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	
	@Autowired
	private AfiliacionServiceBusinessRemote afiliacionService;

	
	@RequestMapping(value = "/ica/{idPersona}/{tipoPersona}", method = RequestMethod.GET)
	public String administrarTramite(
			@PathVariable Long idPersona, @PathVariable Integer tipoPersona,
			Model model, HttpServletResponse response, HttpSession session, Locale locale) {
		
		super.log.debug(":: idPersona - "+idPersona);
		super.log.debug(":: tipoPersona - "+tipoPersona);
		super.log.error("Estoy en el controller...");
		SujetoObligado sujetoTramite = new SujetoObligado();
		boolean esPatronFisico = false;
		if(tipoPersona.equals(TipoPersona.FISICA)){
			esPatronFisico = true;
			Fisica fisica = new Fisica();
			fisica.setIdPersona(idPersona);
			sujetoTramite.setFisica(fisica);
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		}else{
			Moral moral = new Moral();
			moral.setIdPersona(idPersona);
			sujetoTramite.setMoral(moral);
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		}
		
		model.addAttribute("sujetoTramite",sujetoTramite);
		model.addAttribute("esPatronFisico",esPatronFisico);
		
		return "cambioDatosFiscales";
	}
	
	@RequestMapping(value = "/concluirEnLinea", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> concluirSolicitudEnLinea(
			@RequestParam("tipoTramite") Integer tipoTramite,
			@RequestParam("idSolicitud") Long idSolicitud,
			@RequestBody SujetoObligado inputObject,
			HttpServletResponse response, HttpSession session, Locale locale){
		super.log.debug("Se concluye la solicitud en linea");
		super.log.debug("tipoTramite: "+tipoTramite);
		super.log.debug("idSolicitud: "+idSolicitud);
		Map<String,Object> result= new TreeMap<String, Object>();
		if(idSolicitud==null || (idSolicitud!=null && idSolicitud==0) ){
			result = creaActualizaTramite(inputObject, tipoTramite, idSolicitud, response, session, locale);
			idSolicitud = (Long)result.get("idSolicitud");
		}
		return concluirSolicitud(idSolicitud, inputObject, response, session, locale);
		
	}
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 05/02/2013
	 * @param session
	 * @param tipoSolicitante
	 * @param idSolicitante
	 */
	public void gestionarSolicitanteConclusion(HttpSession session, SujetoObligado sujetoTramite, CodigoRolTemporal tipoSolicitante, Long idSolicitante){
		Fisica fisica = null;
		Usuario usuario = new Usuario();
		if(tipoSolicitante.equals(CodigoRolTemporal.REPRESENTANTE_LEGAL)){
			fisica = (Fisica)sujetoObligadoService.obtenerPersonaPorIdentificador(idSolicitante);
			usuario.setFisica(fisica);
			usuario.setNomNombre(fisica.getNombre());
			usuario.setNomPaterno(fisica.getPrimerApellido());
			usuario.setNomMaterno(fisica.getSegundoApellido());
			usuario.setPerfilUsuario(new PerfilUsuario());
			usuario.getPerfilUsuario().setIdPerfilUsuario(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue());
			usuario.getPerfilUsuario().setDescripcion(CodigoRolTemporal.REPRESENTANTE_LEGAL.name());
		}else if(tipoSolicitante.equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO)){
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				usuario.setNomNombre(sujetoTramite.getFisica().getNombre());
				usuario.setNomPaterno(sujetoTramite.getFisica().getPrimerApellido());
				usuario.setNomMaterno(sujetoTramite.getFisica().getSegundoApellido());
				usuario.setFisica(sujetoTramite.getFisica());
			}else{
				usuario.setNomNombre(sujetoTramite.getMoral().getRazonSocial());
				usuario.setNomPaterno("");
				usuario.setNomMaterno("");
				usuario.setMoral(sujetoTramite.getMoral());
			}
			usuario.setPerfilUsuario(new PerfilUsuario());
			usuario.getPerfilUsuario().setIdPerfilUsuario(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue());
			usuario.getPerfilUsuario().setDescripcion(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.name());
		}
		System.err.println("Tipo Solicitante: "+tipoSolicitante);
		System.err.println("Solicitante: "+usuario);
		
		session.setAttribute("solicitante", usuario);
	}
	
	private Map<String, Object> creaActualizaTramite(
			SujetoObligado inputObject, Integer tipoTramite,
			Long idSolicitud, HttpServletResponse response, 
			HttpSession session, Locale locale){
		System.err.println("ACTUALIZARE TRAMITE DENOMINACION SOCIAL");
		Map<String, Object> result = new HashMap<String, Object>();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		System.err.println("Input Object: " + inputObject);
		TipoTramiteEnum tipoTramiteSolicitado = TipoTramiteEnum.obternerEnumById(tipoTramite);
		System.err.println("Tramite Solicitado: "+tipoTramiteSolicitado);
		if(tipoTramiteSolicitado.equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL)){
			Object objModificacion = session.getAttribute("datosICA");
			ICADatosRespuesta datosIca = null;
			MDMDatosEntrada datosMdm = null;
			if(objModificacion!=null){
				datosIca = (ICADatosRespuesta)objModificacion;
				inputObject.setDatosICA(datosIca);
			}
			objModificacion = session.getAttribute("datosMDM");
			if(objModificacion!=null){
				datosMdm = (MDMDatosEntrada)objModificacion;
				inputObject.setDatosMDM(datosMdm);
			}
			System.err.println("Datos ICA: "+datosIca);
			System.err.println("Datos MDM: "+datosMdm);
		}
		Map<String, Object> mapAccion=new HashMap<String, Object>();
		try {
			mapAccion = afiliacionService
					.gestionarTramiteActualizacionAfiliacion(idSolicitud,
							inputObject,
							tipoTramiteSolicitado,
							usuario, false);
		} catch (GestionPatronalBusinessException e) {
			String mensaje = messageSource.getMessage(e.getMessage(),null,locale);
			result.put("idSolicitud", 0);
			result.put("mensajeError", mensaje);
			e.printStackTrace();
			return result;
		}
		String mensaje = messageSource.getMessage("msg.confirma.actualizacion", null, locale);
		result.put("mensajeExito", mensaje);
		result.put("idSolicitud", mapAccion.get("idSolicitud"));
		result.put("folio", mapAccion.get("folio"));
		return result;
	}
	
	private Map<String, Object> concluirSolicitud(
			Long idSolicitud,
			SujetoObligado inputObject, HttpServletResponse response, 
			HttpSession session, Locale locale){
		Map<String, Object> result = new HashMap<String, Object>();
		System.err.println("Input Object: " + inputObject);
		String tipoDocumento=ReporteParam.TIPO_DOCUMENTO_ACUSE;
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		CodigoRolTemporal rolSolicitante = CodigoRolTemporal.PATRON_SUJETO_OBLIGADO;
		Long idSolicitante = null;
		if(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().equals(
				usuario.getPerfilUsuario().getIdPerfilUsuario().intValue())){
			rolSolicitante = CodigoRolTemporal.REPRESENTANTE_LEGAL;
			idSolicitante = usuario.getFisica().getIdPersona();
		}else{//ES PATRON
			if(inputObject.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
				idSolicitante = usuario.getFisica().getIdPersona();
			else
				idSolicitante = usuario.getMoral().getIdPersona();
		}
		
		gestionarSolicitanteConclusion(session, inputObject, rolSolicitante, idSolicitante);
		String mensaje = "";
		Boolean fueFirmada = (Boolean)session.getAttribute("fueFirmada");
		FirmaElectronica datosFirma = null;
		try {
			System.err.println("fue firmada? "+fueFirmada);
			if(fueFirmada!=null && fueFirmada){
				datosFirma = (FirmaElectronica)session.getAttribute("datosFirma");
				System.err.println("Solicitud firmada... "+datosFirma);
			}
			
			Solicitud solicitud  = afiliacionService.enviarSolicitudAlInstituto(inputObject,
					TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, datosFirma);

			session.setAttribute("idSolicitud", solicitud.getSolicitudId());
			
			if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo())){
				mensaje = messageSource.getMessage("msg.confirmacion.envio.solicitud",null,locale);
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())){
				mensaje = messageSource.getMessage("msg.confirmacion.conclusion.solicitud",null,locale);
				tipoDocumento=ReporteParam.TIPO_DOCUMENTO_AVISO;
			}else if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo())){
				mensaje = messageSource.getMessage("msg.validacion.solicitud",null,locale);
			}
			
//			mensaje = "Su solicitud ha sido enviada al Instituto, "
//					+ "por favor presentese en ventanilla con la documentación "
//					+ "requerida para finalizar su trámite";						
						
			String rfc = "";
			if(inputObject.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				rfc = inputObject.getFisica().getRfc();
			}else{
				rfc = inputObject.getMoral().getRfc();
			}
			session.removeAttribute("sujetoTramite");
			session.setAttribute("rfcActual", rfc);
			session.removeAttribute("fueFirmada");
			session.removeAttribute("datosFirma");
			session.setAttribute("idSolicitud",idSolicitud);
			super.log.debug("idSolicitud: "+idSolicitud);
			result.put("tipoDocumento", tipoDocumento);
			result.put("mensajeExito", mensaje);
		} catch (GestionPatronalBusinessException e) {
			mensaje = e.getMessage();
			result.put("mensajeError", mensaje);
			e.printStackTrace();
		} 
		
		return result;

	}
}
