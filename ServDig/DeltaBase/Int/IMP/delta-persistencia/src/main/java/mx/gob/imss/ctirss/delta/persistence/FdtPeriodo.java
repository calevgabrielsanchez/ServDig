package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDT_PERIODO database table.
 * 
 */
@Entity
@Table(name="FDT_PERIODO")
public class FdtPeriodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_DICTAMEN", nullable=false, precision=22)
	private long idDictamen;

	@Column(name="CV_AUDITOR", length=18)
	private String cvAuditor;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ASIGNACION")
	private Date fhAsignacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_CEDULA")
	private Date fhCedula;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_DICTAMEN")
	private Date fhDictamen;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_DOCUMENTOS")
	private Date fhDocumentos;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENTREGA_ANEXO_I")
	private Date fhEntregaAnexoI;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENTREGA_ANEXO_II")
	private Date fhEntregaAnexoIi;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENTREGA_ANEXO_III")
	private Date fhEntregaAnexoIii;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENTREGA_ANEXO_IV")
	private Date fhEntregaAnexoIv;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENTREGA_ANEXO_V")
	private Date fhEntregaAnexoV;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENTREGA_BALANZA")
	private Date fhEntregaBalanza;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENTREGA_CED")
	private Date fhEntregaCed;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENTREGA_COBRANZA")
	private Date fhEntregaCobranza;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENTREGA_MO")
	private Date fhEntregaMo;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENTREGA_MOVAFIL")
	private Date fhEntregaMovafil;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_FIN_ENTREGA")
	private Date fhFinEntrega;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RESULTADOS_RI")
	private Date fhResultadosRi;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_REVISION")
	private Date fhRevision;

	@Column(name="IM_ACT_COP", precision=12, scale=2)
	private BigDecimal imActCop;

	@Column(name="IM_ACT_RCV", precision=12, scale=2)
	private BigDecimal imActRcv;

	@Column(name="IM_COMISIONES", precision=12, scale=2)
	private BigDecimal imComisiones;

	@Column(name="IM_HONORARIOS", precision=12, scale=2)
	private BigDecimal imHonorarios;

	@Column(name="IM_HONORARIOS_SUELDOS", precision=12, scale=2)
	private BigDecimal imHonorariosSueldos;

	@Column(name="IM_NO_SUJETAS_ASEG", precision=12, scale=2)
	private BigDecimal imNoSujetasAseg;

	@Column(name="IM_REC_COP", precision=12, scale=2)
	private BigDecimal imRecCop;

	@Column(name="IM_REC_RCV", precision=12, scale=2)
	private BigDecimal imRecRcv;

	@Column(name="IM_SUJETAS_ASEG", precision=12, scale=2)
	private BigDecimal imSujetasAseg;

	@Column(name="IM_TOT_REMUNERACIONES_DIR", precision=12, scale=2)
	private BigDecimal imTotRemuneracionesDir;

	@Column(name="IM_TOTAL_COP", precision=12, scale=2)
	private BigDecimal imTotalCop;

	@Column(name="IM_TOTAL_RCV", precision=12, scale=2)
	private BigDecimal imTotalRcv;

	@Column(name="IN_DOCTOS_ENTREGADOS", precision=22)
	private BigDecimal inDoctosEntregados;

	@Column(name="IN_ESTADO", nullable=false, length=30)
	private String inEstado;

	@Column(name="IN_FORMA_PAGO", length=1)
	private String inFormaPago;

	@Column(name="IN_STATUS_DICTAMEN", length=12)
	private String inStatusDictamen;

	@Column(name="IN_TP_OPINION", length=20)
	private String inTpOpinion;

	@Column(name="PC_LIMITE_RAZONABILIDAD", precision=9, scale=2)
	private BigDecimal pcLimiteRazonabilidad;

	@Column(name="PC_PORCENTAJE_RAZON", precision=9, scale=2)
	private BigDecimal pcPorcentajeRazon;

	@Column(name="SDELEG_ORIG", precision=2)
	private BigDecimal sdelegOrig;

	@Column(name="TX_AREA_DESTINO", length=20)
	private String txAreaDestino;

	@Column(name="TX_AREAS", length=10)
	private String txAreas;

    @Lob()
	@Column(name="TX_CAMPO7")
	private byte[] txCampo7;

	@Column(name="TX_CAUSAS_RI", length=100)
	private String txCausasRi;

	@Column(name="TX_CONCLUSION", length=29)
	private String txConclusion;

	@Column(name="TX_DIFERENCIAS_ACLARAR", precision=12, scale=2)
	private BigDecimal txDiferenciasAclarar;

	@Column(name="TX_DIFERENCIAS_REVISION", length=255)
	private String txDiferenciasRevision;

	@Column(name="TX_MATRICULA", length=20)
	private String txMatricula;

	@Column(name="TX_MOTIVO_STATUS", length=30)
	private String txMotivoStatus;

    @Lob()
	@Column(name="TX_NOTAS_ANEXO_1")
	private byte[] txNotasAnexo1;

    @Lob()
	@Column(name="TX_NOTAS_ANEXO_2")
	private byte[] txNotasAnexo2;

    @Lob()
	@Column(name="TX_NOTAS_ANEXO_3")
	private byte[] txNotasAnexo3;

    @Lob()
	@Column(name="TX_NOTAS_ANEXO_4")
	private byte[] txNotasAnexo4;

	@Column(name="TX_OBSERVACIONES1_CEDULA", length=300)
	private String txObservaciones1Cedula;

	@Column(name="TX_OBSERVACIONES2_CEDULA", length=300)
	private String txObservaciones2Cedula;

    @Lob()
	@Column(name="TX_OMISIONES_DIC")
	private byte[] txOmisionesDic;

	@Column(name="TX_ORIGEN_STATUS", length=22)
	private String txOrigenStatus;

	@Column(name="TX_RESULTADO_RI", length=20)
	private String txResultadoRi;

	@Column(name="TX_STATUS", length=20)
	private String txStatus;

	@Column(name="TX_STATUS_DOCTOS", length=10)
	private String txStatusDoctos;

	@Column(name="TX_TIPO_ENVIO", length=8)
	private String txTipoEnvio;

	@Column(name="TX_TIPO_REVISION", length=20)
	private String txTipoRevision;

	@Column(name="VALIDEZ_EXTEMPORANEO", length=20)
	private String validezExtemporaneo;

	//bi-directional one-to-one association to FdiDatosExtra
	@OneToOne(mappedBy="fdtPeriodo", fetch=FetchType.LAZY)
	private FdiDatosExtra fdiDatosExtra;

	//bi-directional many-to-one association to FdtA1ContratoTrabajo
	@OneToMany(mappedBy="fdtPeriodo")
	private List<FdtA1ContratoTrabajo> fdtA1ContratoTrabajos;

	//bi-directional many-to-one association to FdtA1PatSustituto
	@OneToMany(mappedBy="fdtPeriodo")
	private List<FdtA1PatSustituto> fdtA1PatSustitutos;

	//bi-directional many-to-one association to FdtA3Clausula
	@OneToMany(mappedBy="fdtPeriodo")
	private List<FdtA3Clausula> fdtA3Clausulas;

	//bi-directional many-to-many association to FdtGrupo
    @ManyToMany
	@JoinTable(
		name="FDT_A3_GRUPO"
		, joinColumns={
			@JoinColumn(name="ID_DICTAMEN", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="CV_GRUPO", nullable=false)
			}
		)
	private List<FdtGrupo> fdtGrupos;

	//bi-directional many-to-one association to FdtA3PfOtro
	@OneToMany(mappedBy="fdtPeriodo")
	private List<FdtA3PfOtro> fdtA3PfOtros;

	//bi-directional many-to-many association to FdcRemuneracione
	@ManyToMany(mappedBy="fdtPeriodos")
	private List<FdcRemuneracione> fdcRemuneraciones;

	//bi-directional one-to-one association to FdtCartaPresentacion
	@OneToOne(mappedBy="fdtPeriodo", fetch=FetchType.LAZY)
	private FdtCartaPresentacion fdtCartaPresentacion;

	//bi-directional many-to-one association to FdtCedrazDictamen
	@OneToMany(mappedBy="fdtPeriodo")
	private List<FdtCedrazDictamen> fdtCedrazDictamens;

	//bi-directional many-to-one association to FdtDeleg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_DELEG_ORIG")
	private FdtDeleg fdtDeleg;

	//bi-directional many-to-one association to FdtAviso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AVISO", nullable=false)
	private FdtAviso fdtAviso;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@OneToMany(mappedBy="fdtPeriodo")
	private List<FdtRegistroPeriodo> fdtRegistroPeriodos;

    public FdtPeriodo() {
    }

	public long getIdDictamen() {
		return this.idDictamen;
	}

	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
	}

	public String getCvAuditor() {
		return this.cvAuditor;
	}

	public void setCvAuditor(String cvAuditor) {
		this.cvAuditor = cvAuditor;
	}

	public Date getFhAsignacion() {
		return this.fhAsignacion;
	}

	public void setFhAsignacion(Date fhAsignacion) {
		this.fhAsignacion = fhAsignacion;
	}

	public Date getFhCedula() {
		return this.fhCedula;
	}

	public void setFhCedula(Date fhCedula) {
		this.fhCedula = fhCedula;
	}

	public Date getFhDictamen() {
		return this.fhDictamen;
	}

	public void setFhDictamen(Date fhDictamen) {
		this.fhDictamen = fhDictamen;
	}

	public Date getFhDocumentos() {
		return this.fhDocumentos;
	}

	public void setFhDocumentos(Date fhDocumentos) {
		this.fhDocumentos = fhDocumentos;
	}

	public Date getFhEntregaAnexoI() {
		return this.fhEntregaAnexoI;
	}

	public void setFhEntregaAnexoI(Date fhEntregaAnexoI) {
		this.fhEntregaAnexoI = fhEntregaAnexoI;
	}

	public Date getFhEntregaAnexoIi() {
		return this.fhEntregaAnexoIi;
	}

	public void setFhEntregaAnexoIi(Date fhEntregaAnexoIi) {
		this.fhEntregaAnexoIi = fhEntregaAnexoIi;
	}

	public Date getFhEntregaAnexoIii() {
		return this.fhEntregaAnexoIii;
	}

	public void setFhEntregaAnexoIii(Date fhEntregaAnexoIii) {
		this.fhEntregaAnexoIii = fhEntregaAnexoIii;
	}

	public Date getFhEntregaAnexoIv() {
		return this.fhEntregaAnexoIv;
	}

	public void setFhEntregaAnexoIv(Date fhEntregaAnexoIv) {
		this.fhEntregaAnexoIv = fhEntregaAnexoIv;
	}

	public Date getFhEntregaAnexoV() {
		return this.fhEntregaAnexoV;
	}

	public void setFhEntregaAnexoV(Date fhEntregaAnexoV) {
		this.fhEntregaAnexoV = fhEntregaAnexoV;
	}

	public Date getFhEntregaBalanza() {
		return this.fhEntregaBalanza;
	}

	public void setFhEntregaBalanza(Date fhEntregaBalanza) {
		this.fhEntregaBalanza = fhEntregaBalanza;
	}

	public Date getFhEntregaCed() {
		return this.fhEntregaCed;
	}

	public void setFhEntregaCed(Date fhEntregaCed) {
		this.fhEntregaCed = fhEntregaCed;
	}

	public Date getFhEntregaCobranza() {
		return this.fhEntregaCobranza;
	}

	public void setFhEntregaCobranza(Date fhEntregaCobranza) {
		this.fhEntregaCobranza = fhEntregaCobranza;
	}

	public Date getFhEntregaMo() {
		return this.fhEntregaMo;
	}

	public void setFhEntregaMo(Date fhEntregaMo) {
		this.fhEntregaMo = fhEntregaMo;
	}

	public Date getFhEntregaMovafil() {
		return this.fhEntregaMovafil;
	}

	public void setFhEntregaMovafil(Date fhEntregaMovafil) {
		this.fhEntregaMovafil = fhEntregaMovafil;
	}

	public Date getFhFinEntrega() {
		return this.fhFinEntrega;
	}

	public void setFhFinEntrega(Date fhFinEntrega) {
		this.fhFinEntrega = fhFinEntrega;
	}

	public Date getFhResultadosRi() {
		return this.fhResultadosRi;
	}

	public void setFhResultadosRi(Date fhResultadosRi) {
		this.fhResultadosRi = fhResultadosRi;
	}

	public Date getFhRevision() {
		return this.fhRevision;
	}

	public void setFhRevision(Date fhRevision) {
		this.fhRevision = fhRevision;
	}

	public BigDecimal getImActCop() {
		return this.imActCop;
	}

	public void setImActCop(BigDecimal imActCop) {
		this.imActCop = imActCop;
	}

	public BigDecimal getImActRcv() {
		return this.imActRcv;
	}

	public void setImActRcv(BigDecimal imActRcv) {
		this.imActRcv = imActRcv;
	}

	public BigDecimal getImComisiones() {
		return this.imComisiones;
	}

	public void setImComisiones(BigDecimal imComisiones) {
		this.imComisiones = imComisiones;
	}

	public BigDecimal getImHonorarios() {
		return this.imHonorarios;
	}

	public void setImHonorarios(BigDecimal imHonorarios) {
		this.imHonorarios = imHonorarios;
	}

	public BigDecimal getImHonorariosSueldos() {
		return this.imHonorariosSueldos;
	}

	public void setImHonorariosSueldos(BigDecimal imHonorariosSueldos) {
		this.imHonorariosSueldos = imHonorariosSueldos;
	}

	public BigDecimal getImNoSujetasAseg() {
		return this.imNoSujetasAseg;
	}

	public void setImNoSujetasAseg(BigDecimal imNoSujetasAseg) {
		this.imNoSujetasAseg = imNoSujetasAseg;
	}

	public BigDecimal getImRecCop() {
		return this.imRecCop;
	}

	public void setImRecCop(BigDecimal imRecCop) {
		this.imRecCop = imRecCop;
	}

	public BigDecimal getImRecRcv() {
		return this.imRecRcv;
	}

	public void setImRecRcv(BigDecimal imRecRcv) {
		this.imRecRcv = imRecRcv;
	}

	public BigDecimal getImSujetasAseg() {
		return this.imSujetasAseg;
	}

	public void setImSujetasAseg(BigDecimal imSujetasAseg) {
		this.imSujetasAseg = imSujetasAseg;
	}

	public BigDecimal getImTotRemuneracionesDir() {
		return this.imTotRemuneracionesDir;
	}

	public void setImTotRemuneracionesDir(BigDecimal imTotRemuneracionesDir) {
		this.imTotRemuneracionesDir = imTotRemuneracionesDir;
	}

	public BigDecimal getImTotalCop() {
		return this.imTotalCop;
	}

	public void setImTotalCop(BigDecimal imTotalCop) {
		this.imTotalCop = imTotalCop;
	}

	public BigDecimal getImTotalRcv() {
		return this.imTotalRcv;
	}

	public void setImTotalRcv(BigDecimal imTotalRcv) {
		this.imTotalRcv = imTotalRcv;
	}

	public BigDecimal getInDoctosEntregados() {
		return this.inDoctosEntregados;
	}

	public void setInDoctosEntregados(BigDecimal inDoctosEntregados) {
		this.inDoctosEntregados = inDoctosEntregados;
	}

	public String getInEstado() {
		return this.inEstado;
	}

	public void setInEstado(String inEstado) {
		this.inEstado = inEstado;
	}

	public String getInFormaPago() {
		return this.inFormaPago;
	}

	public void setInFormaPago(String inFormaPago) {
		this.inFormaPago = inFormaPago;
	}

	public String getInStatusDictamen() {
		return this.inStatusDictamen;
	}

	public void setInStatusDictamen(String inStatusDictamen) {
		this.inStatusDictamen = inStatusDictamen;
	}

	public String getInTpOpinion() {
		return this.inTpOpinion;
	}

	public void setInTpOpinion(String inTpOpinion) {
		this.inTpOpinion = inTpOpinion;
	}

	public BigDecimal getPcLimiteRazonabilidad() {
		return this.pcLimiteRazonabilidad;
	}

	public void setPcLimiteRazonabilidad(BigDecimal pcLimiteRazonabilidad) {
		this.pcLimiteRazonabilidad = pcLimiteRazonabilidad;
	}

	public BigDecimal getPcPorcentajeRazon() {
		return this.pcPorcentajeRazon;
	}

	public void setPcPorcentajeRazon(BigDecimal pcPorcentajeRazon) {
		this.pcPorcentajeRazon = pcPorcentajeRazon;
	}

	public BigDecimal getSdelegOrig() {
		return this.sdelegOrig;
	}

	public void setSdelegOrig(BigDecimal sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}

	public String getTxAreaDestino() {
		return this.txAreaDestino;
	}

	public void setTxAreaDestino(String txAreaDestino) {
		this.txAreaDestino = txAreaDestino;
	}

	public String getTxAreas() {
		return this.txAreas;
	}

	public void setTxAreas(String txAreas) {
		this.txAreas = txAreas;
	}

	public byte[] getTxCampo7() {
		return this.txCampo7;
	}

	public void setTxCampo7(byte[] txCampo7) {
		this.txCampo7 = txCampo7 != null ? txCampo7.clone() : null;
	}

	public String getTxCausasRi() {
		return this.txCausasRi;
	}

	public void setTxCausasRi(String txCausasRi) {
		this.txCausasRi = txCausasRi;
	}

	public String getTxConclusion() {
		return this.txConclusion;
	}

	public void setTxConclusion(String txConclusion) {
		this.txConclusion = txConclusion;
	}

	public BigDecimal getTxDiferenciasAclarar() {
		return this.txDiferenciasAclarar;
	}

	public void setTxDiferenciasAclarar(BigDecimal txDiferenciasAclarar) {
		this.txDiferenciasAclarar = txDiferenciasAclarar;
	}

	public String getTxDiferenciasRevision() {
		return this.txDiferenciasRevision;
	}

	public void setTxDiferenciasRevision(String txDiferenciasRevision) {
		this.txDiferenciasRevision = txDiferenciasRevision;
	}

	public String getTxMatricula() {
		return this.txMatricula;
	}

	public void setTxMatricula(String txMatricula) {
		this.txMatricula = txMatricula;
	}

	public String getTxMotivoStatus() {
		return this.txMotivoStatus;
	}

	public void setTxMotivoStatus(String txMotivoStatus) {
		this.txMotivoStatus = txMotivoStatus;
	}

	public byte[] getTxNotasAnexo1() {
		return this.txNotasAnexo1;
	}

	public void setTxNotasAnexo1(byte[] txNotasAnexo1) {
		this.txNotasAnexo1 = txNotasAnexo1 != null ? txNotasAnexo1.clone() : null;
	}

	public byte[] getTxNotasAnexo2() {
		return this.txNotasAnexo2;
	}

	public void setTxNotasAnexo2(byte[] txNotasAnexo2) {
		this.txNotasAnexo2 = txNotasAnexo2 != null ? txNotasAnexo2.clone() : null;
	}

	public byte[] getTxNotasAnexo3() {
		return this.txNotasAnexo3;
	}

	public void setTxNotasAnexo3(byte[] txNotasAnexo3) {
		this.txNotasAnexo3 = txNotasAnexo3 != null ? txNotasAnexo3.clone() : null;
	}

	public byte[] getTxNotasAnexo4() {
		return this.txNotasAnexo4;
	}

	public void setTxNotasAnexo4(byte[] txNotasAnexo4) {
		this.txNotasAnexo4 = txNotasAnexo4 != null ? txNotasAnexo4.clone() : null;
	}

	public String getTxObservaciones1Cedula() {
		return this.txObservaciones1Cedula;
	}

	public void setTxObservaciones1Cedula(String txObservaciones1Cedula) {
		this.txObservaciones1Cedula = txObservaciones1Cedula;
	}

	public String getTxObservaciones2Cedula() {
		return this.txObservaciones2Cedula;
	}

	public void setTxObservaciones2Cedula(String txObservaciones2Cedula) {
		this.txObservaciones2Cedula = txObservaciones2Cedula;
	}

	public byte[] getTxOmisionesDic() {
		return this.txOmisionesDic;
	}

	public void setTxOmisionesDic(byte[] txOmisionesDic) {
		this.txOmisionesDic = txOmisionesDic != null ? txOmisionesDic.clone() : null;
	}

	public String getTxOrigenStatus() {
		return this.txOrigenStatus;
	}

	public void setTxOrigenStatus(String txOrigenStatus) {
		this.txOrigenStatus = txOrigenStatus;
	}

	public String getTxResultadoRi() {
		return this.txResultadoRi;
	}

	public void setTxResultadoRi(String txResultadoRi) {
		this.txResultadoRi = txResultadoRi;
	}

	public String getTxStatus() {
		return this.txStatus;
	}

	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}

	public String getTxStatusDoctos() {
		return this.txStatusDoctos;
	}

	public void setTxStatusDoctos(String txStatusDoctos) {
		this.txStatusDoctos = txStatusDoctos;
	}

	public String getTxTipoEnvio() {
		return this.txTipoEnvio;
	}

	public void setTxTipoEnvio(String txTipoEnvio) {
		this.txTipoEnvio = txTipoEnvio;
	}

	public String getTxTipoRevision() {
		return this.txTipoRevision;
	}

	public void setTxTipoRevision(String txTipoRevision) {
		this.txTipoRevision = txTipoRevision;
	}

	public String getValidezExtemporaneo() {
		return this.validezExtemporaneo;
	}

	public void setValidezExtemporaneo(String validezExtemporaneo) {
		this.validezExtemporaneo = validezExtemporaneo;
	}

	public FdiDatosExtra getFdiDatosExtra() {
		return this.fdiDatosExtra;
	}

	public void setFdiDatosExtra(FdiDatosExtra fdiDatosExtra) {
		this.fdiDatosExtra = fdiDatosExtra;
	}
	
	public List<FdtA1ContratoTrabajo> getFdtA1ContratoTrabajos() {
		return this.fdtA1ContratoTrabajos;
	}

	public void setFdtA1ContratoTrabajos(List<FdtA1ContratoTrabajo> fdtA1ContratoTrabajos) {
		this.fdtA1ContratoTrabajos = fdtA1ContratoTrabajos;
	}
	
	public List<FdtA1PatSustituto> getFdtA1PatSustitutos() {
		return this.fdtA1PatSustitutos;
	}

	public void setFdtA1PatSustitutos(List<FdtA1PatSustituto> fdtA1PatSustitutos) {
		this.fdtA1PatSustitutos = fdtA1PatSustitutos;
	}
	
	public List<FdtA3Clausula> getFdtA3Clausulas() {
		return this.fdtA3Clausulas;
	}

	public void setFdtA3Clausulas(List<FdtA3Clausula> fdtA3Clausulas) {
		this.fdtA3Clausulas = fdtA3Clausulas;
	}
	
	public List<FdtGrupo> getFdtGrupos() {
		return this.fdtGrupos;
	}

	public void setFdtGrupos(List<FdtGrupo> fdtGrupos) {
		this.fdtGrupos = fdtGrupos;
	}
	
	public List<FdtA3PfOtro> getFdtA3PfOtros() {
		return this.fdtA3PfOtros;
	}

	public void setFdtA3PfOtros(List<FdtA3PfOtro> fdtA3PfOtros) {
		this.fdtA3PfOtros = fdtA3PfOtros;
	}
	
	public List<FdcRemuneracione> getFdcRemuneraciones() {
		return this.fdcRemuneraciones;
	}

	public void setFdcRemuneraciones(List<FdcRemuneracione> fdcRemuneraciones) {
		this.fdcRemuneraciones = fdcRemuneraciones;
	}
	
	public FdtCartaPresentacion getFdtCartaPresentacion() {
		return this.fdtCartaPresentacion;
	}

	public void setFdtCartaPresentacion(FdtCartaPresentacion fdtCartaPresentacion) {
		this.fdtCartaPresentacion = fdtCartaPresentacion;
	}
	
	public List<FdtCedrazDictamen> getFdtCedrazDictamens() {
		return this.fdtCedrazDictamens;
	}

	public void setFdtCedrazDictamens(List<FdtCedrazDictamen> fdtCedrazDictamens) {
		this.fdtCedrazDictamens = fdtCedrazDictamens;
	}
	
	public FdtDeleg getFdtDeleg() {
		return this.fdtDeleg;
	}

	public void setFdtDeleg(FdtDeleg fdtDeleg) {
		this.fdtDeleg = fdtDeleg;
	}
	
	public FdtAviso getFdtAviso() {
		return this.fdtAviso;
	}

	public void setFdtAviso(FdtAviso fdtAviso) {
		this.fdtAviso = fdtAviso;
	}
	
	public List<FdtRegistroPeriodo> getFdtRegistroPeriodos() {
		return this.fdtRegistroPeriodos;
	}

	public void setFdtRegistroPeriodos(List<FdtRegistroPeriodo> fdtRegistroPeriodos) {
		this.fdtRegistroPeriodos = fdtRegistroPeriodos;
	}
	
}