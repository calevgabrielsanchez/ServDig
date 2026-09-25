package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCodigosPostales;


/**
 * The persistent class for the DG_CODIGOS_POSTALES database table.
 * 
 */
//@Entity
//@Table(name="DG_CODIGOS_POSTALES")
//@OnSearchLlavePrimaria(atributos="id.codigo")
//@ComponentComboCampoDescripcion(atributo="id.codigo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCodigosPostales extends AbstractDgCodigosPostales {	
	
	
}