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
 * The persistent class for the CGC_CATREGION database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcCatRegion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_REGION")
	public long cveRegion;

	@Column(name = "DESC_REGION")
	private String descRegion;

	public AbstractCgcCatRegion() {
	}

	public long getCveRegion() {
		return this.cveRegion;
	}

	public void setCveRegion(long cveRegion) {
		this.cveRegion = cveRegion;
	}

	public String getDescRegion() {
		return this.descRegion;
	}

	public void setDescRegion(String descRegion) {
		this.descRegion = descRegion;
	}
}