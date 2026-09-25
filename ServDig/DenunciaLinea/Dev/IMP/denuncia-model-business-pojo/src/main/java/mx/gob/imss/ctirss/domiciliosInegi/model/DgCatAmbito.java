package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.framework.annotations.ComponentComboCampoDescripcion;
import mx.imss.ctirss.framework.annotations.OnSearchLlavePrimaria;
import mx.imss.ctirss.framework.annotations.OrderComboBy;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatAmbito;


/**
 * The persistent class for the DG_CAT_AMBITO database table.
 * 
 */
@Entity
@Table(name="DG_CAT_AMBITO")
@OnSearchLlavePrimaria(atributos="ambito")
@ComponentComboCampoDescripcion(atributo="nombre")
@OrderComboBy(atributos="nombre")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatAmbito extends AbstractDgCatAmbito {
	
}