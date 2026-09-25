package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.io.Serializable;
import java.util.Date;

public class BloqueoDerechosArco implements Serializable {

    private static final long serialVersionUID = -7485247331740619267L;

    private Long idAsignacionNss;
    private Long idTipoTramite;
    private String idUsuario;
    private String motivos;
    private Date fecRegistroAlta;
    private Date fecRegistroBaja;
    private Date fecRegistroActualizado;
    private boolean indBloqueo;
    private Long idDelegacion;
    private Long idSubdelegacion;

    public Long getIdAsignacionNss() {

        return idAsignacionNss;
    }

    public void setIdAsignacionNss(Long idAsignacionNss) {

        this.idAsignacionNss = idAsignacionNss;
    }

    public Long getIdTipoTramite() {

        return idTipoTramite;
    }

    public void setIdTipoTramite(Long idTipoTramite) {

        this.idTipoTramite = idTipoTramite;
    }

    public String getIdUsuario() {

        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {

        this.idUsuario = idUsuario;
    }

    public String getMotivos() {

        return motivos;
    }

    public void setMotivos(String motivos) {

        this.motivos = motivos;
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

    public Long getIdDelegacion() {

        return idDelegacion;
    }

    public void setIdDelegacion(Long idDelegacion) {

        this.idDelegacion = idDelegacion;
    }

    public Long getIdSubdelegacion() {

        return idSubdelegacion;
    }

    public void setIdSubdelegacion(Long idSubdelegacion) {

        this.idSubdelegacion = idSubdelegacion;
    }
}
