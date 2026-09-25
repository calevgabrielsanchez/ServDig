package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_TIPOAVISO database table.
 * 
 */
@Entity
@Table(name="CGC_TIPOAVISO")
public class CgcTipoaviso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_TPOAVISO", nullable=false, precision=22)
	private long cveTpoaviso;

	@Column(name="TX_TIPOAVISO", length=50)
	private String txTipoaviso;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="cgcTipoaviso")
	private List<CgtGestionsinadi> cgtGestionsinadis;

    public CgcTipoaviso() {
    }

	public long getCveTpoaviso() {
		return this.cveTpoaviso;
	}

	public void setCveTpoaviso(long cveTpoaviso) {
		this.cveTpoaviso = cveTpoaviso;
	}

	public String getTxTipoaviso() {
		return this.txTipoaviso;
	}

	public void setTxTipoaviso(String txTipoaviso) {
		this.txTipoaviso = txTipoaviso;
	}

	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
}