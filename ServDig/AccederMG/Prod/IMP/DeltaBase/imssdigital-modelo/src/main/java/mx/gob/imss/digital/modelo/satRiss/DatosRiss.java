package mx.gob.imss.digital.modelo.satRiss;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.interfaces.MensajeError;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datosRiss", namespace = "http://mx.gob.imss.delta.global.service/" ) 
@XmlRootElement(name = "datosRiss", namespace = "http://mx.gob.imss.delta.global.service/")
public class DatosRiss implements Serializable, MensajeError {

	private static final long serialVersionUID = 1L;

    protected Long idPersona;
    protected String rfc;
    protected String nss;
    protected Long idOrigenSolicitud;
    protected String usuario;
    /**
     * Manejo de mensajes de error
     */
    private String errorFormGeneral;
    
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public Long getIdOrigenSolicitud() {
		return idOrigenSolicitud;
	}
	public void setIdOrigenSolicitud(Long idOrigenSolicitud) {
		this.idOrigenSolicitud = idOrigenSolicitud;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
    

	public String getErrorFormGeneral() {
        return errorFormGeneral;
    }
    public void setErrorFormGeneral(String errorFormGeneral) {
        this.errorFormGeneral = errorFormGeneral;
    }
	
	public String toString() {
		return "\n"	+ ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE) + "\n";
	}
	
}
