package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "DIC_ORIGEN_CTA_IND_CDA")
public class DicOrigenCtaIndCda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CVE_ID_ORIGEN_PERIODO_CTA_IND")
    private Long cveIdOrigenPeridoCtaInd;

    @Column(name = "DES_ORIGEN_PERIODO_CTA_IND")
    private String desOrigenPeriodoCtaInd;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO")
    private Date fecRegistroActualizado;

    public Long getCveIdOrigenPeridoCtaInd() {
        return cveIdOrigenPeridoCtaInd;
    }

    public void setCveIdOrigenPeridoCtaInd(Long cveIdOrigenPeridoCtaInd) {
        this.cveIdOrigenPeridoCtaInd = cveIdOrigenPeridoCtaInd;
    }

    public String getDesOrigenPeriodoCtaInd() {
        return desOrigenPeriodoCtaInd;
    }

    public void setDesOrigenPeriodoCtaInd(String desOrigenPeriodoCtaInd) {
        this.desOrigenPeriodoCtaInd = desOrigenPeriodoCtaInd;
    }

    public Date getFecRegistroAlta() {
        return fecRegistroAlta;
    }

    public void setFecRegistroAlta(Date fecRegistroAlta) {
        this.fecRegistroAlta = fecRegistroAlta;
    }

    public Date getFecRegistroBaja() {
        return fecRegistroBaja;
    }

    public void setFecRegistroBaja(Date fecRegistroBaja) {
        this.fecRegistroBaja = fecRegistroBaja;
    }

    public Date getFecRegistroActualizado() {
        return fecRegistroActualizado;
    }

    public void setFecRegistroActualizado(Date fecRegistroActualizado) {
        this.fecRegistroActualizado = fecRegistroActualizado;
    }

}
