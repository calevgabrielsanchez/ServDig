package mx.gob.imss.ctirss.delta.model.riesgosTrabajo;

import java.io.Serializable;
import java.util.Date;

public class ReporteRiesgoTrabajo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3050807439523409854L;

    private String rfc;
    private Date fechaAlta;
    private Date fechaActualiza;
    private Date fechaBaja;
    private String urlReporte;
    private Long generaReporte;
    private Long estadoReporte;
    private byte[] documento;

    public String getRfc() {return rfc;}

    public void setRfc(String rfc) {this.rfc = rfc;}

    public Date getFechaAlta() {return fechaAlta;}

    public void setFechaAlta(Date fechaAlta) {this.fechaAlta = fechaAlta;}

    public Date getFechaActualiza() {return fechaActualiza;}

    public void setFechaActualiza(Date fechaActualiza) {this.fechaActualiza = fechaActualiza;}

    public Date getFechaBaja() {return fechaBaja;}

    public void setFechaBaja(Date fechaBaja) {this.fechaBaja = fechaBaja;}

    public String getUrlReporte() {return urlReporte;}

    public void setUrlReporte(String urlReporte) {this.urlReporte = urlReporte;}

    public Long getGeneraReporte() {return generaReporte;}

    public void setGeneraReporte(Long generaReporte) {this.generaReporte = generaReporte;}

    public Long getEstadoReporte() {return estadoReporte;}

    public void setEstadoReporte(Long estadoReporte) {this.estadoReporte = estadoReporte;}

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }
}
