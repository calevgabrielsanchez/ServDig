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
 * The persistent class for the CGC_CATDELEGACION database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcCatDelegacion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_DELEGACION")
	public long cveDelegacion;

	@Column(name="DESC_DELEGACION")
	private String descDelegacion;
	
}