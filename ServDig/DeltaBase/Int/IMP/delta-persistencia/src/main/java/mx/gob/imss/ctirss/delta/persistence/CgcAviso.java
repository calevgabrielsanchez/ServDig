package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_AVISO database table.
 * 
 */
@Entity
@Table(name="CGC_AVISO")
public class CgcAviso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_AVISO", nullable=false, precision=22)
	private long cveAviso;

	@Column(name="TX_AVISO", length=50)
	private String txAviso;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="cgcAviso")
	private List<CgtGestionsinadi> cgtGestionsinadis;

    public CgcAviso() {
    }

	public long getCveAviso() {
		return this.cveAviso;
	}

	public void setCveAviso(long cveAviso) {
		this.cveAviso = cveAviso;
	}

	public String getTxAviso() {
		return this.txAviso;
	}

	public void setTxAviso(String txAviso) {
		this.txAviso = txAviso;
	}

	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
}