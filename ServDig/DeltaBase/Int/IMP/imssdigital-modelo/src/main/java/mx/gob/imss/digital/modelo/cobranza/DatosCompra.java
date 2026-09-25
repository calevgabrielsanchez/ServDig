/**
 * 
 */
package mx.gob.imss.digital.modelo.cobranza;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Clase que representa los datos de una Compra de control, (Se utilizara para avisar si es pagada o vencida)
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datosCompra", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "idCompra",
        "bimestral",
        "primerPago"
    })
@XmlRootElement(name = "datosCompra", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class DatosCompra implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la compra
     */
    @XmlElement(nillable = false, required = true)
    private Long idCompra;
    /**
     * Indica si es una compra bimestral
     */
    private boolean bimestral;
    /**
     * Indica si es el primer pago en caso de ser bimestrar
     */
    private boolean primerPago;    
    /**
     * @return the idCompra
     */
    public Long getIdCompra() {
        return idCompra;
    }
    /**
     * @param idCompra the idCompra to set
     */
    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }
    /**
     * @return the bimestral
     */
    public boolean getBimestral() {
        return bimestral;
    }
    /**
     * @param bimestral the bimestral to set
     */
    public void setBimestral(boolean bimestral) {
        this.bimestral = bimestral;
    }
    /**
     * @return the primerPago
     */
    public boolean getPrimerPago() {
        return primerPago;
    }
    /**
     * @param primerPago the primerPago to set
     */
    public void setPrimerPago(boolean primerPago) {
        this.primerPago = primerPago;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.idCompra != null ? this.idCompra.hashCode() : 0);
        hash = 59 * hash + (this.bimestral ? 1 : 0);
        hash = 59 * hash + (this.primerPago ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatosCompra other = (DatosCompra) obj;
        if (this.bimestral != other.bimestral) {
            return false;
        }
        if (this.primerPago != other.primerPago) {
            return false;
        }
        if (this.idCompra != other.idCompra && (this.idCompra == null || !this.idCompra.equals(other.idCompra))) {
            return false;
        }
        return true;
    }
            
    
}
