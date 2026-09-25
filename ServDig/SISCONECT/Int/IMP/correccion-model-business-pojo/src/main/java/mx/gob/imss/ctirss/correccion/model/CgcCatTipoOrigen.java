package mx.gob.imss.ctirss.correccion.model;


import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcCatTipoOrigen;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;




/**
 * The persistent class for the CGC_CATTIPOORIGEN database table.
 * 
 */
@Entity
@Table(name="CGC_CATTIPOORIGEN")
@OnSearchLlavePrimaria			(atributos={"idTipoorigen"})
@ComponentComboCampoDescripcion (atributo="descripcion")
public class CgcCatTipoOrigen extends AbstractCgcCatTipoOrigen {

	@Transient
	private Long idTipo;
	@Transient
	private Long idOrigen;
	
	@Transient
	public Long getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}
	@Transient
	public Long getIdOrigen() {
		return idOrigen;
	}
	public void setIdOrigen(Long idOrigen) {
		this.idOrigen = idOrigen;
	}
	
	
	

	
}