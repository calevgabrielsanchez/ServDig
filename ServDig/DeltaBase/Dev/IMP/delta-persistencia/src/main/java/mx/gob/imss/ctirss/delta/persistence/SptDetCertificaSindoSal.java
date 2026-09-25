package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_DET_CERTIFICA_SINDO_SAL database table.
 * 
 */
@Entity
@Table(name="SPT_DET_CERTIFICA_SINDO_SAL")
@NamedQuery(name="SptDetCertificaSindoSal.findAll", query="SELECT s FROM SptDetCertificaSindoSal s")
public class SptDetCertificaSindoSal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDETCERTIFICASINDOSAL", sequenceName = "SEQ_SPTDETCERTIFICASINDOSAL")
	@GeneratedValue(generator = "SEQ_SPTDETCERTIFICASINDOSAL")
	@Column(name="CVE_ID_DET_CERTIFICA_SINDO_SAL")
	private long cveIdDetCertificaSindoSal;

	@Column(name="CVE_CODIGO_ERROR_SALIDA")
	private String cveCodigoErrorSalida;

	@Column(name="CVE_CURP_SALIDA")
	private String cveCurpSalida;

	@Column(name="CVE_DELEGACION_SALIDA")
	private String cveDelegacionSalida;

	@Column(name="CVE_PRESTACION_SOL_SALIDA")
	private String cvePrestacionSolSalida;

	@Column(name="CVE_REGISTRO_PATRONAL_SALIDA")
	private String cveRegistroPatronalSalida;

	@Column(name="CVE_SEXO_SALIDA")
	private String cveSexoSalida;

	@Column(name="CVE_SUBDELEGACION_SALIDA")
	private String cveSubdelegacionSalida;

	@Column(name="CVE_UMF_SALIDA")
	private String cveUmfSalida;

	@Column(name="DES_ERROR_CODIGO_SALIDA")
	private String desErrorCodigoSalida;

	@Column(name="DES_EXITO_SALIDA")
	private String desExitoSalida;

	@Column(name="DES_FUND_LEGAL_73_1_SALIDA")
	private String desFundLegal731Salida;

	@Column(name="DES_FUND_LEGAL_97_1_SALIDA")
	private String desFundLegal971Salida;

	@Column(name="DES_FUND_LEGAL_97_2_SALIDA")
	private String desFundLegal972Salida;

	@Column(name="DES_MENSAJES_PANTALLA_SALIDA")
	private String desMensajesPantallaSalida;

	@Column(name="DES_OBSERVACIONES_73_1_SALIDA")
	private String desObservaciones731Salida;

	@Column(name="DES_OBSERVACIONES_97_1_SALIDA")
	private String desObservaciones971Salida;

	@Column(name="DES_OBSERVACIONES_97_2_SALIDA")
	private String desObservaciones972Salida;

	@Column(name="DES_TERMINAL_SALIDA")
	private String desTerminalSalida;

	@Column(name="FEC_BAJA_ULTIMO_PERIODO_73_SAL")
	private String fecBajaUltimoPeriodo73Sal;

	@Column(name="FEC_BAJA_ULTIMO_PERIODO_97_SAL")
	private String fecBajaUltimoPeriodo97Sal;

	@Column(name="FEC_CONSERVACION_DER_73_SALIDA")
	private String fecConservacionDer73Salida;

	@Column(name="FEC_DERECHOS_97_SALIDA")
	private String fecDerechos97Salida;

	@Column(name="FEC_NACIMIENTO_SALIDA")
	private String fecNacimientoSalida;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="FEC_SINIESTRO_SALIDA")
	private String fecSiniestroSalida;

	@Column(name="ID_DIGITO_VERIFICA_SALIDA")
	private String idDigitoVerificaSalida;

	@Column(name="ID_NSS_SALIDA")
	private String idNssSalida;

	@Column(name="IMP_SALARIO_DIARIO_REG_97_SALI")
	private String impSalarioDiarioReg97Sali;

	@Column(name="IMP_SALARIO_PROMEDIO_73_SALIDA")
	private String impSalarioPromedio73Salida;

	@Column(name="IMP_SALARIO_PROMEDIO_97_SALIDA")
	private String impSalarioPromedio97Salida;

	@Column(name="IND_CONFIRMAR_SALIDA")
	private String indConfirmarSalida;

	@Column(name="IND_DERECHO_73_SALIDA")
	private String indDerecho73Salida;

	@Column(name="IND_DERECHO_97_1_SALIDA")
	private String indDerecho971Salida;

	@Column(name="IND_DERECHO_97_2_SALIDA")
	private String indDerecho972Salida;

	@Column(name="NOM_NOMBRE_SALIDA")
	private String nomNombreSalida;

	@Column(name="NUM_INCREMENTO_ANUAL_73_SALIDA")
	private String numIncrementoAnual73Salida;

	@Column(name="NUM_SEMANAS_COTIZADAS_31121990")
	private String numSemanasCotizadas31121990;

	@Column(name="NUM_SEMANAS_DESCONTADAS_97_SAL")
	private String numSemanasDescontadas97Sal;

	@Column(name="NUM_SEMANAS_INCAPACIDAD_97_SAL")
	private String numSemanasIncapacidad97Sal;

	@Column(name="NUM_SEMANAS_RECONOCIDAS_73_SAL")
	private String numSemanasReconocidas73Sal;

	@Column(name="NUM_SEMANAS_RECONOCIDAS_97_SAL")
	private String numSemanasReconocidas97Sal;

	@Column(name="NUM_VECES_SALARIO_MIN_73_SALID")
	private String numVecesSalarioMin73Salid;

	@Column(name="POR_INVALIDEZ_SALIDA")
	private String porInvalidezSalida;

	//bi-directional many-to-one association to SptRespComunicEntidade
	@ManyToOne
	@JoinColumn(name="CVE_ID_RESP_COMUNIC_ENTIDADES")
	private SptRespComunicEntidade sptRespComunicEntidade;

	public SptDetCertificaSindoSal() {
	}

	public long getCveIdDetCertificaSindoSal() {
		return this.cveIdDetCertificaSindoSal;
	}

	public void setCveIdDetCertificaSindoSal(long cveIdDetCertificaSindoSal) {
		this.cveIdDetCertificaSindoSal = cveIdDetCertificaSindoSal;
	}

	public String getCveCodigoErrorSalida() {
		return this.cveCodigoErrorSalida;
	}

	public void setCveCodigoErrorSalida(String cveCodigoErrorSalida) {
		this.cveCodigoErrorSalida = cveCodigoErrorSalida;
	}

	public String getCveCurpSalida() {
		return this.cveCurpSalida;
	}

	public void setCveCurpSalida(String cveCurpSalida) {
		this.cveCurpSalida = cveCurpSalida;
	}

	public String getCveDelegacionSalida() {
		return this.cveDelegacionSalida;
	}

	public void setCveDelegacionSalida(String cveDelegacionSalida) {
		this.cveDelegacionSalida = cveDelegacionSalida;
	}

	public String getCvePrestacionSolSalida() {
		return this.cvePrestacionSolSalida;
	}

	public void setCvePrestacionSolSalida(String cvePrestacionSolSalida) {
		this.cvePrestacionSolSalida = cvePrestacionSolSalida;
	}

	public String getCveRegistroPatronalSalida() {
		return this.cveRegistroPatronalSalida;
	}

	public void setCveRegistroPatronalSalida(String cveRegistroPatronalSalida) {
		this.cveRegistroPatronalSalida = cveRegistroPatronalSalida;
	}

	public String getCveSexoSalida() {
		return this.cveSexoSalida;
	}

	public void setCveSexoSalida(String cveSexoSalida) {
		this.cveSexoSalida = cveSexoSalida;
	}

	public String getCveSubdelegacionSalida() {
		return this.cveSubdelegacionSalida;
	}

	public void setCveSubdelegacionSalida(String cveSubdelegacionSalida) {
		this.cveSubdelegacionSalida = cveSubdelegacionSalida;
	}

	public String getCveUmfSalida() {
		return this.cveUmfSalida;
	}

	public void setCveUmfSalida(String cveUmfSalida) {
		this.cveUmfSalida = cveUmfSalida;
	}

	public String getDesErrorCodigoSalida() {
		return this.desErrorCodigoSalida;
	}

	public void setDesErrorCodigoSalida(String desErrorCodigoSalida) {
		this.desErrorCodigoSalida = desErrorCodigoSalida;
	}

	public String getDesExitoSalida() {
		return this.desExitoSalida;
	}

	public void setDesExitoSalida(String desExitoSalida) {
		this.desExitoSalida = desExitoSalida;
	}

	public String getDesFundLegal731Salida() {
		return this.desFundLegal731Salida;
	}

	public void setDesFundLegal731Salida(String desFundLegal731Salida) {
		this.desFundLegal731Salida = desFundLegal731Salida;
	}

	public String getDesFundLegal971Salida() {
		return this.desFundLegal971Salida;
	}

	public void setDesFundLegal971Salida(String desFundLegal971Salida) {
		this.desFundLegal971Salida = desFundLegal971Salida;
	}

	public String getDesFundLegal972Salida() {
		return this.desFundLegal972Salida;
	}

	public void setDesFundLegal972Salida(String desFundLegal972Salida) {
		this.desFundLegal972Salida = desFundLegal972Salida;
	}

	public String getDesMensajesPantallaSalida() {
		return this.desMensajesPantallaSalida;
	}

	public void setDesMensajesPantallaSalida(String desMensajesPantallaSalida) {
		this.desMensajesPantallaSalida = desMensajesPantallaSalida;
	}

	public String getDesObservaciones731Salida() {
		return this.desObservaciones731Salida;
	}

	public void setDesObservaciones731Salida(String desObservaciones731Salida) {
		this.desObservaciones731Salida = desObservaciones731Salida;
	}

	public String getDesObservaciones971Salida() {
		return this.desObservaciones971Salida;
	}

	public void setDesObservaciones971Salida(String desObservaciones971Salida) {
		this.desObservaciones971Salida = desObservaciones971Salida;
	}

	public String getDesObservaciones972Salida() {
		return this.desObservaciones972Salida;
	}

	public void setDesObservaciones972Salida(String desObservaciones972Salida) {
		this.desObservaciones972Salida = desObservaciones972Salida;
	}

	public String getDesTerminalSalida() {
		return this.desTerminalSalida;
	}

	public void setDesTerminalSalida(String desTerminalSalida) {
		this.desTerminalSalida = desTerminalSalida;
	}

	public String getFecBajaUltimoPeriodo73Sal() {
		return this.fecBajaUltimoPeriodo73Sal;
	}

	public void setFecBajaUltimoPeriodo73Sal(String fecBajaUltimoPeriodo73Sal) {
		this.fecBajaUltimoPeriodo73Sal = fecBajaUltimoPeriodo73Sal;
	}

	public String getFecBajaUltimoPeriodo97Sal() {
		return this.fecBajaUltimoPeriodo97Sal;
	}

	public void setFecBajaUltimoPeriodo97Sal(String fecBajaUltimoPeriodo97Sal) {
		this.fecBajaUltimoPeriodo97Sal = fecBajaUltimoPeriodo97Sal;
	}

	public String getFecConservacionDer73Salida() {
		return this.fecConservacionDer73Salida;
	}

	public void setFecConservacionDer73Salida(String fecConservacionDer73Salida) {
		this.fecConservacionDer73Salida = fecConservacionDer73Salida;
	}

	public String getFecDerechos97Salida() {
		return this.fecDerechos97Salida;
	}

	public void setFecDerechos97Salida(String fecDerechos97Salida) {
		this.fecDerechos97Salida = fecDerechos97Salida;
	}

	public String getFecNacimientoSalida() {
		return this.fecNacimientoSalida;
	}

	public void setFecNacimientoSalida(String fecNacimientoSalida) {
		this.fecNacimientoSalida = fecNacimientoSalida;
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

	public String getFecSiniestroSalida() {
		return this.fecSiniestroSalida;
	}

	public void setFecSiniestroSalida(String fecSiniestroSalida) {
		this.fecSiniestroSalida = fecSiniestroSalida;
	}

	public String getIdDigitoVerificaSalida() {
		return this.idDigitoVerificaSalida;
	}

	public void setIdDigitoVerificaSalida(String idDigitoVerificaSalida) {
		this.idDigitoVerificaSalida = idDigitoVerificaSalida;
	}

	public String getIdNssSalida() {
		return this.idNssSalida;
	}

	public void setIdNssSalida(String idNssSalida) {
		this.idNssSalida = idNssSalida;
	}

	public String getImpSalarioDiarioReg97Sali() {
		return this.impSalarioDiarioReg97Sali;
	}

	public void setImpSalarioDiarioReg97Sali(String impSalarioDiarioReg97Sali) {
		this.impSalarioDiarioReg97Sali = impSalarioDiarioReg97Sali;
	}

	public String getImpSalarioPromedio73Salida() {
		return this.impSalarioPromedio73Salida;
	}

	public void setImpSalarioPromedio73Salida(String impSalarioPromedio73Salida) {
		this.impSalarioPromedio73Salida = impSalarioPromedio73Salida;
	}

	public String getImpSalarioPromedio97Salida() {
		return this.impSalarioPromedio97Salida;
	}

	public void setImpSalarioPromedio97Salida(String impSalarioPromedio97Salida) {
		this.impSalarioPromedio97Salida = impSalarioPromedio97Salida;
	}

	public String getIndConfirmarSalida() {
		return this.indConfirmarSalida;
	}

	public void setIndConfirmarSalida(String indConfirmarSalida) {
		this.indConfirmarSalida = indConfirmarSalida;
	}

	public String getIndDerecho73Salida() {
		return this.indDerecho73Salida;
	}

	public void setIndDerecho73Salida(String indDerecho73Salida) {
		this.indDerecho73Salida = indDerecho73Salida;
	}

	public String getIndDerecho971Salida() {
		return this.indDerecho971Salida;
	}

	public void setIndDerecho971Salida(String indDerecho971Salida) {
		this.indDerecho971Salida = indDerecho971Salida;
	}

	public String getIndDerecho972Salida() {
		return this.indDerecho972Salida;
	}

	public void setIndDerecho972Salida(String indDerecho972Salida) {
		this.indDerecho972Salida = indDerecho972Salida;
	}

	public String getNomNombreSalida() {
		return this.nomNombreSalida;
	}

	public void setNomNombreSalida(String nomNombreSalida) {
		this.nomNombreSalida = nomNombreSalida;
	}

	public String getNumIncrementoAnual73Salida() {
		return this.numIncrementoAnual73Salida;
	}

	public void setNumIncrementoAnual73Salida(String numIncrementoAnual73Salida) {
		this.numIncrementoAnual73Salida = numIncrementoAnual73Salida;
	}

	public String getNumSemanasCotizadas31121990() {
		return this.numSemanasCotizadas31121990;
	}

	public void setNumSemanasCotizadas31121990(String numSemanasCotizadas31121990) {
		this.numSemanasCotizadas31121990 = numSemanasCotizadas31121990;
	}

	public String getNumSemanasDescontadas97Sal() {
		return this.numSemanasDescontadas97Sal;
	}

	public void setNumSemanasDescontadas97Sal(String numSemanasDescontadas97Sal) {
		this.numSemanasDescontadas97Sal = numSemanasDescontadas97Sal;
	}

	public String getNumSemanasIncapacidad97Sal() {
		return this.numSemanasIncapacidad97Sal;
	}

	public void setNumSemanasIncapacidad97Sal(String numSemanasIncapacidad97Sal) {
		this.numSemanasIncapacidad97Sal = numSemanasIncapacidad97Sal;
	}

	public String getNumSemanasReconocidas73Sal() {
		return this.numSemanasReconocidas73Sal;
	}

	public void setNumSemanasReconocidas73Sal(String numSemanasReconocidas73Sal) {
		this.numSemanasReconocidas73Sal = numSemanasReconocidas73Sal;
	}

	public String getNumSemanasReconocidas97Sal() {
		return this.numSemanasReconocidas97Sal;
	}

	public void setNumSemanasReconocidas97Sal(String numSemanasReconocidas97Sal) {
		this.numSemanasReconocidas97Sal = numSemanasReconocidas97Sal;
	}

	public String getNumVecesSalarioMin73Salid() {
		return this.numVecesSalarioMin73Salid;
	}

	public void setNumVecesSalarioMin73Salid(String numVecesSalarioMin73Salid) {
		this.numVecesSalarioMin73Salid = numVecesSalarioMin73Salid;
	}

	public String getPorInvalidezSalida() {
		return this.porInvalidezSalida;
	}

	public void setPorInvalidezSalida(String porInvalidezSalida) {
		this.porInvalidezSalida = porInvalidezSalida;
	}

	public SptRespComunicEntidade getSptRespComunicEntidade() {
		return this.sptRespComunicEntidade;
	}

	public void setSptRespComunicEntidade(SptRespComunicEntidade sptRespComunicEntidade) {
		this.sptRespComunicEntidade = sptRespComunicEntidade;
	}

}