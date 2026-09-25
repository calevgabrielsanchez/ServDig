package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_DELEGACION database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_DELEGACION")
@NamedQuery(name="AdtCatDelegacion.findAll", query="SELECT a FROM AdtCatDelegacion a")
public class AdtCatDelegacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_DELEGACION")
	private long cveDelegacion;

	@Column(name="DES_DELEGACION")
	private String desDelegacion;

	public AdtCatDelegacion() {
	}

	public long getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getDesDelegacion() {
		return this.desDelegacion;
	}

	public void setDesDelegacion(String desDelegacion) {
		this.desDelegacion = desDelegacion;
	}

}