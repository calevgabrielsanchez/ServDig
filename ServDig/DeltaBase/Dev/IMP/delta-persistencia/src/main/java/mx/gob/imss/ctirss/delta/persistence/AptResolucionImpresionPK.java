package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the APT_RESOLUCION_IMPRESION database table.
 * 
 */
@Embeddable
public class AptResolucionImpresionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_SOLICITUD", insertable=false, updatable=false)
	private String idSolicitud;

	@Column(name="ID_NSS", insertable=false, updatable=false)
	private long idNss;

	public AptResolucionImpresionPK() {
	}
	public String getIdSolicitud() {
		return this.idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public long getIdNss() {
		return this.idNss;
	}
	public void setIdNss(long idNss) {
		this.idNss = idNss;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AptResolucionImpresionPK)) {
			return false;
		}
		AptResolucionImpresionPK castOther = (AptResolucionImpresionPK)other;
		return 
			this.idSolicitud.equals(castOther.idSolicitud)
			&& (this.idNss == castOther.idNss);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idSolicitud.hashCode();
		hash = hash * prime + ((int) (this.idNss ^ (this.idNss >>> 32)));
		
		return hash;
	}
}