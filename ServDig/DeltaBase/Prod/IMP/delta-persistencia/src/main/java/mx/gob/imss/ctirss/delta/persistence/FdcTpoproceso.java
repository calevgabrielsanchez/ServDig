package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FDC_TPOPROCESO database table.
 * 
 */
@Entity
@Table(name="FDC_TPOPROCESO")
public class FdcTpoproceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TPOPROCESO", nullable=false, precision=22)
	private long idTpoproceso;

	@Column(name="DESC_PROCESO", length=50)
	private String descProceso;

	@Column(name="ID_PROCESO", precision=22)
	private BigDecimal idProceso;

	@Column(name="TX_DOCUMENTO", length=50)
	private String txDocumento;

	@Column(name="TX_TABLA", length=50)
	private String txTabla;

	//bi-directional many-to-one association to FdiHModifCpa
	@OneToMany(mappedBy="fdcTpoproceso")
	private List<FdiHModifCpa> fdiHModifCpas;

	//bi-directional many-to-one association to FdtNotariaelectronica
	@OneToMany(mappedBy="fdcTpoproceso")
	private List<FdtNotariaelectronica> fdtNotariaelectronicas;

    public FdcTpoproceso() {
    }

	public long getIdTpoproceso() {
		return this.idTpoproceso;
	}

	public void setIdTpoproceso(long idTpoproceso) {
		this.idTpoproceso = idTpoproceso;
	}

	public String getDescProceso() {
		return this.descProceso;
	}

	public void setDescProceso(String descProceso) {
		this.descProceso = descProceso;
	}

	public BigDecimal getIdProceso() {
		return this.idProceso;
	}

	public void setIdProceso(BigDecimal idProceso) {
		this.idProceso = idProceso;
	}

	public String getTxDocumento() {
		return this.txDocumento;
	}

	public void setTxDocumento(String txDocumento) {
		this.txDocumento = txDocumento;
	}

	public String getTxTabla() {
		return this.txTabla;
	}

	public void setTxTabla(String txTabla) {
		this.txTabla = txTabla;
	}

	public List<FdiHModifCpa> getFdiHModifCpas() {
		return this.fdiHModifCpas;
	}

	public void setFdiHModifCpas(List<FdiHModifCpa> fdiHModifCpas) {
		this.fdiHModifCpas = fdiHModifCpas;
	}
	
	public List<FdtNotariaelectronica> getFdtNotariaelectronicas() {
		return this.fdtNotariaelectronicas;
	}

	public void setFdtNotariaelectronicas(List<FdtNotariaelectronica> fdtNotariaelectronicas) {
		this.fdtNotariaelectronicas = fdtNotariaelectronicas;
	}
	
}