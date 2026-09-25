/**
 * RBG clean service
 * 2013-AGO-03
 */
package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CGC_CATCONCEPTOOMITIDO database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcCatConceptoOmitido extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CONCEPTOOMITIDO")
	public long idConceptoomitido;

	@Column(name = "DESC_CONCEPTOOMITIDO")
	private String descConceptoomitido;

	public AbstractCgcCatConceptoOmitido() {
	}

	public long getIdConceptoomitido() {
		return idConceptoomitido;
	}

	public void setIdConceptoomitido(long idConceptoomitido) {
		this.idConceptoomitido = idConceptoomitido;
	}

	public String getDescConceptoomitido() {
		return descConceptoomitido;
	}

	public void setDescConceptoomitido(String descConceptoomitido) {
		this.descConceptoomitido = descConceptoomitido;
	}

}