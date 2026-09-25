package mx.gob.imss.digital.modelo.util;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUmaResponse", namespace = "java:mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "getUmaResponse", namespace = "java:mx.gob.imss.digital.modelo.seguros")
public class UmaResponse implements Serializable {
	
	private static final long serialVersionUID = -7356861019131925824L;
	
	private BigDecimal uma;

	public BigDecimal getUma() {
		return uma;
	}

	public void setUma(BigDecimal uma) {
		this.uma = uma;
	}
	
	@Override
	public String toString() {
		return "UmaResponse [uma=" + uma + "]";
	}

}
