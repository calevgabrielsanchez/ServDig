/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.registro.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.web.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.cit.cda.web.utils.DocumentoProbatorioUtil;
import mx.gob.imss.cit.cda.web.utils.TramiteCorreccionUtil;
import mx.gob.imss.cit.cda.web.validator.DatosHistoriaLaboralValidator;
import mx.gob.imss.cit.cda.web.validator.DocumentosProbatoriosValidator;
import mx.gob.imss.cit.cda.web.validator.NSSValidator;
import mx.gob.imss.cit.cda.web.vo.DatosAdicionalesHistoriaLaboral;
import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/wizard/correccionDatosAsegurado/documentosProbatorios")
@SessionAttributes(value = { "idPersona", "sol", "personaCorreccion" })
public class DocumentosProbatoriosController extends AbstractController {

	private final Logger log = LoggerFactory
			.getLogger(DocumentosProbatoriosController.class);

	@Autowired
	private NSSValidator nSSValidator;

	@Autowired
	@Qualifier("documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;

	@Autowired
	private DocumentoProbatorioUtil documentoProbatorioUtil;

	@Autowired
	@Qualifier("bovedaBusiness")
	private BovedaRemote bovedaBusiness;

	@Autowired
	private DocumentosProbatoriosValidator documentosProbatoriosValidator;

	@Autowired
	private DatosHistoriaLaboralValidator datosHistoriaLaboralValidator;

	@Autowired
	private TramiteCorreccionUtil tramiteCorreccionUtil;

	@Autowired
	@Qualifier("solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;

	private final String VIEW_DATOS_NSS = "datosNSS";
	private static final String PERSONA_SESSION_KEY = "persona_correccion";
	private static final Long TAMANIO_MAXIMO_ARCHIVO = 4194304L;
	private static final String STATUS_ERROR_UPLOAD = "error";
	private static final String ID_DOCUMENTO_BOVEDA = "idDocBoveda";
	private static final String LISTA_DOCUMENT_NSS = "listpreliminar";
	private static final String STATUS_SUCCESS_UPLOAD = "success";
	private static final String DATOSHISTORIALABORAL = "datosHistoriaLaboral";
	private static final String DATOS_ADICIONALES_HISTORIA = "datosAdicionalesHistoriaLaboral";
	private static final String THISPATHCONTROLLER = "documentosProbatorios";
	private static final String VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL = "datosAdicionalesHistoriaLaboral";
	private static final String VIEW_RECURSO_NO_DISPONIBLE = "recursoNoDisponible";
	private static final String DOC_PROB_LIST = "documentoProbatorioList";
	private static final String REDIRECTNSS = "vistaNssList";
	private static final String ERROR_ADJUNTAR_DOCUMENTO = "Error al adjuntar Documento. Intente nuevamente.";
	public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
	public static final Charset UTF_8 = Charset.forName("UTF-8");

	/* METODOS PARA DOCUMENTOS PROBATORIOS DEL ASEGURADO */

	/**
	 * Guarda los documentos probatorios del asegurado y redirige a la pantalla
	 * de agregar nss
	 * 
	 * @param datosHistoriaLaboralvo
	 * @param result
	 * @param model
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/datosNSS", method = RequestMethod.POST)
	public RedirectView datosNSS(
			@ModelAttribute("datosHistoriaLaboral") DatosHistoriaLaboralVO datosHistoriaLaboralvo,
			BindingResult result, Model model, HttpSession session,
			HttpServletResponse response) {
		log.debug("--CDA-- INICIA GUARDADO PARCIAL DE DOCUMENTOS DEL ASEGURADO.... ");

		DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
				.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);
		Solicitud sol = (Solicitud) session
				.getAttribute(SessionConstants.ATTR_SOLICITUD);

		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) sol
				.getTramites().get(0);

		log.debug("---CDA--- NUMERO DE DOCUMENTOS: "
				+ datosHistoriaLaboral.getDocumentoProbatorioList().size());
		try {
			sol = tramiteCorreccionUtil.actualizarXmlDocumentosAseg(sol,
					datosHistoriaLaboral);

			solicitudBusiness.actualizarXmlTramite(tramite);

			session.setAttribute(SessionConstants.ATTR_SOLICITUD, sol);
			return new RedirectView(
					RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
							+ "/" + THISPATHCONTROLLER + "/" + REDIRECTNSS,
					true);
		} catch (Exception ex) {
			log.error("---CDA--- Errores al buscar el tramite {}", ex);
			cargaPantallaDocumentoAsegurado(model, session);
			model.addAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
			return new RedirectView(
					RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
							+ "/" + THISPATHCONTROLLER + "/"
							+ DATOSHISTORIALABORAL, true);
		}
	}

	/**
	 * Metodo que almacena en documentos probatorios del aseg en boveda
	 * 
	 * @param file
	 * @param idDocporTipo
	 * @param cveIdDocumento
	 * @param desDocumento
	 * @param tipoDocumento
	 * @param ses
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/asegUploadify")
	public ResponseEntity<Map<String, Object>> uploadDocumentosAseguradoBytes(
			@RequestParam("fileData") MultipartFile file,
			@RequestParam("idDocPorTipo") String idDocporTipo,
			@RequestParam("cveIdDocumento") String cveIdDocumento,
			@RequestParam("desDocumento") String desDocumento,
			@RequestParam("tipoDocumento") String tipoDocumento,
			HttpSession ses, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		byte[] ptext = desDocumento.getBytes(ISO_8859_1);
		String value = new String(ptext, UTF_8);

		byte[] conv = file.getOriginalFilename().getBytes(ISO_8859_1);
		String valueConv = new String(conv, UTF_8);

		Map<String, Object> result = new HashMap<String, Object>();

		if (file.getSize() <= TAMANIO_MAXIMO_ARCHIVO) {
			Solicitud solicitud = (Solicitud) ses
					.getAttribute(SessionConstants.ATTR_SOLICITUD);
			log.debug("---CDA--- ##### CARGANDO ARCHIVO DEL ASEGURADO A BOVEDA  ######");
			log.debug("---CDA--- Descripcion {} ", desDocumento);
			log.debug("---CDA--- Tamanio Arc {} ", file.getSize());
			
			String[] n = file.getOriginalFilename().split("\\.");
			Fisica fisica = ses.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) ses
					.getAttribute(PERSONA_SESSION_KEY) : null;
					
			if (fisica != null) {
				String idDocBoveda = null;
				String nombreCompleto = null;
				try {
					nombreCompleto = idDocporTipo + "_" + valueConv;
					
					idDocBoveda = bovedaBusiness.subirDocumento(
							file.getBytes(), solicitud, nombreCompleto,
							n[n.length - 1], file.getContentType());

					if (idDocBoveda != null) {
						log.debug(
								"---CDA--- ID DOCUMENTO ASEGURADO EN BOVEDA {}",
								idDocBoveda);
						result.put(STATUS_SUCCESS_UPLOAD, STATUS_SUCCESS_UPLOAD);
						result.put(ID_DOCUMENTO_BOVEDA, idDocBoveda);
						DocumentoProbatorio documento = new DocumentoProbatorio();
						documento.setIdDocBoveda(idDocBoveda);
						documento.setIdDocumentoPorTipo(new Long(idDocporTipo));
						documento.setCveIdDocumento(new Long(cveIdDocumento));
						documento.setNombre(nombreCompleto);
						documento.setDesDocumento(value);
						documento.setTipoDocumento(Integer
								.valueOf(tipoDocumento));

						addListDocumentoProbatorioAegurado(documento, ses);
					} else {
						log.error("---CDA--- Error en createDocument ::: el idDocBoveda es nulo");
						result.put(STATUS_ERROR_UPLOAD,
								ERROR_ADJUNTAR_DOCUMENTO);
					}

				} catch (BovedaCDAException bce) {
					log.error(bce.getSituacion());
					result.put(STATUS_ERROR_UPLOAD, bce.getSituacion());
					result.put("__situacion_", bce.getSituacion());
					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.setContentType(MediaType.TEXT_HTML);
					return new ResponseEntity<Map<String, Object>>(result,
							httpHeaders, HttpStatus.OK);
				} catch (Exception e) {
					log.error("---CDA--- Error al subir el documento {}", e);
					result.put(STATUS_ERROR_UPLOAD, ERROR_ADJUNTAR_DOCUMENTO);
					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.setContentType(MediaType.TEXT_HTML);
					return new ResponseEntity<Map<String, Object>>(result,
							httpHeaders, HttpStatus.OK);
				}

			} else {
				log.warn("---CDA--- El usuario no esta logeado idPersona null");
				result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento.");
			}
		} else {
			result.put(
					STATUS_ERROR_UPLOAD,
					"El archivo no cumple con el tama\u00f1o m\u00e1ximo permitido [4MB]. No es posible adjuntar el archivo.");
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.TEXT_HTML);
		return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
				HttpStatus.OK);
	}

	/**
	 * Agrega documento probatorio del asegurado a la lista de sesion
	 * 
	 * @param documento
	 * @param ses
	 */
	private void addListDocumentoProbatorioAegurado(
			DocumentoProbatorio documento, HttpSession ses) {

		log.debug("AGREGANDO DOCUMENTO ASEGURADO....... "
				+ documento.getIdDocBoveda());
		DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) ses
				.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);

		if (datosHistoriaLaboral.getDocumentoProbatorioList() != null) {
			log.debug("---CDA--- Se agrega documentacion preliminar");
			datosHistoriaLaboral.getDocumentoProbatorioList().add(documento);
		} else {
			log.debug("---CDA--- Se crea lista de documentacion preliminar");
			datosHistoriaLaboral
					.setDocumentoProbatorioList(new ArrayList<DocumentoProbatorio>());
			datosHistoriaLaboral.getDocumentoProbatorioList().add(documento);
		}

		actualizarDocumentosAseguradoXml(
				datosHistoriaLaboral.getDocumentoProbatorioList(), ses);
	}

