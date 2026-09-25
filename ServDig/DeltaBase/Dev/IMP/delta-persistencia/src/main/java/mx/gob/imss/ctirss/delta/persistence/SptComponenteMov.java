package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SpcTipoMovimiento;
import mx.gob.imss.ctirss.delta.persistence.SptTramitePension;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_COMPONENTE_MOV database table.
 * 
 */
@Entity
@Table(name="SPT_COMPONENTE_MOV")
public class SptComponenteMov implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_SPTCOMPONENTEMOV", sequenceName = "SEQ_SPTCOMPONENTEMOV")
	@GeneratedValue(generator = "SEQ_SPTCOMPONENTEMOV")
	@Column(name="CVE_ID_COMPONENTE_MOV")
	private long cveIdComponenteMov;

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

	@Column(name="ID_COMPONENTE")
	private String idComponente;

	@Column(name="ID_FECHA_MODIFICACION")
	private Timestamp idFechaModificacion;

	@Column(name="ID_NSS")
	private String idNss;

	@Column(name="ID_PARENTESCO")
	private String idParentesco;

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

	//bi-directional many-to-one association to SpcCalendarioEscolar
    @ManyToOne
	@JoinColumn(name="ID_CALENDARIO_ESCOLAR")
	private SpcCalendarioEscolar spcCalendarioEscolar;

	//bi-directional many-to-one association to SpcIncidencia
    @ManyToOne
	@JoinColumn(name="ID_INCIDENCIA")
	private SpcIncidencia spcIncidencia;

	//bi-directional many-to-one association to SpcNivelEstudio
    @ManyToOne
	@JoinColumn(name="ID_NIVEL_ESTUDIOS")
	private SpcNivelEstudio spcNivelEstudio;

	//bi-directional many-to-one association to SpcNumeroPeriodoEscolar
    @ManyToOne
	@JoinColumn(name="ID_NUM_PERIODO_ESCOLAR")
	private SpcNumeroPeriodoEscolar spcNumeroPeriodoEscolar;

	//bi-directional many-to-one association to SpcOrfandad
    @ManyToOne
	@JoinColumn(name="ID_ORFANDAD")
	private SpcOrfandad spcOrfandad;

	//bi-directional many-to-one association to SpcPeriodoEscolar
    @ManyToOne
	@JoinColumn(name="ID_PERIODO_ESCOLAR")
	private SpcPeriodoEscolar spcPeriodoEscolar;

	//bi-directional many-to-one association to SpcReformaLey
    @ManyToOne
	@JoinColumn(name="ID_REFORMA_LEY")
	private SpcReformaLey spcReformaLey;

	//bi-directional many-to-one association to SpcTipoMovimiento
    @ManyToOne
	@JoinColumn(name="ID_TIPO_MOVIMIENTO")
	private SpcTipoMovimiento spcTipoMovimiento;

	//bi-directional many-to-one association to SptTramitePension
    @ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

    public SptComponenteMov() {
    }

	public long getCveIdComponenteMov() {
		return this.cveIdComponenteMov;
	}

	public void setCveIdComponenteMov(long cveIdComponenteMov) {
		this.cveIdComponenteMov = cveIdComponenteMov;
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

	public String getIdNss() {
		return this.idNss;
	}

	public void setIdNss(String idNss) {
		this.idNss = idNss;
	}

	public String getIdParentesco() {
		return this.idParentesco;
	}

	public void setIdParentesco(String idParentesco) {
		this.idParentesco = idParentesco;
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

	public SpcCalendarioEscolar getSpcCalendarioEscolar() {
		return this.spcCalendarioEscolar;
	}

	public void setSpcCalendarioEscolar(SpcCalendarioEscolar spcCalendarioEscolar) {
		this.spcCalendarioEscolar = spcCalendarioEscolar;
	}
	
	public SpcIncidencia getSpcIncidencia() {
		return this.spcIncidencia;
	}

	public void setSpcIncidencia(SpcIncidencia spcIncidencia) {
		this.spcIncidencia = spcIncidencia;
	}
	
	public SpcNivelEstudio getSpcNivelEstudio() {
		return this.spcNivelEstudio;
	}

	public void setSpcNivelEstudio(SpcNivelEstudio spcNivelEstudio) {
		this.spcNivelEstudio = spcNivelEstudio;
	}
	
	public SpcNumeroPeriodoEscolar getSpcNumeroPeriodoEscolar() {
		return this.spcNumeroPeriodoEscolar;
	}

	public void setSpcNumeroPeriodoEscolar(SpcNumeroPeriodoEscolar spcNumeroPeriodoEscolar) {
		this.spcNumeroPeriodoEscolar = spcNumeroPeriodoEscolar;
	}
	
	public SpcOrfandad getSpcOrfandad() {
		return this.spcOrfandad;
	}

	public void setSpcOrfandad(SpcOrfandad spcOrfandad) {
		this.spcOrfandad = spcOrfandad;
	}
	
	public SpcPeriodoEscolar getSpcPeriodoEscolar() {
		return this.spcPeriodoEscolar;
	}

	public void setSpcPeriodoEscolar(SpcPeriodoEscolar spcPeriodoEscolar) {
		this.spcPeriodoEscolar = spcPeriodoEscolar;
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
	
	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}
	
}