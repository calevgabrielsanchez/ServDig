package mx.gob.imss.cit.cda.web.helper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.web.enums.RespuestaPeticionEnum;
import mx.gob.imss.cit.cda.web.utils.TramiteCorreccionUtil;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DocumentoProbatorioHelper {

    @Autowired
    @Qualifier("bovedaBusiness")
    private BovedaRemote bovedaBusiness;

    @Autowired
    private TramiteCorreccionUtil tramiteCorreccionUtil;

    private final Logger log = LoggerFactory
            .getLogger(DocumentoProbatorioHelper.class);

    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * Adjunta un documento a boveda y retorna un mapa con id de boveda y estado
     * de la peticion
     * 
     * @param file
     * @param idDocporTipo
     * @param cveIdDocumento
     * @param desDocumento
     * @param tipoDocumento
     * @param solicitud
     * @param tamanioMaximoArchivo
     * @return
     * @throws IOException
     */
    public Map<String, Object> adjuntarDocumentoBoveda(MultipartFile file,
            String idDocporTipo, String cveIdDocumento, String desDocumento,
            String tipoDocumento, Solicitud solicitud, Long tamanioMaximoArchivo)
            throws IOException {


        byte[] conv = file.getOriginalFilename().getBytes(ISO_8859_1);
        String valueConv = new String(conv, UTF_8);

        Map<String, Object> result = new HashMap<String, Object>();
        log.debug("---CDA--- Tamanio Archivo {} ", file.getSize());
        if (file.getSize() <= tamanioMaximoArchivo) {
            log.debug("---CDA--- #####Subiendo Archivo######");
            String[] n = file.getOriginalFilename().split("\\.");
            String idDocBoveda = null;
            String nombreCompleto = null;
            try {
                nombreCompleto = idDocporTipo + "_" + valueConv;
                idDocBoveda = bovedaBusiness.subirDocumento(file.getBytes(),
                        solicitud, nombreCompleto, n[n.length - 1],
                        file.getContentType());
            } catch (BovedaCDAException bce) {
                log.error(bce.getSituacion());
                result.put(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                        .getDescripcion(), bce.getMensajeError());
                result.put("__situacion_", bce.getSituacion());

                return result;
            } catch (Exception e) {
                log.error("---CDA--- Error al subir el documento {}", e);
                result.put(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                        .getDescripcion(),
                        "Error al adjuntar Documento. Intente nuevamente.");
                return result;
            }

            if (idDocBoveda != null) {
                log.debug("---CDA--- documento insertado en boveda {}",
                        idDocBoveda);
                result.put(RespuestaPeticionEnum.STATUS_SUCCESS_UPLOAD
                        .getDescripcion(),
                        RespuestaPeticionEnum.STATUS_SUCCESS_UPLOAD
                                .getDescripcion());
                result.put(RespuestaPeticionEnum.ID_DOCUMENTO_BOVEDA
                        .getDescripcion(), idDocBoveda);
            } else {
                log.debug("---CDA--- Error en createDocument ::: el idDocBoveda es nulo");
                result.put(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                        .getDescripcion(),
                        "Error al adjuntar Documento. Intente nuevamente.");
            }

        } else {
            result.put(
                    RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
                    "El archivo no cumple con el tama\u00f1o m\u00e1ximo permitido [4MB]. No es posible adjuntar el archivo.");
        }
        return result;
    }

//    /**
//     * Metodo que elimina los documentos en boveda del nss y al finalizar
//     * cancela el tramite
//     * 
//     * @param listNSS
//     * @param result
//     * @param nssvo
//     * @param solicitud
//     * @return
//     */
//    public Map<String, Object> eliminaTramiteNss(List<NSSVO> listNSS,
//            Map<String, Object> result, NSSVO nssvo, Solicitud solicitud) {
//        Boolean bandera = eliminaNSS(listNSS, result, nssvo);
//        /* Valida la bandera que sea falsa */
//        if (!bandera) {
//            result.put(RespuestaPeticionEnum.STATUS_SUCCESS_UPLOAD
//                    .getDescripcion(),
//                    "Los documentos eliminados con \u00e9xito del NSS ");
//            tramiteCorreccionUtil.cancelarNssTramiteXml(solicitud,
//                    nssvo.getNSS());
//        }
//        return result;
//    }

    /**
     * Metodo que elimina los documentos de informacion adicional de boveda del
     * nss asociado
     * 
     * @param listNSS
     * @param result
     * @param nssvo
     * @param solicitud
     * @return
     */
    public Map<String, Object> eliminaNssInformacionAdicional(
            List<NSSVO> listNSS, Map<String, Object> result, NSSVO nssvo,
            Solicitud solicitud) {
        Boolean bandera = eliminaNSS(listNSS, result, nssvo);
        /* Valida la bandera que sea falsa */
        if (!bandera) {
            result.put(RespuestaPeticionEnum.STATUS_SUCCESS_UPLOAD
                    .getDescripcion(),
                    "Los documentos eliminados con \u00e9xito del NSS ");
        }
        return result;
    }

    public Map<String, Object> removerDocumentoByList(
            List<DocumentoProbatorio> listDocumento,
            Map<String, Object> result, DocumentoProbatorio docuemntoProbatorio) {
        if (listDocumento != null && !listDocumento.isEmpty()) {
            Iterator<DocumentoProbatorio> it = listDocumento.iterator();
            while (it.hasNext()) {
                DocumentoProbatorio doc = it.next();
                if (doc.getCveIdDocumento().equals(
                        docuemntoProbatorio.getCveIdDocumento())
                        && doc.getIdDocBoveda().equals(
                                docuemntoProbatorio.getIdDocBoveda())) {
                    result = eliminarDocumentoProbBoveda(result, doc);
                    if (result.get(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                            .getDescripcion()) == null) {
                        log.debug("---CDA-- Se remueve documento",doc.getCveIdDocumento());
                        it.remove();
                    }
                }
            }
        } else {
            log.error("---CDA-- Lista de documentos vacia {}");
        }
        return result;
    }

    public Map<String, Object> eliminarDocumentoProbBoveda(
            Map<String, Object> result, DocumentoProbatorio doc) {
        try {
            String confirmacion = bovedaBusiness.eliminarDocumento(doc
                    .getIdDocBoveda());
            if (confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001
                    .getCodigo())
                    || confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5004
                            .getCodigo())) {
                result.put(
                        RespuestaPeticionEnum.STATUS_SUCCESS_UPLOAD
                                .getDescripcion(),
                        new StringBuilder(MensajesBovedaCDAEnum.MSJ_BP5001
                                .getCodigo())
                                .append(" - ")
                                .append(MensajesBovedaCDAEnum.MSJ_BP5001
                                        .getDescripcion()).append(".")
                                .toString());
            }
        } catch (BovedaCDAException bce) {
            log.error("---CDA-- Ocurrio un error {}", bce.getSituacion());
            result.put(
                    RespuestaPeticionEnum.STATUS_ERROR_UPLOAD.getDescripcion(),
                    bce.getMensajeError());
        }
        return result;
    }

    private Boolean eliminaNSS(List<NSSVO> listNSS, Map<String, Object> result,
            NSSVO nssvo) {
        Boolean bandera = Boolean.FALSE;
        /* Valida si la lista de nss es nula o vacia */
        if (listNSS != null && !listNSS.isEmpty()) {
            Iterator<NSSVO> it = listNSS.iterator();
            /* Correcore la lista */
            while (it.hasNext()) {
                NSSVO nss = it.next();
                /* Valida si el nss es el que se va a borrar */
                if (nss.getNSS().equals(nssvo.getNSS())) {
                    bandera = removerDocumentos(
                            nss.getDocumentoProbatorioList(), bandera, result);
                    /* Borra el nss */
                    it.remove();
                }
            }
        }
        return bandera;
    }

    private Boolean removerDocumentos(
            List<DocumentoProbatorio> listaDocumentosNss, Boolean bandera,
            Map<String, Object> result) {
        /*
         * Recorre sus documentos para borrarlos, si ocurre un error en algun
         * documento la bandera cambiara a verdadera
         */
        for (DocumentoProbatorio doc : listaDocumentosNss) {
            log.error("---CDA-- id boveda {}", doc.getIdDocBoveda());
            try {
                String confirmacion = bovedaBusiness.eliminarDocumento(doc
                        .getIdDocBoveda());
                log.error("---CDA-- confirmacion {}", confirmacion);
                if (confirmacion != null
                        && !confirmacion
                                .contains(MensajesBovedaCDAEnum.MSJ_BP5001
                                        .getCodigo())) {
                    bandera = Boolean.TRUE;
                    result.put(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                            .getDescripcion(), confirmacion);
                }
            } catch (BovedaCDAException bce) {
                bandera = Boolean.TRUE;
                log.error("---CDA-- Ocurrio un error {}", bce.getSituacion());
                result.put(RespuestaPeticionEnum.STATUS_ERROR_UPLOAD
                        .getDescripcion(), bce.getMensajeError());
            }
        }
        return bandera;
    }
}
