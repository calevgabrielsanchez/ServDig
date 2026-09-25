package mx.gob.imss.ctirss.correccion.catalogos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Entity
@Table(name="CRC_ESTATUS_DETECCION")
@OnSearchLlavePrimaria			(atributos={"cveEstatusDeteccion"})
@ComponentComboCampoDescripcion (atributo="descEstatusSeleccion")
public class CrcEstatusDeteccion extends AbstractModel{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ESTATUS")
	private long cveEstatusDeteccion;

	@Column(name="DESC_ESTATUS")
	private String descCriterioseleccion;

	public long getCveEstatusDeteccion() {
		return cveEstatusDeteccion;
	}

	public void setCveEstatusDeteccion(long cveEstatusDeteccion) {
		this.cveEstatusDeteccion = cveEstatusDeteccion;
	}

	public String getDescCriterioseleccion() {
		return descCriterioseleccion;
	}

	public void setDescCriterioseleccion(String descCriterioseleccion) {
		this.descCriterioseleccion = descCriterioseleccion;
	}
	
	

}
