package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ADT_CAT_CTROS_ENROL database table.
 * 
 */
@Embeddable
public class AdtCatCtrosEnrolPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEGACION")
	private long cveDelegacion;

	@Column(name="NUM_NIVEL_ATENCION")
	private long numNivelAtencion;

	@Column(name="NUM_ECONOMICO")
	private long numEconomico;

	public AdtCatCtrosEnrolPK() {
	}
	public long getCveDelegacion() {
		return this.cveDelegacion;
	}
	public void setCveDelegacion(long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public long getNumNivelAtencion() {
		return this.numNivelAtencion;
	}
	public void setNumNivelAtencion(long numNivelAtencion) {
		this.numNivelAtencion = numNivelAtencion;
	}
	public long getNumEconomico() {
		return this.numEconomico;
	}
	public void setNumEconomico(long numEconomico) {
		this.numEconomico = numEconomico;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdtCatCtrosEnrolPK)) {
			return false;
		}
		AdtCatCtrosEnrolPK castOther = (AdtCatCtrosEnrolPK)other;
		return 
			(this.cveDelegacion == castOther.cveDelegacion)
			&& (this.numNivelAtencion == castOther.numNivelAtencion)
			&& (this.numEconomico == castOther.numEconomico);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveDelegacion ^ (this.cveDelegacion >>> 32)));
		hash = hash * prime + ((int) (this.numNivelAtencion ^ (this.numNivelAtencion >>> 32)));
		hash = hash * prime + ((int) (this.numEconomico ^ (this.numEconomico >>> 32)));
		
		return hash;
	}
}