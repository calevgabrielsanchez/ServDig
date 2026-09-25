package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_CERTIFICADO_DERECHOS database table..
 * 
 */
@Entity
@Table(name="SPT_CERTIFICADO_DERECHOS")
@NamedQuery(name="SptCertificadoDerecho.findAll", query="SELECT s FROM SptCertificadoDerecho s")
public class SptCertificadoDerecho implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTCERTIFICADODERECHOS", sequenceName = "SEQ_SPTCERTIFICADODERECHOS")
	@GeneratedValue(generator = "SEQ_SPTCERTIFICADODERECHOS")
	@Column(name="CVE_ID_CERTIFICADO_DERECHOS")
	private long cveIdCertificadoDerechos;

	@Column(name="CVE_CTO_COSTO")
	private String cveCtoCosto;

	@Column(name="CVE_CURP")
	private String cveCurp;

	@Column(name="CVE_DELEGACION")
	private String cveDelegacion;

	@Column(name="CVE_PREI")
	private String cvePrei;

	@Column(name="CVE_PRESTACION_SOLICITADA")
	private String cvePrestacionSolicitada;

	@Column(name="CVE_REGISTRO_PATRONAL")
	private String cveRegistroPatronal;

	@Column(name="CVE_SEXO")
	private BigDecimal cveSexo;

	@Column(name="CVE_SUBDELEGACION")
	private String cveSubdelegacion;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

	@Column(name="CVE_USUARIO_SINDO")
	private String cveUsuarioSindo;

	@Column(name="DES_FUND_LEGAL_NEGATIVA_73")
	private String desFundLegalNegativa73;

	@Column(name="DES_FUND_LEGAL_NEGATIVA_97")
	private String desFundLegalNegativa97;

	@Column(name="DES_FUND_LEGAL_NEGATIVA_972")
	private String desFundLegalNegativa972;

	@Column(name="DES_OBSERVACIONES_73")
	private String desObservaciones73;

	@Column(name="DES_OBSERVACIONES_97")
	private String desObservaciones97;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_BAJA_ULTIMO_PERIODO_73")
	private Date fecBajaUltimoPeriodo73;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_BAJA_ULTIMO_PERIODO_97")
	private Date fecBajaUltimoPeriodo97;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_CERTIFICACION")
	private Date fecCertificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_CONSERVACION_DERECHOS_73")
	private Date fecConservacionDerechos73;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_NACIMIENTO")
	private Date fecNacimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_SINIESTRO")
	private Date fecSiniestro;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_SOLICITUD_CERTIFICACION")
	private Date fecSolicitudCertificacion;

	@Column(name="ID_CERTIFICADO_DERECHOS")
	private String idCertificadoDerechos;

	@Column(name="ID_MODALIDAD")
	private String idModalidad;

	@Column(name="ID_REGIMEN")
	private String idRegimen;

	@Column(name="ID_TIPO_PENSION")
	private String idTipoPension;

	@Column(name="IMP_SALARIO_PROMEDIO_73")
	private BigDecimal impSalarioPromedio73;

	@Column(name="IMP_SALARIO_PROMEDIO_97")
	private BigDecimal impSalarioPromedio97;

	@Column(name="IMP_SALARIO_REGISTRADO_97")
	private BigDecimal impSalarioRegistrado97;

	@Column(name="IND_CONVENIO_OTRO_PAIS")
	private String indConvenioOtroPais;

	@Column(name="IND_DERECHO_REGIMEN_73")
	private String indDerechoRegimen73;

	@Column(name="IND_DERECHO_REGIMEN_97")
	private String indDerechoRegimen97;

	@Column(name="IND_DERECHO_REGIMEN_972")
	private String indDerechoRegimen972;

	@Column(name="IND_FERROCARRILERO_CONVENIO")
	private String indFerrocarrileroConvenio;

	@Column(name="IND_TRABAJADOR_IMSS")
	private String indTrabajadorImss;

	@Column(name="NOM_APELLIDO_MATERNO_AFILIADO")
	private String nomApellidoMaternoAfiliado;

	@Column(name="NOM_APELLIDO_PATERNO_AFILIADO")
	private String nomApellidoPaternoAfiliado;

	@Column(name="NOM_NOMBRE_AFILIADO")
	private String nomNombreAfiliado;

	@Column(name="NUM_DIAS_SUBSIDIADOS")
	private BigDecimal numDiasSubsidiados;

	@Column(name="NUM_INCREMENTO_ANUAL_73")
	private BigDecimal numIncrementoAnual73;

	@Column(name="NUM_SEMANAS_COTIZADAS_31121990")
	private BigDecimal numSemanasCotizadas31121990;

	@Column(name="NUM_SEMANAS_DESCONTADAS")
	private BigDecimal numSemanasDescontadas;

	@Column(name="NUM_SEMANAS_EXTRANJERO")
	private BigDecimal numSemanasExtranjero;

	@Column(name="NUM_SEMANAS_INCAPACIDAD_97")
	private BigDecimal numSemanasIncapacidad97;

	@Column(name="NUM_SEMANAS_RECONOCIDAS_73")
	private BigDecimal numSemanasReconocidas73;

	@Column(name="NUM_SEMANAS_RECONOCIDAS_97")
	private BigDecimal numSemanasReconocidas97;

	@Column(name="NUM_VECES_SALARIO_MINIMO_73")
	private BigDecimal numVecesSalarioMinimo73;

	@Column(name="POR_INVALIDEZ")
	private BigDecimal porInvalidez;

	//bi-directional many-to-one association to SpcEstadoCertificado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO_CERTIFICADO")
	private SpcEstadoCertificado spcEstadoCertificado;

	//bi-directional many-to-one association to SpcMotivoCertificacionMan
	@ManyToOne
	@JoinColumn(name="ID_MOTIVO_CERTIFICACION_MAN")
	private SpcMotivoCertificacionMan spcMotivoCertificacionMan;

	//bi-directional many-to-one association to SptPensionCertificado
	@OneToMany(mappedBy="sptCertificadoDerecho")
	private List<SptPensionCertificado> sptPensionCertificados;

	//bi-directional many-to-one association to SptTramPensCertificado
	@OneToMany(mappedBy="sptCertificadoDerecho")
	private List<SptTramPensCertificado> sptTramPensCertificados;

	public SptCertificadoDerecho() {
	}

	public long getCveIdCertificadoDerechos() {
		return this.cveIdCertificadoDerechos;
	}

	public void setCveIdCertificadoDerechos(long cveIdCertificadoDerechos) {
		this.cveIdCertificadoDerechos = cveIdCertificadoDerechos;
	}

	public String getCveCtoCosto() {
		return this.cveCtoCosto;
	}

	public void setCveCtoCosto(String cveCtoCosto) {
		this.cveCtoCosto = cveCtoCosto;
	}

	public String getCveCurp() {
		return this.cveCurp;
	}

	public void setCveCurp(String cveCurp) {
		this.cveCurp = cveCurp;
	}

	public String getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getCvePrei() {
		return this.cvePrei;
	}

	public void setCvePrei(String cvePrei) {
		this.cvePrei = cvePrei;
	}

	public String getCvePrestacionSolicitada() {
		return this.cvePrestacionSolicitada;
	}

	public void setCvePrestacionSolicitada(String cvePrestacionSolicitada) {
		this.cvePrestacionSolicitada = cvePrestacionSolicitada;
	}

	public String getCveRegistroPatronal() {
		return this.cveRegistroPatronal;
	}

	public void setCveRegistroPatronal(String cveRegistroPatronal) {
		this.cveRegistroPatronal = cveRegistroPatronal;
	}

	public BigDecimal getCveSexo() {
		return this.cveSexo;
	}

	public void setCveSexo(BigDecimal cveSexo) {
		this.cveSexo = cveSexo;
	}

	public String getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}

	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public String getCveUsuarioSindo() {
		return this.cveUsuarioSindo;
	}

	public void setCveUsuarioSindo(String cveUsuarioSindo) {
		this.cveUsuarioSindo = cveUsuarioSindo;
	}

	public String getDesFundLegalNegativa73() {
		return this.desFundLegalNegativa73;
	}

	public void setDesFundLegalNegativa73(String desFundLegalNegativa73) {
		this.desFundLegalNegativa73 = desFundLegalNegativa73;
	}

	public String getDesFundLegalNegativa97() {
		return this.desFundLegalNegativa97;
	}

	public void setDesFundLegalNegativa97(String desFundLegalNegativa97) {
		this.desFundLegalNegativa97 = desFundLegalNegativa97;
	}

	public String getDesFundLegalNegativa972() {
		return this.desFundLegalNegativa972;
	}

	public void setDesFundLegalNegativa972(String desFundLegalNegativa972) {
		this.desFundLegalNegativa972 = desFundLegalNegativa972;
	}

	public String getDesObservaciones73() {
		return this.desObservaciones73;
	}

	public void setDesObservaciones73(String desObservaciones73) {
		this.desObservaciones73 = desObservaciones73;
	}

	public String getDesObservaciones97() {
		return this.desObservaciones97;
	}

	public void setDesObservaciones97(String desObservaciones97) {
		this.desObservaciones97 = desObservaciones97;
	}

	public Date getFecBajaUltimoPeriodo73() {
		return this.fecBajaUltimoPeriodo73;
	}

	public void setFecBajaUltimoPeriodo73(Date fecBajaUltimoPeriodo73) {
		this.fecBajaUltimoPeriodo73 = fecBajaUltimoPeriodo73;
	}

	public Date getFecBajaUltimoPeriodo97() {
		return this.fecBajaUltimoPeriodo97;
	}

	public void setFecBajaUltimoPeriodo97(Date fecBajaUltimoPeriodo97) {
		this.fecBajaUltimoPeriodo97 = fecBajaUltimoPeriodo97;
	}

	public Date getFecCertificacion() {
		return this.fecCertificacion;
	}

	public void setFecCertificacion(Date fecCertificacion) {
		this.fecCertificacion = fecCertificacion;
	}

	public Date getFecConservacionDerechos73() {
		return this.fecConservacionDerechos73;
	}

	public void setFecConservacionDerechos73(Date fecConservacionDerechos73) {
		this.fecConservacionDerechos73 = fecConservacionDerechos73;
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

	public Date getFecSiniestro() {
		return this.fecSiniestro;
	}

	public void setFecSiniestro(Date fecSiniestro) {
		this.fecSiniestro = fecSiniestro;
	}

	public Date getFecSolicitudCertificacion() {
		return this.fecSolicitudCertificacion;
	}

	public void setFecSolicitudCertificacion(Date fecSolicitudCertificacion) {
		this.fecSolicitudCertificacion = fecSolicitudCertificacion;
	}

	public String getIdCertificadoDerechos() {
		return this.idCertificadoDerechos;
	}

	public void setIdCertificadoDerechos(String idCertificadoDerechos) {
		this.idCertificadoDerechos = idCertificadoDerechos;
	}

	public String getIdModalidad() {
		return this.idModalidad;
	}

	public void setIdModalidad(String idModalidad) {
		this.idModalidad = idModalidad;
	}

	public String getIdRegimen() {
		return this.idRegimen;
	}

	public void setIdRegimen(String idRegimen) {
		this.idRegimen = idRegimen;
	}

	public String getIdTipoPension() {
		return this.idTipoPension;
	}

	public void setIdTipoPension(String idTipoPension) {
		this.idTipoPension = idTipoPension;
	}

	public BigDecimal getImpSalarioPromedio73() {
		return this.impSalarioPromedio73;
	}

	public void setImpSalarioPromedio73(BigDecimal impSalarioPromedio73) {
		this.impSalarioPromedio73 = impSalarioPromedio73;
	}

	public BigDecimal getImpSalarioPromedio97() {
		return this.impSalarioPromedio97;
	}

	public void setImpSalarioPromedio97(BigDecimal impSalarioPromedio97) {
		this.impSalarioPromedio97 = impSalarioPromedio97;
	}

	public BigDecimal getImpSalarioRegistrado97() {
		return this.impSalarioRegistrado97;
	}

	public void setImpSalarioRegistrado97(BigDecimal impSalarioRegistrado97) {
		this.impSalarioRegistrado97 = impSalarioRegistrado97;
	}

	public String getIndConvenioOtroPais() {
		return this.indConvenioOtroPais;
	}

	public void setIndConvenioOtroPais(String indConvenioOtroPais) {
		this.indConvenioOtroPais = indConvenioOtroPais;
	}

	public String getIndDerechoRegimen73() {
		return this.indDerechoRegimen73;
	}

	public void setIndDerechoRegimen73(String indDerechoRegimen73) {
		this.indDerechoRegimen73 = indDerechoRegimen73;
	}

	public String getIndDerechoRegimen97() {
		return this.indDerechoRegimen97;
	}

	public void setIndDerechoRegimen97(String indDerechoRegimen97) {
		this.indDerechoRegimen97 = indDerechoRegimen97;
	}

	public String getIndDerechoRegimen972() {
		return this.indDerechoRegimen972;
	}

	public void setIndDerechoRegimen972(String indDerechoRegimen972) {
		this.indDerechoRegimen972 = indDerechoRegimen972;
	}

	public String getIndFerrocarrileroConvenio() {
		return this.indFerrocarrileroConvenio;
	}

	public void setIndFerrocarrileroConvenio(String indFerrocarrileroConvenio) {
		this.indFerrocarrileroConvenio = indFerrocarrileroConvenio;
	}

	public String getIndTrabajadorImss() {
		return this.indTrabajadorImss;
	}

	public void setIndTrabajadorImss(String indTrabajadorImss) {
		this.indTrabajadorImss = indTrabajadorImss;
	}

	public String getNomApellidoMaternoAfiliado() {
		return this.nomApellidoMaternoAfiliado;
	}

	public void setNomApellidoMaternoAfiliado(String nomApellidoMaternoAfiliado) {
		this.nomApellidoMaternoAfiliado = nomApellidoMaternoAfiliado;
	}

	public String getNomApellidoPaternoAfiliado() {
		return this.nomApellidoPaternoAfiliado;
	}

	public void setNomApellidoPaternoAfiliado(String nomApellidoPaternoAfiliado) {
		this.nomApellidoPaternoAfiliado = nomApellidoPaternoAfiliado;
	}

	public String getNomNombreAfiliado() {
		return this.nomNombreAfiliado;
	}

	public void setNomNombreAfiliado(String nomNombreAfiliado) {
		this.nomNombreAfiliado = nomNombreAfiliado;
	}

	public BigDecimal getNumDiasSubsidiados() {
		return this.numDiasSubsidiados;
	}

	public void setNumDiasSubsidiados(BigDecimal numDiasSubsidiados) {
		this.numDiasSubsidiados = numDiasSubsidiados;
	}

	public BigDecimal getNumIncrementoAnual73() {
		return this.numIncrementoAnual73;
	}

	public void setNumIncrementoAnual73(BigDecimal numIncrementoAnual73) {
		this.numIncrementoAnual73 = numIncrementoAnual73;
	}

	public BigDecimal getNumSemanasCotizadas31121990() {
		return this.numSemanasCotizadas31121990;
	}

	public void setNumSemanasCotizadas31121990(BigDecimal numSemanasCotizadas31121990) {
		this.numSemanasCotizadas31121990 = numSemanasCotizadas31121990;
	}

	public BigDecimal getNumSemanasDescontadas() {
		return this.numSemanasDescontadas;
	}

	public void setNumSemanasDescontadas(BigDecimal numSemanasDescontadas) {
		this.numSemanasDescontadas = numSemanasDescontadas;
	}

	public BigDecimal getNumSemanasExtranjero() {
		return this.numSemanasExtranjero;
	}

	public void setNumSemanasExtranjero(BigDecimal numSemanasExtranjero) {
		this.numSemanasExtranjero = numSemanasExtranjero;
	}

	public BigDecimal getNumSemanasIncapacidad97() {
		return this.numSemanasIncapacidad97;
	}

	public void setNumSemanasIncapacidad97(BigDecimal numSemanasIncapacidad97) {
		this.numSemanasIncapacidad97 = numSemanasIncapacidad97;
	}

	public BigDecimal getNumSemanasReconocidas73() {
		return this.numSemanasReconocidas73;
	}

	public void setNumSemanasReconocidas73(BigDecimal numSemanasReconocidas73) {
		this.numSemanasReconocidas73 = numSemanasReconocidas73;
	}

	public BigDecimal getNumSemanasReconocidas97() {
		return this.numSemanasReconocidas97;
	}

	public void setNumSemanasReconocidas97(BigDecimal numSemanasReconocidas97) {
		this.numSemanasReconocidas97 = numSemanasReconocidas97;
	}

	public BigDecimal getNumVecesSalarioMinimo73() {
		return this.numVecesSalarioMinimo73;
	}

	public void setNumVecesSalarioMinimo73(BigDecimal numVecesSalarioMinimo73) {
		this.numVecesSalarioMinimo73 = numVecesSalarioMinimo73;
	}

	public BigDecimal getPorInvalidez() {
		return this.porInvalidez;
	}

	public void setPorInvalidez(BigDecimal porInvalidez) {
		this.porInvalidez = porInvalidez;
	}

	public SpcEstadoCertificado getSpcEstadoCertificado() {
		return this.spcEstadoCertificado;
	}

	public void setSpcEstadoCertificado(SpcEstadoCertificado spcEstadoCertificado) {
		this.spcEstadoCertificado = spcEstadoCertificado;
	}

	public SpcMotivoCertificacionMan getSpcMotivoCertificacionMan() {
		return this.spcMotivoCertificacionMan;
	}

	public void setSpcMotivoCertificacionMan(SpcMotivoCertificacionMan spcMotivoCertificacionMan) {
		this.spcMotivoCertificacionMan = spcMotivoCertificacionMan;
	}

	public List<SptPensionCertificado> getSptPensionCertificados() {
		return this.sptPensionCertificados;
	}

	public void setSptPensionCertificados(List<SptPensionCertificado> sptPensionCertificados) {
		this.sptPensionCertificados = sptPensionCertificados;
	}

	public SptPensionCertificado addSptPensionCertificado(SptPensionCertificado sptPensionCertificado) {
		getSptPensionCertificados().add(sptPensionCertificado);
		sptPensionCertificado.setSptCertificadoDerecho(this);

		return sptPensionCertificado;
	}

	public SptPensionCertificado removeSptPensionCertificado(SptPensionCertificado sptPensionCertificado) {
		getSptPensionCertificados().remove(sptPensionCertificado);
		sptPensionCertificado.setSptCertificadoDerecho(null);

		return sptPensionCertificado;
	}

	public List<SptTramPensCertificado> getSptTramPensCertificados() {
		return this.sptTramPensCertificados;
	}

	public void setSptTramPensCertificados(List<SptTramPensCertificado> sptTramPensCertificados) {
		this.sptTramPensCertificados = sptTramPensCertificados;
	}

	public SptTramPensCertificado addSptTramPensCertificado(SptTramPensCertificado sptTramPensCertificado) {
		getSptTramPensCertificados().add(sptTramPensCertificado);
		sptTramPensCertificado.setSptCertificadoDerecho(this);

		return sptTramPensCertificado;
	}

	public SptTramPensCertificado removeSptTramPensCertificado(SptTramPensCertificado sptTramPensCertificado) {
		getSptTramPensCertificados().remove(sptTramPensCertificado);
		sptTramPensCertificado.setSptCertificadoDerecho(null);

		return sptTramPensCertificado;
	}

}