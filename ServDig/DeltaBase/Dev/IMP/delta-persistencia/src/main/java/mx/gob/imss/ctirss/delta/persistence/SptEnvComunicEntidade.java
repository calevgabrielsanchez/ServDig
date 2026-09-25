package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_ENV_COMUNIC_ENTIDADES database table.
 * 
 */
@Entity
@Table(name="SPT_ENV_COMUNIC_ENTIDADES")
@NamedQuery(name="SptEnvComunicEntidade.findAll", query="SELECT s FROM SptEnvComunicEntidade s")
public class SptEnvComunicEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTENVCOMUNICENTIDADES", sequenceName = "SEQ_SPTENVCOMUNICENTIDADES")
	@GeneratedValue(generator = "SEQ_SPTENVCOMUNICENTIDADES")
	@Column(name="CVE_ID_ENV_COMUNIC_ENTIDADES")
	private long cveIdEnvComunicEntidades;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="TIP_COMUNICACION")
	private BigDecimal tipComunicacion;

	//bi-directional many-to-one association to AptDetPrevalidBenefEnv
	@OneToMany(mappedBy="sptEnvComunicEntidade")
	private List<AptDetPrevalidBenefEnv> aptDetPrevalidBenefEnvs;

	//bi-directional many-to-one association to AptDetProspPrevalidEnv
	@OneToMany(mappedBy="sptEnvComunicEntidade")
	private List<AptDetProspPrevalidEnv> aptDetProspPrevalidEnvs;

	//bi-directional many-to-one association to SptDetCertificaSindoEnv
	@OneToMany(mappedBy="sptEnvComunicEntidade")
	private List<SptDetCertificaSindoEnv> sptDetCertificaSindoEnvs;

	//bi-directional many-to-one association to SptDetEnvioProcesarEnv
	@OneToMany(mappedBy="sptEnvComunicEntidade")
	private List<SptDetEnvioProcesarEnv> sptDetEnvioProcesarEnvs;

	//bi-directional many-to-one association to SptDetPrevalidaSindoEnv
	@OneToMany(mappedBy="sptEnvComunicEntidade")
	private List<SptDetPrevalidaSindoEnv> sptDetPrevalidaSindoEnvs;

	//bi-directional many-to-one association to SptTramitePension
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

	//bi-directional many-to-one association to SptRespComunicEntidade
	@OneToMany(mappedBy="sptEnvComunicEntidade")
	private List<SptRespComunicEntidade> sptRespComunicEntidades;

	public SptEnvComunicEntidade() {
	}

	public long getCveIdEnvComunicEntidades() {
		return this.cveIdEnvComunicEntidades;
	}

	public void setCveIdEnvComunicEntidades(long cveIdEnvComunicEntidades) {
		this.cveIdEnvComunicEntidades = cveIdEnvComunicEntidades;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public BigDecimal getTipComunicacion() {
		return this.tipComunicacion;
	}

	public void setTipComunicacion(BigDecimal tipComunicacion) {
		this.tipComunicacion = tipComunicacion;
	}

	public List<AptDetPrevalidBenefEnv> getAptDetPrevalidBenefEnvs() {
		return this.aptDetPrevalidBenefEnvs;
	}

	public void setAptDetPrevalidBenefEnvs(List<AptDetPrevalidBenefEnv> aptDetPrevalidBenefEnvs) {
		this.aptDetPrevalidBenefEnvs = aptDetPrevalidBenefEnvs;
	}

	public AptDetPrevalidBenefEnv addAptDetPrevalidBenefEnv(AptDetPrevalidBenefEnv aptDetPrevalidBenefEnv) {
		getAptDetPrevalidBenefEnvs().add(aptDetPrevalidBenefEnv);
		aptDetPrevalidBenefEnv.setSptEnvComunicEntidade(this);

		return aptDetPrevalidBenefEnv;
	}

	public AptDetPrevalidBenefEnv removeAptDetPrevalidBenefEnv(AptDetPrevalidBenefEnv aptDetPrevalidBenefEnv) {
		getAptDetPrevalidBenefEnvs().remove(aptDetPrevalidBenefEnv);
		aptDetPrevalidBenefEnv.setSptEnvComunicEntidade(null);

		return aptDetPrevalidBenefEnv;
	}

	public List<AptDetProspPrevalidEnv> getAptDetProspPrevalidEnvs() {
		return this.aptDetProspPrevalidEnvs;
	}

	public void setAptDetProspPrevalidEnvs(List<AptDetProspPrevalidEnv> aptDetProspPrevalidEnvs) {
		this.aptDetProspPrevalidEnvs = aptDetProspPrevalidEnvs;
	}

	public AptDetProspPrevalidEnv addAptDetProspPrevalidEnv(AptDetProspPrevalidEnv aptDetProspPrevalidEnv) {
		getAptDetProspPrevalidEnvs().add(aptDetProspPrevalidEnv);
		aptDetProspPrevalidEnv.setSptEnvComunicEntidade(this);

		return aptDetProspPrevalidEnv;
	}

	public AptDetProspPrevalidEnv removeAptDetProspPrevalidEnv(AptDetProspPrevalidEnv aptDetProspPrevalidEnv) {
		getAptDetProspPrevalidEnvs().remove(aptDetProspPrevalidEnv);
		aptDetProspPrevalidEnv.setSptEnvComunicEntidade(null);

		return aptDetProspPrevalidEnv;
	}

	public List<SptDetCertificaSindoEnv> getSptDetCertificaSindoEnvs() {
		return this.sptDetCertificaSindoEnvs;
	}

	public void setSptDetCertificaSindoEnvs(List<SptDetCertificaSindoEnv> sptDetCertificaSindoEnvs) {
		this.sptDetCertificaSindoEnvs = sptDetCertificaSindoEnvs;
	}

	public SptDetCertificaSindoEnv addSptDetCertificaSindoEnv(SptDetCertificaSindoEnv sptDetCertificaSindoEnv) {
		getSptDetCertificaSindoEnvs().add(sptDetCertificaSindoEnv);
		sptDetCertificaSindoEnv.setSptEnvComunicEntidade(this);

		return sptDetCertificaSindoEnv;
	}

	public SptDetCertificaSindoEnv removeSptDetCertificaSindoEnv(SptDetCertificaSindoEnv sptDetCertificaSindoEnv) {
		getSptDetCertificaSindoEnvs().remove(sptDetCertificaSindoEnv);
		sptDetCertificaSindoEnv.setSptEnvComunicEntidade(null);

		return sptDetCertificaSindoEnv;
	}

	public List<SptDetEnvioProcesarEnv> getSptDetEnvioProcesarEnvs() {
		return this.sptDetEnvioProcesarEnvs;
	}

	public void setSptDetEnvioProcesarEnvs(List<SptDetEnvioProcesarEnv> sptDetEnvioProcesarEnvs) {
		this.sptDetEnvioProcesarEnvs = sptDetEnvioProcesarEnvs;
	}

	public SptDetEnvioProcesarEnv addSptDetEnvioProcesarEnv(SptDetEnvioProcesarEnv sptDetEnvioProcesarEnv) {
		getSptDetEnvioProcesarEnvs().add(sptDetEnvioProcesarEnv);
		sptDetEnvioProcesarEnv.setSptEnvComunicEntidade(this);

		return sptDetEnvioProcesarEnv;
	}

	public SptDetEnvioProcesarEnv removeSptDetEnvioProcesarEnv(SptDetEnvioProcesarEnv sptDetEnvioProcesarEnv) {
		getSptDetEnvioProcesarEnvs().remove(sptDetEnvioProcesarEnv);
		sptDetEnvioProcesarEnv.setSptEnvComunicEntidade(null);

		return sptDetEnvioProcesarEnv;
	}

	public List<SptDetPrevalidaSindoEnv> getSptDetPrevalidaSindoEnvs() {
		return this.sptDetPrevalidaSindoEnvs;
	}

	public void setSptDetPrevalidaSindoEnvs(List<SptDetPrevalidaSindoEnv> sptDetPrevalidaSindoEnvs) {
		this.sptDetPrevalidaSindoEnvs = sptDetPrevalidaSindoEnvs;
	}

	public SptDetPrevalidaSindoEnv addSptDetPrevalidaSindoEnv(SptDetPrevalidaSindoEnv sptDetPrevalidaSindoEnv) {
		getSptDetPrevalidaSindoEnvs().add(sptDetPrevalidaSindoEnv);
		sptDetPrevalidaSindoEnv.setSptEnvComunicEntidade(this);

		return sptDetPrevalidaSindoEnv;
	}

	public SptDetPrevalidaSindoEnv removeSptDetPrevalidaSindoEnv(SptDetPrevalidaSindoEnv sptDetPrevalidaSindoEnv) {
		getSptDetPrevalidaSindoEnvs().remove(sptDetPrevalidaSindoEnv);
		sptDetPrevalidaSindoEnv.setSptEnvComunicEntidade(null);

		return sptDetPrevalidaSindoEnv;
	}

	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}

	public List<SptRespComunicEntidade> getSptRespComunicEntidades() {
		return this.sptRespComunicEntidades;
	}

	public void setSptRespComunicEntidades(List<SptRespComunicEntidade> sptRespComunicEntidades) {
		this.sptRespComunicEntidades = sptRespComunicEntidades;
	}

	public SptRespComunicEntidade addSptRespComunicEntidade(SptRespComunicEntidade sptRespComunicEntidade) {
		getSptRespComunicEntidades().add(sptRespComunicEntidade);
		sptRespComunicEntidade.setSptEnvComunicEntidade(this);

		return sptRespComunicEntidade;
	}

	public SptRespComunicEntidade removeSptRespComunicEntidade(SptRespComunicEntidade sptRespComunicEntidade) {
		getSptRespComunicEntidades().remove(sptRespComunicEntidade);
		sptRespComunicEntidade.setSptEnvComunicEntidade(null);

		return sptRespComunicEntidade;
	}

}