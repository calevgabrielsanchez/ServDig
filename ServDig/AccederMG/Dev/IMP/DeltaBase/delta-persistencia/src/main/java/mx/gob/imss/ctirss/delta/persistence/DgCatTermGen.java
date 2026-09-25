package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.List;


/**
 * The persistent class for the DG_CAT_TERM_GEN database table.
 * 
 */
@Entity
@Table(name="DG_CAT_TERM_GEN")
@OnSearchLlavePrimaria(atributos={"cveTer"})
@ComponentComboCampoDescripcion(atributo="nombre")
public class DgCatTermGen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_TER", nullable=false, precision=2)
	private long cveTer;

	@Column(nullable=false, length=45)
	private String nombre;

	//bi-directional many-to-one association to DgDomiciliosCamino
	@OneToMany(mappedBy="dgCatTermGen")
	private List<DgDomiciliosCamino> dgDomiciliosCaminos;
	
	
    public DgCatTermGen() {
    }

	public long getCveTer() {
		return this.cveTer;
	}

	public void setCveTer(long cveTer) {
		this.cveTer = cveTer;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DgDomiciliosCamino> getDgDomiciliosCaminos() {
		return this.dgDomiciliosCaminos;
	}

	public void setDgDomiciliosCaminos(List<DgDomiciliosCamino> dgDomiciliosCaminos) {
		this.dgDomiciliosCaminos = dgDomiciliosCaminos;
	}
	
}