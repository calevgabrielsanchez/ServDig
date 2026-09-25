package mx.gob.imss.ctirss.correccion.base.model;


import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the CGC_CATMOTIVOCANCELACION database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcCatMotivoCancelacion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_MOTIVOCANCELACION")
	public long idMotivocancelacion;

	@Column(name="MOTIVOCANCELACION")
	private String motivocancelacion;

    public AbstractCgcCatMotivoCancelacion() {
    }

	public long getIdMotivocancelacion() {
		return this.idMotivocancelacion;
	}

	public void setIdMotivocancelacion(long idMotivocancelacion) {
		this.idMotivocancelacion = idMotivocancelacion;
	}

	public String getMotivocancelacion() {
		return this.motivocancelacion;
	}

	public void setMotivocancelacion(String motivocancelacion) {
		this.motivocancelacion = motivocancelacion;
	}

}