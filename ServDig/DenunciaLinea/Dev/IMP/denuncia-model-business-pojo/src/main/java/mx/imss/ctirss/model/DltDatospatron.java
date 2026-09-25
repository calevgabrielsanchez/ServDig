package mx.imss.ctirss.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.base.model.AbstractDltDatospatron;
import mx.imss.ctirss.base.model.AbstractDltPersona;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DLT_DATOSPATRON database table.
 * 
 */
@Entity
@Table(name="DLT_DATOSPATRON")
public class DltDatospatron extends AbstractDltDatospatron implements Serializable {
	private static final long serialVersionUID = 1L;

}