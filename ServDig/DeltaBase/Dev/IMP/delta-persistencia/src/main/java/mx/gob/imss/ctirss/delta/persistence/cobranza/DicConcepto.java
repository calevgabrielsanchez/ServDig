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
 * Entidad persistente con el catalode conceptos de cotizacion
 * @author NOVUTECK1
 *
 */
@Entity
@Table(name="DIC_CONCEPTO")
public class DicConcepto implements Serializable {
    
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id del concepto
     */
    @Id
//    @SequenceGenerator(name = "DIC_CONCEPTO_CVEIDCONCEPTO_GENERATOR", sequenceName = "SEQ_DICCONCEPTO", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIC_CONCEPTO_CVEIDCONCEPTO_GENERATOR")
    @Column(name="CVE_ID_CONCEPTO", nullable=false, precision=22)
    private Long cveIdConcepto;

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
     * Decripcion del concepto o valor del catalogo
     */
    @Column(name = "DES_CONCEPTO")
    private String desConcepto;
    /**
     * @return the cveIdConcepto
     */
    public Long getCveIdConcepto() {
        return cveIdConcepto;
    }
    /**
     * @param cveIdConcepto the cveIdConcepto to set
     */
    public void setCveIdConcepto(Long cveIdConcepto) {
        this.cveIdConcepto = cveIdConcepto;
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
     * @return the desConcepto
     */
    public String getDesConcepto() {
        return desConcepto;
    }
    /**
     * @param desConcepto the desConcepto to set
     */
    public void setDesConcepto(String desConcepto) {
        this.desConcepto = desConcepto;
    }
    
    

}
