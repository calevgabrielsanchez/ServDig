package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ADT_ACT_NACIMIENTO database table.
 * 
 */
@Entity
@Table(name="ADT_ACT_NACIMIENTO")
@NamedQuery(name="AdtActNacimiento.findAll", query="SELECT a FROM AdtActNacimiento a")
public class AdtActNacimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUM_FOLIO_PERSONA")
	private long numFolioPersona;

	@Column(name="CVE_ENT_REGISTRO")
	private BigDecimal cveEntRegistro;

	@Column(name="CVE_MUN_REGISTRO")
	private BigDecimal cveMunRegistro;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO")
	private Date fecRegistro;

	@Column(name="NUM_REG_ACTA_ASIG")
	private String numRegActaAsig;

	@Column(name="REF_ID_LIBRO")
	private String refIdLibro;

	//bi-directional one-to-one association to AdtPersonaCredencializada
	@OneToOne
	@JoinColumn(name="NUM_FOLIO_PERSONA")
	private AdtPersonaCredencializada adtPersonaCredencializada;

	public AdtActNacimiento() {
	}

	public long getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(long numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

	public BigDecimal getCveEntRegistro() {
		return this.cveEntRegistro;
	}

	public void setCveEntRegistro(BigDecimal cveEntRegistro) {
		this.cveEntRegistro = cveEntRegistro;
	}

	public BigDecimal getCveMunRegistro() {
		return this.cveMunRegistro;
	}

	public void setCveMunRegistro(BigDecimal cveMunRegistro) {
		this.cveMunRegistro = cveMunRegistro;
	}

	public Date getFecRegistro() {
		return this.fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public String getNumRegActaAsig() {
		return this.numRegActaAsig;
	}

	public void setNumRegActaAsig(String numRegActaAsig) {
		this.numRegActaAsig = numRegActaAsig;
	}

	public String getRefIdLibro() {
		return this.refIdLibro;
	}

	public void setRefIdLibro(String refIdLibro) {
		this.refIdLibro = refIdLibro;
	}

	public AdtPersonaCredencializada getAdtPersonaCredencializada() {
		return this.adtPersonaCredencializada;
	}

	public void setAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		this.adtPersonaCredencializada = adtPersonaCredencializada;
	}

}