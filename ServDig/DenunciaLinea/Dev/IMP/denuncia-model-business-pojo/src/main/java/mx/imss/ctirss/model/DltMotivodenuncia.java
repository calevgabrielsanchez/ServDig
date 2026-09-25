package mx.imss.ctirss.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.base.model.AbstractDltMotivodenuncia;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DLT_MOTIVODENUNCIA database table.
 * 
 */
@Entity
@Table(name="DLT_MOTIVODENUNCIA")
public class DltMotivodenuncia extends AbstractDltMotivodenuncia implements Serializable {
	private static final long serialVersionUID = 1L;


}