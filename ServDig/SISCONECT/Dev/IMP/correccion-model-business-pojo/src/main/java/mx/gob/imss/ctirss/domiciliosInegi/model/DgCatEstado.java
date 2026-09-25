package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OrderComboBy;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatEstado;


/**
 * The persistent class for the DG_CAT_ESTADO database table.
 * 
 */
//@Entity
//@Table(name="DG_CAT_ESTADO")
//@OnSearchLlavePrimaria(atributos="cveEnt")
//@ComponentComboCampoDescripcion(atributo="nomEnt")
//@OrderComboBy(atributos="nomEnt")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatEstado extends AbstractDgCatEstado {
	
}