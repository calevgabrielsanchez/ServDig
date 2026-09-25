package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CGT_CATCRITERIOSELECCION database table.
 * 
 */
@MappedSuperclass
public class AbstractCgtCatcriterioseleccion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_CRITERIOSELECCION")
	private long idCriterioseleccion;

	@Column(name="DESC_CRITERIOSELECCION")
	private String descCriterioseleccion;

	@Column(name="ID_ORIGEN")
	private Integer idOrigen;

	@Column(name="ID_TIPO")
	private Integer idTipo;
    
    public AbstractCgtCatcriterioseleccion() {
    }

	public long getIdCriterioseleccion() {
		return this.idCriterioseleccion;
	}

	public void setIdCriterioseleccion(long idCriterioseleccion) {
		this.idCriterioseleccion = idCriterioseleccion;
	}

	public String getDescCriterioseleccion() {
		return this.descCriterioseleccion;
	}

	public void setDescCriterioseleccion(String descCriterioseleccion) {
		this.descCriterioseleccion = descCriterioseleccion;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}	
	
}