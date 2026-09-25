package mx.gob.imss.ctirss.delta.gestion.solicitud.visor.web.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TechnicalPersistenceException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.visor.web.controller.pagination.FiltroSolicitudDataTable;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite32D;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramitePersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRiss;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.service.interfaces.ISelectService;
import mx.gob.imss.ctirss.delta.tramite.service.interfaces.TramiteServiceBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/portlet/solicitudes")
public class PortletSolicitudesController extends AbstractController {

	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;
	@Autowired
	private ISelectService componentComboService;
	@Autowired
	private TramiteServiceBusinessRemote tramiteServiceBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	
	private String FROM_REGRESAR = "FROM_REGRESAR";
	private String FILTROS_SESSION = "FILTROS_SESSION";

	@RequestMapping(value = "/{fromRegresar}", method = RequestMethod.GET)
	public String initSolicitudessPortlet(Model model,
			HttpServletRequest request, @PathVariable boolean fromRegresar) {

		this.log.debug("Es regresar -> " + fromRegresar);

		request.setAttribute(FROM_REGRESAR, fromRegresar);

		return "portletSolicitudesInit";
	}

	@RequestMapping(value = "/resumen/{fromRegresar}")
	public String obtenerSolicitudes(HttpSession session,
			HttpServletRequest request, Model model,
			@PathVariable boolean fromRegresar) {

		FiltroSolicitud filtroSolicitud = null;

		if (fromRegresar) {
			filtroSolicitud = (FiltroSolicitud) session
					.getAttribute(FILTROS_SESSION);

			/*
			 * En caso de que el regresar se haya dado a partir de una búsqueda
			 * directa a un folio
			 */
			if (filtroSolicitud == null) {
				filtroSolicitud = new FiltroSolicitud();
			}
		} else {
			filtroSolicitud = new FiltroSolicitud();
			session.removeAttribute(FILTROS_SESSION);
		}

		Usuario usuario = (Usuario) session.getAttribute("usuario");
		List<Modulo> listModulosUsuario = usuario.getUsuarioFuncionario()
				.getModulo();
		
		if (listModulosUsuario != null && !listModulosUsuario.isEmpty()) {
			filtroSolicitud.setListaModulos(listModulosUsuario);
		}
		
		List<TipoTramite> lstTipoTramite = this.tramiteServiceBusiness
				.getTramitesByModulos(listModulosUsuario);
		
		model.addAttribute("lstTipoTramite", lstTipoTramite);

		if (usuario.getUsuarioFuncionario().getDelegacion() != null
				|| usuario.getUsuarioFuncionario().getSubdelegacion() != null) {

			List<SelectBean> delegaciones = null;
			List<SelectBean> subdelegaciones = null;

			if (usuario.getUsuarioFuncionario().getDelegacion() != null
					&& usuario.getUsuarioFuncionario().getDelegacion().getId() != null) {
				filtroSolicitud.setIdDelegacion(usuario.getUsuarioFuncionario()
						.getDelegacion().getId());
				request.setAttribute("BLOCK_DELEG", true);
			}

			if (usuario.getUsuarioFuncionario().getSubdelegacion() != null
					&& usuario.getUsuarioFuncionario().getSubdelegacion()
							.getId() != null) {
				filtroSolicitud.setIdSubdelegacion(usuario
						.getUsuarioFuncionario().getSubdelegacion().getId());
				request.setAttribute("BLOCK_SUBDELEG", true);
			}

			try {
				delegaciones = this.componentComboService
						.getOptions("mx.gob.imss.ctirss.delta.persistence.DicDelegacion");
				subdelegaciones = this.componentComboService
						.getOptions(
								"mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion",
								"dicDelegacion.cveIdDelegacion",
								filtroSolicitud.getIdDelegacion().toString());
			} catch (TechnicalPersistenceException e) {
				this.log.error(e);
			}

			model.addAttribute("delegacionesAux", delegaciones);
			model.addAttribute("subdelegacionesAux", subdelegaciones);
		}

		model.addAttribute("filtroSolicitud", filtroSolicitud);
		model.addAttribute("solicitud", new Solicitud());
		request.setAttribute(FROM_REGRESAR, fromRegresar);

		return "portletSolicitudesContenido";
	}

