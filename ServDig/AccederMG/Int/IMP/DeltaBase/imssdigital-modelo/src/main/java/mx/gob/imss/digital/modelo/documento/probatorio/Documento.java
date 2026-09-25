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
@XmlType(name = "documento", namespace = "http://mx.gob.imss.digital.modelo.documento.probatorio")
@XmlRootElement(name = "documento", namespace = "http://mx.gob.imss.digital.modelo.documento.probatorio")
public class Documento implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador del documento
     */
    protected Long cveIdDocumento;
    /**
     * descripcion de documentos
     */
    protected String desDocumento;
    /**
     * @return the cveIdDocumento
     */
    public Long getCveIdDocumento() {
        return cveIdDocumento;
    }
    /**
     * @param cveIdDocumento the cveIdDocumento to set
     */
    public void setCveIdDocumento(Long cveIdDocumento) {
        this.cveIdDocumento = cveIdDocumento;
    }
    /**
     * @return the desDocumento
     */
    public String getDesDocumento() {
        return desDocumento;
    }
    /**
     * @param desDocumento the desDocumento to set
     */
    public void setDesDocumento(String desDocumento) {
        this.desDocumento = desDocumento;
    }
    
    
    

}
