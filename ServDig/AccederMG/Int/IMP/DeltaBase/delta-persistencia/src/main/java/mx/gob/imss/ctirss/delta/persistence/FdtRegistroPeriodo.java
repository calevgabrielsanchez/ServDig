package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDT_REGISTRO_PERIODO database table.
 * 
 */
@Entity
@Table(name="FDT_REGISTRO_PERIODO")
public class FdtRegistroPeriodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtRegistroPeriodoPK id;

	@Column(name="EXCEDE_POR_CONCEPTO", precision=22)
	private BigDecimal excedePorConcepto;

	@Column(name="EXCEDE_POR_SUJETOS", precision=22)
	private BigDecimal excedePorSujetos;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PRESENTACION")
	private Date fhPresentacion;

	@Column(name="ID_SALVEDAD", length=1)
	private String idSalvedad;

	@Column(name="IM_TOTAL_PER_TOPADO_CUOTAS", precision=12, scale=2)
	private BigDecimal imTotalPerTopadoCuotas;

	@Column(name="IM_TOTAL_PER_TOPADO_CV", precision=12, scale=2)
	private BigDecimal imTotalPerTopadoCv;

	@Column(name="IM_TOTAL_PERCEP_A_EJERCICIO", precision=12, scale=2)
	private BigDecimal imTotalPercepAEjercicio;

	@Column(name="IM_TOTAL_PERCEP_EJERCICIO", precision=12, scale=2)
	private BigDecimal imTotalPercepEjercicio;

	@Column(name="IM_TOTAL_SAL_TOPE_CUOTAS", precision=12, scale=2)
	private BigDecimal imTotalSalTopeCuotas;

	@Column(name="IM_TOTAL_SAL_TOPE_CV", precision=12, scale=2)
	private BigDecimal imTotalSalTopeCv;

	@Column(name="IN_BASE_COT_TIENE", length=1)
	private String inBaseCotTiene;

	@Column(name="IN_PATRON_SUSTITUTO", length=1)
	private String inPatronSustituto;

	@Column(name="IN_PRESENT_A_AFIL", length=1)
	private String inPresentAAfil;

	@Column(name="NU_AVISOS_BAJA", precision=6)
	private BigDecimal nuAvisosBaja;

	@Column(name="NU_AVISOS_DESC", precision=6)
	private BigDecimal nuAvisosDesc;

	@Column(name="NU_AVISOS_INSCR", precision=6)
	private BigDecimal nuAvisosInscr;

	@Column(name="NU_MOD_SAL_ASC", precision=6)
	private BigDecimal nuModSalAsc;

	@Column(name="NU_MOD_SAL_DESC", precision=6)
	private BigDecimal nuModSalDesc;

	@Column(name="NU_TOTAL_TRAB", precision=12)
	private BigDecimal nuTotalTrab;

	@Column(name="NU_TRAB_INSCRITOS", precision=6)
	private BigDecimal nuTrabInscritos;

	@Column(name="NU_TRAB_REC", precision=12)
	private BigDecimal nuTrabRec;

	@Column(name="NU_TRAB_REGULARIZADOS", precision=6)
	private BigDecimal nuTrabRegularizados;

	@Column(name="NU_TRAB_REVISADOS", precision=12)
	private BigDecimal nuTrabRevisados;

	@Column(name="TX_BASE_COT", length=150)
	private String txBaseCot;

    @Lob()
	@Column(name="TX_CONCEPTOS_OMISIONES_ANEXOII")
	private byte[] txConceptosOmisionesAnexoii;

	//bi-directional many-to-one association to FdtA1Clasificacion
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA1Clasificacion> fdtA1Clasificacions;

	//bi-directional many-to-one association to FdtA1CopEjer
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA1CopEjer> fdtA1CopEjers;

	//bi-directional many-to-one association to FdtA2Cuota
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA2Cuota> fdtA2Cuotas;

	//bi-directional many-to-one association to FdtA2Rcv
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA2Rcv> fdtA2Rcvs;

	//bi-directional many-to-one association to FdtA3ActPerNoSujeto
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA3ActPerNoSujeto> fdtA3ActPerNoSujetos;

	//bi-directional many-to-one association to FdtA3Muestra
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA3Muestra> fdtA3Muestras;

	//bi-directional many-to-one association to FdtA3PersonaSujeto
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA3PersonaSujeto> fdtA3PersonaSujetos;

	//bi-directional many-to-one association to FdtA4GastobCuenta
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA4GastobCuenta> fdtA4GastobCuentas;

	//bi-directional many-to-one association to FdtA4GastoBalance
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA4GastoBalance> fdtA4GastoBalances;

	//bi-directional many-to-one association to FdtA4PercepVariable
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA4PercepVariable> fdtA4PercepVariables;

	//bi-directional many-to-many association to FdcRemuneracione
	@ManyToMany(mappedBy="fdtRegistroPeriodos")
	private List<FdcRemuneracione> fdcRemuneraciones;

	//bi-directional many-to-one association to FdtA4Remuneracion
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA4Remuneracion> fdtA4Remuneracions;

	//bi-directional many-to-one association to FdtA5Maquinaria
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA5Maquinaria> fdtA5Maquinarias;

	//bi-directional many-to-one association to FdtA5MatPrima
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA5MatPrima> fdtA5MatPrimas;

	//bi-directional many-to-one association to FdtA5Personal
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtA5Personal> fdtA5Personals;

	//bi-directional many-to-one association to FdtActcomplementaria
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtActcomplementaria> fdtActcomplementarias;

	//bi-directional many-to-one association to FdtAnexo5
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtAnexo5> fdtAnexo5s;

	//bi-directional many-to-one association to FdtBeneficiarioservicio
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtBeneficiarioservicio> fdtBeneficiarioservicios;

	//bi-directional many-to-one association to FdtConstruccionObra
	@OneToMany(mappedBy="fdtRegistroPeriodo")
	private List<FdtConstruccionObra> fdtConstruccionObras;

	//bi-directional many-to-many association to FdtContratoGrupo
