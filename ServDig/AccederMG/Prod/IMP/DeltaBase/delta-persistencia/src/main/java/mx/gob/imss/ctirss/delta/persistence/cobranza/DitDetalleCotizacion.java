/**
 * 
 */
package mx.gob.imss.ctirss.delta.persistence.cobranza;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase persistente para los detalles de una cotizacion
 * @author NOVUTECK1
 *
 */
@Entity
@Table(name = "DIT_DETALLE_COTIZACION")
public class DitDetalleCotizacion implements Serializable {
    
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id del dealle de la cotizacion
     */
    @Id
    @SequenceGenerator(name = "DIT_DETALLE_COTIZACION_CVEIDDETALLECOTIZACION_GENERATOR", 
    sequenceName = "SEQ_DITDETALLECOTIZACION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
    generator = "DIT_DETALLE_COTIZACION_CVEIDDETALLECOTIZACION_GENERATOR")
    @Column(name="CVE_ID_DETALLE_COTIZACION", nullable=false, precision=22)
    private Long cveIdDetalle;

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
     * Fecha en la que se da de alta el registro
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta = new Date();
    /**
     * Xml con el detalle de la cotizacion (salida del motor de calculo)
     */
    @Lob
    @Column(name="REF_DETALLE_COTIZACION")
    private String refDetalleXml;
        
    @ManyToOne
    @JoinColumn(name="CVE_ID_COTIZACION")
    private DitCotizacion ditCotizacion;
    /**
     * @return the cveIdDetalle
     */
    public Long getCveIdDetalle() {
        return cveIdDetalle;
    }
    /**
     * @param cveIdDetalle the cveIdDetalle to set
     */
    public void setCveIdDetalle(Long cveIdDetalle) {
        this.cveIdDetalle = cveIdDetalle;
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
     * @return the fecRegistroAlta
     */
    public Date getFecRegistroAlta() {
        return fecRegistroAlta;
    }
    /**
     * @param fecRegistroAlta the fecRegistroAlta to set
     */
    public void setFecRegistroAlta(Date fecRegistroAlta) {
        this.fecRegistroAlta = fecRegistroAlta;
    }
    /**
     * @return the refDetalleXml
     */
    public String getRefDetalleXml() {
        return refDetalleXml;
    }
    /**
     * @param refDetalleXml the refDetalleXml to set
     */
    public void setRefDetalleXml(String refDetalleXml) {
        this.refDetalleXml = refDetalleXml;
    }
    /**
     * @return the ditCotizacion
     */
    public DitCotizacion getDitCotizacion() {
        return ditCotizacion;
    }
    /**
     * @param ditCotizacion the ditCotizacion to set
     */
    public void setDitCotizacion(DitCotizacion ditCotizacion) {
        this.ditCotizacion = ditCotizacion;
    }  
    
}
