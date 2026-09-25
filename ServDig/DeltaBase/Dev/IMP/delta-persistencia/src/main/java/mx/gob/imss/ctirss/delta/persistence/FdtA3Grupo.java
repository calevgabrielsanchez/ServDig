package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the FDT_A3_GRUPO database table.
 * 
 */
@Entity
@Table(name="FDT_A3_GRUPO")
public class FdtA3Grupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA3GrupoPK id;

	//bi-directional many-to-many association to FdtPatronHi
	@ManyToMany(mappedBy="fdtA3Grupos")
	private List<FdtPatronHi> fdtPatronHis;

	//bi-directional many-to-many association to FdtA3Clausula
//	@ManyToMany(mappedBy="fdtA3Grupos")
//	private List<FdtA3Clausula> fdtA3Clausulas;

	//bi-directional many-to-one association to FdtA3GrupoFactore
	@OneToMany(mappedBy="fdtA3Grupo")
	private List<FdtA3GrupoFactore> fdtA3GrupoFactores;

	//bi-directional many-to-many association to FdtA1ContratoTrabajo
//	@ManyToMany(mappedBy="fdtA3Grupos")
//	private List<FdtA1ContratoTrabajo> fdtA1ContratoTrabajos;

    public FdtA3Grupo() {
    }

	public FdtA3GrupoPK getId() {
		return this.id;
	}

	public void setId(FdtA3GrupoPK id) {
		this.id = id;
	}
	
	public List<FdtPatronHi> getFdtPatronHis() {
		return this.fdtPatronHis;
	}

	public void setFdtPatronHis(List<FdtPatronHi> fdtPatronHis) {
		this.fdtPatronHis = fdtPatronHis;
	}
	
//	public List<FdtA3Clausula> getFdtA3Clausulas() {
//		return this.fdtA3Clausulas;
//	}
//
//	public void setFdtA3Clausulas(List<FdtA3Clausula> fdtA3Clausulas) {
//		this.fdtA3Clausulas = fdtA3Clausulas;
//	}
	
	public List<FdtA3GrupoFactore> getFdtA3GrupoFactores() {
		return this.fdtA3GrupoFactores;
	}

	public void setFdtA3GrupoFactores(List<FdtA3GrupoFactore> fdtA3GrupoFactores) {
		this.fdtA3GrupoFactores = fdtA3GrupoFactores;
	}
	
//	public List<FdtA1ContratoTrabajo> getFdtA1ContratoTrabajos() {
//		return this.fdtA1ContratoTrabajos;
//	}
//
//	public void setFdtA1ContratoTrabajos(List<FdtA1ContratoTrabajo> fdtA1ContratoTrabajos) {
//		this.fdtA1ContratoTrabajos = fdtA1ContratoTrabajos;
//	}
	
}