/*
 * Clase creada el 25/04/2014, por Juan Osorio Alvarez
 * Esta clase representa la llave compuesta de la tabla DIT_SOLICITUD_NSS_CORREO
 */
package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DitSolicitudNssCorreoPK implements Serializable {
	private static final long serialVersionUID = -8703542187222774646L;

	@Column(name = "REF_CORREO_ELECTRONICO")
	private String refCorreoElectronico;

	@Column(name = "CVE_ID_TIPO_SOLICITUD")
	private Long cveIdTipoSolicitud;

	/**
	 * @return the refCorreoElectronico
	 */
	public String getRefCorreoElectronico() {
		return refCorreoElectronico;
	}

	/**
	 * @param refCorreoElectronico
	 *            the refCorreoElectronico to set
	 */
	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}

	/**
	 * 
	 * @return cveIdTipoSolicitud
	 */
	public Long getCveIdTipoSolicitud() {
		return cveIdTipoSolicitud;
	}

	/**
	 * 
	 * @param cveIdTipoSolicitud
	 */
	public void setCveIdTipoSolicitud(Long cveIdTipoSolicitud) {
		this.cveIdTipoSolicitud = cveIdTipoSolicitud;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cveIdTipoSolicitud == null) ? 0 : cveIdTipoSolicitud
						.hashCode());
		result = prime
				* result
				+ ((refCorreoElectronico == null) ? 0 : refCorreoElectronico
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DitSolicitudNssCorreoPK other = (DitSolicitudNssCorreoPK) obj;
		if (cveIdTipoSolicitud == null) {
			if (other.cveIdTipoSolicitud != null)
				return false;
		} else if (!cveIdTipoSolicitud.equals(other.cveIdTipoSolicitud))
			return false;
		if (refCorreoElectronico == null) {
			if (other.refCorreoElectronico != null)
				return false;
		} else if (!refCorreoElectronico.equals(other.refCorreoElectronico))
			return false;
		return true;
	}

}