/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.controller;

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
import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoSolicitanteEnum;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
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
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

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
@RequestMapping(value = RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
        + RequestMappingConstants.REGISTRO_DOCUMENTOS_PROBATORIOS)
@SessionAttributes({ "sol", "personaCorreccion" })
public class DocumentosProbatoriosController extends AbstractController {

    @Autowired
    private NSSValidator nSSValidator;

    @Autowired
    @Qualifier("bovedaBusiness")
    private BovedaRemote bovedaBusiness;
    
    @Autowired
    @Qualifier("solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;

    @Autowired
    private DocumentosProbatoriosValidator documentosProbatoriosValidator;

    @Autowired
    private TramiteCorreccionUtil tramiteCorreccionUtil;

    @Autowired
    private DocumentoProbatorioUtil documentoProbatorioUtil;

    @Autowired
    private DatosHistoriaLaboralValidator datosHistoriaLaboralValidator;

    @ModelAttribute("sol")
    public Solicitud getSolicitud() {
        return new Solicitud();
    }

    @ModelAttribute("personaCorreccion")
    public Fisica getFisica() {
        return new Fisica();
    }

    private static final String VIEW_DATOS_NSS = "datosNSS";
    private static final String VIEW_DATOS_BENEFICIARIO_REPRESENTANTE = "datosBeneficiarioRepresentante";
    private static final String PERSONA_SESSION_KEY = "persona_correccion";
    private static final Long TAMANIO_MAXIMO_ARCHIVO = 4194304L;
    private static final String SOLICITUD = "solicitud";
    private static final String STATUS_ERROR_UPLOAD = "error";
    private static final String ID_DOCUMENTO_BOVEDA = "idDocBoveda";
    private static final String LISTA_DOCUMENT_NSS = "listpreliminar";
    private static final String STATUS_SUCCESS_UPLOAD = "success";
    private static final String DATOS_ADICIONALES_HISTORIA = "datosAdicionalesHistoriaLaboral";
    private static final String DATOSHISTORIALABORAL = "datosHistoriaLaboral";
    private static final String VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL = "datosAdicionalesHistoriaLaboral";
    private static final String VIEW_RECURSO_NO_DISPONIBLE = "recursoNoDisponible";
    private static final String REDIRECTNSS = "vistaNssList";
    private static final String DOCUMENTOS_PROBATORIOS_VO = "documentosProbatoriosVo";
    private static final String DATOSHISTORIALABORALBEBEFICIARIO = "datosHistoriaLaboralBeneficiario";
    private static final String ERROR_ADJUNTAR_DOCUMENTO = "Error al adjuntar Documento. Intente nuevamente.";
    private static final String DOC_PROB_LIST = "documentoProbatorioList";
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private static final String PERSONA_BENEFICIARIO = "personaBeneficiario";

    @RequestMapping(value = "/datosNSS", method = RequestMethod.POST)
    public RedirectView datosNSS(
            @ModelAttribute("datosHistoriaLaboral") DatosHistoriaLaboralVO datosHistoriaLaboralvo,
            BindingResult result, Model model, HttpSession session,
            HttpServletResponse response) {
        log.debug("---CDA---Inicia el guardado parcial de documentos del asegurado /interesado");

        DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
                .getAttribute(DATOSHISTORIALABORAL);
        log.debug("---CDA---tiposolicitante "
                + datosHistoriaLaboral.getTipoSolicitante());
        log.debug("---CDA---defuncion " + datosHistoriaLaboral.isDefuncion());
        log.debug("---CDA---n documentos asegurado " + datosHistoriaLaboral.getDocumentoProbatorioList().size());
        Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
        try {
            sol = tramiteCorreccionUtil.actualizarXmlDocumentosAseg(sol,
                    datosHistoriaLaboral);
            session.setAttribute(SOLICITUD, sol);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + RequestMappingConstants.REGISTRO_DOCUMENTOS_PROBATORIOS
                            + "/" + REDIRECTNSS, true);
        } catch (Exception ex) {
            log.error("---CDA--- Errores al buscar el tramite {}", ex);
            model.addAttribute(DOCUMENTOS_PROBATORIOS_VO,
                    documentoProbatorioUtil
                            .getDocumentProbAsegurado(datosHistoriaLaboral
                                    .isDefuncion()));
            model.addAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + RequestMappingConstants.REGISTRO_DOCUMENTOS_PROBATORIOS
                            + "/" + DATOSHISTORIALABORAL, true);
        }
    }

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
            Solicitud solicitud = (Solicitud) ses.getAttribute(SOLICITUD);
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
                    result.put(STATUS_ERROR_UPLOAD, bce.getMensajeError());
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
                    result.put(STATUS_SUCCESS_UPLOAD, STATUS_SUCCESS_UPLOAD);
                    result.put(ID_DOCUMENTO_BOVEDA, idDocBoveda);
                    DocumentoProbatorio documento = new DocumentoProbatorio();
                    documento.setIdDocBoveda(idDocBoveda);
                    documento.setIdDocumentoPorTipo(new Long(idDocporTipo));
                    documento.setCveIdDocumento(new Long(cveIdDocumento));
                    documento.setNombre(nombreCompleto);
                    documento.setDesDocumento(value);
                    documento.setTipoDocumento(Integer.valueOf(tipoDocumento));
                    DatosHistoriaLaboralVO datoshistoriaLaboral;
                    if (ses.getAttribute(DATOSHISTORIALABORAL) == null) {
                        log.debug("---CDA--- Se inicializa el datoshistorico");
                        datoshistoriaLaboral = new DatosHistoriaLaboralVO();
                        datoshistoriaLaboral
                                .setDocumentoProbatorioList(new ArrayList<DocumentoProbatorio>());
                    } else {
                        datoshistoriaLaboral = (DatosHistoriaLaboralVO) ses
                                .getAttribute(DATOSHISTORIALABORAL);
                        if (datoshistoriaLaboral.getDocumentoProbatorioList() == null) {
                            log.debug("---CDA--- Se inicializa la lista de documentos probatorios");
                            datoshistoriaLaboral
                                    .setDocumentoProbatorioList(new ArrayList<DocumentoProbatorio>());
                        }
                    }
                    datoshistoriaLaboral = (DatosHistoriaLaboralVO) ses
                            .getAttribute(DATOSHISTORIALABORAL);
                    datoshistoriaLaboral.getDocumentoProbatorioList().add(
                            documento);
                } else {
                    log.error("---CDA--- Error en createDocument ::: el idDocBoveda es nulo");
                    result.put(STATUS_ERROR_UPLOAD, ERROR_ADJUNTAR_DOCUMENTO);
                }
            } else {
                log.error("Error al adjuntar Documento.");
                result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento.");
            }
        } else {
            log.error("El archivo no cumple con el tamaño máximo permitido [4MB]. No es posible adjuntar el archivo.");
            result.put(
                    STATUS_ERROR_UPLOAD,
                    "El archivo no cumple con el tamaño máximo permitido [4MB]. No es posible adjuntar el archivo.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);
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
        if (file.getSize() <= TAMANIO_MAXIMO_ARCHIVO) {
            Solicitud solicitud = (Solicitud) ses.getAttribute(SOLICITUD);
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
                result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento.");
            }
        } else {
            result.put(
                    STATUS_ERROR_UPLOAD,
                    "El archivo no cumple con el tamaño máximo permitido [4MB]. No es posible adjuntar el archivo.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/validarDocumentosAsegurado", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, ? extends Object> validarDocumentosAsegurado(
            @RequestBody DatosHistoriaLaboralVO datosHistor,
            HttpServletResponse response, HttpSession session, Model model) {
        log.debug("---CDA---Inicia la validacion de documentos del asegurado");

        DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
                .getAttribute(DATOSHISTORIALABORAL);
        final Errors errors = new BindException(datosHistoriaLaboral, "model");
        Map<String, Object> mapa = new HashMap<String, Object>();
        if (datosHistoriaLaboral != null) {
            if (datosHistor.getTipoSolicitante() != null) {
                String tipoSolicitate = datosHistor.getTipoSolicitante();
                log.debug("---CDA---tipo" + tipoSolicitate);
                if (tipoSolicitate
                        .equals(TipoSolicitanteEnum.ASEGURADO_PENSIONADO
                                .getDescripcion())) {
                    datosHistoriaLaboral.setTipoSolicitante(tipoSolicitate);
                    datosHistoriaLaboral.setTipoBeneficiario("");
                } else if (tipoSolicitate
                        .equals(TipoSolicitanteEnum.BENEFICIARIO
                                .getDescripcion())) {
                    log.debug("---CDA---tipo" + tipoSolicitate);
                    datosHistoriaLaboral.setTipoSolicitante(tipoSolicitate);
                } else if (tipoSolicitate
                        .equals(TipoSolicitanteEnum.REPRESENTANTE_LEGAL
                                .getDescripcion())) {
                    log.debug("---CDA---tipo" + tipoSolicitate);
                    datosHistoriaLaboral.setTipoSolicitante(tipoSolicitate);
                    datosHistoriaLaboral.setTipoBeneficiario("");
                }
                datosHistoriaLaboral.setDefuncion(datosHistor.isDefuncion());
            } else {
                errors.rejectValue("tipoSolicitante", "field.required");
            }
            if (datosHistoriaLaboral.getDocumentoProbatorioList() != null
                    && !datosHistoriaLaboral.getDocumentoProbatorioList()
                            .isEmpty()) {
                log.debug("---CDA---Inicia documentos del NSS : {}"
                        + datosHistoriaLaboral.getDocumentoProbatorioList()
                                .size());
                documentosProbatoriosValidator.validate(datosHistoriaLaboral.getDocumentoProbatorioList(),
                        errors, datosHistor.isDefuncion(), null);
                session.setAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
            } else {
                log.warn("---CDA--- Errores de captura documentoProbatorioList error");
                errors.rejectValue(DOC_PROB_LIST,
                        "field.documentoProbatorio.listaVacia");
            }
        }
        if (errors.hasErrors() || errors.hasFieldErrors(DOC_PROB_LIST)
                || errors.hasFieldErrors(DOC_PROB_LIST)) {
            log.warn("---CDA--- Errores de captura");
            procesaErroresDeCaptura(errors, mapa, response);

        }
        return mapa;
    }

    @RequestMapping(value = "/vistaNssList", method = { RequestMethod.POST,
            RequestMethod.GET })
    public String getVistaNssList(Model model, HttpSession session) {

        DatosHistoriaLaboralVO datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
                .getAttribute(DATOSHISTORIALABORAL);
        Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
        log.debug("---CDA---datosHistoriaLaboral NSS antes : "
                + datosHistoriaLaboral.getNSSList());
        datosHistoriaLaboral = tramiteCorreccionUtil.getPrecargaNssList(sol,
                datosHistoriaLaboral);
        log.debug("---CDA---datosHistoriaLaboral NSS  : "
                + datosHistoriaLaboral.getNSSList());
        session.setAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
        model.addAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
        model.addAttribute(DOCUMENTOS_PROBATORIOS_VO,
                documentoProbatorioUtil.getDocumentProbNss());
        model.addAttribute("documentoNSSClave",
                TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
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
     * @return
     */
    @RequestMapping(value = "/validar/nss/{nssLength}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, ? extends Object> documentoAsociado(
            @RequestBody NSSVO nssvo, HttpServletResponse response,
            HttpSession ses, @PathVariable Integer nssLength, Model model) {
        final Errors errors = new BindException(nssvo, "model");

        log.debug("---CDA--- ############Validar NSS#####################");

        Map<String, Object> result = new HashMap<String, Object>();
        DatosHistoriaLaboralVO datosHistoriaLaboralVO = (DatosHistoriaLaboralVO) ses
                .getAttribute(DATOSHISTORIALABORAL);
        if (nssvo != null) {
            log.debug("---CDA--- nSSValidator");
            nSSValidator.validate(nssvo, errors);
            nSSValidator.validateExisteNssInList(nssvo,
                    datosHistoriaLaboralVO.getNSSList(), errors);
            if (errors.hasErrors() || errors.hasFieldErrors("NSS")) {
                log.debug("---CDA--- procesa errores de captura NSS");
                procesaErroresDeCaptura(errors, result, response);
            }
            log.debug("---CDA--- Validacion probatorios");
            List<DocumentoProbatorio> listaDocumentoProbNSS = (List<DocumentoProbatorio>) ses
                    .getAttribute(LISTA_DOCUMENT_NSS);
            if (listaDocumentoProbNSS != null
                    && !listaDocumentoProbNSS.isEmpty()) {
                documentosProbatoriosValidator.validateDocumentosNSS(
                        listaDocumentoProbNSS, errors,null);
            } else {
                errors.rejectValue(DOC_PROB_LIST,
                        "field.documentoProbatorio.listaVacia");
            }
            if (errors.hasErrors() || errors.hasFieldErrors(DOC_PROB_LIST)) {
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
                ses.setAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboralVO);
            }
        } else {
            errors.rejectValue("NSS", "field.NSS.listaVacia");
        }
        log.debug("---CDA--- Finaliza metodo");
        return result;
    }

    @RequestMapping(value = "/eliminarNSS", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, ? extends Object> eliminarNSS(@RequestBody NSSVO nssvo,
            HttpServletResponse response, HttpSession ses, Model model) {
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
                                        .getCodigo())
                                || !confirmacion
                                        .contains(MensajesBovedaCDAEnum.MSJ_BP5004
                                                .getCodigo())) {
                            bandera = Boolean.TRUE;
                            result.put(STATUS_ERROR_UPLOAD, confirmacion);
                        }
                    } catch (BovedaCDAException bce) {
                        bandera = Boolean.TRUE;
                        log.error("---CDA-- Ocurrio un error {}", bce);
                        result.put(STATUS_ERROR_UPLOAD, bce.getMensajeError());
                    }
                }
                it.remove();
            }
        }

            Solicitud solicitud = (Solicitud) ses.getAttribute(SOLICITUD);
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
            @RequestBody DocumentoProbatorio docuemntoProbatorio,
            HttpServletResponse response, HttpSession ses, Model model) {
        log.debug("---CDA--- Documento a remover : " + docuemntoProbatorio);
        Map<String, String> result = new HashMap<String, String>();
        List<DocumentoProbatorio> listDocumento = (List<DocumentoProbatorio>) ses
                .getAttribute(LISTA_DOCUMENT_NSS);
        Iterator<DocumentoProbatorio> it = listDocumento.iterator();
        while (it.hasNext()) {
            DocumentoProbatorio doc = it.next();
            if (doc.getCveIdDocumento().equals(
                    docuemntoProbatorio.getCveIdDocumento())
                    && doc.getIdDocBoveda().equals(
                            docuemntoProbatorio.getIdDocBoveda())) {
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
                    } else {
                        result.put(STATUS_ERROR_UPLOAD, confirmacion);
                    }
                } catch (BovedaCDAException bce) {
                    log.error("---CDA-- Ocurrio un error {}", bce);
                    result.put(STATUS_ERROR_UPLOAD, bce.getMensajeError());
                }
            }
        }
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }
   
    
    @RequestMapping(value = "/validar/datosNSSDocumentos", method = RequestMethod.POST)
    public RedirectView consultaDatosHistoriaLaboral(
            @ModelAttribute("datosHistoriaLaboral") DatosHistoriaLaboralVO datosHistoriaLaboralvo,
            BindingResult result, Model model, HttpSession session)
            throws DocumentoException, SolicitudNoValidaException {
        log.debug("---CDA--- Validar NSS ingresados y documentacion probatoria");
        DatosHistoriaLaboralVO datosHistoriaLaboral;
        if (session.getAttribute(DATOSHISTORIALABORAL) != null) {
            datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
                    .getAttribute(DATOSHISTORIALABORAL);
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

        session.setAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
            Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD);
            try {
                solicitud = tramiteCorreccionUtil.crearTramiteNss(solicitud,
                        datosHistoriaLaboral);
                session.setAttribute(SOLICITUD, solicitud);
                solicitudBusiness.actualizaTramite(solicitud, solicitud
                        .getTramites().get(0));
            } catch (Exception ex) {
                this.log.warn("---CDA--- Error al crear el tramite");
                return new RedirectView(
                        RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                                + RequestMappingConstants.REGISTRO_DOCUMENTOS_PROBATORIOS
                                + "/recuersoNoDisp", true);
            }
        model.addAttribute(DATOS_ADICIONALES_HISTORIA,
                datosAdicionalesHistoriaLaboral);
        if (datosHistoriaLaboral.getTipoSolicitante().equals(
                TipoSolicitanteEnum.ASEGURADO_PENSIONADO.getDescripcion())) {
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + RequestMappingConstants.REGISTRO_DOCUMENTOS_PROBATORIOS
                            + "/datosAdicionales", true);
        } else {
            return new RedirectView(
                    RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE
                            + RequestMappingConstants.REGISTRO_DOCUMENTOS_PROBATORIOS
                            + "/datosBeneficiarios", true);
        }

    }

    @RequestMapping(value = "/datosAdicionales", method = { RequestMethod.POST,
            RequestMethod.GET })
    public String getVistaDatosAdicionales(Model model, HttpSession session) {
        DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral;
        if (session.getAttribute(DATOS_ADICIONALES_HISTORIA) != null) {
            datosAdicionalesHistoriaLaboral = (DatosAdicionalesHistoriaLaboral) session
                    .getAttribute(DATOS_ADICIONALES_HISTORIA);
        } else {
            datosAdicionalesHistoriaLaboral = new DatosAdicionalesHistoriaLaboral();
        }
        model.addAttribute(DATOS_ADICIONALES_HISTORIA,
                datosAdicionalesHistoriaLaboral);
        return VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
    }

    @RequestMapping(value = "/datosBeneficiarios", method = {
            RequestMethod.POST, RequestMethod.GET })
    public String getVistaBenefRepresentante(Model model, HttpSession session) {
        log.debug("---CDA--- getVistaBenefRepresentante");
        DatosHistoriaLaboralVO datosHistorial = (DatosHistoriaLaboralVO) session
                .getAttribute(DATOSHISTORIALABORAL);
        DatosHistoriaLaboralVO datos;
        if (session.getAttribute(DATOSHISTORIALABORALBEBEFICIARIO) == null) {
            Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
            datos = new DatosHistoriaLaboralVO();
            datos = tramiteCorreccionUtil.getPreCargaInteresado(sol, datos,
                    datosHistorial.getTipoSolicitante());
            session.setAttribute(PERSONA_BENEFICIARIO,
                    tramiteCorreccionUtil.getCargaCURPInteresado(sol));
        } else {
            datos = (DatosHistoriaLaboralVO) session
                    .getAttribute(DATOSHISTORIALABORALBEBEFICIARIO);
            if (!datos.getTipoSolicitante().equals(
                    datosHistorial.getTipoSolicitante())) {
                log.debug("---CDA--- Antes era : " + datos.getTipoSolicitante()
                        + " antes " + datosHistorial.getTipoSolicitante());
                if( datos.getDocumentoProbatorioList()!=null){
                datos.getDocumentoProbatorioList().clear();
                }
                datos.setTipoBeneficiario("");
                session.setAttribute(PERSONA_BENEFICIARIO, null);
            }
        }
        datos.setDefuncion(datosHistorial.isDefuncion());
        datos.setTipoSolicitante(datosHistorial.getTipoSolicitante());
        model.addAttribute(DOCUMENTOS_PROBATORIOS_VO, documentoProbatorioUtil
                .getDocumentProbBeneficiario(datos.getTipoSolicitante(),
                        datos.getTipoBeneficiario()));
        session.setAttribute(DATOSHISTORIALABORALBEBEFICIARIO, datos);
        model.addAttribute(DATOSHISTORIALABORALBEBEFICIARIO, datos);
        model.addAttribute(PERSONA_BENEFICIARIO,
                session.getAttribute(PERSONA_BENEFICIARIO));
        return VIEW_DATOS_BENEFICIARIO_REPRESENTANTE;
    }

    @RequestMapping(value = "/recuersoNoDisp", method = { RequestMethod.POST,
            RequestMethod.GET })
    public String getVistaRecursoNoDisp(Model model, HttpSession session) {
        this.log.warn("---CDA--- Error al crear el tramite");
        return VIEW_RECURSO_NO_DISPONIBLE;
    }

    @RequestMapping(value = "/eliminarDocumentoAsegurado", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, String>> eliminarDocumentoAsegurado(
            @RequestBody DocumentoProbatorio docuemntoProbatorio,
            HttpServletResponse response, HttpSession ses, Model model) {
        log.debug("---CDA--- Documento a remover : " + docuemntoProbatorio);
        Map<String, String> result = new HashMap<String, String>();
        DatosHistoriaLaboralVO datosHistoria = (DatosHistoriaLaboralVO) ses
                .getAttribute(DATOSHISTORIALABORAL);
        Iterator<DocumentoProbatorio> it = datosHistoria
                .getDocumentoProbatorioList().iterator();
        while (it.hasNext()) {
            DocumentoProbatorio doc = it.next();
            if (doc.getCveIdDocumento().equals(
                    docuemntoProbatorio.getCveIdDocumento())
                    && doc.getIdDocBoveda().equals(
                            docuemntoProbatorio.getIdDocBoveda())) {
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
                    } else {
                        result.put(STATUS_ERROR_UPLOAD, confirmacion);
                    }
                } catch (BovedaCDAException bce) {
                    log.error("---CDA-- Ocurrio un error {}", bce);
                    result.put(STATUS_ERROR_UPLOAD, bce.getMensajeError());
                }
            }
        }
        model.addAttribute(DATOSHISTORIALABORAL, datosHistoria);
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }

    private void addListDocumentoProbatorioNSS(DocumentoProbatorio documento,
            HttpSession ses) {
        if (ses.getAttribute(LISTA_DOCUMENT_NSS) != null) {
            log.debug("---CDA--- Se agrega documentacion preliminar");
            List<DocumentoProbatorio> listaDocumentoProbNSS = (List<DocumentoProbatorio>) ses
                    .getAttribute(LISTA_DOCUMENT_NSS);
            listaDocumentoProbNSS.add(documento);
            ses.setAttribute(LISTA_DOCUMENT_NSS, listaDocumentoProbNSS);
        } else {
            log.debug("---CDA--- Se crea lista de documentacion preliminar");
            List<DocumentoProbatorio> listaDocumentoProbNSS = new ArrayList<DocumentoProbatorio>();
            listaDocumentoProbNSS.add(documento);
            ses.setAttribute(LISTA_DOCUMENT_NSS, listaDocumentoProbNSS);
        }

    }

    @RequestMapping(value = "/datosHistoriaLaboral")
    public String consultaDatosHistoriaLaboral(Model model,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {

        DatosHistoriaLaboralVO datosHistoriaLaboralVO = (DatosHistoriaLaboralVO) session
                .getAttribute(DATOSHISTORIALABORAL);
        
        Fisica fisica = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);
        if(fisica != null)
        {   this.log.error("---CDA--- idPersona fisica en sesion: " +fisica.getIdPersona());
        }else
        {
            this.log.error("---CDA--- idPersona fisica en sesion: sin persona en sesion");
        }

        if (datosHistoriaLaboralVO.getTipoSolicitante().equals(
                TipoSolicitanteEnum.BENEFICIARIO.getDescripcion())
                || datosHistoriaLaboralVO.getTipoSolicitante().equals(
                        TipoSolicitanteEnum.REPRESENTANTE_LEGAL
                                .getDescripcion())) {
            DatosHistoriaLaboralVO datosHistBenef = (DatosHistoriaLaboralVO) session
                    .getAttribute(DATOSHISTORIALABORALBEBEFICIARIO);
            model.addAttribute(DOCUMENTOS_PROBATORIOS_VO,
                    documentoProbatorioUtil.getDocumentProbBeneficiario(
                            datosHistBenef.getTipoSolicitante(),
                            datosHistBenef.getTipoBeneficiario()));

            model.addAttribute(DATOSHISTORIALABORALBEBEFICIARIO, datosHistBenef);
            model.addAttribute(PERSONA_BENEFICIARIO,
                    session.getAttribute(PERSONA_BENEFICIARIO));
            return VIEW_DATOS_BENEFICIARIO_REPRESENTANTE;
        } else {
            cargarPantallaDatosNss(model, session);
            return VIEW_DATOS_NSS;
        }

    }

    private void cargarPantallaDatosNss(Model model, HttpSession session) {
        model.addAttribute(DATOSHISTORIALABORAL,
                (DatosHistoriaLaboralVO) session
                        .getAttribute(DATOSHISTORIALABORAL));
        model.addAttribute(DOCUMENTOS_PROBATORIOS_VO,
                documentoProbatorioUtil.getDocumentProbNss());
    }

    @RequestMapping(value = "/obtenerDocsProbatorios", method = RequestMethod.POST)
    @ResponseBody
    public Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> obtenerDocumentosAsegurado(
            @RequestBody DatosHistoriaLaboralVO datosHistoriaLaboralVO,
            HttpServletResponse response, HttpSession session) {
        log.debug("************es defuncion o no "
                + datosHistoriaLaboralVO.isDefuncion());
        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos;
        doctos = documentoProbatorioUtil
                .getDocumentProbAsegurado(datosHistoriaLaboralVO.isDefuncion());
        return doctos;

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
        if (session.getAttribute(DATOSHISTORIALABORAL) != null) {
            datosHistoriaLaboral = (DatosHistoriaLaboralVO) session
                    .getAttribute(DATOSHISTORIALABORAL);
        } else {
            datosHistoriaLaboral = new DatosHistoriaLaboralVO();
        }
        datosHistoriaLaboralValidator.validarNssList(datosHistoriaLaboral,
                errors);
        session.setAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
        if (errors.hasErrors()) {
            this.log.warn("---CDA--- Errores de captura");
            model.addAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
            procesaErroresDeCaptura(errors, mapa, response);
        }
        return mapa;

    }
}
