package mx.imss.ctirss.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.base.model.AbstractDltInfotrabajo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;


/**
 * The persistent class for the DLT_INFOTRABAJO database table.
 * 
 */
@Entity
@Table(name="DLT_INFOTRABAJO")
public class DltInfotrabajo extends AbstractDltInfotrabajo implements Serializable {
	private static final long serialVersionUID = 1L;
	
}