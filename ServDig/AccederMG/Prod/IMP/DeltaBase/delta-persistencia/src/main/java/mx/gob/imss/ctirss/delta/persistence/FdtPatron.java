package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDT_PATRON database table.
 * 
 */
@Entity
@Table(name="FDT_PATRON")
public class FdtPatron implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtPatronPK id;

	@Column(name="CVE_ACT", precision=2)
	private BigDecimal cveAct;

	@Column(name="CVE_MODAL_PR", precision=2)
	private BigDecimal cveModalPr;

	@Column(name="DIG_VERIFICADOR", nullable=false, precision=1)
	private BigDecimal digVerificador;

	@Column(name="E_MAIL", length=30)
	private String eMail;

	@Column(name="ENT_FED", nullable=false, precision=2)
	private BigDecimal entFed;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_INICIO_ACT", nullable=false)
	private Date fhInicioAct;

	@Column(name="ID_MUNICIPIO", nullable=false, length=50)
	private String idMunicipio;

	@Column(name="IN_TP_PATRON", nullable=false, length=1)
	private String inTpPatron;

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

	@Column(length=13)
	private String rfc;

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

	@Column(name="TX_TIPO_PERSONA", length=1)
	private String txTipoPersona;

	//bi-directional many-to-one association to CgtCoredisinadi
	@OneToMany(mappedBy="fdtPatron")
	private List<CgtCoredisinadi> cgtCoredisinadis;

	//bi-directional many-to-one association to CgtGestionauxregpat
	@OneToMany(mappedBy="fdtPatron")
	private List<CgtGestionauxregpat> cgtGestionauxregpats;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="fdtPatron")
	private List<CgtGestionsinadi> cgtGestionsinadis;

	//bi-directional many-to-one association to FdtDatosAfil15
	@OneToMany(mappedBy="fdtPatron")
	private List<FdtDatosAfil15> fdtDatosAfil15s;

	//bi-directional many-to-one association to FdtSubdeleg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG"),
		@JoinColumn(name="SDELEG_ORIG", referencedColumnName="SDELEG_ORIG")
		})
	private FdtSubdeleg fdtSubdeleg;

	//bi-directional many-to-one association to FdtPatronCpa
	@OneToMany(mappedBy="fdtPatron")
	private List<FdtPatronCpa> fdtPatronCpas;

	//bi-directional many-to-one association to FdtPatronNuevo
	@OneToMany(mappedBy="fdtPatron")
	private List<FdtPatronNuevo> fdtPatronNuevos;

	//bi-directional many-to-one association to FiPatRegObra
	@OneToMany(mappedBy="fdtPatron")
	private List<FiPatRegObra> fiPatRegObras;

    public FdtPatron() {
    }

	public FdtPatronPK getId() {
		return this.id;
	}

	public void setId(FdtPatronPK id) {
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

	public List<CgtCoredisinadi> getCgtCoredisinadis() {
		return this.cgtCoredisinadis;
	}

	public void setCgtCoredisinadis(List<CgtCoredisinadi> cgtCoredisinadis) {
		this.cgtCoredisinadis = cgtCoredisinadis;
	}
	
	public List<CgtGestionauxregpat> getCgtGestionauxregpats() {
		return this.cgtGestionauxregpats;
	}

	public void setCgtGestionauxregpats(List<CgtGestionauxregpat> cgtGestionauxregpats) {
		this.cgtGestionauxregpats = cgtGestionauxregpats;
	}
	
	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
	public List<FdtDatosAfil15> getFdtDatosAfil15s() {
		return this.fdtDatosAfil15s;
	}

	public void setFdtDatosAfil15s(List<FdtDatosAfil15> fdtDatosAfil15s) {
		this.fdtDatosAfil15s = fdtDatosAfil15s;
	}
	
	public FdtSubdeleg getFdtSubdeleg() {
		return this.fdtSubdeleg;
	}

	public void setFdtSubdeleg(FdtSubdeleg fdtSubdeleg) {
		this.fdtSubdeleg = fdtSubdeleg;
	}
	
	public List<FdtPatronCpa> getFdtPatronCpas() {
		return this.fdtPatronCpas;
	}

	public void setFdtPatronCpas(List<FdtPatronCpa> fdtPatronCpas) {
		this.fdtPatronCpas = fdtPatronCpas;
	}
	
	public List<FdtPatronNuevo> getFdtPatronNuevos() {
		return this.fdtPatronNuevos;
	}

	public void setFdtPatronNuevos(List<FdtPatronNuevo> fdtPatronNuevos) {
		this.fdtPatronNuevos = fdtPatronNuevos;
	}
	
	public List<FiPatRegObra> getFiPatRegObras() {
		return this.fiPatRegObras;
	}

	public void setFiPatRegObras(List<FiPatRegObra> fiPatRegObras) {
		this.fiPatRegObras = fiPatRegObras;
	}
	
}