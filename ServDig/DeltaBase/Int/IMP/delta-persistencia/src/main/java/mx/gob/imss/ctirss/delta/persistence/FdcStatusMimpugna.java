package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FDC_STATUS_MIMPUGNA database table.
 * 
 */
@Entity
@Table(name="FDC_STATUS_MIMPUGNA")
public class FdcStatusMimpugna implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="STATUS_MEDIOIMP", nullable=false, precision=22)
	private long statusMedioimp;

	@Column(name="DESC_STATUS_MI", length=50)
	private String descStatusMi;

	@Column(name="ID_TPO_STATUS", precision=22)
	private BigDecimal idTpoStatus;

	//bi-directional many-to-one association to FdtImpugnacion
	@OneToMany(mappedBy="fdcStatusMimpugna")
	private List<FdtImpugnacion> fdtImpugnacions;

    public FdcStatusMimpugna() {
    }

	public long getStatusMedioimp() {
		return this.statusMedioimp;
	}

	public void setStatusMedioimp(long statusMedioimp) {
		this.statusMedioimp = statusMedioimp;
	}

	public String getDescStatusMi() {
		return this.descStatusMi;
	}

	public void setDescStatusMi(String descStatusMi) {
		this.descStatusMi = descStatusMi;
	}

	public BigDecimal getIdTpoStatus() {
		return this.idTpoStatus;
	}

	public void setIdTpoStatus(BigDecimal idTpoStatus) {
		this.idTpoStatus = idTpoStatus;
	}

	public List<FdtImpugnacion> getFdtImpugnacions() {
		return this.fdtImpugnacions;
	}

	public void setFdtImpugnacions(List<FdtImpugnacion> fdtImpugnacions) {
		this.fdtImpugnacions = fdtImpugnacions;
	}
	
}