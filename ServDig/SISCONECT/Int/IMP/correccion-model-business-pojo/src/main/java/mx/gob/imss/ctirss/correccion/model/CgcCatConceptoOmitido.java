package mx.gob.imss.ctirss.correccion.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcCatConceptoOmitido;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

import java.util.Set;


/**
 * The persistent class for the CGC_CATCONCEPTOOMITIDO database table.
 * 
 */
@Entity
@Table(name="CGC_CATCONCEPTOOMITIDO")
@OnSearchLlavePrimaria			(atributos={"idConceptoomitido"})
public class CgcCatConceptoOmitido extends AbstractCgcCatConceptoOmitido {
	private static final long serialVersionUID = 1L;

	
}