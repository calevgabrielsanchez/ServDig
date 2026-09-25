package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_FORMAPAGO database table.
 * 
 */
@Entity
@Table(name="CGC_FORMAPAGO")
public class CgcFormapago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_FORMAPAGO", nullable=false, precision=22)
	private long cveFormapago;

	@Column(name="TX_FORMAPAGO", length=50)
	private String txFormapago;

	//bi-directional many-to-one association to CgtCoredisinadi
	@OneToMany(mappedBy="cgcFormapago")
	private List<CgtCoredisinadi> cgtCoredisinadis;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="cgcFormapago")
	private List<CgtGestionsinadi> cgtGestionsinadis;

    public CgcFormapago() {
    }

	public long getCveFormapago() {
		return this.cveFormapago;
	}

	public void setCveFormapago(long cveFormapago) {
		this.cveFormapago = cveFormapago;
	}

	public String getTxFormapago() {
		return this.txFormapago;
	}

	public void setTxFormapago(String txFormapago) {
		this.txFormapago = txFormapago;
	}

	public List<CgtCoredisinadi> getCgtCoredisinadis() {
		return this.cgtCoredisinadis;
	}

	public void setCgtCoredisinadis(List<CgtCoredisinadi> cgtCoredisinadis) {
		this.cgtCoredisinadis = cgtCoredisinadis;
	}
	
	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
}