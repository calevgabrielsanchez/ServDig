package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APT_CALCULO_CUANTIAS_SOL_IMSS database table.
 * 
 */
@Entity
@Table(name="APT_CALCULO_CUANTIAS_SOL_IMSS")
@NamedQuery(name="AptCalculoCuantiasSolImss.findAll", query="SELECT a FROM AptCalculoCuantiasSolImss a")
public class AptCalculoCuantiasSolImss implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id  
	@SequenceGenerator(name = "SEQ_APTCALCULOCUANTIASSOLIMSS", sequenceName = "SEQ_APTCALCULOCUANTIASSOLIMSS")
	@GeneratedValue(generator = "SEQ_APTCALCULOCUANTIASSOLIMSS")
	@Column(name="CVE_ID_CALCULO_CUANTIAS_SOL_IM")
	private long cveIdCalculoCuantiasSolIm;

	@Column(name="CVE_REGIMEN")
	private String cveRegimen;

	@Column(name="CVE_TIPO_ART_167")
	private String cveTipoArt167;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_CALCULO")
	private Date fecCalculo;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REFORMA_LEY_AFILIADO")
	private Date fecReformaLeyAfiliado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_REFORMA_LEY")
	private String idReformaLey;

	@Column(name="ID_REFORMA_LEY_AFILIADO")
	private String idReformaLeyAfiliado;

	@Column(name="IMP_AYUDA_ASISTENCIAL")
	private BigDecimal impAyudaAsistencial;

	@Column(name="IMP_AYUDA_SOLEDAD")
	private BigDecimal impAyudaSoledad;

	@Column(name="IMP_AYUDA_UNICO_ASCENDIENTE")
	private BigDecimal impAyudaUnicoAscendiente;

	@Column(name="IMP_CTIA_MENS_IPT_REV")
	private BigDecimal impCtiaMensIptRev;

	@Column(name="IMP_CUANTIA_ANUAL")
	private BigDecimal impCuantiaAnual;

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

	@Column(name="IMP_CUANTIA_MENSUAL_VE")
	private BigDecimal impCuantiaMensualVe;

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

	@Column(name="IMP_PENSION_REVALUACION")
	private BigDecimal impPensionRevaluacion;

	@Column(name="IMP_REFORMA_LEY")
	private BigDecimal impReformaLey;

	@Column(name="IMP_SALARIO_MINIMO_VIGENTE_DF")
	private BigDecimal impSalarioMinimoVigenteDf;

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

	@Column(name="IMP_PENSION_SAOR")
	private BigDecimal impPensionSaor;

	@Column(name="CVE_CUENTA_USUARIO")
	private String cveCuentaUsuario;

	//bi-directional many-to-one association to SptTramitePension
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

	public AptCalculoCuantiasSolImss() {
	}

	public long getCveIdCalculoCuantiasSolIm() {
		return this.cveIdCalculoCuantiasSolIm;
	}

	public void setCveIdCalculoCuantiasSolIm(long cveIdCalculoCuantiasSolIm) {
		this.cveIdCalculoCuantiasSolIm = cveIdCalculoCuantiasSolIm;
	}

	public String getCveRegimen() {
		return this.cveRegimen;
	}

	public void setCveRegimen(String cveRegimen) {
		this.cveRegimen = cveRegimen;
	}

	public String getCveTipoArt167() {
		return this.cveTipoArt167;
	}

	public void setCveTipoArt167(String cveTipoArt167) {
		this.cveTipoArt167 = cveTipoArt167;
	}

	public Date getFecCalculo() {
		return this.fecCalculo;
	}

	public void setFecCalculo(Date fecCalculo) {
		this.fecCalculo = fecCalculo;
	}

	public Date getFecReformaLeyAfiliado() {
		return this.fecReformaLeyAfiliado;
	}

	public void setFecReformaLeyAfiliado(Date fecReformaLeyAfiliado) {
		this.fecReformaLeyAfiliado = fecReformaLeyAfiliado;
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

	public String getIdReformaLey() {
		return this.idReformaLey;
	}

	public void setIdReformaLey(String idReformaLey) {
		this.idReformaLey = idReformaLey;
	}

	public String getIdReformaLeyAfiliado() {
		return this.idReformaLeyAfiliado;
	}

	public void setIdReformaLeyAfiliado(String idReformaLeyAfiliado) {
		this.idReformaLeyAfiliado = idReformaLeyAfiliado;
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

	public BigDecimal getImpCtiaMensIptRev() {
		return this.impCtiaMensIptRev;
	}

	public void setImpCtiaMensIptRev(BigDecimal impCtiaMensIptRev) {
		this.impCtiaMensIptRev = impCtiaMensIptRev;
	}

	public BigDecimal getImpCuantiaAnual() {
		return this.impCuantiaAnual;
	}

	public void setImpCuantiaAnual(BigDecimal impCuantiaAnual) {
		this.impCuantiaAnual = impCuantiaAnual;
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

	public BigDecimal getImpCuantiaMensualVe() {
		return this.impCuantiaMensualVe;
	}

	public void setImpCuantiaMensualVe(BigDecimal impCuantiaMensualVe) {
		this.impCuantiaMensualVe = impCuantiaMensualVe;
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

	public BigDecimal getImpPensionRevaluacion() {
		return this.impPensionRevaluacion;
	}

	public void setImpPensionRevaluacion(BigDecimal impPensionRevaluacion) {
		this.impPensionRevaluacion = impPensionRevaluacion;
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

	public BigDecimal getImpPensionSaor() {
		return impPensionSaor;
	}

	public void setImpPensionSaor(BigDecimal impPensionSaor) {
		this.impPensionSaor = impPensionSaor;
	}

	public String getCveCuentaUsuario() {
		return cveCuentaUsuario;
	}

	public void setCveCuentaUsuario(String cveCuentaUsuario) {
		this.cveCuentaUsuario = cveCuentaUsuario;
	}

	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}

}
