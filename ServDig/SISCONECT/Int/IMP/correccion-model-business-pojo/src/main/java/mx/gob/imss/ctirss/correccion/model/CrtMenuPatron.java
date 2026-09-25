package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrtMenuPatron;


@Entity
@Table(name="CRT_MENU_PATRON")
public class CrtMenuPatron extends AbstractCrtMenuPatron {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
