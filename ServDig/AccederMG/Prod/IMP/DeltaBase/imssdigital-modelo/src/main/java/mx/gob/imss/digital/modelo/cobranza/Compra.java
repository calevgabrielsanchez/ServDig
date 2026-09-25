/**
 * 
 */
package mx.gob.imss.digital.modelo.cobranza;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.interfaces.MensajeError;

/**
 * Representacio de una compra en un modelo xml
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compra", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "idCompra",
        "fechaCompra",
        "fechaLimite",
        "monto",
        "idCotizacion",
        "formaPago",
        "pagos",
        "estadoCompra",
        "errorFormGeneral"
    })
@XmlRootElement(name = "compra", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class Compra implements Serializable, MensajeError {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador con el que se guarda la compra
     */
    private Long idCompra;
    /**
     * FEcha en que se realiza la compra
     */
    private Date fechaCompra;
    /**
     * FEcha limite para pagar la compra
     */
    private Date fechaLimite;
    /**
     * Monto total de la compra
     */
    private BigDecimal monto;
    /**
     * Identificador de la cotizacion
     */
    private Long idCotizacion;
    /**
     * id de la forma de pago, aanual o bimestral
     */
    private Long formaPago;
    /**
     * Pagos asociados a la compra
     */
    private Pago[] pagos;
    /**
     * Estado de la compra
     */
    private EstadoCompra estadoCompra;
    /**
     * Mensajes de error
     */
    private String errorFormGeneral;
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
     * @return the fechaCompra
     */
    public Date getFechaCompra() {
        return fechaCompra;
    }
    /**
     * @param fechaCompra the fechaCompra to set
     */
    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    /**
     * @return the fechaLimite
     */
    public Date getFechaLimite() {
        return fechaLimite;
    }
    /**
     * @param fechaLimite the fechaLimite to set
     */
    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
    /**
     * @return the idCotizacion
     */
    public Long getIdCotizacion() {
        return idCotizacion;
    }
    /**
     * @param idCotizacion the idCotizacion to set
     */
    public void setIdCotizacion(Long idCotizacion) {
        this.idCotizacion = idCotizacion;
    }
    /**
     * @return the pagos
     */
    public Pago[]  getPagos() {
        return pagos;
    }
    /**
     * @param pagos the pagos to set
     */
    public void setPagos(Pago[] pagos) {
        this.pagos = pagos != null ? pagos.clone() : null;
    }
    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }
    /**
     * @param monto the monto to set
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    /**
     * @return the formaPago
     */
    public Long getFormaPago() {
        return formaPago;
    }
    /**
     * @param formaPago the formaPago to set
     */
    public void setFormaPago(Long formaPago) {
        this.formaPago = formaPago;
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
    /**
     * @return the estadoCompra
     */
    public EstadoCompra getEstadoCompra() {
        return estadoCompra;
    }
    /**
     * @param estadoCompra the estadoCompra to set
     */
    public void setEstadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    
    
    
}
