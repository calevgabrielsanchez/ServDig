package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the APT_RESPUESTA_PREVALIDACION database table.
 * 
 */
@Embeddable
public class AptRespuestaPrevalidacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_NSS")
	private String idNss;

	@Column(name="ID_SOLICITUD")
	private String idSolicitud;

	@Column(name="NUM_ENVIO")
	private long numEnvio;

	public AptRespuestaPrevalidacionPK() {
	}
	public String getIdNss() {
		return this.idNss;
	}
	public void setIdNss(String idNss) {
		this.idNss = idNss;
	}
	public String getIdSolicitud() {
		return this.idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public long getNumEnvio() {
		return this.numEnvio;
	}
	public void setNumEnvio(long numEnvio) {
		this.numEnvio = numEnvio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AptRespuestaPrevalidacionPK)) {
			return false;
		}
		AptRespuestaPrevalidacionPK castOther = (AptRespuestaPrevalidacionPK)other;
		return 
			this.idNss.equals(castOther.idNss)
			&& this.idSolicitud.equals(castOther.idSolicitud)
			&& (this.numEnvio == castOther.numEnvio);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idNss.hashCode();
		hash = hash * prime + this.idSolicitud.hashCode();
		hash = hash * prime + ((int) (this.numEnvio ^ (this.numEnvio >>> 32)));
		
		return hash;
	}
}