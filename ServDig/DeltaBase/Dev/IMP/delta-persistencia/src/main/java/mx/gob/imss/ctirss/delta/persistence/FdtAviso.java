package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDT_AVISO database table.
 * 
 */
@Entity
@Table(name="FDT_AVISO")
public class FdtAviso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_AVISO", nullable=false, precision=22)
	private long idAviso;

	@Column(name="ANTECEDENTES_PATRON", nullable=false, length=1)
	private String antecedentesPatron;

	@Column(name="AUTORIZO_PATRON", length=1)
	private String autorizoPatron;

	@Column(name="CV_FOLIO_AVISO", nullable=false, length=20)
	private String cvFolioAviso;

	@Column(name="CV_SISTEMA_ORIGEN", precision=22)
	private BigDecimal cvSistemaOrigen;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ACTUALIZACION")
	private Date fhActualizacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_AUTORIZACION_RECHAZO")
	private Date fhAutorizacionRechazo;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_AVISO", nullable=false)
	private Date fhAviso;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_DEBE_AUTORIZAR")
	private Date fhDebeAutorizar;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_LIMITE_DIC", nullable=false)
	private Date fhLimiteDic;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_A", nullable=false)
	private Date fhPeriodoA;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_DE", nullable=false)
	private Date fhPeriodoDe;

	@Column(name="FIN_AVISO", length=1)
	private String finAviso;

	@Column(name="FOLIO_SISTEMA_ORIGEN", length=25)
	private String folioSistemaOrigen;

	@Column(name="IN_TP_PATRON", nullable=false, length=14)
	private String inTpPatron;

	@Column(name="NU_CONSECUTIVO", precision=22)
	private BigDecimal nuConsecutivo;

	@Column(name="NU_PROMEDIO_TRAB", precision=22)
	private BigDecimal nuPromedioTrab;

	@Column(name="NU_RPU", nullable=false, precision=22)
	private BigDecimal nuRpu;

	@Column(name="NU_TIPO_CONSTRUCCION", nullable=false, precision=22)
	private BigDecimal nuTipoConstruccion;

	@Column(name="NU_TIPO_PERIODICIDAD", nullable=false, precision=22)
	private BigDecimal nuTipoPeriodicidad;

	@Column(name="TX_ACTIVIDAD", length=200)
	private String txActividad;

	@Column(name="TX_AFIL15", length=15)
	private String txAfil15;

	@Column(name="TX_EVALUADOR", length=70)
	private String txEvaluador;

	@Column(name="TX_REPRESENTANTE", length=80)
	private String txRepresentante;

	@Column(name="TX_UBICACION_OBRA", length=200)
	private String txUbicacionObra;

	@Column(name="VOBO_CONTADOR", precision=22)
	private BigDecimal voboContador;

	@Column(name="VOBO_PATRON", precision=22)
	private BigDecimal voboPatron;

	//bi-directional many-to-one association to FdcTpostatus
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="STATUS_ENTREGA_DICT")
	private FdcTpostatus fdcTpostatus;

	//bi-directional many-to-one association to FdtAvisoAntecedente
	@OneToMany(mappedBy="fdtAviso")
	private List<FdtAvisoAntecedente> fdtAvisoAntecedentes;

	//bi-directional many-to-one association to FdtAvisoSustitucion
	@OneToMany(mappedBy="fdtAviso")
	private List<FdtAvisoSustitucion> fdtAvisoSustitucions;

	//bi-directional many-to-one association to FdtControFlujoAnexo
	@OneToMany(mappedBy="fdtAviso")
	private List<FdtControFlujoAnexo> fdtControFlujoAnexos;

	//bi-directional many-to-one association to FdtDictamenesRechazado
	@OneToMany(mappedBy="fdtAviso")
	private List<FdtDictamenesRechazado> fdtDictamenesRechazados;

	//bi-directional many-to-one association to FdtPatronCpa
	@OneToMany(mappedBy="fdtAviso")
	private List<FdtPatronCpa> fdtPatronCpas;

	//bi-directional many-to-one association to FdtPatronHi
	@OneToMany(mappedBy="fdtAviso")
	private List<FdtPatronHi> fdtPatronHis;

	//bi-directional many-to-one association to FdtPatronNuevo
	@OneToMany(mappedBy="fdtAviso")
	private List<FdtPatronNuevo> fdtPatronNuevos;

	//bi-directional many-to-one association to FdtPeriodo
	@OneToMany(mappedBy="fdtAviso")
	private List<FdtPeriodo> fdtPeriodos;

	//bi-directional many-to-one association to FdtSolicitudProrroga
	@OneToMany(mappedBy="fdtAviso")
	private List<FdtSolicitudProrroga> fdtSolicitudProrrogas;

    public FdtAviso() {
    }

	public long getIdAviso() {
		return this.idAviso;
	}

	public void setIdAviso(long idAviso) {
		this.idAviso = idAviso;
	}

	public String getAntecedentesPatron() {
		return this.antecedentesPatron;
	}

	public void setAntecedentesPatron(String antecedentesPatron) {
		this.antecedentesPatron = antecedentesPatron;
	}

	public String getAutorizoPatron() {
		return this.autorizoPatron;
	}

	public void setAutorizoPatron(String autorizoPatron) {
		this.autorizoPatron = autorizoPatron;
	}

	public String getCvFolioAviso() {
		return this.cvFolioAviso;
	}

	public void setCvFolioAviso(String cvFolioAviso) {
		this.cvFolioAviso = cvFolioAviso;
	}

	public BigDecimal getCvSistemaOrigen() {
		return this.cvSistemaOrigen;
	}

	public void setCvSistemaOrigen(BigDecimal cvSistemaOrigen) {
		this.cvSistemaOrigen = cvSistemaOrigen;
	}

	public Date getFhActualizacion() {
		return this.fhActualizacion;
	}

	public void setFhActualizacion(Date fhActualizacion) {
		this.fhActualizacion = fhActualizacion;
	}

	public Date getFhAutorizacionRechazo() {
		return this.fhAutorizacionRechazo;
	}

	public void setFhAutorizacionRechazo(Date fhAutorizacionRechazo) {
		this.fhAutorizacionRechazo = fhAutorizacionRechazo;
	}

	public Date getFhAviso() {
		return this.fhAviso;
	}

	public void setFhAviso(Date fhAviso) {
		this.fhAviso = fhAviso;
	}

	public Date getFhDebeAutorizar() {
		return this.fhDebeAutorizar;
	}

	public void setFhDebeAutorizar(Date fhDebeAutorizar) {
		this.fhDebeAutorizar = fhDebeAutorizar;
	}

	public Date getFhLimiteDic() {
		return this.fhLimiteDic;
	}

	public void setFhLimiteDic(Date fhLimiteDic) {
		this.fhLimiteDic = fhLimiteDic;
	}

	public Date getFhPeriodoA() {
		return this.fhPeriodoA;
	}

	public void setFhPeriodoA(Date fhPeriodoA) {
		this.fhPeriodoA = fhPeriodoA;
	}

	public Date getFhPeriodoDe() {
		return this.fhPeriodoDe;
	}

	public void setFhPeriodoDe(Date fhPeriodoDe) {
		this.fhPeriodoDe = fhPeriodoDe;
	}

	public String getFinAviso() {
		return this.finAviso;
	}

	public void setFinAviso(String finAviso) {
		this.finAviso = finAviso;
	}

	public String getFolioSistemaOrigen() {
		return this.folioSistemaOrigen;
	}

	public void setFolioSistemaOrigen(String folioSistemaOrigen) {
		this.folioSistemaOrigen = folioSistemaOrigen;
	}

	public String getInTpPatron() {
		return this.inTpPatron;
	}

	public void setInTpPatron(String inTpPatron) {
		this.inTpPatron = inTpPatron;
	}

	public BigDecimal getNuConsecutivo() {
		return this.nuConsecutivo;
	}

	public void setNuConsecutivo(BigDecimal nuConsecutivo) {
		this.nuConsecutivo = nuConsecutivo;
	}

	public BigDecimal getNuPromedioTrab() {
		return this.nuPromedioTrab;
	}

	public void setNuPromedioTrab(BigDecimal nuPromedioTrab) {
		this.nuPromedioTrab = nuPromedioTrab;
	}

	public BigDecimal getNuRpu() {
		return this.nuRpu;
	}

	public void setNuRpu(BigDecimal nuRpu) {
		this.nuRpu = nuRpu;
	}

	public BigDecimal getNuTipoConstruccion() {
		return this.nuTipoConstruccion;
	}

	public void setNuTipoConstruccion(BigDecimal nuTipoConstruccion) {
		this.nuTipoConstruccion = nuTipoConstruccion;
	}

	public BigDecimal getNuTipoPeriodicidad() {
		return this.nuTipoPeriodicidad;
	}

	public void setNuTipoPeriodicidad(BigDecimal nuTipoPeriodicidad) {
		this.nuTipoPeriodicidad = nuTipoPeriodicidad;
	}

	public String getTxActividad() {
		return this.txActividad;
	}

	public void setTxActividad(String txActividad) {
		this.txActividad = txActividad;
	}

	public String getTxAfil15() {
		return this.txAfil15;
	}

	public void setTxAfil15(String txAfil15) {
		this.txAfil15 = txAfil15;
	}

	public String getTxEvaluador() {
		return this.txEvaluador;
	}

	public void setTxEvaluador(String txEvaluador) {
		this.txEvaluador = txEvaluador;
	}

	public String getTxRepresentante() {
		return this.txRepresentante;
	}

	public void setTxRepresentante(String txRepresentante) {
		this.txRepresentante = txRepresentante;
	}

	public String getTxUbicacionObra() {
		return this.txUbicacionObra;
	}

	public void setTxUbicacionObra(String txUbicacionObra) {
		this.txUbicacionObra = txUbicacionObra;
	}

	public BigDecimal getVoboContador() {
		return this.voboContador;
	}

	public void setVoboContador(BigDecimal voboContador) {
		this.voboContador = voboContador;
	}

	public BigDecimal getVoboPatron() {
		return this.voboPatron;
	}

	public void setVoboPatron(BigDecimal voboPatron) {
		this.voboPatron = voboPatron;
	}

	public FdcTpostatus getFdcTpostatus() {
		return this.fdcTpostatus;
	}

	public void setFdcTpostatus(FdcTpostatus fdcTpostatus) {
		this.fdcTpostatus = fdcTpostatus;
	}
	
	public List<FdtAvisoAntecedente> getFdtAvisoAntecedentes() {
		return this.fdtAvisoAntecedentes;
	}

	public void setFdtAvisoAntecedentes(List<FdtAvisoAntecedente> fdtAvisoAntecedentes) {
		this.fdtAvisoAntecedentes = fdtAvisoAntecedentes;
	}
	
	public List<FdtAvisoSustitucion> getFdtAvisoSustitucions() {
		return this.fdtAvisoSustitucions;
	}

	public void setFdtAvisoSustitucions(List<FdtAvisoSustitucion> fdtAvisoSustitucions) {
		this.fdtAvisoSustitucions = fdtAvisoSustitucions;
	}
	
	public List<FdtControFlujoAnexo> getFdtControFlujoAnexos() {
		return this.fdtControFlujoAnexos;
	}

	public void setFdtControFlujoAnexos(List<FdtControFlujoAnexo> fdtControFlujoAnexos) {
		this.fdtControFlujoAnexos = fdtControFlujoAnexos;
	}
	
	public List<FdtDictamenesRechazado> getFdtDictamenesRechazados() {
		return this.fdtDictamenesRechazados;
	}

	public void setFdtDictamenesRechazados(List<FdtDictamenesRechazado> fdtDictamenesRechazados) {
		this.fdtDictamenesRechazados = fdtDictamenesRechazados;
	}
	
	public List<FdtPatronCpa> getFdtPatronCpas() {
		return this.fdtPatronCpas;
	}

	public void setFdtPatronCpas(List<FdtPatronCpa> fdtPatronCpas) {
		this.fdtPatronCpas = fdtPatronCpas;
	}
	
	public List<FdtPatronHi> getFdtPatronHis() {
		return this.fdtPatronHis;
	}

	public void setFdtPatronHis(List<FdtPatronHi> fdtPatronHis) {
		this.fdtPatronHis = fdtPatronHis;
	}
	
	public List<FdtPatronNuevo> getFdtPatronNuevos() {
		return this.fdtPatronNuevos;
	}

	public void setFdtPatronNuevos(List<FdtPatronNuevo> fdtPatronNuevos) {
		this.fdtPatronNuevos = fdtPatronNuevos;
	}
	
	public List<FdtPeriodo> getFdtPeriodos() {
		return this.fdtPeriodos;
	}

	public void setFdtPeriodos(List<FdtPeriodo> fdtPeriodos) {
		this.fdtPeriodos = fdtPeriodos;
	}
	
	public List<FdtSolicitudProrroga> getFdtSolicitudProrrogas() {
		return this.fdtSolicitudProrrogas;
	}

	public void setFdtSolicitudProrrogas(List<FdtSolicitudProrroga> fdtSolicitudProrrogas) {
		this.fdtSolicitudProrrogas = fdtSolicitudProrrogas;
	}
	
}