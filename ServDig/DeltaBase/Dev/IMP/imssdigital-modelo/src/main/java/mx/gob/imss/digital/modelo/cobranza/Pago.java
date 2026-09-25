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

/**
 * Clas que representara los valores de un pago (periodo de pago) para un seguro
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pago", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "idPago",
        "fechaInicioPeriodo",
        "fechaFinPeriodo",
        "fechaLimitePago",
        "monto",
        "lineaCaptura",
        "pdf",
        "suaPago",
        "estadoPago",
        "imprimible",
        "conBeneficio",
        "errorFormGeneral"
    })
@XmlRootElement(name = "pago", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class Pago implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del pago
     */
    private Long idPago;
    /**
     * FEcha de inicio de periodo
     */
    private Date fechaInicioPeriodo;
    /**
     * FEcha fina del periodo de pago
     */
    private Date fechaFinPeriodo;
    /**
     * Fecha limite de pago 
     */
    private Date fechaLimitePago;
    /**
     * Monto del pago
     */
    private BigDecimal monto;
    /**
     * Referencia de la linea de captura
     */
    private String lineaCaptura;
    /**
     * Archivo pdf con la linea de captura
     */    
    private byte[] pdf;
    /**
     * Detalle paa generar el archivo sua y la linea de captura
     */
    private SUAPago suaPago;
    /**
     * Estado del pago
     */
    private EstadoPago estadoPago;
    /**
     * Estado que indica si un pago puede imprimir su linea de captura
     */
    private Boolean imprimible;
    /**
     * Indica si el pago fue con beneficio
     */
    private boolean conBeneficio = false;
    /**
     * Mensajes de error
     */
    private String errorFormGeneral;
    
    /**
     * @return the fechaInicioPeriodo
     */
    public Date getFechaInicioPeriodo() {
        return fechaInicioPeriodo;
    }
    /**
     * @param fechaInicioPeriodo the fechaInicioPeriodo to set
     */
    public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
    }
    /**
     * @return the fechaFinPeriodo
     */
    public Date getFechaFinPeriodo() {
        return fechaFinPeriodo;
    }
    /**
     * @param fechaFinPeriodo the fechaFinPeriodo to set
     */
    public void setFechaFinPeriodo(Date fechaFinPeriodo) {
        this.fechaFinPeriodo = fechaFinPeriodo;
    }
    /**
     * @return the fechaLimitePago
     */
    public Date getFechaLimitePago() {
        return fechaLimitePago;
    }
    /**
     * @param fechaLimitePago the fechaLimitePago to set
     */
    public void setFechaLimitePago(Date fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }
    /**
     * @return the lineaCaptura
     */
    public String getLineaCaptura() {
        return lineaCaptura;
    }
    /**
     * @param lineaCaptura the lineaCaptura to set
     */
    public void setLineaCaptura(String lineaCaptura) {
        this.lineaCaptura = lineaCaptura;
    }
    /**
     * @return the pdf
     */
    public byte[] getPdf() {
        return pdf;
    }
    /**
     * @param pdf the pdf to set
     */
    public void setPdf(byte[] pdf) {
        this.pdf = pdf != null ? pdf.clone() : null;
    }
    /**
     * @return the suaPago
     */
    public SUAPago getSuaPago() {
        return suaPago;
    }
    /**
     * @param suaPago the suaPago to set
     */
    public void setSuaPago(SUAPago suaPago) {
        this.suaPago = suaPago;
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
     * @return the idPago
     */
    public Long getIdPago() {
        return idPago;
    }
    /**
     * @param idPago the idPago to set
     */
    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }
    /**
     * @return the estadoPago
     */
    public EstadoPago getEstadoPago() {
        return estadoPago;
    }
    /**
     * @param estadoPago the estadoPago to set
     */
    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }
    /**
     * @return the imprimible
     */
    public Boolean getImprimible() {
        return imprimible;
    }
    /**
     * @param imprimible the imprimible to set
     */
    public void setImprimible(Boolean imprimible) {
        this.imprimible = imprimible;
    }
    /**
     * @return the conBeneficio
     */
    public boolean getConBeneficio() {
        return conBeneficio;
    }
    /**
     * @param conBeneficio the conBeneficio to set
     */
    public void setConBeneficio(boolean conBeneficio) {
        this.conBeneficio = conBeneficio;
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
