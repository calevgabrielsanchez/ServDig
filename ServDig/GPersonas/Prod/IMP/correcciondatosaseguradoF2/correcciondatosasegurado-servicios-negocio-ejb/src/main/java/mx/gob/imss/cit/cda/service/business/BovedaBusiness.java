package mx.gob.imss.cit.cda.service.business;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.service.model.DocumentoBovedaDTO;
import mx.gob.imss.cit.cda.service.utility.BovedaUtilityLocal;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "bovedaBusiness", mappedName = "bovedaBusiness")
public class BovedaBusiness extends AbstractServiceUtility implements BovedaRemote {

    @EJB
    private BovedaUtilityLocal bovedaUtility;

    private static final String PDF_EXTENSION = "pdf";
    private static final String PDF_MIME_TYPE = "application/pdf";
    private static final String TIPO_ID_USR = "IDPERSONA";
    private static final String SEPARADOR_NOMBRE = "_";
    private static final String ERROR_DESCARGAR_ARCHIVO = "Error al Descargar el Archivo con ObjectID ";
    private static final String NANOSEGUNDOS = " nanoSegundos";
    private static final String CDA_CONSULTA_TARDO = "---CDA--- La Consulta se tardo  ";
    private final Logger log = LoggerFactory.getLogger(BovedaBusiness.class);

    @Override
    public String subirDocumento(byte[] documentoCertificado, Solicitud solicitud, TipoDocumentoCDAEnum tipoDocumentoCDAEnum)
            throws BovedaCDAException {

        if (documentoCertificado != null) {

            DocumentoBovedaDTO documentoBovedaDTO = new DocumentoBovedaDTO();
            documentoBovedaDTO.setNombre(
                    crearNombreArchivo(tipoDocumentoCDAEnum.getPrefijo() + SEPARADOR_NOMBRE + solicitud.getNoFolioSolicitud() + "." + PDF_EXTENSION));
            documentoBovedaDTO.setExt(PDF_EXTENSION);
            documentoBovedaDTO.setDocumento(documentoCertificado);
            documentoBovedaDTO.setFolio(solicitud.getNoFolioSolicitud());

            return bovedaUtility.createDocument(documentoBovedaDTO);

        }
        return null;

    }

    public String subirDocumento(byte[] archivo, Solicitud solicitud, String nombreDoc, String extension, String mimeType) throws BovedaCDAException {

        if (archivo != null) {
            DocumentoBovedaDTO documentoBovedaDTO = new DocumentoBovedaDTO();
            documentoBovedaDTO.setNombre(nombreDoc);
            documentoBovedaDTO.setExt(extension);
            documentoBovedaDTO.setDocumento(archivo);
            documentoBovedaDTO.setFolio(solicitud.getNoFolioSolicitud());

            return bovedaUtility.createDocument(documentoBovedaDTO);
        }
        return null;
    }

    @Override
    public byte[] recuperarDocumento(Solicitud solicitud, TipoDocumentoCDAEnum tipoDocumentoCDAEnum, String objectId) throws BovedaCDAException {

        log.info("---CDA--- Iniciando consulta {}", new Date());
        return bovedaUtility.getDocument(objectId).getDocumento();

    }

    @Override
    public byte[] recuperarDocumento(Solicitud solicitud, String idDocBoveda, String nombreDoc) throws BovedaCDAException {

        log.info("---CDA--- Iniciando consulta {}", new Date());
        return bovedaUtility.getDocument(idDocBoveda).getDocumento();

    }

    public String eliminarDocumento(String idDocBoveda) throws BovedaCDAException {

        return bovedaUtility.deleteDocument(idDocBoveda);
    }

    private String crearNombreArchivo(String nombre) {

        StringBuilder stb = new StringBuilder(nombre);
        stb.insert(0, System.nanoTime());
        return stb.toString();
    }

}
