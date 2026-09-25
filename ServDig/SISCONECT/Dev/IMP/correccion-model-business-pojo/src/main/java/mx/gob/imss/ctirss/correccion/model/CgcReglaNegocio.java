package mx.gob.imss.ctirss.correccion.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgcReglaNegocio;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;


import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CGC_REGLANEGOCIO database table.
 * 
 */
@Entity
@Table(name="CGC_REGLANEGOCIO")
@OnSearchLlavePrimaria			(atributos={"cveReglaNegocio"})
public class CgcReglaNegocio extends AbstractCgcReglaNegocio {
	private static final long serialVersionUID = 1L;

	
}