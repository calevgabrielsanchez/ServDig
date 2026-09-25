package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the V_PENSIONES_NSS database table.
 * 
 */
@Entity
@Table(name = "V_PENSIONES_NSS")
public class VPensionesNss implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VPensionesNssPK id;

	@Column(name = "CVE_ID_TRAMITE_PENSION")
	private BigDecimal cveIdTramitePension;

	@Column(name = "CVE_PERIODO_NOMINA")
	private BigDecimal cvePeriodoNomina;

	@Column(name = "CVE_TIPO_GARANTIA")
	private String cveTipoGarantia;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	private BigDecimal cveriesgotrabajo;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_ACCIDENTE_ST3")
	private Date fecAccidenteSt3;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_ALTA_PENSION")
	private Date fecAltaPension;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_BAJA_PENSION")
	private Date fecBajaPension;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_INICIO_AJUSTE")
	private Date fecInicioAjuste;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_INICIO_PAGO")
	private Date fecInicioPago;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_INICIO_PENSION")
	private Date fecInicioPension;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_MODIFICACION")
	private Date fecModificacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REFORMA_LEY")
	private Date fecReformaLey;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_VENCIMIENTO_PENSION")
	private Date fecVencimientoPension;

	@Column(name = "ID_ANTECEDENTE")
	private String idAntecedente;

	@Column(name = "ID_ART_BASE_NEGATIVA_CONS")
	private String idArtBaseNegativaCons;

	@Column(name = "ID_DICTAMEN_LAUDO")
	private BigDecimal idDictamenLaudo;

	@Column(name = "ID_ESQUEMA")
	private String idEsquema;

	@Column(name = "ID_FORMA_PAGO_PENSION")
	private String idFormaPagoPension;

	@Column(name = "ID_INCIDENCIA")
	private String idIncidencia;

	@Column(name = "ID_MODALIDAD")
	private String idModalidad;

	@Column(name = "ID_RAMA")
	private String idRama;

	@Column(name = "ID_REFORMA_LEY")
	private String idReformaLey;

	@Column(name = "ID_REGIMEN")
	private String idRegimen;

	@Column(name = "ID_SOLICITUD_PENSION")
	private String idSolicitudPension;

	@Column(name = "ID_SOLICITUD_SPTTRAMITEPENSION")
	private String idSolicitudSpttramitepension;

	@Column(name = "ID_TIPO_JUICIO")
	private String idTipoJuicio;

	@Column(name = "ID_TIPO_MOVIMIENTO")
	private String idTipoMovimiento;

	@Column(name = "ID_TIPO_PENSION")
	private String idTipoPension;

	@Column(name = "IMP_CUANTIA_MENSUAL_IPT_REEVAL")
	private BigDecimal impCuantiaMensualIptReeval;

	@Column(name = "IMP_MONTO_CON_SEG_SOB")
	private BigDecimal impMontoConSegSob;

	@Column(name = "IMP_PENSION_REEVALUACION")
	private BigDecimal impPensionReevaluacion;

	@Column(name = "IMP_SALARIO_DIARIO")
	private BigDecimal impSalarioDiario;

	@Column(name = "IND_INVALIDEZ_FICTICIA")
	private String indInvalidezFicticia;

	@Column(name = "NUM_FOLIO_DICTAMEN")
	private BigDecimal numFolioDictamen;

	@Column(name = "NUM_SEMANAS_COTIZADAS_31121990")
	private BigDecimal numSemanasCotizadas31121990;

	@Column(name = "NUM_SEMANAS_RECONOCIDA")
	private BigDecimal numSemanasReconocida;

	@Column(name = "NUM_SEMANAS_RECONOCIDA_EXT")
	private BigDecimal numSemanasReconocidaExt;

	@Column(name = "POR_VALUACION")
	private BigDecimal porValuacion;

	public VPensionesNss() {
	}

	public VPensionesNssPK getId() {
		return id;
	}

	public void setId(VPensionesNssPK id) {
		this.id = id;
	}

	public BigDecimal getCveIdTramitePension() {
		return this.cveIdTramitePension;
	}

	public void setCveIdTramitePension(BigDecimal cveIdTramitePension) {
		this.cveIdTramitePension = cveIdTramitePension;
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

	public String getIdEsquema() {
		return this.idEsquema;
	}

	public void setIdEsquema(String idEsquema) {
		this.idEsquema = idEsquema;
	}

	public String getIdFormaPagoPension() {
		return this.idFormaPagoPension;
	}

	public void setIdFormaPagoPension(String idFormaPagoPension) {
		this.idFormaPagoPension = idFormaPagoPension;
	}

	public String getIdIncidencia() {
		return this.idIncidencia;
	}

	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public String getIdModalidad() {
		return this.idModalidad;
	}

	public void setIdModalidad(String idModalidad) {
		this.idModalidad = idModalidad;
	}

	public String getIdRama() {
		return this.idRama;
	}

	public void setIdRama(String idRama) {
		this.idRama = idRama;
	}

	public String getIdReformaLey() {
		return this.idReformaLey;
	}

	public void setIdReformaLey(String idReformaLey) {
		this.idReformaLey = idReformaLey;
	}

	public String getIdRegimen() {
		return this.idRegimen;
	}

	public void setIdRegimen(String idRegimen) {
		this.idRegimen = idRegimen;
	}

	public String getIdSolicitudPension() {
		return this.idSolicitudPension;
	}

	public void setIdSolicitudPension(String idSolicitudPension) {
		this.idSolicitudPension = idSolicitudPension;
	}

	public String getIdSolicitudSpttramitepension() {
		return this.idSolicitudSpttramitepension;
	}

	public void setIdSolicitudSpttramitepension(
			String idSolicitudSpttramitepension) {
		this.idSolicitudSpttramitepension = idSolicitudSpttramitepension;
	}

	public String getIdTipoJuicio() {
		return this.idTipoJuicio;
	}

	public void setIdTipoJuicio(String idTipoJuicio) {
		this.idTipoJuicio = idTipoJuicio;
	}

	public String getIdTipoMovimiento() {
		return this.idTipoMovimiento;
	}

	public void setIdTipoMovimiento(String idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}

	public String getIdTipoPension() {
		return this.idTipoPension;
	}

	public void setIdTipoPension(String idTipoPension) {
		this.idTipoPension = idTipoPension;
	}

	public BigDecimal getImpCuantiaMensualIptReeval() {
		return this.impCuantiaMensualIptReeval;
	}

	public void setImpCuantiaMensualIptReeval(
			BigDecimal impCuantiaMensualIptReeval) {
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

	public void setNumSemanasCotizadas31121990(
			BigDecimal numSemanasCotizadas31121990) {
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

}