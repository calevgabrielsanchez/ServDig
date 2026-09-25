package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the DG_CAT_AMBITO database table.
 * 
 */
@Entity
@Table(name="DG_CAT_AMBITO")
public class DgCatAmbito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false, precision=1)
	private long ambito;

	@Column(nullable=false, length=30)
	private String nombre;

	//bi-directional many-to-one association to DgCatLocalidad
	@OneToMany(mappedBy="dgCatAmbito")
	private List<DgCatLocalidad> dgCatLocalidads;

	//bi-directional many-to-one association to DgVialidad
	@OneToMany(mappedBy="dgCatAmbito")
	private List<DgVialidad> dgVialidads;

    public DgCatAmbito() {
    }

	public long getAmbito() {
		return this.ambito;
	}

	public void setAmbito(long ambito) {
		this.ambito = ambito;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DgCatLocalidad> getDgCatLocalidads() {
		return this.dgCatLocalidads;
	}

	public void setDgCatLocalidads(List<DgCatLocalidad> dgCatLocalidads) {
		this.dgCatLocalidads = dgCatLocalidads;
	}
	
	public List<DgVialidad> getDgVialidads() {
		return this.dgVialidads;
	}

	public void setDgVialidads(List<DgVialidad> dgVialidads) {
		this.dgVialidads = dgVialidads;
	}
	
}