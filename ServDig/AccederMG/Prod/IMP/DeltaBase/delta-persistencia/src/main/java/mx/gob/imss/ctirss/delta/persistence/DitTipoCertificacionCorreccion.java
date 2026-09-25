package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;



@Entity
@Table(name = "DIT_TIPO_CERTIFICACION_CORREC")
public class DitTipoCertificacionCorreccion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQ_DICTIPOCERTIFICACIONCORREC", sequenceName = "SEQ_DICTIPOCERTIFICACIONCORREC")
    @GeneratedValue(generator = "SEQ_DICTIPOCERTIFICACIONCORREC")
    @Column(name = "CVE_ID_TIPO_CERTIFICACION", nullable = false)
    private Long cveTipoCertificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_TIPO_NSS_ACLARACION")
    private DicTipoNssAclaracion dicTipoNssAclaracion;

    /** FK **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_DETALLE_NSS")
    private DitDetalleNss ditDetalleNss;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;


    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO")
    private Date fecRegistroActualizado;

    
    public Long getCveTipoCertificacion() {
        return cveTipoCertificacion;
    }

    public void setCveTipoCertificacion(Long cveTipoCertificacion) {
        this.cveTipoCertificacion = cveTipoCertificacion;
    }

    
    public DicTipoNssAclaracion getDicTipoNssAclaracion() {
        return dicTipoNssAclaracion;
    }

    public void setDicTipoNssAclaracion(
            DicTipoNssAclaracion dicTipoNssAclaracion) {
        this.dicTipoNssAclaracion = dicTipoNssAclaracion;
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

    public DitDetalleNss getDitDetalleNss() {
        return ditDetalleNss;
    }

    public void setDitDetalleNss(DitDetalleNss ditDetalleNss) {
        this.ditDetalleNss = ditDetalleNss;
    }
}
