package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.FiltroSolicitudDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RolEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.tramite.service.interfaces.TramiteServiceBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Hugo Martinez
 *
 */
@Controller
@RequestMapping(value = "/solicitud")
public class SolicitudController extends AbstractController  {
	
	@Autowired
	TramiteServiceBusinessRemote tramiteService;
	
	@Autowired
	SolicitudServiceBusinessRemote solicitudService;
	
	@Autowired
	SujetoObligadoController sujetoObligadoController;
	
	@Autowired
	AfiliacionController afiliacionController;
	
	@Autowired
	LoginController loginController;
	
	@Autowired
	AfiliacionServiceBusinessRemote afiliacionService;
	
	@Autowired
	RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusiness;
	
	@Autowired
	ClasificacionController clasificacionController;
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 29/08/2012
	 * @param model
	 * @param session
	 * @return forward
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String inicio(Model model, HttpSession session, @ModelAttribute Socio socio) {
		FiltroSolicitud filtroSolicitud = new FiltroSolicitud();
		List<TipoTramite> tiposTramite=tramiteService.listarTipoTramitesPorModulo(ModuloEnum.PATRONES.getCodigo().longValue());
		System.err.println("Tipos de tramite a setear al model: "+tiposTramite.size());
		filtroSolicitud.setListaTipoTramite(tiposTramite);
		System.err.println("Tipos de tramite en el model : "+filtroSolicitud.getListaTipoTramite());
		filtroSolicitud.setRfc(socio.getRfc());
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		boolean isOperador = usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());
		model.addAttribute("isOperador", isOperador);
		if(isOperador)
			filtroSolicitud.setIdSubdelegacion(usuario.getCveIdSubdelegacion());
		model.addAttribute("filtroSolicitud",filtroSolicitud);
		return "filtro.solicitud";
	}
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 26/10/2012
	 * @param model
	 * @param session
	 * @param socio
	 * @return String
	 */
	@RequestMapping(value = "/consultaAvanzadaDeSolicitudes", method = {RequestMethod.GET, RequestMethod.POST})
	public String consultarSolicitudes(Model model, HttpSession session, @ModelAttribute SujetoObligado sujeto) {
		FiltroSolicitud filtroSolicitud = new FiltroSolicitud();
		List<TipoTramite> tiposTramite=tramiteService.listarTipoTramitesPorModulo(ModuloEnum.PATRONES.getCodigo().longValue());
		System.err.println("Tipos de tramite a setear al model: "+tiposTramite.size());
		filtroSolicitud.setListaTipoTramite(tiposTramite);
		System.err.println("Tipos de tramite en el model : "+filtroSolicitud.getListaTipoTramite());
		String rfc="";
		if(sujeto.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			rfc = sujeto.getFisica().getRfc();
		}else if(sujeto.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			rfc = sujeto.getMoral().getRfc();
		}
		
		filtroSolicitud.setRfc(rfc);
		filtroSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo().longValue());
		
		model.addAttribute("filtroSolicitud",filtroSolicitud);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		boolean isOperador = usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());
		model.addAttribute("isOperador", isOperador);
		if(isOperador)
			filtroSolicitud.setIdSubdelegacion(usuario.getCveIdSubdelegacion());
		return "filtro.solicitud";
	}
	
	/**
	 * Lista las solicitudes en base a los filtros proporcionados
	 * en la pantalla de consulta de solicitudes
	 * @UseCase Consultar solicitudes
	 * @author Hugo Martinez
	 * @Date 30/08/2012
	 * @return forward
	 */
	@RequestMapping(value = "/consultarSolicitudes", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DatosSalidaPaginador<Solicitud>  listarSolicitudes(@RequestBody FiltroSolicitudDataTable params, 
			@ModelAttribute FiltroSolicitud filtroSolicitud, Model model, HttpSession session, Locale locale ){
		DatosEntradaPaginador<Solicitud> input = new DatosEntradaPaginador<Solicitud>();
		input.parserArray(params.getAoData());
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		DatosSalidaPaginador<Solicitud> output = null;
		System.out.println("Filtros: "+params.getoForm());
		if(usuario.getPerfilUsuario().getIdPerfilUsuario().equals(RolEnum.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
			|| usuario.getPerfilUsuario().getIdPerfilUsuario().equals(RolEnum.REPRESENTANTE_LEGAL.getCodigo().longValue())	)
			output = solicitudService.listarSolicitudesPorFiltroParaPatron(input, params.getoForm());
		else
			output = solicitudService.listarSolicitudesPorFiltro(input, params.getoForm());
		
		List<Solicitud> solicitudesActualizadas = ajustarDescripcionDeTramites(output.getAaData(),  locale);
		output.setAaData(solicitudesActualizadas);
		System.err.println("Echo: "+input.getsEcho());
		output.setsEcho(input.getsEcho());
		
		return output;
	}
	
	/**
	 * Ajusta la descripción de los mensajes del trámite si es que fueron ratificados
	 * 
	 * 
	 * @author Hugo Martinez
	 * @Date 08/11/2012
	 * @param solicitudesEnProceso
	 * @param locale
	 * @return
	 */
	private List<Solicitud> ajustarDescripcionDeTramites(List<Solicitud> solicitudesEnProceso,  Locale locale){
		List<Solicitud> solicitudesActualizadas = new ArrayList<Solicitud>();
		for(Solicitud solicitud : solicitudesEnProceso){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
			String fecha = sdf.format(solicitud.getFechaSolicitud());
			solicitud.setFechaSolicitudParse(fecha);
			
			String fechaPresentacionSolicitud = solicitud.getFechaPresentacion()!= null ? sdf.format(solicitud.getFechaPresentacion()) : "";
			String fechaConclusionSolicitud = solicitud.getFechaConclusion()!= null ? sdf.format(solicitud.getFechaConclusion()) : "";
			solicitud.setFechaPresentacionParse(fechaPresentacionSolicitud);
			solicitud.setFechaConclusionParse(fechaConclusionSolicitud);
			
			System.err.println("Fecha de solicitud after: "+fecha);
			List<Tramite> tramitesActualizados = new ArrayList<Tramite>();
			for(Tramite tramite : solicitud.getTramites()){
				if(tramite.getFechaPresentacion() != null)
					tramite.setFechaPresentacionParse(sdf.format(tramite.getFechaPresentacion()));
				
				if(tramite.getFechaConclusion() !=null){
					tramite.setFechaConclusionParse(sdf.format(tramite.getFechaConclusion()));
				}
				if(tramite.getFechaPresentacion() == null)
					tramite.setFechaPresentacionParse(sdf.format(solicitud.getFechaSolicitud()));
				
				System.err.println("Es trámite ratificado: "+tramite.getIndRatificado());
				boolean ratificado = tramite.getIndRatificado()!=null ? tramite.getIndRatificado() : false;
				if(ratificado){
					String mensajeRatificacion="";
					if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.denominacion",null,locale);
					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.contacto",null,locale);
					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.representante",null,locale);
					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.socio",null,locale);
					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.acta.constitutiva",null,locale);
					}else if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo())){
						System.err.println("Asignando mensaje de ratificacion");
						mensajeRatificacion=messageSource.getMessage("msg.ratifica.tipo.tramite.registro.sindicato",null,locale);
					}
					tramite.getTipoTramite().setDescripcion(mensajeRatificacion);
					System.err.println("Descripcion tipo de tramite: "+tramite.getTipoTramite().getDescripcion());
				}
				tramitesActualizados.add(tramite);
			}
			
			solicitud.setTramites(tramitesActualizados);
			solicitudesActualizadas.add(solicitud);
		}
		
		return solicitudesActualizadas;
	}
	
	/**
	 * Muestra el detalle se la solicitud seleccionada en el grid de solicitudes
	 * @UseCase Consultar Solicitudes
	 * @author Hugo Martinez
	 * @Date 04/09/2012
	 * @param filtroSolicitud
	 * @param model
	 * @param session
	 * @return Forward String
	 */
	@RequestMapping(value = "/mostrarDetalleSolicitud", method = RequestMethod.POST)
	public String mostrarDetalleSolicitud(@ModelAttribute FiltroSolicitud filtroSolicitud, Model model, HttpSession session  ){
		System.err.println("Filtro Solicitud Form: "+filtroSolicitud);
		System.err.println("Filtro Solicitud Id: "+filtroSolicitud.getIdSolicitud());
		Solicitud solicitud = solicitudService.consultarDetalleSolicitudPorIdentificador(filtroSolicitud.getIdSolicitud());
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		boolean bFisica=false;
		if(solicitud.getSujetoObligado().getFisica() !=null)
			bFisica=true;
		model.addAttribute("solicitud",solicitud);
		model.addAttribute("bFisica",bFisica);
		model.addAttribute("isOperadorIMSS",usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()));
		log.debug("isOpIMSS: "+usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()));
		System.err.println("isOpIMSS: "+usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()));
		session.setAttribute("idSolicitud", solicitud.getSolicitudId());
		session.setAttribute("sujetoObligado", solicitud.getSujetoObligado());
				
		TipoPersonaEnum tipoPersona = null;
		Long idPersona=null;
		if(bFisica){
			tipoPersona=TipoPersonaEnum.FISICA;
			idPersona = solicitud.getSujetoObligado().getFisica().getIdPersona();
		}else{
			tipoPersona=TipoPersonaEnum.MORAL;
			idPersona = solicitud.getSujetoObligado().getMoral().getIdPersona();
		}
		System.err.println("Obteniendo representantes para solicitado por....");
		List<RepresentanteLegal> representantesLegales = representanteLegalServiceBusiness
				.obtenerRepresentantesLegalesConActosAdmonPorPersona(idPersona, tipoPersona);
		model.addAttribute("listaRepresentantesSolicitantes",representantesLegales);
		System.err.println("Finaliza obteniendo representantes para solicitado por...."+representantesLegales);
		
		return "solicitud.detalle";
	}
	
	
	@RequestMapping(value = "/mostrarDocumento", method = {RequestMethod.GET, RequestMethod.POST})
	public void mostrarDocumento(@RequestParam("idSolicitud") Long idSolicitud, 
			@RequestParam("noFolio") String noFolio,
			@RequestParam("tipoDocumento") Integer tipoDocumento,
			Model model, HttpServletResponse response, HttpSession session){
		System.err.println("Invocando generación de solicitud");
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		solicitud = solicitudService.publicarDocumentosDeSolicitud(solicitud);
		String nombreArchivo = "Solicitud_"+noFolio+".pdf";
		byte[] archivo = null;
		
		if(tipoDocumento.equals(TipoDocumentoTramiteEnum.COMPROBANTE.getCodigo())){
			System.err.println("Se imprimirá comrpobante");
			archivo = solicitud.getDocumentoComprobante();
		}else if(tipoDocumento.equals(TipoDocumentoTramiteEnum.ACUSE.getCodigo())){
			System.err.println("Se imprimirá acuse");
			archivo = solicitud.getDocumentoAcuse();
		}
		try {
			if(archivo != null){
				log.debug("EL REPORTE NO ES NULO Y SE DEBE IMPRIMIR y almacenar en la base");
				response.addHeader("Accept-Ranges","bytes");
				response.addHeader("Cache-Control","public");
				response.addHeader("Cache-Control","must-revalidate");
				response.addHeader("Pragma","public");
				response.setContentType("application/pdf");
				response.addHeader("expires","0");
				response.addHeader("Content-disposition", "inline;filename=" + nombreArchivo); 
				response.setContentLength(archivo.length);
				response.getOutputStream().write(archivo);
				response.getOutputStream().close();
			}else{
				System.err.println("No hay documento");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.err.println("Termina generación de documentos de solicitud");
	}
	
	
	@RequestMapping(value = "/validarEdicionTramite", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarEdicionTramite(
			@RequestBody Solicitud inputObject, 
			HttpServletResponse response, HttpSession session, Locale locale) {
		System.err.println("ACTUALIZARE TRAMITE DENOMINACION SOCIAL");
		Map<String, Object> result = new HashMap<String, Object>();
		Integer idEstadoSolicitudAplicar = inputObject.getEstadoSolicitud().getIdEstadoSolicitud();
		System.err.println("Estatus a aplicar.... "+idEstadoSolicitudAplicar);
		
		if(idEstadoSolicitudAplicar.equals(EstadoSolicitudEnum.RECHAZADA.getCodigo())){
			String mensajeExito = construirMensaje("msg.confirma.rechazo", null, locale);
			result.put("mensajeExito", mensajeExito);
			return result;
		}
		
		inputObject = solicitudService.consultarDetalleSolicitudPorIdentificador(inputObject.getSolicitudId());
		System.err.println("Solicitud: "+inputObject);
		inputObject.getEstadoSolicitud().setIdEstadoSolicitud(idEstadoSolicitudAplicar);
		
		if(	idEstadoSolicitudAplicar.equals(EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo())
				|| idEstadoSolicitudAplicar.equals(EstadoSolicitudEnum.EDICION_BACKOFFICE.getCodigo())){
//			TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.obtenerEnumById(inputObject.getTipoSolicitud().getIdTipoSolicitud().intValue());
//			System.err.println("Sujeto Obligado de Solicitud: "+inputObject.getSujetoObligado()); 
//			Solicitud solicitudEnProceso = solicitudService.obtenerSolicitudEnProceso(inputObject.getSujetoObligado(), tipoSolicitud);
//			if(solicitudEnProceso!=null){
//				String mensajeError = construirMensaje("msg.solicitud.proceso.previa", new Object[]{ solicitudEnProceso.getNoFolioSolicitud() }, locale);
//				result.put("mensajeError", mensajeError);
//			}else{
				String mensajeExito = construirMensaje("msg.confirma.edicion", null, locale);
				result.put("mensajeExito", mensajeExito);
//			}
		}
		return result;
	}    
	
	
	@RequestMapping(value = "/actualizarEstatusAPendienteAsignar", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> asignarEstatusPendienteASolicitud(
			@RequestBody Solicitud inputObject,
			@RequestParam("idSolicitudActiva") Long idSolicitud, 
			HttpServletResponse response, HttpSession session, Locale locale) {
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solActiva= solicitudService.consultarDetalleSolicitudPorIdentificador(idSolicitud);
		if(	solActiva.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo())){
			solActiva.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo());
			solicitudService.actualizarEstatus(solActiva);
		}else if(solActiva.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.EDICION_BACKOFFICE.getCodigo())){
			solActiva.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo());
			solicitudService.actualizarEstatus(solActiva);
		}
		return result;
	}
	
	@RequestMapping(value = "/actualizarEstatus", method = RequestMethod.POST)
	public String actualizarEstatus(@ModelAttribute Solicitud solicitud, BindingResult result,
			Model model, HttpServletResponse response, HttpSession session){
		
		Integer idEstadoSolicitudAplicar = solicitud.getEstadoSolicitud().getIdEstadoSolicitud();
		Long idRazonCancelacion = solicitud.getRazonCancelacion()!= null ? solicitud.getRazonCancelacion().getIdRazonCancelacion() : null;
		System.err.println("Razón cancelación: "+idRazonCancelacion);
		System.err.println("Estatus a aplicar.... "+idEstadoSolicitudAplicar);
		solicitud = solicitudService.consultarDetalleSolicitudPorIdentificador(solicitud.getSolicitudId());
		System.err.println("Solicitud: "+solicitud);
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(idEstadoSolicitudAplicar);
		solicitudService.actualizarEstatus(solicitud);
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		if(idEstadoSolicitudAplicar.equals(EstadoSolicitudEnum.RECHAZADA.getCodigo())){
			if(solicitud.getRazonCancelacion() == null)
				solicitud.setRazonCancelacion(new RazonCancelacion());
			solicitud.getRazonCancelacion().setIdRazonCancelacion(idRazonCancelacion);
			solicitud.setSolicitante(usuario);
			solicitudService.actualizarDatosGeneralesDeSolicitud(solicitud);
			return irBusquedaRFC(model, solicitud, response, session);
		}else if(idEstadoSolicitudAplicar.equals(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo())){
			return irBusquedaRFC(model, solicitud, response, session);
		}else if(	idEstadoSolicitudAplicar.equals(EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo())
			|| idEstadoSolicitudAplicar.equals(EstadoSolicitudEnum.EDICION_BACKOFFICE.getCodigo())){
			Socio sujetoOrigen = new Socio();
			sujetoOrigen.setRfc(obtenerRFCDeSolicitud(solicitud));
			model.addAttribute("socio",new Socio());
			Long idPersonaPatronSO = solicitud.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)
					? solicitud.getSujetoObligado().getFisica().getIdPersona() : solicitud.getSujetoObligado().getMoral().getIdPersona();
			session.setAttribute("cveIdPatronSO",idPersonaPatronSO);
			//TODO Cambiar invocacion de carga de centro de trabajo
			if(solicitud.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue())){
				//return afiliacionController.cargarTramiteCentroTrabajo(solicitud.getSujetoObligado(), model, solicitud.getSolicitudId(), session);
				return clasificacionController.inicio(solicitud.getSujetoObligado(), TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.name(), model, solicitud.getSolicitudId().toString(), session);
			}
			
			return afiliacionController.cargarVistaTramite(solicitud.getSujetoObligado(), model, solicitud.getSolicitudId(), response, session);
