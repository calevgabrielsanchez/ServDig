package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.framework.annotations.ComponentComboCampoDescripcion;
import mx.imss.ctirss.framework.annotations.OnSearchLlavePrimaria;
import mx.imss.ctirss.framework.annotations.OrderComboBy;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgAsentamiento;

import java.math.BigDecimal;


/**
 * The persistent class for the DG_ASENTAMIENTO database table.
 * 
 */
@Entity
@Table(name="DG_ASENTAMIENTO")
@OnSearchLlavePrimaria(atributos="id.cveAsen")
@ComponentComboCampoDescripcion(atributo="nomAsen")
@OrderComboBy(atributos="nomAsen")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgAsentamiento extends AbstractDgAsentamiento {
	
}