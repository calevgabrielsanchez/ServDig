package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;

public class DetalleNssCda implements Serializable {

    private static final long serialVersionUID = 8926664838364155093L;

    private String nss;
    private Long idTipoNss;

    public String getNss() {

        return nss;
    }

    public void setNss(String nss) {

        this.nss = nss;
    }

    public Long getIdTipoNss() {

        return idTipoNss;
    }

    public void setIdTipoNss(Long idTipoNss) {

        this.idTipoNss = idTipoNss;
    }
}
