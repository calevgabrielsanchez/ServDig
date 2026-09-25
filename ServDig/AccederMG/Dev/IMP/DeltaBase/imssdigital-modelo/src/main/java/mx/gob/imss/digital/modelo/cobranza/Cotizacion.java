/**
 * 
 */
package mx.gob.imss.digital.modelo.cobranza;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.interfaces.MensajeError;

/**
 * Representacion de una cotizacion realizada por el motor de calculo
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cotizacion", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "idCotizacion",
        "fecha",
        "fechaVigencia",
        "cuotaTotal",
        "concepto",
        "renovacion",
        "detalle",
        "aplicaCuestionario",
        "errorFormGeneral"
    })
@XmlRootElement(name = "cotizacion", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class Cotizacion implements Serializable, MensajeError {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id de la cotizacion peristida
     */
    //@XmlElement( nillable = true, required = false)
    private Long idCotizacion;
    /**
     * Fecha en que se realiza la cotizacion
     */
    @XmlElement( nillable = true, required = false)
    private Date fecha;
    /**
     * Fecha hasta la cual es vigente la cotizacion
     */
    @XmlElement( nillable = true, required = false)
    private Date fechaVigencia;
    /**
     * Cantidad total de la cotizacion
     */
    @XmlElement( nillable = true, required = false)
    private BigDecimal cuotaTotal;
    /**
     * Concepto de la cotizacion
     */
    private Long concepto;
    /**
     * Indica si la cotizacion se trata de una renovacion
     */
    private Boolean renovacion;
    /**
     * Detalle de la cotizacion
     */
    @XmlElement(name="detalle", nillable = true, required = false)
    private CalculoCuota detalle;
    /**
     * Indica si el trabajador asociado a la cotizacion aplica para cuestionario
     */
    private Boolean aplicaCuestionario;
    
    /**
     * Atributo para el control de las validaciones
     * de Forma realizadas con el Validator, para poder 
     * asignar mensajes Genericos.
     */
    private String errorFormGeneral;
    
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }
    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    /**
     * @return the fechaVigencia
     */
    public Date getFechaVigencia() {
        return fechaVigencia;
    }
    /**
     * @param fechaVigencia the fechaVigencia to set
     */
    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }
    /**
     * @return the cuotaTotal
     */
    public BigDecimal getCuotaTotal() {
        return cuotaTotal;
    }
    /**
     * @param cuotaTotal the cuotaTotal to set
     */
    public void setCuotaTotal(BigDecimal cuotaTotal) {
        this.cuotaTotal = cuotaTotal;
    }
    /**
     * @return the detalle
     */
    public CalculoCuota getDetalle() {
        return detalle;
    }
    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(CalculoCuota detalle) {
        this.detalle = detalle;
    }
    /**
     * @return the concepto
     */
    public Long getConcepto() {
        return concepto;
    }
    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(Long concepto) {
        this.concepto = concepto;
    }
    /**
     * @return the renovacion
     */
    public Boolean getRenovacion() {
        return renovacion;
    }
    /**
     * @param renovacion the renovacion to set
     */
    public void setRenovacion(Boolean renovacion) {
        this.renovacion = renovacion;
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
     * @return the aplicaCuestionario
     */
    public Boolean getAplicaCuestionario() {
        return aplicaCuestionario;
    }
    /**
     * @param aplicaCuestionario the aplicaCuestionario to set
     */
    public void setAplicaCuestionario(Boolean aplicaCuestionario) {
        this.aplicaCuestionario = aplicaCuestionario;
    }
    
    
    
}
