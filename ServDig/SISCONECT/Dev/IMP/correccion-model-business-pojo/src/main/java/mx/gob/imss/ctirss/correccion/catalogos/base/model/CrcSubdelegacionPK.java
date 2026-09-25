package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The primary key class for the CRC_SUBDELEGACION database table.
 * 
 */
public class CrcSubdelegacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	private BigDecimal cveDelegOrig;
	
	private BigDecimal sdelegOrig;

    public CrcSubdelegacionPK() {
    }
	public BigDecimal getCveDelegOrig() {
		return this.cveDelegOrig;
	}
	public void setCveDelegOrig(BigDecimal cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}
	public BigDecimal getSdelegOrig() {
		return this.sdelegOrig;
	}
	public void setSdelegOrig(BigDecimal sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CrcSubdelegacionPK)) {
			return false;
		}
		CrcSubdelegacionPK castOther = (CrcSubdelegacionPK)other;
		return 
			(this.cveDelegOrig == castOther.cveDelegOrig)
			&& (this.sdelegOrig == castOther.sdelegOrig);

    }
    
	public int hashCode() {
		return this.cveDelegOrig.hashCode()+this.sdelegOrig.hashCode();
    }
}