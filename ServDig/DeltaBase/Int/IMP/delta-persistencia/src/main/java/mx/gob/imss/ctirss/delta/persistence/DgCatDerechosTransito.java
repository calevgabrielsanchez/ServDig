package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.List;


/**
 * The persistent class for the DG_CAT_DERECHOS_TRANSITO database table.
 * 
 */
@Entity
@Table(name="DG_CAT_DERECHOS_TRANSITO")
@OnSearchLlavePrimaria(atributos={"cveCdt"})
@ComponentComboCampoDescripcion(atributo="nombre")
public class DgCatDerechosTransito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_CDT", nullable=false, precision=2)
	private long cveCdt;

	@Column(nullable=false, length=13)
	private String nombre;

	//bi-directional many-to-one association to DgDomiciliosCarretera
	@OneToMany(mappedBy="dgCatDerechosTransito")
	private List<DgDomiciliosCarretera> dgDomiciliosCarreteras;

    public DgCatDerechosTransito() {
    }

	public long getCveCdt() {
		return this.cveCdt;
	}

	public void setCveCdt(long cveCdt) {
		this.cveCdt = cveCdt;
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