package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_ANTECEDENTES database table.
 * 
 */
@Entity
@Table(name="CGC_ANTECEDENTES")
public class CgcAntecedente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ANTECEDENTE", nullable=false, precision=22)
	private long cveAntecedente;

	@Column(name="TX_ANTECEDENTES", length=50)
	private String txAntecedentes;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="cgcAntecedente")
	private List<CgtGestionsinadi> cgtGestionsinadis;

    public CgcAntecedente() {
    }

	public long getCveAntecedente() {
		return this.cveAntecedente;
	}

	public void setCveAntecedente(long cveAntecedente) {
		this.cveAntecedente = cveAntecedente;
	}

	public String getTxAntecedentes() {
		return this.txAntecedentes;
	}

	public void setTxAntecedentes(String txAntecedentes) {
		this.txAntecedentes = txAntecedentes;
	}

	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
}