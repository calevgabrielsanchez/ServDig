package mx.gob.imss.ctirss.correccion.base.model;


import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CgtCorreccion;
import mx.gob.imss.ctirss.correccion.model.CgtPromocion;

import java.util.Set;


/**
 * The persistent class for the CGT_CATCRITERIOSELECCION database table.
 * 
 */
@MappedSuperclass

public abstract class AbstractCgtCatCriterioSeleccion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_CRITERIOSELECCION")
	public long idCriterioseleccion;

	@Column(name="DESC_CRITERIOSELECCION")
	private String descCriterioseleccion;

	//bi-directional many-to-one association to AbstractCgcCatorigen
    @ManyToOne
	@JoinColumn(name="ID_ORIGEN")
	private CgcCatOrigen cgcCatOrigen;
    
	//bi-directional many-to-one association to AbstractCgcCattipo
    @ManyToOne
	@JoinColumn(name="ID_TIPO")
	private CgcCatTipo cgcCatTipo;
    /*
	//bi-directional many-to-one association to AbstractCgtCorreccion
	@OneToMany(mappedBy="cgtCatcriterioSeleccion")
	private Set<CgtCorreccion> cgtCorreccions;

	//bi-directional many-to-one association to AbstractCgtPromocion
	@OneToMany(mappedBy="cgtCatCriterioSeleccion")
	private Set<CgtPromocion> cgtPromocions;
*/
    
    
    
    public AbstractCgtCatCriterioSeleccion() {
    }

	public CgcCatOrigen getCgcCatOrigen() {
		return cgcCatOrigen;
	}

	public void setCgcCatOrigen(CgcCatOrigen cgcCatOrigen) {
		this.cgcCatOrigen = cgcCatOrigen;
	}

	public CgcCatTipo getCgcCatTipo() {
		return cgcCatTipo;
	}

	public void setCgcCatTipo(CgcCatTipo cgcCatTipo) {
		this.cgcCatTipo = cgcCatTipo;
	}

	public long getIdCriterioseleccion() {
		return idCriterioseleccion;
	}

	public void setIdCriterioseleccion(long idCriterioseleccion) {
		this.idCriterioseleccion = idCriterioseleccion;
	}

	public String getDescCriterioseleccion() {
		return descCriterioseleccion;
	}

	public void setDescCriterioseleccion(String descCriterioseleccion) {
		this.descCriterioseleccion = descCriterioseleccion;
	}

	/*public CgcCatOrigen getCgcCatorigen() {
		return cgcCatorigen;
	}

	public void setCgcCatorigen(CgcCatOrigen cgcCatorigen) {
		this.cgcCatorigen = cgcCatorigen;
	}

	public CgcCatTipo getCgcCattipo() {
		return cgcCattipo;
	}

	public void setCgcCattipo(CgcCatTipo cgcCattipo) {
		this.cgcCattipo = cgcCattipo;
	}

	public Set<CgtCorreccion> getCgtCorreccions() {
		return cgtCorreccions;
	}

	public void setCgtCorreccions(Set<CgtCorreccion> cgtCorreccions) {
		this.cgtCorreccions = cgtCorreccions;
	}

	public Set<CgtPromocion> getCgtPromocions() {
		return cgtPromocions;
	}

	public void setCgtPromocions(Set<CgtPromocion> cgtPromocions) {
		this.cgtPromocions = cgtPromocions;
	}*/
	
}