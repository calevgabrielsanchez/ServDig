package mx.gob.imss.cit.cda.web.reportes.vo;

import java.io.Serializable;

public class ReporteDTO implements Serializable {

    private static final long serialVersionUID = -5952596369947288090L;
    private String contentType;
    private String nombre;
    private byte[] contenido;

    public String getContentType() {

        return contentType;
    }

    public void setContentType(String contentType) {

        this.contentType = contentType;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public byte[] getContenido() {

        return contenido != null ? contenido.clone() : null;
    }

    public void setContenido(byte[] contenido) {

        this.contenido = contenido != null ? contenido.clone() : null;
    }

}