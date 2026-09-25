package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the FDT_A4_CATREMUNERA database table.
 * 
 */
@Entity
@Table(name="FDT_A4_CATREMUNERA")
public class FdtA4Catremunera implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA4CatremuneraPK id;

	//bi-directional many-to-one association to FdtA3Clausula
	@OneToMany(mappedBy="fdtA4Catremunera")
	private List<FdtA3Clausula> fdtA3Clausulas;

    public FdtA4Catremunera() {
    }

	public FdtA4CatremuneraPK getId() {
		return this.id;
	}

	public void setId(FdtA4CatremuneraPK id) {
		this.id = id;
	}
	
	public List<FdtA3Clausula> getFdtA3Clausulas() {
		return this.fdtA3Clausulas;
	}

	public void setFdtA3Clausulas(List<FdtA3Clausula> fdtA3Clausulas) {
		this.fdtA3Clausulas = fdtA3Clausulas;
	}
	
}