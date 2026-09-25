package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDT_TIPO_OBRA database table.
 * 
 */
@Entity
@Table(name="FDT_TIPO_OBRA")
public class FdtTipoObra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TIPO_OBRA", nullable=false, precision=22)
	private long idTipoObra;

	@Column(name="TX_DESC_OBRA", nullable=false, length=70)
	private String txDescObra;

	//bi-directional many-to-one association to FdtConstruccionObra
	@OneToMany(mappedBy="fdtTipoObra")
	private List<FdtConstruccionObra> fdtConstruccionObras;

    public FdtTipoObra() {
    }

	public long getIdTipoObra() {
		return this.idTipoObra;
	}

	public void setIdTipoObra(long idTipoObra) {
		this.idTipoObra = idTipoObra;
	}

	public String getTxDescObra() {
		return this.txDescObra;
	}

	public void setTxDescObra(String txDescObra) {
		this.txDescObra = txDescObra;
	}

	public List<FdtConstruccionObra> getFdtConstruccionObras() {
		return this.fdtConstruccionObras;
	}

	public void setFdtConstruccionObras(List<FdtConstruccionObra> fdtConstruccionObras) {
		this.fdtConstruccionObras = fdtConstruccionObras;
	}
	
}