//	@ManyToMany(mappedBy="fdtRegistroPeriodos")
//	private List<FdtContratoGrupo> fdtContratoGrupos;

	//bi-directional many-to-many association to FdtA3GrupoClausula
//	@ManyToMany(mappedBy="fdtRegistroPeriodos")
//	private List<FdtA3GrupoClausula> fdtA3GrupoClausulas;

	//bi-directional many-to-many association to FdtA3GrupoFactore
//	@ManyToMany(mappedBy="fdtRegistroPeriodos")
//	private List<FdtA3GrupoFactore> fdtA3GrupoFactores;

	//bi-directional many-to-one association to FdtPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DICTAMEN", nullable=false, insertable=false, updatable=false)
	private FdtPeriodo fdtPeriodo;

    public FdtRegistroPeriodo() {
    }

	public FdtRegistroPeriodoPK getId() {
		return this.id;
	}

	public void setId(FdtRegistroPeriodoPK id) {
		this.id = id;
	}
	
	public BigDecimal getExcedePorConcepto() {
		return this.excedePorConcepto;
	}

	public void setExcedePorConcepto(BigDecimal excedePorConcepto) {
		this.excedePorConcepto = excedePorConcepto;
	}

	public BigDecimal getExcedePorSujetos() {
		return this.excedePorSujetos;
	}

	public void setExcedePorSujetos(BigDecimal excedePorSujetos) {
		this.excedePorSujetos = excedePorSujetos;
	}

	public Date getFhPresentacion() {
		return this.fhPresentacion;
	}

	public void setFhPresentacion(Date fhPresentacion) {
		this.fhPresentacion = fhPresentacion;
	}

	public String getIdSalvedad() {
		return this.idSalvedad;
	}

	public void setIdSalvedad(String idSalvedad) {
		this.idSalvedad = idSalvedad;
	}

	public BigDecimal getImTotalPerTopadoCuotas() {
		return this.imTotalPerTopadoCuotas;
	}

	public void setImTotalPerTopadoCuotas(BigDecimal imTotalPerTopadoCuotas) {
		this.imTotalPerTopadoCuotas = imTotalPerTopadoCuotas;
	}

	public BigDecimal getImTotalPerTopadoCv() {
		return this.imTotalPerTopadoCv;
	}

	public void setImTotalPerTopadoCv(BigDecimal imTotalPerTopadoCv) {
		this.imTotalPerTopadoCv = imTotalPerTopadoCv;
	}

	public BigDecimal getImTotalPercepAEjercicio() {
		return this.imTotalPercepAEjercicio;
	}

	public void setImTotalPercepAEjercicio(BigDecimal imTotalPercepAEjercicio) {
		this.imTotalPercepAEjercicio = imTotalPercepAEjercicio;
	}

	public BigDecimal getImTotalPercepEjercicio() {
		return this.imTotalPercepEjercicio;
	}

	public void setImTotalPercepEjercicio(BigDecimal imTotalPercepEjercicio) {
		this.imTotalPercepEjercicio = imTotalPercepEjercicio;
	}

	public BigDecimal getImTotalSalTopeCuotas() {
		return this.imTotalSalTopeCuotas;
	}

	public void setImTotalSalTopeCuotas(BigDecimal imTotalSalTopeCuotas) {
		this.imTotalSalTopeCuotas = imTotalSalTopeCuotas;
	}

	public BigDecimal getImTotalSalTopeCv() {
		return this.imTotalSalTopeCv;
	}

	public void setImTotalSalTopeCv(BigDecimal imTotalSalTopeCv) {
		this.imTotalSalTopeCv = imTotalSalTopeCv;
	}

	public String getInBaseCotTiene() {
		return this.inBaseCotTiene;
	}

	public void setInBaseCotTiene(String inBaseCotTiene) {
		this.inBaseCotTiene = inBaseCotTiene;
	}

	public String getInPatronSustituto() {
		return this.inPatronSustituto;
	}

	public void setInPatronSustituto(String inPatronSustituto) {
		this.inPatronSustituto = inPatronSustituto;
	}

	public String getInPresentAAfil() {
		return this.inPresentAAfil;
	}

	public void setInPresentAAfil(String inPresentAAfil) {
		this.inPresentAAfil = inPresentAAfil;
	}

	public BigDecimal getNuAvisosBaja() {
		return this.nuAvisosBaja;
	}

	public void setNuAvisosBaja(BigDecimal nuAvisosBaja) {
		this.nuAvisosBaja = nuAvisosBaja;
	}

	public BigDecimal getNuAvisosDesc() {
		return this.nuAvisosDesc;
	}

	public void setNuAvisosDesc(BigDecimal nuAvisosDesc) {
		this.nuAvisosDesc = nuAvisosDesc;
	}

	public BigDecimal getNuAvisosInscr() {
		return this.nuAvisosInscr;
	}

	public void setNuAvisosInscr(BigDecimal nuAvisosInscr) {
		this.nuAvisosInscr = nuAvisosInscr;
	}

	public BigDecimal getNuModSalAsc() {
		return this.nuModSalAsc;
	}

	public void setNuModSalAsc(BigDecimal nuModSalAsc) {
		this.nuModSalAsc = nuModSalAsc;
	}

	public BigDecimal getNuModSalDesc() {
		return this.nuModSalDesc;
	}

	public void setNuModSalDesc(BigDecimal nuModSalDesc) {
		this.nuModSalDesc = nuModSalDesc;
	}

	public BigDecimal getNuTotalTrab() {
		return this.nuTotalTrab;
	}

	public void setNuTotalTrab(BigDecimal nuTotalTrab) {
		this.nuTotalTrab = nuTotalTrab;
	}

	public BigDecimal getNuTrabInscritos() {
		return this.nuTrabInscritos;
	}

	public void setNuTrabInscritos(BigDecimal nuTrabInscritos) {
		this.nuTrabInscritos = nuTrabInscritos;
	}

	public BigDecimal getNuTrabRec() {
		return this.nuTrabRec;
	}

	public void setNuTrabRec(BigDecimal nuTrabRec) {
		this.nuTrabRec = nuTrabRec;
	}

	public BigDecimal getNuTrabRegularizados() {
		return this.nuTrabRegularizados;
	}

	public void setNuTrabRegularizados(BigDecimal nuTrabRegularizados) {
		this.nuTrabRegularizados = nuTrabRegularizados;
	}

	public BigDecimal getNuTrabRevisados() {
		return this.nuTrabRevisados;
	}

	public void setNuTrabRevisados(BigDecimal nuTrabRevisados) {
		this.nuTrabRevisados = nuTrabRevisados;
	}

	public String getTxBaseCot() {
		return this.txBaseCot;
	}

	public void setTxBaseCot(String txBaseCot) {
		this.txBaseCot = txBaseCot;
	}

	public byte[] getTxConceptosOmisionesAnexoii() {
		return this.txConceptosOmisionesAnexoii;
	}

	public void setTxConceptosOmisionesAnexoii(byte[] txConceptosOmisionesAnexoii) {
		this.txConceptosOmisionesAnexoii = txConceptosOmisionesAnexoii != null ? txConceptosOmisionesAnexoii.clone() : null;
	}

	public List<FdtA1Clasificacion> getFdtA1Clasificacions() {
		return this.fdtA1Clasificacions;
	}

	public void setFdtA1Clasificacions(List<FdtA1Clasificacion> fdtA1Clasificacions) {
		this.fdtA1Clasificacions = fdtA1Clasificacions;
	}
	
	public List<FdtA1CopEjer> getFdtA1CopEjers() {
		return this.fdtA1CopEjers;
	}

	public void setFdtA1CopEjers(List<FdtA1CopEjer> fdtA1CopEjers) {
		this.fdtA1CopEjers = fdtA1CopEjers;
	}
	
	public List<FdtA2Cuota> getFdtA2Cuotas() {
		return this.fdtA2Cuotas;
	}

	public void setFdtA2Cuotas(List<FdtA2Cuota> fdtA2Cuotas) {
		this.fdtA2Cuotas = fdtA2Cuotas;
	}
	
	public List<FdtA2Rcv> getFdtA2Rcvs() {
		return this.fdtA2Rcvs;
	}

	public void setFdtA2Rcvs(List<FdtA2Rcv> fdtA2Rcvs) {
		this.fdtA2Rcvs = fdtA2Rcvs;
	}
	
	public List<FdtA3ActPerNoSujeto> getFdtA3ActPerNoSujetos() {
		return this.fdtA3ActPerNoSujetos;
	}

	public void setFdtA3ActPerNoSujetos(List<FdtA3ActPerNoSujeto> fdtA3ActPerNoSujetos) {
		this.fdtA3ActPerNoSujetos = fdtA3ActPerNoSujetos;
	}
	
	public List<FdtA3Muestra> getFdtA3Muestras() {
		return this.fdtA3Muestras;
	}

	public void setFdtA3Muestras(List<FdtA3Muestra> fdtA3Muestras) {
		this.fdtA3Muestras = fdtA3Muestras;
	}
	
	public List<FdtA3PersonaSujeto> getFdtA3PersonaSujetos() {
		return this.fdtA3PersonaSujetos;
	}

	public void setFdtA3PersonaSujetos(List<FdtA3PersonaSujeto> fdtA3PersonaSujetos) {
		this.fdtA3PersonaSujetos = fdtA3PersonaSujetos;
	}
	
	public List<FdtA4GastobCuenta> getFdtA4GastobCuentas() {
		return this.fdtA4GastobCuentas;
	}

	public void setFdtA4GastobCuentas(List<FdtA4GastobCuenta> fdtA4GastobCuentas) {
		this.fdtA4GastobCuentas = fdtA4GastobCuentas;
	}
	
	public List<FdtA4GastoBalance> getFdtA4GastoBalances() {
		return this.fdtA4GastoBalances;
	}

	public void setFdtA4GastoBalances(List<FdtA4GastoBalance> fdtA4GastoBalances) {
		this.fdtA4GastoBalances = fdtA4GastoBalances;
	}
	
	public List<FdtA4PercepVariable> getFdtA4PercepVariables() {
		return this.fdtA4PercepVariables;
	}

	public void setFdtA4PercepVariables(List<FdtA4PercepVariable> fdtA4PercepVariables) {
		this.fdtA4PercepVariables = fdtA4PercepVariables;
	}
	
	public List<FdcRemuneracione> getFdcRemuneraciones() {
		return this.fdcRemuneraciones;
	}

	public void setFdcRemuneraciones(List<FdcRemuneracione> fdcRemuneraciones) {
		this.fdcRemuneraciones = fdcRemuneraciones;
	}
	
	public List<FdtA4Remuneracion> getFdtA4Remuneracions() {
		return this.fdtA4Remuneracions;
	}

	public void setFdtA4Remuneracions(List<FdtA4Remuneracion> fdtA4Remuneracions) {
		this.fdtA4Remuneracions = fdtA4Remuneracions;
	}
	
	public List<FdtA5Maquinaria> getFdtA5Maquinarias() {
		return this.fdtA5Maquinarias;
	}

	public void setFdtA5Maquinarias(List<FdtA5Maquinaria> fdtA5Maquinarias) {
		this.fdtA5Maquinarias = fdtA5Maquinarias;
	}
	
	public List<FdtA5MatPrima> getFdtA5MatPrimas() {
		return this.fdtA5MatPrimas;
	}

	public void setFdtA5MatPrimas(List<FdtA5MatPrima> fdtA5MatPrimas) {
		this.fdtA5MatPrimas = fdtA5MatPrimas;
	}
	
	public List<FdtA5Personal> getFdtA5Personals() {
		return this.fdtA5Personals;
	}

	public void setFdtA5Personals(List<FdtA5Personal> fdtA5Personals) {
		this.fdtA5Personals = fdtA5Personals;
	}
	
	public List<FdtActcomplementaria> getFdtActcomplementarias() {
		return this.fdtActcomplementarias;
	}

	public void setFdtActcomplementarias(List<FdtActcomplementaria> fdtActcomplementarias) {
		this.fdtActcomplementarias = fdtActcomplementarias;
	}
	
	public List<FdtAnexo5> getFdtAnexo5s() {
		return this.fdtAnexo5s;
	}

	public void setFdtAnexo5s(List<FdtAnexo5> fdtAnexo5s) {
		this.fdtAnexo5s = fdtAnexo5s;
	}
	
	public List<FdtBeneficiarioservicio> getFdtBeneficiarioservicios() {
		return this.fdtBeneficiarioservicios;
	}

	public void setFdtBeneficiarioservicios(List<FdtBeneficiarioservicio> fdtBeneficiarioservicios) {
		this.fdtBeneficiarioservicios = fdtBeneficiarioservicios;
	}
	
	public List<FdtConstruccionObra> getFdtConstruccionObras() {
		return this.fdtConstruccionObras;
	}

	public void setFdtConstruccionObras(List<FdtConstruccionObra> fdtConstruccionObras) {
		this.fdtConstruccionObras = fdtConstruccionObras;
	}
	
