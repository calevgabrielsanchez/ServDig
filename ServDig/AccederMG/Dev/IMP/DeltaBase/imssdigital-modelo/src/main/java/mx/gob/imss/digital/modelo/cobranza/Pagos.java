/**
 * 
 */
package mx.gob.imss.digital.modelo.cobranza;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.interfaces.MensajeError;

/**
 * CLase que representa la lista de pagos
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pagos", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
@XmlRootElement(name = "pagos", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class Pagos implements Serializable, MensajeError {

    /**
     * Serial version uid
     */
    private static final long serialVersionUID = 1L;

    
    /**
     * Lista de pagos
     */
    private Pago[] pago;
    /**
     * Manejo de mensajes de error
     */
    private String errorFormGeneral;

    /**
     * @return the pago
     */
    public Pago[] getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(Pago[] pago) {
        this.pago = pago != null ? pago.clone() : null;
    }

    /**
     * @return the errorFormGeneral
     */
    public String getErrorFormGeneral() {
        return errorFormGeneral;
    }

    /**
     * @param errorFormGeneral the errorFormGeneral to set
     */
    public void setErrorFormGeneral(String errorFormGeneral) {
        this.errorFormGeneral = errorFormGeneral;
    }
    
    
}
