package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Documento implements Serializable {

    private static final long serialVersionUID = 6412672824400316568L;
    private String documento;
    private String idTramite;
    private String idPersona;
    private String extension;
    private String nombreArchivo;
    private String folio;
    private String idDocBoveda;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public void setIdDocBoveda(String idDocBoveda) {
        this.idDocBoveda = idDocBoveda;
    }

    public String getIdDocBoveda() {
        return idDocBoveda;
    }
}
