package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgtPromocion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the CGT_PROMOCION database table.
 * 
 */
@Entity
@Table(name="CGT_PROMOCION")
@OnSearchLlavePrimaria			(atributos={"folio"})
public class CgtPromocion extends AbstractCgtPromocion {
	private static final long serialVersionUID = 1L;

	
	
}