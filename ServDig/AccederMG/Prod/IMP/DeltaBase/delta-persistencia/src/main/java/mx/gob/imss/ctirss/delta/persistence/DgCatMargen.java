package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.List;


/**
 * The persistent class for the DG_CAT_MARGEN database table.
 * 
 */
@Entity
@Table(name="DG_CAT_MARGEN")
@OnSearchLlavePrimaria(atributos={"cveMargen"})
@ComponentComboCampoDescripcion(atributo="descripcion")
public class DgCatMargen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_MARGEN", nullable=false, precision=2)
	private long cveMargen;

	@Column(nullable=false, length=50)
	private String descripcion;

	//bi-directional many-to-one association to DgDomiciliosCamino
	@OneToMany(mappedBy="dgCatMargen")
	private List<DgDomiciliosCamino> dgDomiciliosCaminos;

    public DgCatMargen() {
    }

	public long getCveMargen() {
		return this.cveMargen;
	}

	public void setCveMargen(long cveMargen) {
		this.cveMargen = cveMargen;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<DgDomiciliosCamino> getDgDomiciliosCaminos() {
		return this.dgDomiciliosCaminos;
	}

	public void setDgDomiciliosCaminos(List<DgDomiciliosCamino> dgDomiciliosCaminos) {
		this.dgDomiciliosCaminos = dgDomiciliosCaminos;
	}
	
}