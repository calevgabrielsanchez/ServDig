/**
 * 
 */
package mx.gob.imss.digital.modelo.seguros;

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
@XmlType(name = "comprobantesSeguroReporte", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "comprobantesSeguroReporte", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class ComprobantesSeguroReporte implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Lista de comprobantes asociados
     */
    private ComprobanteSeguroReporte comprobante[];

    /**
     * @return the comprobante
     */
    public ComprobanteSeguroReporte[] getComprobante() {
        return comprobante;
    }

    /**
     * @param comprobante the comprobante to set
     */
    public void setComprobante(ComprobanteSeguroReporte[] comprobante) {
        this.comprobante = comprobante != null ? comprobante.clone() : null;
    }

    
}
