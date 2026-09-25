package mx.gob.imss.cit.cda.web.bandeja.vo;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

public class TareaTramite extends BaseModel {

    private static final long serialVersionUID = 4291733008852207702L;

    private String idTarea;
    private String idTramite;

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    @Override
    public String toString() {
        return "TareaTramite [idTarea=" + idTarea + ", idTramite=" + idTramite
                + "]";
    }

}
