package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_DET_CERTIFICA_SINDO_ENV database table.
 * 
 */
@Entity
@Table(name="SPT_DET_CERTIFICA_SINDO_ENV")
@NamedQuery(name="SptDetCertificaSindoEnv.findAll", query="SELECT s FROM SptDetCertificaSindoEnv s")
public class SptDetCertificaSindoEnv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDETCERTIFICASINDOENV", sequenceName = "SEQ_SPTDETCERTIFICASINDOENV")
	@GeneratedValue(generator = "SEQ_SPTDETCERTIFICASINDOENV")
	@Column(name="CVE_ID_DET_CERTIFICA_SINDO_ENV")
	private long cveIdDetCertificaSindoEnv;

	@Column(name="CVE_DELEGACION_ENTRADA")
	private String cveDelegacionEntrada;

	@Column(name="CVE_PRESTACION_SOL_ENTRADA")
	private String cvePrestacionSolEntrada;

	@Column(name="CVE_SUBDELEGACION_ENTRADA")
	private String cveSubdelegacionEntrada;

	@Column(name="CVE_UMF_ENTRADA")
	private String cveUmfEntrada;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Column(name="FEC_NACIMIENTO_ENTRADA")
	private String fecNacimientoEntrada;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="FEC_SINIESTRO_ENTRADA")
	private String fecSiniestroEntrada;

	@Column(name="ID_DIGITO_VERIFICA_ENTRADA")
	private String idDigitoVerificaEntrada;

	@Column(name="ID_LOGIN_SINDO_ENTRADA")
	private String idLoginSindoEntrada;

	@Column(name="ID_LOGIN_SISTRAP_ENTRADA")
	private String idLoginSistrapEntrada;

	@Column(name="ID_MUNICIPIO_DELEGACION_ENTRAD")
	private String idMunicipioDelegacionEntrad;

	@Column(name="ID_SOLICITUD")
	private String idSolicitud;

	@Column(name="IND_CONFIRMAR_ENTRADA")
	private String indConfirmarEntrada;

	@Column(name="IND_INVALIDEZ_75MAS_ENTRADA")
	private String indInvalidez75masEntrada;

	@Column(name="NUM_DIAS_SUBSIDIADOS_ENTRADA")
	private String numDiasSubsidiadosEntrada;

	//bi-directional many-to-one association to SptEnvComunicEntidade
	@ManyToOne
	@JoinColumn(name="CVE_ID_ENV_COMUNIC_ENTIDADES")
	private SptEnvComunicEntidade sptEnvComunicEntidade;

	public SptDetCertificaSindoEnv() {
	}

	public long getCveIdDetCertificaSindoEnv() {
		return this.cveIdDetCertificaSindoEnv;
	}

	public void setCveIdDetCertificaSindoEnv(long cveIdDetCertificaSindoEnv) {
		this.cveIdDetCertificaSindoEnv = cveIdDetCertificaSindoEnv;
	}

	public String getCveDelegacionEntrada() {
		return this.cveDelegacionEntrada;
	}

	public void setCveDelegacionEntrada(String cveDelegacionEntrada) {
		this.cveDelegacionEntrada = cveDelegacionEntrada;
	}

	public String getCvePrestacionSolEntrada() {
		return this.cvePrestacionSolEntrada;
	}

	public void setCvePrestacionSolEntrada(String cvePrestacionSolEntrada) {
		this.cvePrestacionSolEntrada = cvePrestacionSolEntrada;
	}

	public String getCveSubdelegacionEntrada() {
		return this.cveSubdelegacionEntrada;
	}

	public void setCveSubdelegacionEntrada(String cveSubdelegacionEntrada) {
		this.cveSubdelegacionEntrada = cveSubdelegacionEntrada;
	}

	public String getCveUmfEntrada() {
		return this.cveUmfEntrada;
	}

	public void setCveUmfEntrada(String cveUmfEntrada) {
		this.cveUmfEntrada = cveUmfEntrada;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public String getFecNacimientoEntrada() {
		return this.fecNacimientoEntrada;
	}

	public void setFecNacimientoEntrada(String fecNacimientoEntrada) {
		this.fecNacimientoEntrada = fecNacimientoEntrada;
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

	public String getFecSiniestroEntrada() {
		return this.fecSiniestroEntrada;
	}

	public void setFecSiniestroEntrada(String fecSiniestroEntrada) {
		this.fecSiniestroEntrada = fecSiniestroEntrada;
	}

	public String getIdDigitoVerificaEntrada() {
		return this.idDigitoVerificaEntrada;
	}

	public void setIdDigitoVerificaEntrada(String idDigitoVerificaEntrada) {
		this.idDigitoVerificaEntrada = idDigitoVerificaEntrada;
	}

	public String getIdLoginSindoEntrada() {
		return this.idLoginSindoEntrada;
	}

	public void setIdLoginSindoEntrada(String idLoginSindoEntrada) {
		this.idLoginSindoEntrada = idLoginSindoEntrada;
	}

	public String getIdLoginSistrapEntrada() {
		return this.idLoginSistrapEntrada;
	}

	public void setIdLoginSistrapEntrada(String idLoginSistrapEntrada) {
		this.idLoginSistrapEntrada = idLoginSistrapEntrada;
	}

	public String getIdMunicipioDelegacionEntrad() {
		return this.idMunicipioDelegacionEntrad;
	}

	public void setIdMunicipioDelegacionEntrad(String idMunicipioDelegacionEntrad) {
		this.idMunicipioDelegacionEntrad = idMunicipioDelegacionEntrad;
	}

	public String getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getIndConfirmarEntrada() {
		return this.indConfirmarEntrada;
	}

	public void setIndConfirmarEntrada(String indConfirmarEntrada) {
		this.indConfirmarEntrada = indConfirmarEntrada;
	}

	public String getIndInvalidez75masEntrada() {
		return this.indInvalidez75masEntrada;
	}

	public void setIndInvalidez75masEntrada(String indInvalidez75masEntrada) {
		this.indInvalidez75masEntrada = indInvalidez75masEntrada;
	}

	public String getNumDiasSubsidiadosEntrada() {
		return this.numDiasSubsidiadosEntrada;
	}

	public void setNumDiasSubsidiadosEntrada(String numDiasSubsidiadosEntrada) {
		this.numDiasSubsidiadosEntrada = numDiasSubsidiadosEntrada;
	}

	public SptEnvComunicEntidade getSptEnvComunicEntidade() {
		return this.sptEnvComunicEntidade;
	}

	public void setSptEnvComunicEntidade(SptEnvComunicEntidade sptEnvComunicEntidade) {
		this.sptEnvComunicEntidade = sptEnvComunicEntidade;
	}

}