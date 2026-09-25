package mx.gob.imss.cit.cda.web.agregarnss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.AgregarNssRemote;
import mx.gob.imss.cit.cda.service.interfaces.TramiteNssRemote;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractUpdateController;
import mx.gob.imss.cit.cda.web.app.common.model.enums.RespuestaPeticionEnum;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.NSS;
import mx.gob.imss.cit.cda.web.app.responsable.model.NSSAdicional;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;
import mx.gob.imss.cit.cda.web.consulta.utils.ReadSolicitudUtils;
import mx.gob.imss.cit.cda.web.utils.TransformerRegistroUtils;
import mx.gob.imss.cit.cda.web.validator.NSSValidator;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
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
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AgregarNssDocumentoController 
extends AbstractUpdateController<NSS, Solicitud> {
    
    @Autowired
    @Qualifier(BeansConstants.AGREGAR_NSS_DOCUMENTO_HELPER)
    UpdateHelper<NSS, Solicitud> service;

    @Override
    public UpdateHelper<NSS, Solicitud> getHelper() {
        return this.service;
    }
    
    @Autowired
    private NSSValidator nSSValidator;
    
    @Autowired
    private ReadSolicitudUtils readSolicitudUtils;
    
    

    
    @Autowired
    @Qualifier("tramitesNssBusiness")
    private TramiteNssRemote tramiteNssBusiness;
    
    private static final Logger logger = LoggerFactory
            .getLogger(AgregarNssDocumentoController.class);
    
    @RequestMapping(RequestMappingConstants.REQUEST_AGREGARNSS_RESPONSABLE_OBTIENE_NSS)
    @ResponseBody
    @Override
    public ResponseEntity<Solicitud> update(@RequestBody NSS input,
            HttpServletRequest request) {
        return super.update(input, request);
    }
    
    @RequestMapping(value = RequestMappingConstants.VALIDAR_NSS)
    public ResponseEntity<Map<String, Object>> validaNss(@RequestParam("nss") String nss,
            HttpSession ses, HttpServletRequest request,
            HttpServletResponse response) {
       Map<String, Object> result = new HashMap<String, Object>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        logger.debug("Entramos a guardar a boveda");
        NSSVO nssVo = new NSSVO();
        nssVo.setNSS(nss);
        final Errors errors = new BindException(nssVo, "model");
        if (nss != null) {
            nSSValidator.validate(nssVo, errors);
            if (errors.hasErrors() || errors.hasFieldErrors("NSS")) {
                result.put(
                        RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),errors.getFieldError("NSS").getCode());
            }else{
                return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                        HttpStatus.OK);
            }
        } else {
            result.put(
                    RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
                    "field.NSS.listaVacia");
        }
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);
    }
    
    @RequestMapping(value = RequestMappingConstants.REQUEST_POR_ORIGEN_NSS)
    public ResponseEntity<Map<String, Object>> obtenerPorOrigenTramites(@RequestParam("folio") String folio,@RequestParam("idOrigen") String idOrigen,
            HttpSession ses, HttpServletRequest request,
            HttpServletResponse response) {
       Map<String, Object> result = new HashMap<String, Object>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        logger.debug("Entramos a obtener origenes");
        List<NSSAdicional> listNss= new ArrayList<NSSAdicional>();
        try {
            logger.debug("idOrigen {} ",idOrigen);
            List<TramiteCorreccionCurp> tramites =tramiteNssBusiness.obtenerTramitesSolicitudByOrigen(folio,Long.parseLong(idOrigen));
            if(tramites!= null ){
                for(int i=0;i<tramites.size();i++){
                    TramiteCorreccionCurp tramiteCurp=tramites.get(i);
                    NSSAdicional nss = new NSSAdicional();
                    nss.setNss(tramiteCurp.getListaNSS().get(0));
                    List<DocumentoProbatorio> listaDocumentos= new ArrayList<DocumentoProbatorio>();
                    for(mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio documento:tramiteCurp.getDocumentosProbatorios()){
                        listaDocumentos.add(TransformerRegistroUtils.modelToVoDocumentoProbatorio(documento));
                    }
                    nss.setDocumentosProbatorios(listaDocumentos);
                    nss.setFolio(folio);
                    nss.setObservacion(tramiteCurp.getObservacion());
                    nss.setOrigen((idOrigen.equals("1")) ? "Asegurado":"Responsable");
                    listNss.add(nss);
                }
            }
           result.put("listNss",listNss);
        } catch (SolicitudNoEncontradaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TramiteNoEncontradoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);
    }
    
     @Autowired
    private AgregarNssRemote agregarNssRemote;
     
    @RequestMapping(value = RequestMappingConstants.REQUEST_ELIMINA_NSS)
    public ResponseEntity<Map<String, Object>> eliminarNss(@RequestParam("nss") String nss,
            @RequestParam("idBoveda") String idBoveda,
            @RequestParam("folio") String folio,
            HttpSession ses, HttpServletRequest request,
            HttpServletResponse response) {
       Map<String, Object> result = new HashMap<String, Object>();
         HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        logger.debug("Entramos a eliminar nss:" + nss);
        logger.debug("    idBoveda:" + idBoveda);
        logger.debug("    folio:" + folio);
       
        List<CorreccionNSS> listaNss = null;
        try {
            listaNss =  agregarNssRemote.elminarDocumento(folio, nss, idBoveda);
        } catch (SolicitudNoEncontradaException ex) {
            logger.error("    error:", ex );
        } catch (BovedaCDAException ex) {
            logger.error("    error:", ex );
        } catch (TramiteNoEncontradoException ex) {
             logger.error("    error:", ex );
        }
           
        
        result.put("data",readSolicitudUtils.getGridNssSolicitud(listaNss, false));     
       
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);
    }
    
}
