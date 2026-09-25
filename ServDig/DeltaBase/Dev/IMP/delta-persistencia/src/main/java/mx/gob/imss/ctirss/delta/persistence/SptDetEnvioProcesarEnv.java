package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_DET_ENVIO_PROCESAR_ENV database table.
 * 
 */
@Entity
@Table(name="SPT_DET_ENVIO_PROCESAR_ENV")
@NamedQuery(name="SptDetEnvioProcesarEnv.findAll", query="SELECT s FROM SptDetEnvioProcesarEnv s")
public class SptDetEnvioProcesarEnv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDETENVIOPROCESARENV", sequenceName = "SEQ_SPTDETENVIOPROCESARENV")
	@GeneratedValue(generator = "SEQ_SPTDETENVIOPROCESARENV")
	@Column(name="CVE_ID_DET_ENVIO_PROCEASAR_ENV")
	private long cveIdDetEnvioProceasarEnv;

	@Column(name="CVE_CURP")
	private String cveCurp;

	@Column(name="CVE_DELEGACION")
	private String cveDelegacion;

	@Column(name="CVE_SUBDELEGACION")
	private String cveSubdelegacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_LOGIN")
	private String idLogin;

	@Column(name="ID_TIPO_PENSION")
	private String idTipoPension;

	@Column(name="IND_PORTABILIDAD")
	private String indPortabilidad;

	@Column(name="IND_SALDO_FIP")
	private String indSaldoFip;

	@Column(name="NUM_CAMBIO_DIAGNOSTICO")
	private BigDecimal numCambioDiagnostico;

	@Column(name="STP_FEC_ENVIO")
	private Timestamp stpFecEnvio;

	@Column(name="STP_FEC_INICIO_PENSION")
	private Timestamp stpFecInicioPension;

	@Column(name="STP_FEC_SOLICITUD")
	private Timestamp stpFecSolicitud;

	//bi-directional many-to-one association to SptEnvComunicEntidade
	@ManyToOne
	@JoinColumn(name="CVE_ID_ENV_COMUNIC_ENTIDADES")
	private SptEnvComunicEntidade sptEnvComunicEntidade;

	public SptDetEnvioProcesarEnv() {
	}

	public long getCveIdDetEnvioProceasarEnv() {
		return this.cveIdDetEnvioProceasarEnv;
	}

	public void setCveIdDetEnvioProceasarEnv(long cveIdDetEnvioProceasarEnv) {
		this.cveIdDetEnvioProceasarEnv = cveIdDetEnvioProceasarEnv;
	}

	public String getCveCurp() {
		return this.cveCurp;
	}

	public void setCveCurp(String cveCurp) {
		this.cveCurp = cveCurp;
	}

	public String getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}

	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
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

	public String getIdLogin() {
		return this.idLogin;
	}

	public void setIdLogin(String idLogin) {
		this.idLogin = idLogin;
	}

	public String getIdTipoPension() {
		return this.idTipoPension;
	}

	public void setIdTipoPension(String idTipoPension) {
		this.idTipoPension = idTipoPension;
	}

	public String getIndPortabilidad() {
		return this.indPortabilidad;
	}

	public void setIndPortabilidad(String indPortabilidad) {
		this.indPortabilidad = indPortabilidad;
	}

	public String getIndSaldoFip() {
		return this.indSaldoFip;
	}

	public void setIndSaldoFip(String indSaldoFip) {
		this.indSaldoFip = indSaldoFip;
	}

	public BigDecimal getNumCambioDiagnostico() {
		return this.numCambioDiagnostico;
	}

	public void setNumCambioDiagnostico(BigDecimal numCambioDiagnostico) {
		this.numCambioDiagnostico = numCambioDiagnostico;
	}

	public Timestamp getStpFecEnvio() {
		return this.stpFecEnvio;
	}

	public void setStpFecEnvio(Timestamp stpFecEnvio) {
		this.stpFecEnvio = stpFecEnvio;
	}

	public Timestamp getStpFecInicioPension() {
		return this.stpFecInicioPension;
	}

	public void setStpFecInicioPension(Timestamp stpFecInicioPension) {
		this.stpFecInicioPension = stpFecInicioPension;
	}

	public Timestamp getStpFecSolicitud() {
		return this.stpFecSolicitud;
	}

	public void setStpFecSolicitud(Timestamp stpFecSolicitud) {
		this.stpFecSolicitud = stpFecSolicitud;
	}

	public SptEnvComunicEntidade getSptEnvComunicEntidade() {
		return this.sptEnvComunicEntidade;
	}

	public void setSptEnvComunicEntidade(SptEnvComunicEntidade sptEnvComunicEntidade) {
		this.sptEnvComunicEntidade = sptEnvComunicEntidade;
	}

}