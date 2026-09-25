/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.informacionadicional.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.cit.cda.web.enums.RespuestaPeticionEnum;
import mx.gob.imss.cit.cda.web.helper.DocumentoProbatorioHelper;
import mx.gob.imss.cit.cda.web.informacionadicional.constants.InformacionAdicionalConstants;
import mx.gob.imss.cit.cda.web.informacionadicional.helper.InformacionAdicionalHelper;
import mx.gob.imss.cit.cda.web.informacionadicional.vo.InformacionAdicionalSolicitudVO;
import mx.gob.imss.cit.cda.web.utils.DocumentoProbatorioUtil;
import mx.gob.imss.cit.cda.web.utils.RegistroCorreccionCurpUtil;
import mx.gob.imss.cit.cda.web.utils.TramiteCorreccionUtil;
import mx.gob.imss.cit.cda.web.vo.ConsultaSolicitudTramiteVO;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador donde se realiza todas las operaciones de la pantalla informacion
 * adicional
 * 
 * @author aldair.vidal
 *
 */
@Controller
@RequestMapping(value = "/wizard/correccionDatosAsegurado/informacionAdicional")
@SessionAttributes(value = { "idPersona", "solicitud", "personaCorreccion" })
public class InformacionAdicionalController extends AbstractController {

    @Autowired
    private InformacionAdicionalHelper infomacionAdicionalHelper;

    @Autowired
    private DocumentoProbatorioHelper documentoProbatorioHelper;

    @Autowired
    private DocumentoProbatorioUtil documentoProbatoriosUtils;

    @Autowired
    private TramiteCorreccionUtil tramiteCorreccionUtil;

    @Autowired
    private RegistroCorreccionCurpUtil registroCorreccionCurpUtil;

