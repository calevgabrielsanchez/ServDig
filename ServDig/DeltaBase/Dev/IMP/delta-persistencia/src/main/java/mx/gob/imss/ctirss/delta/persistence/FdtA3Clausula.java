package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the FDT_A3_CLAUSULA database table.
 * 
 */
@Entity
@Table(name="FDT_A3_CLAUSULA")
public class FdtA3Clausula implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA3ClausulaPK id;

	@Column(name="IN_SALARIO_B_C", nullable=false, length=1)
	private String inSalarioBC;

	@Column(name="IN_TP_PERCEPCION", nullable=false, length=1)
	private String inTpPercepcion;

	@Column(name="TX_CLAUSULA_EN_CCT", nullable=false, length=30)
	private String txClausulaEnCct;

	@Column(name="TX_CONCEPTOS_PERCEP", nullable=false, length=50)
	private String txConceptosPercep;

	//bi-directional many-to-one association to FdtPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DICTAMEN", nullable=false, insertable=false, updatable=false)
	private FdtPeriodo fdtPeriodo;

	//bi-directional many-to-one association to FdtA4Catremunera
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", insertable=false, updatable=false),
		@JoinColumn(name="ID_REMUNERACION", referencedColumnName="ID_REMUNERACION", insertable=false, updatable=false)
		})
	private FdtA4Catremunera fdtA4Catremunera;

	//bi-directional many-to-many association to FdtA3Grupo
//    @ManyToMany
//	@JoinTable(
//		name="FDT_A3_GRUPO_CLAUSULA"
//		, joinColumns={
//			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
//			@JoinColumn(name="NU_CLAUSULA", referencedColumnName="NU_CLAUSULA", nullable=false, insertable=false, updatable=false)
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="CV_GRUPO", referencedColumnName="CV_GRUPO", nullable=false, insertable=false, updatable=false),
//			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false)
//			}
//		)
//	private List<FdtA3Grupo> fdtA3Grupos;

    public FdtA3Clausula() {
    }

	public FdtA3ClausulaPK getId() {
		return this.id;
	}

	public void setId(FdtA3ClausulaPK id) {
		this.id = id;
	}
	
	public String getInSalarioBC() {
		return this.inSalarioBC;
	}

	public void setInSalarioBC(String inSalarioBC) {
		this.inSalarioBC = inSalarioBC;
	}

	public String getInTpPercepcion() {
		return this.inTpPercepcion;
	}

	public void setInTpPercepcion(String inTpPercepcion) {
		this.inTpPercepcion = inTpPercepcion;
	}

	public String getTxClausulaEnCct() {
		return this.txClausulaEnCct;
	}

	public void setTxClausulaEnCct(String txClausulaEnCct) {
		this.txClausulaEnCct = txClausulaEnCct;
	}

	public String getTxConceptosPercep() {
		return this.txConceptosPercep;
	}

	public void setTxConceptosPercep(String txConceptosPercep) {
		this.txConceptosPercep = txConceptosPercep;
	}

	public FdtPeriodo getFdtPeriodo() {
		return this.fdtPeriodo;
	}

	public void setFdtPeriodo(FdtPeriodo fdtPeriodo) {
		this.fdtPeriodo = fdtPeriodo;
	}
	
	public FdtA4Catremunera getFdtA4Catremunera() {
		return this.fdtA4Catremunera;
	}

	public void setFdtA4Catremunera(FdtA4Catremunera fdtA4Catremunera) {
		this.fdtA4Catremunera = fdtA4Catremunera;
	}
	
//	public List<FdtA3Grupo> getFdtA3Grupos() {
//		return this.fdtA3Grupos;
//	}
//
//	public void setFdtA3Grupos(List<FdtA3Grupo> fdtA3Grupos) {
//		this.fdtA3Grupos = fdtA3Grupos;
//	}
	
}