package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FDC_TPOSTATUS database table.
 * 
 */
@Entity
@Table(name="FDC_TPOSTATUS")
public class FdcTpostatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="STATUS_ENTREGA_DICT", nullable=false, length=20)
	private String statusEntregaDict;

	@Column(name="CVE_TPOFISCALIZA", precision=22)
	private BigDecimal cveTpofiscaliza;

	@Column(name="DESC_STATUS", length=50)
	private String descStatus;

	//bi-directional many-to-one association to FdtAviso
	@OneToMany(mappedBy="fdcTpostatus")
	private List<FdtAviso> fdtAvisos;

    public FdcTpostatus() {
    }

	public String getStatusEntregaDict() {
		return this.statusEntregaDict;
	}

	public void setStatusEntregaDict(String statusEntregaDict) {
		this.statusEntregaDict = statusEntregaDict;
	}

	public BigDecimal getCveTpofiscaliza() {
		return this.cveTpofiscaliza;
	}

	public void setCveTpofiscaliza(BigDecimal cveTpofiscaliza) {
		this.cveTpofiscaliza = cveTpofiscaliza;
	}

	public String getDescStatus() {
		return this.descStatus;
	}

	public void setDescStatus(String descStatus) {
		this.descStatus = descStatus;
	}

	public List<FdtAviso> getFdtAvisos() {
		return this.fdtAvisos;
	}

	public void setFdtAvisos(List<FdtAviso> fdtAvisos) {
		this.fdtAvisos = fdtAvisos;
	}
	
}