package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDC_DIVISION database table.
 * 
 */
@Entity
@Table(name="FDC_DIVISION")
public class FdcDivision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_DIVISION", nullable=false, precision=22)
	private long cveDivision;

	@Column(name="NOM_DIVISION", length=100)
	private String nomDivision;

	//bi-directional many-to-one association to FdcGrupo
	@OneToMany(mappedBy="fdcDivision")
	private List<FdcGrupo> fdcGrupos;

    public FdcDivision() {
    }

	public long getCveDivision() {
		return this.cveDivision;
	}

	public void setCveDivision(long cveDivision) {
		this.cveDivision = cveDivision;
	}

	public String getNomDivision() {
		return this.nomDivision;
	}

	public void setNomDivision(String nomDivision) {
		this.nomDivision = nomDivision;
	}

	public List<FdcGrupo> getFdcGrupos() {
		return this.fdcGrupos;
	}

	public void setFdcGrupos(List<FdcGrupo> fdcGrupos) {
		this.fdcGrupos = fdcGrupos;
	}
	
}