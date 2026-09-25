package mx.gob.imss.ctirss.correccion.model;


import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcCatOrigen;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

/**
 * The persistent class for the CGC_CATORIGEN database table.
 * 
 */
@Entity
@Table(name="CGC_CATORIGEN")
@OnSearchLlavePrimaria			(atributos={"idOrigen"})
@ComponentComboCampoDescripcion (atributo="descOrigen")
public class CgcCatOrigen extends AbstractCgcCatOrigen {

	

	
	
}