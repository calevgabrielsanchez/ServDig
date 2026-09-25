package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_REGISTRADOEN database table.
 * 
 */
@Entity
@Table(name="CGC_REGISTRADOEN")
public class CgcRegistradoen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_REGISTRADOEN", nullable=false, precision=22)
	private long cveRegistradoen;

	@Column(name="TX_REGISTRADOEN", length=50)
	private String txRegistradoen;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="cgcRegistradoen")
	private List<CgtGestionsinadi> cgtGestionsinadis;

    public CgcRegistradoen() {
    }

	public long getCveRegistradoen() {
		return this.cveRegistradoen;
	}

	public void setCveRegistradoen(long cveRegistradoen) {
		this.cveRegistradoen = cveRegistradoen;
	}

	public String getTxRegistradoen() {
		return this.txRegistradoen;
	}

	public void setTxRegistradoen(String txRegistradoen) {
		this.txRegistradoen = txRegistradoen;
	}

	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
}