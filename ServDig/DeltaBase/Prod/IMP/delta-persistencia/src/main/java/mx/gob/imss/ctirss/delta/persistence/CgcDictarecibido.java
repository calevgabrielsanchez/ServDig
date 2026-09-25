package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_DICTARECIBIDOS database table.
 * 
 */
@Entity
@Table(name="CGC_DICTARECIBIDOS")
public class CgcDictarecibido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_DICTARECIBIDOS", nullable=false, precision=22)
	private long cveDictarecibidos;

	@Column(name="TX_DICTARECIBIDOS", length=50)
	private String txDictarecibidos;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="cgcDictarecibido")
	private List<CgtGestionsinadi> cgtGestionsinadis;

    public CgcDictarecibido() {
    }

	public long getCveDictarecibidos() {
		return this.cveDictarecibidos;
	}

	public void setCveDictarecibidos(long cveDictarecibidos) {
		this.cveDictarecibidos = cveDictarecibidos;
	}

	public String getTxDictarecibidos() {
		return this.txDictarecibidos;
	}

	public void setTxDictarecibidos(String txDictarecibidos) {
		this.txDictarecibidos = txDictarecibidos;
	}

	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
}