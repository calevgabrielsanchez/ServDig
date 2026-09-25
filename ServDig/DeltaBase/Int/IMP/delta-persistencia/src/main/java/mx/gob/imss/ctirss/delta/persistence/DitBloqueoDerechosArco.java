package mx.gob.imss.ctirss.delta.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="DIT_BLOQUEO_DERECHOS_ARCO")
public class DitBloqueoDerechosArco implements Serializable {

    private static final long serialVersionUID = 7155725076076560419L;

    @Id
    @Column(name = "CVE_ID_ASIGNACION_NSS")
    private Long cveIdAsignacionNss;

    @Column(name = "CVE_ID_TIPO_TRAMITE")
    private Long cveIdTipoTramite;

    @Column(name = "CVE_ID_USUARIO")
    private String cveIdUsuario;

    @Column(name = "REF_OBSERVACION")
    private String refObservacion;

    @Temporal( TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;

    @Temporal( TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO")
    private Date fecRegistroActualizado;

    @Column(name = "IND_BLOQUEO")
    private boolean indBloqueo;

    @Column(name = "CVE_ID_DELEGACION")
    private Long cveIdDelegacion;

    @Column(name = "CVE_ID_SUBDELEGACION")
    private Long cveIdSubdelegacion;

    public Long getCveIdAsignacionNss() {

        return cveIdAsignacionNss;
    }

    public void setCveIdAsignacionNss(Long cveIdAsignacionNss) {

        this.cveIdAsignacionNss = cveIdAsignacionNss;
    }

    public Long getCveIdTipoTramite() {

        return cveIdTipoTramite;
    }

    public void setCveIdTipoTramite(Long cveIdTipoSolicitud) {

        this.cveIdTipoTramite = cveIdTipoSolicitud;
    }

    public String getCveIdUsuario() {

        return cveIdUsuario;
    }

    public void setCveIdUsuario(String cveIdUsuario) {

        this.cveIdUsuario = cveIdUsuario;
    }

    public String getRefObservacion() {

        return refObservacion;
    }

    public void setRefObservacion(String refObservacion) {

        this.refObservacion = refObservacion;
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

    public boolean isIndBloqueo() {

        return indBloqueo;
    }

    public void setIndBloqueo(boolean indBloqueo) {

        this.indBloqueo = indBloqueo;
    }

    public Long getCveIdDelegacion() {

        return cveIdDelegacion;
    }

    public void setCveIdDelegacion(Long cveIdDelegacion) {

        this.cveIdDelegacion = cveIdDelegacion;
    }

    public Long getCveIdSubdelegacion() {

        return cveIdSubdelegacion;
    }

    public void setCveIdSubdelegacion(Long cveIdSubdelegacion) {

        this.cveIdSubdelegacion = cveIdSubdelegacion;
    }
}
