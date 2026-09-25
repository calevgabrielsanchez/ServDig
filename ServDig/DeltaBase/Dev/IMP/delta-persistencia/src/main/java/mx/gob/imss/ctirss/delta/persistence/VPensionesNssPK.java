package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VPensionesNssPK implements Serializable {


	private static final long serialVersionUID = 1L;

	@Column(name = "CVE_ID_PENSION")
	private BigDecimal cveIdPension;

	@Column(name = "ID_NSS")
	private String idNss;

	public BigDecimal getCveIdPension() {
		return this.cveIdPension;
	}

	public void setCveIdPension(BigDecimal cveIdPension) {
		this.cveIdPension = cveIdPension;
	}

	public String getIdNss() {
		return this.idNss;
	}

	public void setIdNss(String idNss) {
		this.idNss = idNss;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cveIdPension == null) ? 0 : cveIdPension.hashCode());
		result = prime * result + ((idNss == null) ? 0 : idNss.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VPensionesNssPK other = (VPensionesNssPK) obj;
		if (cveIdPension == null) {
			if (other.cveIdPension != null)
				return false;
		} else if (!cveIdPension.equals(other.cveIdPension))
			return false;
		if (idNss == null) {
			if (other.idNss != null)
				return false;
		} else if (!idNss.equals(other.idNss))
			return false;
		return true;
	}

	
}
