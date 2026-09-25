package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLMENTWORKFLOWS database table.
 * 
 */
@Embeddable
public class EnrollmentworkflowPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idenrolworkflow;

	@Column(insertable=false, updatable=false)
	private long idenroltype;

	@Column(insertable=false, updatable=false)
	private long idenrolmode;

	public EnrollmentworkflowPK() {
	}
	public long getIdenrolworkflow() {
		return this.idenrolworkflow;
	}
	public void setIdenrolworkflow(long idenrolworkflow) {
		this.idenrolworkflow = idenrolworkflow;
	}
	public long getIdenroltype() {
		return this.idenroltype;
	}
	public void setIdenroltype(long idenroltype) {
		this.idenroltype = idenroltype;
	}
	public long getIdenrolmode() {
		return this.idenrolmode;
	}
	public void setIdenrolmode(long idenrolmode) {
		this.idenrolmode = idenrolmode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnrollmentworkflowPK)) {
			return false;
		}
		EnrollmentworkflowPK castOther = (EnrollmentworkflowPK)other;
		return 
			(this.idenrolworkflow == castOther.idenrolworkflow)
			&& (this.idenroltype == castOther.idenroltype)
			&& (this.idenrolmode == castOther.idenrolmode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idenrolworkflow ^ (this.idenrolworkflow >>> 32)));
		hash = hash * prime + ((int) (this.idenroltype ^ (this.idenroltype >>> 32)));
		hash = hash * prime + ((int) (this.idenrolmode ^ (this.idenrolmode >>> 32)));
		
		return hash;
	}
}