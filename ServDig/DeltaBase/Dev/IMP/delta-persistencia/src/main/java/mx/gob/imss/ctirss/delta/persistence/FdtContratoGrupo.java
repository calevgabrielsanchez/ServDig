package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the FDT_CONTRATO_GRUPO database table.
 * 
 */
@Entity
@Table(name="FDT_CONTRATO_GRUPO")
public class FdtContratoGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtContratoGrupoPK id;

	//bi-directional many-to-many association to FdtRegistroPeriodo
//    @ManyToMany
//	@JoinTable(
//		name="FDT_CONTRATO_REGISTRO"
//		, joinColumns={
//			@JoinColumn(name="CV_CONTRATO", referencedColumnName="CV_CONTRATO", nullable=false),
//			@JoinColumn(name="CV_GRUPO", referencedColumnName="CV_GRUPO", nullable=false),
//			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false)
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false),
//			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false),
//			@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false)
//			}
//		)
//	private List<FdtRegistroPeriodo> fdtRegistroPeriodos;

    public FdtContratoGrupo() {
    }

	public FdtContratoGrupoPK getId() {
		return this.id;
	}

	public void setId(FdtContratoGrupoPK id) {
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