package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the DIT_INFO_COMPLEMENTARIA database table.
 * 
 */
@Entity
@Table(name = "DIT_INFO_COMPLEMENTARIA")
public class DitInfoComplementaria implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = -7179842907443632452L;

	@Id
	@Column(name = "CVE_ID_ANALISIS")
	private long cveIdAnalisis;

	@Column(name = "DES_INFORMACION_EXTERNA")
	private String informacionExterna;

	@Column(name = "DES_OTROS")
	private String otros;

	@Column(name = "DES_OBSERVACIONES")
	private String observaciones;

	public long getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	public void setCveIdAnalisis(long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	public String getInformacionExterna() {
		return informacionExterna;
	}

	public void setInformacionExterna(String informacionExterna) {
		this.informacionExterna = informacionExterna;
	}

	public String getOtros() {
		return otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
