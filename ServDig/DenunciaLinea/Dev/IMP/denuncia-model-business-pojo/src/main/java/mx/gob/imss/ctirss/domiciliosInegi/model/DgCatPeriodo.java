package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatPeriodo;

import java.util.Date;


/**
 * The persistent class for the DG_CAT_PERIODO database table.
 * 
 */
@Entity
@Table(name="DG_CAT_PERIODO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgCatPeriodo extends AbstractDgCatPeriodo {
}