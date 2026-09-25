package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_BENEFICIARIO_SOLICITUD_MOV database table.
 * 
 */
@Entity
@Table(name="SPT_BENEFICIARIO_SOLICITUD_MOV")
@NamedQuery(name="SptBeneficiarioSolicitudMov.findAll", query="SELECT s FROM SptBeneficiarioSolicitudMov s")
public class SptBeneficiarioSolicitudMov implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTBENEFICIARIOSOLICITUDMO", sequenceName = "SEQ_SPTBENEFICIARIOSOLICITUDMO")
	@GeneratedValue(generator = "SEQ_SPTBENEFICIARIOSOLICITUDMO")
	@Column(name="CVE_ID_COMPONENTE_MOV")
	private long cveIdComponenteMov;

	@Column(name="CVE_ID_CABECERA_SOLIC_PENSION")
	private BigDecimal cveIdCabeceraSolicPension;

	@Column(name="CVE_PERIODO_NOMINA")
	private BigDecimal cvePeriodoNomina;

	@Column(name="CVE_SEXO")
	private BigDecimal cveSexo;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_NACIMIENTO")
	private Date fecNacimiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REFORMA_LEY")
	private Date fecReformaLey;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_VENCIMIENTO")
	private Date fecVencimiento;

	@Column(name="ID_CALENDARIO_ESCOLAR")
	private String idCalendarioEscolar;

	@Column(name="ID_COMPONENTE")
	private String idComponente;

	@Column(name="ID_FECHA_MODIFICACION")
	private Timestamp idFechaModificacion;

	@Column(name="ID_NIVEL_ESTUDIOS")
	private String idNivelEstudios;

	@Column(name="ID_NSS")
	private String idNss;

	@Column(name="ID_NUM_PERIODO_ESCOLAR")
	private String idNumPeriodoEscolar;

	@Column(name="ID_ORFANDAD")
	private String idOrfandad;

	@Column(name="ID_PARENTESCO")
	private String idParentesco;

	@Column(name="ID_PERIODO_ESCOLAR")
	private String idPeriodoEscolar;

	@Column(name="ID_TIPO_MOVIMIENTO")
	private String idTipoMovimiento;

	@Column(name="IMP_AYUDA_ASISTENCIAL")
	private BigDecimal impAyudaAsistencial;

	@Column(name="IMP_CUANTIA_MENSUAL")
	private BigDecimal impCuantiaMensual;

	@Column(name="IMP_DIF_MONTO_MIN_VIUDEZ")
	private BigDecimal impDifMontoMinViudez;

	@Column(name="IMP_DIF_MONTO_MINIMO_PMG")
	private BigDecimal impDifMontoMinimoPmg;

	@Column(name="IMP_MONTO_MIN_VIUDEZ")
	private BigDecimal impMontoMinViudez;

	@Column(name="IMP_REFORMA_LEY")
	private BigDecimal impReformaLey;

	@Column(name="IND_PENSION_FONDO_ESPE")
	private String indPensionFondoEspe;

	@Column(name="NUM_FOLIO_DICTAMEN")
	private BigDecimal numFolioDictamen;

	@Column(name="POR_ASIGNACION_LEY")
	private BigDecimal porAsignacionLey;

	@Column(name="POR_AYUDA_ASISTENCIAL")
	private BigDecimal porAyudaAsistencial;

	//bi-directional many-to-one association to SpcIncidencia
    @ManyToOne
	@JoinColumn(name="ID_INCIDENCIA")
	private SpcIncidencia spcIncidencia;

	//bi-directional many-to-one association to SptBeneficiarioSolicitud
    @ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_SOLICITUD")
	private SptBeneficiarioSolicitud sptBeneficiarioSolicitud;

	//bi-directional many-to-one association to SpcReformaLey
    @ManyToOne
	@JoinColumn(name="ID_REFORMA_LEY")
	private SpcReformaLey spcReformaLey;

    public SptBeneficiarioSolicitudMov() {
    }

	public long getCveIdComponenteMov() {
		return this.cveIdComponenteMov;
	}

	public void setCveIdComponenteMov(long cveIdComponenteMov) {
		this.cveIdComponenteMov = cveIdComponenteMov;
	}

	public BigDecimal getCveIdCabeceraSolicPension() {
		return this.cveIdCabeceraSolicPension;
	}

	public void setCveIdCabeceraSolicPension(BigDecimal cveIdCabeceraSolicPension) {
		this.cveIdCabeceraSolicPension = cveIdCabeceraSolicPension;
	}

	public BigDecimal getCvePeriodoNomina() {
		return this.cvePeriodoNomina;
	}

	public void setCvePeriodoNomina(BigDecimal cvePeriodoNomina) {
		this.cvePeriodoNomina = cvePeriodoNomina;
	}

	public BigDecimal getCveSexo() {
		return this.cveSexo;
	}

	public void setCveSexo(BigDecimal cveSexo) {
		this.cveSexo = cveSexo;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public Date getFecNacimiento() {
		return this.fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public Date getFecReformaLey() {
		return this.fecReformaLey;
	}

	public void setFecReformaLey(Date fecReformaLey) {
		this.fecReformaLey = fecReformaLey;
	}

	public Date getFecVencimiento() {
		return this.fecVencimiento;
	}

	public void setFecVencimiento(Date fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}

	public String getIdCalendarioEscolar() {
		return this.idCalendarioEscolar;
	}

	public void setIdCalendarioEscolar(String idCalendarioEscolar) {
		this.idCalendarioEscolar = idCalendarioEscolar;
	}

	public String getIdComponente() {
		return this.idComponente;
	}

	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}

	public Timestamp getIdFechaModificacion() {
		return this.idFechaModificacion;
	}

	public void setIdFechaModificacion(Timestamp idFechaModificacion) {
		this.idFechaModificacion = idFechaModificacion;
	}

	public String getIdNivelEstudios() {
		return this.idNivelEstudios;
	}

	public void setIdNivelEstudios(String idNivelEstudios) {
		this.idNivelEstudios = idNivelEstudios;
	}

	public String getIdNss() {
		return this.idNss;
	}

	public void setIdNss(String idNss) {
		this.idNss = idNss;
	}

	public String getIdNumPeriodoEscolar() {
		return this.idNumPeriodoEscolar;
	}

	public void setIdNumPeriodoEscolar(String idNumPeriodoEscolar) {
		this.idNumPeriodoEscolar = idNumPeriodoEscolar;
	}

	public String getIdOrfandad() {
		return this.idOrfandad;
	}

	public void setIdOrfandad(String idOrfandad) {
		this.idOrfandad = idOrfandad;
	}

	public String getIdParentesco() {
		return this.idParentesco;
	}

	public void setIdParentesco(String idParentesco) {
		this.idParentesco = idParentesco;
	}

	public String getIdPeriodoEscolar() {
		return this.idPeriodoEscolar;
	}

	public void setIdPeriodoEscolar(String idPeriodoEscolar) {
		this.idPeriodoEscolar = idPeriodoEscolar;
	}

	public String getIdTipoMovimiento() {
		return this.idTipoMovimiento;
	}

	public void setIdTipoMovimiento(String idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}

	public BigDecimal getImpAyudaAsistencial() {
		return this.impAyudaAsistencial;
	}

	public void setImpAyudaAsistencial(BigDecimal impAyudaAsistencial) {
		this.impAyudaAsistencial = impAyudaAsistencial;
	}

	public BigDecimal getImpCuantiaMensual() {
		return this.impCuantiaMensual;
	}

	public void setImpCuantiaMensual(BigDecimal impCuantiaMensual) {
		this.impCuantiaMensual = impCuantiaMensual;
	}

	public BigDecimal getImpDifMontoMinViudez() {
		return this.impDifMontoMinViudez;
	}

	public void setImpDifMontoMinViudez(BigDecimal impDifMontoMinViudez) {
		this.impDifMontoMinViudez = impDifMontoMinViudez;
	}

	public BigDecimal getImpDifMontoMinimoPmg() {
		return this.impDifMontoMinimoPmg;
	}

	public void setImpDifMontoMinimoPmg(BigDecimal impDifMontoMinimoPmg) {
		this.impDifMontoMinimoPmg = impDifMontoMinimoPmg;
	}

	public BigDecimal getImpMontoMinViudez() {
		return this.impMontoMinViudez;
	}

	public void setImpMontoMinViudez(BigDecimal impMontoMinViudez) {
		this.impMontoMinViudez = impMontoMinViudez;
	}

	public BigDecimal getImpReformaLey() {
		return this.impReformaLey;
	}

	public void setImpReformaLey(BigDecimal impReformaLey) {
		this.impReformaLey = impReformaLey;
	}

	public String getIndPensionFondoEspe() {
		return this.indPensionFondoEspe;
	}

	public void setIndPensionFondoEspe(String indPensionFondoEspe) {
		this.indPensionFondoEspe = indPensionFondoEspe;
	}

	public BigDecimal getNumFolioDictamen() {
		return this.numFolioDictamen;
	}

	public void setNumFolioDictamen(BigDecimal numFolioDictamen) {
		this.numFolioDictamen = numFolioDictamen;
	}

	public BigDecimal getPorAsignacionLey() {
		return this.porAsignacionLey;
	}

	public void setPorAsignacionLey(BigDecimal porAsignacionLey) {
		this.porAsignacionLey = porAsignacionLey;
	}

	public BigDecimal getPorAyudaAsistencial() {
		return this.porAyudaAsistencial;
	}

	public void setPorAyudaAsistencial(BigDecimal porAyudaAsistencial) {
		this.porAyudaAsistencial = porAyudaAsistencial;
	}

	public SpcIncidencia getSpcIncidencia() {
		return this.spcIncidencia;
	}

	public void setSpcIncidencia(SpcIncidencia spcIncidencia) {
		this.spcIncidencia = spcIncidencia;
	}
	
	public SptBeneficiarioSolicitud getSptBeneficiarioSolicitud() {
		return this.sptBeneficiarioSolicitud;
	}

	public void setSptBeneficiarioSolicitud(SptBeneficiarioSolicitud sptBeneficiarioSolicitud) {
		this.sptBeneficiarioSolicitud = sptBeneficiarioSolicitud;
	}
	
	public SpcReformaLey getSpcReformaLey() {
		return this.spcReformaLey;
	}

	public void setSpcReformaLey(SpcReformaLey spcReformaLey) {
		this.spcReformaLey = spcReformaLey;
	}
	
}