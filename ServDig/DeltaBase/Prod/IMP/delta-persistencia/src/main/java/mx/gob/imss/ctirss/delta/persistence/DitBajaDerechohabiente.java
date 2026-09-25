package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIT_BAJA_DERECHOHABIENTE")
public class DitBajaDerechohabiente implements Serializable {

	private static final long serialVersionUID = -6015681360294915236L;

	@Id
	@Column(name = "CVE_ID_BAJA")
	@SequenceGenerator(name = "SEQ_DITBAJADERECHOHABIENTE", sequenceName = "SEQ_DITBAJADERECHOHABIENTE")
	@GeneratedValue(generator = "SEQ_DITBAJADERECHOHABIENTE")
	private Long cveIdBaja;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizaco;

	@Column(name = "CVE_ID_PERSONA_INTEGRANTE")
	private Long cveIdPersonaIntegrante;

	@Column(name = "CVE_ID_ASIGNACION_NSS")
	private Long cveIdAsignacionNSS;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_TIPO_BAJA_DER")
	private DicTipoBajaDerechohabiente dicTipoBajaDerechohabiente;

	@Column(name = "IND_BAJA_ACTIVA")
	private Long indBajaActiva;

	@Column(name = "CVE_ID_TRAMITE")
	private Long cveIdTramite;
	
	@Column(name = "MATRICULA")
	private String matricula;
	
	@Column(name = "MOTIVO")
	private String motivo;
	
	@Column(name = "FUNDAMENTO_LEGAL")
	private String fundamentoLegal;

	public Long getCveIdBaja() {
		return cveIdBaja;
	}

	public void setCveIdBaja(Long cveIdBaja) {
		this.cveIdBaja = cveIdBaja;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecRegistroActualizaco() {
		return fecRegistroActualizaco;
	}

	public void setFecRegistroActualizaco(Date fecRegistroActualizaco) {
		this.fecRegistroActualizaco = fecRegistroActualizaco;
	}

	public Long getCveIdPersonaIntegrante() {
		return cveIdPersonaIntegrante;
	}

	public void setCveIdPersonaIntegrante(Long cveIdPersonaIntegrante) {
		this.cveIdPersonaIntegrante = cveIdPersonaIntegrante;
	}

	public Long getCveIdAsignacionNSS() {
		return cveIdAsignacionNSS;
	}

	public void setCveIdAsignacionNSS(Long cveIdAsignacionNSS) {
		this.cveIdAsignacionNSS = cveIdAsignacionNSS;
	}

	public DicTipoBajaDerechohabiente getDicTipoBajaDerechohabiente() {
		return dicTipoBajaDerechohabiente;
	}

	public void setDicTipoBajaDerechohabiente(
			DicTipoBajaDerechohabiente dicTipoBajaDerechohabiente) {
		this.dicTipoBajaDerechohabiente = dicTipoBajaDerechohabiente;
	}

	public Long getIndBajaActiva() {
		return indBajaActiva;
	}

	public void setIndBajaActiva(Long indBajaActiva) {
		this.indBajaActiva = indBajaActiva;
	}

	public Long getCveIdTramite() {
		return cveIdTramite;
	}

	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getFundamentoLegal() {
		return fundamentoLegal;
	}

	public void setFundamentoLegal(String fundamentoLegal) {
		this.fundamentoLegal = fundamentoLegal;
	}

}