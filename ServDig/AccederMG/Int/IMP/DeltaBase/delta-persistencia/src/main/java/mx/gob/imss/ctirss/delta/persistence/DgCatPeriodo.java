package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DG_CAT_PERIODO database table.
 * 
 */
@Entity
@Table(name="DG_CAT_PERIODO")
public class DgCatPeriodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_PERIODO", nullable=false, precision=2)
	private long cvePeriodo;

	@Column(nullable=false, length=50)
	private String descripcion;

    @Temporal( TemporalType.DATE)
	@Column(nullable=false)
	private Date fecha;

	//bi-directional many-to-one association to DgCatLocalidad
	@OneToMany(mappedBy="dgCatPeriodo")
	private List<DgCatLocalidad> dgCatLocalidads;

    public DgCatPeriodo() {
    }

	public long getCvePeriodo() {
		return this.cvePeriodo;
	}

	public void setCvePeriodo(long cvePeriodo) {
		this.cvePeriodo = cvePeriodo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<DgCatLocalidad> getDgCatLocalidads() {
		return this.dgCatLocalidads;
	}

	public void setDgCatLocalidads(List<DgCatLocalidad> dgCatLocalidads) {
		this.dgCatLocalidads = dgCatLocalidads;
	}
	
}