package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APT_IMPORTES_RESOLUCION database table.
 * 
 */
@Entity
@Table(name="APT_IMPORTES_RESOLUCION")
@NamedQuery(name="AptImportesResolucion.findAll", query="SELECT a FROM AptImportesResolucion a")
public class AptImportesResolucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_APTIMPORTESRESOLUCION", sequenceName = "SEQ_APTIMPORTESRESOLUCION")
	@GeneratedValue(generator = "SEQ_APTIMPORTESRESOLUCION")
	@Column(name="CVE_ID_IMPORTES_RESOLUCION")
	private long cveIdImportesResolucion;

	@Column(name="CVE_REGIMEN")
	private String cveRegimen;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_CALCULO")
	private Date fecCalculo;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_ARTICULO_167")
	private String idArticulo167;

	@Column(name="ID_REFORMA_LEY")
	private String idReformaLey;

	@Column(name="IMP_ANUAL_ASIGNACIONES_RES")
	private BigDecimal impAnualAsignacionesRes;

	@Column(name="IMP_ANUAL_AYUDA_ASIST_RES")
	private BigDecimal impAnualAyudaAsistRes;

	@Column(name="IMP_ANUAL_MONTO_MIN_PMG_RES")
	private BigDecimal impAnualMontoMinPmgRes;

	@Column(name="IMP_AYUDA_ASISTENCIAL")
	private BigDecimal impAyudaAsistencial;

	@Column(name="IMP_AYUDA_SOLEDAD")
	private BigDecimal impAyudaSoledad;

	@Column(name="IMP_AYUDA_UNICO_ASCENDIENTE")
	private BigDecimal impAyudaUnicoAscendiente;

	@Column(name="IMP_CUANTIA_ANUAL")
	private BigDecimal impCuantiaAnual;

	@Column(name="IMP_CUANTIA_ANUAL_RES")
	private BigDecimal impCuantiaAnualRes;

	@Column(name="IMP_CUANTIA_ANUAL_VE")
	private BigDecimal impCuantiaAnualVe;

	@Column(name="IMP_CUANTIA_ANUAL_VE_RES")
	private BigDecimal impCuantiaAnualVeRes;

	@Column(name="IMP_CUANTIA_BASICA_ANUAL")
	private BigDecimal impCuantiaBasicaAnual;

	@Column(name="IMP_CUANTIA_BASICA_ANUAL_IPT")
	private BigDecimal impCuantiaBasicaAnualIpt;

	@Column(name="IMP_CUANTIA_BASICA_ANUAL_RES")
	private BigDecimal impCuantiaBasicaAnualRes;

	@Column(name="IMP_CUANTIA_INC_ANUAL")
	private BigDecimal impCuantiaIncAnual;

	@Column(name="IMP_CUANTIA_INC_ANUAL_RES")
	private BigDecimal impCuantiaIncAnualRes;

	@Column(name="IMP_CUANTIA_MENSUAL")
	private BigDecimal impCuantiaMensual;

	@Column(name="IMP_CUANTIA_MENSUAL_IPT")
	private BigDecimal impCuantiaMensualIpt;

	@Column(name="IMP_CUANTIA_MENSUAL_IPT_RES")
	private BigDecimal impCuantiaMensualIptRes;

	@Column(name="IMP_CUANTIA_MENSUAL_RES")
	private BigDecimal impCuantiaMensualRes;

	@Column(name="IMP_CUANTIA_REDUCIDA")
	private BigDecimal impCuantiaReducida;

	@Column(name="IMP_DIF_INVALIDEZ_FICTICIA")
	private BigDecimal impDifInvalidezFicticia;

	@Column(name="IMP_DIF_MONTO_MINIMO_PMG")
	private BigDecimal impDifMontoMinimoPmg;

	@Column(name="IMP_DIF_MONTO_MINIMO_PMG_RES")
	private BigDecimal impDifMontoMinimoPmgRes;

	@Column(name="IMP_INDEMNIZACION")
	private BigDecimal impIndemnizacion;

	@Column(name="IMP_INVALIDEZ_FICTICIA")
	private BigDecimal impInvalidezFicticia;

	@Column(name="IMP_MENSUAL_ASIGNACIONES")
	private BigDecimal impMensualAsignaciones;

	@Column(name="IMP_MENSUAL_ASIGNACIONES_RES")
	private BigDecimal impMensualAsignacionesRes;

	@Column(name="IMP_MENSUAL_AYUDA_ASIS_RESO")
	private BigDecimal impMensualAyudaAsisReso;

	@Column(name="IMP_MENSUAL_AYUDA_ASISTENCIAL")
	private BigDecimal impMensualAyudaAsistencial;

	@Column(name="IMP_MENSUAL_TOPE")
	private BigDecimal impMensualTope;

	@Column(name="IMP_MONTO_MINIMO_PMG")
	private BigDecimal impMontoMinimoPmg;

	@Column(name="IMP_MONTO_MINIMO_PMG_RESN")
	private BigDecimal impMontoMinimoPmgResn;

	@Column(name="IMP_PENSION")
	private BigDecimal impPension;

	@Column(name="IMP_PENSION_ANUAL_RES")
	private BigDecimal impPensionAnualRes;

	@Column(name="IMP_PENSION_MENSUAL_DER")
	private BigDecimal impPensionMensualDer;

	@Column(name="IMP_PENSION_MENSUAL_TOT_DER")
	private BigDecimal impPensionMensualTotDer;

	@Column(name="IMP_PMG_MULTIPLICADO")
	private BigDecimal impPmgMultiplicado;

	@Column(name="IMP_REFORMA_LEY")
	private BigDecimal impReformaLey;

	@Column(name="IMP_SALARIO_MINIMO_VIGENTE_DF")
	private BigDecimal impSalarioMinimoVigenteDf;

	@Column(name="IMP_SALDO_INDIVIDUAL")
	private BigDecimal impSaldoIndividual;

	@Column(name="IMP_SUFICIENCIA_RECURSOS")
	private BigDecimal impSuficienciaRecursos;

	@Column(name="NUM_INCREMENTOS")
	private BigDecimal numIncrementos;

	@Column(name="NUM_SEMANAS_REC_POST_500")
	private BigDecimal numSemanasRecPost500;

	@Column(name="NUM_VECES_SMV_DF")
	private BigDecimal numVecesSmvDf;

	@Column(name="POR_AYUDA_ASISTENCIAL")
	private BigDecimal porAyudaAsistencial;

	@Column(name="POR_AYUDA_SOLEDAD")
	private BigDecimal porAyudaSoledad;

	@Column(name="POR_AYUDA_UNICO_ASCENDIENTE")
	private BigDecimal porAyudaUnicoAscendiente;

	@Column(name="POR_CESANTIA")
	private BigDecimal porCesantia;

	@Column(name="POR_CUANTIA_BASICA")
	private BigDecimal porCuantiaBasica;

	@Column(name="POR_FACTOR_AJUSTE_AYUDA_ASIGN")
	private BigDecimal porFactorAjusteAyudaAsign;

	@Column(name="POR_FACTOR_AJUSTE_CUANT_RED")
	private BigDecimal porFactorAjusteCuantRed;

	@Column(name="POR_FACTOR_AJUSTE_PRORRATEO")
	private BigDecimal porFactorAjusteProrrateo;

	@Column(name="POR_FACTOR_VECES_CUANTIA_MENS")
	private BigDecimal porFactorVecesCuantiaMens;

	@Column(name="POR_INC_ANUAL")
	private BigDecimal porIncAnual;

	@Column(name="POR_MENSUAL_ASIGNACIONES")
	private BigDecimal porMensualAsignaciones;

	@Column(name="POR_MENSUAL_AYUDA_ASISTENCIAL")
	private BigDecimal porMensualAyudaAsistencial;

	@Column(name="POR_REDUCCION_ART_ANT")
	private BigDecimal porReduccionArtAnt;

	//bi-directional many-to-one association to SptTramitePension
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

	public AptImportesResolucion() {
	}

	public long getCveIdImportesResolucion() {
		return this.cveIdImportesResolucion;
	}

	public void setCveIdImportesResolucion(long cveIdImportesResolucion) {
		this.cveIdImportesResolucion = cveIdImportesResolucion;
	}

	public String getCveRegimen() {
		return this.cveRegimen;
	}

	public void setCveRegimen(String cveRegimen) {
		this.cveRegimen = cveRegimen;
	}

	public Date getFecCalculo() {
		return this.fecCalculo;
	}

	public void setFecCalculo(Date fecCalculo) {
		this.fecCalculo = fecCalculo;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
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

	public String getIdArticulo167() {
		return this.idArticulo167;
	}

	public void setIdArticulo167(String idArticulo167) {
		this.idArticulo167 = idArticulo167;
	}

	public String getIdReformaLey() {
		return this.idReformaLey;
	}

	public void setIdReformaLey(String idReformaLey) {
		this.idReformaLey = idReformaLey;
	}

	public BigDecimal getImpAnualAsignacionesRes() {
		return this.impAnualAsignacionesRes;
	}

	public void setImpAnualAsignacionesRes(BigDecimal impAnualAsignacionesRes) {
		this.impAnualAsignacionesRes = impAnualAsignacionesRes;
	}

	public BigDecimal getImpAnualAyudaAsistRes() {
		return this.impAnualAyudaAsistRes;
	}

	public void setImpAnualAyudaAsistRes(BigDecimal impAnualAyudaAsistRes) {
		this.impAnualAyudaAsistRes = impAnualAyudaAsistRes;
	}

	public BigDecimal getImpAnualMontoMinPmgRes() {
		return this.impAnualMontoMinPmgRes;
	}

	public void setImpAnualMontoMinPmgRes(BigDecimal impAnualMontoMinPmgRes) {
		this.impAnualMontoMinPmgRes = impAnualMontoMinPmgRes;
	}

	public BigDecimal getImpAyudaAsistencial() {
		return this.impAyudaAsistencial;
	}

	public void setImpAyudaAsistencial(BigDecimal impAyudaAsistencial) {
		this.impAyudaAsistencial = impAyudaAsistencial;
	}

	public BigDecimal getImpAyudaSoledad() {
		return this.impAyudaSoledad;
	}

	public void setImpAyudaSoledad(BigDecimal impAyudaSoledad) {
		this.impAyudaSoledad = impAyudaSoledad;
	}

	public BigDecimal getImpAyudaUnicoAscendiente() {
		return this.impAyudaUnicoAscendiente;
	}

	public void setImpAyudaUnicoAscendiente(BigDecimal impAyudaUnicoAscendiente) {
		this.impAyudaUnicoAscendiente = impAyudaUnicoAscendiente;
	}

	public BigDecimal getImpCuantiaAnual() {
		return this.impCuantiaAnual;
	}

	public void setImpCuantiaAnual(BigDecimal impCuantiaAnual) {
		this.impCuantiaAnual = impCuantiaAnual;
	}

	public BigDecimal getImpCuantiaAnualRes() {
		return this.impCuantiaAnualRes;
	}

	public void setImpCuantiaAnualRes(BigDecimal impCuantiaAnualRes) {
		this.impCuantiaAnualRes = impCuantiaAnualRes;
	}

	public BigDecimal getImpCuantiaAnualVe() {
		return this.impCuantiaAnualVe;
	}

	public void setImpCuantiaAnualVe(BigDecimal impCuantiaAnualVe) {
		this.impCuantiaAnualVe = impCuantiaAnualVe;
	}

	public BigDecimal getImpCuantiaAnualVeRes() {
		return this.impCuantiaAnualVeRes;
	}

	public void setImpCuantiaAnualVeRes(BigDecimal impCuantiaAnualVeRes) {
		this.impCuantiaAnualVeRes = impCuantiaAnualVeRes;
	}

	public BigDecimal getImpCuantiaBasicaAnual() {
		return this.impCuantiaBasicaAnual;
	}

	public void setImpCuantiaBasicaAnual(BigDecimal impCuantiaBasicaAnual) {
		this.impCuantiaBasicaAnual = impCuantiaBasicaAnual;
	}

	public BigDecimal getImpCuantiaBasicaAnualIpt() {
		return this.impCuantiaBasicaAnualIpt;
	}

	public void setImpCuantiaBasicaAnualIpt(BigDecimal impCuantiaBasicaAnualIpt) {
		this.impCuantiaBasicaAnualIpt = impCuantiaBasicaAnualIpt;
	}

	public BigDecimal getImpCuantiaBasicaAnualRes() {
		return this.impCuantiaBasicaAnualRes;
	}

	public void setImpCuantiaBasicaAnualRes(BigDecimal impCuantiaBasicaAnualRes) {
		this.impCuantiaBasicaAnualRes = impCuantiaBasicaAnualRes;
	}

	public BigDecimal getImpCuantiaIncAnual() {
		return this.impCuantiaIncAnual;
	}

	public void setImpCuantiaIncAnual(BigDecimal impCuantiaIncAnual) {
		this.impCuantiaIncAnual = impCuantiaIncAnual;
	}

	public BigDecimal getImpCuantiaIncAnualRes() {
		return this.impCuantiaIncAnualRes;
	}

	public void setImpCuantiaIncAnualRes(BigDecimal impCuantiaIncAnualRes) {
		this.impCuantiaIncAnualRes = impCuantiaIncAnualRes;
	}

	public BigDecimal getImpCuantiaMensual() {
		return this.impCuantiaMensual;
	}

	public void setImpCuantiaMensual(BigDecimal impCuantiaMensual) {
		this.impCuantiaMensual = impCuantiaMensual;
	}

	public BigDecimal getImpCuantiaMensualIpt() {
		return this.impCuantiaMensualIpt;
	}

	public void setImpCuantiaMensualIpt(BigDecimal impCuantiaMensualIpt) {
		this.impCuantiaMensualIpt = impCuantiaMensualIpt;
	}

	public BigDecimal getImpCuantiaMensualIptRes() {
		return this.impCuantiaMensualIptRes;
	}

	public void setImpCuantiaMensualIptRes(BigDecimal impCuantiaMensualIptRes) {
		this.impCuantiaMensualIptRes = impCuantiaMensualIptRes;
	}

	public BigDecimal getImpCuantiaMensualRes() {
		return this.impCuantiaMensualRes;
	}

	public void setImpCuantiaMensualRes(BigDecimal impCuantiaMensualRes) {
		this.impCuantiaMensualRes = impCuantiaMensualRes;
	}

	public BigDecimal getImpCuantiaReducida() {
		return this.impCuantiaReducida;
	}

	public void setImpCuantiaReducida(BigDecimal impCuantiaReducida) {
		this.impCuantiaReducida = impCuantiaReducida;
	}

	public BigDecimal getImpDifInvalidezFicticia() {
		return this.impDifInvalidezFicticia;
	}

	public void setImpDifInvalidezFicticia(BigDecimal impDifInvalidezFicticia) {
		this.impDifInvalidezFicticia = impDifInvalidezFicticia;
	}

	public BigDecimal getImpDifMontoMinimoPmg() {
		return this.impDifMontoMinimoPmg;
	}

	public void setImpDifMontoMinimoPmg(BigDecimal impDifMontoMinimoPmg) {
		this.impDifMontoMinimoPmg = impDifMontoMinimoPmg;
	}

	public BigDecimal getImpDifMontoMinimoPmgRes() {
		return this.impDifMontoMinimoPmgRes;
	}

	public void setImpDifMontoMinimoPmgRes(BigDecimal impDifMontoMinimoPmgRes) {
		this.impDifMontoMinimoPmgRes = impDifMontoMinimoPmgRes;
	}

	public BigDecimal getImpIndemnizacion() {
		return this.impIndemnizacion;
	}

	public void setImpIndemnizacion(BigDecimal impIndemnizacion) {
		this.impIndemnizacion = impIndemnizacion;
	}

	public BigDecimal getImpInvalidezFicticia() {
		return this.impInvalidezFicticia;
	}

	public void setImpInvalidezFicticia(BigDecimal impInvalidezFicticia) {
		this.impInvalidezFicticia = impInvalidezFicticia;
	}

	public BigDecimal getImpMensualAsignaciones() {
		return this.impMensualAsignaciones;
	}

	public void setImpMensualAsignaciones(BigDecimal impMensualAsignaciones) {
		this.impMensualAsignaciones = impMensualAsignaciones;
	}

	public BigDecimal getImpMensualAsignacionesRes() {
		return this.impMensualAsignacionesRes;
	}

	public void setImpMensualAsignacionesRes(BigDecimal impMensualAsignacionesRes) {
		this.impMensualAsignacionesRes = impMensualAsignacionesRes;
	}

	public BigDecimal getImpMensualAyudaAsisReso() {
		return this.impMensualAyudaAsisReso;
	}

	public void setImpMensualAyudaAsisReso(BigDecimal impMensualAyudaAsisReso) {
		this.impMensualAyudaAsisReso = impMensualAyudaAsisReso;
	}

	public BigDecimal getImpMensualAyudaAsistencial() {
		return this.impMensualAyudaAsistencial;
	}

	public void setImpMensualAyudaAsistencial(BigDecimal impMensualAyudaAsistencial) {
		this.impMensualAyudaAsistencial = impMensualAyudaAsistencial;
	}

	public BigDecimal getImpMensualTope() {
		return this.impMensualTope;
	}

	public void setImpMensualTope(BigDecimal impMensualTope) {
		this.impMensualTope = impMensualTope;
	}

	public BigDecimal getImpMontoMinimoPmg() {
		return this.impMontoMinimoPmg;
	}

	public void setImpMontoMinimoPmg(BigDecimal impMontoMinimoPmg) {
		this.impMontoMinimoPmg = impMontoMinimoPmg;
	}

	public BigDecimal getImpMontoMinimoPmgResn() {
		return this.impMontoMinimoPmgResn;
	}

	public void setImpMontoMinimoPmgResn(BigDecimal impMontoMinimoPmgResn) {
		this.impMontoMinimoPmgResn = impMontoMinimoPmgResn;
	}

	public BigDecimal getImpPension() {
		return this.impPension;
	}

	public void setImpPension(BigDecimal impPension) {
		this.impPension = impPension;
	}

	public BigDecimal getImpPensionAnualRes() {
		return this.impPensionAnualRes;
	}

	public void setImpPensionAnualRes(BigDecimal impPensionAnualRes) {
		this.impPensionAnualRes = impPensionAnualRes;
	}

	public BigDecimal getImpPensionMensualDer() {
		return this.impPensionMensualDer;
	}

	public void setImpPensionMensualDer(BigDecimal impPensionMensualDer) {
		this.impPensionMensualDer = impPensionMensualDer;
	}

	public BigDecimal getImpPensionMensualTotDer() {
		return this.impPensionMensualTotDer;
	}

	public void setImpPensionMensualTotDer(BigDecimal impPensionMensualTotDer) {
		this.impPensionMensualTotDer = impPensionMensualTotDer;
	}

	public BigDecimal getImpPmgMultiplicado() {
		return this.impPmgMultiplicado;
	}

	public void setImpPmgMultiplicado(BigDecimal impPmgMultiplicado) {
		this.impPmgMultiplicado = impPmgMultiplicado;
	}

	public BigDecimal getImpReformaLey() {
		return this.impReformaLey;
	}

	public void setImpReformaLey(BigDecimal impReformaLey) {
		this.impReformaLey = impReformaLey;
	}

	public BigDecimal getImpSalarioMinimoVigenteDf() {
		return this.impSalarioMinimoVigenteDf;
	}

	public void setImpSalarioMinimoVigenteDf(BigDecimal impSalarioMinimoVigenteDf) {
		this.impSalarioMinimoVigenteDf = impSalarioMinimoVigenteDf;
	}

	public BigDecimal getImpSaldoIndividual() {
		return this.impSaldoIndividual;
	}

	public void setImpSaldoIndividual(BigDecimal impSaldoIndividual) {
		this.impSaldoIndividual = impSaldoIndividual;
	}

	public BigDecimal getImpSuficienciaRecursos() {
		return this.impSuficienciaRecursos;
	}

	public void setImpSuficienciaRecursos(BigDecimal impSuficienciaRecursos) {
		this.impSuficienciaRecursos = impSuficienciaRecursos;
	}

	public BigDecimal getNumIncrementos() {
		return this.numIncrementos;
	}

	public void setNumIncrementos(BigDecimal numIncrementos) {
		this.numIncrementos = numIncrementos;
	}

	public BigDecimal getNumSemanasRecPost500() {
		return this.numSemanasRecPost500;
	}

	public void setNumSemanasRecPost500(BigDecimal numSemanasRecPost500) {
		this.numSemanasRecPost500 = numSemanasRecPost500;
	}

	public BigDecimal getNumVecesSmvDf() {
		return this.numVecesSmvDf;
	}

	public void setNumVecesSmvDf(BigDecimal numVecesSmvDf) {
		this.numVecesSmvDf = numVecesSmvDf;
	}

	public BigDecimal getPorAyudaAsistencial() {
		return this.porAyudaAsistencial;
	}

	public void setPorAyudaAsistencial(BigDecimal porAyudaAsistencial) {
		this.porAyudaAsistencial = porAyudaAsistencial;
	}

	public BigDecimal getPorAyudaSoledad() {
		return this.porAyudaSoledad;
	}

	public void setPorAyudaSoledad(BigDecimal porAyudaSoledad) {
		this.porAyudaSoledad = porAyudaSoledad;
	}

	public BigDecimal getPorAyudaUnicoAscendiente() {
		return this.porAyudaUnicoAscendiente;
	}

	public void setPorAyudaUnicoAscendiente(BigDecimal porAyudaUnicoAscendiente) {
		this.porAyudaUnicoAscendiente = porAyudaUnicoAscendiente;
	}

	public BigDecimal getPorCesantia() {
		return this.porCesantia;
	}

	public void setPorCesantia(BigDecimal porCesantia) {
		this.porCesantia = porCesantia;
	}

	public BigDecimal getPorCuantiaBasica() {
		return this.porCuantiaBasica;
	}

	public void setPorCuantiaBasica(BigDecimal porCuantiaBasica) {
		this.porCuantiaBasica = porCuantiaBasica;
	}

	public BigDecimal getPorFactorAjusteAyudaAsign() {
		return this.porFactorAjusteAyudaAsign;
	}

	public void setPorFactorAjusteAyudaAsign(BigDecimal porFactorAjusteAyudaAsign) {
		this.porFactorAjusteAyudaAsign = porFactorAjusteAyudaAsign;
	}

	public BigDecimal getPorFactorAjusteCuantRed() {
		return this.porFactorAjusteCuantRed;
	}

	public void setPorFactorAjusteCuantRed(BigDecimal porFactorAjusteCuantRed) {
		this.porFactorAjusteCuantRed = porFactorAjusteCuantRed;
	}

	public BigDecimal getPorFactorAjusteProrrateo() {
		return this.porFactorAjusteProrrateo;
	}

	public void setPorFactorAjusteProrrateo(BigDecimal porFactorAjusteProrrateo) {
		this.porFactorAjusteProrrateo = porFactorAjusteProrrateo;
	}

	public BigDecimal getPorFactorVecesCuantiaMens() {
		return this.porFactorVecesCuantiaMens;
	}

	public void setPorFactorVecesCuantiaMens(BigDecimal porFactorVecesCuantiaMens) {
		this.porFactorVecesCuantiaMens = porFactorVecesCuantiaMens;
	}

	public BigDecimal getPorIncAnual() {
		return this.porIncAnual;
	}

	public void setPorIncAnual(BigDecimal porIncAnual) {
		this.porIncAnual = porIncAnual;
	}

	public BigDecimal getPorMensualAsignaciones() {
		return this.porMensualAsignaciones;
	}

	public void setPorMensualAsignaciones(BigDecimal porMensualAsignaciones) {
		this.porMensualAsignaciones = porMensualAsignaciones;
	}

	public BigDecimal getPorMensualAyudaAsistencial() {
		return this.porMensualAyudaAsistencial;
	}

	public void setPorMensualAyudaAsistencial(BigDecimal porMensualAyudaAsistencial) {
		this.porMensualAyudaAsistencial = porMensualAyudaAsistencial;
	}

	public BigDecimal getPorReduccionArtAnt() {
		return this.porReduccionArtAnt;
	}

	public void setPorReduccionArtAnt(BigDecimal porReduccionArtAnt) {
		this.porReduccionArtAnt = porReduccionArtAnt;
	}

	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}

}
