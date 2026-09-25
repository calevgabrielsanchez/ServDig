package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatTermGen;


/**
 * The persistent class for the DG_CAT_TERM_GEN database table.
 * 
 */
//@Entity
//@Table(name="DG_CAT_TERM_GEN")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatTermGen extends AbstractDgCatTermGen {
	
}