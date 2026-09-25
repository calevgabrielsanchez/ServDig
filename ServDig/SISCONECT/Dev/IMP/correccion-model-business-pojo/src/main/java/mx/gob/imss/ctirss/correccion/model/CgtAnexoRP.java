package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgtAnexoRP;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the CGT_ANEXORP database table.
 * 
 */
@Entity
@Table(name="CGT_ANEXORP")
@OnSearchLlavePrimaria			(atributos={"id.folio"})
public class CgtAnexoRP extends AbstractCgtAnexoRP {
	private static final long serialVersionUID = 1L;
	
	
public String getRegPats(){
	return super.getId().getRp();
}
	
}