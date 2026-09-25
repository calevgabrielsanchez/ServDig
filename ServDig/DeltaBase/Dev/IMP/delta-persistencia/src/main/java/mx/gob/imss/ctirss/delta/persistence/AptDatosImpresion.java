package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the APT_DATOS_IMPRESION database table.
 * 
 */
@Entity
@Table(name="APT_DATOS_IMPRESION")
@NamedQuery(name="AptDatosImpresion.findAll", query="SELECT a FROM AptDatosImpresion a")
public class AptDatosImpresion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AptDatosImpresionPK id;

	@Column(name="ANO_RESOLUCION")
	private String anoResolucion;

	@Column(name="ARTICULO_INCAPACIDAD")
	private String articuloIncapacidad;

	@Column(name="CARGO_FIRMA_RESOL")
	private String cargoFirmaResol;

	@Column(name="CIUDAD_EMISION")
	private String ciudadEmision;

	@Column(name="CUANTIA_PEN_VEJEZ")
	private BigDecimal cuantiaPenVejez;

	@Column(name="CUANTIA_PENSION_INV_A")
	private BigDecimal cuantiaPensionInvA;

	@Column(name="CUANTIA_PENSION_INV_M")
	private BigDecimal cuantiaPensionInvM;

	@Column(name="CUANTIA_PENSION_INV_MES")
	private BigDecimal cuantiaPensionInvMes;

	@Column(name="CUANTIA_PENSION_INV_MES_IG")
	private BigDecimal cuantiaPensionInvMesIg;

	@Column(name="CVE_CURP")
	private String cveCurp;

	@Column(name="CVE_REGISTRO_PATRONAL")
	private String cveRegistroPatronal;

	@Column(name="CVE_RFC")
	private String cveRfc;

	@Column(name="CVE_UMF")
	private String cveUmf;

	@Column(name="CVE_UMF_NEGADA")
	private String cveUmfNegada;

	@Column(name="DES_DELEGACION")
	private String desDelegacion;

	@Column(name="DES_SUBDELEGACION")
	private String desSubdelegacion;

	@Column(name="DESC_CARACTER")
	private String descCaracter;

	@Column(name="DESC_INCAPACIDAD")
	private String descIncapacidad;

	@Column(name="DESC_RAMA")
	private String descRama;

	@Column(name="DIA_RESOLUCION")
	private String diaResolucion;

	private String edad;

	@Column(name="ESTADO_EMISION")
	private String estadoEmision;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_BAJA")
	private Date fecBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_DICTAMEN")
	private Date fecDictamen;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ELABORACION")
	private Date fecElaboracion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INI_PENSION")
	private Date fecIniPension;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PENSION")
	private Date fecInicioPension;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INSCRIPCION")
	private Date fecInscripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_NACIMIENTO")
	private Date fecNacimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REALIZACION_RIESGO")
	private Date fecRealizacionRiesgo;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_SOLICITUD")
	private Date fecSolicitud;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_VENCIMIENTO_PROV")
	private Date fecVencimientoProv;

	@Column(name="FRACCION_INCAPACIDAD")
	private String fraccionIncapacidad;

	@Column(name="IMP_AYUDA_ASIST_A")
	private BigDecimal impAyudaAsistA;

	@Column(name="IMP_AYUDA_ASIST_M")
	private BigDecimal impAyudaAsistM;

	@Column(name="IMP_CESANTIA_ANUAL")
	private BigDecimal impCesantiaAnual;

	@Column(name="IMP_CUANTIA_BASICA")
	private BigDecimal impCuantiaBasica;

	@Column(name="IMP_CUANTIA_INCREMENTOS")
	private BigDecimal impCuantiaIncrementos;

	@Column(name="IMP_CUANTIA_MENSUAL")
	private BigDecimal impCuantiaMensual;

	@Column(name="IMP_IG_M")
	private BigDecimal impIgM;

	@Column(name="IMP_INC_PERM_MENSUAL")
	private BigDecimal impIncPermMensual;

	@Column(name="IMP_PAGO_GPO")
	private BigDecimal impPagoGpo;

	@Column(name="IMP_PEN_GAR_INV_A")
	private BigDecimal impPenGarInvA;

	@Column(name="IMP_PEN_GAR_INV_M")
	private BigDecimal impPenGarInvM;

	@Column(name="IMP_PENSION_GARANTIZADA_A")
	private BigDecimal impPensionGarantizadaA;

	@Column(name="IMP_PENSION_GARANTIZADA_M")
	private BigDecimal impPensionGarantizadaM;

	@Column(name="IMP_PENSION_INPC")
	private BigDecimal impPensionInpc;

	@Column(name="IMP_SALARIO_DIARIO")
	private BigDecimal impSalarioDiario;

	@Column(name="IMP_SALDO_INDIVIDUAL")
	private BigDecimal impSaldoIndividual;

	@Column(name="IMP_SUMA_ASIG_FAM")
	private BigDecimal impSumaAsigFam;

	@Column(name="IMP_SUMA_ASIG_FAM_A")
	private BigDecimal impSumaAsigFamA;

	@Column(name="IMP_TOTAL_PEN_INV_A")
	private BigDecimal impTotalPenInvA;

	@Column(name="IMP_TOTAL_PEN_INV_M")
	private BigDecimal impTotalPenInvM;

	@Column(name="IMP_TOTAL_PEN_VE_A")
	private BigDecimal impTotalPenVeA;

	@Column(name="IMP_TOTAL_PEN_VE_M")
	private BigDecimal impTotalPenVeM;

	@Column(name="LUGAR_PAGO")
	private String lugarPago;

	@Column(name="MES_RESOLUCION")
	private String mesResolucion;

	@Column(name="NOM_NOMBRE_ASEG")
	private String nomNombreAseg;

	@Column(name="NOM_NOMBRE_PATRON")
	private String nomNombrePatron;

	@Column(name="NOM_NOMBRE_SOLICITANTE")
	private String nomNombreSolicitante;

	@Column(name="NUM_FOLIO")
	private String numFolio;

	@Column(name="NUM_INC_CUANTIA_BASICA")
	private BigDecimal numIncCuantiaBasica;

	@Column(name="NUM_RESOLUCION")
	private String numResolucion;

	@Column(name="NUM_SEM_DIC90")
	private BigDecimal numSemDic90;

	@Column(name="NUM_SEM_RECON")
	private BigDecimal numSemRecon;

	@Column(name="POR_AYUDA_ASISTENCIAL")
	private BigDecimal porAyudaAsistencial;

	@Column(name="POR_AYUDA_MENSUAL_ASIST")
	private BigDecimal porAyudaMensualAsist;

	@Column(name="POR_CESANTIA")
	private BigDecimal porCesantia;

	@Column(name="POR_VALUACION")
	private BigDecimal porValuacion;

	@Column(name="RESPONSABLE_FIRMA_RESOL")
	private String responsableFirmaResol;

	//bi-directional many-to-one association to AptComponentesImpresion
	@OneToMany(mappedBy="aptDatosImpresion")
	private List<AptComponentesImpresion> aptComponentesImpresions;

	//bi-directional many-to-one association to SpcRegimen
	@ManyToOne
	@JoinColumn(name="ID_REGIMEN")
	private SpcRegimen spcRegimen;

	//bi-directional many-to-one association to SpcTipoPension
	@ManyToOne
	@JoinColumn(name="ID_TIPO_PENSION")
	private SpcTipoPension spcTipoPension;

	//bi-directional many-to-one association to AptResolucionImpresion
	@OneToMany(mappedBy="aptDatosImpresion")
	private List<AptResolucionImpresion> aptResolucionImpresions;

	public AptDatosImpresion() {
	}

	public AptDatosImpresionPK getId() {
		return this.id;
	}

	public void setId(AptDatosImpresionPK id) {
		this.id = id;
	}

	public String getAnoResolucion() {
		return this.anoResolucion;
	}

	public void setAnoResolucion(String anoResolucion) {
		this.anoResolucion = anoResolucion;
	}

	public String getArticuloIncapacidad() {
		return this.articuloIncapacidad;
	}

	public void setArticuloIncapacidad(String articuloIncapacidad) {
		this.articuloIncapacidad = articuloIncapacidad;
	}

	public String getCargoFirmaResol() {
		return this.cargoFirmaResol;
	}

	public void setCargoFirmaResol(String cargoFirmaResol) {
		this.cargoFirmaResol = cargoFirmaResol;
	}

	public String getCiudadEmision() {
		return this.ciudadEmision;
	}

	public void setCiudadEmision(String ciudadEmision) {
		this.ciudadEmision = ciudadEmision;
	}

	public BigDecimal getCuantiaPenVejez() {
		return this.cuantiaPenVejez;
	}

	public void setCuantiaPenVejez(BigDecimal cuantiaPenVejez) {
		this.cuantiaPenVejez = cuantiaPenVejez;
	}

	public BigDecimal getCuantiaPensionInvA() {
		return this.cuantiaPensionInvA;
	}

	public void setCuantiaPensionInvA(BigDecimal cuantiaPensionInvA) {
		this.cuantiaPensionInvA = cuantiaPensionInvA;
	}

	public BigDecimal getCuantiaPensionInvM() {
		return this.cuantiaPensionInvM;
	}

	public void setCuantiaPensionInvM(BigDecimal cuantiaPensionInvM) {
		this.cuantiaPensionInvM = cuantiaPensionInvM;
	}

	public BigDecimal getCuantiaPensionInvMes() {
		return this.cuantiaPensionInvMes;
	}

	public void setCuantiaPensionInvMes(BigDecimal cuantiaPensionInvMes) {
		this.cuantiaPensionInvMes = cuantiaPensionInvMes;
	}

	public BigDecimal getCuantiaPensionInvMesIg() {
		return this.cuantiaPensionInvMesIg;
	}

	public void setCuantiaPensionInvMesIg(BigDecimal cuantiaPensionInvMesIg) {
		this.cuantiaPensionInvMesIg = cuantiaPensionInvMesIg;
	}

	public String getCveCurp() {
		return this.cveCurp;
	}

	public void setCveCurp(String cveCurp) {
		this.cveCurp = cveCurp;
	}

	public String getCveRegistroPatronal() {
		return this.cveRegistroPatronal;
	}

	public void setCveRegistroPatronal(String cveRegistroPatronal) {
		this.cveRegistroPatronal = cveRegistroPatronal;
	}

	public String getCveRfc() {
		return this.cveRfc;
	}

	public void setCveRfc(String cveRfc) {
		this.cveRfc = cveRfc;
	}

	public String getCveUmf() {
		return this.cveUmf;
	}

	public void setCveUmf(String cveUmf) {
		this.cveUmf = cveUmf;
	}

	public String getCveUmfNegada() {
		return this.cveUmfNegada;
	}

	public void setCveUmfNegada(String cveUmfNegada) {
		this.cveUmfNegada = cveUmfNegada;
	}

	public String getDesDelegacion() {
		return this.desDelegacion;
	}

	public void setDesDelegacion(String desDelegacion) {
		this.desDelegacion = desDelegacion;
	}

	public String getDesSubdelegacion() {
		return this.desSubdelegacion;
	}

	public void setDesSubdelegacion(String desSubdelegacion) {
		this.desSubdelegacion = desSubdelegacion;
	}

	public String getDescCaracter() {
		return this.descCaracter;
	}

	public void setDescCaracter(String descCaracter) {
		this.descCaracter = descCaracter;
	}

	public String getDescIncapacidad() {
		return this.descIncapacidad;
	}

	public void setDescIncapacidad(String descIncapacidad) {
		this.descIncapacidad = descIncapacidad;
	}

	public String getDescRama() {
		return this.descRama;
	}

	public void setDescRama(String descRama) {
		this.descRama = descRama;
	}

	public String getDiaResolucion() {
		return this.diaResolucion;
	}

	public void setDiaResolucion(String diaResolucion) {
		this.diaResolucion = diaResolucion;
	}

	public String getEdad() {
		return this.edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getEstadoEmision() {
		return this.estadoEmision;
	}

	public void setEstadoEmision(String estadoEmision) {
		this.estadoEmision = estadoEmision;
	}

	public Date getFecBaja() {
		return this.fecBaja;
	}

	public void setFecBaja(Date fecBaja) {
		this.fecBaja = fecBaja;
	}

	public Date getFecDictamen() {
		return this.fecDictamen;
	}

	public void setFecDictamen(Date fecDictamen) {
		this.fecDictamen = fecDictamen;
	}

	public Date getFecElaboracion() {
		return this.fecElaboracion;
	}

	public void setFecElaboracion(Date fecElaboracion) {
		this.fecElaboracion = fecElaboracion;
	}

	public Date getFecIniPension() {
		return this.fecIniPension;
	}

	public void setFecIniPension(Date fecIniPension) {
		this.fecIniPension = fecIniPension;
	}

	public Date getFecInicioPension() {
		return this.fecInicioPension;
	}

	public void setFecInicioPension(Date fecInicioPension) {
		this.fecInicioPension = fecInicioPension;
	}

	public Date getFecInscripcion() {
		return this.fecInscripcion;
	}

	public void setFecInscripcion(Date fecInscripcion) {
		this.fecInscripcion = fecInscripcion;
	}

	public Date getFecNacimiento() {
		return this.fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public Date getFecRealizacionRiesgo() {
		return this.fecRealizacionRiesgo;
	}

	public void setFecRealizacionRiesgo(Date fecRealizacionRiesgo) {
		this.fecRealizacionRiesgo = fecRealizacionRiesgo;
	}

	public Date getFecSolicitud() {
		return this.fecSolicitud;
	}

	public void setFecSolicitud(Date fecSolicitud) {
		this.fecSolicitud = fecSolicitud;
	}

	public Date getFecVencimientoProv() {
		return this.fecVencimientoProv;
	}

	public void setFecVencimientoProv(Date fecVencimientoProv) {
		this.fecVencimientoProv = fecVencimientoProv;
	}

	public String getFraccionIncapacidad() {
		return this.fraccionIncapacidad;
	}

	public void setFraccionIncapacidad(String fraccionIncapacidad) {
		this.fraccionIncapacidad = fraccionIncapacidad;
	}

	public BigDecimal getImpAyudaAsistA() {
		return this.impAyudaAsistA;
	}

	public void setImpAyudaAsistA(BigDecimal impAyudaAsistA) {
		this.impAyudaAsistA = impAyudaAsistA;
	}

	public BigDecimal getImpAyudaAsistM() {
		return this.impAyudaAsistM;
	}

	public void setImpAyudaAsistM(BigDecimal impAyudaAsistM) {
		this.impAyudaAsistM = impAyudaAsistM;
	}

	public BigDecimal getImpCesantiaAnual() {
		return this.impCesantiaAnual;
	}

	public void setImpCesantiaAnual(BigDecimal impCesantiaAnual) {
		this.impCesantiaAnual = impCesantiaAnual;
	}

	public BigDecimal getImpCuantiaBasica() {
		return this.impCuantiaBasica;
	}

	public void setImpCuantiaBasica(BigDecimal impCuantiaBasica) {
		this.impCuantiaBasica = impCuantiaBasica;
	}

	public BigDecimal getImpCuantiaIncrementos() {
		return this.impCuantiaIncrementos;
	}

	public void setImpCuantiaIncrementos(BigDecimal impCuantiaIncrementos) {
		this.impCuantiaIncrementos = impCuantiaIncrementos;
	}

	public BigDecimal getImpCuantiaMensual() {
		return this.impCuantiaMensual;
	}

	public void setImpCuantiaMensual(BigDecimal impCuantiaMensual) {
		this.impCuantiaMensual = impCuantiaMensual;
	}

	public BigDecimal getImpIgM() {
		return this.impIgM;
	}

	public void setImpIgM(BigDecimal impIgM) {
		this.impIgM = impIgM;
	}

	public BigDecimal getImpIncPermMensual() {
		return this.impIncPermMensual;
	}

	public void setImpIncPermMensual(BigDecimal impIncPermMensual) {
		this.impIncPermMensual = impIncPermMensual;
	}

	public BigDecimal getImpPagoGpo() {
		return this.impPagoGpo;
	}

	public void setImpPagoGpo(BigDecimal impPagoGpo) {
		this.impPagoGpo = impPagoGpo;
	}

	public BigDecimal getImpPenGarInvA() {
		return this.impPenGarInvA;
	}

	public void setImpPenGarInvA(BigDecimal impPenGarInvA) {
		this.impPenGarInvA = impPenGarInvA;
	}

	public BigDecimal getImpPenGarInvM() {
		return this.impPenGarInvM;
	}

	public void setImpPenGarInvM(BigDecimal impPenGarInvM) {
		this.impPenGarInvM = impPenGarInvM;
	}

	public BigDecimal getImpPensionGarantizadaA() {
		return this.impPensionGarantizadaA;
	}

	public void setImpPensionGarantizadaA(BigDecimal impPensionGarantizadaA) {
		this.impPensionGarantizadaA = impPensionGarantizadaA;
	}

	public BigDecimal getImpPensionGarantizadaM() {
		return this.impPensionGarantizadaM;
	}

	public void setImpPensionGarantizadaM(BigDecimal impPensionGarantizadaM) {
		this.impPensionGarantizadaM = impPensionGarantizadaM;
	}

	public BigDecimal getImpPensionInpc() {
		return this.impPensionInpc;
	}

	public void setImpPensionInpc(BigDecimal impPensionInpc) {
		this.impPensionInpc = impPensionInpc;
	}

	public BigDecimal getImpSalarioDiario() {
		return this.impSalarioDiario;
	}

	public void setImpSalarioDiario(BigDecimal impSalarioDiario) {
		this.impSalarioDiario = impSalarioDiario;
	}

	public BigDecimal getImpSaldoIndividual() {
		return this.impSaldoIndividual;
	}

	public void setImpSaldoIndividual(BigDecimal impSaldoIndividual) {
		this.impSaldoIndividual = impSaldoIndividual;
	}

	public BigDecimal getImpSumaAsigFam() {
		return this.impSumaAsigFam;
	}

	public void setImpSumaAsigFam(BigDecimal impSumaAsigFam) {
		this.impSumaAsigFam = impSumaAsigFam;
	}

	public BigDecimal getImpSumaAsigFamA() {
		return this.impSumaAsigFamA;
	}

	public void setImpSumaAsigFamA(BigDecimal impSumaAsigFamA) {
		this.impSumaAsigFamA = impSumaAsigFamA;
	}

	public BigDecimal getImpTotalPenInvA() {
		return this.impTotalPenInvA;
	}

	public void setImpTotalPenInvA(BigDecimal impTotalPenInvA) {
		this.impTotalPenInvA = impTotalPenInvA;
	}

	public BigDecimal getImpTotalPenInvM() {
		return this.impTotalPenInvM;
	}

	public void setImpTotalPenInvM(BigDecimal impTotalPenInvM) {
		this.impTotalPenInvM = impTotalPenInvM;
	}

	public BigDecimal getImpTotalPenVeA() {
		return this.impTotalPenVeA;
	}

	public void setImpTotalPenVeA(BigDecimal impTotalPenVeA) {
		this.impTotalPenVeA = impTotalPenVeA;
	}

	public BigDecimal getImpTotalPenVeM() {
		return this.impTotalPenVeM;
	}

	public void setImpTotalPenVeM(BigDecimal impTotalPenVeM) {
		this.impTotalPenVeM = impTotalPenVeM;
	}

	public String getLugarPago() {
		return this.lugarPago;
	}

	public void setLugarPago(String lugarPago) {
		this.lugarPago = lugarPago;
	}

	public String getMesResolucion() {
		return this.mesResolucion;
	}

	public void setMesResolucion(String mesResolucion) {
		this.mesResolucion = mesResolucion;
	}

	public String getNomNombreAseg() {
		return this.nomNombreAseg;
	}

	public void setNomNombreAseg(String nomNombreAseg) {
		this.nomNombreAseg = nomNombreAseg;
	}

	public String getNomNombrePatron() {
		return this.nomNombrePatron;
	}

	public void setNomNombrePatron(String nomNombrePatron) {
		this.nomNombrePatron = nomNombrePatron;
	}

	public String getNomNombreSolicitante() {
		return this.nomNombreSolicitante;
	}

	public void setNomNombreSolicitante(String nomNombreSolicitante) {
		this.nomNombreSolicitante = nomNombreSolicitante;
	}

	public String getNumFolio() {
		return this.numFolio;
	}

	public void setNumFolio(String numFolio) {
		this.numFolio = numFolio;
	}

	public BigDecimal getNumIncCuantiaBasica() {
		return this.numIncCuantiaBasica;
	}

	public void setNumIncCuantiaBasica(BigDecimal numIncCuantiaBasica) {
		this.numIncCuantiaBasica = numIncCuantiaBasica;
	}

	public String getNumResolucion() {
		return this.numResolucion;
	}

	public void setNumResolucion(String numResolucion) {
		this.numResolucion = numResolucion;
	}

	public BigDecimal getNumSemDic90() {
		return this.numSemDic90;
	}

	public void setNumSemDic90(BigDecimal numSemDic90) {
		this.numSemDic90 = numSemDic90;
	}

	public BigDecimal getNumSemRecon() {
		return this.numSemRecon;
	}

	public void setNumSemRecon(BigDecimal numSemRecon) {
		this.numSemRecon = numSemRecon;
	}

	public BigDecimal getPorAyudaAsistencial() {
		return this.porAyudaAsistencial;
	}

	public void setPorAyudaAsistencial(BigDecimal porAyudaAsistencial) {
		this.porAyudaAsistencial = porAyudaAsistencial;
	}

	public BigDecimal getPorAyudaMensualAsist() {
		return this.porAyudaMensualAsist;
	}

	public void setPorAyudaMensualAsist(BigDecimal porAyudaMensualAsist) {
		this.porAyudaMensualAsist = porAyudaMensualAsist;
	}

	public BigDecimal getPorCesantia() {
		return this.porCesantia;
	}

	public void setPorCesantia(BigDecimal porCesantia) {
		this.porCesantia = porCesantia;
	}

	public BigDecimal getPorValuacion() {
		return this.porValuacion;
	}

	public void setPorValuacion(BigDecimal porValuacion) {
		this.porValuacion = porValuacion;
	}

	public String getResponsableFirmaResol() {
		return this.responsableFirmaResol;
	}

	public void setResponsableFirmaResol(String responsableFirmaResol) {
		this.responsableFirmaResol = responsableFirmaResol;
	}

	public List<AptComponentesImpresion> getAptComponentesImpresions() {
		return this.aptComponentesImpresions;
	}

	public void setAptComponentesImpresions(List<AptComponentesImpresion> aptComponentesImpresions) {
		this.aptComponentesImpresions = aptComponentesImpresions;
	}

	public AptComponentesImpresion addAptComponentesImpresion(AptComponentesImpresion aptComponentesImpresion) {
		getAptComponentesImpresions().add(aptComponentesImpresion);
		aptComponentesImpresion.setAptDatosImpresion(this);

		return aptComponentesImpresion;
	}

	public AptComponentesImpresion removeAptComponentesImpresion(AptComponentesImpresion aptComponentesImpresion) {
		getAptComponentesImpresions().remove(aptComponentesImpresion);
		aptComponentesImpresion.setAptDatosImpresion(null);

		return aptComponentesImpresion;
	}

	public SpcRegimen getSpcRegimen() {
		return this.spcRegimen;
	}

	public void setSpcRegimen(SpcRegimen spcRegimen) {
		this.spcRegimen = spcRegimen;
	}

	public SpcTipoPension getSpcTipoPension() {
		return this.spcTipoPension;
	}

	public void setSpcTipoPension(SpcTipoPension spcTipoPension) {
		this.spcTipoPension = spcTipoPension;
	}

	public List<AptResolucionImpresion> getAptResolucionImpresions() {
		return this.aptResolucionImpresions;
	}

	public void setAptResolucionImpresions(List<AptResolucionImpresion> aptResolucionImpresions) {
		this.aptResolucionImpresions = aptResolucionImpresions;
	}

	public AptResolucionImpresion addAptResolucionImpresion(AptResolucionImpresion aptResolucionImpresion) {
		getAptResolucionImpresions().add(aptResolucionImpresion);
		aptResolucionImpresion.setAptDatosImpresion(this);

		return aptResolucionImpresion;
	}

	public AptResolucionImpresion removeAptResolucionImpresion(AptResolucionImpresion aptResolucionImpresion) {
		getAptResolucionImpresions().remove(aptResolucionImpresion);
		aptResolucionImpresion.setAptDatosImpresion(null);

		return aptResolucionImpresion;
	}

}