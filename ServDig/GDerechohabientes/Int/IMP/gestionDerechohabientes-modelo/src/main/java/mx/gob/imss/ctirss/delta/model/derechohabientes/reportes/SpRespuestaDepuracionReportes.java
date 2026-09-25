package mx.gob.imss.ctirss.delta.model.derechohabientes.reportes;

import java.util.List;

public class SpRespuestaDepuracionReportes extends SpRespuestaCommon {

    private static final long serialVersionUID = -7652058064571986073L;

    private List<InfoDepuracion> info;
    private Integer indEnvioCorreo;

    public SpRespuestaDepuracionReportes() {

    }

    public SpRespuestaDepuracionReportes(List<InfoDepuracion> info, Integer indEnvioCorreo) {

        this.info = info;
        this.indEnvioCorreo = indEnvioCorreo;
    }

    public List<InfoDepuracion> getInfo() {

        return info;
    }

    public void setInfo(List<InfoDepuracion> info) {

        this.info = info;
    }

    public Integer getIndEnvioCorreo() {

        return indEnvioCorreo;
    }

    public void setIndEnvioCorreo(Integer indEnvioCorreo) {

        this.indEnvioCorreo = indEnvioCorreo;
    }
}
