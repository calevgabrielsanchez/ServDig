package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_RESP_COMUNIC_ENTIDADES database table.
 * 
 */
@Entity
@Table(name="SPT_RESP_COMUNIC_ENTIDADES")
@NamedQuery(name="SptRespComunicEntidade.findAll", query="SELECT s FROM SptRespComunicEntidade s")
public class SptRespComunicEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTRESPCOMUNICENTIDADES", sequenceName = "SEQ_SPTRESPCOMUNICENTIDADES")
	@GeneratedValue(generator = "SEQ_SPTRESPCOMUNICENTIDADES")
	@Column(name="CVE_ID_RESP_COMUNIC_ENTIDADES")
	private long cveIdRespComunicEntidades;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to AptDetPrevalidSal
	@OneToMany(mappedBy="sptRespComunicEntidade")
	private List<AptDetPrevalidSal> aptDetPrevalidSals;

	//bi-directional many-to-one association to SptDetCertificaSindoSal
	@OneToMany(mappedBy="sptRespComunicEntidade")
	private List<SptDetCertificaSindoSal> sptDetCertificaSindoSals;

	//bi-directional many-to-one association to SptDetEnvioProcesarSal
	@OneToMany(mappedBy="sptRespComunicEntidade")
	private List<SptDetEnvioProcesarSal> sptDetEnvioProcesarSals;

	//bi-directional many-to-one association to SptDetPrevalidaSindoSal
	@OneToMany(mappedBy="sptRespComunicEntidade")
	private List<SptDetPrevalidaSindoSal> sptDetPrevalidaSindoSals;

	//bi-directional many-to-one association to SptDetRespProcesarSal
	@OneToMany(mappedBy="sptRespComunicEntidade")
	private List<SptDetRespProcesarSal> sptDetRespProcesarSals;

	//bi-directional many-to-one association to SptEnvComunicEntidade
	@ManyToOne
	@JoinColumn(name="CVE_ID_ENV_COMUNIC_ENTIDADES")
	private SptEnvComunicEntidade sptEnvComunicEntidade;

	public SptRespComunicEntidade() {
	}

	public long getCveIdRespComunicEntidades() {
		return this.cveIdRespComunicEntidades;
	}

	public void setCveIdRespComunicEntidades(long cveIdRespComunicEntidades) {
		this.cveIdRespComunicEntidades = cveIdRespComunicEntidades;
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

	public List<AptDetPrevalidSal> getAptDetPrevalidSals() {
		return this.aptDetPrevalidSals;
	}

	public void setAptDetPrevalidSals(List<AptDetPrevalidSal> aptDetPrevalidSals) {
		this.aptDetPrevalidSals = aptDetPrevalidSals;
	}

	public AptDetPrevalidSal addAptDetPrevalidSal(AptDetPrevalidSal aptDetPrevalidSal) {
		getAptDetPrevalidSals().add(aptDetPrevalidSal);
		aptDetPrevalidSal.setSptRespComunicEntidade(this);

		return aptDetPrevalidSal;
	}

	public AptDetPrevalidSal removeAptDetPrevalidSal(AptDetPrevalidSal aptDetPrevalidSal) {
		getAptDetPrevalidSals().remove(aptDetPrevalidSal);
		aptDetPrevalidSal.setSptRespComunicEntidade(null);

		return aptDetPrevalidSal;
	}

	public List<SptDetCertificaSindoSal> getSptDetCertificaSindoSals() {
		return this.sptDetCertificaSindoSals;
	}

	public void setSptDetCertificaSindoSals(List<SptDetCertificaSindoSal> sptDetCertificaSindoSals) {
		this.sptDetCertificaSindoSals = sptDetCertificaSindoSals;
	}

	public SptDetCertificaSindoSal addSptDetCertificaSindoSal(SptDetCertificaSindoSal sptDetCertificaSindoSal) {
		getSptDetCertificaSindoSals().add(sptDetCertificaSindoSal);
		sptDetCertificaSindoSal.setSptRespComunicEntidade(this);

		return sptDetCertificaSindoSal;
	}

	public SptDetCertificaSindoSal removeSptDetCertificaSindoSal(SptDetCertificaSindoSal sptDetCertificaSindoSal) {
		getSptDetCertificaSindoSals().remove(sptDetCertificaSindoSal);
		sptDetCertificaSindoSal.setSptRespComunicEntidade(null);

		return sptDetCertificaSindoSal;
	}

	public List<SptDetEnvioProcesarSal> getSptDetEnvioProcesarSals() {
		return this.sptDetEnvioProcesarSals;
	}

	public void setSptDetEnvioProcesarSals(List<SptDetEnvioProcesarSal> sptDetEnvioProcesarSals) {
		this.sptDetEnvioProcesarSals = sptDetEnvioProcesarSals;
	}

	public SptDetEnvioProcesarSal addSptDetEnvioProcesarSal(SptDetEnvioProcesarSal sptDetEnvioProcesarSal) {
		getSptDetEnvioProcesarSals().add(sptDetEnvioProcesarSal);
		sptDetEnvioProcesarSal.setSptRespComunicEntidade(this);

		return sptDetEnvioProcesarSal;
	}

	public SptDetEnvioProcesarSal removeSptDetEnvioProcesarSal(SptDetEnvioProcesarSal sptDetEnvioProcesarSal) {
		getSptDetEnvioProcesarSals().remove(sptDetEnvioProcesarSal);
		sptDetEnvioProcesarSal.setSptRespComunicEntidade(null);

		return sptDetEnvioProcesarSal;
	}

	public List<SptDetPrevalidaSindoSal> getSptDetPrevalidaSindoSals() {
		return this.sptDetPrevalidaSindoSals;
	}

	public void setSptDetPrevalidaSindoSals(List<SptDetPrevalidaSindoSal> sptDetPrevalidaSindoSals) {
		this.sptDetPrevalidaSindoSals = sptDetPrevalidaSindoSals;
	}

	public SptDetPrevalidaSindoSal addSptDetPrevalidaSindoSal(SptDetPrevalidaSindoSal sptDetPrevalidaSindoSal) {
		getSptDetPrevalidaSindoSals().add(sptDetPrevalidaSindoSal);
		sptDetPrevalidaSindoSal.setSptRespComunicEntidade(this);

		return sptDetPrevalidaSindoSal;
	}

	public SptDetPrevalidaSindoSal removeSptDetPrevalidaSindoSal(SptDetPrevalidaSindoSal sptDetPrevalidaSindoSal) {
		getSptDetPrevalidaSindoSals().remove(sptDetPrevalidaSindoSal);
		sptDetPrevalidaSindoSal.setSptRespComunicEntidade(null);

		return sptDetPrevalidaSindoSal;
	}

	public List<SptDetRespProcesarSal> getSptDetRespProcesarSals() {
		return this.sptDetRespProcesarSals;
	}

	public void setSptDetRespProcesarSals(List<SptDetRespProcesarSal> sptDetRespProcesarSals) {
		this.sptDetRespProcesarSals = sptDetRespProcesarSals;
	}

	public SptDetRespProcesarSal addSptDetRespProcesarSal(SptDetRespProcesarSal sptDetRespProcesarSal) {
		getSptDetRespProcesarSals().add(sptDetRespProcesarSal);
		sptDetRespProcesarSal.setSptRespComunicEntidade(this);

		return sptDetRespProcesarSal;
	}

	public SptDetRespProcesarSal removeSptDetRespProcesarSal(SptDetRespProcesarSal sptDetRespProcesarSal) {
		getSptDetRespProcesarSals().remove(sptDetRespProcesarSal);
		sptDetRespProcesarSal.setSptRespComunicEntidade(null);

		return sptDetRespProcesarSal;
	}

	public SptEnvComunicEntidade getSptEnvComunicEntidade() {
		return this.sptEnvComunicEntidade;
	}

	public void setSptEnvComunicEntidade(SptEnvComunicEntidade sptEnvComunicEntidade) {
		this.sptEnvComunicEntidade = sptEnvComunicEntidade;
	}

}