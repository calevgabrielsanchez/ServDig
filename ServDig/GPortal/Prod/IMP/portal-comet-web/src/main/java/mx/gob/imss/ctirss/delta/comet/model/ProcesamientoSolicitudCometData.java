package mx.gob.imss.ctirss.delta.comet.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlType(propOrder = { "folio", "exito", "mensajeError" })
public class ProcesamientoSolicitudCometData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String folio;
	private boolean exito;
	private String mensajeError;

	public String getFolio() {
		return folio;
	}

	@XmlElement(required=true)
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public boolean isExito() {
		return exito;
	}

	@XmlElement(required = true)
	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public String toString() {
		return "\n"
				+ ToStringBuilder.reflectionToString(this,
						ToStringStyle.MULTI_LINE_STYLE) + "\n";
	}
}