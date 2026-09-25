package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SpcTipoMovimiento;
import mx.gob.imss.ctirss.delta.persistence.SptPension;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_PENSION_MOV database table.
 * 
 */
@Entity
@Table(name="SPT_PENSION_MOV")
@NamedQuery(name="SptPensionMov.findAll", query="SELECT s FROM SptPensionMov s")
public class SptPensionMov implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTPENSIONMOV", sequenceName = "SEQ_SPTPENSIONMOV")
	@GeneratedValue(generator = "SEQ_SPTPENSIONMOV")
	@Column(name="CVE_ID_PENSION_MOV")
	private long cveIdPensionMov;

	@Column(name="CVE_PERIODO_NOMINA")
	private BigDecimal cvePeriodoNomina;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_PENSION")
	private Date fecInicioPension;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Column(name="ID_ANTECEDENTE")
	private String idAntecedente;

	@Column(name="ID_ARTICULO_167")
	private String idArticulo167;

	@Column(name="ID_CERTIFICADO_DERECHOS")
	private String idCertificadoDerechos;

	@Column(name="ID_FECHA_MODIFICACION")
	private Timestamp idFechaModificacion;

	@Column(name="ID_NSS")
	private String idNss;

	@Column(name="IMP_AYUDA_ASISTENCIAL")
	private BigDecimal impAyudaAsistencial;

	@Column(name="IMP_AYUDA_SOLEDAD")
	private BigDecimal impAyudaSoledad;

	@Column(name="IMP_AYUDA_UNICO_ASCENDIENTE")
	private BigDecimal impAyudaUnicoAscendiente;

	@Column(name="IMP_CUANTIA_ANUAL")
	private BigDecimal impCuantiaAnual;

	@Column(name="IMP_CUANTIA_ANUAL_VE")
	private BigDecimal impCuantiaAnualVe;

	@Column(name="IMP_CUANTIA_BASICA_ANUAL")
	private BigDecimal impCuantiaBasicaAnual;

	@Column(name="IMP_CUANTIA_BASICA_ANUAL_IPT")
	private BigDecimal impCuantiaBasicaAnualIpt;

	@Column(name="IMP_CUANTIA_INC_ANUAL")
	private BigDecimal impCuantiaIncAnual;

	@Column(name="IMP_CUANTIA_MENSUAL")
	private BigDecimal impCuantiaMensual;

	@Column(name="IMP_CUANTIA_MENSUAL_IPT")
	private BigDecimal impCuantiaMensualIpt;

	@Column(name="IMP_CUANTIA_REDUCIDA")
	private BigDecimal impCuantiaReducida;

	@Column(name="IMP_DIF_INVALIDEZ_FICTICIA")
	private BigDecimal impDifInvalidezFicticia;

	@Column(name="IMP_DIF_MONTO_MINIMO_PMG")
	private BigDecimal impDifMontoMinimoPmg;

	@Column(name="IMP_INVALIDEZ_FICTICIA")
	private BigDecimal impInvalidezFicticia;

	@Column(name="IMP_MENSUAL_ASIGNACIONES")
	private BigDecimal impMensualAsignaciones;

	@Column(name="IMP_MENSUAL_AYUDA_ASISTENCIAL")
	private BigDecimal impMensualAyudaAsistencial;

	@Column(name="IMP_MENSUAL_TOPE")
	private BigDecimal impMensualTope;

	@Column(name="IMP_MONTO_MINIMO_PMG")
	private BigDecimal impMontoMinimoPmg;

	@Column(name="IMP_PENSION")
	private BigDecimal impPension;

	@Column(name="IMP_REFORMA_LEY")
	private BigDecimal impReformaLey;

	@Column(name="IMP_SALARIO_DIARIO")
	private BigDecimal impSalarioDiario;

	@Column(name="IMP_SALARIO_MINIMO_VIGENTE_DF")
	private BigDecimal impSalarioMinimoVigenteDf;

	@Column(name="NUM_FOLIO_DICTAMEN")
	private BigDecimal numFolioDictamen;

	@Column(name="NUM_INCREMENTOS")
	private BigDecimal numIncrementos;

	@Column(name="NUM_SEMANAS_COTIZADAS_31121990")
	private BigDecimal numSemanasCotizadas31121990;

	@Column(name="NUM_SEMANAS_REC_POST_500")
	private BigDecimal numSemanasRecPost500;

	@Column(name="NUM_SEMANAS_RECONOCIDA")
	private BigDecimal numSemanasReconocida;

	@Column(name="NUM_SEMANAS_RECONOCIDA_EXT")
	private BigDecimal numSemanasReconocidaExt;

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

	@Column(name="POR_VALUACION")
	private BigDecimal porValuacion;

	//bi-directional many-to-one association to SpcIncidencia
    @ManyToOne
	@JoinColumn(name="ID_INCIDENCIA")
	private SpcIncidencia spcIncidencia;

	//bi-directional many-to-one association to SpcReformaLey
    @ManyToOne
	@JoinColumn(name="ID_REFORMA_LEY")
	private SpcReformaLey spcReformaLey;

	//bi-directional many-to-one association to SpcTipoMovimiento
    @ManyToOne
	@JoinColumn(name="ID_TIPO_MOVIMIENTO")
	private SpcTipoMovimiento spcTipoMovimiento;

	//bi-directional many-to-one association to SptPension
    @ManyToOne
	@JoinColumn(name="CVE_ID_PENSION")
	private SptPension sptPension;

    public SptPensionMov() {
    }

	public long getCveIdPensionMov() {
		return this.cveIdPensionMov;
	}

	public void setCveIdPensionMov(long cveIdPensionMov) {
		this.cveIdPensionMov = cveIdPensionMov;
	}

	public BigDecimal getCvePeriodoNomina() {
		return this.cvePeriodoNomina;
	}

	public void setCvePeriodoNomina(BigDecimal cvePeriodoNomina) {
		this.cvePeriodoNomina = cvePeriodoNomina;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Date getFecInicioPension() {
		return this.fecInicioPension;
	}

	public void setFecInicioPension(Date fecInicioPension) {
		this.fecInicioPension = fecInicioPension;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public String getIdAntecedente() {
		return this.idAntecedente;
	}

	public void setIdAntecedente(String idAntecedente) {
		this.idAntecedente = idAntecedente;
	}

	public String getIdArticulo167() {
		return this.idArticulo167;
	}

	public void setIdArticulo167(String idArticulo167) {
		this.idArticulo167 = idArticulo167;
	}

	public String getIdCertificadoDerechos() {
		return this.idCertificadoDerechos;
	}

	public void setIdCertificadoDerechos(String idCertificadoDerechos) {
		this.idCertificadoDerechos = idCertificadoDerechos;
	}

	public Timestamp getIdFechaModificacion() {
		return this.idFechaModificacion;
	}

	public void setIdFechaModificacion(Timestamp idFechaModificacion) {
		this.idFechaModificacion = idFechaModificacion;
	}

	public String getIdNss() {
		return this.idNss;
	}

	public void setIdNss(String idNss) {
		this.idNss = idNss;
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

	public BigDecimal getImpCuantiaAnualVe() {
		return this.impCuantiaAnualVe;
	}

	public void setImpCuantiaAnualVe(BigDecimal impCuantiaAnualVe) {
		this.impCuantiaAnualVe = impCuantiaAnualVe;
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

	public BigDecimal getImpCuantiaIncAnual() {
		return this.impCuantiaIncAnual;
	}

	public void setImpCuantiaIncAnual(BigDecimal impCuantiaIncAnual) {
		this.impCuantiaIncAnual = impCuantiaIncAnual;
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

	public BigDecimal getImpPension() {
		return this.impPension;
	}

	public void setImpPension(BigDecimal impPension) {
		this.impPension = impPension;
	}

	public BigDecimal getImpReformaLey() {
		return this.impReformaLey;
	}

	public void setImpReformaLey(BigDecimal impReformaLey) {
		this.impReformaLey = impReformaLey;
	}

	public BigDecimal getImpSalarioDiario() {
		return this.impSalarioDiario;
	}

	public void setImpSalarioDiario(BigDecimal impSalarioDiario) {
		this.impSalarioDiario = impSalarioDiario;
	}

	public BigDecimal getImpSalarioMinimoVigenteDf() {
		return this.impSalarioMinimoVigenteDf;
	}

	public void setImpSalarioMinimoVigenteDf(BigDecimal impSalarioMinimoVigenteDf) {
		this.impSalarioMinimoVigenteDf = impSalarioMinimoVigenteDf;
	}

	public BigDecimal getNumFolioDictamen() {
		return this.numFolioDictamen;
	}

	public void setNumFolioDictamen(BigDecimal numFolioDictamen) {
		this.numFolioDictamen = numFolioDictamen;
	}

	public BigDecimal getNumIncrementos() {
		return this.numIncrementos;
	}

	public void setNumIncrementos(BigDecimal numIncrementos) {
		this.numIncrementos = numIncrementos;
	}

	public BigDecimal getNumSemanasCotizadas31121990() {
		return this.numSemanasCotizadas31121990;
	}

	public void setNumSemanasCotizadas31121990(BigDecimal numSemanasCotizadas31121990) {
		this.numSemanasCotizadas31121990 = numSemanasCotizadas31121990;
	}

	public BigDecimal getNumSemanasRecPost500() {
		return this.numSemanasRecPost500;
	}

	public void setNumSemanasRecPost500(BigDecimal numSemanasRecPost500) {
		this.numSemanasRecPost500 = numSemanasRecPost500;
	}

	public BigDecimal getNumSemanasReconocida() {
		return this.numSemanasReconocida;
	}

	public void setNumSemanasReconocida(BigDecimal numSemanasReconocida) {
		this.numSemanasReconocida = numSemanasReconocida;
	}

	public BigDecimal getNumSemanasReconocidaExt() {
		return this.numSemanasReconocidaExt;
	}

	public void setNumSemanasReconocidaExt(BigDecimal numSemanasReconocidaExt) {
		this.numSemanasReconocidaExt = numSemanasReconocidaExt;
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

	public BigDecimal getPorValuacion() {
		return this.porValuacion;
	}

	public void setPorValuacion(BigDecimal porValuacion) {
		this.porValuacion = porValuacion;
	}

	public SpcIncidencia getSpcIncidencia() {
		return this.spcIncidencia;
	}

	public void setSpcIncidencia(SpcIncidencia spcIncidencia) {
		this.spcIncidencia = spcIncidencia;
	}
	
	public SpcReformaLey getSpcReformaLey() {
		return this.spcReformaLey;
	}

	public void setSpcReformaLey(SpcReformaLey spcReformaLey) {
		this.spcReformaLey = spcReformaLey;
	}
	
	public SpcTipoMovimiento getSpcTipoMovimiento() {
		return this.spcTipoMovimiento;
	}

	public void setSpcTipoMovimiento(SpcTipoMovimiento spcTipoMovimiento) {
		this.spcTipoMovimiento = spcTipoMovimiento;
	}
	
	public SptPension getSptPension() {
		return this.sptPension;
	}

	public void setSptPension(SptPension sptPension) {
		this.sptPension = sptPension;
	}
	
}