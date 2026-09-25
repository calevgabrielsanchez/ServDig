package mx.gob.imss.cdsss.delta.portal.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cdsss.delta.portal.utils.Constants;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.UmfDomicilioDTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Ciudadano;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/solicitud")
public class DetalleSolicitudController extends AbstractController {
	private static final String TRAMITE = "tramite";

	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;
	@Autowired
	private UmfServiceRemote umfService;
	@Autowired
	private EmailServiceRemote emailServiceRemote;
	
	@RequestMapping("/finalizada")
	public String detalleFinalizarSolicitud(Model model, HttpSession session) {
		String folioSolicitud = (String) session.getAttribute(Constants.KEY_FOLIO_SOLICITUD);
		Solicitud solicitud = (Solicitud) session.getAttribute(Constants.KEY_SOLICITUD);

		if (solicitud == null || StringUtils.isBlank(solicitud.getNoFolioSolicitud())
				|| !solicitud.getNoFolioSolicitud().equals(folioSolicitud)) {
			session.removeAttribute(Constants.KEY_DATOS_DOM_UMF);
			session.removeAttribute(Constants.KEY_LONGITUD_UMF);
			session.removeAttribute(Constants.KEY_LATITUD_UMF);
			session.removeAttribute(Constants.KEY_DOCUMENTO_TRAMITE);

			solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folioSolicitud);

			try {
				solicitud = solicitudBusiness.consultarFolio(solicitud);
			} catch (SolicitudNoEncontradaException e) {
				log.error(e);
				return procesarError(model, e.getMessage());
			}
		}

		if(solicitud.getTramites().get(0).getDocumentoPorTipos() == null) {
			log.debug("Se agregan la lista de documentos a los tramites de la solicitud");
			 solicitud = solicitudBusiness.agregarListadosDocumentosATramites(solicitud);
		}
		
		session.setAttribute(Constants.KEY_SOLICITUD, solicitud);
		UmfDomicilioDTO umfDomicilio = (UmfDomicilioDTO) session.getAttribute(Constants.KEY_DATOS_DOM_UMF);

