package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the DIT_CORRECCION_DATO_DERECHOHAB database table.
 * 
 */
@Embeddable
public class DitCorreccionDatoDerechohabPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_SOLICITUD")
	private long cveIdSolicitud;

	@Column(name="CVE_ID_PERSONA")
	private long cveIdPersona;

	
	@Column(name="CVE_ID_TIPO_TRAMITE")
	private Long cveIdTipoTramite;
	
    public DitCorreccionDatoDerechohabPK() {
    }
    
	/**
	 * @return the cveIdTipoTramite
	 */
	public Long getCveIdTipoTramite() {
		return cveIdTipoTramite;
	}

	/**
	 * @param cveIdTipoTramite the cveIdTipoTramite to set
	 */
	public void setCveIdTipoTramite(Long cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
	}

	public long getCveIdSolicitud() {
		return this.cveIdSolicitud;
	}
	public void setCveIdSolicitud(long cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}
	public long getCveIdPersona() {
		return this.cveIdPersona;
	}
	public void setCveIdPersona(long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitCorreccionDatoDerechohabPK)) {
			return false;
		}
		DitCorreccionDatoDerechohabPK castOther = (DitCorreccionDatoDerechohabPK)other;
		return 
			(this.cveIdSolicitud == castOther.cveIdSolicitud)
			&& (this.cveIdPersona == castOther.cveIdPersona)
			&& (this.cveIdTipoTramite == castOther.cveIdTipoTramite);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdSolicitud ^ (this.cveIdSolicitud >>> 32)));
		hash = hash * prime + ((int) (this.cveIdPersona ^ (this.cveIdPersona >>> 32)));
		hash = hash * prime + ((int) (this.cveIdTipoTramite ^ (this.cveIdTipoTramite >>> 32)));
		return hash;
    }
}