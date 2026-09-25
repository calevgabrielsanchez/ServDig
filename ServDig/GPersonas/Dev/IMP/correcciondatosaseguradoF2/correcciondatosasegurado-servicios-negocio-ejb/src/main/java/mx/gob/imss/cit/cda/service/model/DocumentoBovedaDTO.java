package mx.gob.imss.cit.cda.service.model;

import java.io.Serializable;

public class DocumentoBovedaDTO implements Serializable {

    private static final long serialVersionUID = 3393910499527800601L;

    private String folio;
    private String nombre;
    private byte[] documento;
    private String ext;
    private String id;

    public String getFolio() {

        return folio;
    }

    public void setFolio(String folio) {

        this.folio = folio;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public byte[] getDocumento() {

        return documento;
    }

    public void setDocumento(byte[] documento) {

        this.documento = documento;
    }

    public String getExt() {

        return ext;
    }

    public void setExt(String ext) {

        this.ext = ext;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

}
