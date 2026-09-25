package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_PENSION database table.
 * 
 */
@Entity
@Table(name="SPT_PENSION")
@NamedQuery(name="SptPension.findAll", query="SELECT s FROM SptPension s")
public class SptPension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTPENSION", sequenceName = "SEQ_SPTPENSION")
	@GeneratedValue(generator = "SEQ_SPTPENSION")
	@Column(name="CVE_ID_PENSION")
	private long cveIdPension;

	@Column(name="CVE_PERIODO_NOMINA")
	private BigDecimal cvePeriodoNomina;

	@Column(name="CVE_TIPO_GARANTIA")
	private String cveTipoGarantia;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

	private BigDecimal cveriesgotrabajo;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ACCIDENTE_ST3")
	private Date fecAccidenteSt3;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ALTA_PENSION")
	private Date fecAltaPension;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_BAJA_PENSION")
	private Date fecBajaPension;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_AJUSTE")
	private Date fecInicioAjuste;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PAGO")
	private Date fecInicioPago;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PENSION")
	private Date fecInicioPension;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REFORMA_LEY")
	private Date fecReformaLey;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_VENCIMIENTO_PENSION")
	private Date fecVencimientoPension;

	@Column(name="ID_ANTECEDENTE")
	private String idAntecedente;

	@Column(name="ID_ART_BASE_NEGATIVA_CONS")
	private String idArtBaseNegativaCons;

	@Column(name="ID_DICTAMEN_LAUDO")
	private BigDecimal idDictamenLaudo;

	@Column(name="ID_SOLICITUD_PENSION")
	private String idSolicitudPension;

	@Column(name="IMP_CUANTIA_MENSUAL_IPT_REEVAL")
	private BigDecimal impCuantiaMensualIptReeval;

	@Column(name="IMP_MONTO_CON_SEG_SOB")
	private BigDecimal impMontoConSegSob;

	@Column(name="IMP_PENSION_REEVALUACION")
	private BigDecimal impPensionReevaluacion;

	@Column(name="IMP_SALARIO_DIARIO")
	private BigDecimal impSalarioDiario;

	@Column(name="IND_INVALIDEZ_FICTICIA")
	private String indInvalidezFicticia;

	@Column(name="NUM_FOLIO_DICTAMEN")
	private BigDecimal numFolioDictamen;

	@Column(name="NUM_SEMANAS_COTIZADAS_31121990")
	private BigDecimal numSemanasCotizadas31121990;

	@Column(name="NUM_SEMANAS_RECONOCIDA")
	private BigDecimal numSemanasReconocida;

	@Column(name="NUM_SEMANAS_RECONOCIDA_EXT")
	private BigDecimal numSemanasReconocidaExt;

	@Column(name="POR_VALUACION")
	private BigDecimal porValuacion;

	//bi-directional many-to-one association to SptBeneficiariopensionPensio
	@OneToMany(mappedBy="sptPension")
	private List<SptBeneficiariopensionPensio> sptBeneficiariopensionPensios;

	//bi-directional many-to-one association to SpcEsquema
	@ManyToOne
	@JoinColumn(name="ID_ESQUEMA")
	private SpcEsquema spcEsquema;

	//bi-directional many-to-one association to SpcFormaPagoPension
	@ManyToOne
	@JoinColumn(name="ID_FORMA_PAGO_PENSION")
	private SpcFormaPagoPension spcFormaPagoPension;

	//bi-directional many-to-one association to SpcIncidencia
	@ManyToOne
	@JoinColumn(name="ID_INCIDENCIA")
	private SpcIncidencia spcIncidencia;

	//bi-directional many-to-one association to SpcModalidad
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="ID_MODALIDAD", referencedColumnName="ID_MODALIDAD"),
		@JoinColumn(name="ID_REGIMEN", referencedColumnName="ID_REGIMEN")
		})
	private SpcModalidad spcModalidad;

	//bi-directional many-to-one association to SpcRama
	@ManyToOne
	@JoinColumn(name="ID_RAMA")
	private SpcRama spcRama;

	//bi-directional many-to-one association to SpcReformaLey
	@ManyToOne
	@JoinColumn(name="ID_REFORMA_LEY")
	private SpcReformaLey spcReformaLey;

	//bi-directional many-to-one association to SpcRegimen
	@ManyToOne
	@JoinColumn(name="ID_REGIMEN" , insertable=false , updatable=false)
	private SpcRegimen spcRegimen;

	//bi-directional many-to-one association to SpcTipoJuicio
	@ManyToOne
	@JoinColumn(name="ID_TIPO_JUICIO")
	private SpcTipoJuicio spcTipoJuicio;

	//bi-directional many-to-one association to SpcTipoMovimiento
	@ManyToOne
	@JoinColumn(name="ID_TIPO_MOVIMIENTO")
	private SpcTipoMovimiento spcTipoMovimiento;

	//bi-directional many-to-one association to SpcTipoPension
	@ManyToOne
	@JoinColumn(name="ID_TIPO_PENSION")
	private SpcTipoPension spcTipoPension;

	//bi-directional many-to-one association to SptTramitePension
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

	//bi-directional many-to-one association to SptPensionCertificado
	@OneToMany(mappedBy="sptPension")
	private List<SptPensionCertificado> sptPensionCertificados;

	//bi-directional many-to-one association to SptPensionDerivada
	@OneToMany(mappedBy="sptPension1")
	private List<SptPensionDerivada> sptPensionDerivadas1;

	//bi-directional many-to-one association to SptPensionDerivada
	@OneToMany(mappedBy="sptPension2")
	private List<SptPensionDerivada> sptPensionDerivadas2;

	public SptPension() {
	}

	public long getCveIdPension() {
		return this.cveIdPension;
	}

	public void setCveIdPension(long cveIdPension) {
		this.cveIdPension = cveIdPension;
	}

	public BigDecimal getCvePeriodoNomina() {
		return this.cvePeriodoNomina;
	}

	public void setCvePeriodoNomina(BigDecimal cvePeriodoNomina) {
		this.cvePeriodoNomina = cvePeriodoNomina;
	}

	public String getCveTipoGarantia() {
		return this.cveTipoGarantia;
	}

	public void setCveTipoGarantia(String cveTipoGarantia) {
		this.cveTipoGarantia = cveTipoGarantia;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public BigDecimal getCveriesgotrabajo() {
		return this.cveriesgotrabajo;
	}

	public void setCveriesgotrabajo(BigDecimal cveriesgotrabajo) {
		this.cveriesgotrabajo = cveriesgotrabajo;
	}

	public Date getFecAccidenteSt3() {
		return this.fecAccidenteSt3;
	}

	public void setFecAccidenteSt3(Date fecAccidenteSt3) {
		this.fecAccidenteSt3 = fecAccidenteSt3;
	}

	public Date getFecAltaPension() {
		return this.fecAltaPension;
	}

	public void setFecAltaPension(Date fecAltaPension) {
		this.fecAltaPension = fecAltaPension;
	}

	public Date getFecBajaPension() {
		return this.fecBajaPension;
	}

	public void setFecBajaPension(Date fecBajaPension) {
		this.fecBajaPension = fecBajaPension;
	}

	public Date getFecInicioAjuste() {
		return this.fecInicioAjuste;
	}

	public void setFecInicioAjuste(Date fecInicioAjuste) {
		this.fecInicioAjuste = fecInicioAjuste;
	}

	public Date getFecInicioPago() {
		return this.fecInicioPago;
	}

	public void setFecInicioPago(Date fecInicioPago) {
		this.fecInicioPago = fecInicioPago;
	}

	public Date getFecInicioPension() {
		return this.fecInicioPension;
	}

	public void setFecInicioPension(Date fecInicioPension) {
		this.fecInicioPension = fecInicioPension;
	}

	public Date getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Date fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public Date getFecReformaLey() {
		return this.fecReformaLey;
	}

	public void setFecReformaLey(Date fecReformaLey) {
		this.fecReformaLey = fecReformaLey;
	}

	public Date getFecVencimientoPension() {
		return this.fecVencimientoPension;
	}

	public void setFecVencimientoPension(Date fecVencimientoPension) {
		this.fecVencimientoPension = fecVencimientoPension;
	}

	public String getIdAntecedente() {
		return this.idAntecedente;
	}

	public void setIdAntecedente(String idAntecedente) {
		this.idAntecedente = idAntecedente;
	}

	public String getIdArtBaseNegativaCons() {
		return this.idArtBaseNegativaCons;
	}

	public void setIdArtBaseNegativaCons(String idArtBaseNegativaCons) {
		this.idArtBaseNegativaCons = idArtBaseNegativaCons;
	}

	public BigDecimal getIdDictamenLaudo() {
		return this.idDictamenLaudo;
	}

	public void setIdDictamenLaudo(BigDecimal idDictamenLaudo) {
		this.idDictamenLaudo = idDictamenLaudo;
	}

	public String getIdSolicitudPension() {
		return this.idSolicitudPension;
	}

	public void setIdSolicitudPension(String idSolicitudPension) {
		this.idSolicitudPension = idSolicitudPension;
	}

	public BigDecimal getImpCuantiaMensualIptReeval() {
		return this.impCuantiaMensualIptReeval;
	}

	public void setImpCuantiaMensualIptReeval(BigDecimal impCuantiaMensualIptReeval) {
		this.impCuantiaMensualIptReeval = impCuantiaMensualIptReeval;
	}

	public BigDecimal getImpMontoConSegSob() {
		return this.impMontoConSegSob;
	}

	public void setImpMontoConSegSob(BigDecimal impMontoConSegSob) {
		this.impMontoConSegSob = impMontoConSegSob;
	}

	public BigDecimal getImpPensionReevaluacion() {
		return this.impPensionReevaluacion;
	}

	public void setImpPensionReevaluacion(BigDecimal impPensionReevaluacion) {
		this.impPensionReevaluacion = impPensionReevaluacion;
	}

	public BigDecimal getImpSalarioDiario() {
		return this.impSalarioDiario;
	}

	public void setImpSalarioDiario(BigDecimal impSalarioDiario) {
		this.impSalarioDiario = impSalarioDiario;
	}

	public String getIndInvalidezFicticia() {
		return this.indInvalidezFicticia;
	}

	public void setIndInvalidezFicticia(String indInvalidezFicticia) {
		this.indInvalidezFicticia = indInvalidezFicticia;
	}

	public BigDecimal getNumFolioDictamen() {
		return this.numFolioDictamen;
	}

	public void setNumFolioDictamen(BigDecimal numFolioDictamen) {
		this.numFolioDictamen = numFolioDictamen;
	}

	public BigDecimal getNumSemanasCotizadas31121990() {
		return this.numSemanasCotizadas31121990;
	}

	public void setNumSemanasCotizadas31121990(BigDecimal numSemanasCotizadas31121990) {
		this.numSemanasCotizadas31121990 = numSemanasCotizadas31121990;
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

	public BigDecimal getPorValuacion() {
		return this.porValuacion;
	}

	public void setPorValuacion(BigDecimal porValuacion) {
		this.porValuacion = porValuacion;
	}

	public List<SptBeneficiariopensionPensio> getSptBeneficiariopensionPensios() {
		return this.sptBeneficiariopensionPensios;
	}

	public void setSptBeneficiariopensionPensios(List<SptBeneficiariopensionPensio> sptBeneficiariopensionPensios) {
		this.sptBeneficiariopensionPensios = sptBeneficiariopensionPensios;
	}

	public SptBeneficiariopensionPensio addSptBeneficiariopensionPensio(SptBeneficiariopensionPensio sptBeneficiariopensionPensio) {
		getSptBeneficiariopensionPensios().add(sptBeneficiariopensionPensio);
		sptBeneficiariopensionPensio.setSptPension(this);

		return sptBeneficiariopensionPensio;
	}

	public SptBeneficiariopensionPensio removeSptBeneficiariopensionPensio(SptBeneficiariopensionPensio sptBeneficiariopensionPensio) {
		getSptBeneficiariopensionPensios().remove(sptBeneficiariopensionPensio);
		sptBeneficiariopensionPensio.setSptPension(null);

		return sptBeneficiariopensionPensio;
	}

	public SpcEsquema getSpcEsquema() {
		return this.spcEsquema;
	}

	public void setSpcEsquema(SpcEsquema spcEsquema) {
		this.spcEsquema = spcEsquema;
	}

	public SpcFormaPagoPension getSpcFormaPagoPension() {
		return this.spcFormaPagoPension;
	}

	public void setSpcFormaPagoPension(SpcFormaPagoPension spcFormaPagoPension) {
		this.spcFormaPagoPension = spcFormaPagoPension;
	}

	public SpcIncidencia getSpcIncidencia() {
		return this.spcIncidencia;
	}

	public void setSpcIncidencia(SpcIncidencia spcIncidencia) {
		this.spcIncidencia = spcIncidencia;
	}

	public SpcModalidad getSpcModalidad() {
		return this.spcModalidad;
	}

	public void setSpcModalidad(SpcModalidad spcModalidad) {
		this.spcModalidad = spcModalidad;
	}

	public SpcRama getSpcRama() {
		return this.spcRama;
	}

	public void setSpcRama(SpcRama spcRama) {
		this.spcRama = spcRama;
	}

	public SpcReformaLey getSpcReformaLey() {
		return this.spcReformaLey;
	}

	public void setSpcReformaLey(SpcReformaLey spcReformaLey) {
		this.spcReformaLey = spcReformaLey;
	}

	public SpcRegimen getSpcRegimen() {
		return this.spcRegimen;
	}

	public void setSpcRegimen(SpcRegimen spcRegimen) {
		this.spcRegimen = spcRegimen;
	}

	public SpcTipoJuicio getSpcTipoJuicio() {
		return this.spcTipoJuicio;
	}

	public void setSpcTipoJuicio(SpcTipoJuicio spcTipoJuicio) {
		this.spcTipoJuicio = spcTipoJuicio;
	}

	public SpcTipoMovimiento getSpcTipoMovimiento() {
		return this.spcTipoMovimiento;
	}

	public void setSpcTipoMovimiento(SpcTipoMovimiento spcTipoMovimiento) {
		this.spcTipoMovimiento = spcTipoMovimiento;
	}

	public SpcTipoPension getSpcTipoPension() {
		return this.spcTipoPension;
	}

	public void setSpcTipoPension(SpcTipoPension spcTipoPension) {
		this.spcTipoPension = spcTipoPension;
	}

	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}

	public List<SptPensionCertificado> getSptPensionCertificados() {
		return this.sptPensionCertificados;
	}

	public void setSptPensionCertificados(List<SptPensionCertificado> sptPensionCertificados) {
		this.sptPensionCertificados = sptPensionCertificados;
	}

	public SptPensionCertificado addSptPensionCertificado(SptPensionCertificado sptPensionCertificado) {
		getSptPensionCertificados().add(sptPensionCertificado);
		sptPensionCertificado.setSptPension(this);

		return sptPensionCertificado;
	}

	public SptPensionCertificado removeSptPensionCertificado(SptPensionCertificado sptPensionCertificado) {
		getSptPensionCertificados().remove(sptPensionCertificado);
		sptPensionCertificado.setSptPension(null);

		return sptPensionCertificado;
	}

	public List<SptPensionDerivada> getSptPensionDerivadas1() {
		return this.sptPensionDerivadas1;
	}

	public void setSptPensionDerivadas1(List<SptPensionDerivada> sptPensionDerivadas1) {
		this.sptPensionDerivadas1 = sptPensionDerivadas1;
	}

	public SptPensionDerivada addSptPensionDerivadas1(SptPensionDerivada sptPensionDerivadas1) {
		getSptPensionDerivadas1().add(sptPensionDerivadas1);
		sptPensionDerivadas1.setSptPension1(this);

		return sptPensionDerivadas1;
	}

	public SptPensionDerivada removeSptPensionDerivadas1(SptPensionDerivada sptPensionDerivadas1) {
		getSptPensionDerivadas1().remove(sptPensionDerivadas1);
		sptPensionDerivadas1.setSptPension1(null);

		return sptPensionDerivadas1;
	}

	public List<SptPensionDerivada> getSptPensionDerivadas2() {
		return this.sptPensionDerivadas2;
	}

	public void setSptPensionDerivadas2(List<SptPensionDerivada> sptPensionDerivadas2) {
		this.sptPensionDerivadas2 = sptPensionDerivadas2;
	}

	public SptPensionDerivada addSptPensionDerivadas2(SptPensionDerivada sptPensionDerivadas2) {
		getSptPensionDerivadas2().add(sptPensionDerivadas2);
		sptPensionDerivadas2.setSptPension2(this);

		return sptPensionDerivadas2;
	}

	public SptPensionDerivada removeSptPensionDerivadas2(SptPensionDerivada sptPensionDerivadas2) {
		getSptPensionDerivadas2().remove(sptPensionDerivadas2);
		sptPensionDerivadas2.setSptPension2(null);

		return sptPensionDerivadas2;
	}

}