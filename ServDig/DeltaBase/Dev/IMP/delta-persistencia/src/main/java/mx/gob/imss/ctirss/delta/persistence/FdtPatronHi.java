package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDT_PATRON_HIS database table.
 * 
 */
@Entity
@Table(name="FDT_PATRON_HIS")
public class FdtPatronHi implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtPatronHiPK id;

	@Column(name="CVE_ACT", precision=2)
	private BigDecimal cveAct;

	@Column(name="CVE_MODAL_PR", precision=2)
	private BigDecimal cveModalPr;

	@Column(name="DIG_VERIFICADOR", nullable=false, precision=1)
	private BigDecimal digVerificador;

	@Column(length=30)
	private String email;

	@Column(name="ENT_FED", nullable=false, precision=2)
	private BigDecimal entFed;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_INICIO_ACT", nullable=false)
	private Date fhInicioAct;

	@Column(name="ID_MUNICIPIO", nullable=false, length=50)
	private String idMunicipio;

	@Column(name="NU_CP", nullable=false, length=5)
	private String nuCp;

	@Column(name="NU_EXTERIOR", nullable=false, length=10)
	private String nuExterior;

	@Column(name="NU_INTERIOR", length=10)
	private String nuInterior;

	@Column(name="NU_TRABAJADORES", nullable=false, precision=8)
	private BigDecimal nuTrabajadores;

	@Column(name="REG_PATRON_PR", length=8)
	private String regPatronPr;

	@Column(precision=15)
	private BigDecimal telefono;

	@Column(name="TX_ACTIVIDAD", nullable=false, length=60)
	private String txActividad;

	@Column(name="TX_CALLE", nullable=false, length=50)
	private String txCalle;

	@Column(name="TX_CLASE", nullable=false, length=5)
	private String txClase;

	@Column(name="TX_COLONIA", length=50)
	private String txColonia;

	@Column(name="TX_FRACCION", nullable=false, length=5)
	private String txFraccion;

	@Column(name="TX_PRIMA", nullable=false, length=8)
	private String txPrima;

	@Column(name="TX_RAZON_SOCIAL", nullable=false, length=80)
	private String txRazonSocial;

	@Column(name="TX_REP_LEGAL", nullable=false, length=80)
	private String txRepLegal;

	@Column(name="TX_RFC", length=13)
	private String txRfc;

	//bi-directional many-to-many association to FdtA3Grupo
    @ManyToMany
	@JoinTable(
		name="FDT_A3_GRUPORPS"
		, joinColumns={
			@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false),
			@JoinColumn(name="ID_AVISO", referencedColumnName="ID_AVISO", nullable=false),
			@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="CV_GRUPO", referencedColumnName="CV_GRUPO", nullable=false),
			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false)
			}
		)
	private List<FdtA3Grupo> fdtA3Grupos;

	//bi-directional many-to-one association to FdtSubdeleg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG"),
		@JoinColumn(name="SDELEG_ORIG", referencedColumnName="SDELEG_ORIG")
		})
	private FdtSubdeleg fdtSubdeleg;

	//bi-directional many-to-one association to FdtAviso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AVISO", nullable=false, insertable=false, updatable=false)
	private FdtAviso fdtAviso;

    public FdtPatronHi() {
    }

	public FdtPatronHiPK getId() {
		return this.id;
	}

	public void setId(FdtPatronHiPK id) {
		this.id = id;
	}
	
	public BigDecimal getCveAct() {
		return this.cveAct;
	}

	public void setCveAct(BigDecimal cveAct) {
		this.cveAct = cveAct;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getTxRfc() {
		return this.txRfc;
	}

	public void setTxRfc(String txRfc) {
		this.txRfc = txRfc;
	}

	public List<FdtA3Grupo> getFdtA3Grupos() {
		return this.fdtA3Grupos;
	}

	public void setFdtA3Grupos(List<FdtA3Grupo> fdtA3Grupos) {
		this.fdtA3Grupos = fdtA3Grupos;
	}
	
	public FdtSubdeleg getFdtSubdeleg() {
		return this.fdtSubdeleg;
	}

	public void setFdtSubdeleg(FdtSubdeleg fdtSubdeleg) {
		this.fdtSubdeleg = fdtSubdeleg;
	}
	
	public FdtAviso getFdtAviso() {
		return this.fdtAviso;
	}

	public void setFdtAviso(FdtAviso fdtAviso) {
		this.fdtAviso = fdtAviso;
	}
	
}