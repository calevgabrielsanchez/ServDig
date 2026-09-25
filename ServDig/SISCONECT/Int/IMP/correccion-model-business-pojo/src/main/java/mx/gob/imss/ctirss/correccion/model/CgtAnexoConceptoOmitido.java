package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgtAnexoConceptoOmitido;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the CGT_ANEXOCONCEPTOOMITIDO database table.
 * 
 */
@Entity
@Table(name="CGT_ANEXOCONCEPTOOMITIDO")
@OnSearchLlavePrimaria			(atributos={"id"})
public class CgtAnexoConceptoOmitido extends AbstractCgtAnexoConceptoOmitido {
	private static final long serialVersionUID = 1L;

	
	
}