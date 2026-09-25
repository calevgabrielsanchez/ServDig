package mx.gob.imss.cit.cda.web.app.responsable.model;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

public class TipoNSSCorreccion extends BaseModel {

    private static final long serialVersionUID = 1L;
    private Long idTipoNSSCorreccion;
    private String desTipoNSSCorreccion;

    public Long getIdTipoNSSCorreccion() {
        return idTipoNSSCorreccion;
    }

    public void setIdTipoNSSCorreccion(Long idTipoNSSCorreccion) {
        this.idTipoNSSCorreccion = idTipoNSSCorreccion;
    }

    public String getDesTipoNSSCorreccion() {
        return desTipoNSSCorreccion;
    }

    public void setDesTipoNSSCorreccion(String desTipoNSSCorreccion) {
        this.desTipoNSSCorreccion = desTipoNSSCorreccion;
    }

}
