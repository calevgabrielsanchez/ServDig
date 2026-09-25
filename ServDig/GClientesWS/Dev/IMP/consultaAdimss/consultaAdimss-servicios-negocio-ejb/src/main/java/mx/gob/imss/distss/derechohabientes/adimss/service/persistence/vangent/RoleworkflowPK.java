package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ROLEWORKFLOWS database table.
 * 
 */
@Embeddable
public class RoleworkflowPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idroleworkflows;

	private long idworkflow;

	public RoleworkflowPK() {
	}
	public long getIdroleworkflows() {
		return this.idroleworkflows;
	}
	public void setIdroleworkflows(long idroleworkflows) {
		this.idroleworkflows = idroleworkflows;
	}
	public long getIdworkflow() {
		return this.idworkflow;
	}
	public void setIdworkflow(long idworkflow) {
		this.idworkflow = idworkflow;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RoleworkflowPK)) {
			return false;
		}
		RoleworkflowPK castOther = (RoleworkflowPK)other;
		return 
			(this.idroleworkflows == castOther.idroleworkflows)
			&& (this.idworkflow == castOther.idworkflow);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idroleworkflows ^ (this.idroleworkflows >>> 32)));
		hash = hash * prime + ((int) (this.idworkflow ^ (this.idworkflow >>> 32)));
		
		return hash;
	}
}