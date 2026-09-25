package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APT_DET_PROSP_PREVALID_ENV database table.
 * 
 */
@Entity
@Table(name="APT_DET_PROSP_PREVALID_ENV")
@NamedQuery(name="AptDetProspPrevalidEnv.findAll", query="SELECT a FROM AptDetProspPrevalidEnv a")
public class AptDetProspPrevalidEnv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APT_DET_PROSP_PREVALID_ENV_CVEIDDETPROSPPREVALIDENV_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APT_DET_PROSP_PREVALID_ENV_CVEIDDETPROSPPREVALIDENV_GENERATOR")
	@Column(name="CVE_ID_DET_PROSP_PREVALID_ENV")
	private long cveIdDetProspPrevalidEnv;

	@Column(name="AGUINALDO_ANUAL_LEY73")
	private BigDecimal aguinaldoAnualLey73;

	@Column(name="APLICA_IP_MENOR_25")
	private String aplicaIpMenor25;

	@Column(name="AYUDA_ASISTENCIAL_LSS97")
	private BigDecimal ayudaAsistencialLss97;

	@Column(name="CAMBIO_MODALIDAD")
	private String cambioModalidad;

	@Column(name="CODIGO_TABLAS")
	private String codigoTablas;

	@Column(name="CUANTIA_BASICA_LSS97")
	private BigDecimal cuantiaBasicaLss97;

	@Column(name="CURP")
	private String curp;

	@Column(name="CVE_DELEGACION")
	private String cveDelegacion;

	@Column(name="CVE_SUBDELEGACION")
	private String cveSubdelegacion;

	@Column(name="DERECHO_ELECCION_LSS73")
	private String derechoEleccionLss73;

	@Column(name="DOMICILIO")
	private String domicilio;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ALTA_DB")
	private Date fecAltaDb;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ENVIO")
	private Date fecEnvio;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INCIO_DERECHOS")
	private Date fecIncioDerechos;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PAGO")
	private Date fecInicioPago;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_NACIMIENTO")
	private Date fecNacimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_OFERTA")
	private Date fecOferta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_PROCESO")
	private Date fecProceso;

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
	@Column(name="FEC_SOLICITUD")
	private Date fecSolicitud;

	@Column(name="FOLIO_IDENTIFICADOR")
	private String folioIdentificador;

	@Column(name="ID_LOGIN")
	private String idLogin;

	@Column(name="ID_RAMA")
	private String idRama;

	@Column(name="ID_TIPO_PENSION")
	private String idTipoPension;

	@Column(name="IMPORTE_MENSUAL_LSS73")
	private BigDecimal importeMensualLss73;

	@Column(name="IMPORTE_MENSUAL_LSS97")
	private BigDecimal importeMensualLss97;

	@Column(name="IND_MODALIDAD")
	private String indModalidad;

	@Column(name="IND_PORTABILIDAD")
	private String indPortabilidad;

	@Column(name="NOMBRE_AFILIADO")
	private String nombreAfiliado;

	@Column(name="NOMBRE_SOLICITANTE")
	private String nombreSolicitante;

	@Column(name="NUM_ENVIO")
	private BigDecimal numEnvio;

	@Column(name="NUM_SEMANAS_RECONOCIDAS")
	private BigDecimal numSemanasReconocidas;

	@Column(name="PMG_LSS97")
	private BigDecimal pmgLss97;

	@Column(name="POR_VALUACION")
	private BigDecimal porValuacion;

	@Column(name="SALARIO_DIARIO_IV")
	private BigDecimal salarioDiarioIv;

	@Column(name="SALARIO_DIARIO_RT")
	private BigDecimal salarioDiarioRt;

	@Column(name="SALDO_APORTACIONES_VOLU")
	private BigDecimal saldoAportacionesVolu;

	@Column(name="SALDO_CUENTA_INDIVIDUAL")
	private BigDecimal saldoCuentaIndividual;

	@Column(name="SALDO_RETIRO_97")
	private BigDecimal saldoRetiro97;

	@Column(name="SALDO_SAR_92")
	private BigDecimal saldoSar92;

	@Column(name="SALDO_VIVIENDA_92")
	private BigDecimal saldoVivienda92;

	@Column(name="SALDO_VIVIENDA_97")
	private BigDecimal saldoVivienda97;

	@Column(name="SEXO")
	private String sexo;

	@Column(name="TASA_POSTURA")
	private BigDecimal tasaPostura;

	@Column(name="TIPO_REGISTRO")
	private String tipoRegistro;

	@Column(name="UMF")
	private String umf;

	@Column(name="ID_PROCESO_SAOR")
	private Long idProcesoSaor;

	@Column(name="CVE_CUENTA_USUARIO")
	private String cveCuentaUsuario;

	//bi-directional many-to-one association to SptEnvComunicEntidade
	@ManyToOne
	@JoinColumn(name="CVE_ID_ENV_COMUNIC_ENTIDADES")
	private SptEnvComunicEntidade sptEnvComunicEntidade;

	public AptDetProspPrevalidEnv() {
	}

	public long getCveIdDetProspPrevalidEnv() {
		return this.cveIdDetProspPrevalidEnv;
	}

	public void setCveIdDetProspPrevalidEnv(long cveIdDetProspPrevalidEnv) {
		this.cveIdDetProspPrevalidEnv = cveIdDetProspPrevalidEnv;
	}

	public BigDecimal getAguinaldoAnualLey73() {
		return this.aguinaldoAnualLey73;
	}

	public void setAguinaldoAnualLey73(BigDecimal aguinaldoAnualLey73) {
		this.aguinaldoAnualLey73 = aguinaldoAnualLey73;
	}

	public String getAplicaIpMenor25() {
		return this.aplicaIpMenor25;
	}

	public void setAplicaIpMenor25(String aplicaIpMenor25) {
		this.aplicaIpMenor25 = aplicaIpMenor25;
	}

	public BigDecimal getAyudaAsistencialLss97() {
		return this.ayudaAsistencialLss97;
	}

	public void setAyudaAsistencialLss97(BigDecimal ayudaAsistencialLss97) {
		this.ayudaAsistencialLss97 = ayudaAsistencialLss97;
	}

	public String getCambioModalidad() {
		return this.cambioModalidad;
	}

	public void setCambioModalidad(String cambioModalidad) {
		this.cambioModalidad = cambioModalidad;
	}

	public String getCodigoTablas() {
		return this.codigoTablas;
	}

	public void setCodigoTablas(String codigoTablas) {
		this.codigoTablas = codigoTablas;
	}

	public BigDecimal getCuantiaBasicaLss97() {
		return this.cuantiaBasicaLss97;
	}

	public void setCuantiaBasicaLss97(BigDecimal cuantiaBasicaLss97) {
		this.cuantiaBasicaLss97 = cuantiaBasicaLss97;
	}

	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
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

	public String getDerechoEleccionLss73() {
		return this.derechoEleccionLss73;
	}

	public void setDerechoEleccionLss73(String derechoEleccionLss73) {
		this.derechoEleccionLss73 = derechoEleccionLss73;
	}

	public String getDomicilio() {
		return this.domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Date getFecAltaDb() {
		return this.fecAltaDb;
	}

	public void setFecAltaDb(Date fecAltaDb) {
		this.fecAltaDb = fecAltaDb;
	}

	public Date getFecEnvio() {
		return this.fecEnvio;
	}

	public void setFecEnvio(Date fecEnvio) {
		this.fecEnvio = fecEnvio;
	}

	public Date getFecIncioDerechos() {
		return this.fecIncioDerechos;
	}

	public void setFecIncioDerechos(Date fecIncioDerechos) {
		this.fecIncioDerechos = fecIncioDerechos;
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

	public Date getFecOferta() {
		return this.fecOferta;
	}

	public void setFecOferta(Date fecOferta) {
		this.fecOferta = fecOferta;
	}

	public Date getFecProceso() {
		return this.fecProceso;
	}

	public void setFecProceso(Date fecProceso) {
		this.fecProceso = fecProceso;
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

	public Date getFecSolicitud() {
		return this.fecSolicitud;
	}

	public void setFecSolicitud(Date fecSolicitud) {
		this.fecSolicitud = fecSolicitud;
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

	public String getIdRama() {
		return this.idRama;
	}

	public void setIdRama(String idRama) {
		this.idRama = idRama;
	}

	public String getIdTipoPension() {
		return this.idTipoPension;
	}

	public void setIdTipoPension(String idTipoPension) {
		this.idTipoPension = idTipoPension;
	}

	public BigDecimal getImporteMensualLss73() {
		return this.importeMensualLss73;
	}

	public void setImporteMensualLss73(BigDecimal importeMensualLss73) {
		this.importeMensualLss73 = importeMensualLss73;
	}

	public BigDecimal getImporteMensualLss97() {
		return this.importeMensualLss97;
	}

	public void setImporteMensualLss97(BigDecimal importeMensualLss97) {
		this.importeMensualLss97 = importeMensualLss97;
	}

	public String getIndModalidad() {
		return this.indModalidad;
	}

	public void setIndModalidad(String indModalidad) {
		this.indModalidad = indModalidad;
	}

	public String getIndPortabilidad() {
		return this.indPortabilidad;
	}

	public void setIndPortabilidad(String indPortabilidad) {
		this.indPortabilidad = indPortabilidad;
	}

	public String getNombreAfiliado() {
		return this.nombreAfiliado;
	}

	public void setNombreAfiliado(String nombreAfiliado) {
		this.nombreAfiliado = nombreAfiliado;
	}

	public String getNombreSolicitante() {
		return this.nombreSolicitante;
	}

	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}

	public BigDecimal getNumEnvio() {
		return this.numEnvio;
	}

	public void setNumEnvio(BigDecimal numEnvio) {
		this.numEnvio = numEnvio;
	}

	public BigDecimal getNumSemanasReconocidas() {
		return this.numSemanasReconocidas;
	}

	public void setNumSemanasReconocidas(BigDecimal numSemanasReconocidas) {
		this.numSemanasReconocidas = numSemanasReconocidas;
	}

	public BigDecimal getPmgLss97() {
		return this.pmgLss97;
	}

	public void setPmgLss97(BigDecimal pmgLss97) {
		this.pmgLss97 = pmgLss97;
	}

	public BigDecimal getPorValuacion() {
		return this.porValuacion;
	}

	public void setPorValuacion(BigDecimal porValuacion) {
		this.porValuacion = porValuacion;
	}

	public BigDecimal getSalarioDiarioIv() {
		return this.salarioDiarioIv;
	}

	public void setSalarioDiarioIv(BigDecimal salarioDiarioIv) {
		this.salarioDiarioIv = salarioDiarioIv;
	}

	public BigDecimal getSalarioDiarioRt() {
		return this.salarioDiarioRt;
	}

	public void setSalarioDiarioRt(BigDecimal salarioDiarioRt) {
		this.salarioDiarioRt = salarioDiarioRt;
	}

	public BigDecimal getSaldoAportacionesVolu() {
		return this.saldoAportacionesVolu;
	}

	public void setSaldoAportacionesVolu(BigDecimal saldoAportacionesVolu) {
		this.saldoAportacionesVolu = saldoAportacionesVolu;
	}

	public BigDecimal getSaldoCuentaIndividual() {
		return this.saldoCuentaIndividual;
	}

	public void setSaldoCuentaIndividual(BigDecimal saldoCuentaIndividual) {
		this.saldoCuentaIndividual = saldoCuentaIndividual;
	}

	public BigDecimal getSaldoRetiro97() {
		return this.saldoRetiro97;
	}

	public void setSaldoRetiro97(BigDecimal saldoRetiro97) {
		this.saldoRetiro97 = saldoRetiro97;
	}

	public BigDecimal getSaldoSar92() {
		return this.saldoSar92;
	}

	public void setSaldoSar92(BigDecimal saldoSar92) {
		this.saldoSar92 = saldoSar92;
	}

	public BigDecimal getSaldoVivienda92() {
		return this.saldoVivienda92;
	}

	public void setSaldoVivienda92(BigDecimal saldoVivienda92) {
		this.saldoVivienda92 = saldoVivienda92;
	}

	public BigDecimal getSaldoVivienda97() {
		return this.saldoVivienda97;
	}

	public void setSaldoVivienda97(BigDecimal saldoVivienda97) {
		this.saldoVivienda97 = saldoVivienda97;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public BigDecimal getTasaPostura() {
		return this.tasaPostura;
	}

	public void setTasaPostura(BigDecimal tasaPostura) {
		this.tasaPostura = tasaPostura;
	}

	public String getTipoRegistro() {
		return this.tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public String getUmf() {
		return this.umf;
	}

	public void setUmf(String umf) {
		this.umf = umf;
	}

	public Long getIdProcesoSaor() {
		return idProcesoSaor;
	}

	public void setIdProcesoSaor(Long idProcesoSaor) {
		this.idProcesoSaor = idProcesoSaor;
	}

	public String getCveCuentaUsuario() {
		return cveCuentaUsuario;
	}

	public void setCveCuentaUsuario(String cveCuentaUsuario) {
		this.cveCuentaUsuario = cveCuentaUsuario;
	}

	public SptEnvComunicEntidade getSptEnvComunicEntidade() {
		return this.sptEnvComunicEntidade;
	}

	public void setSptEnvComunicEntidade(SptEnvComunicEntidade sptEnvComunicEntidade) {
		this.sptEnvComunicEntidade = sptEnvComunicEntidade;
	}

}
