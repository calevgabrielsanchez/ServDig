package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatAdministracion;


/**
 * The persistent class for the DG_CAT_ADMINISTRACION database table.
 * 
 */
@Entity
@Table(name="DG_CAT_ADMINISTRACION")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatAdministracion extends AbstractDgCatAdministracion {
	
}