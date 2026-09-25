/**
 * 
 */
package mx.gob.imss.ctirss.delta.persistence.cobranza;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entidad persistente con el catalo de formas de pago 
 * @author NOVUTECK1
 *
 */
@Entity
@Table(name = "DIC_FORMA_PAGO")
public class DicFormaPago implements Serializable {
    
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id de la forma del pago
     */
    @Id
//    @SequenceGenerator(name = "DIC_FORMA_PAGO_CVEIDFORMAPAGO_GENERATOR", sequenceName = "SEQ_DICFORMAPAGO", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIC_FORMA_PAGO_CVEIDFORMAPAGO_GENERATOR")
    @Column(name="CVE_ID_FORMA_PAGO", nullable=false, precision=22)
    private Long cveIdFormaPago;

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
     * Decripcion de la forma del pago o valor del catalogo
     */
    @Column(name = "DES_FORMA_PAGO")
    private String desFormaPago;
    /**
     * @return the cveIdFormaPago
     */
    public Long getCveIdFormaPago() {
        return cveIdFormaPago;
    }
    /**
     * @param cveIdFormaPago the cveIdFormaPago to set
     */
    public void setCveIdFormaPago(Long cveIdFormaPago) {
        this.cveIdFormaPago = cveIdFormaPago;
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
     * @return the desFormaPago
     */
    public String getDesFormaPago() {
        return desFormaPago;
    }
    /**
     * @param desFormaPago the desFormaPago to set
     */
    public void setDesFormaPago(String desFormaPago) {
        this.desFormaPago = desFormaPago;
    }
    
    
}
