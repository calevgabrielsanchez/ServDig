package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLMENTDATADETAILS_TEMP database table.
 * 
 */
@Embeddable
public class EnrollmentdatadetailsTempPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idenroldatadetail;

	private long idenroldatatype;

	private long idenrol;

	private long idenrollmentstation;

	public EnrollmentdatadetailsTempPK() {
	}
	public long getIdenroldatadetail() {
		return this.idenroldatadetail;
	}
	public void setIdenroldatadetail(long idenroldatadetail) {
		this.idenroldatadetail = idenroldatadetail;
	}
	public long getIdenroldatatype() {
		return this.idenroldatatype;
	}
	public void setIdenroldatatype(long idenroldatatype) {
		this.idenroldatatype = idenroldatatype;
	}
	public long getIdenrol() {
		return this.idenrol;
	}
	public void setIdenrol(long idenrol) {
		this.idenrol = idenrol;
	}
	public long getIdenrollmentstation() {
		return this.idenrollmentstation;
	}
	public void setIdenrollmentstation(long idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnrollmentdatadetailsTempPK)) {
			return false;
		}
		EnrollmentdatadetailsTempPK castOther = (EnrollmentdatadetailsTempPK)other;
		return 
			(this.idenroldatadetail == castOther.idenroldatadetail)
			&& (this.idenroldatatype == castOther.idenroldatatype)
			&& (this.idenrol == castOther.idenrol)
			&& (this.idenrollmentstation == castOther.idenrollmentstation);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idenroldatadetail ^ (this.idenroldatadetail >>> 32)));
		hash = hash * prime + ((int) (this.idenroldatatype ^ (this.idenroldatatype >>> 32)));
		hash = hash * prime + ((int) (this.idenrol ^ (this.idenrol >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		
		return hash;
	}
}