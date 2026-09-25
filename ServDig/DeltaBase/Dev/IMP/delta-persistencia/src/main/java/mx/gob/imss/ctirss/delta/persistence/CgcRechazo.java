package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_RECHAZO database table.
 * 
 */
@Entity
@Table(name="CGC_RECHAZO")
public class CgcRechazo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_RECHAZO", nullable=false, precision=22)
	private long cveRechazo;

	@Column(name="TX_CLAVE", length=10)
	private String txClave;

	@Column(name="TX_RECHAZO", length=100)
	private String txRechazo;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="cgcRechazo")
	private List<CgtGestionsinadi> cgtGestionsinadis;

    public CgcRechazo() {
    }

	public long getCveRechazo() {
		return this.cveRechazo;
	}

	public void setCveRechazo(long cveRechazo) {
		this.cveRechazo = cveRechazo;
	}

	public String getTxClave() {
		return this.txClave;
	}

	public void setTxClave(String txClave) {
		this.txClave = txClave;
	}

	public String getTxRechazo() {
		return this.txRechazo;
	}

	public void setTxRechazo(String txRechazo) {
		this.txRechazo = txRechazo;
	}

	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
}