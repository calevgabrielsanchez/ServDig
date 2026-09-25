package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIC_CLAVE_MOVTOS database table.
 * 
 */
@Entity
@Table(name="DIC_CLAVE_MOVTOS")
public class DicClaveMovto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_MOVTO", nullable=false, length=2)
	private String cveMovto;

	@Column(name="DES_CVE_MOVTO", length=50)
	private String desCveMovto;

    public DicClaveMovto() {
    }

	public String getCveMovto() {
		return this.cveMovto;
	}

	public void setCveMovto(String cveMovto) {
		this.cveMovto = cveMovto;
	}

	public String getDesCveMovto() {
		return this.desCveMovto;
	}

	public void setDesCveMovto(String desCveMovto) {
		this.desCveMovto = desCveMovto;
	}

}