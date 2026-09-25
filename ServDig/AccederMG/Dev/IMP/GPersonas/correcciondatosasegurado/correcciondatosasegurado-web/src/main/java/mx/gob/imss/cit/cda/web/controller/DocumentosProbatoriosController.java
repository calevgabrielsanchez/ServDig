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
import mx.gob.imss.cit.cda.web.constants.RequestMappingConstants;
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
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
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
@SessionAttributes(value = {"idPersona", "sol", "personaCorreccion"})
public class DocumentosProbatoriosController extends AbstractController {

    private final Logger log = LoggerFactory.getLogger(DocumentosProbatoriosController.class);

    @Autowired
    private NSSValidator nSSValidator;

    @Autowired
    @Qualifier("documentoProbatorioServiceBusiness")
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;
    
    @Autowired
    @Qualifier("bovedaBusiness")
    private BovedaRemote bovedaBusiness;
    
    @Autowired
    private DocumentosProbatoriosValidator documentosProbatoriosValidator;
    
    @Autowired
    private DatosHistoriaLaboralValidator datosHistoriaLaboralValidator;
    
    @Autowired
    private TramiteCorreccionUtil tramiteCorreccionUtil;
 

    private final String VIEW_DATOS_NSS = "datosNSS";
    private static final String PERSONA_SESSION_KEY = "persona_correccion";
    private static final Long TAMANIO_MAXIMO_ARCHIVO = 4194304L;
    private static final String SOLICITUD = "solicitud";
    private static final String STATUS_ERROR_UPLOAD="error";
    private static final String ID_DOCUMENTO_BOVEDA="idDocBoveda";
    private static final String LISTA_DOCUMENT_NSS="listpreliminar";
    private static final String STATUS_SUCCESS_UPLOAD="success";
    private static final String DATOSHISTORIALABORAL="datosHistoriaLaboral";
    private static final String THISPATHCONTROLLER="documentosProbatorios";
    private final String VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL = "datosAdicionalesHistoriaLaboral";
    private final String VIEW_DATOS_HISTORIA_LABORAL = "datosHistoriaLaboral";
    private final String VIEW_RECURSO_NO_DISPONIBLE = "recursoNoDisponible";
    private final String REDIRECTNSS = "vistaNssList";
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    @RequestMapping(value = "/datosNSS", method = RequestMethod.POST)
    public RedirectView datosNSS(@ModelAttribute("datosHistoriaLaboral") DatosHistoriaLaboralVO datosHistoriaLaboralvo,BindingResult result,Model model, HttpSession session, HttpServletResponse response) {
        log.debug("---CDA---Inicia el guardado parcial de documentos del asegurado");
        DatosHistoriaLaboralVO datosHistoriaLaboral=(DatosHistoriaLaboralVO) session.getAttribute(DATOSHISTORIALABORAL);
        Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
        try {
            sol=tramiteCorreccionUtil.actualizarXmlDocumentosAseg(sol, datosHistoriaLaboral);
            session.setAttribute(SOLICITUD,sol);
            return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+THISPATHCONTROLLER+"/"+REDIRECTNSS,true);
        } catch (Exception ex) {
            log.error("---CDA--- Errores al buscar el tramite {}", ex);
            cargaPantallaDocumentoAsegurado(model, session);
            model.addAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
            return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+"/"+THISPATHCONTROLLER+"/"+VIEW_DATOS_HISTORIA_LABORAL,true);
        }
    }
    
    @RequestMapping(value = "/validarDocumentosAsegurado", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object>  validarDocumentosAsegurado(@RequestBody NSSVO nssvo,HttpServletResponse response, HttpSession session,Model model) {
        log.debug("---CDA---Inicia la validacion de documentos del asegurado");
        DatosHistoriaLaboralVO datosHistoriaLaboral=(DatosHistoriaLaboralVO) session.getAttribute(DATOSHISTORIALABORAL);
        final Errors errors = new BindException(datosHistoriaLaboral, "model");
        Map<String, Object> mapa = new HashMap<String, Object>();
        if(datosHistoriaLaboral!=null){
            if(datosHistoriaLaboral.getDocumentoProbatorioList()!=null && !datosHistoriaLaboral.getDocumentoProbatorioList().isEmpty()){
                log.debug("---CDA---Inicia documentos del NSS : {}",datosHistoriaLaboral.getDocumentoProbatorioList().size());
                documentosProbatoriosValidator.validate(datosHistoriaLaboral.getDocumentoProbatorioList(), errors);
            }else{
               log.warn("---CDA--- Errores de captura documentoProbatorioList error");
                errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.listaVacia"); 
            }
        }
        if (errors.hasErrors() || errors.hasFieldErrors("documentoProbatorioList")) {
            log.warn("---CDA--- Errores de captura");
            procesaErroresDeCaptura(errors, mapa, response);
           
        }
        return mapa;
    }
    
    @RequestMapping(value = "/vistaNssList", method = {RequestMethod.POST,RequestMethod.GET})
    public String getVistaNssList(Model model, HttpSession session){
        Fisica fisica = session.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) session.getAttribute(PERSONA_SESSION_KEY) : null;
        DatosHistoriaLaboralVO datosHistoriaLaboral=(DatosHistoriaLaboralVO) session.getAttribute(DATOSHISTORIALABORAL);
        Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
        datosHistoriaLaboral = tramiteCorreccionUtil.getPrecargaNssList(sol,datosHistoriaLaboral);
        log.debug("---CDA---datosHistoriaLaboral  : {}",datosHistoriaLaboral);
        session.setAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
        model.addAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboral);
        this.log.debug("---CDA--- idPersona fisica en sesion: {}", fisica != null ? fisica.getIdPersona() : "sin persona en sesion");
        cargaPantallaDocumentoNSS(model, session);
        return VIEW_DATOS_NSS;
    }
    
    /**
     * Guarda los datos del NSS y de los documentos probatorios
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
    public @ResponseBody
    Map<String, ? extends Object> documentoAsociado(@RequestBody NSSVO nssvo,
            HttpServletResponse response,HttpSession ses, @PathVariable Integer nssLength, Model model, @ModelAttribute("idPersona") Long idPersona,BindingResult resultValidate) {
        final Errors errors = new BindException(nssvo, "model");

        log.debug("---CDA--- ############Validar NSS#####################");
        log.debug("---CDA--- NSS recibido : {} ", nssvo);
        log.debug("---CDA--- NSS list length : {} ", nssLength);

        Map<String, Object> result = new HashMap<String, Object>();
        if(nssvo!=null){
            log.debug("---CDA--- nSSValidator");
            nSSValidator.validate(nssvo, errors);
            if (errors.hasErrors() || errors.hasFieldErrors("NSS")) {
                log.debug("---CDA--- procesa errores de captura NSS");
                procesaErroresDeCaptura(errors, result, response);
            }
            log.debug("---CDA--- Validacion probatorios");
            List<DocumentoProbatorio> listaDocumentoProbNSS=(List<DocumentoProbatorio>) ses.getAttribute(LISTA_DOCUMENT_NSS);
            if(listaDocumentoProbNSS!=null && !listaDocumentoProbNSS.isEmpty()){
                log.debug("---CDA--- Valida documentos al NSS : {}",listaDocumentoProbNSS);
                documentosProbatoriosValidator.validateDocumentosNSS(listaDocumentoProbNSS, resultValidate);
            }else{
                errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.listaVacia");
            }
            if (errors.hasErrors() || errors.hasFieldErrors("documentoProbatorioList")) {
                log.debug("---CDA--- procesa erroes de captura Documentos de NSS : {}",nssvo.getNSS());
                    procesaErroresDeCaptura(errors, result, response);
            }else{
                log.debug("---CDA--- Carga la el nuevo NSS y sus documentos");
                nssvo.setDocumentoProbatorioList(listaDocumentoProbNSS);
                ses.setAttribute(LISTA_DOCUMENT_NSS, null);
                DatosHistoriaLaboralVO datosHistoriaLaboralVO;
                if (ses.getAttribute(DATOSHISTORIALABORAL) == null) {
                    ses.setAttribute(DATOSHISTORIALABORAL,new DatosHistoriaLaboralVO());
                }
                datosHistoriaLaboralVO = (DatosHistoriaLaboralVO) ses
                                            .getAttribute(DATOSHISTORIALABORAL);
                if(datosHistoriaLaboralVO.getNSSList()!=null){
                    datosHistoriaLaboralVO.getNSSList().add(nssvo);
                }else{
                    List<NSSVO> listNss= new ArrayList<NSSVO>();
                    datosHistoriaLaboralVO.setNSSList(listNss);
                    datosHistoriaLaboralVO.getNSSList().add(nssvo);
                }   
                ses.setAttribute(DATOSHISTORIALABORAL, datosHistoriaLaboralVO);
            }
        }else{
            errors.rejectValue("NSS", "field.required");
        }
        log.debug("---CDA--- Finaliza metodo");
        return result;
    }
    
    /**Metodo que guardara en boveda la informacion de los documentos y posteriormente
     * los metera en un objeto en session para asociarlo despues a un NSS
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
    @RequestMapping( value = "/uploadify")
    public ResponseEntity<Map<String, Object>> uploadBytes(
                    @RequestParam("fileData") MultipartFile file, 
                    @RequestParam("idDocPorTipo") String idDocporTipo, 
                    @RequestParam("cveIdDocumento") String cveIdDocumento,
                    @RequestParam("desDocumento") String desDocumento,
                    @RequestParam("tipoDocumento") String tipoDocumento,
                    HttpSession ses,
                    HttpServletRequest request, HttpServletResponse response)
                    throws IOException {
        
            byte[] ptext = desDocumento.getBytes(ISO_8859_1); 
            String value = new String(ptext, UTF_8); 
            Map<String, Object> result = new HashMap<String, Object>();
            log.debug("---CDA--- Tamanio Archivo {} ", file.getSize());
            if( file.getSize() <= TAMANIO_MAXIMO_ARCHIVO){		
                    Solicitud solicitud = (Solicitud) ses.getAttribute(SOLICITUD);
                    log.debug("---CDA--- #####Subiendo Archivo######");
                    String[] n = file.getOriginalFilename().split("\\.");
                    Fisica fisica = ses.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) ses
                                    .getAttribute(PERSONA_SESSION_KEY) : null;
                    if (fisica != null) {
                            String idDocBoveda = null;
                            String nombreCompleto=null;
                            try{
                                    nombreCompleto = idDocporTipo + "_" + file.getOriginalFilename();
                                    idDocBoveda = bovedaBusiness.subirDocumento(file.getBytes(), solicitud, nombreCompleto, n[n.length - 1] , file.getContentType());
                            }catch (BovedaCDAException bce){
                                    log.error(bce.getSituacion());					
                                    result.put(STATUS_ERROR_UPLOAD, bce.getMensajeError());
                                    result.put("__situacion_", bce.getSituacion());
                                    HttpHeaders httpHeaders = new HttpHeaders();
                                    httpHeaders.setContentType(MediaType.TEXT_HTML);
                                    return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);			
                            }catch(Exception e){
                                    log.error("---CDA--- Error al subir el documento {}",e);
                                    result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento. Intente nuevamente.");
                                    HttpHeaders httpHeaders = new HttpHeaders();
                                    httpHeaders.setContentType(MediaType.TEXT_HTML);
                                    return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);
                            }

                            if (idDocBoveda != null) {
                                    log.debug("---CDA--- documento insertado en boveda {}",idDocBoveda);
                                    result.put(STATUS_SUCCESS_UPLOAD, STATUS_SUCCESS_UPLOAD);
                                    result.put(ID_DOCUMENTO_BOVEDA, idDocBoveda);
                                    DocumentoProbatorio documentoPreliminar = new DocumentoProbatorio();
                                    documentoPreliminar.setIdDocBoveda(idDocBoveda);
                                    documentoPreliminar.setIdDocumentoPorTipo(new Long(idDocporTipo));
                                    documentoPreliminar.setCveIdDocumento(new Long(cveIdDocumento));
                                    documentoPreliminar.setNombre(nombreCompleto);
                                    documentoPreliminar.setDesDocumento(value);
                                    documentoPreliminar.setTipoDocumento(Integer.valueOf(tipoDocumento));
                                    addListDocumentoProbatorioNSS(documentoPreliminar,ses);
                            } else { 
                                    log.debug("---CDA--- Error en createDocument ::: el idDocBoveda es nulo");
                                    result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento. Intente nuevamente.");
                            }
                    } else {
                            log.warn("---CDA--- El usuario no esta logeado idPersona null");
                            result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento.");
                    }
            }else{
                    result.put(STATUS_ERROR_UPLOAD, "El archivo no cumple con el tama\u00f1o m\u00e1ximo permitido [4MB]. No es posible adjuntar el archivo.");
            }

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.TEXT_HTML);
            return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/eliminarNSS", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> eliminarNSS(@RequestBody NSSVO nssvo,
            HttpServletResponse response,HttpSession ses, Model model, @ModelAttribute("idPersona") Long idPersona,BindingResult resultValidate) {
        log.debug("---CDA--- NSS a remover : {} ", nssvo);

        Map<String, Object> result = new HashMap<String, Object>();
        DatosHistoriaLaboralVO datosHistoriaLaboralVO = (DatosHistoriaLaboralVO) ses.getAttribute(DATOSHISTORIALABORAL);   
        List<NSSVO> listNSS=datosHistoriaLaboralVO.getNSSList();
        Iterator<NSSVO> it= listNSS.iterator();
        Boolean bandera=Boolean.FALSE;
        while(it.hasNext()) {
            NSSVO nss= it.next();
            if (nss.getNSS().equals(nssvo.getNSS())) {
                for(DocumentoProbatorio doc:nss.getDocumentoProbatorioList()){
                    try{
                        String confirmacion = bovedaBusiness.eliminarDocumento(doc.getIdDocBoveda());
                        if (!confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001.getCodigo())) {
                            bandera=Boolean.TRUE;
                            result.put("error", confirmacion);      
                        }
                    }catch (BovedaCDAException bce){
                        bandera=Boolean.TRUE;
                        log.error("---CDA-- Ocurrio un error {}",bce.getSituacion());
                        result.put("error", bce.getMensajeError()); 
                    }
                }
                it.remove();
            }
        }
        if(!bandera){
           Solicitud solicitud = (Solicitud) ses.getAttribute(SOLICITUD);
           result.put("success", "Los documentos eliminados con \u00e9xito del NSS ");
           tramiteCorreccionUtil.cancelarNssTramiteXml(solicitud,nssvo.getNSS());
        }
        log.debug("---CDA--- Finaliza metodo");
        return result;
    }
    
    @RequestMapping(value = "/eliminarDocumentoProbatorio", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Map<String, String>> eliminarDocumentoProbatorio(@RequestBody DocumentoProbatorio docuemntoProbatorio,
            HttpServletResponse response,HttpSession ses, Model model, @ModelAttribute("idPersona") Long idPersona) {
        log.debug("---CDA--- Documento a remover : {} ", docuemntoProbatorio);
        Map<String, String> result = new HashMap<String, String>();
        List<DocumentoProbatorio> listDocumento=(List<DocumentoProbatorio>) ses.getAttribute(LISTA_DOCUMENT_NSS);
        Iterator<DocumentoProbatorio> it= listDocumento.iterator();
        while(it.hasNext()) {
            DocumentoProbatorio doc = it.next();
            if(doc.getCveIdDocumento().equals(docuemntoProbatorio.getCveIdDocumento()) && doc.getIdDocBoveda().equals(docuemntoProbatorio.getIdDocBoveda())){
                try{
                    String confirmacion = bovedaBusiness.eliminarDocumento(doc.getIdDocBoveda());
                    if (confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001.getCodigo()) || confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5004.getCodigo())) {
                        result.put("success", "El documento fue eliminado con \u00e9xito <br>"+confirmacion);
                        it.remove();     
                    }
                }catch (BovedaCDAException bce){
                    log.error("---CDA-- Ocurrio un error {}",bce.getSituacion());
                    result.put("error", bce.getMensajeError()); 
                }
            }
        }
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/validar/datosNSSDocumentos", method = RequestMethod.POST)
    public String consultaDatosHistoriaLaboral(@ModelAttribute("datosHistoriaLaboral") DatosHistoriaLaboralVO datosHistoriaLaboralvo
            ,BindingResult result, Model model, HttpSession session) throws DocumentoException {		
        log.debug("---CDA--- Validar NSS ingresados y documentacion probatoria");
        DatosHistoriaLaboralVO datosHistoriaLaboral;
        if(session.getAttribute(DATOSHISTORIALABORAL)!=null){
            datosHistoriaLaboral=(DatosHistoriaLaboralVO) session.getAttribute(DATOSHISTORIALABORAL);
        }else{
            datosHistoriaLaboral = new DatosHistoriaLaboralVO();
        }
        datosHistoriaLaboralValidator.validarNssList(datosHistoriaLaboral, result);
        DatosAdicionalesHistoriaLaboral datosAdicionalesHistoriaLaboral;
        if (session.getAttribute("datosAdicionalesHistoriaLaboral") != null) {
                datosAdicionalesHistoriaLaboral = (DatosAdicionalesHistoriaLaboral) session
                                .getAttribute("datosAdicionalesHistoriaLaboral");
        } else {
            datosAdicionalesHistoriaLaboral = new DatosAdicionalesHistoriaLaboral();
        }

        session.setAttribute("datosHistoriaLaboral", datosHistoriaLaboral);
        if (result.hasErrors()) {
                this.log.warn("---CDA--- Errores de captura");
                cargaPantallaDocumentoNSS(model, session);
                model.addAttribute("datosHistoriaLaboral", datosHistoriaLaboral);
                return VIEW_DATOS_NSS;
        }else{
            Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD);
            try {
                solicitud = tramiteCorreccionUtil.crearTramiteNss(solicitud, datosHistoriaLaboral);
                session.setAttribute(SOLICITUD, solicitud);
            } catch (Exception ex) {
                this.log.warn("---CDA--- Error al crear el tramite");
                return VIEW_RECURSO_NO_DISPONIBLE;
            }
        }
        log.debug("******************CDA************************* SE AGREGAN AL MODELO DatosHistoriaLaboral {}", datosAdicionalesHistoriaLaboral.getObservaciones());
        model.addAttribute("datosAdicionalesHistoriaLaboral",datosAdicionalesHistoriaLaboral);
        return VIEW_DATOS_ADICIONALES_HISTORIA_LABORAL;
    }
    
    @RequestMapping( value = "/asegUploadify")
    public ResponseEntity<Map<String, Object>> uploadDocumentosAseguradoBytes(
                    @RequestParam("fileData") MultipartFile file, 
                    @RequestParam("idDocPorTipo") String idDocporTipo, 
                    @RequestParam("cveIdDocumento") String cveIdDocumento,
                    @RequestParam("desDocumento") String desDocumento,
                    @RequestParam("tipoDocumento") String tipoDocumento,
                    HttpSession ses,
                    HttpServletRequest request, HttpServletResponse response)
                    throws IOException {
        
            byte[] ptext = desDocumento.getBytes(ISO_8859_1); 
            String value = new String(ptext, UTF_8); 
            Map<String, Object> result = new HashMap<String, Object>();
            
            log.debug("---CDA--- descripcion {} ", desDocumento);
            log.debug("---CDA--- Tamanio Archivo {} ", file.getSize());
            if( file.getSize() <= TAMANIO_MAXIMO_ARCHIVO){		
                    Solicitud solicitud = (Solicitud) ses.getAttribute(SOLICITUD);
                    log.debug("---CDA--- #####Subiendo Archivo######");
                    String[] n = file.getOriginalFilename().split("\\.");
                    Fisica fisica = ses.getAttribute(PERSONA_SESSION_KEY) != null ? (Fisica) ses
                                    .getAttribute(PERSONA_SESSION_KEY) : null;
                    if (fisica != null) {
                            String idDocBoveda = null;
                            String nombreCompleto=null;
                            try{
                                    nombreCompleto = idDocporTipo + "_" + file.getOriginalFilename();
                                    idDocBoveda = bovedaBusiness.subirDocumento(file.getBytes(), solicitud, nombreCompleto, n[n.length - 1] , file.getContentType());
                            }catch (BovedaCDAException bce){
                                    log.error(bce.getSituacion());					
                                    result.put(STATUS_ERROR_UPLOAD, bce.getMensajeError());
                                    result.put("__situacion_", bce.getSituacion());
                                    HttpHeaders httpHeaders = new HttpHeaders();
                                    httpHeaders.setContentType(MediaType.TEXT_HTML);
                                    return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);			
                            }catch(Exception e){
                                    log.error("---CDA--- Error al subir el documento {}",e);
                                    result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento. Intente nuevamente.");
                                    HttpHeaders httpHeaders = new HttpHeaders();
                                    httpHeaders.setContentType(MediaType.TEXT_HTML);
                                    return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);
                            }

                            if (idDocBoveda != null) {
                                    log.debug("---CDA--- documento insertado en boveda {}",idDocBoveda);
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
                                    if(ses.getAttribute(DATOSHISTORIALABORAL)==null){
                                        log.debug("---CDA--- Se inicializa el datoshistorico");
                                        datoshistoriaLaboral = new DatosHistoriaLaboralVO();
                                        datoshistoriaLaboral.setDocumentoProbatorioList(new ArrayList<DocumentoProbatorio>());
                                    }else{
                                      datoshistoriaLaboral=(DatosHistoriaLaboralVO)ses.getAttribute(DATOSHISTORIALABORAL);
                                      if(datoshistoriaLaboral.getDocumentoProbatorioList()==null){
                                          log.debug("---CDA--- Se inicializa la lista de documentos probatorios");
                                          datoshistoriaLaboral.setDocumentoProbatorioList(new ArrayList<DocumentoProbatorio>());
                                      }
                                    }
                                    datoshistoriaLaboral=(DatosHistoriaLaboralVO)ses.getAttribute(DATOSHISTORIALABORAL);
                                    datoshistoriaLaboral.getDocumentoProbatorioList().add(documento);
                            } else { 
                                    log.error("---CDA--- Error en createDocument ::: el idDocBoveda es nulo");
                                    result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento. Intente nuevamente.");
                            }
                    } else {
                            log.warn("---CDA--- El usuario no esta logeado idPersona null");
                            result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento.");
                    }
            }else{
                    result.put(STATUS_ERROR_UPLOAD, "El archivo no cumple con el tama\u00f1o m\u00e1ximo permitido [4MB]. No es posible adjuntar el archivo.");
            }

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.TEXT_HTML);
            return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/eliminarDocumentoAsegurado", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Map<String, String>> eliminarDocumentoAsegurado(@RequestBody DocumentoProbatorio docuemntoProbatorio,
            HttpServletResponse response,HttpSession ses, Model model, @ModelAttribute("idPersona") Long idPersona) {
        log.debug("---CDA--- Documento a remover : {} ", docuemntoProbatorio);
        Map<String, String> result = new HashMap<String, String>();
        DatosHistoriaLaboralVO datosHistoria=(DatosHistoriaLaboralVO) ses.getAttribute(DATOSHISTORIALABORAL);
        Iterator<DocumentoProbatorio> it= datosHistoria.getDocumentoProbatorioList().iterator();
        while(it.hasNext()) {
            DocumentoProbatorio doc = it.next();
            if(doc.getCveIdDocumento().equals(docuemntoProbatorio.getCveIdDocumento()) && doc.getIdDocBoveda().equals(docuemntoProbatorio.getIdDocBoveda())){
                try{
                    log.debug("---CDA--- idBoveda: {} ", doc.getIdDocBoveda());
                    String confirmacion = bovedaBusiness.eliminarDocumento(doc.getIdDocBoveda());
                    if (confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001.getCodigo()) || confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5004.getCodigo())) {
                        result.put("success", "El documento fue eliminado con \u00e9xito <br>"+confirmacion);
                        
                    }else{
                        result.put("error", confirmacion);
                    }
                    log.debug("---CDA--- err: {} ", confirmacion);
                    it.remove();
                }catch (BovedaCDAException bce){
                    log.error("---CDA-- Ocurrio un error {}",bce.getSituacion());
                    result.put("error", bce.getMensajeError()); 
                }
            }
        }
        model.addAttribute(DATOSHISTORIALABORAL, datosHistoria);
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }
        
    private void addListDocumentoProbatorioNSS(DocumentoProbatorio documento,HttpSession ses){
        if(ses.getAttribute(LISTA_DOCUMENT_NSS)!=null){
            log.debug("---CDA--- Se agrega documentacion preliminar");
            List<DocumentoProbatorio> listaDocumentoProbNSS=(List<DocumentoProbatorio>) ses.getAttribute(LISTA_DOCUMENT_NSS);
            listaDocumentoProbNSS.add(documento);
            ses.setAttribute(LISTA_DOCUMENT_NSS, listaDocumentoProbNSS);
        }else{
            log.debug("---CDA--- Se crea lista de documentacion preliminar");
           List<DocumentoProbatorio> listaDocumentoProbNSS=new ArrayList<DocumentoProbatorio>();
            listaDocumentoProbNSS.add(documento);
            ses.setAttribute(LISTA_DOCUMENT_NSS, listaDocumentoProbNSS);
        }
        
    }

    private Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> cargarPantallaDatosHistoriaLaboral(Model model, HttpSession session) {
        model.addAttribute("datosHistoriaLaboral", (DatosHistoriaLaboralVO) session
                .getAttribute("datosHistoriaLaboral"));

        List<DoctoReqTramite> documentos = documentoProbatorioServiceBusiness.getDocumentosRequeridosPorTipoTramite(
                TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo().longValue());

        //agrupacion por tipo
        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos = new HashMap<TipoDocumentoProbatorio, List<DocumentoProbatorio>>();
        for (DoctoReqTramite doctoReqTramite : documentos) {
            Integer tipoDocto = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio();
            TipoDocumentoProbatorio currMapKey = null;
            if (doctos.keySet() != null && !doctos.keySet().isEmpty()) {
                for (TipoDocumentoProbatorio key : doctos.keySet()) {
                    if (tipoDocto.equals(key.getIdTipoDocumentoProbatorio())) {
                        currMapKey = key;
                        log.debug("--CDA-- se encontro la llave: {}", key.getIdTipoDocumentoProbatorio());
                    }
                }
            }

            if (currMapKey == null) {
                currMapKey = doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio();
                doctos.put(currMapKey, new ArrayList<DocumentoProbatorio>());
                log.debug("--CDA-- creando llave para tipo: {}", currMapKey.getIdTipoDocumentoProbatorio());
            }
            DocumentoProbatorio docProbatorioVo = new DocumentoProbatorio();
            docProbatorioVo.setTipoDocumento(doctoReqTramite.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
            log.debug("--CDA-- TIPO DE DOCUMENTO: {}", docProbatorioVo.getTipoDocumento().toString());
            docProbatorioVo.setCveIdDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getCveIdDocumento());
            docProbatorioVo.setDesDocumento(doctoReqTramite.getDocumentoPorTipo().getDocumento().getDesDocumento());
            docProbatorioVo.setIdDocumentoPorTipo(doctoReqTramite.getDocumentoPorTipo().getIdDocumentoPorTipo().longValue());
            log.debug("--CDA-- ID DE DOCUMENTO PROBATORIO POR TIPO: {}", docProbatorioVo.getIdDocumentoPorTipo());
            if (docProbatorioVo.getCveIdDocumento().intValue() == DocumentoEnum.ACTA_NACIMIENTO.getId()
                    || (docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.IDENTIFICACION.getId()
                    && docProbatorioVo.getCveIdDocumento().intValue() != DocumentoEnum.CURP.getId())
                    || docProbatorioVo.getTipoDocumento().longValue() == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()) {
                log.debug("--CDA-- se agrega documento: {}, {}", docProbatorioVo.getCveIdDocumento(), docProbatorioVo.getDesDocumento());
                doctos.get(currMapKey).add(docProbatorioVo);
            }
        }
        return doctos;
    }
    
    
    private void cargaPantallaDocumentoAsegurado(Model model, HttpSession session){
        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos =cargarPantallaDatosHistoriaLaboral(model,session);
        Iterator<TipoDocumentoProbatorio> iterator =doctos.keySet().iterator();
        while(iterator.hasNext()){
            TipoDocumentoProbatorio tipo=iterator.next();
              if (doctos.get(tipo).isEmpty() || tipo.getIdTipoDocumentoProbatorio()==11) {
                    iterator.remove();
                }  
        }
        model.addAttribute("documentosProbatoriosVo", doctos);
        model.addAttribute("documentoNSSClave", TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
    }
    
    /**
     * 
     * @param model
     * @param session 
     */
    private void cargaPantallaDocumentoNSS(Model model, HttpSession session){
        Map<TipoDocumentoProbatorio, List<DocumentoProbatorio>> doctos =cargarPantallaDatosHistoriaLaboral(model,session);
        Iterator<TipoDocumentoProbatorio> iterator =doctos.keySet().iterator();
        while(iterator.hasNext()){
            TipoDocumentoProbatorio tipo=iterator.next();
              if (doctos.get(tipo).isEmpty() || tipo.getIdTipoDocumentoProbatorio()!=11) {
                    iterator.remove();
                }  
        }
        model.addAttribute("documentosProbatoriosVo", doctos);
        model.addAttribute("documentoNSSClave", TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId());
    }
}
