package mx.gob.imss.digital.modelo.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerCurpPersonaResp", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "obtenerCurpPersonaResp", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class ObtenerCurpPersonaResp implements Serializable {

	private static final long serialVersionUID = 1L;
	private String curp;

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	@Override
	public String toString() {
		return "ObtenerCurpPersonaResp [curp=" + curp + "]";
	}
}
