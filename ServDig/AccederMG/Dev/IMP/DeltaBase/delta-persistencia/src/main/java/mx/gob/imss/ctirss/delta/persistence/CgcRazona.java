package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_RAZONA database table.
 * 
 */
@Entity
@Table(name="CGC_RAZONA")
public class CgcRazona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_RAZONA", nullable=false, precision=22)
	private long cveRazona;

	@Column(name="TX_RAZONABILIDAD", length=50)
	private String txRazonabilidad;

	//bi-directional many-to-one association to CgtCoredisinadi
	@OneToMany(mappedBy="cgcRazona")
	private List<CgtCoredisinadi> cgtCoredisinadis;

    public CgcRazona() {
    }

	public long getCveRazona() {
		return this.cveRazona;
	}

	public void setCveRazona(long cveRazona) {
		this.cveRazona = cveRazona;
	}

	public String getTxRazonabilidad() {
		return this.txRazonabilidad;
	}

	public void setTxRazonabilidad(String txRazonabilidad) {
		this.txRazonabilidad = txRazonabilidad;
	}

	public List<CgtCoredisinadi> getCgtCoredisinadis() {
		return this.cgtCoredisinadis;
	}

	public void setCgtCoredisinadis(List<CgtCoredisinadi> cgtCoredisinadis) {
		this.cgtCoredisinadis = cgtCoredisinadis;
	}
	
}