	/**
	 * Lista las solicitudes en base a los filtros proporcionados en la pantalla
	 * de consulta de solicitudes
	 * 
	 * @return forward
	 */
	@RequestMapping(value = "/consultarSolicitudes", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	DatosSalidaPaginador<Solicitud> listarSolicitudes(
			@RequestBody FiltroSolicitudDataTable params,
			@ModelAttribute FiltroSolicitud filtroSolicitud, Model model,
			HttpSession session, Locale locale) {

		DatosEntradaPaginador<Solicitud> input = new DatosEntradaPaginador<Solicitud>();
		input.parserArray(params.getAoData());
		DatosSalidaPaginador<Solicitud> output = null;

		Usuario usuario = (Usuario) session.getAttribute("usuario");

		String roles[] = {session.getServletContext().getInitParameter("ROL_JAC")};
		boolean mostrarSolicInternet = checkGrantedAuthorities(roles);
				
		List<Modulo> listModulosUsuario = usuario.getUsuarioFuncionario()
				.getModulo();
		
		if (listModulosUsuario != null && !listModulosUsuario.isEmpty()) {
			this.log.debug("entre a poner los sistemas");
			filtroSolicitud.setListaModulos(listModulosUsuario);
			params.getoForm().setListaModulos(listModulosUsuario);

		}
		
		this.log.debug("Filtros: " + params.getoForm());

		output = solicitudBusiness.listarSolicitudesPorFiltro(input,
				params.getoForm(), mostrarSolicInternet);

		List<Solicitud> solicitudesActualizadas = ajustarDescripcionDeTramites(
				output.getAaData(), locale);
		output.setAaData(solicitudesActualizadas);

		this.log.debug("Echo: " + input.getsEcho());

		output.setsEcho(input.getsEcho());

		/*
		 * Se suben a la sesion los filtros utilizados para la funcionalidad del
		 * botón regresar
		 */
		session.setAttribute(FILTROS_SESSION, params.getoForm());

		return output;
	}

	/**
	 * Muestra el detalle se la solicitud seleccionada
	 * 
	 * @param filtroSolicitud
	 * @param model
	 * @param session
	 * @return Forward String
	 */
	@RequestMapping(value = "/detalle", method = RequestMethod.POST)
	public String mostrarDetalleSolicitud(@ModelAttribute Solicitud solicitud,
			Model model, HttpSession session, HttpServletRequest request) {

		this.log.debug("Filtro Folio Solicitud: "
				+ solicitud.getNoFolioSolicitud());

		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);

		model.addAttribute("idPersona", usuariosso.getIdPersona());

		try {
			mostrarDetalleSolicitudCommon(solicitud.getNoFolioSolicitud(),
					model, session, request);
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("solicitud", solicitud);
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("solicitud", solicitud);
		}

		return "portletSolicitudesDetalle";
	}

