package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CFT_INTEGRAFISCALIZA database table.
 * 
 */
@Embeddable
public class CftIntegrafiscalizaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEG_ORIG", unique=true, nullable=false, precision=2)
	private long cveDelegOrig;

	@Column(name="SDELEG_ORIG", unique=true, nullable=false, precision=2)
	private long sdelegOrig;

	@Column(name="CVE_TPOFISCALIZA", unique=true, nullable=false, precision=22)
	private long cveTpofiscaliza;

	@Column(name="NUM_EXPEDIENTE", unique=true, nullable=false, length=25)
	private String numExpediente;

    public CftIntegrafiscalizaPK() {
    }
	public long getCveDelegOrig() {
		return this.cveDelegOrig;
	}
	public void setCveDelegOrig(long cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}
	public long getSdelegOrig() {
		return this.sdelegOrig;
	}
	public void setSdelegOrig(long sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}
	public long getCveTpofiscaliza() {
		return this.cveTpofiscaliza;
	}
	public void setCveTpofiscaliza(long cveTpofiscaliza) {
		this.cveTpofiscaliza = cveTpofiscaliza;
	}
	public String getNumExpediente() {
		return this.numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CftIntegrafiscalizaPK)) {
			return false;
		}
		CftIntegrafiscalizaPK castOther = (CftIntegrafiscalizaPK)other;
		return 
			(this.cveDelegOrig == castOther.cveDelegOrig)
			&& (this.sdelegOrig == castOther.sdelegOrig)
			&& (this.cveTpofiscaliza == castOther.cveTpofiscaliza)
			&& this.numExpediente.equals(castOther.numExpediente);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveDelegOrig ^ (this.cveDelegOrig >>> 32)));
		hash = hash * prime + ((int) (this.sdelegOrig ^ (this.sdelegOrig >>> 32)));
		hash = hash * prime + ((int) (this.cveTpofiscaliza ^ (this.cveTpofiscaliza >>> 32)));
		hash = hash * prime + this.numExpediente.hashCode();
		
		return hash;
    }
}