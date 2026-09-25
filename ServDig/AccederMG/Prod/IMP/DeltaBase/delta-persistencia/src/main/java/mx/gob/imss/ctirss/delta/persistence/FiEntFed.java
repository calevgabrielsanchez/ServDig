package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FI_ENT_FED database table.
 * 
 */
@Entity
@Table(name="FI_ENT_FED")
public class FiEntFed implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ENT_FED", nullable=false, precision=2)
	private long entFed;

	@Column(name="ENT_FED_DESC", nullable=false, length=25)
	private String entFedDesc;

	//bi-directional many-to-one association to FdtDeleg
	@OneToMany(mappedBy="fiEntFed")
	private List<FdtDeleg> fdtDelegs;

	//bi-directional many-to-one association to FiMunicipio
	@OneToMany(mappedBy="fiEntFed")
	private List<FiMunicipio> fiMunicipios;

	//bi-directional many-to-one association to FiMunicipiosImssInegi
	@OneToMany(mappedBy="fiEntFed")
	private List<FiMunicipiosImssInegi> fiMunicipiosImssInegis;

    public FiEntFed() {
    }

	public long getEntFed() {
		return this.entFed;
	}

	public void setEntFed(long entFed) {
		this.entFed = entFed;
	}

	public String getEntFedDesc() {
		return this.entFedDesc;
	}

	public void setEntFedDesc(String entFedDesc) {
		this.entFedDesc = entFedDesc;
	}

	public List<FdtDeleg> getFdtDelegs() {
		return this.fdtDelegs;
	}

	public void setFdtDelegs(List<FdtDeleg> fdtDelegs) {
		this.fdtDelegs = fdtDelegs;
	}
	
	public List<FiMunicipio> getFiMunicipios() {
		return this.fiMunicipios;
	}

	public void setFiMunicipios(List<FiMunicipio> fiMunicipios) {
		this.fiMunicipios = fiMunicipios;
	}
	
	public List<FiMunicipiosImssInegi> getFiMunicipiosImssInegis() {
		return this.fiMunicipiosImssInegis;
	}

	public void setFiMunicipiosImssInegis(List<FiMunicipiosImssInegi> fiMunicipiosImssInegis) {
		this.fiMunicipiosImssInegis = fiMunicipiosImssInegis;
	}
	
}