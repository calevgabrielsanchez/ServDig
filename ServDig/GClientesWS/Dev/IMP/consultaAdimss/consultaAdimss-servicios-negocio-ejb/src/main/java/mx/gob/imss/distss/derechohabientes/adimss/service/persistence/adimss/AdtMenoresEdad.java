package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_MENORES_EDAD database table.
 * 
 */
@Entity
@Table(name="ADT_MENORES_EDAD")
@NamedQuery(name="AdtMenoresEdad.findAll", query="SELECT a FROM AdtMenoresEdad a")
public class AdtMenoresEdad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUM_FOLIO_PERSONA")
	private long numFolioPersona;

	@Column(name="CVE_NACIONALIDAD_MADRE")
	private String cveNacionalidadMadre;

	@Column(name="CVE_NACIONALIDAD_PADRE")
	private String cveNacionalidadPadre;

	@Column(name="NOM_MADRE")
	private String nomMadre;

	@Column(name="NOM_PADRE")
	private String nomPadre;

	@Column(name="NOM_PRIMER_APELLIDO_MADRE")
	private String nomPrimerApellidoMadre;

	@Column(name="NOM_PRIMER_APELLIDO_PADRE")
	private String nomPrimerApellidoPadre;

	//bi-directional one-to-one association to AdtPersonaCredencializada
	@OneToOne
	@JoinColumn(name="NUM_FOLIO_PERSONA")
	private AdtPersonaCredencializada adtPersonaCredencializada;

	public AdtMenoresEdad() {
	}

	public long getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(long numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

	public String getCveNacionalidadMadre() {
		return this.cveNacionalidadMadre;
	}

	public void setCveNacionalidadMadre(String cveNacionalidadMadre) {
		this.cveNacionalidadMadre = cveNacionalidadMadre;
	}

	public String getCveNacionalidadPadre() {
		return this.cveNacionalidadPadre;
	}

	public void setCveNacionalidadPadre(String cveNacionalidadPadre) {
		this.cveNacionalidadPadre = cveNacionalidadPadre;
	}

	public String getNomMadre() {
		return this.nomMadre;
	}

	public void setNomMadre(String nomMadre) {
		this.nomMadre = nomMadre;
	}

	public String getNomPadre() {
		return this.nomPadre;
	}

	public void setNomPadre(String nomPadre) {
		this.nomPadre = nomPadre;
	}

	public String getNomPrimerApellidoMadre() {
		return this.nomPrimerApellidoMadre;
	}

	public void setNomPrimerApellidoMadre(String nomPrimerApellidoMadre) {
		this.nomPrimerApellidoMadre = nomPrimerApellidoMadre;
	}

	public String getNomPrimerApellidoPadre() {
		return this.nomPrimerApellidoPadre;
	}

	public void setNomPrimerApellidoPadre(String nomPrimerApellidoPadre) {
		this.nomPrimerApellidoPadre = nomPrimerApellidoPadre;
	}

	public AdtPersonaCredencializada getAdtPersonaCredencializada() {
		return this.adtPersonaCredencializada;
	}

	public void setAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		this.adtPersonaCredencializada = adtPersonaCredencializada;
	}

}