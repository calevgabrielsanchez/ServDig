package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APT_DET_PREVALID_BENEF_ENV database table.
 * 
 */
@Entity
@Table(name="APT_DET_PREVALID_BENEF_ENV")
@NamedQuery(name="AptDetPrevalidBenefEnv.findAll", query="SELECT a FROM AptDetPrevalidBenefEnv a")
public class AptDetPrevalidBenefEnv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APT_DET_PREVALID_BENEF_ENV_CVEIDDETPREVALIDBENEFENV_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APT_DET_PREVALID_BENEF_ENV_CVEIDDETPREVALIDBENEFENV_GENERATOR")
	@Column(name="CVE_ID_DET_PREVALID_BENEF_ENV")
	private long cveIdDetPrevalidBenefEnv;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ENVIO")
	private Date fecEnvio;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_DERECHOS")
	private Date fecInicioDerechos;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PAGO")
	private Date fecInicioPago;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_NACIMIENTO")
	private Date fecNacimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_VENCIMIENTO")
	private Date fecVencimiento;

	@Column(name="FOLIO_IDENTIFICADOR")
	private String folioIdentificador;

	@Column(name="ID_LOGIN")
	private String idLogin;

	@Column(name="NOMBRE_COMPONENTE")
	private String nombreComponente;

	@Column(name="NUM_ENVIO")
	private BigDecimal numEnvio;

	private String orfandad;

	private String parentesco;

	private String sexo;

	//bi-directional many-to-one association to SptBeneficiarioSolicitud
	@ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_SOLICITUD")
	private SptBeneficiarioSolicitud sptBeneficiarioSolicitud;

	//bi-directional many-to-one association to SptEnvComunicEntidade
	@ManyToOne
	@JoinColumn(name="CVE_ID_ENV_COMUNIC_ENTIDADES")
	private SptEnvComunicEntidade sptEnvComunicEntidade;

	public AptDetPrevalidBenefEnv() {
	}

	public long getCveIdDetPrevalidBenefEnv() {
		return this.cveIdDetPrevalidBenefEnv;
	}

	public void setCveIdDetPrevalidBenefEnv(long cveIdDetPrevalidBenefEnv) {
		this.cveIdDetPrevalidBenefEnv = cveIdDetPrevalidBenefEnv;
	}

	public Date getFecEnvio() {
		return this.fecEnvio;
	}

	public void setFecEnvio(Date fecEnvio) {
		this.fecEnvio = fecEnvio;
	}

	public Date getFecInicioDerechos() {
		return this.fecInicioDerechos;
	}

	public void setFecInicioDerechos(Date fecInicioDerechos) {
		this.fecInicioDerechos = fecInicioDerechos;
	}

	public Date getFecInicioPago() {
		return this.fecInicioPago;
	}

	public void setFecInicioPago(Date fecInicioPago) {
		this.fecInicioPago = fecInicioPago;
	}

	public Date getFecNacimiento() {
		return this.fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
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

	public Date getFecVencimiento() {
		return this.fecVencimiento;
	}

	public void setFecVencimiento(Date fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}

	public String getFolioIdentificador() {
		return this.folioIdentificador;
	}

	public void setFolioIdentificador(String folioIdentificador) {
		this.folioIdentificador = folioIdentificador;
	}

	public String getIdLogin() {
		return this.idLogin;
	}

	public void setIdLogin(String idLogin) {
		this.idLogin = idLogin;
	}

	public String getNombreComponente() {
		return this.nombreComponente;
	}

	public void setNombreComponente(String nombreComponente) {
		this.nombreComponente = nombreComponente;
	}

	public BigDecimal getNumEnvio() {
		return this.numEnvio;
	}

	public void setNumEnvio(BigDecimal numEnvio) {
		this.numEnvio = numEnvio;
	}

	public String getOrfandad() {
		return this.orfandad;
	}

	public void setOrfandad(String orfandad) {
		this.orfandad = orfandad;
	}

	public String getParentesco() {
		return this.parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public SptBeneficiarioSolicitud getSptBeneficiarioSolicitud() {
		return this.sptBeneficiarioSolicitud;
	}

	public void setSptBeneficiarioSolicitud(SptBeneficiarioSolicitud sptBeneficiarioSolicitud) {
		this.sptBeneficiarioSolicitud = sptBeneficiarioSolicitud;
	}

	public SptEnvComunicEntidade getSptEnvComunicEntidade() {
		return this.sptEnvComunicEntidade;
	}

	public void setSptEnvComunicEntidade(SptEnvComunicEntidade sptEnvComunicEntidade) {
		this.sptEnvComunicEntidade = sptEnvComunicEntidade;
	}

}