package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_MOPINION database table.
 * 
 */
@Entity
@Table(name="CGC_MOPINION")
public class CgcMopinion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_MOPINION", nullable=false, precision=22)
	private long cveMopinion;

	@Column(name="TX_MOPINION", length=50)
	private String txMopinion;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="cgcMopinion")
	private List<CgtGestionsinadi> cgtGestionsinadis;

    public CgcMopinion() {
    }

	public long getCveMopinion() {
		return this.cveMopinion;
	}

	public void setCveMopinion(long cveMopinion) {
		this.cveMopinion = cveMopinion;
	}

	public String getTxMopinion() {
		return this.txMopinion;
	}

	public void setTxMopinion(String txMopinion) {
		this.txMopinion = txMopinion;
	}

	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
}