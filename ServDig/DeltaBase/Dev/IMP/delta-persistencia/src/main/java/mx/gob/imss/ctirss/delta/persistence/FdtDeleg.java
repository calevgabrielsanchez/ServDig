package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDT_DELEG database table.
 * 
 */
@Entity
@Table(name="FDT_DELEG")
public class FdtDeleg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_DELEG_ORIG", nullable=false, precision=2)
	private long cveDelegOrig;

	@Column(name="DELEG_DESC", nullable=false, length=40)
	private String delegDesc;

	@Column(name="TX_DOMICILIO", length=120)
	private String txDomicilio;

	@Column(name="TX_NOMBRE_TIT", length=80)
	private String txNombreTit;

	//bi-directional many-to-one association to FiEntFed
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ENT_FED", nullable=false)
	private FiEntFed fiEntFed;

	//bi-directional many-to-one association to FdtPeriodo
	@OneToMany(mappedBy="fdtDeleg")
	private List<FdtPeriodo> fdtPeriodos;

	//bi-directional many-to-one association to FdtSubdeleg
	@OneToMany(mappedBy="fdtDeleg")
	private List<FdtSubdeleg> fdtSubdelegs;

    public FdtDeleg() {
    }

	public long getCveDelegOrig() {
		return this.cveDelegOrig;
	}

	public void setCveDelegOrig(long cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}

	public String getDelegDesc() {
		return this.delegDesc;
	}

	public void setDelegDesc(String delegDesc) {
		this.delegDesc = delegDesc;
	}

	public String getTxDomicilio() {
		return this.txDomicilio;
	}

	public void setTxDomicilio(String txDomicilio) {
		this.txDomicilio = txDomicilio;
	}

	public String getTxNombreTit() {
		return this.txNombreTit;
	}

	public void setTxNombreTit(String txNombreTit) {
		this.txNombreTit = txNombreTit;
	}

	public FiEntFed getFiEntFed() {
		return this.fiEntFed;
	}

	public void setFiEntFed(FiEntFed fiEntFed) {
		this.fiEntFed = fiEntFed;
	}
	
	public List<FdtPeriodo> getFdtPeriodos() {
		return this.fdtPeriodos;
	}

	public void setFdtPeriodos(List<FdtPeriodo> fdtPeriodos) {
		this.fdtPeriodos = fdtPeriodos;
	}
	
	public List<FdtSubdeleg> getFdtSubdelegs() {
		return this.fdtSubdelegs;
	}

	public void setFdtSubdelegs(List<FdtSubdeleg> fdtSubdelegs) {
		this.fdtSubdelegs = fdtSubdelegs;
	}
	
}