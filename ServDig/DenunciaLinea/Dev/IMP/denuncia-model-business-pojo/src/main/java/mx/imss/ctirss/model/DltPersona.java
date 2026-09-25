package mx.imss.ctirss.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import mx.imss.ctirss.base.model.AbstractDltPersona;




/**
 * The persistent class for the DLT_PERSONA database table.
 * 
 */
@Entity
@Table(name="DLT_PERSONA")
public class DltPersona  extends AbstractDltPersona implements Serializable {
	private static final long serialVersionUID = 1L;

}