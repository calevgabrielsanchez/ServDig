package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIC_MOTIVO_ACLARACION")
public class DicMotivoAclaracion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CVE_ID_MOTIVO_ACLARACION")
    private Long cveIdMotivoAclaracion;

    @Column(name = "DESC_MOTIVO_ACLARACION", length = 150, nullable = false)
    private String descMotivoAclaracion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_Actualizado")
    private Date fecRegistroActualizado;

    public Long getCveIdMotivoAclaracion() {
        return cveIdMotivoAclaracion;
    }

    public void setCveIdMotivoAclaracion(Long cveIdMotivoAclaracion) {
        this.cveIdMotivoAclaracion = cveIdMotivoAclaracion;
    }

    public String getDescMotivoAclaracion() {
        return descMotivoAclaracion;
    }

    public void setDescMotivoAclaracion(String descMotivoAclaracion) {
        this.descMotivoAclaracion = descMotivoAclaracion;
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
