package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDT_CEDRAZ_DICTAMEN database table.
 * 
 */
@Entity
@Table(name="FDT_CEDRAZ_DICTAMEN")
public class FdtCedrazDictamen implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtCedrazDictamenPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_EDICIONCEDULA")
	private Date fhEdicioncedula;

	//bi-directional many-to-one association to FdtPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DICTAMEN", nullable=false, insertable=false, updatable=false)
	private FdtPeriodo fdtPeriodo;

	//bi-directional many-to-one association to FdtCedrazRegpat
	@OneToMany(mappedBy="fdtCedrazDictamen")
	private List<FdtCedrazRegpat> fdtCedrazRegpats;

    public FdtCedrazDictamen() {
    }

	public FdtCedrazDictamenPK getId() {
		return this.id;
	}

	public void setId(FdtCedrazDictamenPK id) {
		this.id = id;
	}
	
	public Date getFhEdicioncedula() {
		return this.fhEdicioncedula;
	}

	public void setFhEdicioncedula(Date fhEdicioncedula) {
		this.fhEdicioncedula = fhEdicioncedula;
	}

	public FdtPeriodo getFdtPeriodo() {
		return this.fdtPeriodo;
	}

	public void setFdtPeriodo(FdtPeriodo fdtPeriodo) {
		this.fdtPeriodo = fdtPeriodo;
	}
	
	public List<FdtCedrazRegpat> getFdtCedrazRegpats() {
		return this.fdtCedrazRegpats;
	}

	public void setFdtCedrazRegpats(List<FdtCedrazRegpat> fdtCedrazRegpats) {
		this.fdtCedrazRegpats = fdtCedrazRegpats;
	}
	
}