package mx.gob.imss.digital.modelo.util;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerSalarioMinimoResponse", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "obtenerSalarioMinimoResponse", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class obtenerSalarioMinimoVigenteDFResp implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal salarioMinimo;

	public BigDecimal getSalarioMinimo() {
		return salarioMinimo;
	}

	public void setSmvgdf(BigDecimal salarioMinimo) {
		this.salarioMinimo = salarioMinimo;
	}

	@Override
	public String toString() {
		return "obtenerSalarioMinimoVigenteDFResp [smvgdf=" + salarioMinimo + "]";
	}
}
