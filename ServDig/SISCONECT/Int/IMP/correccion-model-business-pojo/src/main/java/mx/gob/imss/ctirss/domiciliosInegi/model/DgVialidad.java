package mx.gob.imss.ctirss.domiciliosInegi.model;


import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OrderComboBy;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgVialidad;




/**
 * The persistent class for the DG_VIALIDAD database table.
 * 
 */
//@Entity
//@Table(name="DG_VIALIDAD")
//@OnSearchLlavePrimaria(atributos="cveVia")
//@ComponentComboCampoDescripcion(atributo="nomVia")
//@OrderComboBy(atributos="nomVia")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgVialidad extends AbstractDgVialidad {

}