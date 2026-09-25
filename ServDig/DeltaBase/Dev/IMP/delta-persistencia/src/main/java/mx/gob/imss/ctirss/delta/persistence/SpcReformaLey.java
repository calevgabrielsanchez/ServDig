package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SptBenefPensDetEstud;
import mx.gob.imss.ctirss.delta.persistence.SptCabeceraSolicPension;
import mx.gob.imss.ctirss.delta.persistence.SptPension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * The persistent class for the SPC_REFORMA_LEY database table.
 * 
 */
@Entity
@Table(name = "SPC_REFORMA_LEY")
public class SpcReformaLey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_REFORMA_LEY")
	private String idReformaLey;

	@Column(name = "CVE_TIPO_CONCEPTO")
	private String cveTipoConcepto;

	@Column(name = "DES_MENSAJE")
	private String desMensaje;

	@Column(name = "DES_REFORMA_LEY")
	private String desReformaLey;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_FIN_VIGENCIA")
	private Date fecFinVigencia;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_INICIO_VIGENCIA")
	private Date fecInicioVigencia;

	@Column(name = "IMP_INCREMENTO")
	private BigDecimal impIncremento;

	@Column(name = "IND_VIGENTE")
	private String indVigente;

	@Column(name = "POR_INCREMENTO")
	private BigDecimal porIncremento;

	// bi-directional many-to-one association to SptBenefPensDetEstud
	@OneToMany(mappedBy = "spcReformaLey")
	private Set<SptBenefPensDetEstud> sptBenefPensDetEstuds;

	// bi-directional many-to-one association to SptCabeceraSolicPension
	// @OneToMany(mappedBy="spcReformaLey") FIXME no esta mapeada esta relacion
	// private Set<SptCabeceraSolicPension> sptCabeceraSolicPensions;

	// bi-directional many-to-one association to SptComponenteMov
	@OneToMany(mappedBy = "spcReformaLey")
	private Set<SptComponenteMov> sptComponenteMovs;

	// bi-directional many-to-one association to SptPension
	// @OneToMany(mappedBy="spcReformaLey") FIXME no esta mapeada esta relacion
	// private Set<SptPension> sptPensions;

	// bi-directional many-to-one association to SptPensionMov
	@OneToMany(mappedBy = "spcReformaLey")
	private Set<SptPensionMov> sptPensionMovs;

	public SpcReformaLey() {
	}

	public String getIdReformaLey() {
		return this.idReformaLey;
	}

	public void setIdReformaLey(String idReformaLey) {
		this.idReformaLey = idReformaLey;
	}

	public String getCveTipoConcepto() {
		return this.cveTipoConcepto;
	}

	public void setCveTipoConcepto(String cveTipoConcepto) {
		this.cveTipoConcepto = cveTipoConcepto;
	}

	public String getDesMensaje() {
		return this.desMensaje;
	}

	public void setDesMensaje(String desMensaje) {
		this.desMensaje = desMensaje;
	}

	public String getDesReformaLey() {
		return this.desReformaLey;
	}

	public void setDesReformaLey(String desReformaLey) {
		this.desReformaLey = desReformaLey;
	}

	public Date getFecFinVigencia() {
		return this.fecFinVigencia;
	}

	public void setFecFinVigencia(Date fecFinVigencia) {
		this.fecFinVigencia = fecFinVigencia;
	}

	public Date getFecInicioVigencia() {
		return this.fecInicioVigencia;
	}

	public void setFecInicioVigencia(Date fecInicioVigencia) {
		this.fecInicioVigencia = fecInicioVigencia;
	}

	public BigDecimal getImpIncremento() {
		return this.impIncremento;
	}

	public void setImpIncremento(BigDecimal impIncremento) {
		this.impIncremento = impIncremento;
	}

	public String getIndVigente() {
		return this.indVigente;
	}

	public void setIndVigente(String indVigente) {
		this.indVigente = indVigente;
	}

	public BigDecimal getPorIncremento() {
		return this.porIncremento;
	}

	public void setPorIncremento(BigDecimal porIncremento) {
		this.porIncremento = porIncremento;
	}

	public Set<SptBenefPensDetEstud> getSptBenefPensDetEstuds() {
		return this.sptBenefPensDetEstuds;
	}

	public void setSptBenefPensDetEstuds(
			Set<SptBenefPensDetEstud> sptBenefPensDetEstuds) {
		this.sptBenefPensDetEstuds = sptBenefPensDetEstuds;
	}

	// public Set<SptCabeceraSolicPension> getSptCabeceraSolicPensions() {
	// return this.sptCabeceraSolicPensions;
	// }
	//
	// public void setSptCabeceraSolicPensions(Set<SptCabeceraSolicPension>
	// sptCabeceraSolicPensions) {
	// this.sptCabeceraSolicPensions = sptCabeceraSolicPensions;
	// }

	public Set<SptComponenteMov> getSptComponenteMovs() {
		return this.sptComponenteMovs;
	}

	public void setSptComponenteMovs(Set<SptComponenteMov> sptComponenteMovs) {
		this.sptComponenteMovs = sptComponenteMovs;
	}

	// public Set<SptPension> getSptPensions() {
	// return this.sptPensions;
	// }
	//
	// public void setSptPensions(Set<SptPension> sptPensions) {
	// this.sptPensions = sptPensions;
	// }

	public Set<SptPensionMov> getSptPensionMovs() {
		return this.sptPensionMovs;
	}

	public void setSptPensionMovs(Set<SptPensionMov> sptPensionMovs) {
		this.sptPensionMovs = sptPensionMovs;
	}

}