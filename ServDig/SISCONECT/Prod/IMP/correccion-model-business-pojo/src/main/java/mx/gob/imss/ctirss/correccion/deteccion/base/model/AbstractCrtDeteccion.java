package mx.gob.imss.ctirss.correccion.deteccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the CRT_DETECCION database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCrtDeteccion extends AbstractModel{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CVE_DETECCION_GENERATOR", sequenceName="SEQ_CVE_DETECCION")
	@GeneratedValue(generator="CVE_DETECCION_GENERATOR")
	@Column(name="CVE_DETECCION")
	private Long cveDeteccion;

	@Column(name="CAN_SUPERFICIE")
	private BigDecimal canSuperficie;

	@Column(name="DES_DEPENDENCIAPUB")
	private String desDependenciapub;

	@Column(name="CVE_TIPOCORR")
	private Integer cveTipocorr;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

	@Column(name="DES_DEPCONTRATANTE")
	private String desDepcontratante;

//	@Column(name="DOM_CALLE")
//	private String domCalle;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHADETECCION_FC")
	private Date fecFechadeteccionFc;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAINICIO_EST")
	private Date fecFechainicioEst;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHATERMINO_EST")
	private Date fecFechaterminoEst;
    
    

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHACANCELA")
	private Date fecFechaCancela;
    

	@Column(name="ID_PROMOVIDO")
	private BigDecimal idPromovido;

	@Column(name="IMP_COSTOOBRA")
	private BigDecimal impCostoobra;

	@Column(name="NOM_RAZONSOCIAL")
	private String nomRazonsocial;

	@Column(name="NU_FOLIODETECCION")
	private String nuFoliodeteccion;

	@Column(name="NU_REPORTECTRLOBRA")
	private String nuReportectrlobra;

//	@Column(name="NUM_CODIGOPOSTAL")
//	private String numCodigopostal;
//
//	@Column(name="NUM_NROEXT")
//	private String numNroext;
//
//	@Column(name="NUM_NROINT")
//	private String numNroint;

	@Column(name="POR_AVANCEOBRA_EST")
	private BigDecimal porAvanceobraEst;

//	@Column(name="REF_COLONIA")
//	private String refColonia;
	
	@Column(name="CVE_FK_PATRON")
	private Long cveFkPatron;

	@Column(name="CVE_FK_SUBDELEGACION")
	private BigDecimal sdelegOrig;

	@Column(name="TIP_CLASEOBRA")
	private String tipClaseobra;

	@Column(name="TX_CURPPATRON")
	private String txCurppatron;

	@Column(name="TX_EMAIL")
	private String txEmail;

	@Column(name="TX_RFCPATRON")
	private String txRfcpatron;

	@Column(name="TX_TELEFONO")
	private String txTelefono;
	
	@Column(name="CVE_FK_TIPOOBRA")
	private Integer cvePkTipObra;
	
	@Column(name="CVE_FK_FASECONSTRUCCION")
	private Integer cvePkFaseConst;
	
	@Column(name="CVE_FK_ZONA")
	private Integer cveFkZona;
	
	@Column(name="NUM_TRABAJADORES")
	private Integer numTrabajdores;
	
	@Column(name="CVE_DOMGEO_OBRA")
	private Integer domicilioId;
	
	@Column(name="ID_MOTIVOCANCELACION")
	private Integer idMotivocancelacion;
	
	@Column(name="ID_OBLIGADO")
	private Integer idObligado;
	
	@Column(name="CVE_SELECTOR")
	private Integer cveSelector;
	
	@Column(name="CVE_FK_CENSOR")
	private Integer cveCensor;
	
	@Column(name="TX_ACTIVIDAD")
	private String txActividad;
	
    @Column(name="NUM_CODIGOPOSTAL")
	private String numeroDeCodigoPostal;
	
	
	public String getTxActividad() {
		return txActividad;
	}

	public void setTxActividad(String txActividad) {
		this.txActividad = txActividad;
	}

	@Transient
	private String origen;
	
	@Transient
	private String claseObra;
	
	@Transient
	private String faseObra;
	
	@Transient
	private String tipoObra;
	
	@Transient
	private String regPatron;
	
	@Transient
	private String numRegObra;
	
	@Transient
	private String cveNrocontrato;
	

    public AbstractCrtDeteccion() {
    }

	public AbstractCrtDeteccion(Long cveDeteccion, Integer cveTipocorr,
			String nuFoliodeteccion, Date fecFechadeteccionFc, String nuReportectrlobra,
			String nomRazonsocial, String txCurppatron, String txRfcpatron, 
			String domCalle, String numNroext, String numNroint,
			String refColonia, String numCodigopostal, BigDecimal sdelegOrig,
			String tipClaseobra, Integer cvePkTipObra, Integer cvePkFaseConst,
			String desDependenciapub, String desDepcontratante, BigDecimal canSuperficie,
			BigDecimal impCostoobra, Date fecFechainicioEst, Date fecFechaterminoEst,
			BigDecimal porAvanceobraEst, String txTelefono, String txEmail,
			BigDecimal idPromovido, Date fecFechareg, String cveUsuario,
			Integer domicilioId, Long cveFkPatron, Integer idMotivo 
			) {
		this.cveDeteccion = cveDeteccion;
		this.canSuperficie = canSuperficie;
		this.desDependenciapub = desDependenciapub;
		this.cveTipocorr = cveTipocorr;
		this.cveUsuario = cveUsuario;
		this.desDepcontratante = desDepcontratante;
//		this.domCalle = domCalle;
		this.fecFechadeteccionFc = fecFechadeteccionFc;
		this.fecFechainicioEst = fecFechainicioEst;
		this.fecFechareg = fecFechareg;
		this.fecFechaterminoEst = fecFechaterminoEst;
		this.idPromovido = idPromovido;
		this.impCostoobra = impCostoobra;
		this.nomRazonsocial = nomRazonsocial;
		this.nuFoliodeteccion = nuFoliodeteccion;
		this.nuReportectrlobra = nuReportectrlobra;
//		this.numCodigopostal = numCodigopostal;
//		this.numNroext = numNroext;
//		this.numNroint = numNroint;
		this.porAvanceobraEst = porAvanceobraEst;
//		this.refColonia = refColonia;
		this.sdelegOrig = sdelegOrig;
		this.tipClaseobra = tipClaseobra;
		this.txCurppatron = txCurppatron;
		this.txEmail = txEmail;
		this.txRfcpatron = txRfcpatron;
		this.txTelefono = txTelefono;
		this.cvePkTipObra = cvePkTipObra;
		this.cvePkFaseConst = cvePkFaseConst;
//		this.cveFkPatron = cveFkPatron;
		this.domicilioId = domicilioId;
		this.cveFkPatron = cveFkPatron;
		this.idMotivocancelacion = idMotivo;
	}

	public AbstractCrtDeteccion(String regPatron, String nomRazonsocial,
			long numRegObra, String domCalle, String numNroint,
			String numNroext, String refColonia, String numCodigopostal,
			String tipClaseobra, Long cvePK, BigDecimal canSuperficie, 
			BigDecimal impCostoobra, Date fecFechainicioEst, Date fecFechaterminoEst
			) {
		super();
		this.canSuperficie = canSuperficie;
//		this.domCalle = domCalle;
		this.fecFechainicioEst = fecFechainicioEst;
		this.fecFechaterminoEst = fecFechaterminoEst;
		this.impCostoobra = impCostoobra;
		this.nomRazonsocial = nomRazonsocial;
//		this.numCodigopostal = numCodigopostal;
//		this.numNroext = numNroext;
//		this.numNroint = numNroint;
//		this.refColonia = refColonia;
		this.tipClaseobra = tipClaseobra;
		this.cveFkPatron = cvePK;
		this.regPatron = regPatron;
		this.numRegObra = (new BigDecimal(numRegObra)).toString();
	}
	
	public AbstractCrtDeteccion(String regPatron, String nomRazonsocial,
			long numRegObra, String domCalle, String numNroint,
			String numNroext, String refColonia, String numCodigopostal,
			String tipClaseobra, Long cvePK, BigDecimal canSuperficie, 
			BigDecimal impCostoobra, Date fecFechainicioEst, Date fecFechaterminoEst,
			String cveNrocontrato
			) {
		super();
		this.canSuperficie = canSuperficie;
//		this.domCalle = domCalle;
		this.fecFechainicioEst = fecFechainicioEst;
		this.fecFechaterminoEst = fecFechaterminoEst;
		this.impCostoobra = impCostoobra;
		this.nomRazonsocial = nomRazonsocial;
//		this.numCodigopostal = numCodigopostal;
//		this.numNroext = numNroext;
//		this.numNroint = numNroint;
//		this.refColonia = refColonia;
		this.tipClaseobra = tipClaseobra;
		this.cveFkPatron = cvePK;
		this.regPatron = regPatron;
		this.numRegObra = (new BigDecimal(numRegObra)).toString();
		this.cveNrocontrato = cveNrocontrato;
	}
	
	public AbstractCrtDeteccion(Long cveDeteccion, Integer cveTipocorr,
			String nuFoliodeteccion, Date fecFechadeteccionFc,
			String nomRazonsocial, BigDecimal sdelegOrig,
			String desDependenciapub, String desDepcontratante,
			Date fecFechainicioEst, Date fecFechaterminoEst,
			BigDecimal idPromovido, Date fecFechareg, String cveUsuario,
			Integer domicilioId, Long cveFkPatron) {
		this.cveDeteccion = cveDeteccion;
		this.cveTipocorr = cveTipocorr;
		this.nuFoliodeteccion = nuFoliodeteccion;
		this.fecFechadeteccionFc = fecFechadeteccionFc;
		this.nomRazonsocial = nomRazonsocial;
		this.sdelegOrig = sdelegOrig;
		this.desDependenciapub = desDependenciapub;
		this.desDepcontratante = desDepcontratante;
		this.fecFechainicioEst = fecFechainicioEst;
		this.fecFechaterminoEst = fecFechaterminoEst;
		this.idPromovido = idPromovido;
		this.fecFechareg = fecFechareg;
		this.cveUsuario = cveUsuario;
		this.domicilioId = domicilioId;
		this.cveFkPatron = cveFkPatron;
		
	}
	
	

	public AbstractCrtDeteccion(Long cveDeteccion) {
		this.cveDeteccion = cveDeteccion;
	}

	public Long getCveDeteccion() {
		return this.cveDeteccion;
	}

	public void setCveDeteccion(Long cveDeteccion) {
		this.cveDeteccion = cveDeteccion;
	}

	public BigDecimal getCanSuperficie() {
		return this.canSuperficie;
	}

	public void setCanSuperficie(BigDecimal canSuperficie) {
		this.canSuperficie = canSuperficie;
	}

	public String getDesDependenciapub() {
		return this.desDependenciapub;
	}

	public void setDesDependenciapub(String desDependenciapub) {
		this.desDependenciapub = desDependenciapub;
	}

	public Integer getCveTipocorr() {
		return this.cveTipocorr;
	}

	public void setCveTipocorr(Integer cveTipocorr) {
		this.cveTipocorr = cveTipocorr;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public String getDesDepcontratante() {
		return this.desDepcontratante;
	}

	public void setDesDepcontratante(String desDepcontratante) {
		this.desDepcontratante = desDepcontratante;
	}

//	public String getDomCalle() {
//		return this.domCalle;
//	}
//
//	public void setDomCalle(String domCalle) {
//		this.domCalle = domCalle;
//	}

	public Date getFecFechadeteccionFc() {
		return this.fecFechadeteccionFc;
	}

	public void setFecFechadeteccionFc(Date fecFechadeteccionFc) {
		this.fecFechadeteccionFc = fecFechadeteccionFc;
	}

	public Date getFecFechainicioEst() {
		return this.fecFechainicioEst;
	}

	public void setFecFechainicioEst(Date fecFechainicioEst) {
		this.fecFechainicioEst = fecFechainicioEst;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public Date getFecFechaterminoEst() {
		return this.fecFechaterminoEst;
	}

	public void setFecFechaterminoEst(Date fecFechaterminoEst) {
		this.fecFechaterminoEst = fecFechaterminoEst;
	}

	public BigDecimal getIdPromovido() {
		return this.idPromovido;
	}

	public void setIdPromovido(BigDecimal idPromovido) {
		this.idPromovido = idPromovido;
	}

	public BigDecimal getImpCostoobra() {
		return this.impCostoobra;
	}

	public void setImpCostoobra(BigDecimal impCostoobra) {
		this.impCostoobra = impCostoobra;
	}

	public String getNomRazonsocial() {
		return this.nomRazonsocial;
	}

	public void setNomRazonsocial(String nomRazonsocial) {
		this.nomRazonsocial = nomRazonsocial;
	}

	public String getNuFoliodeteccion() {
		return this.nuFoliodeteccion;
	}

	public void setNuFoliodeteccion(String nuFoliodeteccion) {
		this.nuFoliodeteccion = nuFoliodeteccion;
	}

	public String getNuReportectrlobra() {
		return this.nuReportectrlobra;
	}

	public void setNuReportectrlobra(String nuReportectrlobra) {
		this.nuReportectrlobra = nuReportectrlobra;
	}

//	public String getNumCodigopostal() {
//		return this.numCodigopostal;
//	}
//
//	public void setNumCodigopostal(String numCodigopostal) {
//		this.numCodigopostal = numCodigopostal;
//	}
//
//	public String getNumNroext() {
//		return this.numNroext;
//	}
//
//	public void setNumNroext(String numNroext) {
//		this.numNroext = numNroext;
//	}
//
//	public String getNumNroint() {
//		return this.numNroint;
//	}
//
//	public void setNumNroint(String numNroint) {
//		this.numNroint = numNroint;
//	}

	public BigDecimal getPorAvanceobraEst() {
		return this.porAvanceobraEst;
	}

	public void setPorAvanceobraEst(BigDecimal porAvanceobraEst) {
		this.porAvanceobraEst = porAvanceobraEst;
	}

//	public String getRefColonia() {
//		return this.refColonia;
//	}
//
//	public void setRefColonia(String refColonia) {
//		this.refColonia = refColonia;
//	}

	public BigDecimal getSdelegOrig() {
		return this.sdelegOrig;
	}

	public void setSdelegOrig(BigDecimal sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}

	public String getTipClaseobra() {
		return this.tipClaseobra;
	}

	public void setTipClaseobra(String tipClaseobra) {
		this.tipClaseobra = tipClaseobra;
	}

	public String getTxCurppatron() {
		return this.txCurppatron;
	}

	public void setTxCurppatron(String txCurppatron) {
		this.txCurppatron = txCurppatron;
	}

	public String getTxEmail() {
		return this.txEmail;
	}

	public void setTxEmail(String txEmail) {
		this.txEmail = txEmail;
	}

	public String getTxRfcpatron() {
		return this.txRfcpatron;
	}

	public void setTxRfcpatron(String txRfcpatron) {
		this.txRfcpatron = txRfcpatron;
	}

	public String getTxTelefono() {
		return this.txTelefono;
	}

	public void setTxTelefono(String txTelefono) {
		this.txTelefono = txTelefono;
	}

	public Integer getCvePkTipObra() {
		return cvePkTipObra;
	}

	public void setCvePkTipObra(Integer cvePkTipObra) {
		this.cvePkTipObra = cvePkTipObra;
	}

	public Integer getCvePkFaseConst() {
		return cvePkFaseConst;
	}

	public void setCvePkFaseConst(Integer cvePkFaseConst) {
		this.cvePkFaseConst = cvePkFaseConst;
	}

	public Long getCveFkPatron() {
		return cveFkPatron;
	}

	public void setCveFkPatron(Long cveFkPatron) {
		this.cveFkPatron = cveFkPatron;
	}

	public Integer getDomicilioId() {
		return domicilioId;
	}

	public void setDomicilioId(Integer domicilioId) {
		this.domicilioId = domicilioId;
	}

	public String getRegPatron() {
		return regPatron;
	}

	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getClaseObra() {
		return claseObra;
	}

	public void setClaseObra(String claseObra) {
		this.claseObra = claseObra;
	}

	public String getFaseObra() {
		return faseObra;
	}

	public void setFaseObra(String faseObra) {
		this.faseObra = faseObra;
	}

	public String getTipoObra() {
		return tipoObra;
	}

	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}

	public Integer getCveFkZona() {
		return cveFkZona;
	}

	public void setCveFkZona(Integer cveFkZona) {
		this.cveFkZona = cveFkZona;
	}

	public Integer getNumTrabajdores() {
		return numTrabajdores;
	}

	public void setNumTrabajdores(Integer numTrabajdores) {
		this.numTrabajdores = numTrabajdores;
	}

	public String getNumRegObra() {
		return numRegObra;
	}

	public void setNumRegObra(String numRegObra) {
		this.numRegObra = numRegObra;
	}

	public Integer getIdMotivocancelacion() {
		return idMotivocancelacion;
	}

	public void setIdMotivocancelacion(Integer idMotivocancelacion) {
		this.idMotivocancelacion = idMotivocancelacion;
	}

	public Integer getIdObligado() {
		return idObligado;
	}

	public void setIdObligado(Integer idObligado) {
		this.idObligado = idObligado;
	}

	public Integer getCveSelector() {
		return cveSelector;
	}

	public void setCveSelector(Integer cveSelector) {
		this.cveSelector = cveSelector;
	}

	public Integer getCveCensor() {
		return cveCensor;
	}

	public void setCveCensor(Integer cveCensor) {
		this.cveCensor = cveCensor;
	}

	/**
	 * @return the cveNrocontrato
	 */
	@Transient
	public String getCveNrocontrato() {
		return cveNrocontrato;
	}

	/**
	 * @param cveNrocontrato the cveNrocontrato to set
	 */
	@Transient
	public void setCveNrocontrato(String cveNrocontrato) {
		this.cveNrocontrato = cveNrocontrato;
	}

	public Date getFecFechaCancela() {
		return fecFechaCancela;
	}

	public void setFecFechaCancela(Date fecFechaCancela) {
		this.fecFechaCancela = fecFechaCancela;
	}

	public String getNumeroDeCodigoPostal() {
		return numeroDeCodigoPostal;
	}

	public void setNumeroDeCodigoPostal(String numeroDeCodigoPostal) {
		this.numeroDeCodigoPostal = numeroDeCodigoPostal;
	}

	
	
	
}