package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the CFC_TPOFISCALIZA database table.
 * 
 */
@Entity
@Table(name="CFC_TPOFISCALIZA")
public class CfcTpofiscaliza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_TPOFISCALIZA", nullable=false, precision=22)
	private long cveTpofiscaliza;

	@Column(name="DESC_ORIGEN", length=30)
	private String descOrigen;

	@Column(name="PERIODOS_REQ", precision=22)
	private BigDecimal periodosReq;

	//bi-directional many-to-one association to CfcFiscaliza
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_FISCALIZA", nullable=false)
	private CfcFiscaliza cfcFiscaliza;

	//bi-directional many-to-one association to CftIntegrafiscaliza
	@OneToMany(mappedBy="cfcTpofiscaliza")
	private List<CftIntegrafiscaliza> cftIntegrafiscalizas;

    public CfcTpofiscaliza() {
    }

	public long getCveTpofiscaliza() {
		return this.cveTpofiscaliza;
	}

	public void setCveTpofiscaliza(long cveTpofiscaliza) {
		this.cveTpofiscaliza = cveTpofiscaliza;
	}

	public String getDescOrigen() {
		return this.descOrigen;
	}

	public void setDescOrigen(String descOrigen) {
		this.descOrigen = descOrigen;
	}

	public BigDecimal getPeriodosReq() {
		return this.periodosReq;
	}

	public void setPeriodosReq(BigDecimal periodosReq) {
		this.periodosReq = periodosReq;
	}

	public CfcFiscaliza getCfcFiscaliza() {
		return this.cfcFiscaliza;
	}

	public void setCfcFiscaliza(CfcFiscaliza cfcFiscaliza) {
		this.cfcFiscaliza = cfcFiscaliza;
	}
	
	public List<CftIntegrafiscaliza> getCftIntegrafiscalizas() {
		return this.cftIntegrafiscalizas;
	}

	public void setCftIntegrafiscalizas(List<CftIntegrafiscaliza> cftIntegrafiscalizas) {
		this.cftIntegrafiscalizas = cftIntegrafiscalizas;
	}
	
}