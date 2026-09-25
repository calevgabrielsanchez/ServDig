package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.annotations.OrderComboBy;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatMunicipio;


/**
 * The persistent class for the DG_CAT_MUNICIPIO database table.
 * 
 */

//@Entity
//@Table(name="DG_CAT_MUNICIPIO")
//@OnSearchLlavePrimaria(atributos="id.cveMun")
//@ComponentComboCampoDescripcion(atributo="nomMun")
@OrderComboBy(atributos="nomMun")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatMunicipio extends AbstractDgCatMunicipio {

}