package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.framework.annotations.ComponentComboCampoDescripcion;
import mx.imss.ctirss.framework.annotations.OnSearchLlavePrimaria;
import mx.imss.ctirss.framework.annotations.OrderComboBy;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatTipoDom;


/**
 * The persistent class for the DG_CAT_TIPO_DOM database table.
 * 
 */
@Entity
@Table(name="DG_CAT_TIPO_DOM")
@OnSearchLlavePrimaria(atributos="cveTipoDom")
@ComponentComboCampoDescripcion(atributo="descripcion")
@OrderComboBy(atributos="descripcion")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatTipoDom extends AbstractDgCatTipoDom{
}