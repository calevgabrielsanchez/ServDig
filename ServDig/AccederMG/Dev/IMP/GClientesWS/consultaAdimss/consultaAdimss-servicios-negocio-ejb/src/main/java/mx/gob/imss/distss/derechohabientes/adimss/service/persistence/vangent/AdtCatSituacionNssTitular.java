package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_SITUACION_NSS_TITULAR database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_SITUACION_NSS_TITULAR")
@NamedQuery(name="AdtCatSituacionNssTitular.findAll", query="SELECT a FROM AdtCatSituacionNssTitular a")
public class AdtCatSituacionNssTitular implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_SITUACION_PERSONA")
	private String cveSituacionPersona;

	@Column(name="DES_SITUACION_PERSONA")
	private String desSituacionPersona;

	public AdtCatSituacionNssTitular() {
	}

	public String getCveSituacionPersona() {
		return this.cveSituacionPersona;
	}

	public void setCveSituacionPersona(String cveSituacionPersona) {
		this.cveSituacionPersona = cveSituacionPersona;
	}

	public String getDesSituacionPersona() {
		return this.desSituacionPersona;
	}

	public void setDesSituacionPersona(String desSituacionPersona) {
		this.desSituacionPersona = desSituacionPersona;
	}

}