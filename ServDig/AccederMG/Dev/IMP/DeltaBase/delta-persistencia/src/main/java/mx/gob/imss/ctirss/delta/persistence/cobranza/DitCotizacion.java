/**
 * 
 */
package mx.gob.imss.ctirss.delta.persistence.cobranza;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase de persistencia para las cotizaciones
 * 
 * @author NOVUTECK1
 * 
 */
@Entity
@Table(name = "DIT_COTIZACION")
public class DitCotizacion implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id de la cotizacion
     */
    @Id
    @SequenceGenerator(name = "DIT_COTIZACION_CVEIDCOTIZACION_GENERATOR", sequenceName = "SEQ_DITCOTIZACION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_COTIZACION_CVEIDCOTIZACION_GENERATOR")
    @Column(name="CVE_ID_COTIZACION", nullable=false, precision=22)
    private Long cveIdCotizacion;

    /**
     * FEcha en que se da de baja el registro
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;
    /**
     * Fecha en la que se actualiza el registro
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO")
    private Date fecRegistroActualizado;

    /**
     * Fecha en que se genera la cotizacion / fecha la que se da de alta el registro
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fechaInicio;
    /**
     * Cantidad total
     */
    @Column(name = "NUM_TOTAL")
    private BigDecimal numTotal;
    /**
     * Fecha hasta la cual es vigente/valida la cotización
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_VIGENCIA")
    private Date fecVigencia;
    
    /**
     * Concepto de la cotizacion
     */
    @ManyToOne
    @JoinColumn(name="CVE_ID_CONCEPTO")
    private DicConcepto dicConcepto;
    /**
     * Detalle de la cotizacion
     */
    @OneToOne(mappedBy = "ditCotizacion")
    private DitDetalleCotizacion ditDetalleCotizacion;

    /**
     * @return the cveIdCotizacion
     */
    public Long getCveIdCotizacion() {
        return cveIdCotizacion;
    }

    /**
     * @param cveIdCotizacion the cveIdCotizacion to set
     */
    public void setCveIdCotizacion(Long cveIdCotizacion) {
        this.cveIdCotizacion = cveIdCotizacion;
    }

    /**
     * @return the fecRegistroBaja
     */
    public Date getFecRegistroBaja() {
        return fecRegistroBaja;
    }

    /**
     * @param fecRegistroBaja the fecRegistroBaja to set
     */
    public void setFecRegistroBaja(Date fecRegistroBaja) {
        this.fecRegistroBaja = fecRegistroBaja;
    }

    /**
     * @return the fecRegistroActualizado
     */
    public Date getFecRegistroActualizado() {
        return fecRegistroActualizado;
    }

    /**
     * @param fecRegistroActualizado the fecRegistroActualizado to set
     */
    public void setFecRegistroActualizado(Date fecRegistroActualizado) {
        this.fecRegistroActualizado = fecRegistroActualizado;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the numTotal
     */
    public BigDecimal getNumTotal() {
        return numTotal;
    }

    /**
     * @param numTotal the numTotal to set
     */
    public void setNumTotal(BigDecimal numTotal) {
        this.numTotal = numTotal;
    }

    /**
     * @return the fecVigencia
     */
    public Date getFecVigencia() {
        return fecVigencia;
    }

    /**
     * @param fecVigencia the fecVigencia to set
     */
    public void setFecVigencia(Date fecVigencia) {
        this.fecVigencia = fecVigencia;
    }

    /**
     * @return the dicConcepto
     */
    public DicConcepto getDicConcepto() {
        return dicConcepto;
    }

    /**
     * @param dicConcepto the dicConcepto to set
     */
    public void setDicConcepto(DicConcepto dicConcepto) {
        this.dicConcepto = dicConcepto;
    }

    /**
     * @return the ditDetalleCotizacion
     */
    public DitDetalleCotizacion getDitDetalleCotizacion() {
        return ditDetalleCotizacion;
    }

    /**
     * @param ditDetalleCotizacion the ditDetalleCotizacion to set
     */
    public void setDitDetalleCotizacion(DitDetalleCotizacion ditDetalleCotizacion) {
        this.ditDetalleCotizacion = ditDetalleCotizacion;
    }   

}
