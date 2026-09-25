package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the FDT_A3_GRUPO_CLAUSULA database table.
 * 
 */
@Entity
@Table(name="FDT_A3_GRUPO_CLAUSULA")
public class FdtA3GrupoClausula implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA3GrupoClausulaPK id;

	//bi-directional many-to-many association to FdtRegistroPeriodo
//    @ManyToMany
//	@JoinTable(
//		name="FDT_REGISTRO_CLAUSULA"
//		, joinColumns={
//			@JoinColumn(name="CV_GRUPO", referencedColumnName="CV_GRUPO", nullable=false),
//			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false),
//			@JoinColumn(name="NU_CLAUSULA", referencedColumnName="NU_CLAUSULA", nullable=false)
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false),
//			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false),
//			@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false)
//			}
//		)
//	private List<FdtRegistroPeriodo> fdtRegistroPeriodos;

    public FdtA3GrupoClausula() {
    }

	public FdtA3GrupoClausulaPK getId() {
		return this.id;
	}

	public void setId(FdtA3GrupoClausulaPK id) {
		this.id = id;
	}
	
//	public List<FdtRegistroPeriodo> getFdtRegistroPeriodos() {
//		return this.fdtRegistroPeriodos;
//	}
//
//	public void setFdtRegistroPeriodos(List<FdtRegistroPeriodo> fdtRegistroPeriodos) {
//		this.fdtRegistroPeriodos = fdtRegistroPeriodos;
//	}
	
}