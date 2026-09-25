package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatEstadoCp;
 

/**
 * The persistent class for the DG_CAT_ESTADO_CP database table.
 * 
 */
@Entity
@Table(name="DG_CAT_ESTADO_CP")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatEstadoCp extends AbstractDgCatEstadoCp {
	
}