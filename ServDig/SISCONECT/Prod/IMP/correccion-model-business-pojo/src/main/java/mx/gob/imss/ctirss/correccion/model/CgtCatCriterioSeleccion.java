package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgtCatCriterioSeleccion;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the CGT_CATCRITERIOSELECCION database table.
 * 
 */
@Entity
@Table(name="CGC_CATCRITERIOSELECCION")
@OnSearchLlavePrimaria			(atributos={"idCriterioseleccion"})
@ComponentComboCampoDescripcion (atributo="descCriterioseleccion")
public class CgtCatCriterioSeleccion extends AbstractCgtCatCriterioSeleccion {
	private static final long serialVersionUID = 1L;

	
	
}