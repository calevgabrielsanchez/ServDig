package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the FDC_GRUPO database table.
 * 
 */
@Entity
@Table(name="FDC_GRUPO")
public class FdcGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdcGrupoPK id;

	@Column(name="NOM_GRUPO", length=200)
	private String nomGrupo;

	//bi-directional many-to-one association to FdcFraccion
	@OneToMany(mappedBy="fdcGrupo")
	private List<FdcFraccion> fdcFraccions;

	//bi-directional many-to-one association to FdcDivision
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_DIVISION", nullable=false, insertable=false, updatable=false)
	private FdcDivision fdcDivision;

    public FdcGrupo() {
    }

	public FdcGrupoPK getId() {
		return this.id;
	}

	public void setId(FdcGrupoPK id) {
		this.id = id;
	}
	
	public String getNomGrupo() {
		return this.nomGrupo;
	}

	public void setNomGrupo(String nomGrupo) {
		this.nomGrupo = nomGrupo;
	}

	public List<FdcFraccion> getFdcFraccions() {
		return this.fdcFraccions;
	}

	public void setFdcFraccions(List<FdcFraccion> fdcFraccions) {
		this.fdcFraccions = fdcFraccions;
	}
	
	public FdcDivision getFdcDivision() {
		return this.fdcDivision;
	}

	public void setFdcDivision(FdcDivision fdcDivision) {
		this.fdcDivision = fdcDivision;
	}
	
}