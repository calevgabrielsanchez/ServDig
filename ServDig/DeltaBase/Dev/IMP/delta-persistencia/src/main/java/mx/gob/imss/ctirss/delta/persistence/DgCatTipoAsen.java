package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the DG_CAT_TIPO_ASEN database table.
 * 
 */
@Entity
@Table(name="DG_CAT_TIPO_ASEN")
public class DgCatTipoAsen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_TIPO_ASEN", nullable=false, precision=2)
	private long cveTipoAsen;

	@Column(nullable=false, length=21)
	private String nombre;

	//bi-directional many-to-one association to DgAsentamiento
	@OneToMany(mappedBy="dgCatTipoAsen")
	private List<DgAsentamiento> dgAsentamientos;

    public DgCatTipoAsen() {
    }

	public long getCveTipoAsen() {
		return this.cveTipoAsen;
	}

	public void setCveTipoAsen(long cveTipoAsen) {
		this.cveTipoAsen = cveTipoAsen;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DgAsentamiento> getDgAsentamientos() {
		return this.dgAsentamientos;
	}

	public void setDgAsentamientos(List<DgAsentamiento> dgAsentamientos) {
		this.dgAsentamientos = dgAsentamientos;
	}
	
}