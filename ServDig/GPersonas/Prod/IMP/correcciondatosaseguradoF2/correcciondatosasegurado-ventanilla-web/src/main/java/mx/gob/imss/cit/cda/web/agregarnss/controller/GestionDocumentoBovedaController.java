package mx.gob.imss.cit.cda.web.agregarnss.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractUpdateController;
import mx.gob.imss.cit.cda.web.app.common.helper.DocumentoProbatorioHelper;
import mx.gob.imss.cit.cda.web.app.common.model.enums.RespuestaPeticionEnum;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.informacionadicional.constants.InformacionAdicionalConstants;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GestionDocumentoBovedaController extends AbstractUpdateController<DocumentoProbatorio, DocumentoProbatorio> {

    @Override
    public UpdateHelper<DocumentoProbatorio, DocumentoProbatorio> getHelper() {
        return null;
    }

    private final Logger log = LoggerFactory
            .getLogger(GestionDocumentoBovedaController.class);

    @Autowired
    private DocumentoProbatorioHelper documentoProbatorioHelper;

    @Autowired
    @Qualifier("bovedaBusiness")
    private BovedaRemote bovedaBusiness;

    @RequestMapping(value = RequestMappingConstants.READ_GUARDAR_DOCUMENTO_BOVEDA, method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> guardarBoveda(@RequestParam("fileData") MultipartFile file,
            @RequestParam("idDocPorTipo") String idDocporTipo,
            @RequestParam("cveIdDocumento") String cveIdDocumento,
            @RequestParam("desDocumento") String desDocumento,
            @RequestParam("tipoDocumento") String tipoDocumento,
            @RequestParam("noFolioSolicitud") String noFolioSolicitud,
            @RequestParam("solicitudId") String solicitudId,
            @RequestParam("nombre") String nombre,
            HttpSession ses, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        log.debug("************Entramos a guardar a boveda************");
        Solicitud solicitud = new Solicitud();
        solicitud.setNoFolioSolicitud(noFolioSolicitud);
        solicitud.setSolicitudId(new Long(solicitudId));
        try {
            result = documentoProbatorioHelper
                    .adjuntarDocumentoBoveda(file, idDocporTipo, cveIdDocumento,
                            desDocumento, tipoDocumento, solicitud,
                            InformacionAdicionalConstants.TAMANIO_MAXIMO_ARCHIVO);
        } catch (IOException e) {

            log.error("Error de boveda {}" + e);
            result.put(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
                    "Error al adjuntar Documento. Intente nuevamente.");
            return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                    HttpStatus.PRECONDITION_FAILED);
        }

        if (result.get(RespuestaPeticionEnum.STATUS_SUCCESS_UPLOAD
                .getDescripcion()) != null
                && result.get(RespuestaPeticionEnum.ID_DOCUMENTO_BOVEDA
                        .getDescripcion()) != null) {

            String idDocBoveda = (String) result
                    .get(RespuestaPeticionEnum.ID_DOCUMENTO_BOVEDA.getDescripcion());

            log.debug("idDocBoveda -->" + idDocBoveda);
        } else {
            log.debug(result
                    .get(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                            .getDescripcion()).toString());

            if (result.get(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                    .getDescripcion()) != null) {
                result.put(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
                        result.get(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion()).toString());
            } else {
                result.put(
                        RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
                        "Error al adjuntar Documento. Intente nuevamente.");
            }

            return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                    HttpStatus.PRECONDITION_FAILED);
        }
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);
    }

    @RequestMapping(value = RequestMappingConstants.ELIMINA_DOCUMENTO_BOVEDA, method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> eliminarBoveda(@RequestParam("idDocBoveda") String idDocBoveda,
            @RequestParam("cveIdDocumento") String cveIdDocumento,
            HttpSession ses, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        try {
            String confirmacion = bovedaBusiness.eliminarDocumento(idDocBoveda);
            if (confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001.getCodigo()) || confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5004.getCodigo())) {

            } else {
                result.put(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(), confirmacion);
            }
        } catch (BovedaCDAException bce) {
            log.error("---CDA-- Ocurrio un error {}", bce);
            result.put(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(), bce.getMensajeError());
        }
        return new ResponseEntity<Map<String, Object>>(result, httpHeaders,
                HttpStatus.OK);
    }

}