	/**
	 * Muestra el detalle se la solicitud seleccionada
	 * 
	 * @param filtroSolicitud
	 * @param model
	 * @param session
	 * @return Forward String
	 */
	@RequestMapping(value = "/detalle/{folioSolicitud}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String mostrarDetalleSolicitud(@PathVariable String folioSolicitud,
			Model model, HttpSession session, HttpServletRequest request) {

		this.log.debug("Filtro Folio Solicitud: " + folioSolicitud);

		try {
			mostrarDetalleSolicitudCommon(folioSolicitud, model, session,
					request);
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			Solicitud solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folioSolicitud);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("solicitud", solicitud);
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			Solicitud solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folioSolicitud);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("solicitud", solicitud);
		}

		return "portletSolicitudesDetalleSimple";
	}
	
	/**
	 * Muestra el detalle se la solicitud seleccionada
	 * 
	 * @param filtroSolicitud
	 * @param model
	 * @param session
	 * @return Forward String
	 */
	@RequestMapping(value = "/detalleSolicitudCorreo/{folioSolicitud}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String mostrarDetalleSolicitudCorreo(@PathVariable String folioSolicitud,
			Model model, HttpSession session, HttpServletRequest request) {

		this.log.debug("el folio de la solicitud a autorizar es: " + folioSolicitud);
		this.log.debug("Mostrara el detalle de la solicitud por autorizar" );

		try {
			
			Solicitud solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folioSolicitud);
			
			log.debug("el folio de la solicitud es: " + solicitud.getNoFolioSolicitud());
			
			solicitud = solicitudBusiness.consultarFolio(solicitud);
			
			TramiteActualizacionCorreo tramite = (TramiteActualizacionCorreo) solicitud.getTramites().get(0);
			
			
			this.log.debug("la solicitud consultada es: " + solicitud);
			this.log.debug("el tramite de la solicitud consultada es: " + tramite);
			
			model.addAttribute("solicitud", solicitud);
			model.addAttribute("tramite", tramite);

			
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			Solicitud solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folioSolicitud);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("solicitud", solicitud);
		} 

		return "portletSolicitudesCorreo";
	}

	@RequestMapping(value = "/mostrarDocumentoResultante", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void mostrarDocumentoResultante(
			@RequestParam("idSolicitud") String idSolicitudHashed,
			@RequestParam("idTramite") String idTramiteHashed,
			@RequestParam("tipoDocumento") String tipoDocumentoHashed,
			Model model, HttpServletResponse response, HttpSession session) {

		byte[] archivo = null;

		Long idSolicitud = null;
		Long idTramite = null;
		Integer tipoDocumento = null;

		try {

			idSolicitud = Long.valueOf(Base64Cipher
					.descrifrar(idSolicitudHashed));
			idTramite = Long.valueOf(Base64Cipher.descrifrar(idTramiteHashed));
			tipoDocumento = Integer.valueOf(Base64Cipher
					.descrifrar(tipoDocumentoHashed));
		} catch (NumberFormatException e) {
			this.log.error(e);
		} catch (InvalidKeyException e) {
			this.log.error(e);
		} catch (IllegalBlockSizeException e) {
			this.log.error(e);
		} catch (BadPaddingException e) {
			this.log.error(e);
		} catch (IOException e) {
			this.log.error(e);
		}

		String nombreArchivo = "Solicitud_" + idTramite + ".pdf";
		archivo = solicitudBusiness.obtenerDocumentoResultante(idSolicitud,
				idTramite, tipoDocumento);

		try {
			if (archivo != null) {
				log.debug("El documento no es nulo");

				response.addHeader("Accept-Ranges", "bytes");
				response.addHeader("Cache-Control", "public");
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.addHeader("expires", "0");
				response.addHeader("Content-disposition", "inline;filename="
						+ nombreArchivo);
				response.setContentLength(archivo.length);
				response.getOutputStream().write(archivo);
				response.getOutputStream().close();
			} else {
				this.log.debug("No hay documento");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.log.debug("Termina generación de documentos de solicitud");
	}
	
	@RequestMapping(value = "/mostrarDocumentoResultanteCorreo", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void mostrarDocumentoResultanteCorreo(
			@RequestParam("idSolicitud") String idSolicitudHashed,
			@RequestParam("idTramite") String idTramiteHashed,
			@RequestParam("accion") String accion,
			@RequestParam("motivo") String motivo,
			Model model, HttpServletResponse response, HttpSession session) throws SolicitudNoEncontradaException{
		
		log.debug("llego al controller de obtencion de correo electronico");
		log.debug("la accion es: " + accion);
		log.debug("el motivo es: " + motivo);
		byte[] archivo = null;

		Long idSolicitud = null;
		Long idTramite = null;
		Integer tipoDocumento = DocumentoPorTipoEnum.ACUSE_ACTUALIIZACION_CORREO_ELECTRONICO.getId().intValue() ;

		try {

			idSolicitud = Long.valueOf(Base64Cipher
					.descrifrar(idSolicitudHashed));
			idTramite = Long.valueOf(Base64Cipher.descrifrar(idTramiteHashed));

		} catch (NumberFormatException e) {
			this.log.error(e);
		} catch (InvalidKeyException e) {
			this.log.error(e);
		} catch (IllegalBlockSizeException e) {
			this.log.error(e);
		} catch (BadPaddingException e) {
			this.log.error(e);
		} catch (IOException e) {
			this.log.error(e);
		}
		
		log.debug("el idsolicitud es: " + idSolicitud);
		log.debug("el idTramite es: " + idTramite);
		log.debug("el tipoDocumento es: " + tipoDocumento);

		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		
		try {
			solicitud = solicitudBusiness.consultar(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			super.log.error("No se encontro la solicitud");
			e.printStackTrace();
		}
		
		this.log.debug("la solicitud consultada es: " + solicitud);
		TramiteActualizacionCorreo tramite = (TramiteActualizacionCorreo) solicitud.getTramites().get(0);
		
		if(accion.equalsIgnoreCase("APROBAR")){
			try {
				solicitudBusiness.actualizaAConcluida(solicitud);
				this.log.debug("la solicitud se ha actualizado correctamente");

			} catch (SolicitudNoEncontradaException e) {
				this.log.error(e);
				solicitud.setNoFolioSolicitud(idSolicitud.toString());
				solicitud.setErrorFormGeneral(e.getMessage());
				model.addAttribute("solicitud", solicitud);
			} 
			
		}else{
			
			try {
				
				solicitudBusiness.cancelarSolicitud(idSolicitud, null , null , null, motivo);
				this.log.debug("la solicitud se ha cancelado correctamente");

			} catch (SolicitudException e) {
				this.log.error(e);
				solicitud.setNoFolioSolicitud(idSolicitud.toString());
				solicitud.setErrorFormGeneral(e.getMessage());
				model.addAttribute("solicitud", solicitud);
			} 
			
		}
		
		String nombreArchivo = "Solicitud_" + idTramite + ".pdf";
		archivo = solicitudBusiness.obtenerDocumentoResultante(idSolicitud,
				idTramite, tipoDocumento);

		try {
			if (archivo != null) {
				log.debug("El documento no es nulo");

				response.addHeader("Accept-Ranges", "bytes");
				response.addHeader("Cache-Control", "public");
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.addHeader("expires", "0");
				response.addHeader("Content-disposition", "inline;filename="
						+ nombreArchivo);
				response.setContentLength(archivo.length);
				response.getOutputStream().write(archivo);
				response.getOutputStream().close();
			} else {
				this.log.debug("No hay documento");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.log.debug("Termina generación de documentos de solicitud");
		
		
	}

	@RequestMapping(value = "/mostrarDocumento", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void mostrarDocumento(@RequestParam("idSolicitud") Long idSolicitud,
			@RequestParam("noFolio") String noFolio,
			@RequestParam("tipoDocumento") Integer tipoDocumento, Model model,
			HttpServletResponse response, HttpSession session) {

		this.log.debug("Invocando generación de solicitud");

		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		// solicitud =
		// solicitudServiceBusiness.publicarDocumentosDeSolicitud(solicitud);

		String nombreArchivo = "Solicitud_" + noFolio + ".pdf";

		byte[] archivo = null;

		if (tipoDocumento.equals(TipoDocumentoTramiteEnum.COMPROBANTE
				.getCodigo())) {
			this.log.debug("Se imprimirá comrpobante");
			archivo = solicitudBusiness.obtenerDocumento(idSolicitud,
					tipoDocumento);
			// archivo = solicitud.getDocumentoComprobante();
		} else if (tipoDocumento.equals(TipoDocumentoTramiteEnum.ACUSE
				.getCodigo())) {
			this.log.debug("Se imprimirá acuse");
			archivo = solicitudBusiness.obtenerDocumento(idSolicitud,
					tipoDocumento);
			// archivo = solicitud.getDocumentoAcuse();
		}

		try {
			if (archivo != null) {
				log.debug("El documento no es nulo");

				response.addHeader("Accept-Ranges", "bytes");
				response.addHeader("Cache-Control", "public");
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.addHeader("expires", "0");
				response.addHeader("Content-disposition", "inline;filename="
						+ nombreArchivo);
				response.setContentLength(archivo.length);
				response.getOutputStream().write(archivo);
				response.getOutputStream().close();
			} else {
				this.log.debug("No hay documento");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.log.debug("Termina generación de documentos de solicitud");
	}

	/**
	 * Ajusta la descripción de los mensajes del trámite si es que fueron
	 * ratificados
	 * 
	 * @param solicitudesEnProceso
	 * @param locale
	 * @return
	 */
	private List<Solicitud> ajustarDescripcionDeTramites(
			List<Solicitud> solicitudesEnProceso, Locale locale) {
		List<Solicitud> solicitudesActualizadas = new ArrayList<Solicitud>();
		for (Solicitud solicitud : solicitudesEnProceso) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");

			String fecha = solicitud.getFechaSolicitud() != null ? sdf
					.format(solicitud.getFechaSolicitud()) : "";
			solicitud.setFechaSolicitudParse(fecha);

			String fechaPresentacionSolicitud = solicitud
					.getFechaPresentacion() != null ? sdf.format(solicitud
					.getFechaPresentacion()) : "";
			solicitud.setFechaPresentacionParse(fechaPresentacionSolicitud);

			String fechaConclusionSolicitud = solicitud.getFechaConclusion() != null ? sdf
					.format(solicitud.getFechaConclusion()) : "";
			solicitud.setFechaConclusionParse(fechaConclusionSolicitud);

			List<Tramite> tramitesActualizados = new ArrayList<Tramite>();
			for (Tramite tramite : solicitud.getTramites()) {

				if (tramite.getFechaPresentacion() != null) {
					tramite.setFechaPresentacionParse(sdf.format(tramite
							.getFechaPresentacion()));
				}

				if (tramite.getFechaConclusion() != null) {
					tramite.setFechaConclusionParse(sdf.format(tramite
							.getFechaConclusion()));
				}

				if (tramite.getFechaPresentacion() != null) {
					tramite.setFechaPresentacionParse(sdf.format(solicitud
							.getFechaSolicitud()));

				}

				tramitesActualizados.add(tramite);
			}

			solicitud.setTramites(tramitesActualizados);
			solicitudesActualizadas.add(solicitud);
		}

		return solicitudesActualizadas;
	}

	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody
	MedioContacto limpiarElementosSesion(final HttpSession session) {

		session.removeAttribute("idSolicitud");
		session.removeAttribute("sujetoObligado");
		session.removeAttribute(FILTROS_SESSION);

		return null;
	}

	private void mostrarDetalleSolicitudCommon(String folioSolicitud,
			Model model, HttpSession session, HttpServletRequest request)
			throws SolicitudNoEncontradaException, SolicitudNoValidaException {

		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioSolicitud);
		
		solicitud = solicitudBusiness.consultarFolio(solicitud);

		/*
		 * Se valida que la solicitud encontrada corresponda con la
		 * subdelegación del usuario firmado
		 */
		if (usuario != null
				&& usuario.getUsuarioFuncionario() != null
				&& usuario.getUsuarioFuncionario().getSubdelegacion() != null
				&& usuario.getUsuarioFuncionario().getSubdelegacion().getId() != null
				&& solicitud.getSubdelegacion() != null
				&& solicitud.getSubdelegacion().getId() != null) {

			Subdelegacion subDelegUsuario = usuario.getUsuarioFuncionario()
					.getSubdelegacion();
			Subdelegacion subDelegSolicitud = solicitud.getSubdelegacion();

			if (!subDelegUsuario.getId().equals(subDelegSolicitud.getId())) {
				throw new SolicitudNoValidaException("La solicitud con folio "
						+ folioSolicitud
						+ " no corresponde a la subdelegación del usuario");
			}
		}

		/*
		 * Solo cuando la solicitud tenga estado de ATENDIDA será cuando se
		 * muestre el detalle de la persona
		 */
		if (solicitud.getEstadoSolicitud().getIdEstadoSolicitud()
				.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
			SujetoObligado sujOblig = obtenerSujObligTramites(solicitud
					.getTramites());

			this.log.debug("Sujeto Obligado para mostrar en el detalle de la solicitud ("
					+ solicitud.getNoFolioSolicitud() + ") -> " + sujOblig);

			solicitud.setSujetoObligado(sujOblig);

			TipoPersonaEnum tipoPersona = null;
			Long idPersona = null;
			Long idTipoPersona = null;

			if (sujOblig != null && sujOblig.getFisica() != null) {
				tipoPersona = TipoPersonaEnum.FISICA;
				idPersona = sujOblig.getFisica().getIdPersona();
				idTipoPersona = tipoPersona.getId();
			} else if (sujOblig != null && sujOblig.getMoral() != null) {
				tipoPersona = TipoPersonaEnum.MORAL;
				idPersona = sujOblig.getMoral().getIdPersona();
				idTipoPersona = tipoPersona.getId();
			} else {
				this.log.warn("La Solicitud " + folioSolicitud
						+ " no tiene persona asociada");
			}

			request.setAttribute("idPersonaWidget", idPersona);
			request.setAttribute("idTipoPersonaWidget", idTipoPersona);
		}
		
		/*
		 * Se checa la longitud del NRP, para sabes si se tiene que concatenar
		 * la modalidad y/o dígito verificador
		 */
		if (solicitud.getSujetoObligado() != null && 
				StringUtils.isNotBlank(solicitud.getSujetoObligado().getNumeroRegistroPatronal())) {
			String nrp = solicitud.getSujetoObligado().getNumeroRegistroPatronal();
			
			if (nrp.length() == 8) {
				// Falta modalidad y dígito verificador
				nrp += solicitud.getSujetoObligado().getModalidad().getNumModalidad();
				nrp += solicitud.getSujetoObligado().getDigVerificador();
				solicitud.getSujetoObligado().setNumeroRegistroPatronal(nrp);
			} else if (nrp.length() == 10) {
				// Falta dígito verificador
				nrp += solicitud.getSujetoObligado().getDigVerificador();
				solicitud.getSujetoObligado().setNumeroRegistroPatronal(nrp);
			}
		}
		
		model.addAttribute("solicitud", solicitud);
		request.setAttribute("idSolicitud", solicitud.getSolicitudId());

	}

	@RequestMapping(value = "/validar/folio", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> validarDomicilio(@RequestParam String folio,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();

		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folio);

		boolean existeSolicitud;

		try {
			existeSolicitud = this.solicitudBusiness.existeSolicitud(solicitud);

			result.put("EXISTE_SOLIC", existeSolicitud);

			if (!existeSolicitud) {
				result.put("MSG_ERROR", new SolicitudNoEncontradaException(
						folio).getMessage());
			}

		} catch (SolicitudException e) {
			this.log.error(e);
		}

		return result;
	}
	
	private SujetoObligado obtenerSujObligTramites(List<Tramite> tramites){
		
		SujetoObligado sujOblig = null;
		
		/*
		 * Se recorren los trámites de la solicitud para obtener la persona
		 * física o moral, para fines prácticos se agrupa en el atributo
		 * sujetoObligado, para no estar recorriendo trámites en la vista
		 */
		for (Tramite tramite : tramites) {
			if (tramite instanceof TramiteFisica) {
				sujOblig = new SujetoObligado();
				sujOblig.setFisica(((TramiteFisica)tramite).getFisica());
				break;
			} else if (tramite instanceof TramiteMoral) {
				sujOblig = new SujetoObligado();
				sujOblig.setMoral(((TramiteMoral)tramite).getMoral());
				break;
			} else if (tramite instanceof TramiteAsegurado) {
				sujOblig = new SujetoObligado();
				sujOblig.setFisica(((TramiteAsegurado)tramite).getFisica());
				break;
			} else if (tramite instanceof TramiteSujetoObligado) {
				sujOblig = ((TramiteSujetoObligado)tramite).getSujetoObligado();
				break;
			} else if (tramite instanceof TramiteRiss) {
				sujOblig = new SujetoObligado();
				TramiteRiss tramiteRiss = (TramiteRiss) tramite;
				Fisica fisica = tramiteRiss.getFisica();
				
				if (fisica != null) {
					sujOblig.setFisica(fisica);
				} else if (!CollectionUtils.isEmpty(tramiteRiss.getListaCveIdSujetosObligados())){
					/*
					 * Se toma la primera posición ya que para este tramite los sujetos
					 * obligados pertenecen a la misma persona
					 */
					sujOblig.setCveIdSujetoObligado(tramiteRiss.getListaCveIdSujetosObligados().get(0));
					sujOblig.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
					sujOblig = this.sujetoObligadoServiceBusiness.obtenerDetalleRP(sujOblig);
				}
				
				break;
			} else if (tramite instanceof TramitePersonaAutorizada) {
				sujOblig = new SujetoObligado();
				TramitePersonaAutorizada tramitePersonaAut = (TramitePersonaAutorizada) tramite;
				
				if (tramitePersonaAut.getPersonaFisica() != null) {
					sujOblig.setFisica(tramitePersonaAut.getPersonaFisica());
				} else if (tramitePersonaAut.getPersonaMoral() != null) {
					sujOblig.setMoral(tramitePersonaAut.getPersonaMoral());
				}
				
				break;
			} else if (tramite instanceof TramiteRepresentanteLegal) {
				sujOblig = new SujetoObligado();
				sujOblig.setFisica(((TramiteRepresentanteLegal)tramite).getFisica());
				
				break;
			} else if (tramite instanceof Tramite32D) {
				sujOblig = new SujetoObligado();
				Tramite32D tramite32d = (Tramite32D) tramite;
				
				if (tramite32d.getPersonaFM() != null) {
					if (tramite32d.getPersonaFM() instanceof Fisica) {
						sujOblig.setFisica((Fisica)tramite32d.getPersonaFM());
					} else {
						sujOblig.setMoral((Moral)tramite32d.getPersonaFM());
					}
				} 
				break;
			}
		}
		
		return sujOblig;
	}
	
}
