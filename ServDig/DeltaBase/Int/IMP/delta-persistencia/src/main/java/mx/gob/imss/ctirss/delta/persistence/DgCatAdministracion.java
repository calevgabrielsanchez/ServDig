package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.List;


/**
 * The persistent class for the DG_CAT_ADMINISTRACION database table.
 * 
 */
@Entity
@Table(name="DG_CAT_ADMINISTRACION")
@OnSearchLlavePrimaria(atributos={"cveCac"})
@ComponentComboCampoDescripcion(atributo="nombre")
public class DgCatAdministracion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_CAC", nullable=false, precision=1)
	private long cveCac;

	@Column(nullable=false, length=20)
	private String nombre;

	//bi-directional many-to-one association to DgDomiciliosCarretera
	@OneToMany(mappedBy="dgCatAdministracion")
	private List<DgDomiciliosCarretera> dgDomiciliosCarreteras;

    public DgCatAdministracion() {
    }

	public long getCveCac() {
		return this.cveCac;
	}

	public void setCveCac(long cveCac) {
		this.cveCac = cveCac;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DgDomiciliosCarretera> getDgDomiciliosCarreteras() {
		return this.dgDomiciliosCarreteras;
	}

	public void setDgDomiciliosCarreteras(List<DgDomiciliosCarretera> dgDomiciliosCarreteras) {
		this.dgDomiciliosCarreteras = dgDomiciliosCarreteras;
	}
	
}