package mx.gob.imss.ctirss.correccion.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the CRC_ACTECONOMICA database table.
 * 
 */
@Entity
@Table(name = "CRC_ACTECONOMICA")
public class AbstractCrcActeconomica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_ACTECONOMICA")
	private long cveActeconomica;

	@Column(name = "ACT_ECONOMICA")
	private String actEconomica;

	public AbstractCrcActeconomica() {
	}

	public long getCveActeconomica() {
		return this.cveActeconomica;
	}

	public void setCveActeconomica(long cveActeconomica) {
		this.cveActeconomica = cveActeconomica;
	}

	public String getActEconomica() {
		return this.actEconomica;
	}

	public void setActEconomica(String actEconomica) {
		this.actEconomica = actEconomica;
	}

}