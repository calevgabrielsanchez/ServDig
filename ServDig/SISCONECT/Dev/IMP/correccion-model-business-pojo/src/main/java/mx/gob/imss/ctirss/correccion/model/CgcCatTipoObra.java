package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcCatTipoObra;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the CGC_CATTIPOOBRA database table.
 * 
 */
@Entity
@Table(name="CGC_CATTIPOOBRA")
@OnSearchLlavePrimaria			(atributos={"id.idCodigoobra"})
@ComponentComboCampoDescripcion (atributo="tipoobra")
public class CgcCatTipoObra extends AbstractCgcCatTipoObra {
	private static final long serialVersionUID = 1L;

	

}