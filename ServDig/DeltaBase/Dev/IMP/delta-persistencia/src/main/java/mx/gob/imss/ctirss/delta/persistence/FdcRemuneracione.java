package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDC_REMUNERACIONES database table.
 * 
 */
@Entity
@Table(name="FDC_REMUNERACIONES")
public class FdcRemuneracione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_REMUNERACION", nullable=false, precision=22)
	private long idRemuneracion;

	@Column(name="TX_REMUNERACION", length=50)
	private String txRemuneracion;

	//bi-directional many-to-many association to FdtPeriodo
    @ManyToMany
	@JoinTable(
		name="FDT_A4_CATREMUNERA"
		, joinColumns={
			@JoinColumn(name="ID_REMUNERACION", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_DICTAMEN", nullable=false)
			}
		)
	private List<FdtPeriodo> fdtPeriodos;

	//bi-directional many-to-many association to FdtRegistroPeriodo
    @ManyToMany
	@JoinTable(
		name="FDT_A4_REMUNERA"
		, joinColumns={
			@JoinColumn(name="ID_REMUNERACION", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false),
			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false),
			@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false)
			}
		)
	private List<FdtRegistroPeriodo> fdtRegistroPeriodos;

	//bi-directional many-to-one association to FdtA4Remuneracion
	@OneToMany(mappedBy="fdcRemuneracione")
	private List<FdtA4Remuneracion> fdtA4Remuneracions;

    public FdcRemuneracione() {
    }

	public long getIdRemuneracion() {
		return this.idRemuneracion;
	}

	public void setIdRemuneracion(long idRemuneracion) {
		this.idRemuneracion = idRemuneracion;
	}

	public String getTxRemuneracion() {
		return this.txRemuneracion;
	}

	public void setTxRemuneracion(String txRemuneracion) {
		this.txRemuneracion = txRemuneracion;
	}

	public List<FdtPeriodo> getFdtPeriodos() {
		return this.fdtPeriodos;
	}

	public void setFdtPeriodos(List<FdtPeriodo> fdtPeriodos) {
		this.fdtPeriodos = fdtPeriodos;
	}
	
	public List<FdtRegistroPeriodo> getFdtRegistroPeriodos() {
		return this.fdtRegistroPeriodos;
	}

	public void setFdtRegistroPeriodos(List<FdtRegistroPeriodo> fdtRegistroPeriodos) {
		this.fdtRegistroPeriodos = fdtRegistroPeriodos;
	}
	
	public List<FdtA4Remuneracion> getFdtA4Remuneracions() {
		return this.fdtA4Remuneracions;
	}

	public void setFdtA4Remuneracions(List<FdtA4Remuneracion> fdtA4Remuneracions) {
		this.fdtA4Remuneracions = fdtA4Remuneracions;
	}
	
}