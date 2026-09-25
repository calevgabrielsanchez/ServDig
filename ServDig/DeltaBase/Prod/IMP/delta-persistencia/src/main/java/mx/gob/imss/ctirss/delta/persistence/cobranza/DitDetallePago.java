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
 * Clase persistente para los detalles de cada apago a realizar
 * @author NOVUTECK1
 *
 */
@Entity
@Table(name = "DIT_DETALLE_PAGO")
public class DitDetallePago implements Serializable {
    
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id del detalle de pago
     */
    @Id
    @SequenceGenerator(name = "DIT_DETALLE_PAGO_CVEIDDETALLEPAGO_GENERATOR", sequenceName = "SEQ_DITDETALLEPAGO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_DETALLE_PAGO_CVEIDDETALLEPAGO_GENERATOR")
    @Column(name="CVE_ID_DETALLE_PAGO", nullable=false, precision=22)
    private Long cveIdDetallePago;

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
    @Column(name="REF_DETALLE_PAGO")
    private String refDetalleXml;
    /**
     * Pagos asociado al detalle
     */
    @ManyToOne
    @JoinColumn(name="CVE_ID_PAGO")
    private DitPago ditPago;
    /**
     * @return the cveIdDetallePago
     */
    public Long getCveIdDetallePago() {
        return cveIdDetallePago;
    }
    /**
     * @param cveIdDetallePago the cveIdDetallePago to set
     */
    public void setCveIdDetallePago(Long cveIdDetallePago) {
        this.cveIdDetallePago = cveIdDetallePago;
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
     * @return the ditPago
     */
    public DitPago getDitPago() {
        return ditPago;
    }
    /**
     * @param ditPago the ditPago to set
     */
    public void setDitPago(DitPago ditPago) {
        this.ditPago = ditPago;
    }
        
}
