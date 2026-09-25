package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FDT_A5_MAQUINARIA database table.
 * 
 */
@Entity
@Table(name="FDT_A5_MAQUINARIA")
public class FdtA5Maquinaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA5MaquinariaPK id;

	@Column(name="ID_MAQUINARIAOTRANSP", length=18)
	private String idMaquinariaotransp;

	@Column(name="TX_CAPACIDAD", nullable=false, length=30)
	private String txCapacidad;

	@Column(name="TX_COMBUSTIBLE", nullable=false, length=30)
	private String txCombustible;

	@Column(name="TX_NOMBRE", nullable=false, length=50)
	private String txNombre;

	@Column(name="TX_NUM_UNIDAD", nullable=false, length=21)
	private String txNumUnidad;

	@Column(name="TX_USO", nullable=false, length=50)
	private String txUso;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtA5Maquinaria() {
    }

	public FdtA5MaquinariaPK getId() {
		return this.id;
	}

	public void setId(FdtA5MaquinariaPK id) {
		this.id = id;
	}
	
	public String getIdMaquinariaotransp() {
		return this.idMaquinariaotransp;
	}

	public void setIdMaquinariaotransp(String idMaquinariaotransp) {
		this.idMaquinariaotransp = idMaquinariaotransp;
	}

	public String getTxCapacidad() {
		return this.txCapacidad;
	}

	public void setTxCapacidad(String txCapacidad) {
		this.txCapacidad = txCapacidad;
	}

	public String getTxCombustible() {
		return this.txCombustible;
	}

	public void setTxCombustible(String txCombustible) {
		this.txCombustible = txCombustible;
	}

	public String getTxNombre() {
		return this.txNombre;
	}

	public void setTxNombre(String txNombre) {
		this.txNombre = txNombre;
	}

	public String getTxNumUnidad() {
		return this.txNumUnidad;
	}

	public void setTxNumUnidad(String txNumUnidad) {
		this.txNumUnidad = txNumUnidad;
	}

	public String getTxUso() {
		return this.txUso;
	}

	public void setTxUso(String txUso) {
		this.txUso = txUso;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}