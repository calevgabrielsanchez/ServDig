/**
 * 
 */
package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * MOdelo de datos para los seguros migrados 
 * @author NOVUTECK1
 *
 */
@Entity
@Table(name="DIT_SEGURO_IVRO_MIGRADO")
public class DitSeguroIvroMigrado implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="CVE_ID_SEGURO_IVRO")
    private Long cveIdSeguroIvro;
    
    @Column(name = "CVE_ID_MODALIDAD")
    private Long cveIdModalidad;
    
    @ManyToOne
    @JoinColumn(name="CVE_ID_PERSONA")
    private DitPersona ditPersona;
   
    @Column(name = "DELEGACION")
    private String delegacion;
    
    @Column(name = "SUBDELEGACION")
    private String subdelegacion;
    
    @Column(name = "NUM_SEG_SOCIAL")
    private String nss;
    
    @Column(name = "REG_PATRONAL")
    private String nrp;
    
    @Column(name = "IMP_SALARIO")
    private BigDecimal salario;

    /**
     * @return the cveIdSeguroIvro
     */
    public Long getCveIdSeguroIvro() {
        return cveIdSeguroIvro;
    }

    /**
     * @param cveIdSeguroIvro the cveIdSeguroIvro to set
     */
    public void setCveIdSeguroIvro(Long cveIdSeguroIvro) {
        this.cveIdSeguroIvro = cveIdSeguroIvro;
    }

    /**
     * @return the cveIdModalidad
     */
    public Long getCveIdModalidad() {
        return cveIdModalidad;
    }

    /**
     * @param cveIdModalidad the cveIdModalidad to set
     */
    public void setCveIdModalidad(Long cveIdModalidad) {
        this.cveIdModalidad = cveIdModalidad;
    }

    /**
     * @return the ditPersona
     */
    public DitPersona getDitPersona() {
        return ditPersona;
    }

    /**
     * @param ditPersona the ditPersona to set
     */
    public void setDitPersona(DitPersona ditPersona) {
        this.ditPersona = ditPersona;
    }

    /**
     * @return the delegacion
     */
    public String getDelegacion() {
        return delegacion;
    }

    /**
     * @param delegacion the delegacion to set
     */
    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    /**
     * @return the subdelegacion
     */
    public String getSubdelegacion() {
        return subdelegacion;
    }

    /**
     * @param subdelegacion the subdelegacion to set
     */
    public void setSubdelegacion(String subdelegacion) {
        this.subdelegacion = subdelegacion;
    }

    /**
     * @return the nss
     */
    public String getNss() {
        return nss;
    }

    /**
     * @param nss the nss to set
     */
    public void setNss(String nss) {
        this.nss = nss;
    }

    /**
     * @return the nrp
     */
    public String getNrp() {
        return nrp;
    }

    /**
     * @param nrp the nrp to set
     */
    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    /**
     * @return the salario
     */
    public BigDecimal getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
    
    
}
