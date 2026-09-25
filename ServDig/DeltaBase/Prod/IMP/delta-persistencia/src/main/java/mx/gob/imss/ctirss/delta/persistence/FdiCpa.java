package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDI_CPA database table.
 * 
 */
@Entity
@Table(name="FDI_CPA")
public class FdiCpa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CV_CURP", nullable=false, length=18)
	private String cvCurp;

	@Column(length=32)
	private String cuenta;

	@Column(name="CV_RFC", length=18)
	private String cvRfc;

	@Column(name="CV_RFC_CP", length=13)
	private String cvRfcCp;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ACEPTA_DOCTOS")
	private Date fhAceptaDoctos;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ACREDITACION")
	private Date fhAcreditacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ALTA_COLEGIO")
	private Date fhAltaColegio;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_DEBE_ENTREGAR_DOC")
	private Date fhDebeEntregarDoc;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_EXPED_CEDULA")
	private Date fhExpedCedula;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_EXPEDICION_MEMB")
	private Date fhExpedicionMemb;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_REGISTRO")
	private Date fhRegistro;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_SOLICITUD")
	private Date fhSolicitud;

	@Column(name="ID_ASOCIACION_ACR", precision=22)
	private BigDecimal idAsociacionAcr;

	@Column(name="ID_COLEGIO_TIT", precision=22)
	private BigDecimal idColegioTit;

	@Column(name="ID_REQUERIMIENTO", length=200)
	private String idRequerimiento;

	@Column(name="ID_STATUS_BAJA", precision=22)
	private BigDecimal idStatusBaja;

	@Column(name="IN_STATUS_REG", length=1)
	private String inStatusReg;

	@Column(name="IND_DOMICILIADO", precision=22)
	private BigDecimal indDomiciliado;

	@Column(name="LLAVE_PUBLICA", length=500)
	private String llavePublica;

	@Column(name="NU_ANIO_A", precision=22)
	private BigDecimal nuAnioA;

	@Column(name="NU_ANIO_M", precision=22)
	private BigDecimal nuAnioM;

	@Column(name="NU_CEDULA_PRF", precision=63)
	private double nuCedulaPrf;

	@Column(name="NU_CP", length=5)
	private String nuCp;

	@Column(name="NU_EXTENSION", length=20)
	private String nuExtension;

	@Column(name="NU_EXTERIOR", length=50)
	private String nuExterior;

	@Column(name="NU_INTERIOR", length=50)
	private String nuInterior;

	@Column(name="NU_REG_CP", precision=9)
	private BigDecimal nuRegCp;

	@Column(name="NU_TELEFONO", length=50)
	private String nuTelefono;

	@Column(name="SERIAL_CERTIFICADO", length=20)
	private String serialCertificado;

	@Column(name="STATUS_CORREO", length=2)
	private String statusCorreo;

	@Column(name="STATUS_SANCION", length=1)
	private String statusSancion;

	@Column(name="TX_APELLIDO_MATERN", length=50)
	private String txApellidoMatern;

	@Column(name="TX_APELLIDO_PATERN", nullable=false, length=50)
	private String txApellidoPatern;

	@Column(name="TX_CALLE", length=250)
	private String txCalle;

	@Column(name="TX_CARGO", length=50)
	private String txCargo;

	@Column(name="TX_CAUSA_RECHAZO", length=1600)
	private String txCausaRechazo;

	@Column(name="TX_COLONIA", length=100)
	private String txColonia;

	@Column(name="TX_CORREO", length=100)
	private String txCorreo;

	@Column(name="TX_MENSAJE_IN", length=100)
	private String txMensajeIn;

	@Column(name="TX_NOMBRE", nullable=false, length=50)
	private String txNombre;

	@Column(name="TX_STATUS_SANCION", length=100)
	private String txStatusSancion;

	@Column(name="TX_UNIVERSIDAD", length=250)
	private String txUniversidad;

	//bi-directional many-to-one association to FiLada
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CV_LADA")
	private FiLada fiLada;

	//bi-directional many-to-one association to FdiColegio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_COLEGIO_ACR")
	private FdiColegio fdiColegio1;

	//bi-directional many-to-one association to FdiColegio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ASOCIACION_TIT")
	private FdiColegio fdiColegio2;

	//bi-directional many-to-one association to FdtSubdeleg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG"),
		@JoinColumn(name="SDELEG_ORIG", referencedColumnName="SDELEG_ORIG")
		})
	private FdtSubdeleg fdtSubdeleg;

	//bi-directional many-to-one association to FiMunicipiosImssInegi
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		//@JoinColumn(name="ENT_FED"),
		@JoinColumn(name="ID_MUNICIPIO", referencedColumnName="ID_MUNI_INEGI_IMSS")
		})
	private FiMunicipiosImssInegi fiMunicipiosImssInegi;

	//bi-directional many-to-one association to FdiCpaDom
	@OneToMany(mappedBy="fdiCpa")
	private List<FdiCpaDom> fdiCpaDoms;

	//bi-directional many-to-one association to FdiCpaHistorico
	@OneToMany(mappedBy="fdiCpa")
	private List<FdiCpaHistorico> fdiCpaHistoricos;

	//bi-directional many-to-one association to FdiHBajaCpa
	@OneToMany(mappedBy="fdiCpa")
	private List<FdiHBajaCpa> fdiHBajaCpas;

	//bi-directional many-to-one association to FdiHModifCpa
	@OneToMany(mappedBy="fdiCpa")
	private List<FdiHModifCpa> fdiHModifCpas;

	//bi-directional many-to-one association to FdiSancion
	@OneToMany(mappedBy="fdiCpa")
	private List<FdiSancion> fdiSancions;

	//bi-directional many-to-one association to FdtPatronCpa
	@OneToMany(mappedBy="fdiCpa")
	private List<FdtPatronCpa> fdtPatronCpas;

	//bi-directional many-to-one association to FdtRegistroContador
	@OneToMany(mappedBy="fdiCpa")
	private List<FdtRegistroContador> fdtRegistroContadors;

    public FdiCpa() {
    }

	public String getCvCurp() {
		return this.cvCurp;
	}

	public void setCvCurp(String cvCurp) {
		this.cvCurp = cvCurp;
	}

	public String getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getCvRfc() {
		return this.cvRfc;
	}

	public void setCvRfc(String cvRfc) {
		this.cvRfc = cvRfc;
	}

	public String getCvRfcCp() {
		return this.cvRfcCp;
	}

	public void setCvRfcCp(String cvRfcCp) {
		this.cvRfcCp = cvRfcCp;
	}

	public Date getFhAceptaDoctos() {
		return this.fhAceptaDoctos;
	}

	public void setFhAceptaDoctos(Date fhAceptaDoctos) {
		this.fhAceptaDoctos = fhAceptaDoctos;
	}

	public Date getFhAcreditacion() {
		return this.fhAcreditacion;
	}

	public void setFhAcreditacion(Date fhAcreditacion) {
		this.fhAcreditacion = fhAcreditacion;
	}

	public Date getFhAltaColegio() {
		return this.fhAltaColegio;
	}

	public void setFhAltaColegio(Date fhAltaColegio) {
		this.fhAltaColegio = fhAltaColegio;
	}

	public Date getFhDebeEntregarDoc() {
		return this.fhDebeEntregarDoc;
	}

	public void setFhDebeEntregarDoc(Date fhDebeEntregarDoc) {
		this.fhDebeEntregarDoc = fhDebeEntregarDoc;
	}

	public Date getFhExpedCedula() {
		return this.fhExpedCedula;
	}

	public void setFhExpedCedula(Date fhExpedCedula) {
		this.fhExpedCedula = fhExpedCedula;
	}

	public Date getFhExpedicionMemb() {
		return this.fhExpedicionMemb;
	}

	public void setFhExpedicionMemb(Date fhExpedicionMemb) {
		this.fhExpedicionMemb = fhExpedicionMemb;
	}

	public Date getFhRegistro() {
		return this.fhRegistro;
	}

	public void setFhRegistro(Date fhRegistro) {
		this.fhRegistro = fhRegistro;
	}

	public Date getFhSolicitud() {
		return this.fhSolicitud;
	}

	public void setFhSolicitud(Date fhSolicitud) {
		this.fhSolicitud = fhSolicitud;
	}

	public BigDecimal getIdAsociacionAcr() {
		return this.idAsociacionAcr;
	}

	public void setIdAsociacionAcr(BigDecimal idAsociacionAcr) {
		this.idAsociacionAcr = idAsociacionAcr;
	}

	public BigDecimal getIdColegioTit() {
		return this.idColegioTit;
	}

	public void setIdColegioTit(BigDecimal idColegioTit) {
		this.idColegioTit = idColegioTit;
	}

	public String getIdRequerimiento() {
		return this.idRequerimiento;
	}

	public void setIdRequerimiento(String idRequerimiento) {
		this.idRequerimiento = idRequerimiento;
	}

	public BigDecimal getIdStatusBaja() {
		return this.idStatusBaja;
	}

	public void setIdStatusBaja(BigDecimal idStatusBaja) {
		this.idStatusBaja = idStatusBaja;
	}

	public String getInStatusReg() {
		return this.inStatusReg;
	}

	public void setInStatusReg(String inStatusReg) {
		this.inStatusReg = inStatusReg;
	}

	public BigDecimal getIndDomiciliado() {
		return this.indDomiciliado;
	}

	public void setIndDomiciliado(BigDecimal indDomiciliado) {
		this.indDomiciliado = indDomiciliado;
	}

	public String getLlavePublica() {
		return this.llavePublica;
	}

	public void setLlavePublica(String llavePublica) {
		this.llavePublica = llavePublica;
	}

	public BigDecimal getNuAnioA() {
		return this.nuAnioA;
	}

	public void setNuAnioA(BigDecimal nuAnioA) {
		this.nuAnioA = nuAnioA;
	}

	public BigDecimal getNuAnioM() {
		return this.nuAnioM;
	}

	public void setNuAnioM(BigDecimal nuAnioM) {
		this.nuAnioM = nuAnioM;
	}

	public double getNuCedulaPrf() {
		return this.nuCedulaPrf;
	}

	public void setNuCedulaPrf(double nuCedulaPrf) {
		this.nuCedulaPrf = nuCedulaPrf;
	}

	public String getNuCp() {
		return this.nuCp;
	}

	public void setNuCp(String nuCp) {
		this.nuCp = nuCp;
	}

	public String getNuExtension() {
		return this.nuExtension;
	}

	public void setNuExtension(String nuExtension) {
		this.nuExtension = nuExtension;
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

	public BigDecimal getNuRegCp() {
		return this.nuRegCp;
	}

	public void setNuRegCp(BigDecimal nuRegCp) {
		this.nuRegCp = nuRegCp;
	}

	public String getNuTelefono() {
		return this.nuTelefono;
	}

	public void setNuTelefono(String nuTelefono) {
		this.nuTelefono = nuTelefono;
	}

	public String getSerialCertificado() {
		return this.serialCertificado;
	}

	public void setSerialCertificado(String serialCertificado) {
		this.serialCertificado = serialCertificado;
	}

	public String getStatusCorreo() {
		return this.statusCorreo;
	}

	public void setStatusCorreo(String statusCorreo) {
		this.statusCorreo = statusCorreo;
	}

	public String getStatusSancion() {
		return this.statusSancion;
	}

	public void setStatusSancion(String statusSancion) {
		this.statusSancion = statusSancion;
	}

	public String getTxApellidoMatern() {
		return this.txApellidoMatern;
	}

	public void setTxApellidoMatern(String txApellidoMatern) {
		this.txApellidoMatern = txApellidoMatern;
	}

	public String getTxApellidoPatern() {
		return this.txApellidoPatern;
	}

	public void setTxApellidoPatern(String txApellidoPatern) {
		this.txApellidoPatern = txApellidoPatern;
	}

	public String getTxCalle() {
		return this.txCalle;
	}

	public void setTxCalle(String txCalle) {
		this.txCalle = txCalle;
	}

	public String getTxCargo() {
		return this.txCargo;
	}

	public void setTxCargo(String txCargo) {
		this.txCargo = txCargo;
	}

	public String getTxCausaRechazo() {
		return this.txCausaRechazo;
	}

	public void setTxCausaRechazo(String txCausaRechazo) {
		this.txCausaRechazo = txCausaRechazo;
	}

	public String getTxColonia() {
		return this.txColonia;
	}

	public void setTxColonia(String txColonia) {
		this.txColonia = txColonia;
	}

	public String getTxCorreo() {
		return this.txCorreo;
	}

	public void setTxCorreo(String txCorreo) {
		this.txCorreo = txCorreo;
	}

	public String getTxMensajeIn() {
		return this.txMensajeIn;
	}

	public void setTxMensajeIn(String txMensajeIn) {
		this.txMensajeIn = txMensajeIn;
	}

	public String getTxNombre() {
		return this.txNombre;
	}

	public void setTxNombre(String txNombre) {
		this.txNombre = txNombre;
	}

	public String getTxStatusSancion() {
		return this.txStatusSancion;
	}

	public void setTxStatusSancion(String txStatusSancion) {
		this.txStatusSancion = txStatusSancion;
	}

	public String getTxUniversidad() {
		return this.txUniversidad;
	}

	public void setTxUniversidad(String txUniversidad) {
		this.txUniversidad = txUniversidad;
	}

	public FiLada getFiLada() {
		return this.fiLada;
	}

	public void setFiLada(FiLada fiLada) {
		this.fiLada = fiLada;
	}
	
	public FdiColegio getFdiColegio1() {
		return this.fdiColegio1;
	}

	public void setFdiColegio1(FdiColegio fdiColegio1) {
		this.fdiColegio1 = fdiColegio1;
	}
	
	public FdiColegio getFdiColegio2() {
		return this.fdiColegio2;
	}

	public void setFdiColegio2(FdiColegio fdiColegio2) {
		this.fdiColegio2 = fdiColegio2;
	}
	
	public FdtSubdeleg getFdtSubdeleg() {
		return this.fdtSubdeleg;
	}

	public void setFdtSubdeleg(FdtSubdeleg fdtSubdeleg) {
		this.fdtSubdeleg = fdtSubdeleg;
	}
	
	public FiMunicipiosImssInegi getFiMunicipiosImssInegi() {
		return this.fiMunicipiosImssInegi;
	}

	public void setFiMunicipiosImssInegi(FiMunicipiosImssInegi fiMunicipiosImssInegi) {
		this.fiMunicipiosImssInegi = fiMunicipiosImssInegi;
	}
	
	public List<FdiCpaDom> getFdiCpaDoms() {
		return this.fdiCpaDoms;
	}

	public void setFdiCpaDoms(List<FdiCpaDom> fdiCpaDoms) {
		this.fdiCpaDoms = fdiCpaDoms;
	}
	
	public List<FdiCpaHistorico> getFdiCpaHistoricos() {
		return this.fdiCpaHistoricos;
	}

	public void setFdiCpaHistoricos(List<FdiCpaHistorico> fdiCpaHistoricos) {
		this.fdiCpaHistoricos = fdiCpaHistoricos;
	}
	
	public List<FdiHBajaCpa> getFdiHBajaCpas() {
		return this.fdiHBajaCpas;
	}

	public void setFdiHBajaCpas(List<FdiHBajaCpa> fdiHBajaCpas) {
		this.fdiHBajaCpas = fdiHBajaCpas;
	}
	
	public List<FdiHModifCpa> getFdiHModifCpas() {
		return this.fdiHModifCpas;
	}

	public void setFdiHModifCpas(List<FdiHModifCpa> fdiHModifCpas) {
		this.fdiHModifCpas = fdiHModifCpas;
	}
	
	public List<FdiSancion> getFdiSancions() {
		return this.fdiSancions;
	}

	public void setFdiSancions(List<FdiSancion> fdiSancions) {
		this.fdiSancions = fdiSancions;
	}
	
	public List<FdtPatronCpa> getFdtPatronCpas() {
		return this.fdtPatronCpas;
	}

	public void setFdtPatronCpas(List<FdtPatronCpa> fdtPatronCpas) {
		this.fdtPatronCpas = fdtPatronCpas;
	}
	
	public List<FdtRegistroContador> getFdtRegistroContadors() {
		return this.fdtRegistroContadors;
	}

	public void setFdtRegistroContadors(List<FdtRegistroContador> fdtRegistroContadors) {
		this.fdtRegistroContadors = fdtRegistroContadors;
	}
	
}