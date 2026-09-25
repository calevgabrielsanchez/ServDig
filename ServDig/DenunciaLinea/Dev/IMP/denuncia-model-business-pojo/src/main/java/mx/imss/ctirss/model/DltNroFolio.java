package mx.imss.ctirss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.imss.ctirss.base.model.AbstractDltNroFolio;

@Entity
@Table(name="DLT_NROFOLIO")
public class DltNroFolio extends AbstractDltNroFolio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
