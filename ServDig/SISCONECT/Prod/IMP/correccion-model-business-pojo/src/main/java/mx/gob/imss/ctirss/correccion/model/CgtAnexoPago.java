package mx.gob.imss.ctirss.correccion.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgtAnexoPago;
import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CGT_ANEXOPAGOS database table.
 * 
 */
@Entity
@Table(name="CGT_ANEXOPAGOS")
@OnSearchLlavePrimaria			(atributos={"idPago"})
public class CgtAnexoPago extends AbstractCgtAnexoPago {
	private static final long serialVersionUID = 1L;

	
}