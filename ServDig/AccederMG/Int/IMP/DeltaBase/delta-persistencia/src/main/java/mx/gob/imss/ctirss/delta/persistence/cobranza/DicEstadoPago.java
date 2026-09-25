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
 * Entidad persistente con el catalo de estados de pago
 * @author NOVUTECK1
 *
 */
@Entity
@Table(name = "DIC_ESTADO_PAGO")
public class DicEstadoPago implements Serializable {
    
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id del estado del pago
     */
    @Id
//    @SequenceGenerator(name = "DIC_ESTADO_PAGO_CVEIDESTADOPAGO_GENERATOR", sequenceName = "SEQ_DICESTADOPAGO", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIC_ESTADO_PAGO_CVEIDESTADOPAGO_GENERATOR")
    @Column(name="CVE_ID_ESTADO_PAGO", nullable=false, precision=22)
    private Long cveIdEstadoPago;

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
     * Decripcion del estao del pago o valor del catalogo
     */
    @Column(name = "DES_ESTADO_PAGO")
    private String desEstadoPago;
    /**
     * @return the cveIdEstadoPago
     */
    public Long getCveIdEstadoPago() {
        return cveIdEstadoPago;
    }
    /**
     * @param cveIdEstadoPago the cveIdEstadoPago to set
     */
    public void setCveIdEstadoPago(Long cveIdEstadoPago) {
        this.cveIdEstadoPago = cveIdEstadoPago;
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
     * @return the desEstadoPago
     */
    public String getDesEstadoPago() {
        return desEstadoPago;
    }
    /**
     * @param desEstadoPago the desEstadoPago to set
     */
    public void setDesEstadoPago(String desEstadoPago) {
        this.desEstadoPago = desEstadoPago;
    }    

}
