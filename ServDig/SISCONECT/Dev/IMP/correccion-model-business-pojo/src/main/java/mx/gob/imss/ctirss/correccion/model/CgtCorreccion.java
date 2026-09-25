package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCgtCorreccion;
import mx.gob.imss.ctirss.correccion.utils.Functions;


/**
 * The persistent class for the CGT_CORRECCION database table.
 * 
 */
@Entity
@Table(name="CGT_CORRECCION")
public class CgtCorreccion extends AbstractCgtCorreccion {
	private static final long serialVersionUID = 1L;

	public String getDel(){
		return Functions.dateToString(this.getPeriododel());
	}

	public String getAl(){
		return Functions.dateToString(this.getPeriodoal());
	}
	
}