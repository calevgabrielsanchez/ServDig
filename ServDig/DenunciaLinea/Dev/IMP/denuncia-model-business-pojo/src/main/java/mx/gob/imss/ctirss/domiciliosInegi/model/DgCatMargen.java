package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatMargen;


/**
 * The persistent class for the DG_CAT_MARGEN database table.
 * 
 */
@Entity
@Table(name="DG_CAT_MARGEN")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatMargen extends AbstractDgCatMargen {
}