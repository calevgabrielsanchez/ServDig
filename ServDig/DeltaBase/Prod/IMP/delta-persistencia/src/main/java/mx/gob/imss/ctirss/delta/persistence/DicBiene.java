package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIC_BIENES database table.
 * 
 */
@Entity
@Table(name="DIC_BIENES")
public class DicBiene implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_BIEN", nullable=false, precision=3)
	private long cveBien;

	@Column(name="DES_BIEN", length=50)
	private String desBien;

    public DicBiene() {
    }

	public long getCveBien() {
		return this.cveBien;
	}

	public void setCveBien(long cveBien) {
		this.cveBien = cveBien;
	}

	public String getDesBien() {
		return this.desBien;
	}

	public void setDesBien(String desBien) {
		this.desBien = desBien;
	}

}