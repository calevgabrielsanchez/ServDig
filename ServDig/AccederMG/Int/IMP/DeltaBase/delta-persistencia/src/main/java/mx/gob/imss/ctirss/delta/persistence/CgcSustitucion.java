package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_SUSTITUCION database table.
 * 
 */
@Entity
@Table(name="CGC_SUSTITUCION")
public class CgcSustitucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_SUSTITUCION", nullable=false, precision=22)
	private long cveSustitucion;

	@Column(name="TX_CLAVE", length=5)
	private String txClave;

	@Column(name="TX_SUSTITUCION", length=50)
	private String txSustitucion;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="cgcSustitucion")
	private List<CgtGestionsinadi> cgtGestionsinadis;

    public CgcSustitucion() {
    }

	public long getCveSustitucion() {
		return this.cveSustitucion;
	}

	public void setCveSustitucion(long cveSustitucion) {
		this.cveSustitucion = cveSustitucion;
	}

	public String getTxClave() {
		return this.txClave;
	}

	public void setTxClave(String txClave) {
		this.txClave = txClave;
	}

	public String getTxSustitucion() {
		return this.txSustitucion;
	}

	public void setTxSustitucion(String txSustitucion) {
		this.txSustitucion = txSustitucion;
	}

	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
}