//	public List<FdtContratoGrupo> getFdtContratoGrupos() {
//		return this.fdtContratoGrupos;
//	}
//
//	public void setFdtContratoGrupos(List<FdtContratoGrupo> fdtContratoGrupos) {
//		this.fdtContratoGrupos = fdtContratoGrupos;
//	}
	
//	public List<FdtA3GrupoClausula> getFdtA3GrupoClausulas() {
//		return this.fdtA3GrupoClausulas;
//	}
//
//	public void setFdtA3GrupoClausulas(List<FdtA3GrupoClausula> fdtA3GrupoClausulas) {
//		this.fdtA3GrupoClausulas = fdtA3GrupoClausulas;
//	}
	
//	public List<FdtA3GrupoFactore> getFdtA3GrupoFactores() {
//		return this.fdtA3GrupoFactores;
//	}
//
//	public void setFdtA3GrupoFactores(List<FdtA3GrupoFactore> fdtA3GrupoFactores) {
//		this.fdtA3GrupoFactores = fdtA3GrupoFactores;
//	}
	
	public FdtPeriodo getFdtPeriodo() {
		return this.fdtPeriodo;
	}

	public void setFdtPeriodo(FdtPeriodo fdtPeriodo) {
		this.fdtPeriodo = fdtPeriodo;
	}
	
}