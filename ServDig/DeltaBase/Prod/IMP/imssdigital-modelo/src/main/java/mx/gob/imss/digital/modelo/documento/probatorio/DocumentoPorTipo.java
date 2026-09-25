/**
 * 
 */
package mx.gob.imss.digital.modelo.documento.probatorio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documentoPorTipo", namespace = "http://mx.gob.imss.digital.modelo.documento.probatorio")
@XmlRootElement(name = "documentoPorTipo", namespace = "http://mx.gob.imss.digital.modelo.documento.probatorio")
public class DocumentoPorTipo implements Serializable {
    
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -7679617085570835897L;
    /**
     * Tipo de documento probatorio
     */
    private TipoDocumentoProbatorio tipoDocumentoProbatorio;
    /**
     * Documento
     */
    private Documento documento;
    /**
     * Id del documento
     */
    private Long idDocumentoPorTipo;

    /**
     * Idtipo hashed
     */
    private String idDocumentoPorTipoHashed;

    public TipoDocumentoProbatorio getTipoDocumentoProbatorio() {
        return tipoDocumentoProbatorio;
    }

    public void setTipoDocumentoProbatorio(
            TipoDocumentoProbatorio tipoDocumentoProbatorio) {
        this.tipoDocumentoProbatorio = tipoDocumentoProbatorio;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Long getIdDocumentoPorTipo() {
        return idDocumentoPorTipo;
    }

    public void setIdDocumentoPorTipo(Long idDocumentoPorTipo) {
        this.idDocumentoPorTipo = idDocumentoPorTipo;
    }

    public String getIdDocumentoPorTipoHashed() {
        return idDocumentoPorTipoHashed;
    }

    public void setIdDocumentoPorTipoHashed(String idDocumentoPorTipoHashed) {
        this.idDocumentoPorTipoHashed = idDocumentoPorTipoHashed;
    }

}
