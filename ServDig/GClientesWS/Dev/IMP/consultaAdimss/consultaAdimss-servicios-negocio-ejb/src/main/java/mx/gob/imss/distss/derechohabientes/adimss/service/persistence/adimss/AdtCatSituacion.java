package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADT_CAT_SITUACION database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_SITUACION")
@NamedQuery(name="AdtCatSituacion.findAll", query="SELECT a FROM AdtCatSituacion a")
public class AdtCatSituacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_SITUACION_PERSONA")
	private String cveSituacionPersona;

	@Column(name="DES_SITUACION_PERSONA")
	private String desSituacionPersona;

	//bi-directional many-to-one association to AdtPersonaCredencializada
	@OneToMany(mappedBy="adtCatSituacion")
	private List<AdtPersonaCredencializada> adtPersonaCredencializadas;

	public AdtCatSituacion() {
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

	public List<AdtPersonaCredencializada> getAdtPersonaCredencializadas() {
		return this.adtPersonaCredencializadas;
	}

	public void setAdtPersonaCredencializadas(List<AdtPersonaCredencializada> adtPersonaCredencializadas) {
		this.adtPersonaCredencializadas = adtPersonaCredencializadas;
	}

	public AdtPersonaCredencializada addAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().add(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdtCatSituacion(this);

		return adtPersonaCredencializada;
	}

	public AdtPersonaCredencializada removeAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		getAdtPersonaCredencializadas().remove(adtPersonaCredencializada);
		adtPersonaCredencializada.setAdtCatSituacion(null);

		return adtPersonaCredencializada;
	}

}