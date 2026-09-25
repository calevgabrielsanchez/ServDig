package mx.gob.imss.digital.modelo.documento.probatorio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ipo documento probatorio
 * 
 * @author NOVUTECK1
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoDocumentoProbatorio", namespace = "http://mx.gob.imss.digital.modelo.documento.probatorio")
@XmlRootElement(name = "tipoDocumentoProbatorio", namespace = "http://mx.gob.imss.digital.modelo.documento.probatorio")
public class TipoDocumentoProbatorio implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del tipo de documento
     */
    private Long idTipoDocumentoProbatorio;
    /**
     * Descripcion del tipo de documento
     */
    private String descripcion;

    /**
     * @return the idTipoDocumentoProbatorio
     */
    public Long getIdTipoDocumentoProbatorio() {
        return idTipoDocumentoProbatorio;
    }

    /**
     * @param idTipoDocumentoProbatorio
     *            the idTipoDocumentoProbatorio to set
     */
    public void setIdTipoDocumentoProbatorio(Long idTipoDocumentoProbatorio) {
        this.idTipoDocumentoProbatorio = idTipoDocumentoProbatorio;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TipoDocumentoProbatorio [idTipoDocumentoProbatorio=" + idTipoDocumentoProbatorio
                + ", descripcion=" + descripcion + "]";
    }

}
