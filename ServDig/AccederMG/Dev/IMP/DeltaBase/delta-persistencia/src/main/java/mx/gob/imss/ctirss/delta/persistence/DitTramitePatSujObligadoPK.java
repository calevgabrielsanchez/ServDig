package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_TRAMITE_PAT_SUJ_OBLIGADO database table.
 * 
 */
@Embeddable
public class DitTramitePatSujObligadoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_TRAMITE", insertable=false, updatable=false)
	private long cveIdTramite;

	@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO", insertable=false, updatable=false)
	private long cveIdPatronSujetoObligado;

	public DitTramitePatSujObligadoPK() {
	}
	public long getCveIdTramite() {
		return this.cveIdTramite;
	}
	public void setCveIdTramite(long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}
	public long getCveIdPatronSujetoObligado() {
		return this.cveIdPatronSujetoObligado;
	}
	public void setCveIdPatronSujetoObligado(long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitTramitePatSujObligadoPK)) {
			return false;
		}
		DitTramitePatSujObligadoPK castOther = (DitTramitePatSujObligadoPK)other;
		return 
			(this.cveIdTramite == castOther.cveIdTramite)
			&& (this.cveIdPatronSujetoObligado == castOther.cveIdPatronSujetoObligado);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdTramite ^ (this.cveIdTramite >>> 32)));
		hash = hash * prime + ((int) (this.cveIdPatronSujetoObligado ^ (this.cveIdPatronSujetoObligado >>> 32)));
		
		return hash;
	}
}