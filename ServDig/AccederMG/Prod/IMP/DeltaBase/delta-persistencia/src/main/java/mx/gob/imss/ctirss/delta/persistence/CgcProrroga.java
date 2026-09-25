package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_PRORROGA database table.
 * 
 */
@Entity
@Table(name="CGC_PRORROGA")
public class CgcProrroga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_PRORROGA", nullable=false, precision=22)
	private long cveProrroga;

	@Column(name="TX_PRORROGA", length=50)
	private String txProrroga;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="cgcProrroga")
	private List<CgtGestionsinadi> cgtGestionsinadis;

    public CgcProrroga() {
    }

	public long getCveProrroga() {
		return this.cveProrroga;
	}

	public void setCveProrroga(long cveProrroga) {
		this.cveProrroga = cveProrroga;
	}

	public String getTxProrroga() {
		return this.txProrroga;
	}

	public void setTxProrroga(String txProrroga) {
		this.txProrroga = txProrroga;
	}

	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
}