		InstanceofPredicate tramiteRegDhabPredicate = new InstanceofPredicate(TramiteRegistroDerechohabiente.class);
		InstanceofPredicate tramiteCorrDhabPredicate = new InstanceofPredicate(TramiteCorreccionDerechohabiente.class);
		Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramiteRegDhabPredicate);
		Object tramiteModifInicial = CollectionUtils.find(solicitud.getTramites(), tramiteCorrDhabPredicate);
		String homoclave = null;

		UmfDomicilioDTO umfDomicilioAsociado = new UmfDomicilioDTO();
		if (tramiteInicial != null) {
			TramiteRegistroDerechohabiente tramiteRegistro = (TramiteRegistroDerechohabiente) tramiteInicial;
			homoclave = tramiteRegistro.getTipoTramite().getHomoclave();
			Integer tipoTramite = tramiteRegistro.getTipoTramite().getIdTipoTramite();
			umfDomicilioAsociado.setMedicoEnTurno(tramiteRegistro.getMedicoEnTurno());
			if(tipoTramite.equals(TipoTramiteEnum.REGISTRO_HIJOS.getCodigo())) {
				model.addAttribute("tituloTramite", "derechohabientes.tramite.registroHijos.title");
			} else {
				model.addAttribute("tituloTramite", "derechohabientes.tramite.registro.ciudadano");
			}
			session.setAttribute(TRAMITE, "registro");
		} else if(tramiteModifInicial != null) {
			TramiteCorreccionDerechohabiente tramiteCorreccion = (TramiteCorreccionDerechohabiente) tramiteModifInicial;
			homoclave = tramiteCorreccion.getTipoTramite().getHomoclave();
			umfDomicilioAsociado.setMedicoEnTurno(tramiteCorreccion.getMedicoEnTurno());
			model.addAttribute("tituloTramite", "derechohabientes.tramite.cambioClinica.title");
			session.setAttribute(TRAMITE, "cambioClinica");
		}

		session.setAttribute(Constants.KEY_HOMOCLAVE, homoclave);
		if (umfDomicilio == null || umfDomicilio.getMedicoEnTurno() == null
				|| umfDomicilio.getMedicoEnTurno().getUnidadMedicaFamiliar() == null
				|| umfDomicilio.getMedicoEnTurno().getConsultorio() == null
				|| umfDomicilio.getMedicoEnTurno().getTurno() == null) {
			if (tramiteInicial != null || tramiteModifInicial != null) {
				umfDomicilio = umfDomicilioAsociado;
			} else {
				return procesarError(model, "La solicitud no tiene asoicado ningun tramite del tipo solicitado");
			}
		}

		session.setAttribute(Constants.KEY_DATOS_DOM_UMF, umfDomicilio);
		BigDecimal latitud = umfDomicilio.getMedicoEnTurno().getUnidadMedicaFamiliar().getLatitud();
		BigDecimal longitud = umfDomicilio.getMedicoEnTurno().getUnidadMedicaFamiliar().getLongitud();
		if (latitud != null && longitud != null) {
			session.setAttribute(Constants.KEY_LONGITUD_UMF, longitud);
			session.setAttribute(Constants.KEY_LATITUD_UMF, latitud);
		} else {
			session.setAttribute(Constants.KEY_LONGITUD_UMF, "SIN_UBICACION");
			session.setAttribute(Constants.KEY_LATITUD_UMF, "SIN_UBICACION");
		}

		// Datos de umf (si no se encuentran en sesion)
		UnidadMedicaFamiliar umf = umfDomicilio.getMedicoEnTurno().getUnidadMedicaFamiliar();
		if (StringUtils.isBlank(umf.getDesDireccion()) || StringUtils.isBlank(umf.getDescripcion())) {
			try {
				UnidadMedicaFamiliar umfLocalizada = umfService.getUnidadMedicaFamiliarById(umf.getIdUMF());
				umf.setDesDireccion(umfLocalizada.getDesDireccion());
				umf.setDescripcion(umfLocalizada.getDescripcion());
			} catch (DerechohabientesBusinessException e) {
				umf.setDesDireccion("No disponible");
				umf.setDescripcion("No disponible");
			}
		}
		
		Turno turno = umfDomicilio.getMedicoEnTurno().getTurno();
		if(StringUtils.isBlank(turno.getDescripcion())){
			try {
				List<Turno> listTurnos = umfService.getTurnosDisponiblesPorUmf(umf.getIdUMF());
				for (Turno turnoDisponible : listTurnos) {
					if (turnoDisponible.getIdTurno().equals(turno.getIdTurno())) {
						turno.setDescripcion(turnoDisponible.getDescripcion());
					}
				}
			} catch (DerechohabientesBusinessException e) {
				turno.setDescripcion("No disponible");
			}
		}

		return Constants.KEY_VIEW_FINALIZAR_SOLICITUD;
	}

	@RequestMapping("/finalizada/{folioSolicitud}")
	public String detalleSolicitudPorFolio(Model model, HttpSession session, 
			@PathVariable String folioSolicitud) {
		Ciudadano ciudadanoSesion = (Ciudadano)session.getAttribute(Constants.KEY_CIUDADANO_SESSION);
		if(ciudadanoSesion == null) {
			throw new RuntimeException("No existe la sesi\u00f3n del usuario");			
		}
		session.setAttribute(Constants.KEY_FOLIO_SOLICITUD, folioSolicitud);
		
		return detalleFinalizarSolicitud(model, session);
	}

	@RequestMapping("/finalizada/id/{idSolicitud}")
	public String detalleSolicitudPorId(Model model, HttpSession session, 
			@PathVariable Long idSolicitud) {
		Ciudadano ciudadanoSesion = (Ciudadano)session.getAttribute(Constants.KEY_CIUDADANO_SESSION);
		if(ciudadanoSesion == null) {
			throw new RuntimeException("No existe la sesi\u00f3n del usuario");			
		}
		
		Solicitud solicitudActiva = new Solicitud();
		solicitudActiva.setSolicitudId(idSolicitud);

		try {
			solicitudActiva = solicitudBusiness.consultar(solicitudActiva);

			session.setAttribute(Constants.KEY_SOLICITUD, solicitudActiva);
			session.setAttribute(Constants.KEY_FOLIO_SOLICITUD,
					solicitudActiva.getNoFolioSolicitud());
			session.removeAttribute(Constants.KEY_DATOS_DOM_UMF);
			session.removeAttribute(Constants.KEY_LONGITUD_UMF);
			session.removeAttribute(Constants.KEY_LATITUD_UMF);
			session.removeAttribute(Constants.KEY_DOCUMENTO_TRAMITE);

			return detalleFinalizarSolicitud(model, session);
		} catch (SolicitudNoEncontradaException e) {
			log.error(e);
			return procesarError(model, e.getMessage());
		}
	}

	private String procesarError(Model model, String mensaje) {
		Fisica fisica = new Fisica();
		fisica.setErrorFormGeneral(mensaje);
		model.addAttribute("fisica", fisica);

		return Constants.KEY_VIEW_GENERAL_DHABIENTES;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mostrarDocumentoResultante", method = {
			RequestMethod.GET, RequestMethod.POST })
	public void mostrarDocumentoResultante(
			@RequestParam("idTramite") String idTramiteHashed,
			@RequestParam("tipoDocumento") String tipoDocumentoHashed,
			Model model, HttpServletResponse response, HttpSession session ) {
		Ciudadano ciudadanoSesion = (Ciudadano)session.getAttribute(Constants.KEY_CIUDADANO_SESSION);
		if(ciudadanoSesion == null) {
			throw new RuntimeException("No existe la sesi\u00f3n del usuario");			
		}

		Long idTramite = 0L;
		Integer idtipoDocumento = 0;

		try {
			idTramite = Long.valueOf(Base64Cipher.descrifrar(idTramiteHashed));
			idtipoDocumento = Integer.valueOf(Base64Cipher.descrifrar(tipoDocumentoHashed));
		} catch (NumberFormatException e) {
			log.error(e);
		} catch (InvalidKeyException e) {
			log.error(e);
		} catch (IllegalBlockSizeException e) {
			log.error(e);
		} catch (BadPaddingException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		
		Solicitud solicitud = (Solicitud) session.getAttribute(Constants.KEY_SOLICITUD);
		if (solicitud == null) {
			try {
				solicitud = solicitudBusiness.consultarPorIdTramite(idTramite);
			} catch (SolicitudNoEncontradaException e) {
				log.error(e);
			}
		}
		session.setAttribute(Constants.KEY_SOLICITUD, solicitud);

		StringBuffer sbLlaveTramite = new StringBuffer();
		sbLlaveTramite.append(idTramite).append("-").append(idtipoDocumento);
		Map<String, byte[]> mapDoctosGenerados = (Map<String, byte[]>) session.getAttribute(
				Constants.KEY_DOCUMENTO_TRAMITE);

		if (mapDoctosGenerados == null
				|| mapDoctosGenerados.get(sbLlaveTramite.toString()) == null) {
			byte[] documentoResultante = solicitudBusiness.obtenerDocumentoResultante(solicitud, idTramite,
							idtipoDocumento);

			if (mapDoctosGenerados == null) {
				mapDoctosGenerados = new HashMap<String, byte[]>();
			}
			mapDoctosGenerados.put(sbLlaveTramite.toString(), documentoResultante);
		}

		session.setAttribute(Constants.KEY_DOCUMENTO_TRAMITE, mapDoctosGenerados);

		byte[] documentoResultante = mapDoctosGenerados.get(sbLlaveTramite.toString());
		String nombreArchivo = "Solicitud_" + idTramite + ".pdf";

		try {
			if (documentoResultante != null) {
				log.debug("El documento no es nulo");

				response.addHeader("Accept-Ranges", "bytes");
				response.addHeader("Cache-Control", "public");
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.addHeader("expires", "0");
				response.addHeader("Content-disposition", "inline;filename=" + nombreArchivo);
				response.setContentLength(documentoResultante.length);
				response.getOutputStream().write(documentoResultante);
				response.getOutputStream().close();
			} else {
				log.debug("No hay documento");
			}
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	@RequestMapping(value = "/mostrarComprobante", method = {RequestMethod.GET, RequestMethod.POST })
	public void mostrarComprobatne(HttpServletResponse response, HttpSession session) {
		Ciudadano ciudadanoSesion = (Ciudadano)session.getAttribute(Constants.KEY_CIUDADANO_SESSION);
		if(ciudadanoSesion == null) {
			throw new RuntimeException("No existe la sesi\u00f3n del usuario");			
		}
		
		byte[] documentoResultante = (byte[]) session.getAttribute(Constants.KEY_ACUSE_TRAMITE);

		try {
			if (documentoResultante != null) {
				log.debug("El documento no es nulo");

				response.addHeader("Accept-Ranges", "bytes");
				response.addHeader("Cache-Control", "public");
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.addHeader("expires", "0");
				response.addHeader("Content-disposition", "inline;filename=" + "acuseTramite.pdf");
				response.setContentLength(documentoResultante.length);
				response.getOutputStream().write(documentoResultante);
				response.getOutputStream().close();
			} else {
				log.debug("No hay documento");
			}
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	
	@RequestMapping(value = "/reenviarDocumento", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> reenviarCorreo(
			@RequestParam String idTramiteHashed,
			@RequestParam String tipoDocumentoHashed,
			Model model, HttpServletResponse response, HttpSession session) {
		
		Ciudadano ciudadano = (Ciudadano) session.getAttribute(Constants.KEY_CIUDADANO_SESSION);
		if(ciudadano == null) {
			throw new RuntimeException("No existe la sesi\u00f3n del usuario");			
		}
		
		Solicitud solicitud = (Solicitud) session.getAttribute(Constants.KEY_SOLICITUD);
		UmfDomicilioDTO umfDomicilio = (UmfDomicilioDTO) session.getAttribute(Constants.KEY_DATOS_DOM_UMF);
		String correoCiudadano = (String) session.getAttribute(Constants.KEY_CORREO_CIUDADANO);
		String tipoTramite = (String) session.getAttribute(TRAMITE);

		String descripcionTipoTramite;
		if (StringUtils.isNotBlank(tipoTramite)) {
			if (tipoTramite.equals("cambioClinica")) {
				descripcionTipoTramite = "Cambio de Cl\u00EDnica o UMF (Unidad de Medicina Familiar) con CURP";
			} else if (tipoTramite.equals("cambioClinicaD")) {
				descripcionTipoTramite = "Cambio de Cl\u00EDnica o UMF (Unidad de Medicina Familiar) con CURP";
			} else if (tipoTramite.equals("registro")) {
				descripcionTipoTramite = "Alta en Cl\u00EDnica o UMF (Unidad de Medicina Familiar) con CURP";
			} else if (tipoTramite.equals("registroD")) {
				descripcionTipoTramite = "Alta en Cl\u00EDnica o UMF (Unidad de Medicina Familiar) con CURP";
			} else {
				descripcionTipoTramite = " ";
			}
		} else {
			descripcionTipoTramite = " ";
		}

		Map<String, Object> result = new HashMap<String, Object>();
		Long idTramite = 0L;
		Integer idtipoDocumento = 0;

		try {
			idTramite = Long.valueOf(Base64Cipher.descrifrar(idTramiteHashed));
			idtipoDocumento = Integer.valueOf(Base64Cipher.descrifrar(tipoDocumentoHashed));
		} catch (NumberFormatException e) {
			log.error(e);
		} catch (InvalidKeyException e) {
			log.error(e);
		} catch (IllegalBlockSizeException e) {
			log.error(e);
		} catch (BadPaddingException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}

		Map<String, byte[]> doctosMail = null;
		Map<String, String> documentosReenviados = (Map<String, String>) session.getAttribute(Constants.KEY_DOCTOS_REENVIADOS);

		if(documentosReenviados == null) {
			documentosReenviados = new HashMap<String, String>();
		}

		if(!documentosReenviados.containsKey(idtipoDocumento.toString())) {
			byte[] doctoGenerado = (byte[]) session.getAttribute(idtipoDocumento.toString());

			if(doctoGenerado == null){
				doctoGenerado = solicitudBusiness.obtenerDocumentoResultante(solicitud, idTramite, idtipoDocumento);
				log.debug("Se genero el documento " + idtipoDocumento);
			}else {
				log.debug("No se genero el documento " + idtipoDocumento);
			}

			if (doctoGenerado != null) {
				doctosMail = new HashMap<String, byte[]>();
				doctosMail.put("documentoReenviado" + idtipoDocumento + ".pdf", doctoGenerado);
				documentosReenviados.put("" + idtipoDocumento, "");
				session.setAttribute(Constants.KEY_DOCTOS_REENVIADOS, documentosReenviados);
				session.setAttribute(idtipoDocumento.toString(), doctoGenerado);

				if (correoCiudadano != null) {
					try {
						String[] toEmail = { correoCiudadano };
						String strFechaOperacion = DateFormat.getDateInstance(
								DateFormat.FULL, new Locale("es", "MX")).format(new Date());
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
						String strFechaSolicitud = sdf.format(solicitud.getFechaSolicitud());

						Map<String, String> mailAttr = new HashMap<String, String>();
						mailAttr.put("fechaOperacion", strFechaOperacion);
						mailAttr.put("folio", solicitud.getNoFolioSolicitud());
						mailAttr.put("idSolicitud", solicitud.getSolicitudId().toString());
						
						InstanceofPredicate tramiteRegDhabPredicate = new InstanceofPredicate(TramiteRegistroDerechohabiente.class);
						InstanceofPredicate tramiteCorrDhabPredicate = new InstanceofPredicate(TramiteCorreccionDerechohabiente.class);
						Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramiteRegDhabPredicate);
						Object tramiteModifInicial = CollectionUtils.find(solicitud.getTramites(), tramiteCorrDhabPredicate);
						
						if (tramiteInicial != null) {
							TramiteRegistroDerechohabiente tramiteRegistro = (TramiteRegistroDerechohabiente) tramiteInicial;
							mailAttr.put("idTipoTramite", tramiteRegistro.getTipoTramite().getIdTipoTramite().toString());
							mailAttr.put("nombreEmpaquetado", "registroDerechoHabiente_" + idtipoDocumento + ".pdf");
						} else if(tramiteModifInicial != null) {
							TramiteCorreccionDerechohabiente tramiteCorreccion = (TramiteCorreccionDerechohabiente) tramiteModifInicial;
							mailAttr.put("idTipoTramite", tramiteCorreccion.getTipoTramite().getIdTipoTramite().toString());
							mailAttr.put("nombreEmpaquetado", "cambioClinica_" + idtipoDocumento + ".pdf");
						}

						mailAttr.put("fechaSolicitud", strFechaSolicitud);

						if (umfDomicilio != null && umfDomicilio.getMedicoEnTurno() != null) {
							MedicoEnTurno medicoEnTurno = umfDomicilio.getMedicoEnTurno();

							mailAttr.put("clinicaAsignada", medicoEnTurno.getUnidadMedicaFamiliar().getDescripcion());
							mailAttr.put("direccionClinica", medicoEnTurno.getUnidadMedicaFamiliar().getDesDireccion());
							mailAttr.put("turno", medicoEnTurno.getTurno().getDescripcion());
							mailAttr.put("consultorio", medicoEnTurno.getConsultorio().getIdConsultorio().toString());
						} else {
							mailAttr.put("clinicaAsignada", "-");
							mailAttr.put("direccionClinica", "-");
							mailAttr.put("turno", "-");
							mailAttr.put("consultorio", "-");
						}

						if (ciudadano != null
								&& StringUtils.isNotBlank(ciudadano.getNombreCompleto())) {
							mailAttr.put("nombreCompleto", ciudadano.getNombreCompleto());
						} else {
							mailAttr.put("nombreCompleto", " ");
						}

						if(StringUtils.isNotBlank(descripcionTipoTramite)){
							mailAttr.put("descripcionTipoTramite", descripcionTipoTramite);
						} else {
							mailAttr.put("descripcionTipoTramite", " ");
						}

						if (doctosMail.isEmpty()) {
							doctosMail = null;
						}
						emailServiceRemote.enviarCorreoCambioRegistroClinicaByQueue(correoCiudadano,
								null, "Reenvio de documento",
								doctosMail, mailAttr);
					} catch (Exception e) {
						log.error("No fue posible mandar el correo", e);
					}
				}
			}
		} 
		
		result.put("mensaje", "El documento ya ha sido reenviado a la direcci\u00f3n " + correoCiudadano);
		
		return result;
	}

	@RequestMapping(value ="/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> limpiarSession(final HttpSession session) {
		session.removeAttribute(Constants.KEY_CABEZA_GRUPO);
		session.removeAttribute(Constants.KEY_ASIGNACION_NSS);
		session.removeAttribute(Constants.KEY_PATRONES_ASEGURADO);
		session.removeAttribute(Constants.KEY_IDS_MODALIDADES);
		session.removeAttribute(Constants.KEY_MODALIDADES_ACTIVAS);
		session.removeAttribute(Constants.KEY_PATRON_IMSS);
		session.removeAttribute(Constants.KEY_DATOS_ASEGURADO);
		session.removeAttribute(Constants.KEY_CIUDADANO_SESSION);
		session.removeAttribute(Constants.KEY_SOLICITUD);
		session.removeAttribute(Constants.KEY_FOLIO_SOLICITUD);
		session.removeAttribute(Constants.KEY_DATOS_DOM_UMF);
		session.removeAttribute(Constants.KEY_LONGITUD_UMF);
		session.removeAttribute(Constants.KEY_LATITUD_UMF);
		session.removeAttribute(Constants.KEY_CORREO_CIUDADANO);
		session.removeAttribute(Constants.KEY_TRAMITE);
		session.removeAttribute(Constants.KEY_DOCTOS_REENVIADOS);
		session.removeAttribute(Constants.KEY_DOCUMENTO_TRAMITE);
		session.removeAttribute(Constants.KEY_ACUSE_TRAMITE);

		return null;
	}
	
	private static class InstanceofPredicate implements Serializable, Predicate {
		private static final long serialVersionUID = 1L;
		private final Class iType;

		public static Predicate getInstance(Class type) {
			if (type == null) {
				throw new IllegalArgumentException("The type to check instanceof must not be null");
			}

			return new InstanceofPredicate(type);
		}

		public InstanceofPredicate(Class type) {
			this.iType = type;
		}

		public boolean evaluate(Object object) {
			return this.iType.isInstance(object);
		}

		public Class getType() {
			return this.iType;
		}

	}
}


