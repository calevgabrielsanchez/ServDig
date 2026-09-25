package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgAsentamientoId;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatLocalidadId;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatMunicipioId;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgDomicilioGeografico;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.math.BigDecimal;


/**
 * The persistent class for the DG_DOMICILIO_GEOGRAFICO database table.
 * 
 */
@Entity
@Table(name="DG_DOMICILIO_GEOGRAFICO")
@OnSearchLlavePrimaria(atributos="domicilioId")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgDomicilioGeografico extends AbstractDgDomicilioGeografico {
	
	
	public DgDomicilioGeografico(){}
	
	
}