//			return sujetoObligadoController.buscarSujetoObligadoPorRFC(sujetoOrigen, null, model, session);
		}else if(  idEstadoSolicitudAplicar.equals(EstadoSolicitudEnum.PROCESADA_BACKOFFICE.getCodigo())
				|| idEstadoSolicitudAplicar.equals(EstadoSolicitudEnum.PROCESADA_VENTANILLA.getCodigo())){
				
				try {
					afiliacionService.concluirSolicitud(solicitud.getSolicitudId(), solicitud.getSujetoObligado(), usuario);
				} catch (GestionPatronalBusinessException e) {
					System.err.println("Algo feo pasó");
				}
			
				Socio sujetoOrigen = new Socio();
				sujetoOrigen.setRfc(obtenerRFCDeSolicitud(solicitud));
				model.addAttribute("socio",new Socio());
				//return sujetoObligadoController.buscarSujetoObligadoPorRFC(sujetoOrigen, null, model, session);
				FiltroSolicitud filtro = new FiltroSolicitud();
				filtro.setIdSolicitud(solicitud.getSolicitudId());
				return mostrarDetalleSolicitud(filtro, model, session);
		}
		
		FiltroSolicitud filtro = new FiltroSolicitud();
		filtro.setIdSolicitud(solicitud.getSolicitudId());
		return mostrarDetalleSolicitud(filtro, model, session);
	}
	
	
	@RequestMapping(value = "/concluirSolicitud", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object>  concluirSolicitud(@RequestBody Solicitud solicitud, Model model, 
			HttpServletResponse response, HttpSession session){
		Map<String, Object> result = new HashMap<String, Object>();
		Integer idEstadoSolicitudAplicar = solicitud.getEstadoSolicitud().getIdEstadoSolicitud();
		Long idRolSolicitante = solicitud.getSolicitante().getPerfilUsuario().getIdPerfilUsuario();
		Long idPersonaSolicitante = solicitud.getSolicitante().getFisica().getIdPersona();
		System.err.println("Estatus a aplicar.... "+idEstadoSolicitudAplicar);
		solicitud = solicitudService.consultarDetalleSolicitudPorIdentificador(solicitud.getSolicitudId());
		System.err.println("Solicitud: "+solicitud);
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(idEstadoSolicitudAplicar);
		solicitudService.actualizarEstatus(solicitud);
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		try {
			CodigoRolTemporal rolSolicitante = idRolSolicitante.equals(CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue())
					? CodigoRolTemporal.PATRON_SUJETO_OBLIGADO : CodigoRolTemporal.REPRESENTANTE_LEGAL;
			if(idPersonaSolicitante!=null)
				afiliacionController.gestionarSolicitanteConclusion(session, solicitud.getSujetoObligado(), rolSolicitante, idPersonaSolicitante);
			
			afiliacionService.concluirSolicitud(solicitud.getSolicitudId(), solicitud.getSujetoObligado(), usuario);
		} catch (GestionPatronalBusinessException e) {
			System.err.println("Algo feo pasó");
		}
		
		if(solicitud.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue()))
			result.put("imprimirAvisoClasificacion", true);
		else
			result.put("imprimirAvisoClasificacion", false);
			
		result.put("exito", true);
		return result;
	}
	
	
	@RequestMapping(value = "/validarFolioSolicitud", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object>  validarFolioSolicitud(@RequestBody Solicitud solicitud, Model model, 
			HttpServletResponse response, HttpSession session, Locale locale){
		String mensaje = null;
		
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitudEncntrada = solicitudService.consultarSolicitudPorFolio(solicitud.getNoFolioSolicitud());
		System.err.println("Solicitud encontrada: "+solicitudEncntrada);
		Usuario usuario=(Usuario)session.getAttribute("usuario");
		boolean isOperador = usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());
		if(solicitud.getSujetoObligado()!=null && solicitud.getSujetoObligado().getTipoPersonaFiscal()!=null ){
			String rfc=null;
			if(solicitud.getSujetoObligado().getTipoPersonaFiscal()!=null){
				if(solicitud.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
					rfc=solicitud.getSujetoObligado().getFisica().getRfc();
				else if(solicitud.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL))
					rfc=solicitud.getSujetoObligado().getMoral().getRfc();
			}
			
			if(rfc!=null && rfc!=""){
				if(solicitudEncntrada!=null)
					solicitudEncntrada = solicitudService.consultarDetalleSolicitudPorIdentificador(solicitudEncntrada.getSolicitudId());
				
				if(solicitudEncntrada!= null && solicitudEncntrada.getSujetoObligado()!=null){
					System.err.println("Comenzare a evaluar la solicitud");
					if(solicitud.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
						rfc=solicitud.getSujetoObligado().getFisica().getRfc();
						String rfcSolicitud = solicitudEncntrada.getSujetoObligado().getFisica().getRfc();
						System.err.println("RFC enviado: "+rfc);
						System.err.println("RFC solicitud: "+rfcSolicitud);
						if(!rfcSolicitud.equals(rfc)){
							solicitudEncntrada=null;
							mensaje = messageSource.getMessage("error.solicitud.otro.patron", null, locale);
						}else{
							System.err.println("mismo RFC");
						}
					}else{
						rfc=solicitud.getSujetoObligado().getMoral().getRfc();
						System.err.println("RFC enviado: "+rfc);
						String rfcSolicitud = solicitudEncntrada.getSujetoObligado().getMoral().getRfc();
						System.err.println("RFC solicitud: "+rfcSolicitud);
						if(!rfcSolicitud.equals(rfc)){
							solicitudEncntrada=null;
							mensaje = messageSource.getMessage("error.solicitud.otro.patron", null, locale);
						}else{
							System.err.println("mismo RFC");
						}
					}
				}else{
					mensaje = messageSource.getMessage("error.solicitud.folio.inexistente", null, locale);
					System.err.println("No se encontro solicitud o no tiene sujeto obligado");
				}
			}
		}
		System.err.println("Solicitud: "+solicitudEncntrada);
		if(solicitudEncntrada!=null){
			
			if(!solicitudEncntrada.getTipoSolicitud().getIdTipoSolicitud().equals(
					TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor().longValue())){
				if(isOperador && usuario.getCveIdSubdelegacion()!=null && solicitudEncntrada.getSubdelegacion()!=null 
						&& solicitudEncntrada.getSubdelegacion().getId()!=null
						&& !solicitudEncntrada.getSubdelegacion().getId().equals(usuario.getCveIdSubdelegacion())){
					mensaje = messageSource.getMessage("error.solicitud.otra.subdelegacion", null, locale);
					result.put("exito", false);
					result.put("error", true);
					result.put("mensajeError", mensaje);
					result.put("idSolicitud", solicitudEncntrada.getSolicitudId());
					return result;
				}
			}
			result.put("idSolicitud", solicitudEncntrada.getSolicitudId());
			result.put("exito", true);
			result.put("error", false);
		}else{
			mensaje = messageSource.getMessage("error.solicitud.folio.inexistente", null, locale);
			result.put("exito", false);
			result.put("error", true);
			result.put("mensajeError", mensaje);
		}
		return result;
	}
	
	
	@RequestMapping(value = "/mostrarBusquedaRfc", method = RequestMethod.POST)
	public String irBusquedaRFC(Model model, @ModelAttribute Solicitud solicitud, HttpServletResponse response, HttpSession session){
		
		Socio sujetoOrigen = new Socio();
		String rfc = solicitud.getSujetoObligado().getFisica()!=null ? 
				solicitud.getSujetoObligado().getFisica().getRfc() :
				solicitud.getSujetoObligado().getMoral().getRfc();
		sujetoOrigen.setRfc(rfc);
		return sujetoObligadoController.buscarSujetoObligadoPorRFC(sujetoOrigen, null, model, session);
//		return "buscar.patron.rfc";
	}
	
	private String obtenerRFCDeSolicitud(Solicitud solicitud){
		SujetoObligado sujeto = solicitud.getSujetoObligado();
		if(sujeto.getFisica()!= null){
			return sujeto.getFisica().getRfc();
		}else if(sujeto.getMoral()!= null){
			return sujeto.getMoral().getRfc();
		}
		
		return null;
	}
	
	private String construirMensaje(String msgCode, Object[] parametros, Locale locale){
		StringBuffer msg=new StringBuffer();
		msg.append(messageSource.getMessage(msgCode,parametros,locale));
		return msg.toString();
	}
}
