package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.framework.annotations.ComponentComboCampoDescripcion;
import mx.imss.ctirss.framework.annotations.OnSearchLlavePrimaria;
import mx.imss.ctirss.framework.annotations.OrderComboBy;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatVialidad;


/**
 * The persistent class for the DG_CAT_VIALIDAD database table.
 * 
 */
@Entity
@Table(name="DG_CAT_VIALIDAD")
@OnSearchLlavePrimaria(atributos="cveTipoVial")
@ComponentComboCampoDescripcion(atributo="descripcion")
@OrderComboBy(atributos="descripcion")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatVialidad extends AbstractDgCatVialidad {
}