    /**
     * Metodo que muetra el mensaje de informacion adicional que necesita el
     * responsable por parte del asegurado para finalizar el tramite
     * 
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "")
    public String inicioInformacionAdiconal(Model model, HttpSession session) {
        log.debug("Se inicia informacion adicional");
        session.removeAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL);
        model.addAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL, "");
        Solicitud sol = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        Fisica persona = (Fisica) session
                .getAttribute(InformacionAdicionalConstants.PERSONA_SESSION_KEY);
        TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp) sol
                .getTramites().get(0);
        ConsultaSolicitudTramiteVO informacionConsulta = registroCorreccionCurpUtil
                .procesarInformacionConsulta(persona, sol, tramiteCDA);
        model.addAttribute(SessionConstants.ATTR_INFORMACION_CONSULTA,
                informacionConsulta);
        session.setAttribute(SessionConstants.ATTR_INFORMACION_CONSULTA,
                informacionConsulta);
        return InformacionAdicionalConstants.VIEW_REQUIERE_INFO_ADICIONAL;
    }

    /**
     * Prepara la vista donde se registrará la información adicional
     * 
     * @param model
     * @param session
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/correcionSolicitud", method = {
            RequestMethod.GET, RequestMethod.POST })
    public String correcionSolicitud(Model model, HttpSession session) {
        log.debug("Se inicia informacion adicional captura nss");
        Solicitud solicitudActiva = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        InformacionAdicionalSolicitudVO informacionSolicitud = infomacionAdicionalHelper
                .getSolicitudByInfoAdicional(solicitudActiva);
        session.setAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL,
                informacionSolicitud);
        model.addAttribute(SessionConstants.COMBO_DOCUMENTOS_ASEGURADO,
                documentoProbatoriosUtils
                        .getDocumentProbAsegurado(informacionSolicitud
                                .isDefuncion()));
        model.addAttribute(SessionConstants.COMBO_DOCUMENTOS_BENEFICIARIO,
                documentoProbatoriosUtils.getDocumentProbBeneficiario(
                        informacionSolicitud.getTipoSolicitante(),
                        informacionSolicitud.getTipoBeneficiario()));
        model.addAttribute(SessionConstants.COMBO_DOCUMENTOS_NSS,
                documentoProbatoriosUtils.getDocumentProbNss());
        model.addAttribute(SessionConstants.ATTR_INFORMACION_CONSULTA,
                session.getAttribute(SessionConstants.ATTR_INFORMACION_CONSULTA));
        model.addAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL,
                informacionSolicitud);
        model.addAttribute(SessionConstants.ATTR_LISTA_DOCUMENT_TEMP,
                new ArrayList<DocumentoProbatorio>());
        log.debug("Se carga la vista");
        return InformacionAdicionalConstants.VIEW_CORRECION_INFO_ADICIONAL;
    }

    @RequestMapping(value = "/concluyeInformacionAdicional", method = {
            RequestMethod.GET, RequestMethod.POST })
    public String concluyeInformacionAdicional(Model model,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        Solicitud solicitudActiva = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        infomacionAdicionalHelper.getSolicitudByInfoAdicional(solicitudActiva);
        return InformacionAdicionalConstants.VIEW_CORRECION_INFO_ADICIONAL;
    }

    /**
     * Valida toda la informacion de adicional para su persistencia
     * 
     * @param nssvo
     * @param response
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/validacionInformacionAdicional", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, ? extends Object> validacionInformacionAdicional(HttpServletResponse response,
            HttpSession session, Model model , @RequestBody InformacionAdicionalSolicitudVO informacionAdicionalObserv) {
        InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO = (InformacionAdicionalSolicitudVO) session
                .getAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL);
        Solicitud solicitudActiva = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        Errors errors = new BindException(
                informacionAdicionalSolicitudVO, "model");
        log.debug("---CDA--- Validacion Informacion Adicional");
        log.debug("---CDA--- observaciones "+informacionAdicionalObserv.getObservaciones());
        Map<String, Object> mapa = new HashMap<String, Object>();
        if (informacionAdicionalSolicitudVO != null) {
            errors = infomacionAdicionalHelper.validarInformacionAdicional(
                    informacionAdicionalSolicitudVO, errors, 
                    informacionAdicionalObserv.isBanderaAsegurado(),informacionAdicionalObserv.isBanderaBeneficiario(),informacionAdicionalObserv.isBanderaNss());
        }
        if (errors.hasErrors()
                || errors.hasFieldErrors(InformacionAdicionalConstants.FIELD_ERRORS_LIST_NSS)
                || errors.hasFieldErrors(InformacionAdicionalConstants.FIELD_ERRORS_DOCUMENTO_PROBATORIO_LIST)
                || errors.hasFieldErrors(InformacionAdicionalConstants.FIELD_ERRORS_DOCUMENTO_PROBATORIO_ASEGURADO_LIST)
                || errors.hasFieldErrors(InformacionAdicionalConstants.FIELD_ERRORS_DOCUMENTO_PROBATORIO_BENEFICIARIO_LIST)) {
            log.warn("---CDA--- Errores de captura");
            procesaErroresDeCaptura(errors, mapa, response);

        } else {
            try {
                log.debug("---CDA--- asegurados "+informacionAdicionalSolicitudVO.getDocumentoProbatorioAseguradoList());
                log.debug("---CDA--- beneficiario "+informacionAdicionalSolicitudVO.getDocumentoProbatorioBeneficiarioList());
                log.debug("---CDA--- nss "+informacionAdicionalSolicitudVO.getNssvo());
                log.debug("---CDA--- solicitudActiva "+solicitudActiva);
                log.debug("---CDA--- solicitudActiva.getTramites().get(0) "+solicitudActiva.getTramites().get(0));
                log.debug("---CDA--- solicitudActiva.getTramites().get(0).getPersona() "+solicitudActiva.getTramites().get(0).getPersona());
                
                
                
                informacionAdicionalSolicitudVO.setObservaciones(informacionAdicionalObserv.getObservaciones()!= null ? informacionAdicionalObserv.getObservaciones(): "");
               
                TramiteCorreccionCurp tramiteCorreccionCurp = (TramiteCorreccionCurp) solicitudActiva.getTramites().get(0);
                
                log.debug("---CDA--- tramiteCorreccionCurp.getPersonaRENAPO().getNombreCompleto() "+tramiteCorreccionCurp.getPersonaRENAPO().getNombreCompleto());
                
               Solicitud solicitudInformacionAdicional=infomacionAdicionalHelper.getGuardarDocumentosAdicionales(
                                    solicitudActiva, informacionAdicionalSolicitudVO,"");
               session
               .setAttribute(SessionConstants.ATTR_SOLICITUD,solicitudInformacionAdicional);
				
            }catch (SolicitudNoEncontradaException e) {
                log.debug(
                        "---CDA--- Errores de captura TramiteNoEncontradoException",
                        e);
                mapa.put(
                        RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
                        "No se encontro el tramite a asociar.");
            } catch (TramiteNoEncontradoException e) {
                log.debug(
                        "---CDA--- Errores de captura TramiteNoEncontradoException",
                        e);
                mapa.put(
                        RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
                        "No se encontro el tramite a asociar.");
            } catch (IllegalArgumentException e) {
                log.debug("---CDA--- Errores de capturar documentos de informacion adicional :",e);
                mapa.put(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
                        "Errores de capturar documentos de informacion adicional.");
            } /*catch (DocumentoProbatorioException e) {
				// TODO Auto-generated catch block
            	  mapa.put(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
                          "Errores de capturar documentos de informacion adicional.");
			}*/
        }
        return mapa;
    }

    /**
     * Valida si se selecciono un NSS asociado y al menos un documento
     * probatorio para relacionarlos y mostrarlos en otra seccion de la vista(si
     * este metodo no retorna un error el cambio en vista se realiza por jquery)
     * 
     * @param nssvo
     * @param response
     * @param ses
     * @param model
     * @param idPersona
     * @param resultValidate
     * @return
     */
    @RequestMapping(value = "/validarNss", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, ? extends Object> documentoAsociado(HttpSession ses,
            Model model, HttpServletResponse response, @RequestBody NSSVO nssvo) {
        log.debug("Inicia validcion de nss");
        Errors errors = new BindException(nssvo, "model");
        Map<String, Object> result = new HashMap<String, Object>();
        List<DocumentoProbatorio> listaDocumentoProbNSS = (List<DocumentoProbatorio>) ses
                .getAttribute(SessionConstants.ATTR_LISTA_DOCUMENT_TEMP);
        log.debug("documnetos : "+ listaDocumentoProbNSS.toString());
        errors = infomacionAdicionalHelper
                .validarNssDocumentos(
                        nssvo,
                        listaDocumentoProbNSS,
                        errors,
                        InformacionAdicionalConstants.FIELD_ERRORS_DOCUMENTOS_NSS_LISTA_TEMP);
        if (errors.hasErrors()
                || errors
                        .hasFieldErrors(InformacionAdicionalConstants.FIELD_ERRORS_NSS)
                || errors
                        .hasFieldErrors(InformacionAdicionalConstants.FIELD_ERRORS_DOCUMENTOS_NSS_LISTA_TEMP)) {
            procesaErroresDeCaptura(errors, result, response);
        } else {
            nssvo.setDocumentoProbatorioList(listaDocumentoProbNSS);
            InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO = (InformacionAdicionalSolicitudVO) ses
                    .getAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL);
            List<NSSVO> listNss = informacionAdicionalSolicitudVO.getListNSS() != null ? informacionAdicionalSolicitudVO
                    .getListNSS() : new ArrayList<NSSVO>();
            listNss.add(nssvo);
            informacionAdicionalSolicitudVO.setListNSS(listNss);
            log.debug("listanss : "+ listNss.toString());
            ses.setAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL,
                    informacionAdicionalSolicitudVO);
        }
        return result;
    }

    /**
     * Adjunta un documento del asegurado, si retorna el id de boveda se agrega
     * el DocumentoProbatorio a la lista de documentos pertenecientes al
     * asegurado, al final la lista se actualiza en sesion y la vista se
     * actualiza con jquery
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
    @RequestMapping(value = "/adjuntarDocumentoAsegurado")
    public ResponseEntity<Map<String, Object>> adjuntarDocumentoAsegurado(
            @RequestParam("fileDataAsegurado") MultipartFile file,
            @RequestParam("idDocPorTipo") String idDocporTipo,
            @RequestParam("cveIdDocumento") String cveIdDocumento,
            @RequestParam("desDocumento") String desDocumento,
            @RequestParam("tipoDocumento") String tipoDocumento,
            HttpSession ses, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        
        Solicitud solicitud = (Solicitud) ses
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        Map<String, Object> result = documentoProbatorioHelper
                .adjuntarDocumentoBoveda(file, idDocporTipo, cveIdDocumento,
                        desDocumento, tipoDocumento, solicitud,
                        InformacionAdicionalConstants.TAMANIO_MAXIMO_ARCHIVO);
        if (result.get(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                .getDescripcion()) == null
                && result.get(RespuestaPeticionEnum.ID_DOCUMENTO_BOVEDA
                        .getDescripcion()) != null) {
            String idDocBoveda = (String) result
                    .get(RespuestaPeticionEnum.ID_DOCUMENTO_BOVEDA
                            .getDescripcion());
            log.debug(new StringBuilder("---CDA--- ID_DOCUMENTO_BOVEDA ASEGURADO")
                    .append(idDocBoveda).toString());
            InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO = (InformacionAdicionalSolicitudVO) ses
                    .getAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL);
            DocumentoProbatorio documentoProbatorio = tramiteCorreccionUtil
                    .convertDocumentoProbatorioVo(file, desDocumento,
                            idDocporTipo, cveIdDocumento, tipoDocumento,
                            idDocBoveda);
            if(informacionAdicionalSolicitudVO.getDocumentoProbatorioAseguradoList() == null){
                informacionAdicionalSolicitudVO.setDocumentoProbatorioAseguradoList(new ArrayList<DocumentoProbatorio>());
            }
            informacionAdicionalSolicitudVO
                    .getDocumentoProbatorioAseguradoList().add(
                            documentoProbatorio);
            ses.setAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL,informacionAdicionalSolicitudVO);
            log.debug(new StringBuilder("---CDA--- LISTA DE ASEGURADO ").append(informacionAdicionalSolicitudVO.getDocumentoProbatorioAseguradoList()));
        } else {
            log.debug(new StringBuilder("---CDA--- STATUS_ERROR_UPLOAD ")
                    .append(result
                            .get(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                                    .getDescripcion())).toString());
//            result.put(
//                    RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
//                    "Error al adjuntar Documento.");
        }
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);
    }

    /**
     * Adjunta un documento del beneficiario, si retorna el id de boveda se
     * agrega el DocumentoProbatorio a la lista de documentos pertenecientes al
     * beneficiario, al final la lista se actualiza en sesion y la vista se
     * actualiza con jquery
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
    @RequestMapping(value = "/adjuntarDocumentoBeneficiario")
    public ResponseEntity<Map<String, Object>> adjuntarDocumentoBeneficiario(
            @RequestParam("fileData") MultipartFile file,
            @RequestParam("idDocPorTipo") String idDocporTipo,
            @RequestParam("cveIdDocumento") String cveIdDocumento,
            @RequestParam("desDocumento") String desDocumento,
            @RequestParam("tipoDocumento") String tipoDocumento,
            HttpSession ses, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
         
        Solicitud solicitud = (Solicitud) ses
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        Map<String, Object> result = documentoProbatorioHelper
                .adjuntarDocumentoBoveda(file, idDocporTipo, cveIdDocumento,
                        desDocumento, tipoDocumento, solicitud,
                        InformacionAdicionalConstants.TAMANIO_MAXIMO_ARCHIVO);
        if (result.get(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                .getDescripcion()) == null
                && result.get(RespuestaPeticionEnum.ID_DOCUMENTO_BOVEDA
                        .getDescripcion()) != null) {
            String idDocBoveda = (String) result
                    .get(RespuestaPeticionEnum.ID_DOCUMENTO_BOVEDA
                            .getDescripcion());
            log.debug(new StringBuilder("---CDA--- ID_DOCUMENTO_BOVEDA BENEFICIARIO ")
                    .append(idDocBoveda).toString());
            InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO = (InformacionAdicionalSolicitudVO) ses
                    .getAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL);
            DocumentoProbatorio documentoProbatorio = tramiteCorreccionUtil
                    .convertDocumentoProbatorioVo(file, desDocumento,
                            idDocporTipo, cveIdDocumento, tipoDocumento,
                            idDocBoveda);
            if(informacionAdicionalSolicitudVO.getDocumentoProbatorioBeneficiarioList() == null){
                informacionAdicionalSolicitudVO.setDocumentoProbatorioBeneficiarioList(new ArrayList<DocumentoProbatorio>());
            }
            informacionAdicionalSolicitudVO
                    .getDocumentoProbatorioBeneficiarioList().add(
                            documentoProbatorio);
            ses.setAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL,informacionAdicionalSolicitudVO);
        } else {
            log.debug(new StringBuilder("---CDA--- STATUS_ERROR_UPLOAD ")
                    .append(result
                            .get(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                                    .getDescripcion())).toString());
//            result.put(
//                    RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
//                    "Error al adjuntar Documento.");
        }
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);

    }

    /**
     * Adjunta un documento del nss, si retorna el id de boveda se agrega el
     * DocumentoProbatorio a la lista de documentos pertenecientes al nss, al
     * final la lista se actualiza en sesion y la vista se actualiza con jquery
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
    @RequestMapping(value = "/adjuntarDocumentoNss")
    public ResponseEntity<Map<String, Object>> adjuntarDocumentoNss(
            @RequestParam("fileDataDocNss") MultipartFile file,
            @RequestParam("idDocPorTipo") String idDocporTipo,
            @RequestParam("cveIdDocumento") String cveIdDocumento,
            @RequestParam("desDocumento") String desDocumento,
            @RequestParam("tipoDocumento") String tipoDocumento,
            HttpSession ses, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        Solicitud solicitud = (Solicitud) ses
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        Map<String, Object> result = documentoProbatorioHelper
                .adjuntarDocumentoBoveda(file, idDocporTipo, cveIdDocumento,
                        desDocumento, tipoDocumento, solicitud,
                        InformacionAdicionalConstants.TAMANIO_MAXIMO_ARCHIVO);

        if (result.get(RespuestaPeticionEnum.STATUS_SUCCESS_UPLOAD
                .getDescripcion()) != null
                && result.get(RespuestaPeticionEnum.ID_DOCUMENTO_BOVEDA
                        .getDescripcion()) != null) {

            String idDocBoveda = (String) result
                    .get(RespuestaPeticionEnum.ID_DOCUMENTO_BOVEDA.getDescripcion());
            log.debug(new StringBuilder("---CDA--- ID_DOCUMENTO_BOVEDA NSS")
                    .append(idDocBoveda).toString());
            List<DocumentoProbatorio> documentosProbaNsstemp = (List<DocumentoProbatorio>) ses
                    .getAttribute(SessionConstants.ATTR_LISTA_DOCUMENT_TEMP);
            if (documentosProbaNsstemp == null) {
                documentosProbaNsstemp = new ArrayList<DocumentoProbatorio>();
            }
            DocumentoProbatorio documentoProbatorio = tramiteCorreccionUtil
                    .convertDocumentoProbatorioVo(file, desDocumento,
                            idDocporTipo, cveIdDocumento, tipoDocumento,
                            idDocBoveda);
            documentosProbaNsstemp.add(documentoProbatorio);
            ses.setAttribute(SessionConstants.ATTR_LISTA_DOCUMENT_TEMP,
                    documentosProbaNsstemp);

        } else {
            log.debug(new StringBuilder("---CDA--- STATUS_ERROR_UPLOAD ")
                    .append(result
                            .get(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                                    .getDescripcion())).toString());
//            result.put(
//                    RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
//                    "Error al adjuntar Documento. Intente nuevamente.");

        }
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);
    }

    /**
     * Elimina un NSS y los documentos probatorios asociados(se elimina cada
     * documentos en boveda) y actualiza sesion
     * 
     * @param nssvo
     * @param response
     * @param ses
     * @param model
     * @param idPersona
     * @param resultValidate
     * @return
     */
    @RequestMapping(value = "/eliminarNSS", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String, ? extends Object> eliminarNSS(
            @RequestBody NSSVO nssvo, HttpServletResponse response,
            HttpSession ses, Model model,
            @ModelAttribute("idPersona") Long idPersona) {
        Map<String, Object> result = new HashMap<String, Object>();
        InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO = (InformacionAdicionalSolicitudVO) ses
                .getAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL);
        Solicitud solicitud = (Solicitud) ses
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        result = documentoProbatorioHelper.eliminaNssInformacionAdicional(
                informacionAdicionalSolicitudVO.getListNSS(), result, nssvo,
                solicitud);

        if (result.get(RespuestaPeticionEnum.STATUS_SUCCESS_UPLOAD
                .getDescripcion()) != null) {
            /*
             * Fue exitoso la eliminacion de del NSS y sus documentos, se
             * actualiza en sesion los nss
             */
            log.debug(new StringBuilder(
                    "--CDA---InformacionAdicional tiene los NSS : ")
                    .append(informacionAdicionalSolicitudVO.getListNSS()));
            ses.setAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL,
                    informacionAdicionalSolicitudVO);
        }
        log.debug("---CDA--- Finaliza metodo");
        return result;
    }

    /**
     * Elimina el documento en boveda, sí fue exitoso actualiza se actualiza la
     * vista(jquery) y la sesion
     * 
     * @param docuemntoProbatorio
     * @param response
     * @param ses
     * @param model
     * @param idPersona
     * @return
     */
    @RequestMapping(value = "/eliminarDocProbBeneficiario", method = RequestMethod.POST)
    @ResponseBody 
    public ResponseEntity<Map<String, Object>> eliminarDocumentoProbatorio(
            @RequestBody DocumentoProbatorio docuemntoProbatorio,
            HttpServletResponse response, HttpSession ses, Model model,
            @ModelAttribute("idPersona") Long idPersona) {
        log.debug("---CDA--- Documento a remover : {} " + docuemntoProbatorio);
        Map<String, Object> result = new HashMap<String, Object>();
        InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO = (InformacionAdicionalSolicitudVO) ses
                .getAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL);
        result = documentoProbatorioHelper.removerDocumentoByList(
                informacionAdicionalSolicitudVO
                        .getDocumentoProbatorioBeneficiarioList(), result,
                docuemntoProbatorio);
        ses.setAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL,
                informacionAdicionalSolicitudVO);
        model.addAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL,
                informacionAdicionalSolicitudVO);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    /**
     * Elimina de boveda un documento probatorio de boveda, sí es exitoso
     * actualiza la vista y la sesion
     * 
     * @param docuemntoProbatorio
     * @param response
     * @param ses
     * @param model
     * @param idPersona
     * @return
     */
    @RequestMapping(value = "/eliminarDocProbAsegurado", method = RequestMethod.POST)
    @ResponseBody 
    public ResponseEntity<Map<String, Object>> eliminarDocumentoAsegurado(
            @RequestBody DocumentoProbatorio docuemntoProbatorio,
            HttpServletResponse response, HttpSession ses, Model model,
            @ModelAttribute("idPersona") Long idPersona) {
        log.debug(new StringBuilder("---CDA--- Documento a remover : {} ")
                .append(docuemntoProbatorio).toString());
       
        Map<String, Object> result = new HashMap<String, Object>();
        InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO = (InformacionAdicionalSolicitudVO) ses
                .getAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL);

        log.debug(new StringBuilder(
                "--CDA---InformacionAdicional tiene los documentos : ")
                .append(informacionAdicionalSolicitudVO.getDocumentoProbatorioAseguradoList()));
        result = documentoProbatorioHelper.removerDocumentoByList(
                informacionAdicionalSolicitudVO
                        .getDocumentoProbatorioAseguradoList(), result,
                docuemntoProbatorio);
        ses.setAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL,
                informacionAdicionalSolicitudVO);
        model.addAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL,
                informacionAdicionalSolicitudVO);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    /**
     * Elimina de boveda un documento probatorio de boveda, sí es exitoso
     * actualiza la vista y la sesion
     * 
     * @param docuemntoProbatorio
     * @param response
     * @param ses
     * @param model
     * @param idPersona
     * @return
     */
    @RequestMapping(value = "/eliminarDocProbTempNss", method = RequestMethod.POST)
    @ResponseBody 
    public ResponseEntity<Map<String, Object>> eliminarDocProbTempNss(
            @RequestBody DocumentoProbatorio docuemntoProbatorio,
            HttpServletResponse response, HttpSession ses, Model model,
            @ModelAttribute("idPersona") Long idPersona) {
        log.debug(new StringBuilder("---CDA--- Documento a remover : ").append(
                docuemntoProbatorio).toString());
        Map<String, Object> result = new HashMap<String, Object>();
        List<DocumentoProbatorio> listDocumentoTemp = (List<DocumentoProbatorio>) ses
                .getAttribute(SessionConstants.ATTR_LISTA_DOCUMENT_TEMP);
        log.debug(new StringBuilder(
                "--CDA---InformacionAdicional tiene los documentos en temp : ")
                .append(listDocumentoTemp));
        result = documentoProbatorioHelper.removerDocumentoByList(
                listDocumentoTemp, result, docuemntoProbatorio);
        ses.setAttribute(SessionConstants.ATTR_LISTA_DOCUMENT_TEMP, listDocumentoTemp);
        model.addAttribute(SessionConstants.ATTR_LISTA_DOCUMENT_TEMP,listDocumentoTemp);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    
    
    /**
     * Finaliza la infomacion adicionar que se agregara
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/finalizaInformacionAdicional", method = RequestMethod.POST)
    public String finalizaInformacionAdicional(HttpSession session, Model model) {
      /* cambia de estado y envia correo*/
        Solicitud solicitudActiva = (Solicitud) session
                .getAttribute(SessionConstants.ATTR_SOLICITUD);
        InformacionAdicionalSolicitudVO informacion= (InformacionAdicionalSolicitudVO) session.getAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL);
        infomacionAdicionalHelper.cambiaEstadoSolicitud(solicitudActiva,informacion.getObservaciones());
        Fisica persona = (Fisica) session
                .getAttribute(SessionConstants.ATTR_PERSONA_KEY);
        TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp) solicitudActiva
                .getTramites().get(0);

        solicitudActiva.setPersonaInteresada(tramiteCDA.getPersonaRENAPO());
        log.debug("FECHA DE FLUJO TRABAJO 2: " + solicitudActiva.getFechaSolicitudParse());

        ConsultaSolicitudTramiteVO informacionConsulta = registroCorreccionCurpUtil.procesarInformacionConsulta(persona, solicitudActiva, tramiteCDA);
        informacionConsulta.setIdEstadoTramite(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE_ATENDIDA.getCodigo() );
        model.addAttribute(SessionConstants.ATTR_INFORMACION_CONSULTA, informacionConsulta);
        return InformacionAdicionalConstants.VIEW_SEGUIMIENTO_TRAMITE;
    }
    
    /**
     * Cancela el proceso de infomacion adicional
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/cancelaInformacionAdicional", method = RequestMethod.POST)
    public String cancelaInformacionAdicional(HttpSession session, Model model) {
        /*se limpia session */
        
        session.removeAttribute(SessionConstants.ATTR_INFORMACION_ADICIONAL);
        session.removeAttribute(SessionConstants.ATTR_LISTA_DOCUMENT_TEMP);
        ConsultaSolicitudTramiteVO informacionConsulta = (ConsultaSolicitudTramiteVO) session.getAttribute(SessionConstants.ATTR_INFORMACION_CONSULTA);
        model.addAttribute(SessionConstants.ATTR_INFORMACION_CONSULTA, informacionConsulta);
        return InformacionAdicionalConstants.VIEW_SEGUIMIENTO_TRAMITE;
    }

}
