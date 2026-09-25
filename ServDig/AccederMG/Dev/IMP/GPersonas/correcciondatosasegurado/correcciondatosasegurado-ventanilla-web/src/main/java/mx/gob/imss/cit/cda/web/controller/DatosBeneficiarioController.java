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
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import static mx.gob.imss.cit.cda.web.controller.DocumentosProbatoriosController.ISO_8859_1;
import static mx.gob.imss.cit.cda.web.controller.DocumentosProbatoriosController.UTF_8;

import mx.gob.imss.cit.cda.web.utils.DocumentoProbatorioUtil;
import mx.gob.imss.cit.cda.web.utils.TramiteCorreccionUtil;
import mx.gob.imss.cit.cda.web.validator.BeneficiarioRepresentanteValidator;
import mx.gob.imss.cit.cda.web.validator.DatosHistoriaLaboralValidator;
import mx.gob.imss.cit.cda.web.validator.DocumentosProbatoriosValidator;
import mx.gob.imss.cit.cda.web.validator.NSSValidator;
import mx.gob.imss.cit.cda.web.validator.SolicitudResponsableValidator;
import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+RequestMappingConstants.RESPONSABLE_DATOBENEFICIARIOS)
@SessionAttributes({"sol","personaCorreccion"})
public class DatosBeneficiarioController extends AbstractController {

 
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
    private PersonaBusinessRemote personaBusiness;
    
    @Autowired
    private TramiteCorreccionUtil tramiteCorreccionUtil;
    
    @Autowired
    private DocumentoProbatorioUtil documentoProbatorioUtil;
    
    @Autowired
    private DatosHistoriaLaboralValidator datosHistoriaLaboralValidator;
    
    @Autowired
    private BeneficiarioRepresentanteValidator beneficiarioRepresentanteValidator;
    
    @ModelAttribute("sol")
	public Solicitud  getSolicitud() {
		return new Solicitud();
    }
	
    @ModelAttribute("personaCorreccion")
    public Fisica  getFisica() {
            return new Fisica();
    }
    
    private final String VIEW_DATOS_NSS = "datosNSS";
    private final String VIEW_DATOS_BENEFICIARIO_REPRESENTANTE = "datosBeneficiarioRepresentante";
    private static final String PERSONA_BENEFICIARIO = "personaBeneficiario";
    private static final String PERSONA_SESSION_KEY = "persona_correccion";
    private static final Long TAMANIO_MAXIMO_ARCHIVO = 4194304L;
    private static final String SOLICITUD = "solicitud";
    private static final String STATUS_ERROR_UPLOAD="error";
    private static final String ID_DOCUMENTO_BOVEDA="idDocBoveda";
    private static final String STATUS_SUCCESS_UPLOAD="success";
    private static final String DATOSHISTORIALABORALBEBEFICIARIO="datosHistoriaLaboralBeneficiario";
    private static final String PERSONARENAPO = "personaRenapo";
    
