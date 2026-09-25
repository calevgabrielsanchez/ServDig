package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIC_DESCRIP_BAJAS database table.
 * 
 */
@Entity
@Table(name="DIC_DESCRIP_BAJAS")
public class DicDescripBaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_BAJA", nullable=false, length=1)
	private String cveBaja;

	@Column(name="DES_BAJA", length=50)
	private String desBaja;

    public DicDescripBaja() {
    }

	public String getCveBaja() {
		return this.cveBaja;
	}

	public void setCveBaja(String cveBaja) {
		this.cveBaja = cveBaja;
	}

	public String getDesBaja() {
		return this.desBaja;
	}

	public void setDesBaja(String desBaja) {
		this.desBaja = desBaja;
	}

}