	/**
	 * Actualiza el xml para mantener los cambios parciales del usuario además
	 * de los objetos en sesion
	 */
	public void actualizarDocumentosAseguradoXml(
			List<DocumentoProbatorio> documentos, HttpSession session) {

		log.info("ACTUALIZANDO XML Y OBJETOS DE SESSION  DOSCS PROB");
		DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
				.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);

		Solicitud solicitud = (Solicitud) session
				.getAttribute(SessionConstants.ATTR_SOLICITUD);

		try {

			datosHistoriaLaboral.setDocumentoProbatorioList(documentos);
			solicitud = tramiteCorreccionUtil.actualizarXmlDocumentosAseg(
					solicitud, datosHistoriaLaboral);

			/**
			 * Se actualizan los objetos de session
			 */
			session.setAttribute(SessionConstants.ATTR_SOLICITUD, solicitud);
			session.setAttribute(
					SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL,
					datosHistoriaLaboral);

		} catch (TramiteNoEncontradoException e) {
			log.error("ERROR ACTUALIZANDO XML", e);
		}

	}

	@RequestMapping(value = "/validarDocumentosAsegurado", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> validarDocumentosAsegurado(
			@RequestBody NSSVO nssvo, HttpServletResponse response,
			HttpSession session, Model model) {
		log.debug("---CDA---Inicia la validacion de documentos del asegurado");
		DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
				.getAttribute(DATOSHISTORIALABORAL);
		final Errors errors = new BindException(datosHistoriaLaboral, "model");
		Map<String, Object> mapa = new HashMap<String, Object>();
		if (datosHistoriaLaboral != null) {
			if (datosHistoriaLaboral.getDocumentoProbatorioList() != null
					&& !datosHistoriaLaboral.getDocumentoProbatorioList()
							.isEmpty()) {
				log.debug("---CDA---Inicia documentos del NSS : {}",
						datosHistoriaLaboral.getDocumentoProbatorioList()
								.size());
				documentosProbatoriosValidator.validate(
						datosHistoriaLaboral.getDocumentoProbatorioList(),
						errors, false, DOC_PROB_LIST);
			} else {
				log.warn("---CDA--- Errores de captura documentoProbatorioList error");
				errors.rejectValue(DOC_PROB_LIST,
						"field.documentoProbatorio.listaVacia");
			}
		}
		if (errors.hasErrors() || errors.hasFieldErrors(DOC_PROB_LIST)) {
			log.warn("---CDA--- Errores de captura");
			procesaErroresDeCaptura(errors, mapa, response);

		}
		return mapa;
	}

	/**
	 * Carga la pantalla de Agregar nss
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/vistaNssList", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String getVistaNssList(Model model, HttpSession session) {
		Fisica fisica = session.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) session
				.getAttribute(PERSONA_SESSION_KEY) : null;

		log.debug("--CDA-- CARGANDO VISTA AGREGAR NSS.................");
		DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
				.getAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL);

		Solicitud sol = (Solicitud) session
				.getAttribute(SessionConstants.ATTR_SOLICITUD);
		datosHistoriaLaboral = tramiteCorreccionUtil.getPrecargaNssList(sol,
				datosHistoriaLaboral);

		// session.setAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
		model.addAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
		this.log.debug("---CDA--- idPersona fisica en sesion: {}",
				fisica != null ? fisica.getIdPersona()
						: "sin persona en sesion");
		cargaPantallaDocumentoNSS(model, session);
		return VIEW_DATOS_NSS;
	}

	/**
	 * Guarda los datos del NSS y de los documentos probatorios
	 * 
	 * @param nssvo
	 * @param response
	 * @param ses
	 * @param nssLength
	 * @param model
	 * @param idPersona
	 * @param resultValidate
	 * @return
	 */
	@RequestMapping(value = "/validar/nss/{nssLength}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> documentoAsociado(
			@RequestBody NSSVO nssvo, HttpServletResponse response,
			HttpSession ses, @PathVariable Integer nssLength, Model model,
			@ModelAttribute("idPersona") Long idPersona,
			BindingResult resultValidate) {
		final Errors errors = new BindException(nssvo, "model");

		log.debug("---CDA--- ############ VALIDANDO NSS #####################");
		log.debug("---CDA--- NSS recibido : {} ", nssvo);
		log.debug("---CDA--- NSS list length : {} ", nssLength);

		Map<String, Object> result = new HashMap<String, Object>();
		DatosHistoriaLaboralVO datosHistoriaLaboralVO = (DatosHistoriaLaboralVO) ses
				.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);

		if (nssvo != null) {
			log.debug("---CDA--- nSSValidator");
			nSSValidator.validate(nssvo, errors);
			nSSValidator.validateExisteNssInList(nssvo,
					datosHistoriaLaboralVO.getNSSList(), errors);
			if (errors.hasErrors() || errors.hasFieldErrors("NSS")) {
				log.debug("---CDA--- procesa errores de captura NSS");
				procesaErroresDeCaptura(errors, result, response);
			} else {
				log.debug("---CDA--- Validacion probatorios");
				List<DocumentoProbatorio> listaDocumentoProbNSS = (List<DocumentoProbatorio>) ses
						.getAttribute(LISTA_DOCUMENT_NSS);
				if (listaDocumentoProbNSS != null
						&& !listaDocumentoProbNSS.isEmpty()) {
					log.debug("---CDA--- Valida documentos al NSS : {}",
							listaDocumentoProbNSS);
					documentosProbatoriosValidator.validateDocumentosNSS(
							listaDocumentoProbNSS, resultValidate,
							DOC_PROB_LIST);
				} else {
					errors.rejectValue(DOC_PROB_LIST,
							"field.documentoProbatorio.listaVacia");
				}
				if (errors.hasErrors() || errors.hasFieldErrors(DOC_PROB_LIST)) {
					log.debug(
							"---CDA--- procesa erroes de captura Documentos de NSS : {}",
							nssvo.getNSS());
					procesaErroresDeCaptura(errors, result, response);
				} else {
					log.debug("---CDA--- Carga la el nuevo NSS y sus documentos");
					nssvo.setDocumentoProbatorioList(listaDocumentoProbNSS);
					ses.setAttribute(LISTA_DOCUMENT_NSS, null);

					if (ses.getAttribute(DATOSHISTORIALABORAL) == null) {
						ses.setAttribute(DATOSHISTORIALABORAL,
								new DatosHistoriaLaboralVO());
					}

					if (datosHistoriaLaboralVO.getNSSList() != null) {
						datosHistoriaLaboralVO.getNSSList().add(nssvo);
					} else {
						List<NSSVO> listNss = new ArrayList<NSSVO>();
						datosHistoriaLaboralVO.setNSSList(listNss);
						datosHistoriaLaboralVO.getNSSList().add(nssvo);
					}

					ses.setAttribute(
							SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL,
							datosHistoriaLaboralVO);
				}
			}
		} else {
			errors.rejectValue("NSS", "field.NSS.listaVacia");
		}
		log.debug("---CDA--- Finaliza metodo");
		return result;
	}

	
	  /**
     * Metodo que guardara en boveda la informacion de los documentos y
     * posteriormente los metera en un objeto en session para asociarlo despues
     * a un NSS
     * 
     * @param file
     * @param idDocporTipo
     * @param cveIdDocumento
     * @param ses
     * @param tipoDocumento
     * @param desDocumento
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadify")
    public ResponseEntity<Map<String, Object>> uploadBytes(
            @RequestParam("fileData") MultipartFile file,
            @RequestParam("idDocPorTipo") String idDocporTipo,
            @RequestParam("cveIdDocumento") String cveIdDocumento,
            @RequestParam("desDocumento") String desDocumento,
            @RequestParam("tipoDocumento") String tipoDocumento,
            HttpSession ses, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        byte[] ptext = desDocumento.getBytes(ISO_8859_1);
        String value = new String(ptext, UTF_8);

        byte[] conv = file.getOriginalFilename().getBytes(ISO_8859_1);
        String valueConv = new String(conv, UTF_8);

        Map<String, Object> result = new HashMap<String, Object>();
        log.debug("---CDA--- Tamanio Archivo {} ", file.getSize());
        if (file.getSize() <= TAMANIO_MAXIMO_ARCHIVO) {
            Solicitud solicitud = (Solicitud) ses.getAttribute(SessionConstants.ATTR_SOLICITUD);
            log.debug("---CDA--- #####Subiendo Archivo######");
            String[] n = file.getOriginalFilename().split("\\.");
            Fisica fisica = ses.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) ses
                    .getAttribute(PERSONA_SESSION_KEY) : null;
            if (fisica != null) {
                String idDocBoveda = null;
                String nombreCompleto = null;
                try {
                    nombreCompleto = idDocporTipo + "_" + valueConv;
                    idDocBoveda = bovedaBusiness.subirDocumento(
                            file.getBytes(), solicitud, nombreCompleto,
                            n[n.length - 1], file.getContentType());
                } catch (BovedaCDAException bce) {
                    log.error(bce.getSituacion());
                    result.put(STATUS_ERROR_UPLOAD, bce.getSituacion());
                    result.put("__situacion_", bce.getSituacion());
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.TEXT_HTML);
                    return new ResponseEntity<Map<String, Object>>(result,
                            httpHeaders, HttpStatus.OK);
                } catch (Exception e) {
                    log.error("---CDA--- Error al subir el documento {}", e);
                    result.put(STATUS_ERROR_UPLOAD, ERROR_ADJUNTAR_DOCUMENTO);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.TEXT_HTML);
                    return new ResponseEntity<Map<String, Object>>(result,
                            httpHeaders, HttpStatus.OK);
                }

                if (idDocBoveda != null) {
                    log.debug("---CDA--- documento insertado en boveda {}",
                            idDocBoveda);
                    result.put(STATUS_SUCCESS_UPLOAD, STATUS_SUCCESS_UPLOAD);
                    result.put(ID_DOCUMENTO_BOVEDA, idDocBoveda);
                    DocumentoProbatorio documentoPreliminar = new DocumentoProbatorio();
                    documentoPreliminar.setIdDocBoveda(idDocBoveda);
                    documentoPreliminar.setIdDocumentoPorTipo(new Long(
                            idDocporTipo));
                    documentoPreliminar.setCveIdDocumento(new Long(
                            cveIdDocumento));
                    documentoPreliminar.setNombre(nombreCompleto);
                    documentoPreliminar.setDesDocumento(value);
                    documentoPreliminar.setTipoDocumento(Integer
                            .valueOf(tipoDocumento));
                    addListDocumentoProbatorioNSS(documentoPreliminar, ses);
                } else {
                    log.debug("---CDA--- Error en createDocument ::: el idDocBoveda es nulo");
                    result.put(STATUS_ERROR_UPLOAD, ERROR_ADJUNTAR_DOCUMENTO);
                }
            } else {
                log.warn("---CDA--- El usuario no esta logeado idPersona null");
                result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento.");
            }
        } else {
            result.put(
                    STATUS_ERROR_UPLOAD,
                    "El archivo no cumple con el tama\u00f1o m\u00e1ximo permitido [4MB]. No es posible adjuntar el archivo.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);
    }
	
	
	@RequestMapping(value = "/eliminarNSS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> eliminarNSS(@RequestBody NSSVO nssvo,
			HttpServletResponse response, HttpSession ses, Model model,
			@ModelAttribute("idPersona") Long idPersona,
			BindingResult resultValidate) {
		log.debug("---CDA--- NSS a remover : {} ", nssvo);

		Map<String, Object> result = new HashMap<String, Object>();
		DatosHistoriaLaboralVO datosHistoriaLaboralVO = (DatosHistoriaLaboralVO) ses
				.getAttribute(DATOSHISTORIALABORAL);
		List<NSSVO> listNSS = datosHistoriaLaboralVO.getNSSList();
		Iterator<NSSVO> it = listNSS.iterator();
		Boolean bandera = Boolean.FALSE;
		while (it.hasNext()) {
			NSSVO nss = it.next();
			if (nss.getNSS().equals(nssvo.getNSS())) {
				for (DocumentoProbatorio doc : nss.getDocumentoProbatorioList()) {
					try {
						String confirmacion = bovedaBusiness
								.eliminarDocumento(doc.getIdDocBoveda());
						if (!confirmacion
								.contains(MensajesBovedaCDAEnum.MSJ_BP5001
										.getCodigo())) {
							bandera = Boolean.TRUE;
							result.put(STATUS_ERROR_UPLOAD, confirmacion);
						}
					} catch (BovedaCDAException bce) {
						bandera = Boolean.TRUE;
						log.error("---CDA-- Ocurrio un error {}",
								bce.getSituacion());
						result.put(STATUS_ERROR_UPLOAD, bce.getSituacion());
					}
				}
				it.remove();
			}
		}
			Solicitud solicitud = (Solicitud) ses
					.getAttribute(SessionConstants.ATTR_SOLICITUD);
			result.put(STATUS_SUCCESS_UPLOAD,
					"Los documentos eliminados con \u00e9xito del NSS ");
			tramiteCorreccionUtil.eliminarNssdelTramiteXml(solicitud, nssvo.getNSS());
            ses.setAttribute(SessionConstants.ATTR_SOLICITUD, solicitud);

		log.debug("---CDA--- Finaliza metodo");
		return result;
	}

	@RequestMapping(value = "/eliminarDocumentoProbatorio", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> eliminarDocumentoProbatorio(
			@RequestBody DocumentoProbatorio documentoProbatorio,
			HttpServletResponse response, HttpSession ses, Model model,
			@ModelAttribute("idPersona") Long idPersona) {
		log.debug("---CDA--- DOCUMENTO NSS  REMOVER : {} ", documentoProbatorio);
		Map<String, String> result = new HashMap<String, String>();
		List<DocumentoProbatorio> listDocumento = (List<DocumentoProbatorio>) ses
				.getAttribute(LISTA_DOCUMENT_NSS);
		Iterator<DocumentoProbatorio> it = listDocumento.iterator();
		while (it.hasNext()) {
			DocumentoProbatorio doc = it.next();
			if (doc.getCveIdDocumento().equals(
					documentoProbatorio.getCveIdDocumento())
					&& doc.getIdDocBoveda().equals(
							documentoProbatorio.getIdDocBoveda())) {
				try {
					String confirmacion = bovedaBusiness.eliminarDocumento(doc
							.getIdDocBoveda());
					if (confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001
							.getCodigo())
							|| confirmacion
									.contains(MensajesBovedaCDAEnum.MSJ_BP5004
											.getCodigo())) {
						result.put(
								STATUS_SUCCESS_UPLOAD,
								new StringBuilder(
										MensajesBovedaCDAEnum.MSJ_BP5001
												.getCodigo())
										.append(" - ")
										.append(MensajesBovedaCDAEnum.MSJ_BP5001
												.getDescripcion()).append(".")
										.toString());
						it.remove();
					}
				} catch (BovedaCDAException bce) {
					log.error("---CDA-- Ocurrio un error {}",
							bce.getSituacion());
					result.put(STATUS_ERROR_UPLOAD, bce.getSituacion());
				}
			}
		}
		return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
	}

	
	
	
	
	
	
	@RequestMapping(value = "/validar/datosNSSDocumentos", method = RequestMethod.POST)
	public String consultaDatosHistoriaLaboral(
			@ModelAttribute("datosHistoriaLaboral") DatosHistoriaLaboralVO datosHistoriaLaboralvo,
			BindingResult result, Model model, HttpSession session)
			throws DocumentoException {
		log.debug("---CDA--- VALIDANDO DOCUMENTO PROBATORIO PARA NSS");
		DatosHistoriaLaboralVO datosHistoriaLaboral;
		if (session.getAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL) != null) {
			datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
					.getAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL);
		} else {
			datosHistoriaLaboral = new DatosHistoriaLaboralVO();
		}

		DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral;
		if (session.getAttribute(DATOS_ADICIONALES_HISTORIA) != null) {
			datosAdicionalesHistoriaLaboral = (DatosAdicionalesHistoriaLaboral) session
					.getAttribute(DATOS_ADICIONALES_HISTORIA);
		} else {
			datosAdicionalesHistoriaLaboral = new DatosAdicionalesHistoriaLaboral();
		}

		session.setAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL,
				datosHistoriaLaboral);
		Solicitud solicitud = (Solicitud) session
				.getAttribute(SessionConstants.ATTR_SOLICITUD);
		try {
			solicitud = tramiteCorreccionUtil.crearTramiteNss(solicitud,
					datosHistoriaLaboral);
			session.setAttribute(SessionConstants.ATTR_SOLICITUD, solicitud);
		} catch (Exception ex) {
			this.log.error("---CDA--- Error al crear el tramite", ex);
			return VIEW_RECURSO_NO_DISPONIBLE;
		}
		log.debug(
				"******************CDA************************* SE AGREGAN AL MODELO DatosHistoriaLaboral {}",
				datosAdicionalesHistoriaLaboral.getObservaciones());
		model.addAttribute(DATOS_ADICIONALES_HISTORIA,
				datosAdicionalesHistoriaLaboral);
		return VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
	}

	@RequestMapping(value = "/eliminarDocumentoAsegurado", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> eliminarDocumentoAsegurado(
			@RequestBody DocumentoProbatorio docuemntoProbatorio,
			HttpServletResponse response, HttpSession ses, Model model,
			@ModelAttribute("idPersona") Long idPersona) {

		Map<String, String> result = new HashMap<String, String>();

		log.debug("---CDA-- DOCUMENTO A ELIMINAR : {} ",
				docuemntoProbatorio.getIdDocBoveda());

		DatosHistoriaLaboralVO datosHistoria = (DatosHistoriaLaboralVO) ses
				.getAttribute(SessionConstants.ATTR_INFORMACION_HISTORIAL_LABORAL);

		Iterator<DocumentoProbatorio> it = datosHistoria
				.getDocumentoProbatorioList().iterator();

		while (it.hasNext()) {
			DocumentoProbatorio doc = it.next();
			if (doc.getCveIdDocumento().equals(
					docuemntoProbatorio.getCveIdDocumento())
					&& doc.getIdDocBoveda().equals(
							docuemntoProbatorio.getIdDocBoveda())) {
				try {
					log.debug("---CDA--- idBoveda: {} ", doc.getIdDocBoveda());
					String confirmacion = bovedaBusiness.eliminarDocumento(doc
							.getIdDocBoveda());
					if (confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001
							.getCodigo())
							|| confirmacion
									.contains(MensajesBovedaCDAEnum.MSJ_BP5004
											.getCodigo())) {
						result.put(
								STATUS_SUCCESS_UPLOAD,
								new StringBuilder(
										MensajesBovedaCDAEnum.MSJ_BP5001
												.getCodigo())
										.append(" - ")
										.append(MensajesBovedaCDAEnum.MSJ_BP5001
												.getDescripcion()).append(".")
										.toString());

					} else {
						result.put(STATUS_ERROR_UPLOAD, confirmacion);
					}
					log.debug("---CDA--- RESPUESTA BOVEDA: {} ", confirmacion);

				} catch (BovedaCDAException bce) {
					log.error("---CDA-- Ocurrio un error {}",
							bce.getSituacion());
					result.put(STATUS_ERROR_UPLOAD, bce.getSituacion());
				}
				it.remove();
			}
		}

		log.debug("Numero de documentos probatorios: {} ", datosHistoria
				.getDocumentoProbatorioList().size());

		actualizarDocumentosAseguradoXml(
				datosHistoria.getDocumentoProbatorioList(), ses);
		model.addAttribute(DATOSHISTORIALABORAL, datosHistoria);
		return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
	}

	private void addListDocumentoProbatorioNSS(DocumentoProbatorio documento,
			HttpSession ses) {

		List<DocumentoProbatorio> listaDocumentoProbNSS = (List<DocumentoProbatorio>) ses
				.getAttribute(LISTA_DOCUMENT_NSS);
		if (listaDocumentoProbNSS != null) {
			log.debug("---CDA--- Se agrega documentacion preliminar");
			listaDocumentoProbNSS.add(documento);
		} else {
			log.debug("---CDA--- Se crea lista de documentacion preliminar");
			listaDocumentoProbNSS = new ArrayList<DocumentoProbatorio>();
			listaDocumentoProbNSS.add(documento);
		}

		ses.setAttribute(LISTA_DOCUMENT_NSS, listaDocumentoProbNSS);

	}

	private void cargaPantallaDocumentoAsegurado(Model model,
			HttpSession session) {
		Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos = documentoProbatorioUtil
				.getDocumentProbAsegurado(false);
		model.addAttribute("documentosProbatoriosVo", doctos);
		model.addAttribute("documentoNSSClave",
				TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
	}

	/**
	 * 
	 * @param model
	 * @param session
	 */
	private void cargaPantallaDocumentoNSS(Model model, HttpSession session) {
		Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos = documentoProbatorioUtil
				.getDocumentProbNss();
		model.addAttribute("documentosProbatoriosVo", doctos);
		model.addAttribute("documentoNSSClave",
				TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
	}

	@RequestMapping(value = "/validarDocumentosbyNSS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> validarDocumentosbyNSS(
			@RequestBody DatosHistoriaLaboralVO datosHistor,
			HttpServletResponse response, HttpSession session, Model model) {
		log.debug("---CDA---Inicia la validacion de documentos del asegurado");
		final Errors errors = new BindException(datosHistor, "model");
		log.debug("---CDA--- Validar NSS ingresados y documentacion probatoria");
		Map<String, Object> mapa = new HashMap<String, Object>();

		DatosHistoriaLaboralVO datosHistoriaLaboral;
		if (session.getAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL) != null) {
			datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
					.getAttribute(SessionConstants.ATTR_DATOS_HISTORIA_LABORAL);
		} else {
			datosHistoriaLaboral = new DatosHistoriaLaboralVO();
		}
		datosHistoriaLaboralValidator.validarNssList(datosHistoriaLaboral,
				errors);
		if (errors.hasErrors()) {
			this.log.warn("---CDA--- Errores de captura");
			model.addAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
			procesaErroresDeCaptura(errors, mapa, response);
		}
		return mapa;

	}
}
