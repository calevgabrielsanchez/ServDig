package mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.respuesta;

import mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.vo.DocumentosByteVO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RespuestaDocumentosRTT {
    private DocumentosByteVO documentos;

    @XmlElement(nillable = true, required = false, name = "claveError")
    private Integer claveError;

    @XmlElement(nillable = true, required = false, name = "mensajeError")
    private String mensajeError;

    public void crearMensaje(Integer clave, String mensaje) {

        setClaveError(clave);
        setMensajeError(mensaje);
    }

    public DocumentosByteVO getDocumentos() {
        return documentos;
    }

    public void setDocumentos(DocumentosByteVO documentos) {
        this.documentos = documentos;
    }

    public Integer getClaveError() {
        return claveError;
    }

    public void setClaveError(Integer claveError) {
        this.claveError = claveError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
