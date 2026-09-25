package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CGT_GESTIONSINADI database table.
 * 
 */
@Entity
@Table(name="CGT_GESTIONSINADI")
public class CgtGestionsinadi implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CgtGestionsinadiPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_CONPRORROGA")
	private Date fhConprorroga;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_FECHA_CARGA")
	private Date fhFechaCarga;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_NOTIFICACION")
	private Date fhNotificacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PAGO")
	private Date fhPago;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODOA")
	private Date fhPeriodoa;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODODE")
	private Date fhPeriodode;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PETICIONORDENVISITA")
	private Date fhPeticionordenvisita;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PRESENTADICTAMEN")
	private Date fhPresentadictamen;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RECEPCION")
	private Date fhRecepcion;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RECHAZO")
	private Date fhRechazo;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_SINPRORROGA")
	private Date fhSinprorroga;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_SUSTITUCIONCP")
	private Date fhSustitucioncp;

	@Column(name="IM_ACTUALIZACION", precision=12, scale=2)
	private BigDecimal imActualizacion;

	@Column(name="IM_MULTASPAGADAS", precision=12, scale=2)
	private BigDecimal imMultaspagadas;

	@Column(name="IM_RCVPAGADO", precision=12, scale=2)
	private BigDecimal imRcvpagado;

	@Column(name="IM_RECARGOS", precision=12, scale=2)
	private BigDecimal imRecargos;

	@Column(name="IM_RECLASIFICACION", precision=12, scale=2)
	private BigDecimal imReclasificacion;

	@Column(name="IM_SUERTEPRIN", precision=12, scale=2)
	private BigDecimal imSuerteprin;

	@Column(name="IM_TOTAL", precision=12, scale=2)
	private BigDecimal imTotal;

	@Column(name="NU_AVIBAJAPRESENTA", precision=22)
	private BigDecimal nuAvibajapresenta;

	@Column(name="NU_AVIDESCCORRESP1ER", precision=22)
	private BigDecimal nuAvidesccorresp1er;

	@Column(name="NU_AVIINSCBAJAS", precision=22)
	private BigDecimal nuAviinscbajas;

	@Column(name="NU_AVIMODIFSALARIO", precision=22)
	private BigDecimal nuAvimodifsalario;

	@Column(name="NU_AVIMODIFSALDESC", precision=22)
	private BigDecimal nuAvimodifsaldesc;

	@Column(name="NU_AVIRECTIFFECHAPOST", precision=22)
	private BigDecimal nuAvirectiffechapost;

	@Column(name="NU_AVITRABNOINSC", precision=22)
	private BigDecimal nuAvitrabnoinsc;

	@Column(name="NU_DICTAPENDIENTES", precision=22)
	private BigDecimal nuDictapendientes;

	@Column(name="NU_EJERPERIODO", length=50)
	private String nuEjerperiodo;

	@Column(name="NU_NUMPARCIA", precision=22)
	private BigDecimal nuNumparcia;

	@Column(name="NU_NUMPARCIADE", precision=22)
	private BigDecimal nuNumparciade;

	@Column(name="NU_OBLIGAT_EXTP", precision=22)
	private BigDecimal nuObligatExtp;

	@Column(name="NU_PRESENT_EXTP", precision=22)
	private BigDecimal nuPresentExtp;

	@Column(name="NU_RECHAZO", precision=22)
	private BigDecimal nuRechazo;

	@Column(name="NU_REG_CP", precision=9)
	private BigDecimal nuRegCp;

	@Column(name="NU_REG_CP_SUST", precision=9)
	private BigDecimal nuRegCpSust;

	@Column(name="NU_REGPATRONALES", precision=22)
	private BigDecimal nuRegpatronales;

	@Column(name="NU_STATUSDERIVACION", length=18)
	private String nuStatusderivacion;

	@Column(name="NU_TRABREGULA", precision=22)
	private BigDecimal nuTrabregula;

	@Column(name="NU_TRABREVISADOS", precision=22)
	private BigDecimal nuTrabrevisados;

	@Column(name="TX_AVISODERIVADOOTRASUB", length=200)
	private String txAvisoderivadootrasub;

	@Column(name="TX_AVISORECIBIDOOTRASUB", length=200)
	private String txAvisorecibidootrasub;

	@Column(name="TX_FOLIOANTECED", length=50)
	private String txFolioanteced;

	@Column(name="TX_FOLIOCMAA", length=15)
	private String txFoliocmaa;

	@Column(name="TX_FOLIOSUA", length=250)
	private String txFoliosua;

	@Column(name="TX_NOMRAZONSOCIAL", length=200)
	private String txNomrazonsocial;

	@Column(name="TX_NUMAVISO1", length=10)
	private String txNumaviso1;

	@Column(name="TX_NUMAVISO2", length=10)
	private String txNumaviso2;

	@Column(name="TX_OBSERVACIONES", length=250)
	private String txObservaciones;

	@Column(name="TX_OBSERVACIONESINGRESOS", length=250)
	private String txObservacionesingresos;

	@Column(name="TX_OBSERVACIONESMULTAS", length=250)
	private String txObservacionesmultas;

	@Column(name="TX_OBSERVACIONESRCV", length=250)
	private String txObservacionesrcv;

	@Column(name="TX_REGPATRONAL", length=11)
	private String txRegpatronal;

	@Column(name="TX_RFCPATRON", length=13)
	private String txRfcpatron;

	//bi-directional one-to-one association to CgtCoredisinadi
	@OneToOne(mappedBy="cgtGestionsinadi", fetch=FetchType.LAZY)
	private CgtCoredisinadi cgtCoredisinadi;

	//bi-directional many-to-one association to CgtGestionauxregpat
	@OneToMany(mappedBy="cgtGestionsinadi")
	private List<CgtGestionauxregpat> cgtGestionauxregpats;

	//bi-directional many-to-one association to FdtSubdeleg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="SDELEG_ORIG", referencedColumnName="SDELEG_ORIG", nullable=false, insertable=false, updatable=false)
		})
	private FdtSubdeleg fdtSubdeleg;

	//bi-directional many-to-one association to CgcAviso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_AVISO")
	private CgcAviso cgcAviso;

	//bi-directional many-to-one association to CgcTipoaviso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_TPOAVISO")
	private CgcTipoaviso cgcTipoaviso;

	//bi-directional many-to-one association to CgcAntecedente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ANTECEDENTE")
	private CgcAntecedente cgcAntecedente;

	//bi-directional many-to-one association to CgcRechazo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_RECHAZO")
	private CgcRechazo cgcRechazo;

	//bi-directional many-to-one association to CgcRegistradoen
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_REGISTRADOEN")
	private CgcRegistradoen cgcRegistradoen;

	//bi-directional many-to-one association to FdtPatron
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL"),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON")
		})
	private FdtPatron fdtPatron;

	//bi-directional many-to-one association to CgcProrroga
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_PRORROGA")
	private CgcProrroga cgcProrroga;

	//bi-directional many-to-one association to CgcDictarecibido
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_DICTARECIBIDOS")
	private CgcDictarecibido cgcDictarecibido;

	//bi-directional many-to-one association to CgcMopinion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_MOPINION")
	private CgcMopinion cgcMopinion;

	//bi-directional many-to-one association to CgcFormapago
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_FORMAPAGO")
	private CgcFormapago cgcFormapago;

	//bi-directional many-to-one association to CgcActividad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ACTIVIDAD")
	private CgcActividad cgcActividad;

	//bi-directional many-to-one association to CgcSustitucion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SUSTITUCION")
	private CgcSustitucion cgcSustitucion;

    public CgtGestionsinadi() {
    }

	public CgtGestionsinadiPK getId() {
		return this.id;
	}

	public void setId(CgtGestionsinadiPK id) {
		this.id = id;
	}
	
	public Date getFhConprorroga() {
		return this.fhConprorroga;
	}

	public void setFhConprorroga(Date fhConprorroga) {
		this.fhConprorroga = fhConprorroga;
	}

	public Date getFhFechaCarga() {
		return this.fhFechaCarga;
	}

	public void setFhFechaCarga(Date fhFechaCarga) {
		this.fhFechaCarga = fhFechaCarga;
	}

	public Date getFhNotificacion() {
		return this.fhNotificacion;
	}

	public void setFhNotificacion(Date fhNotificacion) {
		this.fhNotificacion = fhNotificacion;
	}

	public Date getFhPago() {
		return this.fhPago;
	}

	public void setFhPago(Date fhPago) {
		this.fhPago = fhPago;
	}

	public Date getFhPeriodoa() {
		return this.fhPeriodoa;
	}

	public void setFhPeriodoa(Date fhPeriodoa) {
		this.fhPeriodoa = fhPeriodoa;
	}

	public Date getFhPeriodode() {
		return this.fhPeriodode;
	}

	public void setFhPeriodode(Date fhPeriodode) {
		this.fhPeriodode = fhPeriodode;
	}

	public Date getFhPeticionordenvisita() {
		return this.fhPeticionordenvisita;
	}

	public void setFhPeticionordenvisita(Date fhPeticionordenvisita) {
		this.fhPeticionordenvisita = fhPeticionordenvisita;
	}

	public Date getFhPresentadictamen() {
		return this.fhPresentadictamen;
	}

	public void setFhPresentadictamen(Date fhPresentadictamen) {
		this.fhPresentadictamen = fhPresentadictamen;
	}

	public Date getFhRecepcion() {
		return this.fhRecepcion;
	}

	public void setFhRecepcion(Date fhRecepcion) {
		this.fhRecepcion = fhRecepcion;
	}

	public Date getFhRechazo() {
		return this.fhRechazo;
	}

	public void setFhRechazo(Date fhRechazo) {
		this.fhRechazo = fhRechazo;
	}

	public Date getFhSinprorroga() {
		return this.fhSinprorroga;
	}

	public void setFhSinprorroga(Date fhSinprorroga) {
		this.fhSinprorroga = fhSinprorroga;
	}

	public Date getFhSustitucioncp() {
		return this.fhSustitucioncp;
	}

	public void setFhSustitucioncp(Date fhSustitucioncp) {
		this.fhSustitucioncp = fhSustitucioncp;
	}

	public BigDecimal getImActualizacion() {
		return this.imActualizacion;
	}

	public void setImActualizacion(BigDecimal imActualizacion) {
		this.imActualizacion = imActualizacion;
	}

	public BigDecimal getImMultaspagadas() {
		return this.imMultaspagadas;
	}

	public void setImMultaspagadas(BigDecimal imMultaspagadas) {
		this.imMultaspagadas = imMultaspagadas;
	}

	public BigDecimal getImRcvpagado() {
		return this.imRcvpagado;
	}

	public void setImRcvpagado(BigDecimal imRcvpagado) {
		this.imRcvpagado = imRcvpagado;
	}

	public BigDecimal getImRecargos() {
		return this.imRecargos;
	}

	public void setImRecargos(BigDecimal imRecargos) {
		this.imRecargos = imRecargos;
	}

	public BigDecimal getImReclasificacion() {
		return this.imReclasificacion;
	}

	public void setImReclasificacion(BigDecimal imReclasificacion) {
		this.imReclasificacion = imReclasificacion;
	}

	public BigDecimal getImSuerteprin() {
		return this.imSuerteprin;
	}

	public void setImSuerteprin(BigDecimal imSuerteprin) {
		this.imSuerteprin = imSuerteprin;
	}

	public BigDecimal getImTotal() {
		return this.imTotal;
	}

	public void setImTotal(BigDecimal imTotal) {
		this.imTotal = imTotal;
	}

	public BigDecimal getNuAvibajapresenta() {
		return this.nuAvibajapresenta;
	}

	public void setNuAvibajapresenta(BigDecimal nuAvibajapresenta) {
		this.nuAvibajapresenta = nuAvibajapresenta;
	}

	public BigDecimal getNuAvidesccorresp1er() {
		return this.nuAvidesccorresp1er;
	}

	public void setNuAvidesccorresp1er(BigDecimal nuAvidesccorresp1er) {
		this.nuAvidesccorresp1er = nuAvidesccorresp1er;
	}

	public BigDecimal getNuAviinscbajas() {
		return this.nuAviinscbajas;
	}

	public void setNuAviinscbajas(BigDecimal nuAviinscbajas) {
		this.nuAviinscbajas = nuAviinscbajas;
	}

	public BigDecimal getNuAvimodifsalario() {
		return this.nuAvimodifsalario;
	}

	public void setNuAvimodifsalario(BigDecimal nuAvimodifsalario) {
		this.nuAvimodifsalario = nuAvimodifsalario;
	}

	public BigDecimal getNuAvimodifsaldesc() {
		return this.nuAvimodifsaldesc;
	}

	public void setNuAvimodifsaldesc(BigDecimal nuAvimodifsaldesc) {
		this.nuAvimodifsaldesc = nuAvimodifsaldesc;
	}

	public BigDecimal getNuAvirectiffechapost() {
		return this.nuAvirectiffechapost;
	}

	public void setNuAvirectiffechapost(BigDecimal nuAvirectiffechapost) {
		this.nuAvirectiffechapost = nuAvirectiffechapost;
	}

	public BigDecimal getNuAvitrabnoinsc() {
		return this.nuAvitrabnoinsc;
	}

	public void setNuAvitrabnoinsc(BigDecimal nuAvitrabnoinsc) {
		this.nuAvitrabnoinsc = nuAvitrabnoinsc;
	}

	public BigDecimal getNuDictapendientes() {
		return this.nuDictapendientes;
	}

	public void setNuDictapendientes(BigDecimal nuDictapendientes) {
		this.nuDictapendientes = nuDictapendientes;
	}

	public String getNuEjerperiodo() {
		return this.nuEjerperiodo;
	}

	public void setNuEjerperiodo(String nuEjerperiodo) {
		this.nuEjerperiodo = nuEjerperiodo;
	}

	public BigDecimal getNuNumparcia() {
		return this.nuNumparcia;
	}

	public void setNuNumparcia(BigDecimal nuNumparcia) {
		this.nuNumparcia = nuNumparcia;
	}

	public BigDecimal getNuNumparciade() {
		return this.nuNumparciade;
	}

	public void setNuNumparciade(BigDecimal nuNumparciade) {
		this.nuNumparciade = nuNumparciade;
	}

	public BigDecimal getNuObligatExtp() {
		return this.nuObligatExtp;
	}

	public void setNuObligatExtp(BigDecimal nuObligatExtp) {
		this.nuObligatExtp = nuObligatExtp;
	}

	public BigDecimal getNuPresentExtp() {
		return this.nuPresentExtp;
	}

	public void setNuPresentExtp(BigDecimal nuPresentExtp) {
		this.nuPresentExtp = nuPresentExtp;
	}

	public BigDecimal getNuRechazo() {
		return this.nuRechazo;
	}

	public void setNuRechazo(BigDecimal nuRechazo) {
		this.nuRechazo = nuRechazo;
	}

	public BigDecimal getNuRegCp() {
		return this.nuRegCp;
	}

	public void setNuRegCp(BigDecimal nuRegCp) {
		this.nuRegCp = nuRegCp;
	}

	public BigDecimal getNuRegCpSust() {
		return this.nuRegCpSust;
	}

	public void setNuRegCpSust(BigDecimal nuRegCpSust) {
		this.nuRegCpSust = nuRegCpSust;
	}

	public BigDecimal getNuRegpatronales() {
		return this.nuRegpatronales;
	}

	public void setNuRegpatronales(BigDecimal nuRegpatronales) {
		this.nuRegpatronales = nuRegpatronales;
	}

	public String getNuStatusderivacion() {
		return this.nuStatusderivacion;
	}

	public void setNuStatusderivacion(String nuStatusderivacion) {
		this.nuStatusderivacion = nuStatusderivacion;
	}

	public BigDecimal getNuTrabregula() {
		return this.nuTrabregula;
	}

	public void setNuTrabregula(BigDecimal nuTrabregula) {
		this.nuTrabregula = nuTrabregula;
	}

	public BigDecimal getNuTrabrevisados() {
		return this.nuTrabrevisados;
	}

	public void setNuTrabrevisados(BigDecimal nuTrabrevisados) {
		this.nuTrabrevisados = nuTrabrevisados;
	}

	public String getTxAvisoderivadootrasub() {
		return this.txAvisoderivadootrasub;
	}

	public void setTxAvisoderivadootrasub(String txAvisoderivadootrasub) {
		this.txAvisoderivadootrasub = txAvisoderivadootrasub;
	}

	public String getTxAvisorecibidootrasub() {
		return this.txAvisorecibidootrasub;
	}

	public void setTxAvisorecibidootrasub(String txAvisorecibidootrasub) {
		this.txAvisorecibidootrasub = txAvisorecibidootrasub;
	}

	public String getTxFolioanteced() {
		return this.txFolioanteced;
	}

	public void setTxFolioanteced(String txFolioanteced) {
		this.txFolioanteced = txFolioanteced;
	}

	public String getTxFoliocmaa() {
		return this.txFoliocmaa;
	}

	public void setTxFoliocmaa(String txFoliocmaa) {
		this.txFoliocmaa = txFoliocmaa;
	}

	public String getTxFoliosua() {
		return this.txFoliosua;
	}

	public void setTxFoliosua(String txFoliosua) {
		this.txFoliosua = txFoliosua;
	}

	public String getTxNomrazonsocial() {
		return this.txNomrazonsocial;
	}

	public void setTxNomrazonsocial(String txNomrazonsocial) {
		this.txNomrazonsocial = txNomrazonsocial;
	}

	public String getTxNumaviso1() {
		return this.txNumaviso1;
	}

	public void setTxNumaviso1(String txNumaviso1) {
		this.txNumaviso1 = txNumaviso1;
	}

	public String getTxNumaviso2() {
		return this.txNumaviso2;
	}

	public void setTxNumaviso2(String txNumaviso2) {
		this.txNumaviso2 = txNumaviso2;
	}

	public String getTxObservaciones() {
		return this.txObservaciones;
	}

	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}

	public String getTxObservacionesingresos() {
		return this.txObservacionesingresos;
	}

	public void setTxObservacionesingresos(String txObservacionesingresos) {
		this.txObservacionesingresos = txObservacionesingresos;
	}

	public String getTxObservacionesmultas() {
		return this.txObservacionesmultas;
	}

	public void setTxObservacionesmultas(String txObservacionesmultas) {
		this.txObservacionesmultas = txObservacionesmultas;
	}

	public String getTxObservacionesrcv() {
		return this.txObservacionesrcv;
	}

	public void setTxObservacionesrcv(String txObservacionesrcv) {
		this.txObservacionesrcv = txObservacionesrcv;
	}

	public String getTxRegpatronal() {
		return this.txRegpatronal;
	}

	public void setTxRegpatronal(String txRegpatronal) {
		this.txRegpatronal = txRegpatronal;
	}

	public String getTxRfcpatron() {
		return this.txRfcpatron;
	}

	public void setTxRfcpatron(String txRfcpatron) {
		this.txRfcpatron = txRfcpatron;
	}

	public CgtCoredisinadi getCgtCoredisinadi() {
		return this.cgtCoredisinadi;
	}

	public void setCgtCoredisinadi(CgtCoredisinadi cgtCoredisinadi) {
		this.cgtCoredisinadi = cgtCoredisinadi;
	}
	
	public List<CgtGestionauxregpat> getCgtGestionauxregpats() {
		return this.cgtGestionauxregpats;
	}

	public void setCgtGestionauxregpats(List<CgtGestionauxregpat> cgtGestionauxregpats) {
		this.cgtGestionauxregpats = cgtGestionauxregpats;
	}
	
	public FdtSubdeleg getFdtSubdeleg() {
		return this.fdtSubdeleg;
	}

	public void setFdtSubdeleg(FdtSubdeleg fdtSubdeleg) {
		this.fdtSubdeleg = fdtSubdeleg;
	}
	
	public CgcAviso getCgcAviso() {
		return this.cgcAviso;
	}

	public void setCgcAviso(CgcAviso cgcAviso) {
		this.cgcAviso = cgcAviso;
	}
	
	public CgcTipoaviso getCgcTipoaviso() {
		return this.cgcTipoaviso;
	}

	public void setCgcTipoaviso(CgcTipoaviso cgcTipoaviso) {
		this.cgcTipoaviso = cgcTipoaviso;
	}
	
	public CgcAntecedente getCgcAntecedente() {
		return this.cgcAntecedente;
	}

	public void setCgcAntecedente(CgcAntecedente cgcAntecedente) {
		this.cgcAntecedente = cgcAntecedente;
	}
	
	public CgcRechazo getCgcRechazo() {
		return this.cgcRechazo;
	}

	public void setCgcRechazo(CgcRechazo cgcRechazo) {
		this.cgcRechazo = cgcRechazo;
	}
	
	public CgcRegistradoen getCgcRegistradoen() {
		return this.cgcRegistradoen;
	}

	public void setCgcRegistradoen(CgcRegistradoen cgcRegistradoen) {
		this.cgcRegistradoen = cgcRegistradoen;
	}
	
	public FdtPatron getFdtPatron() {
		return this.fdtPatron;
	}

	public void setFdtPatron(FdtPatron fdtPatron) {
		this.fdtPatron = fdtPatron;
	}
	
	public CgcProrroga getCgcProrroga() {
		return this.cgcProrroga;
	}

	public void setCgcProrroga(CgcProrroga cgcProrroga) {
		this.cgcProrroga = cgcProrroga;
	}
	
	public CgcDictarecibido getCgcDictarecibido() {
		return this.cgcDictarecibido;
	}

	public void setCgcDictarecibido(CgcDictarecibido cgcDictarecibido) {
		this.cgcDictarecibido = cgcDictarecibido;
	}
	
	public CgcMopinion getCgcMopinion() {
		return this.cgcMopinion;
	}

	public void setCgcMopinion(CgcMopinion cgcMopinion) {
		this.cgcMopinion = cgcMopinion;
	}
	
	public CgcFormapago getCgcFormapago() {
		return this.cgcFormapago;
	}

	public void setCgcFormapago(CgcFormapago cgcFormapago) {
		this.cgcFormapago = cgcFormapago;
	}
	
	public CgcActividad getCgcActividad() {
		return this.cgcActividad;
	}

	public void setCgcActividad(CgcActividad cgcActividad) {
		this.cgcActividad = cgcActividad;
	}
	
	public CgcSustitucion getCgcSustitucion() {
		return this.cgcSustitucion;
	}

	public void setCgcSustitucion(CgcSustitucion cgcSustitucion) {
		this.cgcSustitucion = cgcSustitucion;
	}
	
}