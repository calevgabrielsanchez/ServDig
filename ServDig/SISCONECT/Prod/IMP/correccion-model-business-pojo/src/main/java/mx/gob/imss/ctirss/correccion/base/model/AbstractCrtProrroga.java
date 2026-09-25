package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CRT_PRORROGA database table.
 * 
 */
@MappedSuperclass
public class AbstractCrtProrroga extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CVE_PRORROGA_GENERATOR", sequenceName = "CVE_SOLPRORROGA")
	@GeneratedValue(generator = "CVE_PRORROGA_GENERATOR")
	@Column(name = "CVE_SOLPRORROGA")
	private long cveSolprorroga;

	@Column(name = "CVE_SOLICITUDCORR")
	private BigDecimal cveSolicitudcorr;

	@Column(name = "CVE_USER")
	private String cveUser;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_ELABORASOLPRO")
	private Date fecElaborasolpro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREG")
	private Date fecFechareg;

	@Column(name = "TX_LUGAR")
	private String txLugar;

	@Column(name = "TX_MOTIVORAZON")
	private String txMotivorazon;

	@Column(name = "TX_REP_LEGAL_ELAB")
	private String txRepLegalElab;

	@Column(name = "CVE_STATUS")
	private Long cveStatus;

	/**
	 * @ManyToOne
	 * @JoinColumn(name="CVE_STATUS") private AbstractCrcStatus crcStatus;
	 */

	public AbstractCrtProrroga() {
	}

	public long getCveSolprorroga() {
		return this.cveSolprorroga;
	}

	public void setCveSolprorroga(long cveSolprorroga) {
		this.cveSolprorroga = cveSolprorroga;
	}

	public BigDecimal getCveSolicitudcorr() {
		return this.cveSolicitudcorr;
	}

	public void setCveSolicitudcorr(BigDecimal cveSolicitudcorr) {
		this.cveSolicitudcorr = cveSolicitudcorr;
	}

	public String getCveUser() {
		return this.cveUser;
	}

	public void setCveUser(String cveUser) {
		this.cveUser = cveUser;
	}

	public Date getFecElaborasolpro() {
		return this.fecElaborasolpro;
	}

	public void setFecElaborasolpro(Date fecElaborasolpro) {
		this.fecElaborasolpro = fecElaborasolpro;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public String getTxLugar() {
		return this.txLugar;
	}

	public void setTxLugar(String txLugar) {
		this.txLugar = txLugar;
	}

	public String getTxMotivorazon() {
		return this.txMotivorazon;
	}

	public void setTxMotivorazon(String txMotivorazon) {
		this.txMotivorazon = txMotivorazon;
	}

	public String getTxRepLegalElab() {
		return this.txRepLegalElab;
	}

	public void setTxRepLegalElab(String txRepLegalElab) {
		this.txRepLegalElab = txRepLegalElab;
	}

	public Long getCveStatus() {
		return cveStatus;
	}

	public void setCveStatus(Long cveStatus) {
		this.cveStatus = cveStatus;
	}

}