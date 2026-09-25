package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OrderComboBy;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatLocalidad;

import java.math.BigDecimal;


/**
 * The persistent class for the DG_CAT_LOCALIDAD database table.
 * 
 */
//@Entity
//@Table(name="DG_CAT_LOCALIDAD")
//@OnSearchLlavePrimaria(atributos="id.cveLoc")
//@ComponentComboCampoDescripcion(atributo="nomLoc")
//@OrderComboBy(atributos="nomLoc")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatLocalidad extends AbstractDgCatLocalidad {
	
}