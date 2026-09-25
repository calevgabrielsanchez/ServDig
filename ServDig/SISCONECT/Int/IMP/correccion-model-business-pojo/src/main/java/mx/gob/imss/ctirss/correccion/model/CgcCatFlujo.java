package mx.gob.imss.ctirss.correccion.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcCatFlujo;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

import java.util.Set;


/**
 * The persistent class for the CGC_CATFLUJO database table.
 * 
 */
@Entity
@Table(name="CGC_CATFLUJO")
@OnSearchLlavePrimaria			(atributos={"idFlujo"})
@ComponentComboCampoDescripcion (atributo="descFlujo")
public class CgcCatFlujo extends AbstractCgcCatFlujo {
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