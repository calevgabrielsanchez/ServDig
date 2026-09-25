package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatDerechosTransito;


/**
 * The persistent class for the DG_CAT_DERECHOS_TRANSITO database table.
 * 
 */
@Entity
@Table(name="DG_CAT_DERECHOS_TRANSITO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatDerechosTransito extends AbstractDgCatDerechosTransito {
	
}