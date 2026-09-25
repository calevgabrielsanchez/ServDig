package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OrderComboBy;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatTipoAsen;


/**
 * The persistent class for the DG_CAT_TIPO_ASEN database table.
 * 
 */
//@Entity
//@Table(name="DG_CAT_TIPO_ASEN")
//@OnSearchLlavePrimaria(atributos="cveTipoAsen")
//@ComponentComboCampoDescripcion(atributo="nombre")
//@OrderComboBy(atributos="nombre")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatTipoAsen extends AbstractDgCatTipoAsen {

}