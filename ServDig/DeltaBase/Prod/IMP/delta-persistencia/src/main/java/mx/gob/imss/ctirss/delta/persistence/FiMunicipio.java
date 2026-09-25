package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FI_MUNICIPIOS database table.
 * 
 */
@Entity
@Table(name="FI_MUNICIPIOS")
public class FiMunicipio implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FiMunicipioPK id;

	@Column(name="TX_MUNICIPIO", nullable=false, length=50)
	private String txMunicipio;

	//bi-directional many-to-one association to FiEntFed
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ENT_FED", nullable=false, insertable=false, updatable=false)
	private FiEntFed fiEntFed;

    public FiMunicipio() {
    }

	public FiMunicipioPK getId() {
		return this.id;
	}

	public void setId(FiMunicipioPK id) {
		this.id = id;
	}
	
	public String getTxMunicipio() {
		return this.txMunicipio;
	}

	public void setTxMunicipio(String txMunicipio) {
		this.txMunicipio = txMunicipio;
	}

	public FiEntFed getFiEntFed() {
		return this.fiEntFed;
	}

	public void setFiEntFed(FiEntFed fiEntFed) {
		this.fiEntFed = fiEntFed;
	}
	
}