    private final String TIPOSOLICITANTE_BENEFICIARIO = "Beneficiario";
    private final String TIPOSOLICITANTE_REPRESENTANTE = "Representante";
    private final String TIPOSOLICITANTE_ASEGURADO = "Asegurado";
    
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    
    @RequestMapping(value = "/continuacionTramite", method = RequestMethod.POST)
    public RedirectView continuacionTramite(@ModelAttribute("datosHistoriaLaboralBeneficiario") DatosHistoriaLaboralVO datosHistoriaLaboralvo,BindingResult result,Model model, HttpSession session, HttpServletResponse response) {
        log.debug("---CDA---Inicia el guardado parcial de documentos del asegurado /interesado");
        DatosHistoriaLaboralVO datosBeneficiario=(DatosHistoriaLaboralVO) session.getAttribute(DATOSHISTORIALABORALBEBEFICIARIO);
         log.debug("---CDA---datos del beneficiario Vista "+datosBeneficiario.getTipoSolicitante());
        Solicitud sol = (Solicitud) session.getAttribute(SOLICITUD);
        Fisica personaRenapo=(Fisica) session.getAttribute(PERSONA_BENEFICIARIO);
        try {
            sol=tramiteCorreccionUtil.actualizarXmlDocumentosBeneficiarioRepresentante(sol, datosBeneficiario,personaRenapo);
            session.setAttribute(SOLICITUD,sol);
            return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+RequestMappingConstants.REGISTRO_DOCUMENTOS_PROBATORIOS+"/datosAdicionales",true);
        } catch (Exception ex) {
            log.error("---CDA--- Errores al buscar el tramite {}", ex);
            return new RedirectView(RequestMappingConstants.REGISTRO_SOLICITUD_RESPONSABLE+RequestMappingConstants.REGISTRO_DOCUMENTOS_PROBATORIOS+"/recuersoNoDisp",true);
        }
    }
    
    @RequestMapping( value = "/beneficiarioUploadify")
    public ResponseEntity<Map<String, Object>> beneficiarioUploadify(
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
//            log.debug("---CDA--- Tamanio Archivo {} ", file.getSize());
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
//                                    log.debug("---CDA--- documento insertado en boveda {}",idDocBoveda);
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
                                    if(ses.getAttribute(DATOSHISTORIALABORALBEBEFICIARIO)==null){
                                        log.debug("---CDA--- Se inicializa "+ DATOSHISTORIALABORALBEBEFICIARIO);
                                        datoshistoriaLaboral = new DatosHistoriaLaboralVO();
                                        datoshistoriaLaboral.setDocumentoProbatorioList(new ArrayList<DocumentoProbatorio>());
                                        ses.setAttribute(DATOSHISTORIALABORALBEBEFICIARIO,datoshistoriaLaboral);
                                    }else{
                                      datoshistoriaLaboral=(DatosHistoriaLaboralVO)ses.getAttribute(DATOSHISTORIALABORALBEBEFICIARIO);
                                      if(datoshistoriaLaboral.getDocumentoProbatorioList()==null){
                                          log.debug("---CDA--- Se inicializa la lista de documentos probatorios");
                                          datoshistoriaLaboral.setDocumentoProbatorioList(new ArrayList<DocumentoProbatorio>());
                                      }
                                    }
                                    datoshistoriaLaboral=(DatosHistoriaLaboralVO)ses.getAttribute(DATOSHISTORIALABORALBEBEFICIARIO);
                                    datoshistoriaLaboral.getDocumentoProbatorioList().add(documento);
                            } else { 
                                    log.error("---CDA--- Error en createDocument ::: el idDocBoveda es nulo");
                                    result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento. Intente nuevamente.");
                            }
                    } else {
                            result.put(STATUS_ERROR_UPLOAD, "Error al adjuntar Documento.");
                    }
            }else{
                    result.put(STATUS_ERROR_UPLOAD, "El archivo no cumple con el tama\u00f1o m\u00e1ximo permitido [4MB]. No es posible adjuntar el archivo.");
            }

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.TEXT_HTML);
            return new ResponseEntity<Map<String, Object>>(result,httpHeaders,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/validarDocumentosBeneficiario", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object>  validarDocumentosBeneficiario(@RequestBody DatosHistoriaLaboralVO datosHistor,HttpServletResponse response, HttpSession session,Model model) {
        log.debug("---CDA---Inicia la validacion de documentos del asegurado");
        
        DatosHistoriaLaboralVO datosHistoriaLaboral=(DatosHistoriaLaboralVO) session.getAttribute(DATOSHISTORIALABORALBEBEFICIARIO);
        final Errors errors = new BindException(datosHistoriaLaboral, "model");
        Map<String, Object> mapa = new HashMap<String, Object>();
        if(datosHistoriaLaboral!=null){
            if(datosHistoriaLaboral.getDocumentoProbatorioList()!=null && !datosHistoriaLaboral.getDocumentoProbatorioList().isEmpty()){
                log.debug("---CDA---Inicia documentos del NSS : {}"+datosHistoriaLaboral.getDocumentoProbatorioList().size());
                documentosProbatoriosValidator.validate(datosHistoriaLaboral.getDocumentoProbatorioList(), errors, 0);
                session.setAttribute(DATOSHISTORIALABORALBEBEFICIARIO,datosHistoriaLaboral);
            }else{
               log.warn("---CDA--- Errores de captura documentoProbatorioList error");
                errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.listaVacia"); 
            }
        }
        if (errors.hasErrors() || errors.hasFieldErrors("documentoProbatorioList") || errors.hasFieldErrors("documentoProbatorioList")) {
            log.warn("---CDA--- Errores de captura");
            procesaErroresDeCaptura(errors, mapa, response);
           
        }
        return mapa;
    }
    
    @RequestMapping(value = "/buscarPersonaRenapo", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> buscarPersonaRenapo(Model model,
			@RequestBody Fisica persona, HttpSession ses, HttpServletRequest request, HttpServletResponse response) { 
		
            Map<String, Object> result = new HashMap<String, Object>();
            ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String,Object>>(HttpStatus.OK);
            HttpHeaders httpHeaders = new HttpHeaders();
            final Errors errors = new BindException(persona, "model");
		log.debug("---CDA--- ###Validar formulario de registro###");
		log.debug("---CDA--- datos de persona recibidos : "+persona);
		beneficiarioRepresentanteValidator.validateCurp(persona, errors);
		if (errors.hasErrors()) {
			procesaErroresDeCaptura(errors, result, response);
			log.debug("---CDA--- mapa resultado validar persona "+ result);
			respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.PRECONDITION_FAILED);
		} else {
                    httpHeaders.setContentType(MediaType.TEXT_HTML);
                    Fisica personaRenapo = persona;		
                    boolean excepcion = false;
                    if (persona.getCurp()!= null && !persona.getCurp().equals("")){
                            try {
                                    personaRenapo = this.personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(persona.getCurp());
                            } catch (ClienteWebserviceRenapoCurpException e) {
                                    log.error("---CDA--- Error en la busqueda de persona por CURP {} en RENAPO", e);
                                    excepcion = true;
                                    result.put("exception", "El servicio web de RENAPO no se encuentra disponible en este momento, por favor int\u00e9ntelo m\u00e1s tarde.");
                                    respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                            if (personaRenapo == null && !excepcion){
                                    result.put("exception" , "La CURP proporcionada no fue localizada en RENAPO, por favor verifique la informaci\u00f3n capturada.");
                                    respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                    }
                    if (personaRenapo != null  && !excepcion){
                            persona = personaRenapo;
                            ses.setAttribute(PERSONA_BENEFICIARIO, persona);
                            result.put(STATUS_SUCCESS_UPLOAD, STATUS_SUCCESS_UPLOAD);
                            result.put(PERSONARENAPO, persona);
                            respuesta = new ResponseEntity<Map<String,Object>>(result, httpHeaders, HttpStatus.OK);
                    }			
                }
            return respuesta;
	}
        
    @RequestMapping(value = "/eliminarDocumentoBeneficiario", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Map<String, String>> eliminarDocumentoBeneficiario(@RequestBody DocumentoProbatorio docuemntoProbatorio,
            HttpServletResponse response,HttpSession ses, Model model) {
//        log.debug("---CDA--- Documento a remover : {} ", docuemntoProbatorio);
        Map<String, String> result = new HashMap<String, String>();
        DatosHistoriaLaboralVO datosHistoria=(DatosHistoriaLaboralVO) ses.getAttribute(DATOSHISTORIALABORALBEBEFICIARIO);
        Iterator<DocumentoProbatorio> it= datosHistoria.getDocumentoProbatorioList().iterator();
        while(it.hasNext()) {
            DocumentoProbatorio doc = it.next();
            if(doc.getCveIdDocumento().equals(docuemntoProbatorio.getCveIdDocumento()) && doc.getIdDocBoveda().equals(docuemntoProbatorio.getIdDocBoveda())){
                try{
                    String confirmacion = bovedaBusiness.eliminarDocumento(doc.getIdDocBoveda());
                    if (confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001.getCodigo())) {
                        result.put("success", "El documento fue eliminado con \u00e9xito <br>"+confirmacion);
                        it.remove();
                    }else{
                        result.put("error", confirmacion);
                    }
                }catch (BovedaCDAException bce){
                    log.error("---CDA-- Ocurrio un error {}",bce);
                    result.put("error", bce.getMensajeError()); 
                }
            }
        }
        model.addAttribute(DATOSHISTORIALABORALBEBEFICIARIO, datosHistoria);
        return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/validarDatosBeneficiario", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object>  validarDatosBeneficiario(@RequestBody DatosHistoriaLaboralVO datosHistor,HttpServletResponse response, HttpSession session,Model model) {
        log.debug("---CDA---Inicia la validacion de documentos del asegurado");
        DatosHistoriaLaboralVO datosHistoriaLaboral=(DatosHistoriaLaboralVO) session.getAttribute(DATOSHISTORIALABORALBEBEFICIARIO);
        
        final Errors errors = new BindException(datosHistoriaLaboral, "model");
        Map<String, Object> mapa = new HashMap<String, Object>();
        if(datosHistoriaLaboral!=null){
            
            if(session.getAttribute(PERSONA_BENEFICIARIO)==null){
                errors.rejectValue("curp", "field.documentoProbatorio.beneficiario.curp"); 
            }
            
            if(datosHistoriaLaboral.getTipoSolicitante().equals(TIPOSOLICITANTE_BENEFICIARIO)){
                if(datosHistor.getTipoBeneficiario()!=null && !datosHistor.getTipoBeneficiario().equals("")){
                    datosHistoriaLaboral.setTipoBeneficiario(datosHistor.getTipoBeneficiario());
                }else{
                    errors.rejectValue("tipoBeneficiario", "field.required");
                }
            }
            if(datosHistoriaLaboral.getDocumentoProbatorioList()!=null && !datosHistoriaLaboral.getDocumentoProbatorioList().isEmpty()){
                log.debug("---CDA---Inicia documentos del NSS : {}"+datosHistoriaLaboral.getDocumentoProbatorioList().size());
                documentosProbatoriosValidator.validate(datosHistoriaLaboral.getDocumentoProbatorioList(), errors, 0);
                
            }else{
               log.warn("---CDA--- Errores de captura documentoProbatorioList error");
                errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.listaVacia"); 
            }
            
            
        }
        if (errors.hasErrors() 
                || errors.hasFieldErrors("documentoProbatorioList") 
                || errors.hasFieldErrors("tipoBeneficiario")
                || errors.hasFieldErrors("curp")) {
            log.warn("---CDA--- Errores de captura");
            procesaErroresDeCaptura(errors, mapa, response);
           
        }else{
            session.setAttribute(DATOSHISTORIALABORALBEBEFICIARIO,datosHistoriaLaboral);
            log.debug("---CDA---DATOSHISTORIALABORALBEBEFICIARIO final : {}"+datosHistoriaLaboral);
        }
        return mapa;
    }
    
    
}
