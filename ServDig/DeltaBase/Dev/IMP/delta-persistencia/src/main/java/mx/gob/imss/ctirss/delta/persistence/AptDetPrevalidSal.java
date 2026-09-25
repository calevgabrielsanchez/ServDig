package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APT_DET_PREVALID_SAL database table.
 * 
 */
@Entity
@Table(name="APT_DET_PREVALID_SAL")
@NamedQuery(name="AptDetPrevalidSal.findAll", query="SELECT a FROM AptDetPrevalidSal a")
public class AptDetPrevalidSal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APT_DET_PREVALID_SAL_CVEIDDETPREVALIDSAL_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APT_DET_PREVALID_SAL_CVEIDDETPREVALIDSAL_GENERATOR")
	@Column(name="CVE_ID_DET_PREVALID_SAL")
	private long cveIdDetPrevalidSal;

	@Column(name="APORTACION_CUENTA_INDIVIDUAL")
	private BigDecimal aportacionCuentaIndividual;

	@Column(name="CUANTIA_BASICA_IV_CALCULO")
	private BigDecimal cuantiaBasicaIvCalculo;

	@Column(name="CUANTIA_BASICA_IV_INCIO_DER")
	private BigDecimal cuantiaBasicaIvIncioDer;

	@Column(name="CUANTIA_BASICA_RT_CALCULO")
	private BigDecimal cuantiaBasicaRtCalculo;

	@Column(name="CUANTIA_BASICA_RT_INCIO_DER")
	private BigDecimal cuantiaBasicaRtIncioDer;

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
	@Column(name="FEC_RESPUESTA")
	private Date fecRespuesta;

	@Column(name="FOLIO_IDENTIFICADOR")
	private String folioIdentificador;

	@Column(name="ID_CODIGO")
	private String idCodigo;

	@Column(name="ID_DIAGNOSTICO")
	private String idDiagnostico;

	@Column(name="ID_LOGIN")
	private String idLogin;

	@Column(name="ID_PROCESO_OCUPADO")
	private String idProcesoOcupado;

	@Column(name="ID_RAMA")
	private String idRama;

	@Column(name="MONTO_EXCEDENTE_CUENTA_IND")
	private BigDecimal montoExcedenteCuentaInd;

	@Column(name="NUM_ENVIO")
	private BigDecimal numEnvio;

	@Column(name="PMG_CALCULO")
	private BigDecimal pmgCalculo;

	@Column(name="PMG_INCIO_DERECHOS")
	private BigDecimal pmgIncioDerechos;

	@Column(name="POR_DE_SALDO_CUENTA_IND")
	private BigDecimal porDeSaldoCuentaInd;

	@Column(name="RENTA_MENSUAL_CALCULO")
	private BigDecimal rentaMensualCalculo;

	@Column(name="RENTA_MENSUAL_INICIO_DERECHOS")
	private BigDecimal rentaMensualInicioDerechos;

	@Column(name="RENTA_RCV_TOTAL")
	private BigDecimal rentaRcvTotal;

	@Column(name="SALDO_CUENTA_INDIVIDUAL")
	private BigDecimal saldoCuentaIndividual;

	@Column(name="TIPO_PENSION")
	private String tipoPension;

	//bi-directional many-to-one association to SptRespComunicEntidade
	@ManyToOne
	@JoinColumn(name="CVE_ID_RESP_COMUNIC_ENTIDADES")
	private SptRespComunicEntidade sptRespComunicEntidade;

	public AptDetPrevalidSal() {
	}

	public long getCveIdDetPrevalidSal() {
		return this.cveIdDetPrevalidSal;
	}

	public void setCveIdDetPrevalidSal(long cveIdDetPrevalidSal) {
		this.cveIdDetPrevalidSal = cveIdDetPrevalidSal;
	}

	public BigDecimal getAportacionCuentaIndividual() {
		return this.aportacionCuentaIndividual;
	}

	public void setAportacionCuentaIndividual(BigDecimal aportacionCuentaIndividual) {
		this.aportacionCuentaIndividual = aportacionCuentaIndividual;
	}

	public BigDecimal getCuantiaBasicaIvCalculo() {
		return this.cuantiaBasicaIvCalculo;
	}

	public void setCuantiaBasicaIvCalculo(BigDecimal cuantiaBasicaIvCalculo) {
		this.cuantiaBasicaIvCalculo = cuantiaBasicaIvCalculo;
	}

	public BigDecimal getCuantiaBasicaIvIncioDer() {
		return this.cuantiaBasicaIvIncioDer;
	}

	public void setCuantiaBasicaIvIncioDer(BigDecimal cuantiaBasicaIvIncioDer) {
		this.cuantiaBasicaIvIncioDer = cuantiaBasicaIvIncioDer;
	}

	public BigDecimal getCuantiaBasicaRtCalculo() {
		return this.cuantiaBasicaRtCalculo;
	}

	public void setCuantiaBasicaRtCalculo(BigDecimal cuantiaBasicaRtCalculo) {
		this.cuantiaBasicaRtCalculo = cuantiaBasicaRtCalculo;
	}

	public BigDecimal getCuantiaBasicaRtIncioDer() {
		return this.cuantiaBasicaRtIncioDer;
	}

	public void setCuantiaBasicaRtIncioDer(BigDecimal cuantiaBasicaRtIncioDer) {
		this.cuantiaBasicaRtIncioDer = cuantiaBasicaRtIncioDer;
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

	public Date getFecRespuesta() {
		return this.fecRespuesta;
	}

	public void setFecRespuesta(Date fecRespuesta) {
		this.fecRespuesta = fecRespuesta;
	}

	public String getFolioIdentificador() {
		return this.folioIdentificador;
	}

	public void setFolioIdentificador(String folioIdentificador) {
		this.folioIdentificador = folioIdentificador;
	}

	public String getIdCodigo() {
		return this.idCodigo;
	}

	public void setIdCodigo(String idCodigo) {
		this.idCodigo = idCodigo;
	}

	public String getIdDiagnostico() {
		return this.idDiagnostico;
	}

	public void setIdDiagnostico(String idDiagnostico) {
		this.idDiagnostico = idDiagnostico;
	}

	public String getIdLogin() {
		return this.idLogin;
	}

	public void setIdLogin(String idLogin) {
		this.idLogin = idLogin;
	}

	public String getIdProcesoOcupado() {
		return this.idProcesoOcupado;
	}

	public void setIdProcesoOcupado(String idProcesoOcupado) {
		this.idProcesoOcupado = idProcesoOcupado;
	}

	public String getIdRama() {
		return this.idRama;
	}

	public void setIdRama(String idRama) {
		this.idRama = idRama;
	}

	public BigDecimal getMontoExcedenteCuentaInd() {
		return this.montoExcedenteCuentaInd;
	}

	public void setMontoExcedenteCuentaInd(BigDecimal montoExcedenteCuentaInd) {
		this.montoExcedenteCuentaInd = montoExcedenteCuentaInd;
	}

	public BigDecimal getNumEnvio() {
		return this.numEnvio;
	}

	public void setNumEnvio(BigDecimal numEnvio) {
		this.numEnvio = numEnvio;
	}

	public BigDecimal getPmgCalculo() {
		return this.pmgCalculo;
	}

	public void setPmgCalculo(BigDecimal pmgCalculo) {
		this.pmgCalculo = pmgCalculo;
	}

	public BigDecimal getPmgIncioDerechos() {
		return this.pmgIncioDerechos;
	}

	public void setPmgIncioDerechos(BigDecimal pmgIncioDerechos) {
		this.pmgIncioDerechos = pmgIncioDerechos;
	}

	public BigDecimal getPorDeSaldoCuentaInd() {
		return this.porDeSaldoCuentaInd;
	}

	public void setPorDeSaldoCuentaInd(BigDecimal porDeSaldoCuentaInd) {
		this.porDeSaldoCuentaInd = porDeSaldoCuentaInd;
	}

	public BigDecimal getRentaMensualCalculo() {
		return this.rentaMensualCalculo;
	}

	public void setRentaMensualCalculo(BigDecimal rentaMensualCalculo) {
		this.rentaMensualCalculo = rentaMensualCalculo;
	}

	public BigDecimal getRentaMensualInicioDerechos() {
		return this.rentaMensualInicioDerechos;
	}

	public void setRentaMensualInicioDerechos(BigDecimal rentaMensualInicioDerechos) {
		this.rentaMensualInicioDerechos = rentaMensualInicioDerechos;
	}

	public BigDecimal getRentaRcvTotal() {
		return this.rentaRcvTotal;
	}

	public void setRentaRcvTotal(BigDecimal rentaRcvTotal) {
		this.rentaRcvTotal = rentaRcvTotal;
	}

	public BigDecimal getSaldoCuentaIndividual() {
		return this.saldoCuentaIndividual;
	}

	public void setSaldoCuentaIndividual(BigDecimal saldoCuentaIndividual) {
		this.saldoCuentaIndividual = saldoCuentaIndividual;
	}

	public String getTipoPension() {
		return this.tipoPension;
	}

	public void setTipoPension(String tipoPension) {
		this.tipoPension = tipoPension;
	}

	public SptRespComunicEntidade getSptRespComunicEntidade() {
		return this.sptRespComunicEntidade;
	}

	public void setSptRespComunicEntidade(SptRespComunicEntidade sptRespComunicEntidade) {
		this.sptRespComunicEntidade = sptRespComunicEntidade;
	}

}