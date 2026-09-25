package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcCatTipo;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the CGC_CATTIPO database table.
 * 
 */
@Entity
@Table							(name="CGC_CATTIPO")
@OnSearchLlavePrimaria			(atributos={"idTipo"})
@ComponentComboCampoDescripcion (atributo="descripcion")
public class CgcCatTipo extends AbstractCgcCatTipo {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private String cboSeccionDesc;
	
	@Transient
	public String getCboSeccionDesc() {
		return cboSeccionDesc;
	}

	@Transient
	public void setCboSeccionDesc(String cboSeccionDesc) {
		this.cboSeccionDesc = cboSeccionDesc;
	}
	
	

}