package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDT_FASES_SUBCONTRATISTAS database table.
 * 
 */
@Entity
@Table(name="FDT_FASES_SUBCONTRATISTAS")
public class FdtFasesSubcontratista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_FASE_SUBCONTRATADA", nullable=false, precision=22)
	private long idFaseSubcontratada;

	@Column(name="TX_DESC_FASE_SUBCONTRATADA", nullable=false, length=50)
	private String txDescFaseSubcontratada;

	//bi-directional many-to-one association to FdtConstruccionSubcontratist
	@OneToMany(mappedBy="fdtFasesSubcontratista")
	private List<FdtConstruccionSubcontratist> fdtConstruccionSubcontratists;

    public FdtFasesSubcontratista() {
    }

	public long getIdFaseSubcontratada() {
		return this.idFaseSubcontratada;
	}

	public void setIdFaseSubcontratada(long idFaseSubcontratada) {
		this.idFaseSubcontratada = idFaseSubcontratada;
	}

	public String getTxDescFaseSubcontratada() {
		return this.txDescFaseSubcontratada;
	}

	public void setTxDescFaseSubcontratada(String txDescFaseSubcontratada) {
		this.txDescFaseSubcontratada = txDescFaseSubcontratada;
	}

	public List<FdtConstruccionSubcontratist> getFdtConstruccionSubcontratists() {
		return this.fdtConstruccionSubcontratists;
	}

	public void setFdtConstruccionSubcontratists(List<FdtConstruccionSubcontratist> fdtConstruccionSubcontratists) {
		this.fdtConstruccionSubcontratists = fdtConstruccionSubcontratists;
	}
	
}