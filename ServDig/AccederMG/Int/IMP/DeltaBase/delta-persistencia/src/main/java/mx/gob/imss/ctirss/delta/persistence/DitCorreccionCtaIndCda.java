package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "DIT_CORRECCION_CTA_IND_CDA")
public class DitCorreccionCtaIndCda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_DITCORRCTAINDCDA", sequenceName = "SEQ_DITCORRCTAINDCDA")
    @GeneratedValue(generator = "SEQ_DITCORRCTAINDCDA")
    @Column(name = "CVE_ID_CORRECCION_CTA_IND_CDA")
    private Long cveIdCorreccionCtaIndCda;

    @JoinColumn(name = "CVE_ID_DETALLE_NSS_CDA_DEST", referencedColumnName = "CVE_ID_DETALLE_NSS_CDA")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private DitDetalleNss cveDetalleNssOperDestino;

    @JoinColumn(name = "CVE_ID_CTA_IND_OPER_DESTINO", referencedColumnName = "CVE_ID_CTA_IND")
    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    private DitCtaIndNssCda cveIdCtaIndOperDestino;

    @JoinColumn(name = "CVE_ID_MOV_OPER_DESTINO", referencedColumnName = "CVE_ID_MOV_CORRECCION")
    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    private DicMovCorrecCtaIndCda cveIdMovOperDestino;

    @JoinColumn(name = "CVE_ID_DETALLE_NSS_CDA_ORIG", referencedColumnName = "CVE_ID_DETALLE_NSS_CDA")
    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    private DitDetalleNss cveDetalleNssOperOrigen;

    @JoinColumn(name = "CVE_ID_CTA_IND_OPER_ORIGEN", referencedColumnName = "CVE_ID_CTA_IND")
    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    private DitCtaIndNssCda cveIdCtaIndOperOrigen;

    @JoinColumn(name = "CVE_ID_MOV_OPER_ORIGEN", referencedColumnName = "CVE_ID_MOV_CORRECCION")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DicMovCorrecCtaIndCda cveIdMovOperOrigen;

    @Column(name = "IND_CONSECUTIVO_MOVIMIENTO", length = 11,nullable = false)    
    private Long indConsecutivoMovimiento;

    /*@JoinColumn(name = "CVE_ID_MOV_ACLARACION_NSS", referencedColumnName = "CVE_ID_MOV_ACLARACION_NSS")
	@ManyToOne(fetch = FetchType.LAZY)	
    private DitMovAclaracionNssCda ditMovAclaracionNssCda;*/
    @Column(name = "CVE_ID_MOV_ACLARACION_NSS")
    private Long cveIdMovAclaracionNss;
    
    @Column(name = "CVE_ID_ESTADO_MOV_ENV_SINDO")
    private Long cveIdEstadoMovSindo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_MOV_ENV_SINDO")
    private Date fecMovEnvSindo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ALTA", nullable = false)
    private Date fecRegistroAlta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO", nullable = false)
    private Date fecRegistroActualizado;

    public Long getCveIdMovAclaracionNss() {
        return cveIdMovAclaracionNss;
    }

    public void setCveIdMovAclaracionNss(Long cveIdMovAclaracionNss) {
        this.cveIdMovAclaracionNss = cveIdMovAclaracionNss;
    }

    public Long getCveIdCorreccionCtaIndCda() {
        return cveIdCorreccionCtaIndCda;
    }

    public void setCveIdCorreccionCtaIndCda(Long cveIdCorreccionCtaIndCda) {
        this.cveIdCorreccionCtaIndCda = cveIdCorreccionCtaIndCda;
    }

    public DitDetalleNss getCveDetalleNssOperDestino() {
        return cveDetalleNssOperDestino;
    }

    public void setCveDetalleNssOperDestino(DitDetalleNss cveDetalleNssOperDestino) {
        this.cveDetalleNssOperDestino = cveDetalleNssOperDestino;
    }

    public DicMovCorrecCtaIndCda getCveIdMovOperDestino() {
        return cveIdMovOperDestino;
    }

    public void setCveIdMovOperDestino(DicMovCorrecCtaIndCda cveIdMovOperDestino) {
        this.cveIdMovOperDestino = cveIdMovOperDestino;
    }

    public DitDetalleNss getCveDetalleNssOperOrigen() {
        return cveDetalleNssOperOrigen;
    }

    public void setCveDetalleNssOperOrigen(DitDetalleNss cveDetalleNssOperOrigen) {
        this.cveDetalleNssOperOrigen = cveDetalleNssOperOrigen;
    }

    public DitCtaIndNssCda getCveIdCtaIndOperOrigen() {
        return cveIdCtaIndOperOrigen;
    }

    public void setCveIdCtaIndOperOrigen(DitCtaIndNssCda cveIdCtaIndOperOrigen) {
        this.cveIdCtaIndOperOrigen = cveIdCtaIndOperOrigen;
    }

    public DicMovCorrecCtaIndCda getCveIdMovOperOrigen() {
        return cveIdMovOperOrigen;
    }

    public void setCveIdMovOperOrigen(DicMovCorrecCtaIndCda cveIdMovOperOrigen) {
        this.cveIdMovOperOrigen = cveIdMovOperOrigen;
    }

    public Long getIndConsecutivoMovimiento() {
        return indConsecutivoMovimiento;
    }

    public void setIndConsecutivoMovimiento(Long indConsecutivoMovimiento) {
        this.indConsecutivoMovimiento = indConsecutivoMovimiento;
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

    public Date getFecMovEnvSindo() {
        return fecMovEnvSindo;
    }

    public void setFecMovEnvSindo(Date fecMovEnvSindo) {
        this.fecMovEnvSindo = fecMovEnvSindo;
    }

    public Long getCveIdEstadoMovSindo() {
        return cveIdEstadoMovSindo;
    }

    public void setCveIdEstadoMovSindo(Long cveIdEstadoMovSindo) {
        this.cveIdEstadoMovSindo = cveIdEstadoMovSindo;
    }

    public DitCtaIndNssCda getCveIdCtaIndOperDestino() {
        return cveIdCtaIndOperDestino;
    }

    public void setCveIdCtaIndOperDestino(DitCtaIndNssCda cveIdCtaIndOperDestino) {
        this.cveIdCtaIndOperDestino = cveIdCtaIndOperDestino;
    }

}
