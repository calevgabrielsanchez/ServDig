package mx.gob.imss.ctirss.correccion.base.model;


import javax.persistence.*;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CRC_PATRON database table.
 * 
 */
@MappedSuperclass
@IdClass(AbstractCrcPatronPK.class) 
public class AbstractCrcPatron extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REG_PATRON")
	private String regPatron;

	@Id
	@Column(name="CVE_MODAL")
	private long cveModal;

	@Column(name="CVE_DELEG_ORIG")
	private BigDecimal cveDelegOrig;

	@Column(name="CVE_MODAL_PR")
	private BigDecimal cveModalPr;

	@Column(name="DIG_VERIFICADOR")
	private BigDecimal digVerificador;

	@Column(name="E_MAIL")
	private String eMail;

	@Column(name="ENT_FED")
	private BigDecimal entFed;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_INICIO_ACT")
	private Date fhInicioAct;

	@Column(name="ID_MUNICIPIO")
	private String idMunicipio;

	@Column(name="IN_TP_PATRON")
	private String inTpPatron;

	@Column(name="NU_CP")
	private String nuCp;

	@Column(name="NU_EXTERIOR")
	private String nuExterior;

	@Column(name="NU_INTERIOR")
	private String nuInterior;

	@Column(name="NU_TRABAJADORES")
	private BigDecimal nuTrabajadores;

	@Column(name="REG_PATRON_PR")
	private String regPatronPr;

	private String rfc;

	@Column(name="SDELEG_ORIG")
	private BigDecimal sdelegOrig;

	private BigDecimal telefono;

	@Column(name="TX_ACTIVIDAD")
	private String txActividad;

	@Column(name="TX_CALLE")
	private String txCalle;

	@Column(name="TX_CLASE")
	private String txClase;

	@Column(name="TX_COLONIA")
	private String txColonia;

	@Column(name="TX_FRACCION")
	private String txFraccion;

	@Column(name="TX_PRIMA")
	private String txPrima;

	@Column(name="TX_RAZON_SOCIAL")
	private String txRazonSocial;

	@Column(name="TX_REP_LEGAL")
	private String txRepLegal;

	@Column(name="TX_TIPO_PERSONA")
	private String txTipoPersona;

	@Column(name="CVE_ACTECONOMICA")
	private BigDecimal cveActEconomiva;

	
	
	
    public String getRegPatron() {
		return regPatron;
	}

	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}

	public long getCveModal() {
		return cveModal;
	}

	public void setCveModal(long cveModal) {
		this.cveModal = cveModal;
	}

	public AbstractCrcPatron() {
    }

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	
	public BigDecimal getCveActEconomiva() {
		return cveActEconomiva;
	}

	public void setCveActEconomiva(BigDecimal cveActEconomiva) {
		this.cveActEconomiva = cveActEconomiva;
	}

	public BigDecimal getCveDelegOrig() {
		return this.cveDelegOrig;
	}

	public void setCveDelegOrig(BigDecimal cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}

	public BigDecimal getCveModalPr() {
		return this.cveModalPr;
	}

	public void setCveModalPr(BigDecimal cveModalPr) {
		this.cveModalPr = cveModalPr;
	}

	public BigDecimal getDigVerificador() {
		return this.digVerificador;
	}

	public void setDigVerificador(BigDecimal digVerificador) {
		this.digVerificador = digVerificador;
	}

	public String getEMail() {
		return this.eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public BigDecimal getEntFed() {
		return this.entFed;
	}

	public void setEntFed(BigDecimal entFed) {
		this.entFed = entFed;
	}

	public Date getFhInicioAct() {
		return this.fhInicioAct;
	}

	public void setFhInicioAct(Date fhInicioAct) {
		this.fhInicioAct = fhInicioAct;
	}

	public String getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getInTpPatron() {
		return this.inTpPatron;
	}

	public void setInTpPatron(String inTpPatron) {
		this.inTpPatron = inTpPatron;
	}

	public String getNuCp() {
		return this.nuCp;
	}

	public void setNuCp(String nuCp) {
		this.nuCp = nuCp;
	}

	public String getNuExterior() {
		return this.nuExterior;
	}

	public void setNuExterior(String nuExterior) {
		this.nuExterior = nuExterior;
	}

	public String getNuInterior() {
		return this.nuInterior;
	}

	public void setNuInterior(String nuInterior) {
		this.nuInterior = nuInterior;
	}

	public BigDecimal getNuTrabajadores() {
		return this.nuTrabajadores;
	}

	public void setNuTrabajadores(BigDecimal nuTrabajadores) {
		this.nuTrabajadores = nuTrabajadores;
	}

	public String getRegPatronPr() {
		return this.regPatronPr;
	}

	public void setRegPatronPr(String regPatronPr) {
		this.regPatronPr = regPatronPr;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public BigDecimal getSdelegOrig() {
		return this.sdelegOrig;
	}

	public void setSdelegOrig(BigDecimal sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}

	public BigDecimal getTelefono() {
		return this.telefono;
	}

	public void setTelefono(BigDecimal telefono) {
		this.telefono = telefono;
	}

	public String getTxActividad() {
		return this.txActividad;
	}

	public void setTxActividad(String txActividad) {
		this.txActividad = txActividad;
	}

	public String getTxCalle() {
		return this.txCalle;
	}

	public void setTxCalle(String txCalle) {
		this.txCalle = txCalle;
	}

	public String getTxClase() {
		return this.txClase;
	}

	public void setTxClase(String txClase) {
		this.txClase = txClase;
	}

	public String getTxColonia() {
		return this.txColonia;
	}

	public void setTxColonia(String txColonia) {
		this.txColonia = txColonia;
	}

	public String getTxFraccion() {
		return this.txFraccion;
	}

	public void setTxFraccion(String txFraccion) {
		this.txFraccion = txFraccion;
	}

	public String getTxPrima() {
		return this.txPrima;
	}

	public void setTxPrima(String txPrima) {
		this.txPrima = txPrima;
	}

	public String getTxRazonSocial() {
		return this.txRazonSocial;
	}

	public void setTxRazonSocial(String txRazonSocial) {
		this.txRazonSocial = txRazonSocial;
	}

	public String getTxRepLegal() {
		return this.txRepLegal;
	}

	public void setTxRepLegal(String txRepLegal) {
		this.txRepLegal = txRepLegal;
	}

	public String getTxTipoPersona() {
		return this.txTipoPersona;
	}

	public void setTxTipoPersona(String txTipoPersona) {
		this.txTipoPersona = txTipoPersona;
	}

	
}