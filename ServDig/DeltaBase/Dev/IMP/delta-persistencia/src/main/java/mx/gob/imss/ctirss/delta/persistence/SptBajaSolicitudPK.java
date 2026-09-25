package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SPT_BAJA_SOLICITUD database table.
 * 
 */
@Embeddable
public class SptBajaSolicitudPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_TRAMITE_PENSION", insertable=false, updatable=false)
	private long cveIdTramitePension;

	@Column(name="ID_CAUSA_BAJA", insertable=false, updatable=false)
	private String idCausaBaja;

	public SptBajaSolicitudPK() {
	}
	public long getCveIdTramitePension() {
		return this.cveIdTramitePension;
	}
	public void setCveIdTramitePension(long cveIdTramitePension) {
		this.cveIdTramitePension = cveIdTramitePension;
	}
	public String getIdCausaBaja() {
		return this.idCausaBaja;
	}
	public void setIdCausaBaja(String idCausaBaja) {
		this.idCausaBaja = idCausaBaja;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SptBajaSolicitudPK)) {
			return false;
		}
		SptBajaSolicitudPK castOther = (SptBajaSolicitudPK)other;
		return 
			(this.cveIdTramitePension == castOther.cveIdTramitePension)
			&& this.idCausaBaja.equals(castOther.idCausaBaja);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdTramitePension ^ (this.cveIdTramitePension >>> 32)));
		hash = hash * prime + this.idCausaBaja.hashCode();
		
		return hash;
	}
}