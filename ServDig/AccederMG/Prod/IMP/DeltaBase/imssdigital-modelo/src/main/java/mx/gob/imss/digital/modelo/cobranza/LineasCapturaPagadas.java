/**
 * 
 */
package mx.gob.imss.digital.modelo.cobranza;

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
@XmlType(name = "lineasCapturaPagadas", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "lineaCaptura"
    })
@XmlRootElement(name = "lineasCapturaPagadas", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class LineasCapturaPagadas implements Serializable {
    
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Lieas de captura pagadas
     */
    private String[] lineaCaptura;

    /**
     * @return the lineaCaptura
     */
    public String[] getLineaCaptura() {
        return lineaCaptura;
    }

    /**
     * @param lineaCaptura the lineaCaptura to set
     */
    public void setLineaCaptura(String[] lineaCaptura) {
        this.lineaCaptura = lineaCaptura != null ? lineaCaptura.clone() : null;
    }
    
    
}
