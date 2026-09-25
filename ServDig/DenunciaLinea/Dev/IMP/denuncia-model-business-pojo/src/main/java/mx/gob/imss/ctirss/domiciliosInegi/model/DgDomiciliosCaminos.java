package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgDomiciliosCaminos;

import java.math.BigDecimal;


/**
 * The persistent class for the DG_DOMICILIOS_CAMINOS database table.
 * 
 */
@Entity
@Table(name="DG_DOMICILIOS_CAMINOS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgDomiciliosCaminos extends AbstractDgDomiciliosCaminos{
	
	
}