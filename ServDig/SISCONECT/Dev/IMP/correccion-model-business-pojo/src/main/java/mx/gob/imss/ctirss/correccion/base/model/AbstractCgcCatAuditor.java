/**
 * RBG clean service
 * 2013-AG-03
 */

package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CGC_CATAUDITOR database table.
 * 
 */
@MappedSuperclass
public class AbstractCgcCatAuditor extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public AbstractCgcCatAuditorPK id;

	private String curp;

	private String matricula;

	private String nombre;

	public AbstractCgcCatAuditor() {
	}

	public AbstractCgcCatAuditorPK getId() {
		return this.id;
	}

	public void setId(AbstractCgcCatAuditorPK id) {
		this.id = id;
	}

	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}