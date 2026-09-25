package mx.gob.imss.ctirss.delta.model.derechohabientes.reportes;

import java.io.Serializable;
import java.util.Date;

public class InfoDepuracion implements Serializable {

    private static final long serialVersionUID = -1253752452446334856L;

    private Date fecha;
    private String delegacion;
    private String nomDelegacion;
    private Integer depurados;

    public InfoDepuracion(Date fecha, String delegacion, String nomDelegacion, Integer depurados) {

        this.fecha = fecha;
        this.delegacion = delegacion;
        this.nomDelegacion = nomDelegacion;
        this.depurados = depurados;
    }

    public Date getFecha() {

        return fecha;
    }

    public void setFecha(Date fecha) {

        this.fecha = fecha;
    }

    public String getDelegacion() {

        return delegacion;
    }

    public void setDelegacion(String delegacion) {

        this.delegacion = delegacion;
    }

    public String getNomDelegacion() {

        return nomDelegacion;
    }

    public void setNomDelegacion(String nomDelegacion) {

        this.nomDelegacion = nomDelegacion;
    }

    public Integer getDepurados() {

        return depurados;
    }

    public void setDepurados(Integer depurados) {

        this.depurados = depurados;
    }

    @Override
    public String toString() {

        return "InfoDepuracion{" + "fecha=" + fecha + ", delegacion='" + delegacion + '\'' + ", nomDelegacion='" + nomDelegacion + '\''
                + ", depurados=" + depurados + '}';
    }
}
