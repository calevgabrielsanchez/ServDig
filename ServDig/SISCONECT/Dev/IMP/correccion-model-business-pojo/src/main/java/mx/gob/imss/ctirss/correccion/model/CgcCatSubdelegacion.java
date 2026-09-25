package mx.gob.imss.ctirss.correccion.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcCatSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the CGC_CATSUBDELEGACION database table.
 * 
 */
@Entity
@Table(name="CGC_CATSUBDELEGACION")
@OnSearchLlavePrimaria		(atributos={"idSubdelegacion"})
@ComponentComboCampoDescripcion	(atributo ="descSubdelegacion")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CgcCatSubdelegacion extends AbstractCgcCatSubdelegacion {
	private static final long serialVersionUID = 1L;

	
}