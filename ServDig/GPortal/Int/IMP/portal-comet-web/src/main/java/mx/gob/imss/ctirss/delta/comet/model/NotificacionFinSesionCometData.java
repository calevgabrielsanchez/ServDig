package mx.gob.imss.ctirss.delta.comet.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class NotificacionFinSesionCometData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String usuario;

	public String getUsuario() {
		return usuario;
	}

	@XmlElement(required = true)
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String toString() {
		return "\n"
				+ ToStringBuilder.reflectionToString(this,
						ToStringStyle.MULTI_LINE_STYLE) + "\n";
	}

}
